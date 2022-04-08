<script lang="ts">
  import { List } from 'framework7-svelte';

  import './Cells.scss';

  export let selected: any[] | undefined = undefined;

  let checkbox = false;

  $: selected && !checkbox && selected.length > 1 && (checkbox = true);
  $: selected && checkbox && selected.length === 0 && (checkbox = false);
</script>

<List
  class={`ui-cells ${selected ? 'ui-cells-select' : ''}`}
  inset
  mediaList
  noHairlines
  noHairlinesBetween
  noChevron
  {...$$restProps}
>
  <slot {checkbox} />
</List>
