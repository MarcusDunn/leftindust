<script lang="ts">
  import { fade } from 'svelte/transition';
  import './Dialog.scss';
  import DialogContent from './DialogContent.svelte';
  import { dialogs, dialogBackdropOpen } from './store';

  export let fill = false;

  $: $dialogBackdropOpen = $dialogs.length > 0;
</script>

<div class={`ui-dialog${$dialogBackdropOpen ? ' ui-dialog-open' : ''}${fill ? ' ui-dialog-fill' : ''}`}>
  {#each $dialogs as dialog, index}
    <div out:fade={{ duration: 200 }} class="ui-dialog-content">
      {#if !dialog.component}
        <DialogContent {dialog} callback={() => $dialogs = $dialogs.filter((_, i) => index !== i)} />
      {:else}
        <svelte:component this={dialog.component} open={dialogBackdropOpen} />
      {/if}
    </div>
  {/each}
</div>