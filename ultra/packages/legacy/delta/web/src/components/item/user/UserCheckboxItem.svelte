<script lang="ts">
  import type { UsersQueryResult } from '../../../requests/queries';
  
  import type { Selectable } from '../../../modules/SelectModule';
  
  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  import UserTags from '../../../components/tags/UserTags.svelte';

  export let user: UsersQueryResult['users'][0];
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: user.__typename,
    id: user.uid ?? '',
  };

  let value = JSON.stringify(selectable);

</script>

<CheckboxItemUI
  title={`${user.names.firstName} ${user?.names.middleName ? `${user?.names.middleName?.charAt(0)}.` : ''} ${user.names.lastName}`}
  {value}
  {multiple}
  bind:selected={selected}
>
  <UserTags slot="text" {...user} small outline />
</CheckboxItemUI>
