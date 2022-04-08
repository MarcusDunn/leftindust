<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { Popover, Router } from 'framework7/types';
  import type { SelectableRouteParams } from '@/routes';
  import { Layouts } from '@framework/types/index';

  import language from '@framework/languages';

  import { PluginCategories, PluginTypes } from '@framework/plugins/index';
  import {
    ClientsSelected,
    ClientsSelectedTab,
    ClientsTab,
    ClientTabs,
  } from '../../store/ClientsStore';

  import { ACCOUNT, WIZARD_ACTIVE } from '@framework/store/index';
  import { openWizard, openPopover } from '@framework/modules/NavigationModule';


  import PatientEngine from '@framework/engines/patients/PatientEngine';

  import { Tabs, Tab, Block } from 'framework7-svelte';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import MenuUI from '@framework/ui/controller/MenuUI/MenuUI.svelte';
  import ProfileUI, { ProfileUIDrawer } from '@framework/ui/layout/ProfileUI/ProfileUI.svelte';
  import BoxedUI, { BoxedUISizes } from '@framework/ui/surface/BoxedUI/BoxedUI.svelte';
  import SelectButtonUI from '@framework/ui/button/SelectButtonUI/SelectButtonUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import ClampsButtonUI from '@framework/ui/button/ClampsButtonUI/ClampsButtonUI.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import PatientTags from '@framework/components/tags/PatientTags.svelte';
  import GenericPersonTable from '@framework/components/table/GenericPersonTable.svelte';
  import GenericPluginGrid from '@framework/components/grid/GenericPluginGrid.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import PluginPlaceholderLayout from '@framework/components/layout/PluginPlaceholderLayout.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;

  let layout: Layouts = $ACCOUNT.database.settings.options.layout || Layouts.Bundled;
  let tab: ClientTabs = ClientTabs.Documents;

  let createMenu: Popover.Popover;

  const selectable: Selectable = JSON.parse((<SelectableRouteParams>f7route.params).selectable);

  let { request } = PatientEngine({
    pids: [{ id: selectable.id }],
  });

  $: patient = $request?.data?.patients[0];
  $: $ACCOUNT.database.layout.pinned['Patient'][patient?.pid.id] ??= [];
</script>

