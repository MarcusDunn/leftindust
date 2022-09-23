<script lang="ts">
  import type { Selectable } from '@framework/modules/SelectModule';
  import type { Popover } from 'framework7/types';

  import type {
    PatientInput,
    NameInfo,
    PatientEditInput,
  } from '@framework/schemas/leftindust.schema';
  import type { PatientEngine as PatientEngineType } from '@framework/engines/patients/PatientEngine';
  
  import {
    Sex,
    Ethnicity,
    Month,
    Relationship,
    Country,
  } from '@framework/schemas/leftindust.schema';
  import { PluginTypes } from '@framework/plugins';
  
  import { writable } from 'svelte/store';
  import language from '@framework/languages';
  import { closeWizard, openPopover } from '@framework/modules/NavigationModule';
  import PatientEngine from '@framework/engines/patients/PatientEngine';
  
  import { ClientsSelectedTab, ClientsTab } from '../../store/ClientsStore';
  import PatientMutateEngine from '@framework/engines/patients/PatientMutateEngine';
  import getNativeAPI from '@framework/bridge';
  
  import deepmerge from 'deepmerge';
  import { Row, Col, Block } from 'framework7-svelte';

  import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';
  import SelectUI from '@framework/ui/input/SelectUI/SelectUI.svelte';
  import WizardUI from '@framework/ui/layout/WizardUI/WizardUI.svelte';
  import DateUI from '@framework/ui/input/DateUI/DateUI.svelte';
  import PhonesUI from '@framework/ui/input/PhoneUI/PhonesUI.svelte';
  import EmailsUI from '@framework/ui/input/EmailUI/EmailsUI.svelte';
  import AddressesUI from '@framework/ui/input/AddressUI/AddressesUI.svelte';
  import AddUI from '@framework/ui/input/AddUI/AddUI.svelte';

  import ContactsInput from '@framework/components/input/contact/ContactsInput.svelte';
  import AttachmentsModal from '@framework/components/modal/AttachmentsModal.svelte';
  import SpecificPluginGrid from '@framework/components/grid/SpecificPluginGrid.svelte';

  export let selectable: Selectable<'Patient'> | undefined = undefined;

  const { Dialog } = getNativeAPI();

  let patient: PatientEngineType['patient'] = writable();
  let disabled = true;

  if (selectable) {
    const engine = PatientEngine({
      pids: [{ id: selectable.id }],
    });

    patient = engine.patient;
  }

  let input: Partial<PatientInput | PatientEditInput> & { nameInfo: NonNullable<NameInfo>} = {
    nameInfo: {
      firstName: '',
      middleName: '',
      lastName: '',
    },
    ethnicity: undefined,
    dateOfBirth: undefined,
    sex: undefined,
    gender: undefined,
    insuranceNumber: '',
    addresses: [],
    doctors: [],
    emails: [],
    emergencyContacts: [],
    phones: [],
  };

  let doctors = writable<Selectable[]>([]);
  
  let doctorsPopover: Popover.Popover;

  const edit = () => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { firstName, middleName, lastName, ...stripped } = deepmerge(input, {
      ...$patient,
      nameInfo: {
        firstName: $patient.firstName,
        middleName: $patient.middleName,
        lastName: $patient.lastName,
      },
      doctors: $patient.doctors.map((doctor) => ({ id: doctor.did?.id })),
    });

    $doctors = $patient.doctors.map((doctor) => ({
      type: doctor.__typename,
      id: doctor.did?.id,
    }));

    input = stripped;
  };

  const submit = () => {
    void PatientMutateEngine(<PatientInput>input, !!selectable).then((result) => {
      result ? (() => {
        closeWizard();
        $ClientsSelectedTab = ClientsTab.Patients;
      })() : void Dialog.alert({
        message: language().errors.internalError.text,
        detail: language().errors.request.text,
        buttons: [language().buttons.ok.text],
      });
    });
  };
  
  $: disabled = !(input.nameInfo.firstName
    && input.nameInfo.lastName
    && input.dateOfBirth
    && (input.ethnicity && Object.values(Ethnicity).includes(input.ethnicity))
    && (input.sex && Object.values(Sex).includes(input.sex))
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
    && (input.emergencyContacts?.length === 0
      || input.emergencyContacts?.some(
        ({ firstName, lastName, relationship, emails, phones }) => firstName
          && lastName
          && Object.values(Relationship).includes(relationship)
          && (emails?.length === 0 || emails?.some((email) => email.email && email.type))
          && (phones?.length === 0 || phones?.some((phone) => phone.number && phone.type)),
      )
    )
  );

  $: input = {
    ...input,
    doctors: $doctors.map((doctor) => ({ id: doctor.id })),
  };

  $: if ($patient) edit();
  
