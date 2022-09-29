<script lang="ts">
  import type { CreatePhone } from '@/api/server';
  import Cleave from 'cleave.js';
  import 'cleave.js/dist/addons/cleave-phone.ca';
  
  import { Row, Col } from 'framework7-svelte';
  import { PhoneType } from '@/api/server';
  import Input from '../../Input.svelte';
  import Select from '../Select/Select.svelte';
  import { onMount } from 'svelte';
  
  export let value: Partial<CreatePhone>;
  export let name: string | undefined = undefined;

  export let error: {
    type: string[] | string | null | undefined,
    number: string[] | string | null | undefined
  } | undefined = undefined;
  
  let ref: HTMLInputElement;
  
  onMount(() => {
    new Cleave(ref, {
      phone: true,
      phoneRegionCode: 'ca',
    });
  });
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
            text: 'Cell', 
            value: PhoneType.Cell,
          },
          {
            text: 'Home', 
            value: PhoneType.Home,
          },
          {
            text: 'Work', 
            value: PhoneType.Work,
          },
          {
            text: 'Pager', 
            value: PhoneType.Pager,
          },
          {
            text: 'Other', 
            value: PhoneType.Other,
          },
        ]}
        bind:value={value.type}
      />
    </Col>
    <Col width="66">
      <Input error={error?.number} clear style="margin-top: -12px">
        <input 
          type="tel"
          name={name ? `${name}.number` : undefined}
          placeholder="Phone"
          bind:value={value.number}
          bind:this={ref}
        />
      </Input>
    </Col>
  </Row>
</div>
