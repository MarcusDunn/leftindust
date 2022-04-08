<script lang="ts">
  import { _ } from '@/language';
  import { Block } from 'framework7-svelte';
  import CollapsableContent from '@/features/UI/components/Collapsable/CollapsableContent.svelte';

  export let title = $_('generics.thisClinic');
</script>

<Block style="margin-top: -5px">
  {#if $$slots.recents}
    <CollapsableContent title={$_('generics.recents')}>
      <slot name="recents" />
    </CollapsableContent>
    <br />
  {/if}
  <h4>{title}</h4>
  <slot />
</Block>
