<script lang="ts">
  import type { Doctor } from '../../../schemas/leftindust.schema';

  import { ListItem } from 'framework7-svelte';

  import DoctorTags from '../../tags/DoctorTags.svelte';
  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';

  export let doctor: Partial<Doctor>;
  export let link = true;
</script>

<ListItem
  title={`${doctor.firstName} ${doctor.lastName}`}
  {link}
  on:click
>
  <BoxedUI slot="media" color="primary" fill round>
    {`${doctor.firstName?.charAt(0)}${doctor.lastName?.charAt(0)}`}
  </BoxedUI>
  <svelte:fragment slot="text">
    <DoctorTags title={doctor.title} small outline />
  </svelte:fragment>
</ListItem>
