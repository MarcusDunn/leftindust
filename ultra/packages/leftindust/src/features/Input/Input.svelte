<script lang="ts">
  import { Icon } from 'framework7-svelte';
  import RecordingModal from '../Recording/components/RecordingModal/RecordingModal.svelte';
  import { openDialog } from '../UI/components/Dialog';
  import MenuButton from '../UI/components/MenuButton/MenuButton.svelte';

  import './Input.scss';
  import InputError from './InputError.svelte';

  export let title = '';

  export let clear = false;

  export let disabled = false;

  export let style = '';

  export let error: string[] | string | null | undefined = undefined;

  let input: HTMLInputElement | null;

  const reciveTranscript = (transcript: string) => {
    if (input) input.value = transcript;
  };

  const microphone = (node: HTMLInputElement) => {
    input = node;
  };
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
            <div class="display-flex">
              <slot {microphone} />
  
              <div style="margin-top: 5px">
                {#if input}
                  <MenuButton
                    on:click={() => openDialog({
                      title: 'Recording',
                      component: {
                        component: RecordingModal,
                        params: {
                          onStop: reciveTranscript,
                        },
                      },
                    })}
                  >
                    <Icon
                      color="blue"
                      f7="mic_fill"
                      style="font-size: 21px"
                    />
                  </MenuButton>
                {/if}
              </div>

              {#if clear}
                <span class="input-clear-button" />
              {/if}
            </div>
          </div>
        </div>

      </li>
    {/if}
  </ul>
</div>

{#if error}
  {#if Array.isArray(error)}
    {#each error as e}
      <InputError message={e} />
    {/each}
  {:else}
    <InputError message={error} />
  {/if}
{/if}