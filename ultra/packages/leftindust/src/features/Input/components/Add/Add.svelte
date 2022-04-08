<script lang="ts">
  import { Icon, ListItem } from 'framework7-svelte';
  import { _ } from 'svelte-i18n';
  import Input from '../../Input.svelte';

  export let title = '';
  export let placeholder = $_('generics.addInput');
  export let disabled = false;
</script>

<div class={`input-add ${disabled ? 'disabled' : ''}`} on:click>
  <Input {title}>
    <ListItem
      title={placeholder}
      link
      noChevron
      slot="content"
      {...$$restProps}
      
    >
      <Icon color="green" f7="plus_circle_fill" slot="media" style="font-size: 24px" />
      <slot />
    </ListItem>
  </Input>
</div>
