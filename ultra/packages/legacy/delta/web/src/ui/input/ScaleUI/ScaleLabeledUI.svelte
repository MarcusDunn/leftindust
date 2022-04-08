<script lang="ts">
  import type { MultipleChoiceInput } from '../../../modules/InputsModule';

  import { Block, Icon } from 'framework7-svelte';

  import MenuButtonUI from '../../button/MenuButtonUI/MenuButtonUI.svelte';

  export let name: string;
  export let inputs: MultipleChoiceInput[] = [];

  export let value: string | undefined;
</script>

<Block id={name} class="ultra-scale-labeled" inset strong>
  {#each inputs as input, index}
    <MenuButtonUI
      title={input?.abbreviation || (index + 1).toString()}
      on:click={() => {
        value = input.value;
      }}
    >
      {#if value === input.value}
        <Icon f7="checkmark_circle_fill" color="blue" />
      {:else}
        <Icon f7="circle" color="gray" />
      {/if}
    </MenuButtonUI>
  {/each}
</Block>