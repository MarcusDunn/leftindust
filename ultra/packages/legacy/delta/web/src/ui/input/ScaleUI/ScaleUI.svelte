<script lang="ts">
  import type { MultipleChoiceInput } from '../../../modules/InputsModule';

  import { Block } from 'framework7-svelte';

  export let name: string;
  export let inputs: MultipleChoiceInput[];

  export let type: 'radio' | 'checkbox' = 'radio';

  export let labels = false;

  export let value: string | undefined;

</script>

<div class="ultra-scale">
  {#if labels}
    <p>
      {inputs[0].value}
    </p>
  {/if}
  <Block id={name} class="ultra-scale-container" inset strong>
    <form name={name}>
      {#each inputs as input, index}
        <input
          id={index.toString()}
          type={type}
          value={input.value}
          checked={value === input.value}
          on:input={() => {
            value = input.value;
          }}
        />
        <label for={index.toString()}>{input?.abbreviation || index + 1}</label>
      {/each}
    </form>
  </Block>
  {#if labels}
    <p>
      {inputs[inputs.length - 1].value}
    </p>
  {/if}
</div>
