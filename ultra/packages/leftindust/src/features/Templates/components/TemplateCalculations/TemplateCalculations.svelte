<script lang="ts">
  import type { TemplateInput } from '../..';
  import AppLauncherApp from '@/features/Apps/components/AppLauncher/AppLauncherApp.svelte';
  import FlowCover from '@/apps/flow/assets/flow.png';
  import { TemplateDefaultComputation, TemplateInputItems, TemplateNodesModalOpen, TemplateComputations } from '../../store';
  import type { NodeState } from 'function-junctions/types';

  import { BlockFooter, Button } from 'framework7-svelte';

  import { _ } from '@/language';
  import TemplateCalculationInput from './TemplateCalculationInput.svelte';

  let inputs: (TemplateInput & {
    sectionIndex: number;
    index: number;
  })[];

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
    let state: Record<string, NodeState> = {};

    inputs.forEach((input, index) => {
      state = {
        ...state,
        [input.id]: {
          type: 'template-input',
          x: index * 200,
          y: 0,
          store: {
            sectionIndex: input.sectionIndex,
            index: input.index,
            id: input.id,
          },
          outputs: {
            Value: {
              type: input.type,
            },
          },
        },
      };
    });

    $TemplateDefaultComputation = state;
  }

  $: $TemplateComputations = $TemplateComputations.map(({ title, computation }) => ({
    title,
    computation: {
      ...computation,
      nodes: {
        ...$TemplateDefaultComputation,
        ...computation.nodes,
      },
    },
  }));

  $: console.log($TemplateDefaultComputation);
</script>

<br />
<br />
<br />
<br />

{#if $TemplateComputations.length > 0}
  {#each $TemplateComputations as computation, index}
    <TemplateCalculationInput
      {index}
      {inputs}
      bind:computations={$TemplateComputations}
      bind:computation
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
          $TemplateComputations = [
            ...$TemplateComputations,
            {
              title: '',
              computation: {
                position: {
                  originX: 0,
                  originY: 0,
                  translateX: 0,
                  translateY: 0,
                  scale: 0,
                },
                nodes: $TemplateDefaultComputation,
              },
            },
          ];
        }}
        disabled={Object.keys($TemplateDefaultComputation).length < 1}
      >
        Add Calculation
      </Button>
    </div>
  </div>
</div>