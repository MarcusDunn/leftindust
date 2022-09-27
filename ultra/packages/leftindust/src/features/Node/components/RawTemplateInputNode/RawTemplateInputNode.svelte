<script lang="ts">
  import type { Editor } from 'function-junctions';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';

  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown>;
  }>;

  export let editor: Editor;

  export let store: {
    id: number | undefined;
  } = {
    id: undefined,
  };
    
  const { value: Value } = outputs.Value;

  const { value } = (editor.inputs[store.id ?? 0] ??= { value: { value: undefined } }).value;
    
  $: if (typeof store.id !== 'undefined' && value && Value) $Value = $value;
</script>