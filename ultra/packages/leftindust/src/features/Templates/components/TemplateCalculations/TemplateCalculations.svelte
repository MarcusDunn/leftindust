<script lang="ts">
  import { type TemplateInput, TemplateInputType, getTemplateSocketType } from '../..';
  import AppLauncherApp from '@/features/Apps/components/AppLauncher/AppLauncherApp.svelte';
  import FlowCover from '@/apps/flow/assets/flow.png';
  import { TemplateDefaultCalculation, TemplateInputItems, TemplateCalculations } from '../../store';
  import type { NodeState } from 'function-junctions/types';
  import { writable, type Writable } from 'svelte/store';

  import { BlockFooter, Button } from 'framework7-svelte';

  import { _ } from '@/language';
  import TemplateCalculationInput from './TemplateCalculationInput.svelte';

  let inputs: (TemplateInput & {
    sectionIndex: number;
    index: number;
  })[];

  let nodeInputs: Record<string, { type: string; value: Writable<unknown> }> = {};

  $: inputs = $TemplateInputItems.sections.flatMap((section, index) =>
    section.inputs.map((input, inputIndex) => ({
      ...input,
      sectionIndex: index,
      index: inputIndex,
      label: `${input.label}${
        $TemplateInputItems.sections.length > 1
          // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
          ? ` (${$_('generics.sectionIndexed', { values: { number: index + 1 } })})`
          : ''
      }`,
    })),
  );

  $: {
    nodeInputs = {};

    inputs.forEach((input) => {
      const type = getTemplateSocketType(input.type);
      
      nodeInputs = {
        ...nodeInputs,
        [input.id]: {
          type,
          value: nodeInputs[input.id]?.value ?? writable(),
        },
      };
    });

    inputs.forEach((input) => {
      nodeInputs[input.id].value.update(() => input.value);
    });
  }

  // Updates default state
  $: {
    let state: Record<string, NodeState> = {};
    
    inputs.forEach((input, index) => {
      state = {
        ...state,
        [`i_${input.id}`]: {
          type: 'input',
          x: 75,
          y: (index * 230) + 100,
          store: {
            sectionIndex: input.sectionIndex,
            index: input.index,
            id: input.id,
          },
          outputs: {
            Value: {
              type: input.type,
              value: '',
            },
          },
        },
      };
    });

    $TemplateDefaultCalculation = state;
  }
</script>

<br />
<br />
<br />
<br />

{#if $TemplateCalculations.length > 0}
  {#each $TemplateCalculations as calculation, index}
    <TemplateCalculationInput
      {index}
      {nodeInputs}
      bind:calculations={$TemplateCalculations}
      bind:calculation
    />
    <br />
  {/each}
  <br />
  <br />
  <br />
{/if}
<div class="display-flex" style="align-items: center; justify-content: center">
  <AppLauncherApp
    cover={FlowCover}
    small
  />
  <br />
  <div style="padding-left: 20px">
    <BlockFooter class="no-margin no-padding">Get the most out of your results by adding a calculated score</BlockFooter>
    <p />
    <div>
      <Button
        outline
        round
        color="deeppurple"
        style="width: 100%;margin-right: 0"
        on:click={() => {
          $TemplateCalculations = [
            ...$TemplateCalculations,
            {
              label: '',
              type: TemplateInputType.Text,
              showOnComplete: false,
              calculation: {
                position: {
                  originX: 0,
                  originY: 0,
                  translateX: 0,
                  translateY: 0,
                  scale: 1,
                },
                nodes: {
                  ...$TemplateDefaultCalculation,
                  [`o_${0}`]: {
                    type: 'output',
                    x: window.innerWidth - 360,
                    y: 100,
                    store: {
                      index: $TemplateCalculations.length,
                    },
                  },
                },
              },
            },
          ];
        }}
        disabled={Object.keys($TemplateDefaultCalculation).length < 1}
      >
        Add Calculation
      </Button>
    </div>
  </div>
</div>