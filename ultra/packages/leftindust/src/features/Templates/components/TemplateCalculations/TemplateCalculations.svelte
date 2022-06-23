<script lang="ts">
  import AppLauncherApp from '@/features/Apps/components/AppLauncher/AppLauncherApp.svelte';
  import FlowCover from '@/apps/flow/assets/flow.png';
  import { TemplateDefaultComputation, TemplateInputItems, TemplateNodesModalOpen, TemplateComputations } from '../../store';
  import type { NodeState } from 'function-junctions/types';

  import { BlockFooter, Button } from 'framework7-svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';

  import { _ } from '@/language';
  import TemplateCalculationInput from './TemplateCalculationInput.svelte';

  let selectedIndex = 0;

  $: inputs = $TemplateInputItems.sections.flatMap((section, index) =>
    section.inputs.map((input) => ({
      ...input,
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
          type: 'input',
          x: index * 200,
          y: 0,
          store: {
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
    title: title,
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
  <NodesModal
    bind:state={$TemplateComputations[selectedIndex].computation}
    bind:open={$TemplateNodesModalOpen}
  />
{/if}

{#if $TemplateComputations.length > 0}
  {#each $TemplateComputations as computation, index}
    <TemplateCalculationInput
      {index}
      {selectedIndex}
      bind:computations={$TemplateComputations}
      bind:computation
      bind:modalOpen={$TemplateNodesModalOpen}
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

          selectedIndex = $TemplateComputations.length - 1;
        }}
        disabled={Object.keys($TemplateDefaultComputation).length < 1}
      >
        Add Calculation
      </Button>
    </div>
  </div>
</div>