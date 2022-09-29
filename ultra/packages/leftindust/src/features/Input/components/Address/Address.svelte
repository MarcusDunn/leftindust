<script lang="ts">
  import { type CreateAddress, AddressType } from '@/api/server';

  import { Countries } from '@/api/server';

  import { Row, Col } from 'framework7-svelte';
  import Input from '../../Input.svelte';
  import Select from '../Select/Select.svelte';

  export let value: Partial<CreateAddress>;
  export let name: string | undefined = undefined;

  export let error: {
    address: string[] | string | null | undefined,
    addressType: string[] | string | null | undefined,
    city: string[] | string | null | undefined,
    country: string[] | string | null | undefined,
    postalCode: string[] | string | null | undefined,
    province: string[] | string | null | undefined,
  } | any | undefined = undefined;
</script>

<div style="width: 100%">
  <Row>
    <Col xlarge="50" width="100">
      <Row>
        <Col width="25">
          <Select
            placeholder="Type"
            name={name ? `${name}.type` : undefined}
            error={error?.addressType}
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
          <Input error={error?.address} clear style="margin-top: -12px">
            <input 
              type="text"
              name={name ? `${name}.address` : undefined}
              placeholder="Street Address"
              bind:value={value.address}
            />
          </Input>
        </Col>
        <Col width="25">
          <Input error={error?.city} style="margin-top: -12px">
            <input 
              type="text"
              name={name ? `${name}.city` : undefined}
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
            placeholder="Country"
            error={error?.country}
            name={name ? `${name}.country` : undefined}
            options={[
              {
                text: 'Canada',
                value: Countries.Canada,
              },
            ]}
            disabled
            bind:value={value.country}
          />
        </Col>
        <Col>
          <Select 
            placeholder="Province"
            error={error?.province}
            name={name ? `${name}.province` : undefined}
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
          <Input error={error?.postalCode} clear style="margin-top: -12px">
            <input 
              type="text"
              name={name ? `${name}.postalCode` : undefined}
              placeholder="Postal Code"
              bind:value={value.postalCode}
            />
          </Input>
        </Col>
      </Row>
    </Col>
  </Row>
</div>