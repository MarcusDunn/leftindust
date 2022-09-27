<script lang="ts">
  import { SurveyTemplateInputType } from '@/api/server';

  import type { Editor } from 'function-junctions';
  import type { OutputSocket, OutputSockets } from 'function-junctions/types';
  import { get } from 'svelte/store';

  export let outputs: OutputSockets<{
    Values: OutputSocket<unknown[]>;
    Indexes: OutputSocket<number[]>;
  }>;

  export let editor: Editor;

  export let store: {
    type: SurveyTemplateInputType;
    ids: string[];
    options: Record<string, string[] | undefined>;
  } = {
    type: SurveyTemplateInputType.Text,
    ids: [],
    options: {},
  };
    
  const { value: Values } = outputs.Values;
  const { value: Indexes } = outputs.Indexes;

  $: $Values = store.ids.map(
    (id) => editor.inputs?.[id]?.value ? get(editor.inputs[id].value) : undefined,
  ).filter((value) => value !== undefined);

  $: if (store.type === SurveyTemplateInputType.SingleSelect) {
    const indexes: number[] = store.ids.map((id) => {
      const value = editor.inputs?.[id]?.value ? get(editor.inputs[id].value) : undefined;
      
      if (value) {
        const index = store.options[id]?.findIndex((option) => option === (value as string[])[0]);
        return index;
      }
    }).filter((value): value is number => value !== undefined);

    console.log(indexes);

    $Indexes = indexes;
  } else {
    $Indexes = [];
  }

</script>