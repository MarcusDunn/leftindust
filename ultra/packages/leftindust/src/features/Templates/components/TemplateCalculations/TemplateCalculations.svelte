<script lang="ts">
  import AppLauncherApp from '@/features/Apps/components/AppLauncher/AppLauncherApp.svelte';
  import FlowCover from '@/apps/flow/assets/flow.png';
  import { TemplateDefaultNodes, TemplateInputItems, TemplateNodesModalOpen } from '../../store';
  import type { NodeState } from 'function-junctions/types';

  import { BlockFooter, Button } from 'framework7-svelte';
  import NodesModal from '@/features/Nodes/components/NodesModal/NodesModal.svelte';

  import { _ } from '@/language';

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

    $TemplateDefaultNodes = state;
  }

  $: console.log($TemplateDefaultNodes);
</script>

<br />
<br />
<br />
<br />
<br />

<div class="display-flex" style="align-items: center; justify-content: center">
  <AppLauncherApp
    cover={FlowCover}
    small
  />
  <br />
  <div style="padding-left: 20px">
    <BlockFooter class="no-margin no-padding">Get the most out of your results by adding a calculated score</BlockFooter>
    <p />
    <NodesModal bind:open={$TemplateNodesModalOpen} />
    <div>
      <Button
        outline
        round
        color="deeppurple"
        style="width: 100%;margin-right: 0"
        on:click={() => ($TemplateNodesModalOpen = true)}
        disabled={Object.keys($TemplateDefaultNodes).length < 1}
      >
        Add Calculation
      </Button>
    </div>
  </div>
</div>