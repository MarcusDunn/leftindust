<script lang="ts">
  import { List } from 'framework7-svelte';
</script>

<List
  class="ultra-inset-list"
  inset
  mediaList
  noHairlinesBetween
  noHairlines
  {...$$restProps}
>
  <slot />
</List>