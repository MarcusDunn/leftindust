<script lang="ts">
  import type { CardProps } from '@/features/Widgets';
  import type { Popup } from 'framework7/types';
  
  import { operationStore } from '@urql/svelte';
  import { type PartialUserFragment, PartialUserByUserUniqueIdQueryDocument } from '@/api/server';
  
  import { _ } from '@/language';
  import { pin, pinned } from '@/features/Pin';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';

  import { AppViews } from '@/features/App';
  import { openPopup } from '@/features/View';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';
  
  import UserTags from '../UserTags/UserTags.svelte';
  import FirebaseUserTags from '../UserTags/FirebaseUserTags.svelte';
  import Quicklook from '@/features/View/components/Quicklook/Quicklook.svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';

  const { data, dragger, reference, attachments, quicklook } = $$props as CardProps;
  let quicklookPopup: Popup.Popup;

  let user: PartialUserFragment;

  const request = operationStore(PartialUserByUserUniqueIdQueryDocument, {
    uniqueId: data.id,
  });

  $: if (request.data?.userByUserUniqueId) user = request.data?.userByUserUniqueId;

  const url = `/user/${JSON.stringify(data)}/`;
</script>

<Quicklook
  {url}
  view={AppViews.Clients}
  color="blue"
  bind:instance={quicklookPopup}
/>

<Card
  color="blue"
  {dragger}
  loading={!user}
  shadow={!attachments}
>
  <svelte:fragment slot="title">
    <div>
      {#if user.accountDetails?.isRegistered}
        {`${user.name?.firstName} ${user.name?.middleName ? `${user.name?.middleName?.charAt(0)}.` : ''} ${user.name?.lastName}`}
      {:else}
        {user.accountDetails?.email}
      {/if}
    </div>
  </svelte:fragment>
  
  <svelte:fragment slot="before-title">
    {#if reference}
      <PinButton
        pinned={pinned({
          id: user.id.value,
          type: user.__typename,
        }, reference)}
        on:pin={({ detail }) => reference && pin(detail, {
          id: user.id.value,
          type: user.__typename,
        }, reference)}
      />
    {:else}
      {#if user.accountDetails?.isRegistered}
        <div style="margin-top: 6px">
          <Boxed
            color="blue"
            fill
            round
          >
            {`${user.name?.firstName.charAt(0)}${user.name?.lastName.charAt(0)}`}
          </Boxed>
        </div>
      {/if}
    {/if}
  </svelte:fragment>

  <svelte:fragment slot="subtitle">
    <div>
      {#if user.accountDetails?.isRegistered}
        <UserTags {...user} small outline />
      {:else}
        <FirebaseUserTags {...user} small outline />
      {/if}
    </div>
  </svelte:fragment>

  <svelte:fragment slot="controls">
    {#if reference}
      <PinButton
        pinned={pinned({
          id: user.id.value,
          type: user.__typename,
        }, reference)}
        on:pin={({ detail }) => reference && pin(detail, {
          id: user.id.value,
          type: user.__typename,
        }, reference)}
      />
    {/if}
    <Row>
      <Col width="50">
        {#if attachments}
          <Button
            round
            fill
            color="red"
            on:click={() => {
              if (attachments && $attachments)
                $attachments = $attachments.filter((attachment) => attachment.id !== data.id);
            }}
          >
            <Icon f7="trash_fill" />
            {$_('generics.delete')}
          </Button>
        {:else}
          <Button
            round
            fill
            color="blue"
          >
            {$_('generics.viewUser')}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <div
          class={`${quicklook ? 'disabled' : ''}`}
          on:click={() => openPopup(quicklookPopup)}
        >
          <Button
            round
            outline
            color="blue"
          >
            <Icon f7="eye_fill" />
            {$_('generics.quicklook')}
          </Button>
        </div>
      </Col>
    </Row>
  </svelte:fragment>
</Card>