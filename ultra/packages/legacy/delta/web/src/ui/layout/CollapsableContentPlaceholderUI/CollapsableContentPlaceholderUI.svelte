<script lang="ts">
  import { BlockFooter } from 'framework7-svelte';

  export let center = false;

</script>

<div class={`ultra-collapsable-content-placeholder ${center ? 'text-align-center' : ''}`}>
  <BlockFooter>
    <slot />
  </BlockFooter>
</div>
