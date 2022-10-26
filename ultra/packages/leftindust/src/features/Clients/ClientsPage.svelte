<script lang="ts">
  import type { Router, Popover } from 'framework7/types';
  import { _ } from '@/language';

  import { clientsSearchQuery, clientsSelectedTab	} from './store';

  import {
    Tabs,
    Tab,
    Segmented,
    Button,
    Icon,
    Searchbar,
    PageContent,
  } from 'framework7-svelte';

  import { wizardOpen } from '@/features/Wizard/store';

  import { openPopover } from '@/features/View';
  import { ClientsTab } from '.';  

  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import Menu from '@/features/UI/components/Menu/Menu.svelte';
  import { openWizard } from '../Wizard';

  import PatientsTab from '@/features/Patients/components/PatientsTab/PatientsTab.svelte';
  import DoctorsTab from '../Doctors/components/DoctorsTab/DoctorsTab.svelte';
  import { operationStore } from '@urql/svelte';
  import { defaultRangeInput, PartialDoctorsByDoctorIdQueryDocument, PartialDoctorsByRangeQueryDocument, PartialPatientsByPatientIdQueryDocument, PartialPatientsByRangeQueryDocument } from '@/api/server';
  import { getTimestampedValues } from '../Recents';
  import { account } from '../Account/store';
  import { isDoctorSelected } from '../Doctors/store';
  

  export let f7router: Router.Router;

  let createMenuRef: Popover.Popover;

  const patientsRequest = operationStore(PartialPatientsByRangeQueryDocument, {
    range: defaultRangeInput,
  });

  const patientsRecentsRequest = operationStore(PartialPatientsByPatientIdQueryDocument, {
    patientIds: getTimestampedValues($account.database.recents.Patient ??= {})
      .map(id => ({ value: id }))
      .filter((value) => value != undefined),
  });

  const doctorsRequest = operationStore(PartialDoctorsByRangeQueryDocument, {
    range: defaultRangeInput,
  });

  const doctorsRecentsRequest = operationStore(PartialDoctorsByDoctorIdQueryDocument, {
    doctorIds: getTimestampedValues($account.database.recents.Doctor ??= [])
      .map((id) => ({ value: id }))
      .filter((value) => value != undefined),
  });


</script>

<Page style="overflow: hidden" pageContent={false}>
  <svelte:fragment slot="fixed">
    <Menu
      items={[
        {
          title: $_('generics.patient'),
          text: $_('descriptions.addPatient'),
          icon: {
            f7: 'person_2_alt',
            color: 'purple',
          },
          onClick: () => openWizard('/wizard/patient/', {
            callback: () => {
              patientsRequest.reexecute();
              patientsRecentsRequest.reexecute();
            },
          }),
        },
        {
          title: $_('generics.doctor'),
          text: $_('descriptions.addDoctor'),
          icon: {
            f7: 'briefcase_fill',
            color: 'blue',
          },
          onClick: () => {
            $isDoctorSelected = false;
            return openWizard('/wizard/doctor/', {
              callback: () => {
                doctorsRequest.reexecute();
                doctorsRecentsRequest.reexecute();
              },
            });
          },
        },
      ]}
      bind:instance={createMenuRef}
    />
	
    <AppBar>
      <Searchbar
        class="color-purple disabled"
        customSearch
        inline
        disableButton={false}
        placeholder={($clientsSelectedTab === ClientsTab.Patients && $_('generics.patientsSearch')
          || $clientsSelectedTab === ClientsTab.Doctors && $_('generics.doctorsSearch')) || ''}
        bind:value={$clientsSearchQuery}
      />
      <svelte:fragment slot="right">
        <MenuButton
          title={$_('generics.filter')}
          icon={{ f7: 'line_horizontal_3_decrease_circle_fill' }}
          disabled
        />
        {#if !$wizardOpen}
          <MenuButton
            title={$_('generics.create')}
            icon={{ f7: 'plus_circle_fill', color: 'purple' }}
            on:click={(event) => { openPopover(createMenuRef, event); }}
          />
        {/if}
      </svelte:fragment>
      <svelte:fragment slot="subnavbar">
        <Segmented strong tag="p">
          <Button
            active={$clientsSelectedTab === ClientsTab.Patients}
            on:click={() => $clientsSelectedTab = ClientsTab.Patients}
          >
            <Icon f7="person_2_alt" color="purple"/>
            {$_('generics.patients')}
          </Button>
          <Button
            active={$clientsSelectedTab === ClientsTab.Doctors}
            on:click={() => $clientsSelectedTab = ClientsTab.Doctors}
          >
            <Icon f7="briefcase_fill" color="blue"/>
            {$_('generics.doctors')}
          </Button>
        </Segmented>
      </svelte:fragment>
    </AppBar>
  </svelte:fragment>
  <PageContent style="padding: 0; overflow: hidden">
    <Tabs style="height: 100%">
      <Tab tabActive={$clientsSelectedTab === ClientsTab.Patients}>
        <PatientsTab request={patientsRequest} recentsRequest={patientsRecentsRequest} {f7router} />
      </Tab>
      <Tab tabActive={$clientsSelectedTab === ClientsTab.Doctors}>
        <DoctorsTab request={doctorsRequest} recentsRequest={doctorsRecentsRequest} {f7router} />
      </Tab>
    </Tabs>
  </PageContent>
</Page>
