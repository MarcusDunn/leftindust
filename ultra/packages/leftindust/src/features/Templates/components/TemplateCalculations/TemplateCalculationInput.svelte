<script lang="ts">
  import type { TemplateComputation } from '../..';
  import Input from '@/features/Input/Input.svelte';
  import { writable, type Writable } from 'svelte/store';
  import { _ } from 'svelte-i18n';
  import { Button } from 'framework7-svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';
  import { TemplateNodesModalOpen } from '../../store';
  import type { TemplateInput } from '../..';
  import type { NodeBlueprint } from 'function-junctions/types';
  import TemplateInputNode from '@/features/Node/components/TemplateInputNode';

  export let index: number;
  export let computations: TemplateComputation[];
  export let computation: TemplateComputation;

  export let inputs: TemplateInput[];
  export let modalOpen = false;

  let nodeInputs: Record<string, Record<string, Writable<unknown>>>;

  inputs.forEach((input) => {
    nodeInputs = {
      ...nodeInputs,
      [input.category ?? '']: {
        ...(nodeInputs?.[input.category ?? ''] ?? {}),
        [input.id]: writable(),
      },
    };
  });

  const nodes: Record<string, NodeBlueprint> = {
    Input: TemplateInputNode,
  };
</script>

<NodesModal
  {nodes}
  bind:state={computation.computation}
  bind:open={modalOpen}
  on:close={() => $TemplateNodesModalOpen = false}
/>

<div>
  <Input style="width: 100%">
    <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
    <input
      type="text"
      bind:value={computation.title}
      placeholder={$_('examples.computation')}
    />
  </Input>
  <div class="display-flex" style="margin-top: 20px">
    <div style="width: 100%;margin-top: 11px">
      <Button
        color="deeppurple"
        round
        outline
        on:click={() => {
          $TemplateNodesModalOpen = true;
          modalOpen = true;
        }}
      >
        Edit Algorithm
      </Button>
    </div>
    <div class="flex-grow" />
    <div class="display-flex" style="padding-left: 15px">
      <MenuButton
        title={$_('generics.clone')}
        icon={{
          f7: 'square_on_square',
          color: 'gray',
        }}
        on:click={() => {
          computations = [
            ...computations.slice(0, index),
            {
              title: computation.title,
              computation: computation.computation,
            },
            ...computations.slice(index),
          ];
        }}
      />
      <MenuButton
        title={$_('generics.delete')}
        icon={{
          f7: 'minus_circle_fill',
          color: 'red',
        }}
        on:click={() => (computations = computations.filter((_, i) => i !== index))}
      />
    </div>
  </div>
</div>
