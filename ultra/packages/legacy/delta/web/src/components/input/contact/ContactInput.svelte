<script lang="ts">
  import type { EmergencyContactInput } from '../../../schemas/leftindust.schema';

  import { Row, Col } from 'framework7-svelte';
  import { Relationship } from '../../../schemas/leftindust.schema';

  import InputUI from '../../../ui/input/InputUI/InputUI.svelte';
  import SelectUI from '../../../ui/input/SelectUI/SelectUI.svelte';
  import EmailsUI from '../../../ui/input/EmailUI/EmailsUI.svelte';
  import PhonesUI from '../../../ui/input/PhoneUI/PhonesUI.svelte';

  export let value: Partial<EmergencyContactInput>;
</script>

<div style="width: 100%">
  <Row>
    <Col width="100" xlarge="50">
      <Row>
        <Col width="100" medium="33">
          <InputUI>
            <input type="text" placeholder="First Name" bind:value={value.firstName} />
          </InputUI>
          <p />
        </Col>
        <Col width="100" medium="33">
          <InputUI>
            <input type="text" placeholder="Middle Name (Optional)" bind:value={value.middleName} />
          </InputUI>
          <p />
        </Col>
        <Col width="100" medium="33">
          <InputUI>
            <input type="text" placeholder="Last Name" bind:value={value.lastName} />
          </InputUI>
          <p />
        </Col>
      </Row>
    </Col>
    <Col width="100" xlarge="50">
      <SelectUI
        title="Relationship"
        options={[
          {
            text: 'Aunt',
            value: Relationship.Aunt,
          },
          {
            text: 'Child',
            value: Relationship.Child,
          },
          {
            text: 'Cousin',
            value: Relationship.Cousin,
          },
          {
            text: 'Grandchild',
            value: Relationship.Grandchild,
          },
          {
            text: 'Grandparent',
            value: Relationship.Grandparent,
          },
          {
            text: 'Guardian',
            value: Relationship.Guardian,
          },
          {
            text: 'Parent',
            value: Relationship.Parent,
          },
          {
            text: 'Partner',
            value: Relationship.Partner,
          },
          {
            text: 'Sibling',
            value: Relationship.Sibling,
          },
          {
            text: 'Uncle',
            value: Relationship.Uncle,
          },
          {
            text: 'Other',
            value: Relationship.Other,
          },
        ]}
        bind:value={value.relationship}
      />
      <p />
    </Col>
    <Col width="100" xlarge="50">
      <EmailsUI bind:value={value.emails} />
      <p />
    </Col>
    <Col width="100" xlarge="50">
      <PhonesUI bind:value={value.phones} />
      <p />
    </Col>
  </Row>
</div>