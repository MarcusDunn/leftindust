<script lang="ts">
  import { _ } from '@/language';

  import {
    Ethnicity,
    Sex,
  } from '@/api/server/graphql/schema/leftindust.schema';

  import { Row, Col, Block } from 'framework7-svelte';

  import Input from '../Input/Input.svelte';
  import Select from '../Input/components/Select/Select.svelte';
  import Wizard from '../Wizard/Wizard.svelte';

  import { createPatientForm } from './';
  import DatePicker from '../Input/components/Date/DatePicker.svelte';
  import Phones from '../Input/components/Phone/Phones.svelte';
  import Emails from '../Input/components/Email/Emails.svelte';

  export let patientId: string | undefined = undefined;

  const { form, data: formData, handleSubmit, errors } = createPatientForm(patientId);

  let ref: HTMLFormElement;
</script>

<Wizard
  title={$_('generics.newPatient')}
  subtitle={$_('descriptions.addPatientDescription')}
  color="purple"
  on:submit={() => ref?.requestSubmit()}
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
                    placeholder="Birthday" 
                    pastOnly
                    value={new Date($formData.dateOfBirth).getTime()}
                    on:change={(e) => {
                      $formData.dateOfBirth = new Date(e.detail).toLocaleDateString('en-GB');
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
                    placeholder="Gender Identity"
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
        <h4>Contact</h4>
        <Row>
          <Col xlarge="50" width="100">
            <Phones title="Add Phone (Optional)" bind:value={$formData.phones} />
          </Col>
          <Col xlarge="50" width="100">
            <Emails title="Add Email (Optional)" bind:value={$formData.emails} />
          </Col>
        </Row>
      </Block>
    </Block>
  </form>
</Wizard>