<script lang="ts">
  import { Icon } from 'framework7-svelte';
  import MenuButton from '../UI/components/MenuButton/MenuButton.svelte';

  import './Input.scss';

  export let title = '';

  export let clear = false;
  export let microphone = false;

  export let disabled = false;

  export let style = '';
</script>
 
<div
  class={`input list inset no-hairlines ${$$slots.content ? 'input-custom-content' : ''} ${disabled ? 'disabled' : ''}`}
  {style}
>
  {#if !!$$slots.content && (!!$$slots.title || title)}
    <div class="input-content-title">
      <slot name="title" />
      {title}
    </div>
  {/if}
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
          {#if !!$$slots.title || title}
            <div class="item-title item-label">
              {title}
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
            <MenuButton>
              <Icon
                color="purple"
                f7="mic_fill"
                style="font-size: 21px"
              />
            </MenuButton>
          </div>
        {/if}
      </li>
    {/if}
  </ul>
</div>