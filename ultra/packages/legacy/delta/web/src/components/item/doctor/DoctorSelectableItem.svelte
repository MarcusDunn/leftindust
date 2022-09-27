<script lang="ts">
  import type { DoctorsQueryResult } from '../../../requests/queries/index';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Selectable } from '../../../modules/SelectModule';

  import { createEventDispatcher } from 'svelte';

  import { click } from '../../../modules/SelectModule';

  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';
  import SelectableItemUI from '../../../ui/item/SelectableItemUI/SelectableItemUI.svelte';

  import DoctorTags from '../../tags/DoctorTags.svelte';

  const dispatch = createEventDispatcher();

  export let doctors: DoctorsQueryResult['doctors'];
  export let doctor: DoctorsQueryResult['doctors'][0];
  export let selected: Writable<Selectable[]>;

  export let multiselect = true;

</script>

<SelectableItemUI
  title={`${doctor.firstName} ${doctor.lastName}`}
  selected={$selected.some((selectable) => selectable.id === doctor.id?.value)}
  on:click={(event) => {
    let selectable = { id: doctor.id?.value, type: doctor.__typename };
    let reference = doctors.map((item) => ({ id: item.id?.value, type: item.__typename }));

    $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });
    dispatch('selected');
  }}
>
  <BoxedUI slot="media" color="primary" fill round>
    {`${doctor.firstName.charAt(0)}${doctor.lastName.charAt(0)}`}
  </BoxedUI>
  <DoctorTags slot="text" {...doctor} small outline />
</SelectableItemUI>
