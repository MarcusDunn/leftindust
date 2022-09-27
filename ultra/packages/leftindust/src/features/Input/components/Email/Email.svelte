<script lang="ts">
  import type { CreateEmail } from '@/api/server';

  import { Row, Col } from 'framework7-svelte';
  import { EmailType } from '@/api/server';
  import Input from '../../Input.svelte';
  import Select from '../Select/Select.svelte';

  export let value: Partial<CreateEmail>;
</script>

<div style="width: 100%">
  <Row>
    <Col width="33">
      <Select
        title="Type"
        options={[
          {
            text: 'Personal',
            value: EmailType.Personal
          },
          {
            text: 'Work',
            value: EmailType.Work
          },
          {
            text: 'School',
            value: EmailType.School
          },
          {
            text: 'Other',
            value: EmailType.Other
          },
        ]}
        bind:value={value.type}
      />
    </Col>
    <Col width="66">
      <br />
      <Input>
        <input 
          type="email"
          placeholder="Email"
          bind:value={value.email}
        />
      </Input>
    </Col>
  </Row>
</div>