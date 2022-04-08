<script lang="ts">
  import type { Doctor } from '@/api/server/schema/leftindust.schema';

  import Cell from '@/features/UI/components/Cell/Cell.svelte';

  import DoctorTags from '../DoctorTags/DoctorTags.svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';

  export let doctor: Partial<Doctor>;
  export let link = true;
</script>

<Cell
  title={`${doctor.firstName} ${doctor.lastName}`}
  {link}
  on:click
>
  <Boxed slot="media" color="primary" fill round>
    {`${doctor.firstName?.charAt(0)}${doctor.lastName?.charAt(0)}`}
  </Boxed>
  <svelte:fragment slot="text">
    <DoctorTags title={doctor.title} small outline />
  </svelte:fragment>
</Cell>
