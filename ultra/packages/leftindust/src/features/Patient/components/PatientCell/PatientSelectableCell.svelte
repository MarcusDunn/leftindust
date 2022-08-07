<script lang="ts">
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Data, PartialPatientFragment } from '@/api/server';

  import { createEventDispatcher, tick } from 'svelte';

  import { click } from '@/features/Select';

  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import Cell from '@/features/UI/components/Cell/Cell.svelte';

  import PatientTags from '../PatientTags/PatientTags.svelte';

  const dispatch = createEventDispatcher();
  
  export let patient: PartialPatientFragment;
  export let patients: PartialPatientFragment[];
  export let selected: Writable<Data[]>;

  export let multiselect = true;

</script>

<Cell
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  selected={$selected.some((selectable) => selectable.id === patient.id.value)}
  on:click={(event) => {
    let selectable = { id: patient.id.value, type: patient.__typename };
    let reference = patients.map((item) => ({ id: item.pid.id, type: item.__typename }));

    $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });

    dispatch('selected');
  }}
>
  <Boxed slot="media" color="gray" fill round>
    {`${patient.firstName.charAt(0)}${patient.lastName.charAt(0)}`}
  </Boxed>
  <PatientTags slot="text" {...patient} small outline />
</Cell>
