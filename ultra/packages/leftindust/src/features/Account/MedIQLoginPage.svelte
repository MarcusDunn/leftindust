<script lang="ts">
  import { _ } from '@/language';
  import { signInStatus } from './store';
  import { Block, Preloader, PageContent } from 'framework7-svelte';
  
  import { signOut } from '.';
  
  import Page from '../UI/components/Page/Page.svelte';
  import MedIQBanner from '../App/components/MedIQBanner/MedIQBanner.svelte';
  import ErrorButtons from '../Errors/components/ErrorButtons/ErrorButtons.svelte';

  import { getFirebaseUserDatabaseAndSignIn } from '.';

  import LoginForm from './components/LoginForm/LoginForm.svelte';
</script>

<Page pageContent={false} loginScreen>
  <PageContent style="overflow: hidden" loginScreen>
    <Block>
      <MedIQBanner>
        {#if !$signInStatus.signedIn}
          <p>{$_('descriptions.signIn')}</p>
          <LoginForm buttonProps={{
            fill: true,
            color: 'purple',
            large: true,
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
      </MedIQBanner>
    </Block>
  </PageContent>
</Page>
