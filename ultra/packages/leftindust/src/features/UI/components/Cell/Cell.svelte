<script lang="ts">
	import { Icon, ListItem } from 'framework7-svelte';

	import './Cell.scss';

	export let checkbox = false;
	export let selected = false;
	export let link = true;
</script>

<div class="ui-cell">
	<slot name="root-start" />
	<ListItem
		class={`ui-cell-item ${selected ? 'ui-cell-item-selected' : ''}`}
		{link}
		{...$$restProps}
		on:click
	>
		<svelte:fragment slot="content-start">
			<div class={`ui-cell-item-toggle ${checkbox ? 'ui-cell-item-toggle-active' : ''}`}>
				{#if checkbox}
					{#if selected}
						<Icon f7="checkmark_circle_fill" color="primary" />
					{:else}
						<Icon f7="circle" color="gray" />
					{/if}
				{/if}
			</div>
			<slot name="content-start" />
		</svelte:fragment>
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
		<svelte:fragment slot="content-end">
			<slot name="content-end" />
		</svelte:fragment>
	</ListItem>
	<slot name="root-end" />
</div>