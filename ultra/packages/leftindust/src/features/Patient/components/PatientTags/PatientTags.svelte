<script lang="ts">
  import type { Ethnicity, Sex } from '@/api/server';
  import intervalToDuration from 'date-fns/intervalToDuration';
  import format from 'date-fns/format';

  import { Chip, Icon } from 'framework7-svelte';

  import SexTag from '../../../Clients/components/SexTag/SexTag.svelte';

  export let dateOfBirth: string | undefined = undefined;
  export let ethnicity: Ethnicity | undefined = undefined;
  export let sex: Sex | undefined = undefined;
  export let gender: string | undefined = undefined;

  export let outline = false;
  export let small = false;

  let birthday: Date | undefined;

  
  if (dateOfBirth) {
    // Temporary fix, may need to look into timezone things
    const utcToPst = new Date(dateOfBirth).getTimezoneOffset() * 60000;
    birthday = new Date(new Date(dateOfBirth).getTime() + utcToPst);
  }

</script>

{#if birthday}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={format(birthday, 'dd/MM/yyyy')}
    mediaBgColor="blue"
    {outline}
  >
    <span slot="media">{intervalToDuration({ start: birthday, end: new Date() }).years}</span>
  </Chip>
{/if}
{#if sex}
  <SexTag {...{ small, outline }} {gender} {sex} />
{/if}
{#if ethnicity}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={ethnicity}
    mediaBgColor="primary"
    {outline}
  >
    <span slot="media"><Icon f7="globe" /></span>
  </Chip>
{/if}
