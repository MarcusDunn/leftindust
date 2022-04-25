<script lang="ts">
  import { signOut } from '@/features/Account';

  import { _ } from '@/language';

  import {
    Block,
    PageContent,
    List,
    ListItem,
  } from 'framework7-svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import { BoxedSizes } from '@/features/UI/components/Boxed';

  import { account } from '@/features/Account/store';
  import UserTags from '@/features/User/components/UserTags/UserTags.svelte';

</script>

<style lang="scss">
  .center {
    display: flex;
    align-items: center;
    justify-content: center;
  }

</style>

<PageContent>
  <Block class="no-margin-top">
    <div class="center">
      <Boxed
        dimensions={BoxedSizes.Largest}
        color="blue"
        fill
        round
      >
        {`${$account.names?.firstName} ${$account.names?.lastName}`.match(/\b(\w)/g)?.join('')}
      </Boxed>
    </div>
    <div class="text-align-center" style="margin-top: 20px">
      <h2>{`${$account.names?.firstName} ${$account.names?.lastName}`}</h2>
      <UserTags {...$account} />
    </div>
    <div style="margin-top: 20px">
      <List inset noHairlines noChevron>
        <ListItem
          class="text-color-red"
          title={$_('generics.signOut')}
          link
          popupClose
          on:click={() => signOut()}
        />
      </List>
    </div>
  </Block>
</PageContent>
