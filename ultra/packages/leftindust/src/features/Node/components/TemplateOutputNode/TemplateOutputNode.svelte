<script lang="ts">
  import Select from '@/features/Input/components/Select/Select.svelte';
  
  import type { Editor,  InputSocket, InputSockets, SocketBlueprint } from 'function-junctions/types';
  import type { Writable } from 'svelte/store';

  import { TemplateInputType } from '@/features/Templates';
  import { _ } from '@/language';

  export let editor: Editor;
  
  export let inputs: InputSockets<{
    Value: InputSocket<unknown>;
  }>;

  const { value: Value } = inputs.Value;
  const { registered } = editor.nodes;
  
  let name = '';
  let type = '';

  let store: Writable<unknown> | undefined;
  
  let sockets: SocketBlueprint[] = [];

  $: name, type, (() => {
    if (store && Value) $store = $Value;
  })();

  $: store = editor.outputs?.[name]?.value;

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
    inputs.Value.type = type;
    inputs.Value.disabled = !store;
    inputs.Value.color = sockets.filter((socket) => socket.type === type)?.[0]?.color;
  }

  $: if (!type) type = sockets[0].type;
</script>

<Select
  title={$_('generics.type')}
  placeholder={$_('examples.text')}
  options={[
    {
      text: $_('generics.text'),
      value: TemplateInputType.Text,
    },
    {
      text: $_('generics.number'),
      value: TemplateInputType.Number,
    },
    {
      text: $_('generics.date'),
      value: TemplateInputType.Date,
    },
    {
      text: $_('generics.paragraph'),
      value: TemplateInputType.Paragraph,
    },
    {
      text: $_('generics.singleSelect'),
      value: TemplateInputType.SingleSelect,
    },
    {
      text: $_('generics.multiSelect'),
      value: TemplateInputType.MultiSelect,
    },
    {
      text: $_('generics.upload'),
      value: TemplateInputType.Upload,
    },
    {
      text: $_('generics.title'),
      value: TemplateInputType.Title,
    },
  ]}
  bind:value={type}
/>
<p />
<div style="padding: 0 15px">
  <input type="text" placeholder="Name" bind:value={name} />
</div>
<p />