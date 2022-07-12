<script lang="ts">
  import { TemplateInputType, type TemplateCalculationWithInstance } from '../..';
  import Input from '@/features/Input/Input.svelte';
  import { writable, type Writable } from 'svelte/store';
  import { _ } from 'svelte-i18n';
  import { Button, ListItem, Toggle } from 'framework7-svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';
  import { TemplateNodesModalOpen } from '../../store';
  import type { Editor, NodeBlueprint } from 'function-junctions/types';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import TemplateInputNode from '@/features/Node/components/TemplateInputNode';
  import TemplateOutputNode from '@/features/Node/components/TemplateOutputNode';
  import MathNode from '@/features/Node/components/MathNode';
  import PowerNode from '@/features/Node/components/PowerNode';
  import LogNode from '@/features/Node/components/LogNode';
  import TemplateInputsNode from '@/features/Node/components/TemplateInputsNode';
  import type { MenuNodes } from '@/features/Nodes';
  import ConditionalNode from '@/features/Node/components/ConditionalNode';
  import TrigNode from '@/features/Node/components/TrigNode';
  import JoinNode from '@/features/Node/components/JoinNode';

  export let index: number;
  export let calculations: TemplateCalculationWithInstance[];
  export let calculation: TemplateCalculationWithInstance;

  export let modalOpen = false;

  export let nodeInputs: Record<string, { type: string; value: Writable<unknown> }>;
  
  let nodeOutputs = {
    Value: {
      type: '',
      value: writable(),
    },
  };

  const { value } = nodeOutputs.Value;

  let editor: Editor;

  const nodes: Record<string, NodeBlueprint> = {
    input: TemplateInputNode,
    output: TemplateOutputNode,
    Group: TemplateInputsNode,
    Math: MathNode,
    Condition: ConditionalNode,
    Power: PowerNode,
    Logarithm: LogNode,
    Trigonometry: TrigNode,
    Join: JoinNode,
  };

  const menuNodes: MenuNodes = [{
    title: 'I/O',
    description: 'Tools to handle inputs and outputs',
    color: 'deeppurple',
    icon: {
      f7: 'skew',
    },
    nodes: [{
      title: 'Group',
      description: 'Group multiple inputs together',
      blueprint: TemplateInputsNode,
    }],
  },
  {
    title: 'Mathematics',
    description: 'Tools to preform basic arithmetic calculations',
    color: 'pink',
    icon: {
      f7: 'function',
    },
    nodes: [{
      title: 'Math',
      description: 'Addition, subtraction, multiplication, and division',
      blueprint: MathNode,
    }, {
      title: 'Condition',
      description: 'Equals, not equals, greater than, less than',
      blueprint: ConditionalNode,
    }, {
      title: 'Logarithm',
      description: 'Logarithms',
      blueprint: LogNode,
    }, {
      title: 'Power',
      description: 'Powers and roots',
      blueprint: PowerNode,
    }, {
      title: 'Trigonometry',
      description: 'Sine, cosine, and tangent',
      blueprint: TrigNode,
    }],
  },
  {
    title: 'Text',
    description: 'Tools to work with text',
    color: 'green',
    icon: {
      f7: 'skew',
    },
    nodes: [{
      title: 'Join',
      description: 'Join text together',
      blueprint: JoinNode,
    }],
  }];

  $: calculation.editor = editor;
  $: console.log($value);
</script>

<NodesModal
  {nodes}
  {menuNodes}
  inputs={nodeInputs}
  outputs={nodeOutputs}
  bind:state={calculation.calculation}
  bind:open={modalOpen}
  on:close={() => $TemplateNodesModalOpen = false}
  bind:editor={editor}
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
  <Input style="width: 100%;margin-top: 30px">
    <ListItem class="aurora" slot="content">
      <span>Show on Complete</span>
      <Toggle
        color="deeppurple"
        bind:checked={calculation.showOnComplete}
      />
    </ListItem>
  </Input>
  <div class="display-flex" style="margin-top: 35px">
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
          calculations = [
            ...calculations.slice(0, index),
            {
              label: calculation.label,
              type: calculation.type,
              showOnComplete: false,
              calculation: calculation.calculation,
            },
            ...calculations.slice(index),
          ];
        }}
      />
      <MenuButton
        title={$_('generics.delete')}
        icon={{
          f7: 'minus_circle_fill',
          color: 'red',
        }}
        on:click={() => (calculations = calculations.filter((_, i) => i !== index))}
      />
    </div>
  </div>
</div>
