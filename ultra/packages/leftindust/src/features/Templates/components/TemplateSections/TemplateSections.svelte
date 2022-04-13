<script lang="ts">
  import type { DndEvent } from 'svelte-dnd-action';
  import type { TemplateSection as TemplateSectionT } from '../..';
  import TemplateSection from '../TemplateSection/TemplateSection.svelte';
  import { dndzone, SOURCES, TRIGGERS	} from 'svelte-dnd-action';
  import { flip } from 'svelte/animate';
  import { Button } from 'framework7-svelte';
  import { _ } from 'svelte-i18n';

  const flipDurationMs = 200;
  let dragDisabled = true;

  // Global index starts at 1 because sections always init with one item
  let globalIndex = 1;

  export let sections: TemplateSectionT[];
        
  const handleConsider = (e: CustomEvent<DndEvent>) => {
    const { items: newItems, info: { source, trigger } } = e.detail;
    sections = newItems as TemplateSectionT[];
    // Ensure dragging is stopped on drag finish via keyboard
    if (source === SOURCES.KEYBOARD && trigger === TRIGGERS.DRAG_STOPPED) {
      dragDisabled = true;
    }
  };

  const handleFinalize = (e: CustomEvent<DndEvent>) => {
    const { items: newItems, info: { source } } = e.detail;
    sections = newItems as TemplateSectionT[];
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
</script>

<section
  use:dndzone="{{ items: sections, dragDisabled, flipDurationMs, type: 'sections' }}"
  on:consider="{handleConsider}"
  on:finalize="{handleFinalize}"
>
  {#each sections as section, index (section.id)}
    <div
      animate:flip="{{ duration: flipDurationMs }}"
    >
      <TemplateSection
        dragger={sections.length > 1 ? startDrag : undefined}
        {index}
        bind:sections
        bind:globalIndex
        bind:section
      />
    </div>
  {/each}
</section>
<br />
<br />
<br />
<div class="display-flex">
  <div class="flex-grow" />
  <Button
    fill
    round
    color="deeppurple"
    style="width: 100%;margin-right: 0"
    on:click={() => {
      sections = [
        ...sections,
        {
          title: '',
          inputs: [],
          id: globalIndex,
        },
      ];

      globalIndex += 1;
    }}
  >
    {$_('generics.addSection')}
  </Button>
</div>