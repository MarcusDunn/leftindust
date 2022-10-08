<script lang="ts">
  import { createEventDispatcher } from "svelte";
  import MenuButton from "@/features/UI/components/MenuButton/MenuButton.svelte";

  const dispatch = createEventDispatcher();
</script>

<div class="ultra-delete-button">
  <MenuButton 
    icon={{ f7: 'minus_circle_fill', color: 'red' }}
    on:click={() => dispatch('delete')}
  />
</div>