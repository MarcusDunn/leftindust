<script lang="ts">  
  import type { Color } from '../UI';

  import { createEventDispatcher } from 'svelte';
  import { _ } from 'svelte-i18n';

  import MenuButton from '../UI/components/MenuButton/MenuButton.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import { closeWizard } from '.';
  import { openDialog } from '../UI/components/Dialog';
   
  const dispatch = createEventDispatcher();

  export let title: string | undefined = undefined;
  export let subtitle: string | undefined = undefined;
  export let color: Color = 'purple';
  export let submit = true;

  export let interacted = false;

  export let disabled = false;
  export let overflow = true;

  const close = () => {
    closeWizard();
    dispatch('close');
  };

  const closeHandler = () => {
    if (interacted) {
      openDialog({
        title:  'Are you sure you want to close?',
        text: 'You have edited this form. If you close it, your changes will be lost.',
        buttons: [
          {
            label: $_('generics.cancel'),
            primary: true,
          },
          {
            label: 'Don\'t Save',
            onClick: close,
          },
        ],
      });
    } else {
      close();
    }
  };
</script>

<Page pageContent={false} class={`wizard ${$$props.class}`} style={`overflow: ${overflow ? 'scroll' : 'hidden'}; height: 100%`}>
  <Appbar slot="fixed">
    <svelte:fragment slot="left">
      <MenuButton
        title={$_('generics.cancel')}
        icon={{ f7: 'xmark_circle_fill' }}
        on:click={closeHandler}
        {disabled}
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
