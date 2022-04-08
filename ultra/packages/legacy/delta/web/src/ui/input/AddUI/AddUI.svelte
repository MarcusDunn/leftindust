<script>
import { Icon, ListItem } from 'framework7-svelte';
import InputUI from '../InputUI/InputUI.svelte';

export let title = 'Add Input';

</script>

<div class="ultra-add" on:click>
  <InputUI>
    <ListItem
      title={title}
      link
      noChevron
      slot="content"
      {...$$restProps}
      
    >
      <Icon color="green" f7="plus_circle_fill" slot="media" />
      <slot />
    </ListItem>
  </InputUI>
</div>
