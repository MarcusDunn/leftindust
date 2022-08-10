<script lang="ts">
  import type { Data } from '@/api/server/graphql' 
  import type { Popover } from 'framework7/types'

  import type { NameInfo, DoctorInput } from '@/api/server/graphql/schema/leftindust.schema'
  // import type {DoctorEngine as DoctorEngineType}

  import { Country } from '@/api/server/graphql/schema/leftindust.schema'
  import { WidgetType } from '../Widgets';

  import { writable } from 'svelte/store';
  import { _ } from '@/language';
  import { loginForm } from '../Account';
  import { closeWizard } from '../Wizard';
  // import DoctorEngine from '';

  // import { DoctorMutationDocument } from '@/api/server';
  import getNativeAPI from '@/api/bridge';
  import deepmerge from 'deepmerge';

  import { Row, Col, Block } from 'framework7-svelte';

  import Input from '../Input/Input.svelte';
  import Wizard from '../Wizard/Wizard.svelte';
  import Add from '../Input/components/Add/Add.svelte'
  // import Phone from '../Phone/Phone.svelte';

  import { AttachmentsTabs } from '../Attachments'; //!!!
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { clientsSelectedTab } from '@/features/Clients/store'
  import { ClientsTab } from '@/features/Clients/index'

  export let data: Data<'Doctor'> | undefined = undefined;

  const { Dialog } = getNativeAPI();

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

  const { form, errors } = loginForm();

</script>

<!-- <AttachmentsTabs 
  attachments={patients}
  types={['Patient']}
  bind:instance={patientsPopover}
/> -->

<form use:form>
  <Wizard
    title={$_('generics.newDoctor')}
    subtitle={$_('descriptions.addDoctorDescription')}
    color="purple"
    {disabled}
    on:submit={submit}
  >
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
          </Col>
          <Col xlarge="50" width="100">
            <Row>
              <Col width="100">
                <Input>
                  <input type="text" placeholder="Title" bind:value={input.title} />
                </Input>
                <p />
              </Col>
            </Row>
          </Col>
        </Row>
      </Block>
    </Block>
  </Wizard>
</form>