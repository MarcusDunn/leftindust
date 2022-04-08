<script lang="ts">
  import { Icon } from 'framework7-svelte';
  import MenuButtonUI from '../../button/MenuButtonUI/MenuButtonUI.svelte'; 

  export let clear = false;
  export let microphone = false;
</script>
 
<div
  class="ultra-input list inset inline-labels no-hairlines"
>
  <ul>
    <slot name="content" />
    {#if !$$slots.content}
      <li class="item-content item-input">
        {#if !!$$slots.media}
          <div class="item-media">
            <slot name="media" />
          </div>
        {/if}
        <div class="item-inner">
          {#if !!$$slots.title}
            <div class="item-title">
              <slot name="title" />
            </div>
          {/if}
          <div class="item-input-wrap">
            <slot />
            {#if clear}
              <span class="input-clear-button" />
            {/if}
          </div>
        </div>
        {#if microphone}
          <div class="item-media">
            <MenuButtonUI>
              <Icon
                color="purple"
                f7="mic_fill"
                style="font-size: 21px"
              />
            </MenuButtonUI>
          </div>
        {/if}
      </li>
    {/if}
  </ul>
</div>