<script lang="ts">
  import type { PhoneInput } from '../../../schemas/leftindust.schema';

  import Cleave from 'cleave.js';
  import 'cleave.js/dist/addons/cleave-phone.ca';

  import { Row, Col } from 'framework7-svelte';
  import { PhoneType } from '../../../schemas/leftindust.schema';
  import InputUI from '../InputUI/InputUI.svelte';
  import SelectUI from '../SelectUI/SelectUI.svelte';
  import { onMount } from 'svelte';

  export let value: Partial<PhoneInput>;

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
      <SelectUI
        title="Type"
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
      <InputUI clear>
        <input
          type="tel"
          placeholder="Phone"
          bind:value={value.number}
          bind:this={ref}
        />
      </InputUI>
    </Col>
  </Row>
</div>