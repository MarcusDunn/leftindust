<script lang="ts">
  import type {
    PluginGridUIPlugin,
    PluginGridUIProps,
    SvelteGridConstraints,
  } from '../../ui/layout/PluginGridUI/module';
  
  import type { Selectable } from '../../modules/SelectModule';

  import type { PluginTypes } from '../../plugins';

  import Plugins from '../../plugins';

  import { PluginGridUICols } from '../../ui/layout/PluginGridUI/module';

  import PluginGridUI from '../../ui/layout/PluginGridUI/PluginGridUI.svelte';

  // These props will be scoped to each specific component instead of overall
  // to every plugin
  export let props: (PluginGridUIProps | undefined)[];
  export let type: PluginTypes;

  export let fixed = false;
  export let cols = PluginGridUICols;
  
  export let dynamicGap = false;

  export let store: Record<string, Record<string, SvelteGridConstraints>> | boolean = false;

  const plugins: PluginGridUIPlugin[] = Object.keys(Plugins[type])
    .flatMap((key) => 
      props.map((prop) => {
        if (prop) {
          const { id, ...strippedProp } = prop;
          const plugin = Plugins[type][key];
        
          if ((!plugin.comparables || plugin.comparables.includes(id)) && 'selectable' in strippedProp) {
            return <PluginGridUIPlugin>{
              id: (<Selectable>strippedProp.selectable).id,
              plugin: plugin.plugin,
              properties: plugin.properties,
              props: strippedProp,
            };
          }
        }
      }))
    .filter((plugin): plugin is PluginGridUIPlugin => !!plugin);
</script>

<PluginGridUI
  {plugins}
  {type}
  {fixed}
  {cols}
  {dynamicGap}
  bind:store
/>