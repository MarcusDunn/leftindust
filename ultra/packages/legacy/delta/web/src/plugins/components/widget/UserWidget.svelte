<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Writable } from 'svelte/store';
  import type { Plugin } from '../../';
  import type { Popover } from 'framework7/types';
  
  import UserEngine from '../../../engines/users/UserEngine';
  
  import language from '../../../languages/index';
  import { pin, pinned } from '../../../modules/PinModule';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';
  import { AppViews } from '../../../modules/AppModule';
  import { openPopover, safeNavigate } from '../../../modules/NavigationModule';
  
  import PluginWidgetUI from '../../../ui/plugin/PluginWidgetUI/PluginWidgetUI.svelte';
  import BoxedUI from '../../../ui/surface/BoxedUI/BoxedUI.svelte';
  import QuicklookUI from '../../../ui/view/QuicklookUI/QuicklookUI.svelte';
  import PinButtonUI from '../../../ui/button/PinButtonUI/PinButtonUI.svelte';

  import UserTags from '../../../components/tags/UserTags.svelte';
  import FirebaseUserTags from '../../../components/tags/FirebaseUserTags.svelte';

  export let dragger: () => void | undefined;
  export let selectable: Selectable<'Patient'>;
  export let reference: Selectable | undefined = undefined;
  export let properties: NonNullable<Plugin['properties']>;
  export let attachments: Writable<Selectable[]> | undefined = undefined;
  
  export let quicklook = false;

  let quicklookPopover: Popover.Popover;

  const { user } = UserEngine({
    uid: selectable.id,
  });

  const url = `/user/${JSON.stringify(selectable)}/`;
</script>

<QuicklookUI
  {url}
  view={AppViews.Clients}
  color={properties.color}
  bind:instance={quicklookPopover}
/>

<PluginWidgetUI
  color={properties.color}
  {dragger}
  loading={!$user}
  shadow={!attachments}
>
  <svelte:fragment slot="title">
    <div>
      {#if $user.isRegistered}
        {`${$user.names.firstName} ${$user.names?.middleName ? `${$user.names?.middleName?.charAt(0)}.` : ''} ${$user.names.lastName}`}
      {:else}
        {$user.firebaseUserInfo.email}
      {/if}
    </div>
  </svelte:fragment>
  
  <svelte:fragment slot="before-title">
    {#if reference}
      <PinButtonUI
        pinned={pinned({
          id: $user.uid,
          type: $user.__typename,
        }, reference)}
        on:pin={({ detail }) => reference && pin(detail, {
          id: $user.uid,
          type: $user.__typename,
        }, reference)}
      />
    {:else}
      {#if $user.isRegistered}
        <div style="margin-top: 6px">
          <BoxedUI
            color="blue"
            fill
            round
          >
            {`${$user.names.firstName.charAt(0)}${$user.names.lastName.charAt(0)}`}
          </BoxedUI>
        </div>
      {/if}
    {/if}
  </svelte:fragment>

  <svelte:fragment slot="subtitle">
    <div>
      {#if $user.isRegistered}
        <UserTags {...$user} small outline />
      {:else}
        <FirebaseUserTags {...$user} small outline />
      {/if}
    </div>
  </svelte:fragment>

  <svelte:fragment slot="controls">
    <Row>
      <Col width="50">
        {#if attachments}
          <Button
            round
            fill
            color="red"
            on:click={() => {
              if (attachments && $attachments)
                $attachments = $attachments.filter((attachment) => attachment.id !== selectable.id);
            }}
          >
            <Icon f7="trash_fill" />
            {language().buttons.delete.text}
          </Button>
        {:else}
          <Button
            round
            fill
            color={properties.color}
            on:click={() => safeNavigate(url, AppViews.Clients)}
          >
            {language().clients.patients.buttons.viewPatient.text}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <div
          class={`${quicklook ? 'disabled' : ''}`}
          on:click={(event) => openPopover(quicklookPopover, event)}
        >
          <Button
            round
            outline
            color="blue"
          >
            <Icon f7="eye_fill" />
            {language().buttons.quicklook.text}
          </Button>
        </div>
      </Col>
    </Row>
  </svelte:fragment>
</PluginWidgetUI>