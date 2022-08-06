<script lang="ts">
  import type { View as ViewType, Popup as PopupType } from 'framework7/types';
  import type { AppViews } from '@/features/App';
  
  import { View, Button, Popup } from 'framework7-svelte';
  
  import { onMount } from 'svelte';
  import { _ } from '@/language';
  
  import Footer from '@/features/UI/components/Footer/Footer.svelte';

  import './Quicklook.scss';
  
  export let url: string;

  export let props: Record<string, unknown> = {};
  export let color = 'primary';

  export let view: AppViews;

  export let instance: PopupType.Popup | undefined = undefined;

  let router: ViewType.View['router'];

  let modalRef: Popup;
  let viewRef: View;

  onMount(() => {
    instance = (<{ instance: () => PopupType.Popup }>(modalRef as unknown)).instance();
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

<Popup
  class="view-quicklook"
  bind:this={modalRef}
>
  <View
    stackPages
    iosSwipeBack={false}
    animate={false}
    url="/blank/"
    bind:this={viewRef}
  >
    <Footer>
      <p />
      <div style="float: right; margin: 0 20px">
        <slot name="footer" /> 
        <Button
          {color}
          fill
          round
          style="width: 200px"
          popupClose
        >
          {$_('generics.viewFile')}
        </Button>
        <p />
      </div>
    </Footer>
  </View>
</Popup>