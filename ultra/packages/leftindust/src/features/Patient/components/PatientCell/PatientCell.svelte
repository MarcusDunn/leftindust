<script lang="ts">
  import type { PartialPatientFragment } from '@/api/server';
  
  import Cell from '@/features/UI/components/Cell/Cell.svelte';
  import Boxed from '../../../UI/components/Boxed/Boxed.svelte';

  import PatientTags from '../PatientTags/PatientTags.svelte';

  export let patient: PartialPatientFragment;
  export let link = true;
</script>

<Cell
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  {link}
  on:click
>
  <Boxed slot="media" color="gray" fill round>
    {`${patient.firstName.charAt(0)}${patient.lastName.charAt(0)}`}
  </Boxed>
  <PatientTags slot="text" {...patient} small outline />
</Cell>
