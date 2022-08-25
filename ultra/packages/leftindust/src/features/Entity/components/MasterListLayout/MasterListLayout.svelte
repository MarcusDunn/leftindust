<script lang="ts">
  import { _ } from '@/language';
  import { Block, Button, Icon, Link } from 'framework7-svelte';
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
  <h4 class="display-flex" style="align-items: center">
    <span>{title}</span>
    <!-- 
      <div style="flex-grow: 1" />
      <Link>
        <Icon f7="arrow_up_arrow_down_circle_fill" color="gray" size="24" style="margin-right: 4px" />
      </Link>
      <Link>
        <Icon f7="ellipsis_circle_fill" color="purple" size="24" />
      </Link>
      -->
    </h4>
  <slot />
</Block>
