<script lang="ts">
  import Checkbox from '@/features/Input/components/Checkbox/Checkbox.svelte';

  import type { Data, PartialPatientFragment } from '@/api/server';
  import PatientTags from '../PatientTags/PatientTags.svelte';

  export let patient: PartialPatientFragment;
  export let selected: string[];

  export let multiple = false;

  const data: Data = {
    type: patient.__typename,
    id: patient.id.value ?? '',
  };

  let value = JSON.stringify(data);
</script>

<Checkbox
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  {value}
  {multiple}
  bind:selected
>
  <PatientTags slot="text" {...patient} small outline />
</Checkbox>