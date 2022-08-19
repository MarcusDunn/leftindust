<script lang="ts">
  import Input from '../../Input.svelte';

  import './Checkbox.scss';

  export let value: string | number = '';
  export let selected: (string | number)[] = [];
  export let multiple = false;

  export let header: string | undefined = undefined;
  export let title: string | undefined = undefined;
  export let text: string | undefined = undefined;
  export let subtitle: string | undefined = undefined;

  // Checkbox needs to be controlled with external function and cannot be used with
  // bind:group if the component is nested
  // https://github.com/sveltejs/svelte/issues/2308
  const onCheckboxChange = ({ target }: Event) => {
    const { value, checked } = <HTMLInputElement>target;
    if (checked) {
      selected = [...selected, value];
    } else {
      selected = selected.filter((item) => item !== value);
    }
  };

  // Added radio change listener instead of default "on:group" hook
  // to allow for an array return for parity with checkbox
  const onRadioChange = ({ target }: Event) => {
    const { value, checked } = <HTMLInputElement>target;
    if (checked) {
      selected = [value];
    } else {
      selected = [];
    }
  };

  $: console.log(value);
</script>

<div class="input-checkbox">
  <Input>
    <li {title}>
      <label
        class={`
          item-content ${multiple ? 'item-checkbox' : 'item-radio item-radio-icon-start'}
        `}
        for={undefined}
      >
        {#if multiple}
          <input
            type="checkbox"
            {value}
            checked={selected.includes(value)}
            on:click={onCheckboxChange}
          />
        {:else}
          <input
            type="radio"
            {value}
            checked={selected.includes(value)}
            on:click={onRadioChange}
          />
        {/if}
    
        <slot />
        {#if multiple}
          <i class="icon icon-checkbox" />
        {:else}
          <i class="icon icon-radio" />
        {/if}
    
        <div class="item-inner">
          <div class="item-title">
            {#if $$slots.header || header}
              <div class="item-footer">
                {#if header}
                  {header}
                {:else}
                  <slot name="header" />
                {/if}
              </div>
            {/if}
            {#if title}
              {title}
            {:else}
              <slot name="title" />
            {/if}
          </div>
          {#if $$slots.text || text}
            <div class="item-text">
              {#if text}
                {text}
              {:else}
                <slot name="text" />
              {/if}
            </div>
          {/if}
          {#if $$slots.subtitle || subtitle}
            <div class="item-subtitle">
              {#if subtitle}
                {subtitle}
              {:else}
                <slot name="subtitle" />
              {/if}
            </div>
          {/if}
        </div>
      </label>
    </li>
  </Input>
</div>