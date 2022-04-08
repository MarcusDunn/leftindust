<script lang="ts">
  
  import type { Selectable } from '../../../modules/SelectModule';
  
  import type { BasicPatientFragment } from '../../../requests/fragments';

  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  import PatientTags from '../../tags/PatientTags.svelte';

  export let patient: BasicPatientFragment;
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: patient.__typename,
    id: patient.pid.id ?? '',
  };

  let value = JSON.stringify(selectable);

</script>

<CheckboxItemUI
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  value={value}
  multiple={multiple}
  bind:selected={selected}
>
  <PatientTags slot="text" {...patient} small outline />
</CheckboxItemUI>
