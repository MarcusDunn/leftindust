<script lang="ts">
  import { BlockFooter } from 'framework7-svelte';

  import './CollapsableContentPlaceholder.scss';

  export let center = false;

</script>

<div class={`ui-collapsable_content-placeholder ${center ? 'text-align-center' : ''}`}>
  <BlockFooter>
    <slot />
  </BlockFooter>
</div>
