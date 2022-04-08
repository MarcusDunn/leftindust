<script lang="ts">
  import type { Dimensions } from '../../types/helpers';
  import type {
    PluginGridUIPlugin,
    PluginGridUIProps,
    SvelteGridConstraints,
  } from '../../ui/layout/PluginGridUI/module';

  import type { PluginCategories, PluginComparables, PluginTypes } from '../../plugins';

  import Plugins from '../../plugins';

  import { PluginGridUICols } from '../../ui/layout/PluginGridUI/module';

  import PluginGridUI from '../../ui/layout/PluginGridUI/PluginGridUI.svelte';

  export let props: PluginGridUIProps;
  export let type: PluginTypes;

  export let comparables: PluginComparables[] = [];
  export let categories: PluginCategories[] = [];

  export let fixed = false;
  export let cols = PluginGridUICols;
  export let size: Dimensions | undefined = undefined;

  export let dynamicGap = false;

  export let store: Record<string, Record<string, SvelteGridConstraints>> | boolean = false;

  const plugins: PluginGridUIPlugin[] = Object.keys(Plugins[type])
    .map((id) => {
      const plugin = Plugins[type][id];
      if (
        // Check to see if plugins are of the same comparable type
        (comparables.length === 0
        || (!plugin.comparables || comparables.some((input) => plugin.comparables?.includes(input))))
        // Check to see if plugin catagories are the same
        && (categories.length === 0
        || (!plugin.categories || categories.some((input) => plugin.categories?.includes(input))))
      ) {
        return {
          id,
          ...plugin,
        };
      }
    })
    .filter((plugin): plugin is PluginGridUIPlugin => !!plugin);

</script>

<PluginGridUI
  {plugins}
  {type}
  {props}
  {fixed}
  {cols}
  {size}
  {dynamicGap}
  bind:store
/>
