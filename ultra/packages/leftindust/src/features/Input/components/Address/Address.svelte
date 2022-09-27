<script lang="ts">
  import { type CreateAddress, AddressType } from '@/api/server';

  import { Countries } from '@/api/server';

  import { Row, Col } from 'framework7-svelte'
  import Input from '../../Input.svelte';
  import Select from '../Select/Select.svelte';

  export let value: Partial<CreateAddress>;
</script>

<div style="width: 100%">
  <Row>
    <Col xlarge="50" width="100">
      <Row>
        <Col width="25">
          <Select
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
          <br />
          <Input clear>
            <input 
              type="text"
              placeholder="Street Address"
              bind:value={value.address}
            />
          </Input>
        </Col>
        <Col width="25">
          <br />
          <Input>
            <input 
              type="text"
              placeholder="City"
              bind:value={value.city}
            />
          </Input>
        </Col>
      </Row>
    </Col>
    <Col xlarge="50" width="100">
      <Row>
        <Col>
          <Select
            title="Country"
            options={[
              {
                text: 'Canada',
                value: Countries.Canada
              },
            ]}
            disabled
            bind:value={value.country}
          />
        </Col>
        <Col>
          <Select 
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
        <Col>
          <br />
          <Input clear>
           <input 
             type="text"
             placeholder="Postal Code"
             bind:value={value.postalCode}
           />
          </Input>
        </Col>
      </Row>
    </Col>
  </Row>
</div>