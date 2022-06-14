<script lang="ts">
  import type { DoctorsFragment } from '@/api/server';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Data } from '@/api/server';

  import { createEventDispatcher } from 'svelte';

  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import DoctorSelectableCell from '@/features/Doctor/components/DoctorCell/DoctorSelectableCell.svelte';

  const dispatch = createEventDispatcher();

  export let doctors: DoctorsFragment[];
  export let selected: Writable<Data[]>;

</script>

<Cells selected={$selected}>
  {#each doctors as doctor}
    <DoctorSelectableCell
      {doctors}
      {doctor}
      {selected}
      on:selected={() => dispatch('navigate')}
    />
  {/each}
</Cells>
