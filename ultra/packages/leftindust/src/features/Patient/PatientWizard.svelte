<script lang="ts">
  import type { Data } from '@/api/server/graphql';

  import { _ } from '@/language';

  import {
    Ethnicity,
    Month,
    Sex,
  } from '@/api/server/graphql/schema/leftindust.schema';

  import { Row, Col, Block } from 'framework7-svelte';
  import patientMutateEngine from '../Engines/Patient/PatientMutateEngine';

  import Input from '../Input/Input.svelte';
  import Select from '../Input/components/Select/Select.svelte'
  import Wizard from '../Wizard/Wizard.svelte';

  import { createPatientForm } from './';

  export let data: Data<'Patient'> | undefined = undefined;

  const { form, data: formData, handleSubmit } = createPatientForm((form) => {
    patientMutateEngine({
      nameInfo: {
        firstName: form.firstName,
        lastName: form.lastName,
        middleName: form.middleName,
      },
      sex: form.sex,
      gender: form.genderIdentity,
      dateOfBirth: {
        day: form.day,
        month: form.month,
        year: form.year,
      },
      ethnicity: form.ethnicity
    })
  });

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
      <Block style="margin-top: 60px">
        <Block>
          <h4>Identification</h4>
          <Row>
            <Col xlarge="50" width="100">
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
                </Col>
              </Row>
              <Row class="align-items-flex-end">
                <Col width="33">
                  <Select
                    title="Date of Birth"
                    options={[
                      {
                        text: 'January',
                        value: Month.Jan,
                      },
                      {
                        text: 'February',
                        value: Month.Feb,
                      },
                      {
                        text: 'March',
                        value: Month.Mar,
                      },
                      {
                        text: 'April',
                        value: Month.Apr,
                      },
                      {
                        text: 'May',
                        value: Month.May,
                      },
                      {
                        text: 'June',
                        value: Month.Jun,
                      },
                      {
                        text: 'July',
                        value: Month.Jul,
                      },
                      {
                        text: 'August',
                        value: Month.Aug,
                      },
                      {
                        text: 'September',
                        value: Month.Sep,
                      },
                      {
                        text: 'October',
                        value: Month.Oct,
                      },
                      {
                        text: 'November',
                        value: Month.Nov,
                      },
                      {
                        text: 'December',
                        value: Month.Dec,
                      },
                    ]}
                    bind:value={$formData.month}
                    />
                </Col>
                <Col width="33">
                  <Input>
                    <input type="number" name="day" placeholder="Day" />
                  </Input>
                </Col>
                <Col width="33">
                  <Input>
                    <input type="number" name="year" placeholder="Year" />
                  </Input>
                </Col>
              </Row>
              <Row>
                <Col width="100">
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