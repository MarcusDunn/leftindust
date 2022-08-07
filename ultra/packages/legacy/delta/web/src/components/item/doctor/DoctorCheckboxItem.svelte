<script lang="ts">
  import type { BasicDoctorFragment } from '../../../requests/fragments';
  
  import type { Selectable } from '../../../modules/SelectModule';
  
  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  import DoctorTags from '../../../components/tags/DoctorTags.svelte';

  export let doctor: BasicDoctorFragment;
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: doctor.__typename,
    id: doctor.id?.value ?? '',
  };

  let value = JSON.stringify(selectable);

</script>

<CheckboxItemUI
  title={`${doctor.firstName} ${doctor?.middleName ? `${doctor?.middleName?.charAt(0)}.` : ''} ${doctor.lastName}`}
  {value}
  {multiple}
  bind:selected={selected}
>
  <DoctorTags slot="text" {...doctor} small outline />
</CheckboxItemUI>
