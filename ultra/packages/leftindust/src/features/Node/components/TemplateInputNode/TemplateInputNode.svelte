<script lang="ts">
  import type { Writable } from 'svelte/store';
  import type { Editor, OutputSocket, OutputSockets, SocketBlueprint } from 'function-junctions/types';
  import Input from '@/features/Input/Input.svelte';
  import { _ } from '@/language';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import { TemplateInputType } from '@/features/Templates';
  import { TemplateInputItems } from '@/features/Templates/store';
  
  export let editor: Editor;
  
  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown>;
  }>;

  export let store: {
    sectionIndex: number;
    index: number;
    id: string;
  } = {
    sectionIndex: 0,
    index: 0,
    id: '',
  };

  const { value: Value } = outputs.Value;
  const { registered } = editor.nodes;
  
  let label = '';
  let type = '';
  let value: Writable<unknown> | undefined;

  let sockets: SocketBlueprint[] = [];

  $: type, (() => {
    value = editor.inputs?.[type]?.value;
  })();

  $: if (value) $Value = $value;

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

  $: input = $TemplateInputItems.sections[store.sectionIndex].inputs[store.index];

  $: input.type = type as TemplateInputType;
  $: input.label = label;
</script>

<div>
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
  <Input style="width: 100%">
    <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
    <input
      type="text"
      bind:value={label}
      placeholder={$_('examples.totalPlateletCount')}
    />
  </Input>
</div>
