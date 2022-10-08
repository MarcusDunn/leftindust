<script lang="ts">
  import type { PatientsQueryResult } from '../../../requests/queries/index';
  import type { BasicPatientFragment } from '../../../requests/fragments';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Selectable } from '../../../modules/SelectModule';

  import { createEventDispatcher } from 'svelte';

  import { click } from '../../../modules/SelectModule';

  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';
  import SelectableItemUI   from '../../../ui/item/SelectableItemUI/SelectableItemUI.svelte';

  import PatientTags from '../../tags/PatientTags.svelte';

  const dispatch = createEventDispatcher();

  export let patients: PatientsQueryResult['patients'];
  export let patient: BasicPatientFragment;
  export let selected: Writable<Selectable[]>;

  export let multiselect = true;

</script>

<SelectableItemUI
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  selected={$selected.some((selectable) => selectable.id === patient.id.value)}
  on:click={(event) => {
    let selectable = { id: patient.id.value, type: patient.__typename };
    let reference = patients.map((item) => ({ id: item.pid.id, type: item.__typename }));

    $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });
    dispatch('selected');
  }}
>
  <BoxedUI slot="media" color="gray" fill round>
    {`${patient.firstName.charAt(0)}${patient.lastName.charAt(0)}`}
  </BoxedUI>
  <PatientTags slot="text" {...patient} small outline />
</SelectableItemUI>
