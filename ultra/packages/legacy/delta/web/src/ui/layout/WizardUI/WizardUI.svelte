<script lang="ts">  
  import { createEventDispatcher } from 'svelte';
  import language from '../../../languages';

  import { closeWizard } from '../../../modules/NavigationModule';
  import MenuButtonUI from '../../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import AppBarUI from '../../controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '../PageUI/PageUI.svelte';

  const dispatch = createEventDispatcher();

  export let title: string | undefined = undefined;
  export let subtitle: string | undefined = undefined;
  export let color = 'purple';
  export let submit = true;

  export let disabled = true;

  export let overflow = true;
</script>

<PageUI pageContent={false} class={`ultra-wizard ${$$props.class}`} style={`overflow: ${overflow ? 'scroll' : 'hidden'}`}>
  <AppBarUI slot="fixed">
    <svelte:fragment slot="left">
      <MenuButtonUI
        title={language().buttons.cancel.text}
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
        <MenuButtonUI
          title={language().buttons.done.text}
          icon={{ f7: 'checkmark_circle_fill', color }}
          on:click={() => dispatch('submit')}
          {disabled}
        />
      {/if}
    </svelte:fragment>
  </AppBarUI>
  <slot />
</PageUI>
