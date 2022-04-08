<script lang="ts">
  import { List } from 'framework7-svelte';

</script>

<List class="ultra-pinnable-list" inset mediaList noHairlines noHairlinesBetween {...$$restProps}>
  <slot />
</List>
