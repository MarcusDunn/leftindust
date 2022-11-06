<script lang="ts">
  import { _ } from '@/language';
  import { signInStatus } from './store';
  import { Block, Preloader, PageContent } from 'framework7-svelte';
  
  import { signOut } from '.';
  
  import Page from '../UI/components/Page/Page.svelte';
  import ErrorButtons from '../Errors/components/ErrorButtons/ErrorButtons.svelte';
  import LeftindustBanner from '../App/components/LeftindustBanner/LeftindustBanner.svelte';
  import LoginForm from './components/LoginForm/LoginForm.svelte';

  import { getFirebaseUserDatabaseAndSignIn } from '.';
</script>

<Page pageContent={false} loginScreen>
  <PageContent style="overflow: hidden" loginScreen>
    <Block>
      <LeftindustBanner text="Marissa">
        {#if !$signInStatus.signedIn}
          <p>{$_('descriptions.signIn')}</p>
          <LoginForm buttonProps={{
            fill: true,
            color: 'deeppurple',
          }} />
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
