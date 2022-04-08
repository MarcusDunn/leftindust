<script lang="ts">
  import language from '@framework/languages/index';
  import { SIGN_IN } from '@framework/store';
  import {
    Page,
    Block,
    List,
    ListInput,
    Button,
    BlockFooter,
    Preloader,
  } from 'framework7-svelte';

  import MedIQBanner from '@framework/components/banner/MedIQBanner.svelte';
  import ErrorButtonsController from '@framework/components/controller/ErrorButtonsController.svelte';

  import {
    authenticateFirebaseUser,
    getFirebaseUserDatabaseAndSignIn,
  } from '@framework/modules/UserModule';

  let email = '';
  let password = '';

  const submit = () => {
    void authenticateFirebaseUser({ email, password }).then((signInSuccess) => {
      if (signInSuccess) {
        email = '';
        password = '';
      }
    });
  };

</script>

<Page loginScreen>
  <Block>
    <MedIQBanner>
      {#if !$SIGN_IN.signedIn}
        <p>{language().user.signIn.login.description.text}</p>
        <List noHairlinesMd>
          <ListInput
                  label={language().form.email.text}
                  type="text"
                  placeholder="me@domain.com"
                  bind:value={email}
          />
          <ListInput
                  label={language().form.password.text}
                  type="password"
                  placeholder={language().form.password.text}
                  clearButton
                  bind:value={password}
          />
        </List>
        <Block class="no-padding">
          <Button on:click={submit} large fill color="purple">
            {language().user.signIn.title.text}
          </Button>
          <br />
          <BlockFooter>{language().user.signIn.login.alphaDescription.text}</BlockFooter>
        </Block>
      {:else if $SIGN_IN.error}
        <p>{$SIGN_IN.error}</p>
        <Block class="no-padding display-flex">
          <Button
                  color="primary"
                  fill
                  round
                  on:click={() => $SIGN_IN.user ? getFirebaseUserDatabaseAndSignIn($SIGN_IN.user) : undefined}
          >
            {language().buttons.tryAgain.text}
          </Button>
          <ErrorButtonsController />
        </Block>
      {:else}
        <p>{language().user.signIn.login.description.text}</p>
        <Block class="text-align-center">
          <br />
          <br />
          <Preloader />
        </Block>
      {/if}
    </MedIQBanner>
  </Block>
</Page>
