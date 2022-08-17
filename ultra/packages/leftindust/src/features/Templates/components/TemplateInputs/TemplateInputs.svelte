<script lang="ts">
  import type { DndEvent } from 'svelte-dnd-action';
  import type { templateForm, Template as TemplateType } from '../../';
  import { dndzone, SOURCES, TRIGGERS	} from 'svelte-dnd-action';
  import { flip } from 'svelte/animate';
  import { Button } from 'framework7-svelte';
  import TemplateInput from '../TemplateInput/TemplateInput.svelte';
  import { SurveyTemplateCategory, SurveyTemplateInputType, TemplateInputUploadType } from '@/api/server';
  
  export let inputs: TemplateType['sections'][number]['inputs'] = [];
  export let globalIndex = 0;
  export let sectionIndex = 0;
			
  const flipDurationMs = 200;
  let dragDisabled = true;
			
  const handleConsider = (e: CustomEvent<DndEvent>) => {
    const { items: newItems, info: { source, trigger } } = e.detail;
    inputs = newItems as TemplateType['sections'][number]['inputs'];
    // Ensure dragging is stopped on drag finish via keyboard
    if (source === SOURCES.KEYBOARD && trigger === TRIGGERS.DRAG_STOPPED) {
      dragDisabled = true;
    }
  };

  const handleFinalize = (e: CustomEvent<DndEvent>) => {
    const { items: newItems, info: { source } } = e.detail;
    inputs = newItems as TemplateType['sections'][number]['inputs'];
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

  export let errors: ReturnType<typeof templateForm>['errors'];
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
          {sectionIndex}
          dragger={startDrag}
          {errors}
          bind:globalIndex
          bind:inputs
          bind:type={input.type}
          bind:id={input.id}
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
            type: SurveyTemplateInputType.Text,
            label: '',
            placeholder: '',
            required: false,
            options: [''],
            uploadAccept: TemplateInputUploadType.All,
            uploadMultiple: true,
            category: SurveyTemplateCategory.Body,
            value: '',
          },
        ];
        globalIndex += 1;
      }}
    >
      Add Input
    </Button>
  </div>
</div>