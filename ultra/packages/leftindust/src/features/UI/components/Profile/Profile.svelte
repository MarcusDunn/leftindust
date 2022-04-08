<script lang="ts">
  import type { ProfileDrawerOptions } from '.';
  import {
    Block,
    AccordionContent,
    AccordionItem,
    Button,
  } from 'framework7-svelte';

  import { ProfileDrawer } from '.';

  import './Profile.scss';

  export let drawer: ProfileDrawerOptions = ProfileDrawer;

  let openState: boolean = drawer.open ?? false;

</script>

<Block class="ui-profile">
  <div class="ui-profile-content">
    {#if $$slots.media}
      <div class="ui-profile-item">
        <div class="ui-profile-media">
          <slot name="media" />
        </div>
      </div>
    {/if}
    <div class="ui-profile-item">
      <slot name="title" />
      <div class="ui-profile-tags">
        <slot name="tags" />
      </div>
    </div>
  </div>

  <div class="ui-profile-information">
    <slot />
  </div>

  <Block class="ui-profile-drawer-container ui-profile-content" accordionList>
    {#if drawer.visible}
      <AccordionItem class="ui-profile-drawer" opened={openState}>
        <AccordionContent class="ui-profile-drawer-content">
          <slot name="drawer" />
        </AccordionContent>
        {#if $$slots.drawer}
          <Button
            class="ui-profile-drawer-toggle accordion-item-toggle"
            on:click={() => (openState = !openState)}
          >
            {#if openState}
              <span>{drawer.name ? `Hide ${drawer.name}` : 'View Less'}</span>
            {:else}<span>{drawer.name ? `View ${drawer.name}` : 'View More'}</span>{/if}
          </Button>
        {/if}
      </AccordionItem>
    {/if}
  </Block>
</Block>