</script>

<AttachmentsModal
  attachments={doctors}
  types={['Doctor']}
  bind:instance={doctorsPopover}
/>

<WizardUI
  title={selectable ? language().clients.patients.headers.editPatient.text : language().clients.patients.headers.newPatient.text}
  subtitle={selectable ?
    language().clients.patients.placeholders.editPatientDescription(`${$patient?.firstName} ${$patient?.lastName}`).text
    : language().clients.patients.placeholders.addPatientDescription.text
  }
  color="purple"
  {disabled}
  on:submit={submit}
>
  {#key $patient}
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

              <Col width="100" medium="50">
                <SelectUI
                  title="Ethnicity"
                  options={[
                    {
                      text: 'American Aboriginal',
                      value: Ethnicity.AmericanAboriginal,
                    },
                    {
                      text: 'Asian',
                      value: Ethnicity.Asian,
                    },
                    {
                      text: 'Black',
                      value: Ethnicity.Black,
                    },
                    {
                      text: 'Hispanic',
                      value: Ethnicity.Hispanic,
                    },
                    {
                      text: 'Pacific Islander',
                      value: Ethnicity.PacificIslander,
                    },
                    {
                      text: 'White',
                      value: Ethnicity.White,
                    },
                  ]}
                  bind:value={input.ethnicity}
                />
                <p />
              </Col>
              <Col width="100" medium="50">
                <DateUI
                  placeholder="Birthday"
                  header="Select Birthday"
                  pastOnly
                  value={$patient ? new Date(
                      `${$patient.dateOfBirth.month} ${$patient.dateOfBirth.day} ${$patient.dateOfBirth.year}`,
                    ).getTime() : undefined
                  }
                  on:change={({ detail }) => {
                    const date = new Date(detail);

                    const day = date.getDate();
                    const month = Object.values(Month)[date.getMonth()];
                    const year = date.getFullYear();

                    input.dateOfBirth = {
                      day,
                      month,
                      year,
                    };
                  }}
                />
                <p />
              </Col>
            </Row>
          </Col>
          <Col xlarge="50" width="100">
            <Row>
              <Col width="100" medium="50">
                <SelectUI
                  title="Sex"
                  options={[
                    {
                      text: 'Male',
                      value: Sex.Male,
                    },
                    {
                      text: 'Female',
                      value: Sex.Female,
                    },
                    {
                      text: 'Intersex',
                      value: Sex.Intersex,
                    },
                  ]}
                  bind:value={input.sex}
                />
                <p />
              </Col>
              <Col width="100" medium="50">
                <InputUI>
                  <input type="text" placeholder="Gender Identity (Optional)" bind:value={input.gender} />
                </InputUI>
                <p />
              </Col>

              <Col width="100">
                <InputUI>
                  <input type="text" placeholder="Insurance Number (Optional)" bind:value={input.insuranceNumber} />
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
        <h4>Doctors</h4>
        {#if $doctors.length > 0}
          {#key $doctors}
            <SpecificPluginGrid
              props={$doctors.map((selectable) => {
                if (selectable.type) {
                  return {
                    id: selectable.type,
                    selectable: {
                      id: selectable.id,
                      type: selectable.type,
                    },
                    attachments: doctors,
                  };
                }
              })}
              type={PluginTypes.Widget}
              dynamicGap
            />
          {/key}
        {/if}
        <AddUI title="Attach Doctors (Optional)" on:click={(event) => openPopover(doctorsPopover, event)} />
        <p />
        <br />
        <h4>Emergency Contacts</h4>
        <ContactsInput bind:value={input.emergencyContacts} />
        <p />
      </Block>
    </Block>
  {/key}
</WizardUI>