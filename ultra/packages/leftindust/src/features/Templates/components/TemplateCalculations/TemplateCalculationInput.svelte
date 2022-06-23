<script lang="ts">
  import type { TemplateComputation } from '../..';
  import Input from '@/features/Input/Input.svelte';
  import { _ } from 'svelte-i18n';
  import { Button } from 'framework7-svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';

  export let index: number;
  export let computations: TemplateComputation[];
  export let computation: TemplateComputation;

  export let selectedIndex: number;

  export let modalOpen = false;
</script>

<div>
  <Input style="width: 100%">
    <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
    <input
      type="text"
      bind:value={computation.title}
      placeholder={$_('examples.computation')}
    />
  </Input>
  <div class="display-flex" style="margin-top: 20px">
    <div style="width: 100%;margin-top: 11px">
      <Button
        color="deeppurple"
        round
        outline
        on:click={() => {
          selectedIndex = index;
          modalOpen = true;
        }}
      >
        Edit Algorithm
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
              title: computation.title,
              computation: computation.computation,
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
        on:click={() => {
          computations = computations.filter((_, i) => i !== index);
          selectedIndex = index - 1;
        }}
      />
    </div>
  </div>
</div>
