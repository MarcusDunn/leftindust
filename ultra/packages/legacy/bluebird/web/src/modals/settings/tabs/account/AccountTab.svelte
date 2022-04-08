<script lang="ts">
  import { signOut } from '@framework/modules/UserModule';

  import {
    Block,
    PageContent,
    List,
    ListItem,
} from 'framework7-svelte';
  import BoxedUI, { BoxedUISizes } from '@framework/ui/surface/BoxedUI/BoxedUI.svelte';

  import { ACCOUNT } from '@framework/store/index';
import UserTags from '@framework/components/tags/UserTags.svelte';

</script>

<style lang="scss">
  .center {
    display: flex;
    align-items: center;
    justify-content: center;
  }

</style>

<PageContent>
  <Block>
    <div class="center">
      <BoxedUI constraints={BoxedUISizes.Largest} color="blue" fill round>
        {`${$ACCOUNT.names.firstName} ${$ACCOUNT.names.lastName}`.match(/\b(\w)/g)?.join('')}
      </BoxedUI>
    </div>
    <div class="text-align-center" style="margin-top: 20px">
      <h2>{`${$ACCOUNT.names.firstName} ${$ACCOUNT.names.lastName}`}</h2>
      <UserTags {...$ACCOUNT} />
    </div>
    <div style="margin-top: 20px">
      <List inset noHairlines noChevron>
        <ListItem class="text-color-red" title="Sign Out" link on:click={() => signOut()} popupClose />
      </List>
    </div>
  </Block>
</PageContent>
