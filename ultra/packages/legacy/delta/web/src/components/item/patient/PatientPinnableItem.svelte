<script lang="ts">
  import type { BasicPatientFragment } from '../../../requests/fragments';

  import { createEventDispatcher } from 'svelte';

  import PinnableItemUI from '../../../ui/item/PinnableItemUI/PinnableItemUI.svelte';
   

  import PatientTags from '../../tags/PatientTags.svelte';

  const dispatch = createEventDispatcher();

  export let patient: BasicPatientFragment;
  export let pinned: boolean;

</script>

<PinnableItemUI
  title={`${patient.firstName} ${patient?.middleName ? `${patient?.middleName?.charAt(0)}.` : ''} ${patient.lastName}`}
  {pinned}
  on:pin={({ detail }) => dispatch('pin', detail)}
  on:click
>
  <PatientTags slot="text" {...patient} small outline />
</PinnableItemUI>
