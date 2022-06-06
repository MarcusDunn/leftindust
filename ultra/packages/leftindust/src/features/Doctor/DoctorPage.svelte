<script lang="ts">
  import type { Router } from 'framework7/types';
  import { DoctorQueryDocument, type Data, type DoctorFragment } from '@/api/server';
  
  import { account } from '../Account/store';
  import { DoctorTab } from '.';
  import { Layout } from '../App';

  import { Tab, Tabs, Block } from 'framework7-svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import { wizardOpen } from '../Wizard/store';
  import { clientsSelected, clientsSelectedTab } from '../Clients/store';
  import { ClientsTab } from '../Clients';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import { WidgetCategory, WidgetType } from '../Widgets';
  import { _ } from '@/language';
  import SelectButton from '../UI/components/SelectButton/SelectButton.svelte';
  import Request from '../Server/components/Request/Request.svelte';
  import Boxed from '../UI/components/Boxed/Boxed.svelte';
  import { BoxedSizes } from '../UI/components/Boxed';
  import { ProfileDrawer } from '../UI/components/Profile';
  import Clamp from '../UI/components/Clamp/Clamp.svelte';
  import DoctorTags from './components/DoctorTags/DoctorTags.svelte';
  import EntityTable from '../Entity/components/EntityTable/EntityTable.svelte';
  import DescriptivePlaceholder
    from '../App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { operationStore, query } from '@urql/svelte';
  import GenericGrid from '../Widgets/components/Grid/GenericGrid.svelte';
    
  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;

  let doctor: DoctorFragment | undefined;

  let layout: Layout = $account.database.settings.options.layout || Layout.Bundled;
  let tab: DoctorTab = DoctorTab.Documents;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(DoctorQueryDocument, {
    dids: [{ id: data.id }],
  });

  $: doctor = $request.data?.doctors[0];

  query(request);
</script>

<Page
  on:pageAfterIn={() => {
    if (!$wizardOpen && !quicklook) {
      $clientsSelected = [data];
      $clientsSelectedTab = ClientsTab.Doctors;
    }
  }}
>
  <svelte:fragment slot="fixed">
    <Appbar
      close={{ popup: quicklook }}
      history={!quicklook}
      {f7router}
      right={!quicklook ? [
        {
          title: $_('generics.edit'),
          icon: { f7: 'pencil_outline', color: 'gray' },
          condense: true,
        },
        {
          title: $_('generics.create'),
          icon: { f7: 'plus_circle_fill', color: 'purple' },
          condense: true,
        },
      ] : []}
    >
      <svelte:fragment slot="left">
        <SelectButton
          options={[
            {
              text: $_('generics.bundled'),
              value: Layout.Bundled,
              icon: {
                f7: 'rectangle_grid_3x2',
              },
            },
            {
              text: $_('generics.stacked'),
              value: Layout.Stacked,
              icon: {
                f7: 'rectangle_grid_1x2',
              },
            },
          ]}
          bind:value={layout}
        />
        <SelectButton
          options={[
            {
              text: $_('generics.documents'),
              value: DoctorTab.Documents,
            },
            {
              text: $_('generics.records'),
              value: DoctorTab.Records,
            },
            {
              text: $_('generics.contacts'),
              value: DoctorTab.Contacts,
            },
          ]}
          bind:value={tab}
        />
      </svelte:fragment>
    </Appbar>
  </svelte:fragment>
  <Request {...$request} refetch={request.reexecute} large middle>
    <Profile drawer={{ ...ProfileDrawer, name: $_('generics.pinned') }}>
      <h2 slot="title">
        {doctor?.firstName}
        {#if doctor?.middleName}
          <Clamp text={doctor?.middleName} />
        {/if}
        {doctor?.lastName}
      </h2>
      <Boxed slot="media" dimensions={BoxedSizes.Large} color="primary" round fill>
        <span>{`${doctor?.firstName.charAt(0)}${doctor?.lastName.charAt(0)}`}</span>
      </Boxed>
      <DoctorTags slot="tags" {...doctor} />
      <EntityTable
        phones={doctor?.phones}
        emails={doctor?.emails}
        addresses={doctor?.addresses}
      />
      <div slot="drawer">
        {#key $account}
          {#if $account.database.layout.pinned['Doctor']?.[doctor?.did.id]?.length ?? 0 > 0}
            <SpecificGrid
              props={$account.database.layout.pinned['Doctor']?.[doctor?.did.id]?.map(({ type, id }) => {
                if (type) {
                  return {
                    id: type,
                    data: { id, type },
                    reference: {
                      id: doctor?.did.id,
                      type: 'Doctor',
                    },
                    quicklook,
                  };
                }
              }) ?? []}
              type={WidgetType.Card}
            />
          {:else}
            <div style="max-width: 690px">
              <br />
              <br />
              <DescriptivePlaceholder
                title={$_('generics.noPinned')}
                description={$_('descriptions.addPinned')}
                link={{
                  label: $_('descriptions.learnMorePinning'),
                  href: '#',
                }}
              />
              <br />
            </div>
          {/if}
        {/key}
      </div>
    </Profile>
    <Tabs>
      <Tab tabActive={layout === Layout.Bundled && tab !== DoctorTab.Overview}>
        <Block style="margin-left: 25px;margin-right: 25px">
          <Tabs>
            <Tab tabActive={tab === DoctorTab.Contacts}>
              <GenericGrid 
              props={{ id:'Doctor', data, quicklook }}
              type={WidgetType.Bundle}
              dataType={['Doctor']}
              category={[WidgetCategory.Contact]}
              store
              />
            </Tab>
          </Tabs>
        </Block>
      </Tab>
    </Tabs>
  </Request>
</Page>
