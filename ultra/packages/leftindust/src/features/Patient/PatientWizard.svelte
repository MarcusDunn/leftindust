<script lang="ts">
  import { _ } from '@/language';

  import {
    Ethnicity,
    Sex,
  } from '@/api/server/graphql/schema/leftindust.schema';
  import type { PatientFragment } from '@/api/server';

  import { Row, Col, Block } from 'framework7-svelte';

  import Input from '../Input/Input.svelte';
  import Select from '../Input/components/Select/Select.svelte';
  import Wizard from '../Wizard/Wizard.svelte';

  import { createPatientForm } from './';
  import DatePicker from '../Input/components/Date/DatePicker.svelte';
  import Phones from '../Input/components/Phone/Phones.svelte';
  import Emails from '../Input/components/Email/Emails.svelte';
  import Addresses from '../Input/components/Address/Addresses.svelte';
  import { closeWizard } from '../Wizard';

  export let patient: PatientFragment | undefined;
  export let callback: () => void;
  
  const closeWizardHandler = () => {
    reset();
    callback();
    closeWizard();
  };
  
  const { form, data: formData, handleSubmit, errors, reset, interacted } = createPatientForm(closeWizardHandler, patient);
  $: patientDob = new Date($formData?.dateOfBirth).getTime(); 

  let ref: HTMLFormElement;
</script>

<Wizard
  title={patient ? $_('generics.editPatient') : $_('generics.newPatient')}
  subtitle={patient ? $_('descriptions.editPatientDescription') : $_('descriptions.addPatientDescription')}
  color="purple"
  interacted={!!$interacted}
  on:submit={() => ref?.requestSubmit()}
  on:close={closeWizardHandler}
>
  <form use:form on:submit={handleSubmit} bind:this={ref}>
    <Block style="margin-top: 100px">
      <Block>
        <h4>Identification</h4>
        <Row>
          <Col width="100">
            <Row>              
              <Col width="100" medium="33">
                <Input error={$errors.nameInfo.firstName}>
                  <input type="text" name="nameInfo.firstName" placeholder="First Name" />
                </Input>
              </Col>
              <Col width="100" medium="33">
                <Input error={$errors.nameInfo.middleName}>
                  <input type="text" name="nameInfo.middleName" placeholder="Middle Name (Optional)" />
                </Input>
              </Col>
              <Col width="100" medium="33">
                <Input error={$errors.nameInfo.lastName}>
                  <input type="text" name="nameInfo.lastName" placeholder="Last Name" />
                </Input>
              </Col>
              <Col width="100" medium="50">
                <p />
                <Select
                  placeholder="Ethnicity"
                  error={$errors.ethnicity}
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
                  bind:value={$formData.ethnicity}
                />
              </Col>
              <Col width="100" medium="50">
                <div style="margin-top: 2px;">
                  <DatePicker
                    value={patient ? patientDob : undefined}
                    placeholder="Birthday"
                    error={$errors.dateOfBirth}
                    pastOnly
                    on:change={(e) => {
                      $formData.dateOfBirth = new Date(e.detail).toLocaleDateString('en-ca',  {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                      });
                    }}
                  />
                </div>
              </Col>
            </Row>
            <Row>
              <Col width="50">
                <p />
                <Select
                  placeholder="Sex"
                  error={$errors.sex}
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
                  bind:value={$formData.sex}
                />
              </Col>
              <Col width="50">
                <Input style="margin-top: 2px" error={$errors.gender}>
                  <input
                    type="text"
                    name="gender"
                    placeholder="Gender Identity (Optional)"
                  />
                </Input>
              </Col>
            </Row>
            <Col width="100">
              <Input error={$errors.insuranceNumber}>
                <input type="text" name="insuranceNumber" placeholder="Insurance Number (Optional)" />
              </Input>
            </Col>
          </Col>
        </Row>
        <br />
        <br />
        <h4 style="margin-bottom: 10px">Contact</h4>
        <Row>
          <Col xlarge="50" width="100">
            <Phones
              title="Add Phone (Optional)"
              errors={$errors.phones}
              bind:value={$formData.phones}
            />
            <p />
          </Col>
          <Col xlarge="50" width="100">
            <Emails
              title="Add Email (Optional)"
              errors={$errors.emails}
              bind:value={$formData.emails}
            />
            <p />
          </Col>
        </Row>
        <br />
        <h4 style="margin-bottom: 10px">Address</h4>
        <Addresses
          title="Add Address (Optional)"
          errors={$errors.addresses}
          bind:value={$formData.addresses}
        />
        <p />
      </Block>
    </Block>
  </form>
</Wizard>