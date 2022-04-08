<script lang="ts">
  import type { Writable } from 'svelte/store';

  import language from '../../../languages';
  import { DEFAULT_RANGE_INPUT } from '../../../requests';

  import { Block, Col, Row } from 'framework7-svelte';

  import PageUI from '../../../ui/layout/PageUI/PageUI.svelte';
  import AppBarUI from '../../../ui/controller/AppBarUI/AppBarUI.svelte';
  import MenuButtonUI from '../../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import InsetListUI from '../../../ui/list/InsetListUI/InsetListUI.svelte';
  import CollapsableContentPlaceholderUI
    from '../../../ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';
    
  import UsersGenericEngine from '../../../engines/users/UsersGenericEngine';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import FirebaseUsersGenericEngine from '../../../engines/users/FirebaseUsersGenericEngine';
  import FirebaseUserCheckboxItem
    from '../../../components/item/user/FirebaseUserCheckboxItem.svelte';
  import UserCheckboxItem from '../../../components/item/user/UserCheckboxItem.svelte';

  export let attachments: Writable<string[]>;
  export let multiple: boolean;
  export let submit: () => void;
  export let back: boolean;

  export let filterRegistered = false;

  const { request, users } = filterRegistered ? FirebaseUsersGenericEngine({
    range: DEFAULT_RANGE_INPUT,
  }) : UsersGenericEngine({
    range: DEFAULT_RANGE_INPUT,
  });

</script>

<PageUI>
  <AppBarUI
    slot="fixed"
    navigation={{ back, close: { popover: !back } }}
  >
    <svelte:fragment slot="right">
      <MenuButtonUI
        title={language().buttons.done.text}
        icon={{ f7: 'checkmark_circle_fill', color: 'purple' }}
        on:click={submit}
      />
    </svelte:fragment>
  </AppBarUI>
  <Block>
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if $users.length > 0}
        <Row class="no-gutter">
          {#each $users as user}
            <Col width="33" style="margin-bottom: 10px">
              <InsetListUI>
                {#if filterRegistered}
                  {#if 'email' in user}
                    <FirebaseUserCheckboxItem {user} bind:selected={$attachments} {multiple} />
                  {/if}
                {:else}
                  {#if 'names' in user}
                    <UserCheckboxItem {user} bind:selected={$attachments} {multiple} />
                  {/if}
                {/if}
              </InsetListUI>
            </Col>
          {/each}
        </Row>
      {:else}
        <br />
        <CollapsableContentPlaceholderUI center>
          {language().placeholders.noResults.text}
        </CollapsableContentPlaceholderUI>
      {/if}
    </RequestLayout>
  </Block>
</PageUI>
