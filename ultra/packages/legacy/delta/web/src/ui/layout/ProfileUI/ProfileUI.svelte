<script lang="ts" context="module">
  export type ProfileUIDrawerOptions = {
    name?: string | boolean;
    open?: boolean;
    visible?: boolean;
    tagsButton?: boolean;
  };

  export const ProfileUIDrawer: ProfileUIDrawerOptions = {
    name: false,
    open: false,
    visible: true,
    tagsButton: false,
  };
</script>

<script lang="ts">
import {
  Block,
  AccordionContent,
  AccordionItem,
  Button,
  Chip,
  Icon,
} from 'framework7-svelte';

export let drawer: ProfileUIDrawerOptions = ProfileUIDrawer;

let openState: boolean = drawer.open ?? false;

</script>

<Block class="ultra-profile">
  <div class="ultra-profile-content">
    {#if $$slots.media}
      <div class="ultra-profile-item">
        <div class="ultra-profile-media">
          <slot name="media" />
        </div>
      </div>
    {/if}
    <div class="ultra-profile-item">
      <slot name="title" />
      <div class="ultra-profile-tags">
        <slot name="tags" />
      </div>
    </div>
  </div>

  <div class="ultra-profile-information">
    <slot />
  </div>

  <Block class="ultra-profile-drawer-container ultra-profile-content" accordionList>
    {#if drawer.visible}
      <AccordionItem class="ultra-profile-drawer" opened={openState}>
        <AccordionContent class="ultra-profile-drawer-content">
          <slot name="drawer" />
        </AccordionContent>
        {#if $$slots.drawer}
          <Button
            class="ultra-profile-drawer-toggle accordion-item-toggle"
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
