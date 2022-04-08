<script lang="ts" context="module">
  import type { Popover as PopoverType } from 'framework7/types';
  import type { Framework7Icon } from '../../../modules/UIModule';

  export type MenuUIItems = {
    title: string;
    text?: string;
    icon?: Framework7Icon;
    onClick?: () => void;
  };
</script>

<script lang="ts">
  import {
    Popover,
    List,
    ListItem,
    Icon,
    Block,
  } from 'framework7-svelte';
  import { onMount } from 'svelte';
  
  export let items: MenuUIItems[];
  export let closeOnSelect = true;

  export let instance: PopoverType.Popover | undefined = undefined;

  let ref: Popover;

  onMount(() => {
    instance = ref.instance();
  });
  
</script>

<Popover class="ultra-menu" bind:this={ref}>
  <Block>
    <List
      class="no-hairlines no-hairlines-between"
      mediaList
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