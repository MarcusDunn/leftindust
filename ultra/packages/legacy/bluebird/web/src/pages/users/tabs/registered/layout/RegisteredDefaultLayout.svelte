<script lang="ts">
  import type { Router } from 'framework7/types';
  import { DEFAULT_RANGE_INPUT } from '@framework/requests/index';
  import { UsersSelected } from '../../../store/UsersStore';
  import language from '@framework/languages';

  import UsersGenericEngine from '@framework/engines/users/UsersGenericEngine';
  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import { Link, PageContent } from 'framework7-svelte';

  import MasterListLayout from '@framework/components/layout/MasterListLayout.svelte';
  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import UserSelectableItem from '@framework/components/item/user/UserSelectableItem.svelte';

  import SelectableListUI from '@framework/ui/list/SelectableListUI/SelectableListUI.svelte';

  export let f7router: Router.Router;
  
  const { request, users } = UsersGenericEngine({
    range: DEFAULT_RANGE_INPUT,
  });

  const navigate = () => f7router.navigate(`/user/${JSON.stringify($UsersSelected[0])}/`);
</script>

<PageContent style="padding-top: 10px" infinite infiniteDistance={50} infinitePreloader={false} onInfinite={undefined}>
  <MasterListLayout>
    <RequestLayout {...$request} refetch={request.refetch}>
      {#if $users.length > 0}
        <SelectableListUI>
          {#each $users as user}
            <UserSelectableItem
              users={$users}
              {user}
              selected={UsersSelected}
              multiselect={false}
              on:selected={navigate}
            />
          {/each}
        </SelectableListUI>
      {:else}
        <CollapsableContentPlaceholderUI>
          {language().user.pending.placeholders.notFoundCreate.text}
          <Link>{language().user.pending.placeholders.notFoundCreate.link}</Link>
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>
  </MasterListLayout>
</PageContent>
