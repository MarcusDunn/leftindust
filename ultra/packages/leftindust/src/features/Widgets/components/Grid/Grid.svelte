<script lang="ts">
  import type { Dimensions } from '@/types';
  import type { WidgetType } from '../..';
  import type {
    SvelteGridConstraints,
    WidgetGridItem,
    WidgetGridWidget,
    WidgetGridProps,
  } from '.';

  import { f7 } from 'framework7-svelte';
  import { WidgetGridCols, WidgetGridGenerateConstraints, WidgetGridSizes } from '.';

  import './Grid.scss';

  import { Grid as SvelteGrid } from './svelte-grid';

  import deepmerge from 'deepmerge';

  import { account } from '@/features/Account/store';

  export let widgets: WidgetGridWidget[];

  // Props are universal and will be applied to all components in
  // the grid
  export let props: WidgetGridProps | undefined = undefined;

  export let type: WidgetType;
  export let fixed = false;

  export let cols = WidgetGridCols;
  export let size: Dimensions | undefined = undefined;

  export let dynamicGap = false;

  let col = 0;

  // Pass in custom user saved widget grid layouts (optional)
  export let store: Record<string, Record<string, SvelteGridConstraints>> | boolean = false;

  let items: WidgetGridItem[] = widgets.map((widget, index) => (<WidgetGridItem>{
    id: widget.id,
    component: widget.component,
    props: {
      ...props,
      ...widget.props,
    },
    ...(() => {
      const constraints = WidgetGridGenerateConstraints(
        index,
        {
          ...(widget?.constraints?.w && widget?.constraints?.h ?
            { w: widget.constraints.w, h: widget.constraints.h }
            : WidgetGridSizes[type]),
          ...size,
        },
      );

      if (props && Object.keys($account.database.layout.grid[type][props.id]?.[widget.id] || []).length > 0) {
        // Merge Svelte Grid constraints with saved constraints in fb
        return deepmerge(
          // Merge general constraints with widget specific constraints
          constraints,
          // Firebase cannot internally differenciate between arrays and objects, and due to the fact
          // that layout items are being stored with a numbered index, firebase stores it as an array
          // and therefore returns the values back as undefined if the object has missing indexes
          // eg. { 1: value, 2: value, 4: value } will return back as { 1: value, 2: value, 3: undefined, 4: value }
          Object.fromEntries(Object.entries(store || $account.database.layout.grid[type][props.id][widget.id])
            .map(([key, value]) => [key, value])),
        );
      }

      return constraints;
    })(),
  }));

  let previousItems = items;

  const gridChange = (event: CustomEvent<WidgetGridItem & { cols: number }>) => {
    col = event.detail.cols;
  };

  const gridUpdate = (event: CustomEvent<WidgetGridItem & { cols: number }>) => {
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
      items = [...newItems.slice(0, index), item, ...newItems.slice(index)].map((value, i) => (<WidgetGridItem>{
        ...value,
        ...WidgetGridGenerateConstraints(i, { w, h }),
      }));

      previousItems = items;

      // Update grid state
      /*
      Stored as:
      {
        PLUGIN_NAME (ID): {
          COL (Constraints set by WidgetGridCols): { x, y, w, h }
        }
      }
      */
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const newGridState = Object.fromEntries(
        items.map((newItem) => [newItem.id, Object.keys(newItem)
          .filter((key) => !(['id', 'component', 'props'].includes(key)))
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

    /*
        if (Boolean(store) && store && props) {
          $ACCOUNT.database.layout.grid[type][props.id] = newGridState;
        } else if (store) {
          store = newGridState;
        }
      */
    }
  };

  // Calculate grid spacing so widgets don't show up with unbalanced gaps
  // TODO: this is sort of broken right now
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const computeGridSpacing = (item: WidgetGridItem) => {
    let widthOffset = (12 ^ col) / col;
    f7.$('.widgets-grid-item').forEach((node) => {
      if (node.parentElement)
        node.parentElement.style.width = `${(node.parentElement?.parentElement?.clientWidth ?? 0) / col - widthOffset}px`;
    });
    return `padding-left: ${((items.indexOf(item) + 1) % col === 1 || col === 1) ? '0' : `${widthOffset}px`}`;
  };

  console.log(items);
</script>

<div class="widgets-grid">
  {#if items}
    <SvelteGrid
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
      <div class="widgets-grid-item">
        <svelte:component
          this={dataItem.component}
          {...props}
          {...dataItem.props}
          properties={dataItem.properties}
          dragger={fixed ? undefined : movePointerDown}
        />
      </div>
    </SvelteGrid>
  {/if}
</div>
