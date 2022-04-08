<script lang="ts">
  import { List } from 'framework7-svelte';

</script>

<List
  class="ultra-selectable-list"
  inset
  mediaList
  noHairlines
  noHairlinesBetween
  noChevron
  {...$$restProps}
>
  <slot />
</List>
