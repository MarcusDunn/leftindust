<script lang="ts">
  import type { Data } from '@/api/server/graphql';

  import {
    Ethnicity,
  } from '@/api/server/graphql/schema/leftindust.schema';

  import { _ } from '@/language';

  import { Row, Col, Block } from 'framework7-svelte';

  import Input from '../Input/Input.svelte';
  import Select from '../Input/components/Select/Select.svelte'
  import Wizard from '../Wizard/Wizard.svelte';

  import { createPatientFormValidator } from './';

  export let data: Data<'Patient'> | undefined = undefined;

  const { form, data: formData, handleSubmit } = createPatientFormValidator();

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
                  <p />
                </Col>
                <Col width="100" medium="33">
                  <Input>
                    <input type="text" name="middleName" placeholder="Middle Name (Optional)" />
                  </Input>
                  <p />
                </Col>
                <Col width="100" medium="33">
                  <Input>
                    <input type="text" name="lastName" placeholder="Last Name" />
                  </Input>
                  <p />
                </Col>
              </Row>
              <Row class="align-items-flex-end">
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
                  <p />
                </Col>
                <Col width="100" medium="50">
                  <Input>
                    <input type="text" name="genderIdentity" placeholder="Gender Identity (Optional)" />
                  </Input>
                  <p />
                </Col>
              </Row>
              <Col width="100">
                <Input>
                  <input type="text" name="insuranceNumber" placeholder="Insurance Number (Optional)" />
                </Input>
                <p />
              </Col>
            </Col>
          </Row>
        </Block>
      </Block>
    </form>
  </Wizard>