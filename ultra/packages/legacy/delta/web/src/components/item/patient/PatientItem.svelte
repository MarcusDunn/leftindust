<script lang="ts">
  import type { BasicPatientFragment } from '../../../requests/fragments';

  import { ListItem } from 'framework7-svelte';

  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';

  import PatientTags from '../../tags/PatientTags.svelte';

  export let patient: BasicPatientFragment;
  export let link = true;

</script>

<ListItem
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  {link}
  on:click
>
  <BoxedUI slot="media" color="gray" fill round>
    {`${patient.firstName.charAt(0)}${patient.lastName.charAt(0)}`}
  </BoxedUI>
  <PatientTags slot="text" {...patient} small outline />
</ListItem>
