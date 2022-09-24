<script lang="ts">
  import type { Data } from '@/api/server/graphql';
  
  import { _ } from '@/language';

  import { Row, Col, Block } from 'framework7-svelte';
  import doctorMutateEngine from '../Engines/Doctor/DoctorMutateEngine';

  import Input from '../Input/Input.svelte';
  import Wizard from '../Wizard/Wizard.svelte';
  
  import { createDoctorForm } from './';

  export let data: Data<'Doctor'> | undefined = undefined;

  const { form, data: formData, handleSubmit } = createDoctorForm((form) => {
    doctorMutateEngine({
      nameInfo: {
        firstName: form.firstName,
        lastName: form.firstName,
        middleName: form.middleName, 
      },
      title: form.title
    })
  });

  let ref: HTMLFormElement;
</script>

<Wizard
  title={$_('generics.newDoctor')}
  subtitle={$_('descriptions.addDoctorDescription')}
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
            </Row>
          </Col>
          <Col xlarge="50" width="100">
            <Row>
              <Col width="100">
                <Input>
                  <input type="text" name="title" placeholder="Title" />
                </Input>
              </Col>
            </Row>
          </Col>
        </Row>
      </Block>
    </Block>
  </form>
</Wizard>