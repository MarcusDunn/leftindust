<script lang="ts">
	import type { Router, Popover } from 'framework7/types';
	import language from '@framework/languages/index';

	import {
	  ClientsSearchQuery,
	  ClientsSelectedTab,
	  ClientsTab,
	  DoctorsSelectedAttachments,
	  DoctorsSelectedAttachmentsFragments,
	  PatientsSelectedAttachments,
	  PatientsSelectedAttachmentsFragments,
	} from '@/pages/clients/store/ClientsStore';

	import {
	  Tabs,
	  Tab,
	  Segmented,
	  Button,
	  Icon,
	  Searchbar,
	} from 'framework7-svelte';
	import { WIZARD_ACTIVE } from '@framework/store';

	import { openPopover, openWizard } from '@framework/modules/NavigationModule';

	import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
	import MenuButtonUI from '@framework/ui/button/MenuButtonUI/MenuButtonUI.svelte';
	import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';
	import MenuUI from '@framework/ui/controller/MenuUI/MenuUI.svelte';

	import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';

	import PatientsTab from '@/pages/clients/tabs/patients/PatientsTab.svelte';
	import DoctorsTab from './tabs/doctors/DoctorsTab.svelte';

	export let f7router: Router.Router;
	export let f7route: Router.Route;

	let patientsAttachmentsPopover: Popover.Popover;
	let doctorsAttachmentsPopover: Popover.Popover;
	
	let createMenu: Popover.Popover;

</script>

<PageUI style="overflow: hidden">
	<svelte:fragment slot="fixed">
		<MenuUI
      items={[
        {
          title: language().clients.patients.headers.patient.text,
          text: language().clients.patients.placeholders.addPatientDescription.text,
          icon: {
            f7: 'person_2_alt',
            color: 'purple',
          },
					onClick: () => openWizard('/wizard/patient/'),
        },
        {
          title: language().clients.doctors.headers.doctor.text,
          text: language().clients.doctors.placeholders.addDoctorDescription.text,
          icon: {
            f7: 'briefcase_fill',
            color: 'blue',
          },
					onClick: () => openWizard('/wizard/doctor/'),
        },
      ]}
      bind:instance={createMenu}
    />
		<AttachmentsModal
			attachments={PatientsSelectedAttachments}
			types={['IcdLinearizationEntity']}
			forceMainScreen
			bind:instance={patientsAttachmentsPopover}
			on:fragment={({ detail }) => {
				$PatientsSelectedAttachmentsFragments = detail;
			}}
		/>
		<AttachmentsModal
			attachments={DoctorsSelectedAttachments}
			types={['Patient']}
			forceMainScreen
			bind:instance={doctorsAttachmentsPopover}
			on:fragment={({ detail }) => {
				$DoctorsSelectedAttachmentsFragments = detail;
			}}
		/>
		<AppBarUI>
			<Searchbar
				class="color-purple"
				customSearch
				inline
				disableButton={false}
				placeholder={($ClientsSelectedTab === ClientsTab.Patients && language().clients.patients.search.text
					|| $ClientsSelectedTab === ClientsTab.Doctors && language().clients.doctors.search.text) || ''}
				bind:value={$ClientsSearchQuery}
			/>
			<svelte:fragment slot="right">
				<MenuButtonUI
					title={language().buttons.filter.text}
					icon={{ f7: 'line_horizontal_3_decrease_circle_fill' }}
					on:click={(event) => openPopover(
						$ClientsSelectedTab === ClientsTab.Patients
							? patientsAttachmentsPopover : doctorsAttachmentsPopover, event,
						)
					}
					disabled={$ClientsSelectedTab === ClientsTab.Doctors}
				/>
				{#if !$WIZARD_ACTIVE}
					<MenuButtonUI
						title={language().buttons.create.text}
						icon={{ f7: 'plus_circle_fill', color: 'purple' }}
						on:click={(event) => { openPopover(createMenu, event); }}
					/>
				{/if}
			</svelte:fragment>
			<svelte:fragment slot="subnavbar">
				<Segmented strong tag="p">
					<Button
						active={$ClientsSelectedTab === ClientsTab.Patients}
						on:click={() => $ClientsSelectedTab = ClientsTab.Patients}
					>
						<Icon f7="person_2_alt" color="purple"/>
						{language().clients.patients.title.text}
					</Button>
					<Button
						active={$ClientsSelectedTab === ClientsTab.Doctors}
						on:click={() => $ClientsSelectedTab = ClientsTab.Doctors}
					>
						<Icon f7="briefcase_fill" color="blue"/>
						{language().clients.doctors.title.text}
					</Button>
				</Segmented>
			</svelte:fragment>
		</AppBarUI>
	</svelte:fragment>
	<Tabs style="height: 100%">
		<Tab tabActive={$ClientsSelectedTab === ClientsTab.Patients}>
			<PatientsTab {f7router} {f7route}/>
		</Tab>
		<Tab tabActive={$ClientsSelectedTab === ClientsTab.Doctors}>
			<DoctorsTab {f7router} {f7route} />
		</Tab>
	</Tabs>
</PageUI>
