<script lang="ts">
  import type { View as ViewType, Popover as PopoverType } from 'framework7/types';
  import type { AppViews } from '../../../modules/AppModule';
  
  import { Popover, View, Button } from 'framework7-svelte';
  
  import { onMount } from 'svelte';
  import language from '../../../languages';
  import { safeNavigate } from '../../../modules/NavigationModule';
  
  import FooterUI from '../../../ui/layout/FooterUI/FooterUI.svelte';
  
  export let url: string;
  export let view: AppViews;

  export let props: Record<string, unknown> = {};
  export let color = 'primary';

  export let instance: PopoverType.Popover | undefined = undefined;

  let router: ViewType.View['router'];

  let modalRef: Popover;
  let viewRef: View;

  onMount(() => {
    instance = (<{ instance: () => PopoverType.Popover }>(modalRef as unknown)).instance();
    router = (<{ instance: () => ViewType.View }>(<unknown>viewRef)).instance().router;

    instance?.on('open', () => {
      router.navigate(url, {
        force: true,
        ignoreCache: true,
        props: {
          ...props,
          quicklook: true,
        },
      });
    });

    instance?.on('closed', () => {
      router.navigate('/blank/', {
        force: true,
        ignoreCache: true,
      });
    });
  });
</script>

<Popover
  class="ultra-quicklook"
  bind:this={modalRef}
>
  <View
    stackPages
    iosSwipeBack={false}
    animate={false}
    url="/blank/"
    bind:this={viewRef}
  >
    <FooterUI>
      <p />
      <div style="float: right">
        <slot name="footer" /> 
        <Button
          {color}
          fill
          round
          style="width: 200px"
          on:click={() => void safeNavigate(url, view)}
          popoverClose
        >
          {language().buttons.viewFile.text}
        </Button>
        <p />
      </div>
       
    </FooterUI>
  </View>
</Popover>