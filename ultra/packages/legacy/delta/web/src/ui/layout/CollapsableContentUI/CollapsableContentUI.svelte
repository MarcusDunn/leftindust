<script lang="ts">
  import { AccordionItem, AccordionContent } from 'framework7-svelte';
  import CollapsableButtonUI from '../../controller/CollapsableButtonUI/CollapsableButtonUI.svelte';

  export let title: string | undefined = undefined;

  // 2 opened variables because controlling the AccordionItem "opened" prop causes
  // animation to go away.

  export let opened = true;
  let openState = opened;

</script>

<div class="ultra-collapsable-content">
  <AccordionItem opened={opened}>
    <div class="ultra-collapsable-content-title">
      <CollapsableButtonUI
        on:click={() => (openState = !openState)}
        opened={openState}
      />
      <slot name="title" />
      {#if title}
        <h4 on:pointerdown>{title}</h4>
      {/if}
      <span class="flex-grow"></span>
      <div>
        <slot name="controls" />
      </div>
    </div>
    <AccordionContent>
      <slot />
    </AccordionContent>
  </AccordionItem>
</div>
