<script lang="ts">
  import type { DoctorsFragment } from '@/api/server';
  import { createEventDispatcher } from 'svelte';
  
  import Cell from '@/features/UI/components/Cell/Cell.svelte';
  import DoctorTags from '../DoctorTags/DoctorTags.svelte';
  import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';

  const dispatch = createEventDispatcher();

  export let doctor: Partial<DoctorsFragment>;
  export let link = true;
  export let pinned = false;
</script>

<Cell
  title={`${doctor.firstName} ${doctor.lastName}`}
  {link}
  on:click
>
  <PinButton slot="root-start" {pinned} on:pin={({ detail }) => dispatch('pin', detail)} />
  <Boxed slot="media" color="primary" fill round>
    {`${doctor.firstName?.charAt(0)}${doctor.lastName?.charAt(0)}`}
  </Boxed>
  <svelte:fragment slot="text">
    <DoctorTags {...doctor} small outline />
  </svelte:fragment>
</Cell>