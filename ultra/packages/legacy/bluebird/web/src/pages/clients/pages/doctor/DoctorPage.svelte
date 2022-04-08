<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { Popover, Router } from 'framework7/types';
  import type { SelectableRouteParams } from '@/routes';
  import { Layouts } from '@framework/types/index';

  import language from '@framework/languages/index';

  import { PluginCategories, PluginTypes } from '@framework/plugins/index';
  import {
    ClientsSelected,
    ClientsSelectedTab,
    ClientTabs,
    ClientsTab,
  } from '../../store/ClientsStore';

  import { ACCOUNT, WIZARD_ACTIVE } from '@framework/store/index';
  import { openPopover, openWizard } from '@framework/modules/NavigationModule';

  import DoctorEngine from '@framework/engines/doctors/DoctorEngine';

  import { Tabs, Tab, Block } from 'framework7-svelte';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import ProfileUI, { ProfileUIDrawer } from '@framework/ui/layout/ProfileUI/ProfileUI.svelte';
  import BoxedUI, { BoxedUISizes } from '@framework/ui/surface/BoxedUI/BoxedUI.svelte';
  import MenuUI from '@framework/ui/controller/MenuUI/MenuUI.svelte';
  import SelectButtonUI from '@framework/ui/button/SelectButtonUI/SelectButtonUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import ClampsButtonUI from '@framework/ui/button/ClampsButtonUI/ClampsButtonUI.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import DoctorTags from '@framework/components/tags/DoctorTags.svelte';
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

  let { request } = DoctorEngine({
    dids: [{ id: selectable.id }],
  });

  $: doctor = $request?.data?.doctors[0];
  $: $ACCOUNT.database.layout.pinned['Doctor'][doctor?.did.id] ??= [];
</script>

<PageUI on:pageAfterIn={() => {
  if (!$WIZARD_ACTIVE && !quicklook) {
    $ClientsSelected = [selectable];
    $ClientsSelectedTab = ClientsTab.Doctors;
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
              type: doctor?.__typename,
              id: doctor?.did.id,
            }],
          }),
        },
      ]}
      bind:instance={createMenu}
    />
    <AppBarUI
      navigation={{ close: quicklook, history: !quicklook }}
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
            on:click={() => openWizard('/wizard/doctor/', {
              selectable: {
                type: doctor?.__typename,
                id: doctor?.did.id,
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
        {doctor?.firstName}
        {#if doctor?.middleName}
          <ClampsButtonUI text={doctor?.middleName} />
        {/if}
        {doctor?.lastName}
      </h2>
      <BoxedUI slot="media" constraints={BoxedUISizes.Large} color="primary" round fill>
        <span>{`${doctor?.firstName.charAt(0)}${doctor?.lastName.charAt(0)}`}</span>
      </BoxedUI>
      <DoctorTags slot="tags" {...doctor} />
      <GenericPersonTable
        phones={doctor?.phones}
        emails={doctor?.emails}
        addresses={doctor?.addresses}
      />
      <div slot="drawer">
        {#key $ACCOUNT}
          {#if $ACCOUNT.database.layout.pinned['Doctor'][doctor?.did.id].length > 0}
            <SpecificPluginGrid
              props={$ACCOUNT.database.layout.pinned['Doctor'][doctor?.did.id].map((selectable) => {
                if (selectable.type) {
                  return {
                    id: selectable.type,
                    selectable: {
                      id: selectable.id,
                      type: selectable.type,
                    },
                    reference: {
                      id: doctor?.did.id,
                      type: 'Doctor',
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
                props={{ id: 'Doctor', selectable }}
                type={PluginTypes.Bundle}
                comparables={['Doctor']}
                categories={[PluginCategories.Document]}
              />
            </Tab>
            <Tab tabActive={tab === ClientTabs.Contacts}>
              <GenericPluginGrid
                props={{ id: 'Doctor', selectable }}
                type={PluginTypes.Bundle}
                comparables={['Doctor']}
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
                props={{ id: 'Doctor', selectable }}
                type={PluginTypes.Stack}
                comparables={['Doctor']}
                categories={[PluginCategories.Document]}
                cols={[[Math.pow(10, 100), 1]]}
              />
            -->
          </Tab>
          <Tab tabActive={tab === ClientTabs.Contacts}>
          </Tab>
        </Tabs>
      </Tab>
    </Tabs>
  </RequestLayout>
  <br />
  <br />
</PageUI>
