<script lang="ts">
  // This component was created as compensation for a bug in svelte
  // https://github.com/sveltejs/svelte/issues/6341

  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';
  import DeleteButtonUI from '@framework/ui/button/DeleteButtonUI/DeleteButtonUI.svelte';

  export let index: number;
  export let options: string[];

</script>


<div class="display-flex">
  <div style="width: 100%">
    <InputUI clear>
      <input type="text" placeholder={`Answer ${index + 1}`} bind:value={options[index]} />
    </InputUI>
  </div>
  {#if options.length > 1}
    <DeleteButtonUI on:delete={() => {
      options = options.filter((_, i) => i !== index);
    }} />
  {/if}
</div>