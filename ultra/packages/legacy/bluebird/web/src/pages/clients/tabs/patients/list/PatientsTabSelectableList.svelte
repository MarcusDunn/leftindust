<script lang="ts">
  import type { PatientsQueryResult } from '@framework/requests/queries/index';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Selectable } from '@framework/modules/SelectModule';

  import { createEventDispatcher } from 'svelte';

  import language from '@framework/languages/index';

  import { Link } from 'framework7-svelte';


  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';
  import SelectableListUI from '@framework/ui/list/SelectableListUI/SelectableListUI.svelte';
  import PatientSelectableInput
    from '@framework/components/item/patient/PatientSelectableItem.svelte';

  const dispatch = createEventDispatcher();

  export let patients: PatientsQueryResult['patients'];
  export let selected: Writable<Selectable[]>;

</script>

{#if patients.length > 0}
  <SelectableListUI>
    {#each patients as patient}
      <PatientSelectableInput {patients} {patient} {selected} on:selected={() => dispatch('navigate')} />
    {/each}
  </SelectableListUI>
{:else}
  <CollapsableContentPlaceholderUI>
    {language().clients.patients.placeholders.notFoundCreate.text}
    <Link>{language().clients.patients.placeholders.notFoundCreate.link}</Link>
  </CollapsableContentPlaceholderUI>
{/if}
