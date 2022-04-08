<script lang="ts">
	import { createEventDispatcher } from 'svelte';
	import { ListItem } from 'framework7-svelte';
	import PinButtonUI from '../../button/PinButtonUI/PinButtonUI.svelte';

	const dispatch = createEventDispatcher();

	export let link = true;
	export let pinned = false;

</script>

<ListItem class="ultra-pinnable-item" {link} {...$$restProps} on:click>
  <PinButtonUI on:pin={() => dispatch('pin', !pinned)} bind:pinned slot="root-start" />
	<svelte:fragment slot="media">
		<slot name="media"/>
	</svelte:fragment>
	<svelte:fragment slot="title">
		<slot name="title"/>
	</svelte:fragment>
	<svelte:fragment slot="subtitle">
		<slot name="subtitle"/>
	</svelte:fragment>
	<svelte:fragment slot="header">
		<slot name="header"/>
	</svelte:fragment>
	<svelte:fragment slot="text">
		<slot name="text"/>
	</svelte:fragment>
</ListItem>
