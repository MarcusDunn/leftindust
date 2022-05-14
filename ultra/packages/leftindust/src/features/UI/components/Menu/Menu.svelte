<script lang="ts">
  import type { Popover as PopoverType } from 'framework7/types';
  import type { MenuItem } from '.';
  import {
    Popover,
    List,
    ListItem,
    Icon,
    Block,
  } from 'framework7-svelte';
  import { onMount } from 'svelte';

  import './Menu.scss';

  export let items: MenuItem[];
  export let closeOnSelect = true;

  export let instance: PopoverType.Popover | undefined = undefined;

  let ref: Popover;

  onMount(() => {
    instance = ref.instance();
  });
  
</script>

<Popover class="ui-menu" bind:this={ref}>
  <Block>
    <List
      class="no-hairlines no-hairlines-between"
      mediaList
      noChevron
    >
      {#each items as {title, text, icon, onClick}}
        <ListItem
          {title}
          {text}
          link
          popoverClose={closeOnSelect}
          on:click={() => {
            onClick ? onClick() : undefined;
          }}
        >
          <Icon
            slot="media"
            {...icon}
          />
        </ListItem>
      {/each}
    </List>
  </Block>
</Popover>