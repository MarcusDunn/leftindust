<script lang="ts">
  import { AccordionItem, AccordionContent } from 'framework7-svelte';
  import CollapsableButton from './CollapsableButton.svelte';

  import './CollapsableContent.scss';

  export let title: string | undefined = undefined;

  // 2 opened variables because controlling the AccordionItem "opened" prop causes
  // animation to go away.

  export let opened = true;
  let openState = opened;

</script>

<div class="ui-collapsable_content">
  <AccordionItem opened={opened}>
    <div class="ui-collapsable_content-title">
      <CollapsableButton
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
