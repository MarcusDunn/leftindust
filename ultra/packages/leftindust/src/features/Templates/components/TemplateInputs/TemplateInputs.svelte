<script lang="ts">
  import type { DndEvent } from 'svelte-dnd-action';
  import type { TemplateInput as TemplateInputT } from '../../';
  import { TemplateInputType, TemplateInputUploadType } from '../../';
  import { dndzone, SOURCES, TRIGGERS	} from 'svelte-dnd-action';
  import { flip } from 'svelte/animate';
  import { Button } from 'framework7-svelte';
  import TemplateInput from '../TemplateInput/TemplateInput.svelte';
  import { TemplateCalculations } from '../../store';
	
  export let sectionIndex: number;
  export let inputs: TemplateInputT[] = [];
  export let globalIndex = 0;
			
  const flipDurationMs = 200;
  let dragDisabled = true;
			
  const handleConsider = (e: CustomEvent<DndEvent>) => {
    const { items: newItems, info: { source, trigger } } = e.detail;
    inputs = newItems as TemplateInputT[];
    // Ensure dragging is stopped on drag finish via keyboard
    if (source === SOURCES.KEYBOARD && trigger === TRIGGERS.DRAG_STOPPED) {
      dragDisabled = true;
    }
  };

  const handleFinalize = (e: CustomEvent<DndEvent>) => {
    const { items: newItems, info: { source } } = e.detail;
    inputs = newItems as TemplateInputT[];
    // Ensure dragging is stopped on drag finish via pointer (mouse, touch)
    if (source === SOURCES.POINTER) {
      dragDisabled = true;
    }
  };

  const startDrag = (e: Event) => {
    // preventing default to prevent lag on touch devices (because of the browser checking for screen scrolling)
    e.preventDefault();
    dragDisabled = false;
  };

  const addNodeToComputations = (input: TemplateInputT, index: number) => {
    $TemplateCalculations.forEach((calculation) => {
      calculation.editor?.addNode(
        'input',
        {
          x: 75,
          y: ((inputs.length - 1) * 230) + 100,
        },
        {
          id: `i_${input.id}`,
          blueprint: {
            type: 'input',
            x: 75,
            y: ((inputs.length - 1) * 230) + 100,
            store: {
              sectionIndex,
              index: index,
              id: input.id,
            },
          },
        },
      );
    });
  };

  const deleteNodeFromComputation = (id: string) => {
    $TemplateCalculations.forEach((calculation) => {
      console.log(`i_${id}`);
      calculation.editor?.deleteNode(`i_${id}`);
    });
  };
</script>

{#if inputs.length > 0}
  <section
    use:dndzone={{ items: inputs, dragDisabled, flipDurationMs, type: 'inputs' }}
    on:consider={handleConsider}
    on:finalize={handleFinalize}
  >
    {#each inputs as input, index (input.id)}
      <div
        animate:flip={{ duration: flipDurationMs }}
        style="margin-bottom: 30px"
      >
        <TemplateInput
          {index}
          dragger={startDrag}
          {addNodeToComputations}
          {deleteNodeFromComputation}
          bind:globalIndex
          bind:inputs
          bind:type={input.type}
          bind:label={input.label}
          bind:options={input.options}
          bind:placeholder={input.placeholder}
          bind:uploadAccept={input.uploadAccept}
          bind:uploadMultiple={input.uploadMultiple}
          bind:required={input.required}
        />
      </div>
    {/each}
  </section>
{/if}
<div style={`${inputs.length > 0 ? 'margin-left: 70px;margin-top: 40px;' : ''}`}>
  <div class="display-flex">
    <Button
      fill
      round
      color="deeppurple"
      style="width: 100%;margin-right: 0"
      on:click={() => {
        inputs = [
          ...inputs,
          {
            id: globalIndex,
            type: TemplateInputType.Text,
            label: '',
            placeholder: '',
            required: false,
            options: [''],
            uploadAccept: TemplateInputUploadType.All,
            uploadMultiple: true,
          },
        ];
        addNodeToComputations(inputs[inputs.length - 1], inputs.length - 1);
        globalIndex += 1;
      }}
    >
      Add Input
    </Button>
  </div>
</div>