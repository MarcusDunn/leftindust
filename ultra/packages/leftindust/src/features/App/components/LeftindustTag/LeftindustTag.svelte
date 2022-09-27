<script lang="ts">
  import { Chip } from 'framework7-svelte';
  import { _ } from '@/language';
  import leftindust from '@/../public/leftindust/favicon-57.png';

  export let small = false;

  export let link = false;

  export let text = $_('generics.poweredByDust');

  const url = 'https://leftindust.com';
</script>

<Chip
  class={`${small ? 'chip-small' : ''}`}
  mediaBgColor="black"
  outline
  {text}
  on:click={() => link && window.open(url, '_blank')}
  style={`${link ? 'cursor: pointer;' : ''}${$$restProps.style}`}
>
  <span slot="media">
    <img src={leftindust} alt="Leftindust" />
  </span>
</Chip>
