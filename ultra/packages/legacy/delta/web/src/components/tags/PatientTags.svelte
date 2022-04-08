<script lang="ts">
  import type { DateInput, Ethnicity, Sex } from '../../schemas/leftindust.schema';
  import intervalToDuration from 'date-fns/intervalToDuration';
  import format from 'date-fns/format';

  import { Chip, Icon } from 'framework7-svelte';

  import SexTag from './SexTag.svelte';

  export let dateOfBirth: DateInput | undefined = undefined;
  export let ethnicity: Ethnicity | undefined = undefined;
  export let sex: Sex | undefined = undefined;
  export let gender: string | undefined = undefined;

  export let outline = false;
  export let small = false;

  let birthday: Date | undefined;

  if (dateOfBirth)
    birthday = new Date(`${dateOfBirth.month} ${dateOfBirth.day} ${dateOfBirth.year}`);

</script>

{#if birthday}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={format(birthday, 'dd/MM/yyyy')}
    mediaBgColor="blue"
    outline={outline}
  >
    <span slot="media">{intervalToDuration({ start: birthday, end: new Date() }).years}</span>
  </Chip>
{/if}
{#if sex}
  <SexTag {...{ small, outline }} gender={gender} sex={sex} />
{/if}
{#if ethnicity}
  <Chip
    class={`${small ? 'chip-small' : ''}`}
    text={ethnicity}
    mediaBgColor="primary"
    outline={outline}
  >
    <span slot="media"><Icon f7="globe" /></span>
  </Chip>
{/if}
