<script lang="ts">
    import type { PatientsFragment } from '@/api/server';
    import { createEventDispatcher } from 'svelte';
    
    import Cell from '@/features/UI/components/Cell/Cell.svelte';
    import PatientTags from '../PatientTags/PatientTags.svelte';
    import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';
    import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  
    const dispatch = createEventDispatcher();
  
    export let patient: Partial<PatientsFragment>;
    export let link = true;
    export let pinned = false;
  </script>
  
  <Cell
    title={`${patient.firstName} ${patient.lastName}`}
    {link}
    on:click
  >
    <PinButton slot="root-start" {pinned} on:pin={({ detail }) => dispatch('pin', detail)} />
    <Boxed slot="media" color="primary" fill round>
      {`${patient.firstName?.charAt(0)}${patient.lastName?.charAt(0)}`}
    </Boxed>
    <svelte:fragment slot="text">
      <PatientTags {...patient} small outline />
    </svelte:fragment>
  </Cell>
  