<script lang="ts">
  import type { PartialUserFragment } from '@/api/server';

  import Cell from '@/features/UI/components/Cell/Cell.svelte';
  import UserTags from '../UserTags/UserTags.svelte';
  
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';

  export let user: PartialUserFragment;
  export let link = true;
</script>

<Cell
  title={`${user.name?.firstName} ${user.name?.lastName}`}
  {link}
  on:click
>
  <Boxed slot="media" color="blue" fill round>
    {`${user.name?.firstName?.charAt(0)}${user.name?.lastName?.charAt(0)}`}
  </Boxed>
  <svelte:fragment slot="text">
    <UserTags {...user} small outline />
  </svelte:fragment>
</Cell>
