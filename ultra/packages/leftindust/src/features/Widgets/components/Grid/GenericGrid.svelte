<script lang="ts">
  import type { Dimensions } from '@/types';
  import type { WidgetGridWidget, WidgetGridProps, SvelteGridConstraints } from '.';
  import type { ResolversTypes } from '@/api/server';

  import type { WidgetCategory, WidgetType } from '../..';

  import Widgets from '../..';

  import { WidgetGridCols } from '.';

  import Grid from './Grid.svelte';

  export let props: WidgetGridProps;
  export let type: WidgetType;

  export let dataType: (keyof Partial<ResolversTypes>)[] = [];
  export let category: WidgetCategory[] = [];

  export let fixed = false;
  export let cols = WidgetGridCols;
  export let size: Dimensions | undefined = undefined;

  export let dynamicGap = false;

  export let store: Record<string, Record<string, SvelteGridConstraints>> | boolean = false;

  const widgets: WidgetGridWidget[] = Object.keys(Widgets[type])
    .map((id) => {
      const widget = Widgets[type][id];
      if (
        // Check to see if Widgets are of the same comparable type
        (dataType.length === 0
        || (!widget.type || dataType.some((input) => widget.type?.includes(input))))
        // Check to see if Widget catagories are the same
        && (category.length === 0
        || (!widget.category || category.some((input) => widget.category?.includes(input))))
      ) {
        return {
          id,
          ...widget,
        };
      }
    })
    .filter((widget): widget is WidgetGridWidget => !!widget);

</script>

<Grid
  {widgets}
  {type}
  {props}
  {fixed}
  {cols}
  {size}
  {dynamicGap}
  bind:store
/>
