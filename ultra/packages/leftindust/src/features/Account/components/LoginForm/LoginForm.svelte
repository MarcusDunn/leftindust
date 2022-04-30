<script lang="ts">
import {
  Block,
  BlockFooter,
  List,
  ListInput,
  Button,
} from 'framework7-svelte';

import type { Framework7Parameters } from 'framework7/types';

import { _ } from '@/language';
import { loginForm } from '../..';

export let buttonProps: Record<string, unknown> = {};

const { form, errors } = loginForm();
</script>

<form use:form>            
  <List noHairlinesMd>
    <ListInput
      type="text"
      name="email"
      label={$_('generics.email')}
      placeholder={$_('generics.emailPlaceholder')}
      errorMessage={$errors['email']?.toString()}
      errorMessageForce
      validate
    />
    <ListInput
      type="password"
      name="password"
      label={$_('generics.password')}
      placeholder={$_('generics.password')}
      errorMessage={$errors['password']?.toString()}
      errorMessageForce
      validate
      clearButton
    />
  </List>
  <Block class="no-padding">
    <Button type="submit" {...buttonProps}>
      {$_('generics.signIn')}
    </Button>
    <br />
    <BlockFooter>{$_('descriptions.alphaLoginNotice')}</BlockFooter>
  </Block>
</form>