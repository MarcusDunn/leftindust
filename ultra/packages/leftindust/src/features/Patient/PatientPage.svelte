<script lang="ts">
  import type { Router } from 'framework7/types';
  import { PatientsByPatientIdQueryDocument, type Data, type PatientFragment } from '@/api/server';
  
  import { account } from '../Account/store';
  import { ClientTab } from '../Client';
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
  import PatientTags from './components/PatientTags/PatientTags.svelte';
  import EntityTable from '../Entity/components/EntityTable/EntityTable.svelte';
  import DescriptivePlaceholder from '../App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { operationStore, query } from '@urql/svelte';
  import GenericGrid from '../Widgets/components/Grid/GenericGrid.svelte';
  import { openWizard } from '../Wizard';
    
  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;

  let patient: PatientFragment | undefined;

  let layout: Layout = $account.database.settings.options.layout || Layout.Bundled;
  let tab: ClientTab = ClientTab.Records;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(PatientsByPatientIdQueryDocument, {
    patientIds: [{ value: data.id }],
  });

  $: patient = $request.data?.patientsByPatientId[0];

  query(request);
</script>

<Page
  on:pageAfterIn={() => {
    if (!$wizardOpen && !quicklook) {
      $clientsSelected = [data];
      $clientsSelectedTab = ClientsTab.Patients;
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
          onClick: () => openWizard('/wizard/patient/' ,{ patient, callback: () => {
            $request.reexecute();
          } }),
        },
        // {
        //   title: $_('generics.create'),
        //   icon: { f7: 'plus_circle_fill', color: 'purple' },
        //   condense: true,
        // },
      ] : []}
    >
      <svelte:fragment slot="left">
        <!--
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
        -->
        <SelectButton
          options={[
            {
              text: $_('generics.records'),
              value: ClientTab.Records,
            },
            /*
            {
              text: $_('generics.contacts'),
              value: ClientTab.Contacts,
            },
            */
          ]}
          bind:value={tab}
        />
      </svelte:fragment>
    </Appbar>
  </svelte:fragment>
  <Request {...$request} refetch={request.reexecute} large middle>
    <Profile drawer={{ ...ProfileDrawer, name: $_('generics.pinned') }}>
      <h2 slot="title">
        {patient?.firstName}
        {#if patient?.middleName}
          <Clamp text={patient?.middleName} />
        {/if}
        {patient?.lastName}
      </h2>
      <Boxed slot="media" dimensions={BoxedSizes.Large} color="gray" round fill>
        <span>{`${patient?.firstName.charAt(0)}${patient?.lastName.charAt(0)}`}</span>
      </Boxed>
      <PatientTags slot="tags" {...patient} />
      <EntityTable
        phones={patient?.phoneNumbers}
        emails={patient?.emails}
        addresses={patient?.addresses}
      />
      <div slot="drawer">
        {#key $account}
          {#if $account.database.layout.pinned['Patient']?.[patient?.id.value]?.length ?? 0 > 0}
            <SpecificGrid
              props={$account.database.layout.pinned['Patient']?.[patient?.id.value]?.map(({ type, id }) => {
                if (type) {
                  return {
                    id: type,
                    data: { id, type },
                    reference: {
                      id: patient?.id.value,
                      type: 'Patient',
                    },
                    quicklook,
                  };
                }
              }) ?? []}
              type={WidgetType.Card}
            />
          {:else}
            <div style="margin-top: 40px">
              <DescriptivePlaceholder
                title={$_('generics.noPinned')}
                description={$_('descriptions.addPinned')}
                link={{
                  label: $_('descriptions.learnMorePinning'),
                  href: '#',
                }}
              />
            </div>
            <br />
          {/if}
        {/key}
      </div>
    </Profile>
    <Tabs>
      <Tab tabActive={layout === Layout.Bundled}>
        <Block style="margin-left: 25px;margin-right: 25px">
          <Tabs>
            <Tab tabActive={tab === ClientTab.Records}>
              <GenericGrid
                props={{ id: 'CompleteSurvey', data, quicklook }}
                type={WidgetType.Bundle}
                dataType={['CompleteSurvey']}
                category={[WidgetCategory.Record]}
                store
              />
            </Tab>
            <Tab tabActive={tab === ClientTab.Contacts}>
              <GenericGrid
                props={{ id: 'Patient', data, quicklook }}
                type={WidgetType.Bundle}
                dataType={['Patient']}
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