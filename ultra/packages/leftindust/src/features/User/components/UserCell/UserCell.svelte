<script lang="ts">
  import type { UsersFragment } from '@/api/server';

  import Cell from '@/features/UI/components/Cell/Cell.svelte';
  import UserTags from '../UserTags/UserTags.svelte';
  
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';

  export let user: UsersFragment;
  export let link = true;
</script>

<Cell
  title={`${user.names?.firstName} ${user.names?.lastName}`}
  {link}
  on:click
>
  <Boxed slot="media" color="blue" fill round>
    {`${user.names?.firstName?.charAt(0)}${user.names?.lastName?.charAt(0)}`}
  </Boxed>
  <svelte:fragment slot="text">
    <UserTags {...user} small outline />
  </svelte:fragment>
</Cell>
