<script lang="ts">
  import { fade } from 'svelte/transition';
  import './Dialog.scss';
  import DialogContent from './DialogContent.svelte';
  import { dialogs, dialogBackdropOpen } from './store';

  export let fill = false;

  $: $dialogBackdropOpen = $dialogs.length > 0;

  $: dialog = $dialogs[$dialogs.length - 1];

  $: console.log(dialog);

  const close = () => $dialogs = $dialogs.filter((_, i) => $dialogs.length - 1 !== i);
</script>

<div class={`ui-dialog${$dialogBackdropOpen ? ' ui-dialog-open' : ''}${fill ? ' ui-dialog-fill' : ''}`}>
  {#if dialog}
    <div out:fade={{ duration: 200 }} class="ui-dialog-content">
      {#if !dialog?.component?.component}
        <DialogContent {dialog} callback={close} />
      {:else}
        <svelte:component
          this={dialog.component.component}
          open={dialogBackdropOpen}
          {close}
          {...dialog.component.params}
        />
      {/if}
    </div>
  {/if}
</div>