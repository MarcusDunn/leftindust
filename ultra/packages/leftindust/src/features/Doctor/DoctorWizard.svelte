<script lang="ts">
  import { _ } from '@/language';

  import { Row, Col, Block } from 'framework7-svelte';
  import Addresses from '../Input/components/Address/Addresses.svelte';
  import Emails from '../Input/components/Email/Emails.svelte';
  import Phones from '../Input/components/Phone/Phones.svelte';

  import Input from '../Input/Input.svelte';
  import { sendTrigger } from '../Triggers';
  import { closeWizard } from '../Wizard';
  import Wizard from '../Wizard/Wizard.svelte';
  
  import { createDoctorForm } from './';

  export let doctorId: string | undefined = undefined;

  export let callback: () => void;
  
  const closeWizardHandler = () => {
    reset();
    callback();
    closeWizard();
    sendTrigger('doctors-update');
  };

  const { form, data: formData, handleSubmit, errors, reset } = createDoctorForm(closeWizardHandler, doctorId);

  let ref: HTMLFormElement;
</script>

<Wizard
  title={$_('generics.newDoctor')}
  subtitle={$_('descriptions.addDoctorDescription')}
  color="purple"
  disabled={false}
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
              <Col width="100" medium="20">
                <Input error={$errors.firstName}>
                  <input type="text" name="firstName" placeholder="First Name" />  
                </Input>
              </Col>
              <Col width="100" medium="20">
                <Input error={$errors.middleName}>
                  <input type="text" name="middleName" placeholder="Middle Name (Optional)" />
                </Input>
              </Col>
              <Col width="100" medium="20">
                <Input error={$errors.lastName}>
                  <input type="text" name="lastName" placeholder="Last Name" />
                </Input>
              </Col>
              <Col width="100" medium="40">
                <Input error={$errors.title}>
                  <input type="text" name="title" placeholder="Title" />
                </Input>
              </Col>
            </Row>
          </Col>
        </Row>
        <br />
        <br />
        <h4 style="margin-bottom: 10px">Contact</h4>
        <Row>
          <Col xlarge="50" width="100">
            <Phones
              title="Add Phone (Optional)"
              bind:value={$formData.phones}
              errors={$errors.phones}
            />
            <p />
          </Col>
          <Col xlarge="50" width="100">
            <Emails
              title="Add Email (Optional)"
              bind:value={$formData.emails}
              errors={$errors.emails}
            />
            <p />
          </Col>
        </Row>
        <br />
        <h4 style="margin-bottom: 10px">Address</h4>
        <Addresses
          title="Add Address (Optional)"
          bind:value={$formData.addresses}
          errors={$errors.addresses}
        />
        <p />
      </Block>
    </Block>
  </form>
</Wizard>