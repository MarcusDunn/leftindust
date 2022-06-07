<script lang="ts">
  import { signOut } from '@/features/Account';
  
  import { onMount } from 'svelte';
  import { _ } from '@/language';

  import {
    Block,
    Row,
    Col,
    List,
    ListItem,
  } from 'framework7-svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';

  import { openPopupUrl } from '../View';
  import { account } from '@/features/Account/store';
  import UserTags from '@/features/User/components/UserTags/UserTags.svelte';
  
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import AppLauncher from './components/AppLauncher/AppLauncher.svelte';

  let time = new Date();
  let message: string;

  const hour = time.getHours();

  if (hour < 12) {
    message = $_('generics.goodMorning', { values: { name: $account.names?.firstName } });
  } else if (hour < 18) {
    message = $_('generics.goodAfternoon', { values: { name: $account.names?.firstName } });
  } else {
    message = $_('generics.goodEvening', { values: { name: $account.names?.firstName } });
  }

  onMount(() => {
    const interval = setInterval(() => {
      time = new Date();
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  });
</script>

<style lang="scss">
  .center {
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>

<Page>
  <AppBar slot="fixed" />
  <Block>
    <br />
    <div class="center">
      <Boxed
        dimensions={{
          width: 120,
          height: 120,
          fontSize: 50,
        }}
        color="blue"
        fill
        round
      >
        {`${$account.names?.firstName} ${$account.names?.lastName}`.match(/\b(\w)/g)?.join('')}
      </Boxed>
    </div>
    <div class="text-align-center" style="margin-top: 20px">
      <h2>{message}</h2>
      <p />
      <UserTags {...$account} />
      <p />
      <div class="center">
        <Row noGap style="width:600px">
          <Col width="100" xsmall="66">
            <List
              class="no-margin-bottom"
              inset
              noHairlines
              style="margin-top: 15px"
            >
              <ListItem
                title={$_('generics.settings')}
                link
                popupClose
                on:click={() => openPopupUrl('/settings/')}
              />
            </List>
          </Col>
          <Col width="100" xsmall="33">
            <List
              class="no-margin-bottom"
              inset
              noHairlines
              noChevron
              style="margin-top: 15px"
            >
              <ListItem
                class="text-color-red"
                title={$_('generics.signOut')}
                link
                popupClose
                on:click={() => signOut()}
              />
            </List>
          </Col>
        </Row>
      </div>
    </div>
    <div style="margin-top: 70px;">
      <AppLauncher />
    </div>
  </Block>
</Page>
