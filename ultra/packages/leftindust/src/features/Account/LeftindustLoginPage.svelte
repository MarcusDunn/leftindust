<script lang="ts">
  import { _ } from '@/language';
  import { signInStatus } from './store';
  import {
    Block,
    List,
    ListInput,
    Button,
    BlockFooter,
    Preloader,
    PageContent,
  } from 'framework7-svelte';
  
  import { signOut } from '.';
  
  import Page from '../UI/components/Page/Page.svelte';
  import ErrorButtons from '../Errors/components/ErrorButtons/ErrorButtons.svelte';
  import LeftindustBanner from '../App/components/LeftindustBanner/LeftindustBanner.svelte';

  import { authenticateFirebaseUser, getFirebaseUserDatabaseAndSignIn } from '.';

  let email = '';
  let password = '';

  const submit = () => void authenticateFirebaseUser({ email, password }).then((signInSuccess) => {
    if (signInSuccess) {
      email = '';
      password = '';
    }
  });

</script>

<Page pageContent={false} loginScreen>
  <PageContent style="overflow: hidden" loginScreen>
    <Block>
      <LeftindustBanner>
        {#if !$signInStatus.signedIn}
          <p>{$_('descriptions.signIn')}</p>
          <List noHairlinesMd>
            <ListInput
              label={$_('generics.email')}
              type="text"
              placeholder={$_('generics.emailPlaceholder')}
              bind:value={email}
            />
            <ListInput
              label={$_('generics.password')}
              type="password"
              placeholder={$_('generics.password')}
              clearButton
              bind:value={password}
            />
          </List>
          <Block class="no-padding">
            <Button on:click={submit} fill color="deeppurple">
              {$_('generics.signIn')}
            </Button>
            <br />
            <BlockFooter>{$_('descriptions.alphaLoginNotice')}</BlockFooter>
            
          </Block>
        {:else if $signInStatus.error}
          <p>{$signInStatus.error}</p>
          <br />
          <ErrorButtons
            tryAgain={() => $signInStatus.user ? getFirebaseUserDatabaseAndSignIn($signInStatus.user) : undefined}
            {signOut}
          />
        {:else}
          <Block class="text-align-center">
            <br />
            <br />
            <br />
            <Preloader />
          </Block>
        {/if}
      </LeftindustBanner>
    </Block>
  </PageContent>
</Page>
