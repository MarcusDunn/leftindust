<script lang="ts">
  import type { UsersQueryResult } from '../../../requests/queries/index';
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Selectable } from '../../../modules/SelectModule';

  import { createEventDispatcher } from 'svelte';

  import { click } from '../../../modules/SelectModule';
  
  import SelectableItemUI from '../../../ui/item/SelectableItemUI/SelectableItemUI.svelte';
  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';
  
  import UserTags from '../../../components/tags/UserTags.svelte';

  const dispatch = createEventDispatcher();

  export let users: UsersQueryResult['users'];
  export let user: UsersQueryResult['users'][0];
  export let selected: Writable<Selectable[]>;

  export let multiselect = true;

</script>

<SelectableItemUI
  title={`${user.names.firstName} ${user.names.lastName}`}
  selected={$selected.some((selectable) => selectable.id === user.uid)}
  on:click={(event) => {
    let selectable = { id: user.uid, type: user.__typename };
    let reference = users.map((item) => ({ id: item.uid, type: item.__typename }));

    $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });
    dispatch('selected');
  }}
>
  <BoxedUI slot="media" color="blue" fill round>
    {`${user.names.firstName.charAt(0)}${user.names.lastName.charAt(0)}`}
  </BoxedUI>
  <UserTags slot="text" {...user} small outline />
</SelectableItemUI>
