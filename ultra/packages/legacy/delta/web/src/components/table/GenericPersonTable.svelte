<script lang="ts">
  import language from '../../languages';
  import type { Address, Email, Phone } from '../../schemas/leftindust.schema';

  import { Block } from 'framework7-svelte';

  export let phones: Phone[] | undefined = undefined;
  export let emails: Email[] | undefined = undefined;
  export let addresses: Address[] | undefined = undefined;

</script>

{#if phones || emails || addresses}
  <div class="data-table">
    <table>
      <tbody>
        {#if phones}
          {#each phones as phone}
            <tr>
              <td class="label-cell">{`${phone.type} ${language().form.phone.text}`}</td>
              <td>{phone.number}</td>
            </tr>
          {/each}
        {/if}
        {#if emails}
          {#each emails as email}
            <tr>
              <td class="label-cell">{`${email.type} ${language().form.email.text}`}</td>
              <td>{email.email}</td>
            </tr>
          {/each}
        {/if}
        {#if addresses}
          {#each addresses as address}
            <tr>
              <td class="label-cell">{`${address.addressType} ${language().form.address.text}`}</td>
              <td>
                {`${address.address}, ${address.city}, ${address.province} ${address.postalCode}`}
              </td>
            </tr>
          {/each}
        {/if}
      </tbody>
    </table>
  </div>
{:else}
  <Block>
    <p class="text-align-center">{language().placeholders.noContactInformation.text}</p>
  </Block>
{/if}
