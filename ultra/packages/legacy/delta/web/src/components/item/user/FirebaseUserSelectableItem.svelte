<script lang="ts">
  import type { FirebaseUsersQueryResult } from '../../../requests/queries/index';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Selectable } from '../../../modules/SelectModule';

  import { createEventDispatcher } from 'svelte';

  import { click } from '../../../modules/SelectModule';
  
  import SelectableItemUI from '../../../ui/item/SelectableItemUI/SelectableItemUI.svelte';
  import FirebaseUserTags from '../../../components/tags/FirebaseUserTags.svelte';

  const dispatch = createEventDispatcher();

  export let users: FirebaseUsersQueryResult['firebaseUsers'];
  export let user: FirebaseUsersQueryResult['firebaseUsers'][0];
  export let selected: Writable<Selectable[]>;

  export let multiselect = true;

</script>

<SelectableItemUI
  title={user.email}
  selected={$selected.some((selectable) => selectable.id === user.uid)}
  on:click={(event) => {
    let selectable = { id: user.uid, type: user.__typename };
    let reference = users.map((item) => ({ id: item.uid, type: item.__typename }));

    $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });
    dispatch('selected');
  }}
>
  <FirebaseUserTags slot="text" {...user} small outline />
</SelectableItemUI>
