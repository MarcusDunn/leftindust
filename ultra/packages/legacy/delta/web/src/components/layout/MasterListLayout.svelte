<script lang="ts">
  import language from '../../languages';
  import { Block } from 'framework7-svelte';
  import CollapsableContentUI from '../../ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';

  export let title = language().headers.clinic.text;

</script>

<Block style="margin-top: -5px">
  {#if $$slots.recents}
    <CollapsableContentUI title={language().headers.recents.text}>
      <slot name="recents" />
    </CollapsableContentUI>
    <br />
  {/if}
  <h4>{title}</h4>
  <slot />
</Block>
