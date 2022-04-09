<script lang="ts">
  import type { Router } from 'framework7/types';
  import type { Data } from '@/api/server';
  import type { Patient } from '@/api/server/schema/leftindust.schema';
  
  import { account } from '../Account/store';
  import { PatientTab } from '.';
  import { Layout } from '../App';
  
  import PatientEngine from '@/api/server/engines/patients/PatientEngine';
  import Page from '../UI/components/Page/Page.svelte';
  import { wizardOpen } from '../Wizard/store';
  import { clientsSelected, clientsSelectedTab } from '../Clients/store';
  import { ClientsTab } from '../Clients';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import Select from '../Input/components/Select/Select.svelte';
  import Patients from '../Patients';
  import { WidgetType } from '../Widgets';
  import { _ } from '@/language';
  import SelectButton from '../UI/components/SelectButton/SelectButton.svelte';
  import Request from '../Server/components/Request/Request.svelte';
  import Boxed from '../UI/components/Boxed/Boxed.svelte';
  import { BoxedSizes } from '../UI/components/Boxed';
  import { ProfileDrawer } from '../UI/components/Profile';
  import Clamp from '../UI/components/Clamp/Clamp.svelte';
  import PatientTags from './components/PatientTags/PatientTags.svelte';
  import EntityTable from '../Entity/components/EntityTable/EntityTable.svelte';
  import DescriptivePlaceholder
    from '../App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
    
  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;


  let layout: Layout = $account.database.settings.options.layout || Layout.Bundled;
  let tab: PatientTab = PatientTab.Documents;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const { patient, request } = PatientEngine({
    pids: [{ id: data.id }],
  });

  $: $account.database.layout.pinned['Patient'][$patient?.pid.id] ??= [];
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
      close={{ popover: quicklook }}
      history={!quicklook}
      {f7router}
      right={[
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
      ]}
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
              value: PatientTab.Documents,
            },
            {
              text: $_('generics.records'),
              value: PatientTab.Records,
            },
            {
              text: $_('generics.contacts'),
              value: PatientTab.Contacts,
            },
          ]}
          bind:value={tab}
        />
      </svelte:fragment>
    </Appbar>
  </svelte:fragment>
  <Request {...$request} refetch={request.refetch} large middle>
    <Profile drawer={{ ...ProfileDrawer, name: $_('generics.pinned') }}>
      <h2 slot="title">
        {$patient?.firstName}
        {#if $patient?.middleName}
          <Clamp text={$patient?.middleName} />
        {/if}
        {$patient?.lastName}
      </h2>
      <Boxed slot="media" dimensions={BoxedSizes.Large} color="gray" round fill>
        <span>{`${$patient?.firstName.charAt(0)}${$patient?.lastName.charAt(0)}`}</span>
      </Boxed>
      <PatientTags slot="tags" {...$patient} />
      <EntityTable
        phones={$patient?.phones}
        emails={$patient?.emails}
        addresses={$patient?.addresses}
      />
      <div slot="drawer">
        {#key $account}
          {#if $account.database.layout.pinned['Patient'][$patient?.pid.id].length > 0}
            <SpecificGrid
              props={$account.database.layout.pinned['Patient'][$patient?.pid.id].map(({ type, id }) => {
                if (type) {
                  return {
                    id: type,
                    data: { id, type },
                    reference: {
                      id: $patient?.pid.id,
                      type: 'Patient',
                    },
                    quicklook,
                  };
                }
              })}
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
  </Request>
</Page>