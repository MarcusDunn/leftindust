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

  const { form, errors, data: formData, handleSubmit } = createDoctorFormValidator();

  let doctor: any = writable();

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

  $: if ($doctor) edit();
  let ref: HTMLFormElement;
</script>

<Wizard
  title={$_('generics.newDoctor')}
  subtitle={$_('descriptions.addDoctorDescription')}
  color="purple"
  disabled={false}
  on:submit={() => ref?.requestSubmit()}
> 
  <form use:form on:submit="{handleSubmit}">
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
          </Col>
          <Col xlarge="50" width="100">
            <Row>
              <Col width="100">
                <Input>
                  <input type="text" name="title" placeholder="Title" />
                </Input>
                <p />
              </Col>
            </Row>
          </Col>
        </Row>
      </Block>
      <button type="submit" style="height: 80px;">Done</button>
    </Block>
  </form>
</Wizard>