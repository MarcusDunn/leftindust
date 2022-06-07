<script lang="ts">
  import type { PatientsFragment } from '@/api/server';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Data } from '@/api/server';

  import { createEventDispatcher } from 'svelte';

  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import PatientSelectableCell  
    from '@/features/Patient/components/PatientCell/PatientSelectableCell.svelte';

  const dispatch = createEventDispatcher();

  export let patients: PatientsFragment[];
  export let selected: Writable<Data[]>;
</script>

<Cells selected={$selected}>
  {#each patients as patient}
    <PatientSelectableCell
      {patients}
      {patient}
      {selected}
      on:selected={() => dispatch('navigate')}
    />
  {/each}
</Cells>
