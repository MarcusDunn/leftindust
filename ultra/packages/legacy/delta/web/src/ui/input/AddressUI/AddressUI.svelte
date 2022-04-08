<script lang="ts">
  import type { AddressInput } from '../../../schemas/leftindust.schema';
  
  import { Country } from '../../../schemas/leftindust.schema';
  
  import { Row, Col } from 'framework7-svelte';
  import { AddressType } from '../../../schemas/leftindust.schema';
  import InputUI from '../InputUI/InputUI.svelte';
  import SelectUI from '../SelectUI/SelectUI.svelte';

  export let value: Partial<AddressInput>;
</script>

<div style="width: 100%">
  <Row>
    <Col width="100" xlarge="50">
      <Row>
        <Col width="25">
          <SelectUI
            title="Type"
            options={[
              {
                text: 'Home',
                value: AddressType.Home,
              },
              {
                text: 'Apartment',
                value: AddressType.Apartment,
              },
              {
                text: 'Work',
                value: AddressType.Work,
              },
              {
                text: 'School',
                value: AddressType.School,
              },
              {
                text: 'Other',
                value: AddressType.Other,
              },
            ]}
            bind:value={value.addressType}
          />
        </Col>
        <Col width="50">
          <InputUI clear>
            <input
              type="text"
              placeholder="Street Address"
              bind:value={value.address}
            />
          </InputUI>
        </Col>
        <Col width="25">
          <InputUI clear>
            <input
              type="text"
              placeholder="City"
              bind:value={value.city}
            />
          </InputUI>
        </Col>
      </Row>
    </Col>
    <Col width="100" xlarge="50">
      <Row>
        <Col width="25">
          <SelectUI
            title="Country"
            options={[
              {
                text: 'Canada',
                value: Country.Canada,
              },
            ]}
            disabled
            bind:value={value.country}
          />
        </Col>
        <Col width="50">
          <SelectUI
            title="Province"
            options={[
              {
                text: 'Alberta',
                value: 'AB',
              },
              {
                text: 'British Columbia',
                value: 'BC',
              },
              {
                text: 'Manitoba',
                value: 'MB',
              },
              {
                text: 'New Brunswick',
                value: 'NB',
              },
              {
                text: 'Newfoundland and Labrador',
                value: 'NL',
              },
              {
                text: 'Northwest Territories',
                value: 'NT',
              },
              {
                text: 'Nova Scotia',
                value: 'NS',
              },
              {
                text: 'Nunavut',
                value: 'NV',
              },
              {
                text: 'Ontario',
                value: 'ON',
              },
              {
                text: 'Prince Edward Island',
                value: 'PEI',
              },
              {
                text: 'Quebec',
                value: 'QB',
              },
              {
                text: 'Saskatchewan',
                value: 'SK',
              },
              {
                text: 'Yukon',
                value: 'YK',
              },
            ]}
            bind:value={value.province}
          />
        </Col>
        <Col width="25">
          <InputUI clear>
            <input
              type="text"
              placeholder="Postal Code"
              bind:value={value.postalCode}
            />
          </InputUI>
        </Col>
      </Row>
    </Col>
  </Row>
</div>