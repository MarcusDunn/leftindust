<script lang="ts">
  import { getTemplateSocketType, TemplateInputType, type TemplateCalculation } from '../..';
  import Input from '@/features/Input/Input.svelte';
  import { writable, type Writable } from 'svelte/store';
  import { _ } from 'svelte-i18n';
  import { Button } from 'framework7-svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';
  import { TemplateDefaultComputation, TemplateNodesModalOpen } from '../../store';
  import type { TemplateInput } from '../..';
  import type { NodeBlueprint } from 'function-junctions/types';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import TemplateInputNode from '@/features/Node/components/TemplateInputNode';

  export let index: number;
  export let computations: TemplateCalculation[];
  export let calculation: TemplateCalculation;

  export let inputs: TemplateInput[];
  export let modalOpen = false;

  let nodeInputs: Record<string, { type: string; value: Writable<unknown> }> = {};

  inputs.forEach((input) => {
    const type = getTemplateSocketType(input.type);
    
    nodeInputs = {
      ...nodeInputs,
      [input.id]: {
        type,
        value: writable(),
      },
    };
  });

  const nodes: Record<string, NodeBlueprint> = {
    'template-input': TemplateInputNode,
  };

  $: $TemplateDefaultComputation;

  $: inputs.forEach((input) => {
    nodeInputs[input.id].value.set(input.value);
  });
</script>

<NodesModal
  {nodes}
  inputs={nodeInputs}
  bind:state={calculation.calculation}
  bind:open={modalOpen}
  on:close={() => $TemplateNodesModalOpen = false}
/>

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
    bind:value={calculation.type}
  />
  <p />
  <Input style="width: 100%">
    <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
    <input
      type="text"
      bind:value={calculation.label}
      placeholder={$_('examples.calculation')}
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
        Edit Calculation
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
              label: calculation.label,
              type: calculation.type,
              calculation: calculation.calculation,
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
