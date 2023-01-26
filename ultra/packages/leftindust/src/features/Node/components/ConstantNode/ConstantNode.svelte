<script lang="ts">
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  import { List, ListInput } from 'framework7-svelte';

  export let outputs: OutputSockets<{
    Value: OutputSocket<number>;
  }>;

  export let store: ('pi' | 'e') = 'pi';

  const { value } = outputs.Value;

  $: $value = (() => {
    switch (store) {
      case 'pi':
        return Math.PI;
      case 'e':
        return Math.E;
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
  </ListInput>
</List>