<script lang="ts">
  import type { EmailInput } from '../../../schemas/leftindust.schema';

  import { Row, Col } from 'framework7-svelte';
  import { EmailType } from '../../../schemas/leftindust.schema';
  import InputUI from '../InputUI/InputUI.svelte';
  import SelectUI from '../SelectUI/SelectUI.svelte';

  export let value: Partial<EmailInput>;
</script>

<div style="width: 100%">
  <Row>
    <Col width="33">
      <SelectUI
        title="Type"
        options={[
          {
            text: 'Personal',
            value: EmailType.Personal,
          },
          {
            text: 'Work',
            value: EmailType.Work,
          },
          {
            text: 'School',
            value: EmailType.School,
          },
          {
            text: 'Other',
            value: EmailType.Other,
          },
        ]}
        bind:value={value.type}
      />
    </Col>
    <Col width="66">
      <InputUI clear>
        <input
          type="email"
          placeholder="Email"
          bind:value={value.email}
        />
      </InputUI>
    </Col>
  </Row>
</div>