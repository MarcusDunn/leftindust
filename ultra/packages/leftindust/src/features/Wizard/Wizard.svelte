<script lang="ts">  
  import type { Color } from '../UI';

  import { createEventDispatcher } from 'svelte';
  import { _ } from 'svelte-i18n';

  import MenuButton from '../UI/components/MenuButton/MenuButton.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import { closeWizard } from '.';
  
  const dispatch = createEventDispatcher();

  export let title: string | undefined = undefined;
  export let subtitle: string | undefined = undefined;
  export let color: Color = 'purple';
  export let submit = true;

  export let disabled = true;
  export let overflow = true;
</script>

<Page pageContent={false} class={`wizard ${$$props.class}`} style={`overflow: ${overflow ? 'scroll' : 'hidden'}; height: 100%`}>
  <Appbar slot="fixed">
    <svelte:fragment slot="left">
      <MenuButton
        title={$_('generics.cancel')}
        icon={{ f7: 'xmark_circle_fill' }}
        on:click={() => {
          dispatch('close');
          closeWizard();
        }}
      />
      {#if $$slots.title}
        <slot name="title" />
      {/if}
      {#if title && !$$slots.title}
        <div class="title" style="margin-left: 15px;text-align:left">
          {title}
          <h6>{subtitle}</h6>
        </div>
      {/if}
    </svelte:fragment>
    <slot name="appbar" />
    <svelte:fragment slot="right">
      {#if submit}
        <MenuButton
          title={$_('generics.done')}
          icon={{ f7: 'checkmark_circle_fill', color }}
          on:click={() => dispatch('submit')}
          {disabled}
        />
      {/if}
    </svelte:fragment>
  </Appbar>
  <slot />
</Page>
