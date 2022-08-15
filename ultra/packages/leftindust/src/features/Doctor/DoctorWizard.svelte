<script lang="ts">
  import type { Data } from '@/api/server/graphql' 
  import type { Popover } from 'framework7/types'

  import type { NameInfo, DoctorInput } from '@/api/server/graphql/schema/leftindust.schema'

  import { writable } from 'svelte/store';
  import { _ } from '@/language';

  import deepmerge from 'deepmerge';

  import { Row, Col, Block } from 'framework7-svelte';

  import Input from '../Input/Input.svelte';
  import Wizard from '../Wizard/Wizard.svelte';

  import { createDoctorFormValidator } from './';

  export let data: Data<'Doctor'> | undefined = undefined;

  const { form, errors, data: formData } = createDoctorFormValidator();

  let ref: HTMLFormElement;

  let doctor: any = writable();
  let disabled = true;

  let input: Partial<DoctorInput> & { nameInfo: NonNullable<NameInfo>} = {
    nameInfo:{
      firstName: '',
      middleName: '',
      lastName: '',
    },
    title: '',
    addresses: [],
    patients: [],
    emails: [],
    phones: [],
  }
  
  let patients = writable<Data[]>([]);

  let patientsPopover: Popover.Popover;

  const edit = (): void => {
    input = deepmerge(input, {
      ...$doctor,
      nameInfo: {
        firstName: $doctor.firstName,
        middleName: $doctor.middleName,
        lastName: $doctor.lastName,
      },
    });
  }

  const submit = () => {}

  $: if ($doctor) edit();

</script>

<Wizard
  title={$_('generics.newDoctor')}
  subtitle={$_('descriptions.addDoctorDescription')}
  color="purple"
  {disabled}
  on:submit={submit}
> 
  <form use:form bind:this={ref}>   
    <Block style="margin-top: 60px">
      <Block>
        <h4>Identification</h4>
        <Row>
          <Col xlarge="50" width="100">
            <Row>
              <Col width="100" medium="33">
                <Input>
                  <input type="text" name="firstName" placeholder="First Name" bind:value={input.nameInfo.firstName} />  
                </Input>
                <p />
              </Col>
              <Col width="100" medium="33">
                <Input>
                  <input type="text" name="middleName" placeholder="Middle Name (Optional)" bind:value={input.nameInfo.middleName} />
                </Input>
                <p />
              </Col>
              <Col width="100" medium="33">
                <Input>
                  <input type="text" name="lastName" placeholder="Last Name" bind:value={input.nameInfo.lastName} />
                </Input>
                <p />
              </Col>
            </Row>
          </Col>
          <Col xlarge="50" width="100">
            <Row>
              <Col width="100">
                <Input>
                  <input type="text" name="title" placeholder="Title" bind:value={input.title} />
                </Input>
                <p />
              </Col>
            </Row>
          </Col>
        </Row>
      </Block>
    </Block>
  </form>
</Wizard>