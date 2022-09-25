<script lang="ts">
  import type { Data } from '@/api/server';

  import { _ } from '@/language';

  import {
    Ethnicity,
    Month,
    Sex,
    type Patient,
  } from '@/api/server/graphql/schema/leftindust.schema';

  import { Row, Col, Block } from 'framework7-svelte';
  import { mutateAddPatient } from '../Engines/Patient/PatientMutateEngine';

  import Input from '../Input/Input.svelte';
  import Select from '../Input/components/Select/Select.svelte'
  import Wizard from '../Wizard/Wizard.svelte';

  import { createPatientForm } from './';
  import DatePicker from '../Input/components/Date/DatePicker.svelte';
  import type { Router } from 'framework7/types';

  const { form, data: formData, handleSubmit } = createPatientForm((form) => {
    const dateOfBirth = form.dateOfBirth;
    if(!dateOfBirth || !dateOfBirth.day || !dateOfBirth.month || !dateOfBirth.year) throw new Error('Date of birth is not filled in');

    mutateAddPatient({
      nameInfo: {
        firstName: form.firstName,
        lastName: form.lastName,
        middleName: form.middleName,
      },
      sex: form.sex,
      gender: form.genderIdentity,
      dateOfBirth: {
        day: dateOfBirth.day,
        month: dateOfBirth.month,
        year: dateOfBirth.year,
      },
      ethnicity: form.ethnicity
    });
  });

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  let dateOfBirthTime: number | undefined;
  let dateOfBirthDate: Date;
  
  // DatePicker sets the date as a millisecond number, here we convert to a date when it is updated
  $: if (dateOfBirthTime) {
    dateOfBirthDate = new Date(dateOfBirthTime);
    const month = Object.values(Month).at(dateOfBirthDate.getMonth());
    if(!month) throw new Error(`Could not convert number ${dateOfBirthDate.getMonth()} to a month`);
    $formData.dateOfBirth = {
      day: dateOfBirthDate.getDate(),
      month: month,
      year: dateOfBirthDate.getFullYear()
    };
  }

  let ref: HTMLFormElement;
</script>

<Wizard
  title={$_("generics.newPatient")}
  subtitle={$_("descriptions.addPatientDescription")}
  color="purple"
  disabled={false}
  on:submit={() => ref?.requestSubmit()}
>
  <form use:form on:submit="{handleSubmit}" bind:this="{ref}">
      <Block style="margin-top: 100px">
        <Block>
          <h4>Identification</h4>
          <Row>
            <Col width="100">
              <Row>              
                <Col width="100" medium="33">
                  <Input>
                    <input type="text" name="firstName" placeholder="First Name" />
                  </Input>
                </Col>
                <Col width="100" medium="33">
                  <Input>
                    <input type="text" name="middleName" placeholder="Middle Name (Optional)" />
                  </Input>
                </Col>
                <Col width="100" medium="33">
                  <Input>
                    <input type="text" name="lastName" placeholder="Last Name" />
                  </Input>
                  <p />
                </Col>
                <Col width="100" medium="50">
                  <Select
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
                    bind:value={$formData.ethnicity}
                  />
                </Col>
                <Col width="100" medium="50">
                  <DatePicker 
                    title="Birth Date" 
                    placeholder="Select Birth Date" 
                    pastOnly
                    bind:value={dateOfBirthTime}
                  />
                </Col>
              </Row>
              <Row class="align-items-flex-end">
                <Col width="50">
                  <Select
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
                    bind:value={$formData.sex}
                  />
                </Col>
                <Col width="50">
                  <Input>
                    <input type="text" name="genderIdentity" placeholder="Gender Identity (Optional)" />
                  </Input>
                </Col>
              </Row>
              <Col width="100">
                <p />
                <Input>
                  <input type="text" name="insuranceNumber" placeholder="Insurance Number (Optional)" />
                </Input>
              </Col>
            </Col>
          </Row>
        </Block>
      </Block>
    </form>
  </Wizard>