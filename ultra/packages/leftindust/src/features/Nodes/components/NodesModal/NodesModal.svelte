<script lang="ts">
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { Editor } from 'function-junctions';

  import { _ } from '@/language';

  import './NodesModal.scss';

  export let open = false;
</script>

<div class={`nodes-nodes_modal ${open ? 'nodes-nodes_modal-open' : ''}`}>
  <Editor nodes={{}} />
  <div class={`nodes-nodes_modal-controls ${open ? 'nodes-nodes_modal-controls-open' : ''}`}>
    <div class="nodes-nodes_modal-controls-content">
      <MenuButton
        title="Pointer"
        icon={{ f7: 'cursor_rays', color: 'blue' }}
      />
      <MenuButton
        title="Select"
        icon={{ f7: 'crop', color: 'gray' }}
      />
      <div class="flex-grow" />
      <MenuButton
        title={$_('generics.close')}
        icon={{ f7: 'xmark_circle_fill', color: 'gray' }}
        on:click={() => (open = false)}
      />
    </div>
  </div>
</div>