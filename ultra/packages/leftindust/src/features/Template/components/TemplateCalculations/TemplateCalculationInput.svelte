<script lang="ts">
  import { type templateForm, type TemplateCalculationWithInstance, templateCalculationNodes, getInputType } from '../..';
  import Input from '@/features/Input/Input.svelte';
  import { writable, type Writable } from 'svelte/store';
  import { _ } from 'svelte-i18n';
  import { Button, ListItem, Toggle } from 'framework7-svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';
  import { TemplateNodesModalOpen } from '../../store';
  import type { Editor } from 'function-junctions/types';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import TemplateInputNode from '@/features/Node/components/TemplateInputNode';
  import MathNode from '@/features/Node/components/MathNode';
  import TemplateInputsNode from '@/features/Node/components/TemplateInputsNode';
  import AverageNode from '@/features/Node/components/AverageNode';
  import NumberNode from '@/features/Node/components/NumberNode';
  import TextNode from '@/features/Node/components/TextNode';
  import DateNode from '@/features/Node/components/DateNode';
  import { SurveyTemplateInputType } from '@/api/server';
  import type { MenuNodes } from '@/features/Nodes';
  import { TemplateCalculations } from '../../store';
  import deepmerge from 'deepmerge';

  export let index: number;
  export let calculations: TemplateCalculationWithInstance[];
  export let calculation: TemplateCalculationWithInstance;

  export let modalOpen = false;

  export let nodeInputs: Record<string, { type: string; value: Writable<unknown> }>;

  export let errors: ReturnType<typeof templateForm>['errors'];
  
  let nodeOutputs = {
    Value: {
      type: '',
      value: writable(),
    },
  };

  const { value } = nodeOutputs.Value;

  let editor: Editor;

  const menuNodes: MenuNodes = [
    {
      title: 'I/O',
      description: 'Tools to handle inputs and outputs',
      color: 'deeppurple',
      icon: {
        f7: 'skew',
      },
      nodes: [
        {
          title: 'Input',
          description: 'Connect the inputs from your form',
          blueprint: TemplateInputNode,
        },
        {
          title: 'Group',
          description: 'Group multiple inputs together',
          blueprint: TemplateInputsNode,
        },
      ],
    },
    {
      title: 'Numbers',
      description: 'Tools to preform basic arithmetic calculations',
      color: 'blue',
      icon: {
        f7: 'sum',
      },
      nodes: [
        {
          title: 'Number',
          description: 'Basic number input',
          blueprint: NumberNode,
        },
        {
          title: 'Math',
          description: 'Addition, subtraction, multiplication, and division',
          blueprint: MathNode,
        },
        {
          title: 'Average',
          description: 'Calculate the average of a set of numbers',
          blueprint: AverageNode,
        },
      ],
    },
    {
      title: 'Text',
      description: 'Tools to preform text operations',
      color: 'orange',
      icon: {
        f7: 'textformat',
      },
      nodes: [
        {
          title: 'Text',
          description: 'Basic text input',
          blueprint: TextNode,
        },
      ],
    },
    {
      title: 'Date',
      description: 'Tools to preform operations with dates and times',
      color: 'teal',
      icon: {
        f7: 'clock_fill',
      },
      nodes: [
        {
          title: 'Date',
          description: 'Basic date input',
          blueprint: DateNode,
        },
      ],
    },
  ];

  $: calculation.editor = editor;
  $: console.log($value);

  $: if ($TemplateCalculations?.[index] && JSON.stringify($TemplateCalculations[index].deserializedCalculation) !== JSON.stringify(calculation.deserializedCalculation))
    $TemplateCalculations[index].deserializedCalculation = calculation.deserializedCalculation;

</script>

<NodesModal
  nodes={templateCalculationNodes}
  {menuNodes}
  inputs={nodeInputs}
  outputs={nodeOutputs}
  bind:state={calculation.deserializedCalculation}
  bind:open={modalOpen}
  on:close={() => $TemplateNodesModalOpen = false}
  bind:editor={calculation.editor}
/>

<div>
  <Select
    title={$_('generics.type')}
    placeholder={calculation.inputType ? getInputType(calculation.inputType) : ''}
    options={[
      {
        text: $_('generics.text'),
        value: SurveyTemplateInputType.Text,
      },
      {
        text: $_('generics.number'),
        value: SurveyTemplateInputType.Number,
      },
      {
        text: $_('generics.date'),
        value: SurveyTemplateInputType.Date,
      },
      {
        text: $_('generics.paragraph'),
        value: SurveyTemplateInputType.Paragraph,
      },
      {
        text: $_('generics.singleSelect'),
        value: SurveyTemplateInputType.SingleSelect,
      },
      {
        text: $_('generics.multiSelect'),
        value: SurveyTemplateInputType.MultiSelect,
      },
      {
        text: $_('generics.upload'),
        value: SurveyTemplateInputType.Upload,
      },
      {
        text: $_('generics.title'),
        value: SurveyTemplateInputType.Title,
      },
    ]}
    bind:value={calculation.inputType}
    name={`calculations.${index}.type`}
  />
  <p />
  <Input
    style="width: 100%"
    error={// https://github.com/pablo-abc/felte/issues/162
      // @ts-expect-error
      $errors?.calculations?.[index]?.label}
  >
    <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
    <input
      type="text"
      bind:value={calculation.label}
      placeholder={$_('examples.calculation')}
      name={`calculations.${index}.label`}
    />
  </Input>
  <Input style="width: 100%;margin-top: 30px">
    <ListItem class="aurora" slot="content">
      <span>Show on Complete</span>
      <Toggle
        color="deeppurple"
        name={`calculations.${index}.showOnComplete`}
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
          /*
          // TODO: Add a way to clone calculations while maintaining the index
          calculations = [
            ...calculations.slice(0, index),
            deepmerge({
              label: calculation.label,
              inputType: calculation.inputType,
              showOnComplete: false,
              deserializedCalculation: calculation.deserializedCalculation,
            }, {}),
            ...calculations.slice(index),
          ];
          */
          if (calculation.deserializedCalculation.nodes?.['0']) {
            calculations = [
              ...calculations,
              deepmerge({
                label: calculation.label,
                inputType: calculation.inputType,
                showOnComplete: false,
                deserializedCalculation: {
                  ...calculation.deserializedCalculation,
                  nodes: {
                    ...calculation.deserializedCalculation.nodes,
                    '0': {
                      ...calculation.deserializedCalculation.nodes['0'],
                      store: {
                        index: calculations.length,
                      },
                    },
                  },
                },
              }, {}),
            ];
          }
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
