<script lang="ts">
  import { type TemplateInput, TemplateInputType, getTemplateSocketType, type TemplateCalculationWithInstance, templateForm } from '../..';
  import AppLauncherApp from '@/features/Apps/components/AppLauncher/AppLauncherApp.svelte';
  import FlowCover from '@/apps/flow/assets/flow.png';
  import { TemplateInputItems } from '../../store';
  import { writable, type Writable } from 'svelte/store';

  import { BlockFooter, Button } from 'framework7-svelte';

  import { _ } from '@/language';
  import TemplateCalculationInput from './TemplateCalculationInput.svelte';

  export let calculations: TemplateCalculationWithInstance[];

  const error = templateForm().errors;
  export let errors: typeof error;

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
</script>

<br />
<br />
<br />
<br />

{#if calculations.length > 0}
  {#each calculations as calculation, index}
    <TemplateCalculationInput
      {index}
      {nodeInputs}
      {errors}
      bind:calculations={calculations}
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
          calculations = [
            ...calculations,
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
                  '0': {
                    type: 'output',
                    x: window.innerWidth - 360,
                    y: 100,
                    store: {
                      index: calculations.length,
                    },
                  },
                },
              },
            },
          ];
        }}
      >
        Add Calculation
      </Button>
    </div>
  </div>
</div>