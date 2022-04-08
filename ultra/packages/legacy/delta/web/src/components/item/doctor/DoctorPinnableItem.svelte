<script lang="ts">
  import type { BasicDoctorFragment } from '../../../requests/fragments';

  import { createEventDispatcher } from 'svelte';

  import PinnableItemUI from '../../../ui/item/PinnableItemUI/PinnableItemUI.svelte';
   
  import DoctorTags from '../../../components/tags/DoctorTags.svelte';

  const dispatch = createEventDispatcher();

  export let doctor: BasicDoctorFragment;
  export let pinned: boolean;

</script>

<PinnableItemUI
  title={`${doctor.firstName} ${doctor?.middleName ? `${doctor?.middleName?.charAt(0)}.` : ''} ${doctor.lastName}`}
  {pinned}
  on:pin={({ detail }) => dispatch('pin', detail)}
  on:click
>
  <DoctorTags slot="text" {...doctor} small outline />
</PinnableItemUI>
