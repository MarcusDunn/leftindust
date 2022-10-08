<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { Popover } from 'framework7/types';

  import type { NameInfo, DoctorInput } from '@framework/schemas/leftindust.schema';
  import type { DoctorEngine as DoctorEngineType } from '@framework/engines/doctors/DoctorEngine';
  
  import { Country } from '@framework/schemas/leftindust.schema';
  import { PluginTypes } from '@framework/plugins';
  
  import { writable } from 'svelte/store';
  import language from '@framework/languages';
  import { closeWizard, openPopover } from '@framework/modules/NavigationModule';
  import DoctorEngine from '@framework/engines/doctors/DoctorEngine';
  
  import DoctorMutateEngine from '@framework/engines/doctors/DoctorMutateEngine';
  import getNativeAPI from '@framework/bridge';
  import deepmerge from 'deepmerge';
  
  import { Row, Col, Block } from 'framework7-svelte';

  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';
  import WizardUI from '@framework/ui/layout/WizardUI/WizardUI.svelte';
  import PhonesUI from '@framework/ui/input/PhoneUI/PhonesUI.svelte';
  import EmailsUI from '@framework/ui/input/EmailUI/EmailsUI.svelte';
  import AddressesUI from '@framework/ui/input/AddressUI/AddressesUI.svelte';
  import AddUI from '@framework/ui/input/AddUI/AddUI.svelte';

  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';
  import { ClientsSelectedTab, ClientsTab } from '../../store/ClientsStore';

  export let selectable: Selectable<'Doctor'> | undefined = undefined;

  const { Dialog } = getNativeAPI();

  let doctor: DoctorEngineType['doctor'] = writable();
  let disabled = true;

  if (selectable) {
    const engine = DoctorEngine({
      dids: [{ id: selectable.id }],
    });

    doctor = engine.doctor;
  }

  let input: Partial<DoctorInput> & { nameInfo: NonNullable<NameInfo>} = {
    nameInfo: {
      firstName: '',
      middleName: '',
      lastName: '',
    },
    title: '',
    addresses: [],
    patients: [],
    emails: [],
    phones: [],
  };

  let patients = writable<Selectable[]>([]);
  
  let patientsPopover: Popover.Popover;

  const edit = () => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { firstName, middleName, lastName, user, ...stripped } = deepmerge(input, {
      ...$doctor,
      nameInfo: {
        firstName: $doctor.firstName,
        middleName: $doctor.middleName,
        lastName: $doctor.lastName,
      },
    });

    $patients = $doctor.patients.map((patient) => ({
      type: patient.__typename,
      id: patient.id.value,
    }));

    input = stripped;
  };

  const submit = () => {
    void DoctorMutateEngine(<DoctorInput>input, !!selectable).then((result) => {
      result ? (() => {
        closeWizard();
        $ClientsSelectedTab = ClientsTab.Doctors;
      })() : void Dialog.alert({
        message: language().errors.internalError.text,
        detail: language().errors.request.text,
        buttons: [language().buttons.ok.text],
      });
    });
  };

  $: disabled = !(input.nameInfo.firstName
    && input.nameInfo.lastName
    && (input.emails?.length === 0 || input.emails?.some((email) => email.email && email.type))
    && (input.phones?.length === 0 || input.phones?.some((phone) => phone.number && phone.type))
    && (input.addresses?.length === 0
        || input.addresses?.some(
          ({ address, addressType, city, country, postalCode }) => address
            && addressType
            && city
            && Object.values(Country).includes(country)
            && postalCode,
        )
    )
  );

  $: input = {
    ...input,
    patients: $patients.map((patient) => ({ id: patient.id })),
  };

  $: if ($doctor) edit();
  
</script>

<AttachmentsModal
  attachments={patients}
  types={['Patient']}
  bind:instance={patientsPopover}
/>

<WizardUI
  title={selectable ? language().clients.doctors.headers.editDoctor.text : language().clients.doctors.headers.newDoctor.text}
  subtitle={selectable ?
    language().clients.doctors.placeholders.editDoctorDescription(input.nameInfo.lastName).text
    : language().clients.doctors.placeholders.addDoctorDescription.text
  }
  color="purple"
  {disabled}
  on:submit={submit}
>
  <Block style="margin-top: 60px">
    <Block>
      <h4>Identification</h4>
      <Row>
        <Col xlarge="50" width="100">
          <Row>
            <Col width="100" medium="33">
              <InputUI>
                <input type="text" placeholder="First Name" bind:value={input.nameInfo.firstName} />
              </InputUI>
              <p />
            </Col>
            <Col width="100" medium="33">
              <InputUI>
                <input type="text" placeholder="Middle Name (Optional)" bind:value={input.nameInfo.middleName} />
              </InputUI>
              <p />
            </Col>
            <Col width="100" medium="33">
              <InputUI>
                <input type="text" placeholder="Last Name" bind:value={input.nameInfo.lastName} />
              </InputUI>
              <p />
            </Col>
          </Row>
        </Col>
        <Col xlarge="50" width="100">
          <Row>
            <Col width="100">
              <InputUI>
                <input type="text" placeholder="Title" bind:value={input.title} />
              </InputUI>
              <p />
            </Col>
          </Row>
        </Col>
      </Row>
      <br />
      <h4>Contact</h4>
      <Row>
        <Col xlarge="50" width="100">
          <PhonesUI title="Add Phone (Optional)" bind:value={input.phones} />
          <p />
        </Col>
        <Col xlarge="50" width="100">
          <EmailsUI title="Add Email (Optional)" bind:value={input.emails} />
          <p />
        </Col>
      </Row>
      <br />
      <h4>Address</h4>
      <AddressesUI title="Add Address (Optional)" bind:value={input.addresses} />
      <p />
      <br />
      <h4>Patients</h4>
      {#if $patients.length > 0}
        {#key $patients}
          <SpecificPluginGrid
            props={$patients.map((selectable) => {
              if (selectable.type) {
                return {
                  id: selectable.type,
                  selectable: {
                    id: selectable.id,
                    type: selectable.type,
                  },
                  attachments: patients,
                };
              }
            })}
            type={PluginTypes.Widget}
            dynamicGap
          />
        {/key}
      {/if}
      <AddUI title="Attach Patients (Optional)" on:click={(event) => openPopover(patientsPopover, event)} />
      <p />
    </Block>
  </Block>
</WizardUI>