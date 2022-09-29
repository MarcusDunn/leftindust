<script lang="ts">
  import type { CreateEmail } from '@/api/server';

  import { Row, Col } from 'framework7-svelte';
  import { EmailType } from '@/api/server';
  import Input from '../../Input.svelte';
  import Select from '../Select/Select.svelte';

  export let value: Partial<CreateEmail>;
  export let name: string | undefined = undefined;

  export let error: {
    type: string[] | string | null | undefined,
    email: string[] | string | null | undefined
  } | undefined = undefined;
</script>

<div style="width: 100%">
  <Row>
    <Col width="33">
      <Select
        placeholder="Type"
        name={name ? `${name}.type` : undefined}
        error={error?.type}
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
      <Input error={error?.email} clear style="margin-top: -12px">
        <input 
          type="email"
          name={name ? `${name}.email` : undefined}
          placeholder="Email"
          bind:value={value.email}
        />
      </Input>
    </Col>
  </Row>
</div>