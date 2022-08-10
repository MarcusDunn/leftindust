<script lang="ts">
  import type { Data } from '@/api/server/graphql';
  import type { Popover } from 'framework7/types';

  import type {
    PatientInput,
    NameInfo,
    PatientEditInput,
  } from '@/api/server/graphql/schema/leftindust.schema';

  import {
    Sex,
    Ethnicity,
    Month,
    Relationship,
    Country,
  } from '@/api/server/graphql/schema/leftindust.schema';
  import { WidgetType } from '../Widgets';

  import { writable } from 'svelte/store';
  import { _ } from '@/language';
  import { loginForm } from '../Account';
  import { closeWizard } from '../Wizard';

  import getNativeAPI from '@/api/bridge';
  import deepmerge from 'deepmerge';

  import { Row, Col, Block } from 'framework7-svelte';

  import Input from '../Input/Input.svelte';
  import Select from '../Input/components/Select/Select.svelte'
  import Wizard from '../Wizard/Wizard.svelte';
  import Add from '../Input/components/Add/Add.svelte';

  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { clientsSelectedTab } from '@/features/Clients/store';
  import { ClientsTab } from '@/features/Clients/index';

  export let data: Data<'Patient'> | undefined = undefined;

  const { Dialog } = getNativeAPI();

  let patient: any = writable();
  let disabled = true;

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

  let doctors = writable<Data[]>([]);
  
  let doctorsPopover: Popover.Popover;

  const edit = () => {
    input = deepmerge(input, {
      ...$patient,
      nameInfo: {
        firstName: $patient.firstName,
        middleName: $patient.middleName,
        lastName: $patient.lastName,
      },
    });
  };

  const submit = () => {};

  const { form, errors } = loginForm();
</script>

<form use:form>
  <Wizard
    title={$_("generics.newPatient")}
    subtitle={$_("descriptions.addPatientDescription")}
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
                <Input>
                  <input type="text" placeholder="First Name" bind:value={input.nameInfo.firstName} />
                </Input>
                <p />
              </Col>
              <Col width="100" medium="33">
                <Input>
                  <input type="text" placeholder="Middle Name (Optional)" bind:value={input.nameInfo.middleName} />
                </Input>
                <p />
              </Col>
              <Col width="100" medium="33">
                <Input>
                  <input type="text" placeholder="Last Name" bind:value={input.nameInfo.lastName} />
                </Input>
                <p />
              </Col>
              </Row>
              <Row>
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
                    bind:value={input.ethnicity}
                  />
                  <p />
                </Col>
                <Col width="100" medium="50">
                  <Input>
                    <input type="text" placeholder="Gender Identity (Optional)" bind:value={input.gender} />
                  </Input>
                  <p />
                </Col>
              </Row>
              <Col width="100">
                <Input>
                  <input type="text" placeholder="Insurance Number (Optional)" bind:value={input.insuranceNumber} />
                </Input>
                <p />
              </Col>
            </Col>
          </Row>
        </Block>
      </Block>
    {/key}
  </Wizard>
</form>