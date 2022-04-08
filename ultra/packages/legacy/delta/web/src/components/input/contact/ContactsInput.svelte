<script lang="ts">
  import type { EmergencyContactInput } from '../../../schemas/leftindust.schema';
  
  import AddUI from '../../../ui/input/AddUI/AddUI.svelte';
  import DeleteButtonUI from '../../../ui/button/DeleteButtonUI/DeleteButtonUI.svelte';
  import ContactInput from './ContactInput.svelte';

  export let value: Partial<EmergencyContactInput>[] = [];
  export let required = false;
</script>

{#each value as _, index}
  <div class="display-flex">
    <ContactInput bind:value={value[index]} />
    {#if value.length > 1 || !required}
      <div style="margin-top: 28px">
        <DeleteButtonUI on:delete={() => {
          value = value.filter((_, i) => i !== index);
        }} />
      </div>
    {/if}
  </div>
  <p />
{/each}

<AddUI
  title="Add Contact"
  on:click={() => {
    value = [...value, {
      firstName: '',
      middleName: '',
      lastName: '',
      relationship: undefined,
      emails: [],
      phones: [],
    }];
  }}
/>