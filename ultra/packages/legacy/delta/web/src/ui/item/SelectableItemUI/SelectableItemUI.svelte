<script lang="ts">
	import { ListItem } from 'framework7-svelte';

	export let selected = false;
	export let link = true;
</script>

<ListItem
	class={`ultra-selectable-item ${selected ? 'ultra-selectable-item-selected' : ''}`}
	{link}
	{...$$restProps}
	on:click
>
	<svelte:fragment slot="media">
		<slot name="media" />
	</svelte:fragment>
	<svelte:fragment slot="title">
		<slot name="title" />
	</svelte:fragment>
	<svelte:fragment slot="subtitle">
		<slot name="subtitle" />
	</svelte:fragment>
	<svelte:fragment slot="header">
		<slot name="header" />
	</svelte:fragment>
	<svelte:fragment slot="text">
		<slot name="text" />
	</svelte:fragment>
</ListItem>
