<script lang="ts">
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { Editor } from 'function-junctions';
  import type { Editor as EditorType, EditorState, NodeBlueprint } from 'function-junctions/types';
  import type { Writable } from 'svelte/store';
  import type { MenuNode, MenuNodes } from '../..';

  import { _ } from '@/language';
  import { createEventDispatcher } from 'svelte';
  import NodesSelectorModal from '../NodesSelectorModal/NodesSelectorModal.svelte';
  import type { Popover } from 'framework7/types';

  import './NodesModal.scss';

  const dispatch = createEventDispatcher();

  export let open = false;
  export let inputs: Record<string, { type: string; value: Writable<unknown> }> = {};
  export let outputs: Record<string, { type: string; value: Writable<unknown> }> = {};
  export let nodes: Record<string, NodeBlueprint> = {};
  export let state: EditorState;

  export let editor: EditorType | undefined = undefined;

  export let menuNodes: MenuNodes = [];

  export let editable = true;

  let modal: Popover.Popover;

  const onAdd = (nodes: MenuNode[]) => nodes.forEach((node) => editor?.addNode(node.title, {
    x: (window.innerWidth / 2) - 100,
    y: (window.innerHeight / 2) - 100,
  }));

  // The type of event.currentTarget is incompatible with f7's popover type which is wrong
  // @ts-expect-error
  const openModal = (event: MouseEvent) => (modal.open(event.currentTarget, true));
</script>

<div class={`nodes-nodes_modal ${open ? 'nodes-nodes_modal-open' : ''}`}>
  <NodesSelectorModal
    {menuNodes}
    bind:instance={modal}
    on:add={({ detail }) => {
      onAdd(detail);
      modal.close();
    }}
  />
  <Editor
    {inputs}
    {outputs}
    {nodes}
    bind:state
    bind:instance={editor}
    {editable}
    interactable={editable}
    nodeControlButtons={editable}
  />
  <div class={`nodes-nodes_modal-controls ${open ? 'nodes-nodes_modal-controls-open' : ''}`}>
    <div class="nodes-nodes_modal-controls-content">
      {#if menuNodes.length > 0}
        <MenuButton
          title="Create"
          icon={{ f7: 'plus_circle_fill', color: 'blue' }}
          on:click={openModal}
        />
      {/if}

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