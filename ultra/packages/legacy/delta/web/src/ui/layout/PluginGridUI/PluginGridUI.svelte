<script lang="ts">
  import type { Dimensions } from '../../../types/helpers';
  import type { PluginTypes } from '../../../plugins';
  import type {
    SvelteGridConstraints,
    PluginGridUIItem,
    PluginGridUIPlugin,
    PluginGridUIProps,
  } from './module';

  import { f7 } from 'framework7-svelte';
  import { PluginGridUICols, PluginGridUIGenerateConstraints, PluginGridUISizes } from './module';

  // @ts-ignore
  import Grid from 'svelte-grid';

  import deepmerge from 'deepmerge';

  import { ACCOUNT } from '../../../store';

  export let plugins: PluginGridUIPlugin[];

  // Props are universal and will be applied to all components in
  // the grid
  export let props: PluginGridUIProps | undefined = undefined;

  export let type: PluginTypes;
  export let fixed = false;

  export let cols = PluginGridUICols;
  export let size: Dimensions | undefined = undefined;

  export let dynamicGap = false;

  let col = 0;

  // Pass in custom user saved plugin grid layouts (optional)
  export let store: Record<string, Record<string, SvelteGridConstraints>> | boolean = false;

  let items: PluginGridUIItem[] = plugins.map((plugin, index) => (<PluginGridUIItem>{
    id: plugin.id,
    plugin: plugin.plugin,
    properties: plugin.properties,
    props: {
      ...props,
      ...plugin.props,
    },
    ...(() => {
      const constraints = PluginGridUIGenerateConstraints(
        index,
        {
          ...(plugin?.constraints?.w && plugin?.constraints?.h ?
            { w: plugin.constraints.w, h: plugin.constraints.h }
            : PluginGridUISizes[type]),
          ...size,
        },
      );

      if (props && Object.keys($ACCOUNT.database.layout.grid[type][props.id]?.[plugin.id] || []).length > 0) {
        // Merge Svelte Grid constraints with saved constraints in fb
        return deepmerge(
          // Merge general constraints with plugin specific constraints
          constraints,
          // Firebase cannot internally differenciate between arrays and objects, and due to the fact
          // that layout items are being stored with a numbered index, firebase stores it as an array
          // and therefore returns the values back as undefined if the object has missing indexes
          // eg. { 1: value, 2: value, 4: value } will return back as { 1: value, 2: value, 3: undefined, 4: value }
          Object.fromEntries(Object.entries(store || $ACCOUNT.database.layout.grid[type][props.id][plugin.id])
            .map(([key, value]) => [key, value])),
        );
      }

      return constraints;
    })(),
  }));

  console.log(items);

  let previousItems = items;

  const gridChange = (event: CustomEvent<PluginGridUIItem & { cols: number }>) => {
    col = event.detail.cols;
  };

  const gridUpdate = (event: CustomEvent<PluginGridUIItem & { cols: number }>) => {
    col = event.detail.cols;
    
    const rowSize: number = Math.floor(items.length / col);

    let item = items.filter((value) => value.id === event.detail.id)[0];

    const x = item[col].x;
    const y = item[col].y;
    const w = item[col].w;
    const h = item[col].h;

    const index = (y / h) * col + x;

    // If the y of the new items is greater than the rowSize
    // OR the index of the new item is larger than the items length
    if (Math.floor(y / col) > rowSize || index >= items.length) {
      // Revert to previous
      items = previousItems;
    } else {
      // Recalculate the positioning for each item col so the items retains position
      // when the screen is resized
      const newItems = items.filter((value) => value.id !== event.detail.id);
      items = [...newItems.slice(0, index), item, ...newItems.slice(index)].map((value, i) => (<PluginGridUIItem>{
        ...value,
        ...PluginGridUIGenerateConstraints(i, { w, h }),
      }));

      previousItems = items;

      // Update grid state
      /*
      Stored as:
      {
        PLUGIN_NAME (ID): {
          COL (Constraints set by PluginGridUICols): { x, y, w, h }
        }
      }
      */
      const newGridState = Object.fromEntries(
        items.map((newItem) => [newItem.id, Object.keys(newItem)
          .filter((key) => !(['id', 'plugin', 'props'].includes(key)))
          .reduce((obj: Record<string, SvelteGridConstraints>, key) => {
            obj[key] = {
              x: newItem[key].x,
              y: newItem[key].y,
              w: newItem[key].w,
              h: newItem[key].h,
            };
            return obj;
          }, {})],
        ),
      );

      // Update grid state
      /*
      if (Boolean(store) && store && props) {
        $ACCOUNT.database.layout.grid[type][props.id] = newGridState;
      } else if (store) {
        store = newGridState;
      }
      */
    }
  };

  // Calculate grid spacing so plugins don't show up with unbalanced gaps
  // TODO: this is sort of broken right now
  const computeGridSpacing = (item: PluginGridUIItem) => {
    let widthOffset = (12 ^ col) / col;
    f7.$('.ultra-component-plugin-grid-item').forEach((node) => {
      if (node.parentElement)
        node.parentElement.style.width = `${(node.parentElement?.parentElement?.clientWidth ?? 0) / col - widthOffset}px`;
    });
    return `padding-left: ${((items.indexOf(item) + 1) % col === 1 || col === 1) ? '0' : `${widthOffset}px`}`;
  };
</script>

<div class="ultra-component-plugin-grid">
  {#if items}
    <Grid
      bind:items
      let:dataItem
      let:movePointerDown
      rowHeight={90}
      {cols}
      fillSpace
      fastStart
      on:pointerup={gridUpdate}
      on:mount={gridChange}
      on:resize={gridChange}
      gap={dynamicGap ? [0, 10] : [10, 10]}
    >
      <div
        class="ultra-component-plugin-grid-item"
        style={(() => {
          col;
          // TODO: Dynamic grid is acting up
          // if (dynamicGap) return computeGridSpacing(dataItem);
        })()}
      >
        <svelte:component
          this={dataItem.plugin}
          {...props}
          {...dataItem.props}
          properties={dataItem.properties}
          dragger={fixed ? undefined : movePointerDown}
        />
      </div>
    </Grid>
  {/if}
</div>
