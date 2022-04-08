<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { Router, Popover } from 'framework7/types';
  import type { SelectableRouteParams } from '@/routes';
  import type { Doctor, Patient } from '@framework/schemas/leftindust.schema';
  import type { BasicDoctorFragment, BasicPatientFragment } from '@framework/requests/fragments';

  import { PluginTypes } from '@framework/plugins';
  import { WIZARD_ACTIVE } from '@framework/store';
  import { ClientsSelected } from '../../store/ClientsStore';
  import { SpecificEngine } from '@framework/engines';
  import { email } from '@framework/modules/ContactModule';
  import { openPopover } from '@framework/modules/NavigationModule';
  import { writable } from 'svelte/store';

  import { Block, Button, Icon } from 'framework7-svelte';

  import language from '@framework/languages';

  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
  import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import CollapsableContentUI
    from '@framework/ui/layout/CollapsableContentUI/CollapsableContentUI.svelte';

  import MissingEmailsModal from '@framework/components/modal/MissingEmailsModal.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';
  import SurveyTemplateAssignEngine from '@framework/engines/surveys/SurveyTemplateAssignEngine';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  let emails: string[] = [];
  let hasEmails = false;

  let missingEmailsPopover: Popover.Popover;

  const selectables: Selectable<Doctor['__typename'] | Patient['__typename']>[]
    = JSON.parse((<SelectableRouteParams>f7route.params).selectables);

  const patientSelectables = selectables.filter((selectable) => selectable.type === 'Patient');
  const doctorSelectables = selectables.filter((selectable) => selectable.type === 'Doctor');

  const people = SpecificEngine<BasicDoctorFragment | BasicPatientFragment>(selectables);

  const patientAttachments = writable<Selectable[]>([]);

  let patientsAttachmentsPopover: Popover.Popover;

  $: hasEmails = Object.keys($people).some((key) => $people[key].emails.length > 0);
  // eslint-disable-next-line @typescript-eslint/no-unsafe-return
  $: emails = Object.keys($people).flatMap((key) => $people[key].emails[0].email);

  const assignPatientAttachments = () => {
    if ($patientAttachments.length > 0) {
      $patientAttachments.forEach((attachment) => {
        if (attachment.type === 'GraphQLFormTemplate') {
          void SurveyTemplateAssignEngine({
            patients: patientSelectables.map((patient) => ({ id: patient.id })),
            survey: { id: attachment.id },
          });
        }
      });

      $patientAttachments = [];
    }
  };

</script>

<PageUI on:pageAfterIn={() => !$WIZARD_ACTIVE && ($ClientsSelected = selectables)}>
  {#key $people}
    <MissingEmailsModal
      selectables={selectables.filter((_, index) => $people[Object.keys($people)[index]]?.emails?.length > 0)}
      {emails}
      bind:instance={missingEmailsPopover}
    />
  {/key}
  <svelte:fragment slot="fixed">
    <AppBarUI
      navigation={{ history: true }}
      {f7router}
    >
      <svelte:fragment slot="right">
        {#if !$WIZARD_ACTIVE}
          <MenuButtonUI
            title={language().form.email.text}
            icon={{ f7: 'envelope_circle_fill', color: 'blue' }}
            disabled={emails.length === 0}
            on:click={(event) => {
              hasEmails ? email(emails) : openPopover(missingEmailsPopover, event);
            }}
          />
        {/if}
      </svelte:fragment>
    </AppBarUI>
  </svelte:fragment>

  <AttachmentsModal
    attachments={patientAttachments}
    types={['GraphQLFormTemplate', 'Doctor']}
    forceMainScreen
    bind:instance={patientsAttachmentsPopover}
    on:change={() => assignPatientAttachments()}
  />

  <Block class="no-margin-top">
    <Block style="margin-top: 30px">
      <h2>{selectables.length} Selected</h2>
      <br />
      {#if patientSelectables.length > 0}
        <CollapsableContentUI title={language().clients.patients.title.text}>
          <svelte:fragment slot="controls">
            <div on:click={(event) => openPopover(patientsAttachmentsPopover, event)}>
              <Button
                color="purple"
                small
                fill
                round
              >
                <Icon f7="link" />
                Attach
              </Button>
            </div>
          </svelte:fragment>
  
          <SpecificPluginGrid
            props={patientSelectables.map((selectable) => {
              if (selectable.type) {
                return {
                  id: selectable.type,
                  selectable: {
                    id: selectable.id,
                    type: selectable.type,
                  },
                };
              }
            })}
            type={PluginTypes.Widget}
          />
          <br />
        </CollapsableContentUI>
      {/if}
      {#if doctorSelectables.length > 0}
        <br />
        <CollapsableContentUI title={language().clients.doctors.title.text}>
          <SpecificPluginGrid
            props={doctorSelectables.map((selectable) => {
              if (selectable.type) {
                return {
                  id: selectable.type,
                  selectable: {
                    id: selectable.id,
                    type: selectable.type,
                  },
                };
              }
            })}
            type={PluginTypes.Widget}
          />
        </CollapsableContentUI>
      {/if}
    </Block>
  </Block>
</PageUI>
