/** eslint-disable no-undef */
<script lang="ts">
  import type { Editor } from 'function-junctions';
  import type { InputSockets, InputSocket } from 'function-junctions/types';
  import type { Writable } from 'svelte/store';

  export let editor: Editor;
  
  export let inputs: InputSockets<{
    Value: InputSocket<unknown>;
  }>;

  const { value: Value } = inputs.Value;

  let value: Writable<unknown> | undefined;
    
  $: value = editor.outputs?.Value?.value;

  $: if (value && Value) $value = $Value;
</script>