<script lang="ts">
  import type { WidgetGridWidget, WidgetGridProps, SvelteGridConstraints } from '.';
  import type { WidgetType } from '../..';

  import Widgets from '../..';

  import { WidgetGridCols } from '.';

  import Grid from './Grid.svelte';

  // These props will be scoped to each specific component instead of overall
  // to every widget
  export let props: (WidgetGridProps | undefined)[];
  export let type: WidgetType;

  export let fixed = false;
  export let cols = WidgetGridCols;
  
  export let dynamicGap = false;

  export let store: Record<string, Record<string, SvelteGridConstraints>> | boolean = false;

  const widgets: WidgetGridWidget[] = Object.keys(Widgets[type])
    .flatMap((key) =>
      props.map((prop) => {
        if (prop) {
          const { id, ...restProps } = prop;
          const widget = Widgets[type][key];
        
          if ((!widget.type || widget.type.includes(id)) && restProps.data && !Array.isArray(restProps.data)) {
            return {
              id: restProps.data.id,
              component: widget.component,
              props: restProps,
            } as WidgetGridWidget;
          }
        }
      }))
    .filter((widget): widget is WidgetGridWidget => !!widget);

  console.log(widgets);
</script>

<Grid
  {widgets}
  {type}
  {fixed}
  {cols}
  {dynamicGap}
  bind:store
/>