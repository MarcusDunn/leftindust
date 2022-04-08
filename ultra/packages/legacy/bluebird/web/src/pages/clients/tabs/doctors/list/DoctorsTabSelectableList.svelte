<script lang="ts">
  import type { DoctorsQueryResult } from '@framework/requests/queries/index';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Selectable } from '@framework/modules/SelectModule';

  import { createEventDispatcher } from 'svelte';

  import language from '@framework/languages/index';

  import { Link } from 'framework7-svelte';


  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';
  import SelectableListUI from '@framework/ui/list/SelectableListUI/SelectableListUI.svelte';
  import DoctorSelectableItem from '@framework/components/item/doctor/DoctorSelectableItem.svelte';

  const dispatch = createEventDispatcher();

  export let doctors: DoctorsQueryResult['doctors'];
  export let selected: Writable<Selectable[]>;

</script>

{#if doctors.length > 0}
  <SelectableListUI>
    {#each doctors as doctor}
      <DoctorSelectableItem {doctors} {doctor} {selected} on:selected={() => dispatch('navigate')} />
    {/each}
  </SelectableListUI>
{:else}
  <CollapsableContentPlaceholderUI>
    {language().clients.doctors.placeholders.notFoundCreate.text}
    <Link>{language().clients.doctors.placeholders.notFoundCreate.link}</Link>
  </CollapsableContentPlaceholderUI>
{/if}
