<script lang="ts">
  import { Chip } from "framework7-svelte";
  import { _ } from '@/language';
  import leftindust from '@/../public/leftindust/favicon-57.png';

  export let outline = true;
  export let small = false;

  export let link = false;

  export let text = $_('generics.poweredByDust');

  const url = 'https://leftindust.com';
</script>

<Chip
  class={`${small ? 'chip-small' : ''}`}
  mediaBgColor="black"
  bgColor="black"
  {text}
  {outline}
  on:click={() => link && window.open(url, '_blank')}
  style={link ? 'cursor: pointer' : ''}
>
  <span slot="media">
    <img src={leftindust} alt="Leftindust" />
  </span>
</Chip>
