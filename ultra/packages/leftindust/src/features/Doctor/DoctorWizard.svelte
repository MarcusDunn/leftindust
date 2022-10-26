<script lang="ts">
  import { _ } from '@/language';
  import type { DoctorFragment } from '@/api/server';

  import { Row, Col, Block } from 'framework7-svelte';
  import Addresses from '../Input/components/Address/Addresses.svelte';
  import DatePicker from '../Input/components/Date/DatePicker.svelte';
  import Emails from '../Input/components/Email/Emails.svelte';
  import Phones from '../Input/components/Phone/Phones.svelte';

  import Input from '../Input/Input.svelte';
  import { closeWizard } from '../Wizard';
  import Wizard from '../Wizard/Wizard.svelte';
  
  import { createDoctorForm } from './';
  import { selectedDoctor, isDoctorSelected } from '../Doctors/store';

  export let callback: () => void;

  let doctor: DoctorFragment | undefined;
  
  const closeWizardHandler = () => {
    reset();
    callback();
    closeWizard();
  };

  let { form, data: formData, handleSubmit, errors, reset, interacted } = createDoctorForm(closeWizardHandler);

  // Bandaid fix for updating form data
  $: {
    doctor = $selectedDoctor;
    $isDoctorSelected;

    let doctorForm = createDoctorForm(closeWizardHandler, doctor?.id?.value);
    ({ form, data: formData, handleSubmit, errors, reset, interacted } = doctorForm);

    console.log($formData.firstName);
  }

  let ref: HTMLFormElement;
</script>

<Wizard
  title={$isDoctorSelected ? $_('generics.editDoctor') : $_('generics.newDoctor')}
  subtitle={$isDoctorSelected ? $_('descriptions.editDoctorDescription') : $_('descriptions.addDoctorDescription')}
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
              <Col width="100" medium="20">
                <Input error={$errors.firstName}>
                  <input type="text" name="firstName" placeholder="First Name">  
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
          <Col width="100" medium="50">
            <div style="margin-top: 2px;">
              <DatePicker
                placeholder="Birthday"
                error={$errors.dateOfBirth}
                pastOnly
                on:change={(e) => {
                  $formData.dateOfBirth = new Date(e.detail).toLocaleDateString('en-CA',  {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                  });
                }}
              />
            </div>
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