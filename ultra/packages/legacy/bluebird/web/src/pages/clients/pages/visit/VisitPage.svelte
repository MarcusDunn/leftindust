<script lang="ts">
  import type { Router, Popover } from 'framework7/types';
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { SelectableRouteParams } from '@/routes';

  import {
    Block,
    Button,
    Chip,
    Icon,
  } from 'framework7-svelte';

  import language from '@framework/languages';
  import VisitEngine from '@framework/engines/visits/VisitEngine';
  import { WIZARD_ACTIVE } from '@framework/store';
  import { PluginTypes } from '@framework/plugins';
  import { ClientsSelected } from '../../store/ClientsStore';
  import { AppViews } from '@framework/modules/AppModule';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import ProfileUI from '@framework/ui/layout/ProfileUI/ProfileUI.svelte';
  import CollapsableContentUI
    from '@framework/ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';
  import CollapsableContentPlaceholderUI
    from '@framework/ui/layout/CollapsableContentPlaceholderUI/CollapsableContentPlaceholderUI.svelte';

  import RequestLayout from '@framework/components/layout/RequestLayout.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import { openPopover, openWizard, safeNavigate } from '@framework/modules/NavigationModule';
  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;

  const selectable: Selectable = JSON.parse((<SelectableRouteParams>f7route.params).selectable);

  let attachmentsPopover: Popover.Popover;

  const { request, visit } = VisitEngine({
    vids: [{ id: selectable.id }],
  });

  const pageAfterIn = () => {
    if (!$WIZARD_ACTIVE && !quicklook)
      visit.subscribe((value) => {
        if (value)
          $ClientsSelected = [
            ...value.event.doctors.map((doctor) => ({ type: 'Doctor', id: doctor.id?.value })),
            ...value.event.patients.map((doctor) => ({ type: 'Patient', id: doctor.pid.id })),
          ] as Selectable[];
      });
  };
</script>

<style lang="scss">
  div {
    margin-top: -10px;
    & :global(.ultra-calendar) {
      height: 350px;
    }
  }
</style>

<PageUI on:pageAfterIn={pageAfterIn}>
  <svelte:fragment slot="fixed">
    <AttachmentsModal
      types={['Patient']}
      multiple={false}
      bind:instance={attachmentsPopover}
      on:change={({ detail }) => {
        openWizard('/wizard/visit/', {
          selectable,
          reference: detail[0],
        });
      }}
    />
    <AppBarUI
      navigation={{ close: quicklook, history: !quicklook }}
      {f7router}
    >
      <svelte:fragment slot="right">
        {#if !WIZARD_ACTIVE || !quicklook}     
          <MenuButtonUI
            title={language().buttons.edit.text}
            icon={{ f7: 'pencil_outline', color: 'gray' }}
            on:click={(event) => openPopover(attachmentsPopover, event)}
          />
        {/if}
      </svelte:fragment>
    </AppBarUI>
  </svelte:fragment>
  <RequestLayout {...$request} refetch={request.refetch} large middle>
    <ProfileUI drawer={{ visible: false }}>
      <h2 slot="title">{$visit.title}</h2>
      <svelte:fragment slot="tags">
      <Chip
        text={$visit.event.date}
        mediaBgColor="pink"
        outline
      >
        <span slot="media"><Icon f7="calendar" /></span>
      </Chip>
      </svelte:fragment>
    </ProfileUI>
    <Block style="margin-left: 25px;margin-right: 25px">
      <CollapsableContentUI title={language().icds.title.text}>
        <SpecificPluginGrid
          props={$visit.icds.map((icd) => ({
            id: icd.__typename,
            selectable: {
              id: icd.id,
              type: icd.__typename,
            },
            quicklook,
          }))}
          type={PluginTypes.Widget}
          fixed
        />
        <br />
      </CollapsableContentUI>
      <br />
      <CollapsableContentUI title={language().headers.description.text}>
        <p>{$visit.description ?? language().placeholders.noDescription.text}</p>
        <br />
      </CollapsableContentUI>
      <br />
      <CollapsableContentUI title={language().clients.people.title.text}>
        {#if [...$visit.event.patients, ...$visit.event.doctors].length > 0}
          <SpecificPluginGrid
            props={[...$visit.event.patients, ...$visit.event.doctors].map((person) => ({
              id: person.__typename,
              selectable: {
                id: ('pid' in person) ? person.pid.id : person.did.id,
                type: person.__typename,
              },
              quicklook,
            }))}
            type={PluginTypes.Widget}
            fixed
          />
        {:else}
          <CollapsableContentPlaceholderUI center>
            {language().clients.people.placeholders.notFound.text}
          </CollapsableContentPlaceholderUI>
        {/if}
        <div class="display-flex" slot="controls">
          <Button
            color="purple"
            small
            outline
            round
            disabled={[...$visit.event.patients, ...$visit.event.doctors].length < 2}
            on:click={() => safeNavigate(`/people/${JSON.stringify([...$visit.event.patients, ...$visit.event.doctors].map((person) => ({
              id: ('pid' in person) ? person.pid.id : person.did.id,
              type: person.__typename,
            })))}/`, AppViews.Clients)}
          >
            {language().buttons.viewAll.text}
          </Button>
        </div>
        <br />
      </CollapsableContentUI>
      <br />
    </Block>
  </RequestLayout>
  <br />
  <br />
</PageUI>
