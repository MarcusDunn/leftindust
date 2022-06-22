<script lang="ts">
  import type { Writable } from 'svelte/store';
  import type { Editor, OutputSocket, OutputSockets, SocketBlueprint } from 'function-junctions/types';

  export let editor: Editor;
  
  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown>;
  }>;

  export let store: {
    id: number;
  } = {
    id: 0,
  };

  const { value: Value } = outputs.Value;
  const { registered } = editor.nodes;
  
  let name = '';
  let type = '';
  let value: Writable<unknown> | undefined;

  let sockets: SocketBlueprint[] = [];

  $: name, type, (() => {
    const store = editor.inputs?.[type]?.[name];
    value = store;
  })();

  $: if (value) {
    $Value = $value;
  }

  $: Object.keys($registered).forEach((key) => {
    let io = {
      ...$registered[key].inputs,
      ...$registered[key].outputs,
    };

    sockets = [
      ...sockets,
      ...Object.keys(io).map((key) => io[key]),
    ].filter((socket, index, self) =>
      index === self.findIndex((t) => (
        t.type === socket.type
      )),
    ).filter((socket) => !!socket.type);
  });

  $: {
    outputs.Value.type = type;
    outputs.Value.disabled = !value;
    outputs.Value.color = sockets.filter((socket) => socket.type === type)?.[0]?.color;
  }

  $: if (!type) type = sockets[0].type;
</script>


<p />
<div style="padding: 0 15px">
  <input type="text" placeholder="Name" bind:value={name} />
</div>
<p />