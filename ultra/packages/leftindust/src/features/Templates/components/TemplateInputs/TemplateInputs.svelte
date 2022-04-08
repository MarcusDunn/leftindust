<script lang="ts">
	import type { DndEvent } from 'svelte-dnd-action';
	import type { TemplateInput as TemplateInputT } from '../../';
	import { TemplateInputType, TemplateInputUploadType } from '../../';
	import { dndzone, SOURCES, TRIGGERS	} from 'svelte-dnd-action';
	import { flip } from 'svelte/animate';
	import { Icon } from 'framework7-svelte';
	import TemplateInput from '../TemplateInput/TemplateInput.svelte';
	import Add from '@/features/Input/components/Add/Add.svelte';
	import DescriptivePlaceholder
	  from '@/features/App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
	
	export let items: TemplateInputT[] = [];
			
	const flipDurationMs = 200;
	let dragDisabled = true;
	let globalIndex = 0;
			
	const handleConsider = (e: CustomEvent<DndEvent>) => {
	  const { items: newItems, info: { source, trigger } } = e.detail;
	  items = newItems as TemplateInputT[];
	  // Ensure dragging is stopped on drag finish via keyboard
	  if (source === SOURCES.KEYBOARD && trigger === TRIGGERS.DRAG_STOPPED) {
	    dragDisabled = true;
	  }
	};

	const handleFinalize = (e: CustomEvent<DndEvent>) => {
	  const { items: newItems, info: { source } } = e.detail;
	  items = newItems as TemplateInputT[];
	  // Ensure dragging is stopped on drag finish via pointer (mouse, touch)
	  if (source === SOURCES.POINTER) {
	    dragDisabled = true;
	  }
	};

	const startDrag = (e: Event) => {
	  // preventing default to prevent lag on touch devices (because of the browser checking for screen scrolling)
	  e.preventDefault();
	  dragDisabled = false;
	};
</script>

{#if items.length > 0}
	<section
		use:dndzone="{{ items, dragDisabled, flipDurationMs }}"
		on:consider="{handleConsider}"
		on:finalize="{handleFinalize}"
	>
		{#each items as item, index (item.id)}
			<div
				animate:flip="{{ duration: flipDurationMs }}"
				style="margin-bottom: 30px"
			>
				<TemplateInput
					{index}
					dragger={startDrag}
					bind:globalIndex
					bind:items
					bind:type={item.type}
					bind:label={item.label}
					bind:options={item.options}
					bind:placeholder={item.placeholder}
					bind:uploadAccept={item.uploadAccept}
					bind:uploadMultiple={item.uploadMultiple}
					bind:required={item.required}
				/>
			</div>
		{/each}
	</section>
{:else}
	<div class="text-align-center" style="margin-top: 20px">
		<DescriptivePlaceholder
			title="Add an input"
			description="Begin designing your template by adding an interactable input"
			link={{
				label: 'Learn more about templates...',
			}}
		>
			<Icon f7="textbox" color="blue" style="font-size: 45px; margin-bottom: 20px" />
		</DescriptivePlaceholder>
	</div>
{/if}
<div style={`${items.length > 0 ? 'margin-left: 70px;margin-top: 40px;' : ''}`}>
	<Add
		style="margin-top: 30px"
		on:click={() => {
			items = [
				...items,
				{
					id: globalIndex,
					type: TemplateInputType.Text,
					label: '',
					placeholder: '',
					required: false,
					options: [''],
					uploadAccept: TemplateInputUploadType.All,
					uploadMultiple: true,
				},
			];
			globalIndex += 1;
		}}
	/>
</div>