<script lang="ts">
  import { Chip } from 'framework7-svelte';
  import { Sex } from '../../schemas/leftindust.schema';

  export let sex: Sex;
  export let gender: string | undefined;

  export let outline = true;
  export let small = false;

  let mediaBgColor =
    (sex === Sex.Male && 'primary') ||
    (sex === Sex.Female && 'pink') ||
    (sex === Sex.Intersex && 'purple');

</script>

<Chip
  class={`${small ? 'chip-small' : ''}`}
  mediaBgColor={mediaBgColor || ''}
  text={gender && gender !== sex ? `${sex}/${gender}` : sex}
  outline={outline}
>
  <span slot="media">{sex.charAt(0)}</span>
</Chip>
