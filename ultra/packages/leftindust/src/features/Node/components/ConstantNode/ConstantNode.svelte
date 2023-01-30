<script lang="ts">
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  import { List, ListInput } from 'framework7-svelte';

  export let outputs: OutputSockets<{
    Constant: OutputSocket<number>;
  }>;

  export let store: ('pi' | 'e' | 'inf') = 'pi';

  const { value } = outputs.Constant;

  $: $value = (() => {
    switch (store) {
      case 'pi':
        return Math.PI;
      case 'e':
        return Math.E;
      case 'inf':
        return Infinity;
    }
  })();
</script>

<List class="no-margin" noHairlines>
  <ListInput
    type="select"
    style="background-color:#F2F2F7; border-radius: 6px"
    bind:value={store}
  >
    <option value="pi">&pi;</option>
    <option value="e">e</option>
    <option value="inf">&infin;</option>
  </ListInput>
</List>