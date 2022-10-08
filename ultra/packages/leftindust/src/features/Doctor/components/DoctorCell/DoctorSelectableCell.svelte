<script lang="ts">
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Data, PartialDoctorFragment } from '@/api/server';

  import { createEventDispatcher, tick } from 'svelte';

  import { click } from '@/features/Select';

  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import Cell from '@/features/UI/components/Cell/Cell.svelte';

  import DoctorTags from '../DoctorTags/DoctorTags.svelte';

  const dispatch = createEventDispatcher();
  
  export let doctor: PartialDoctorFragment;
  export let doctors: PartialDoctorFragment[];
  export let selected: Writable<Data[]>;

  export let multiselect = true;

</script>

{#if doctor}
  <Cell
    title={`${doctor.firstName} ${doctor?.middleName ? `${doctor?.middleName?.charAt(0)}.` : ''} ${doctor.lastName}`}
    selected={$selected.some((selectable) => selectable.id === doctor.id?.value)}
    on:click={(event) => {
      let selectable = { id: doctor.id?.value, type: doctor.__typename };
      let reference = doctors.map((item) => ({ id: item.id?.value, type: item.__typename }));

      $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });

      void tick().then(() => {
        dispatch('selected');
      });
    }}
  >
    <Boxed slot="media" color="primary" fill round>
      {`${doctor.firstName.charAt(0)}${doctor.lastName.charAt(0)}`}
    </Boxed>
    <DoctorTags slot="text" {...doctor} small outline />
  </Cell>
{/if}