<PageUI on:pageAfterIn={() => {
  if (!$WIZARD_ACTIVE && !quicklook) {
    $ClientsSelected = [selectable];
    $ClientsSelectedTab = ClientsTab.Patients;
  }
}}>
  <svelte:fragment slot="fixed">
    <MenuUI
      items={[
        {
          title: language().events.headers.event.text,
          text: language().events.descriptions.createDescription.text,
          icon: {
            f7: 'calendar_today',
            color: 'pink',
          },
          onClick: () => openWizard('/wizard/event/', {
            selectables: [{
              type: patient?.__typename,
              id: patient?.pid.id,
            }],
          }),
        },
        {
          title: language().visits.headers.visit.text,
          text: language().visits.descriptions.createDescription.text,
          icon: {
            f7: 'building_2_fill',
            color: 'blue',
          },
          onClick: () => openWizard('/wizard/visit/', {
            reference: {
              type: patient?.__typename,
              id: patient?.pid.id,
            },
          }),
        },
        {
          title: 'Record',
          text: 'Document and create a new record',
          icon: {
            f7: 'doc_on_doc_fill',
            color: 'deeporange',
          },
        },
      ]}
      bind:instance={createMenu}
    />
    <AppBarUI
      navigation={{ close: { popover: quicklook }, history: !quicklook }}
      {f7router}
    >
      <svelte:fragment slot="left">
        <SelectButtonUI
          options={[
            {
              text: 'Bundled',
              value: Layouts.Bundled,
              icon: {
                f7: 'rectangle_grid_3x2',
              },
            },
            {
              text: 'Stacked',
              value: Layouts.Stacked,
              icon: {
                f7: 'rectangle_grid_1x2',
              },
            },
          ]}
          bind:value={layout}
        />
        <SelectButtonUI
          options={[
            {
              text: language().clients.headers.documents.text,
              value: ClientTabs.Documents,
            },
            {
              text: language().clients.headers.records.text,
              value: ClientTabs.Records,
            },
            {
              text: language().clients.headers.contacts.text,
              value: ClientTabs.Contacts,
            },
          ]}
          bind:value={tab}
        />
      </svelte:fragment>

      <svelte:fragment slot="right">
        {#if !$WIZARD_ACTIVE && !quicklook}
          <MenuButtonUI
            title={language().buttons.edit.text}
            icon={{ f7: 'pencil_outline', color: 'gray' }}
            on:click={() => openWizard('/wizard/patient/', {
              selectable: {
                type: patient?.__typename,
                id: patient?.pid.id,
              },
            })}
          />
          <MenuButtonUI
            title={language().buttons.create.text}
            icon={{ f7: 'plus_circle_fill', color: 'purple' }}
            on:click={(event) => { openPopover(createMenu, event); }}
          />
        {/if}
      </svelte:fragment>
    </AppBarUI>
  </svelte:fragment>
 
  <RequestLayout {...$request} refetch={request.refetch} large middle>
    <ProfileUI drawer={{ ...ProfileUIDrawer, name: language().headers.pinned.text }}>
      <h2 slot="title">
        {patient?.firstName}
        {#if patient?.middleName}
          <ClampsButtonUI text={patient?.middleName} />
        {/if}
        {patient?.lastName}
      </h2>
      <BoxedUI slot="media" constraints={BoxedUISizes.Large} color="gray" round fill>
        <span>{`${patient?.firstName.charAt(0)}${patient?.lastName.charAt(0)}`}</span>
      </BoxedUI>
      <PatientTags slot="tags" {...patient} />
      <GenericPersonTable
        phones={patient?.phones}
        emails={patient?.emails}
        addresses={patient?.addresses}
      />
      <div slot="drawer">
        {#key $ACCOUNT}
          {#if $ACCOUNT.database.layout.pinned['Patient'][patient?.pid.id].length > 0}
            <SpecificPluginGrid
              props={$ACCOUNT.database.layout.pinned['Patient'][patient?.pid.id].map((selectable) => {
                if (selectable.type) {
                  return {
                    id: selectable.type,
                    selectable: {
                      id: selectable.id,
                      type: selectable.type,
                    },
                    reference: {
                      id: patient?.pid.id,
                      type: 'Patient',
                    },
                    quicklook,
                  };
                }
              })}
              type={PluginTypes.Widget}
            />
          {:else}
            <div style="max-width: 690px">
              <br />
              <br />
              <PluginPlaceholderLayout
                title={language().placeholders.noPinned.text}
                description={language().placeholders.addPinnedPlaceholder.text}
                link={{
                  label: language().placeholders.learnMoreAboutPinning.text,
                  href: '#',
                }}
              />
              <br />
            </div>
          {/if}
        {/key}
      </div>
    </ProfileUI>
    <Tabs>
      <Tab tabActive={tab === ClientTabs.Overview}>
      </Tab>
      <Tab tabActive={layout === Layouts.Bundled && tab !== ClientTabs.Overview}>
        <Block style="margin-left: 25px;margin-right: 25px">
          <Tabs>
            <Tab tabActive={tab === ClientTabs.Documents}>
              <GenericPluginGrid
                props={{ id: 'Patient', selectable, quicklook }}
                type={PluginTypes.Bundle}
                comparables={['Patient']}
                categories={[PluginCategories.Document]}
                store
              />
            </Tab>
            <Tab tabActive={tab === ClientTabs.Records}>
              <GenericPluginGrid
                props={{ id: 'Patient', selectable }}
                type={PluginTypes.Bundle}
                comparables={['Patient']}
                categories={[PluginCategories.Record]}
              />
            </Tab>
            <Tab tabActive={tab === ClientTabs.Contacts}>
              <GenericPluginGrid
                props={{ id: 'Patient', selectable }}
                type={PluginTypes.Bundle}
                comparables={['Patient']}
                categories={[PluginCategories.Contact]}
              />
            </Tab>
          </Tabs>
        </Block>
      </Tab>
      <Tab tabActive={layout === Layouts.Stacked && tab !== ClientTabs.Overview}>
        <Tabs>
          <Tab tabActive={tab === ClientTabs.Documents}>
            <!--
              <GenericPluginGrid
                props={{ id: 'Patient', selectable }}
                type={PluginTypes.Stack}
                comparables={['Patient']}
                categories={[PluginCategories.Document]}
                cols={[[Math.pow(10, 100), 1]]}
              />
            -->
          </Tab>
          <Tab tabActive={tab === ClientTabs.Records}>
          </Tab>
          <Tab tabActive={tab === ClientTabs.Contacts}>
          </Tab>
        </Tabs>
      </Tab>
    </Tabs>
    <br />
    <br />
  </RequestLayout>
</PageUI>
