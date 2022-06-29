<script lang="ts">
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { Editor } from 'function-junctions';
  import type { Editor as EditorType, EditorState, NodeBlueprint } from 'function-junctions/types';
  import type { Writable } from 'svelte/store';
  import type { MenuNodes } from '../..';

  import { _ } from '@/language';
  import { createEventDispatcher } from 'svelte';

  import './NodesModal.scss';

  const dispatch = createEventDispatcher();

  export let open = false;
  export let inputs: Record<string, { type: string; value: Writable<unknown> }> = {};
  export let outputs: Record<string, { type: string; value: Writable<unknown> }> = {};
  export let nodes: Record<string, NodeBlueprint> = {};
  export let state: EditorState;

  export let editor: EditorType | undefined = undefined;

  export let menuNodes: MenuNodes = [];

  $: console.log(inputs);
</script>

<div class={`nodes-nodes_modal ${open ? 'nodes-nodes_modal-open' : ''}`}>
  <Editor
    {inputs}
    {outputs}
    {nodes}
    bind:state
    bind:instance={editor}
  />
  <div class={`nodes-nodes_modal-controls ${open ? 'nodes-nodes_modal-controls-open' : ''}`}>
    <div class="nodes-nodes_modal-controls-content">
      <MenuButton
        title="Create"
        icon={{ f7: 'plus_circle_fill', color: 'blue' }}
      />
      <div class="flex-grow" />
      <MenuButton
        title={$_('generics.close')}
        icon={{ f7: 'xmark_circle_fill', color: 'gray' }}
        on:click={() => {
          open = false;
          dispatch('close');
        }}
      />
    </div>
  </div>
</div>