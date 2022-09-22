<script lang="ts">
  import type { Data, PatientQueryQuery } from '@/api/server';

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
  import { operationStore } from '@urql/svelte';
  import { writable } from 'svelte/store';
  import { queryPatient } from '../Engines/Patient/PatientQueryEngine'
  import { onMount } from 'svelte';

  let patient: Patient;

  const { form, data: formData, handleSubmit } = createPatientForm((form) => {
    console.log(`submitted: ${form}`);
    mutateAddPatient({
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
    });
  });

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  $: console.log(`patient ${JSON.stringify(patient)}`);
  $: console.log(`formData ${JSON.stringify(formData)}`)

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
                    placeholder="Birth Date"
                    title="Select Birth Date"
                    pastOnly
                    value={
                      patient
                        ? new Date(formData.dateOfBirth.toUtcTime.unixMilliseconds).getTime()
                        : undefined
                    }
                    on:change={({ detail }) => {
                      const date = new Date(detail);

                      const day = date.getDate();
                      const month = Object.values(Month)[date.getMonth()];
                      const year = date.getFullYear();

                      formData.dateOfBirth = {
                        day,
                        month,
                        year
                      };
                    }}
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