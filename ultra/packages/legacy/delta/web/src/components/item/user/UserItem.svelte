<script lang="ts">
  import type { UsersQueryResult } from '../../../requests/queries';

  import { ListItem } from 'framework7-svelte';

  import UserTags from '../../../components/tags/UserTags.svelte';
  
  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';

  export let user: UsersQueryResult['users'][0];
  export let link = true;
</script>

<ListItem
  title={`${user.names.firstName} ${user.names.lastName}`}
  {link}
  on:click
>
  <BoxedUI slot="media" color="blue" fill round>
    {`${user.names.firstName?.charAt(0)}${user.names.lastName?.charAt(0)}`}
  </BoxedUI>
  <svelte:fragment slot="text">
    <UserTags {...user} small outline />
  </svelte:fragment>
</ListItem>
