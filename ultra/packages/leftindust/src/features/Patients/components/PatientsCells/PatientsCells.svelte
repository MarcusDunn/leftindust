<script lang="ts">
  import type { PartialPatientFragment } from '@/api/server';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Data } from '@/api/server';

  import { createEventDispatcher } from 'svelte';

  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import PatientSelectableCell from '@/features/Patient/components/PatientCell/PatientSelectableCell.svelte';

  const dispatch = createEventDispatcher();

  export let patients: PartialPatientFragment[];
  export let selected: Writable<Data[]>;

  console.log(patients);
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
