<script lang="ts" context="module">
  enum AttachmentsTabs {
    Attributes = 'Attributes',
    Attachments = 'Attachments',
  }
</script>

<script lang="ts">
  import type {
    Popover as PopoverType,
    Popup as PopupType,
    View as ViewType,
  } from 'framework7/types';
  import type { Writable } from 'svelte/store';

  import type { Selectable } from '../../modules/SelectModule';
  import type { Plugin, PluginComparables } from '../../plugins';

  import {
    Popover,
    Popup,
    View,
    Tabs,
    Tab,
    Block,
    Row,
    Col,
    ListItem,
    Icon,
    f7,
  } from 'framework7-svelte';
  
  import { createEventDispatcher } from 'svelte';
  import { writable } from 'svelte/store';

  import { SpecificEngine } from '../../engines';

  import Plugins from '../../plugins';

  import language from '../../languages';
  import { onMount } from 'svelte';

  import AppBarUI from '../../ui/controller/AppBarUI/AppBarUI.svelte';
  import MenuButtonUI from '../../ui/button/MenuButtonUI/MenuButtonUI.svelte';
  import InsetListUI from '../../ui/list/InsetListUI/InsetListUI.svelte';
  import BoxedUI, { BoxedUISizes } from '../../ui/surface/BoxedUI/BoxedUI.svelte';
  import PageUI from '../../ui/layout/PageUI/PageUI.svelte';

  const dispatch = createEventDispatcher();

  export let openIn: 'Popover' | 'Popup' = 'Popover';

  export let attachments: Writable<Selectable[]> = writable([]);
  export let types: PluginComparables[] = [];
  export let multiple = true;

  // Force main screen even if only one type is allowed 
  export let forceMainScreen = false;

  export let props: Record<string, unknown> | undefined = undefined;

  export let reference: Selectable | undefined = undefined; 

  export let instance: PopoverType.Popover | PopupType.Popup | undefined = undefined;

  let temporaryAttachments: Writable<string[]> = writable<string[]>([]);

  let plugins: Plugin[] = Object.keys(Plugins.attachments).map((key) => {
    if (types.some((type) => Plugins.attachments[key].comparables?.includes(type)) || types.length === 0) {
      return Plugins.attachments[key];
    }
  })
    .filter((plugin): plugin is Plugin => !!plugin);

  let view: ViewType.View;
  let tab: AttachmentsTabs = AttachmentsTabs.Attachments;

  let modalRef: unknown;
  let viewRef: View;

  $attachments = $attachments.map((attachment) => ({
    type: attachment.type,
    id: attachment.id,
  }));

  const component = (() => {
    switch (openIn) {
      case 'Popover':
        return Popover;
      case 'Popup':
        return Popup;
      default:
        return Popover;
    }
  })();

  const getReferences = (newAttachments: Selectable[]) => {
    SpecificEngine(newAttachments).subscribe((values) => {
      dispatch('fragment', values);
    });
  };

  const submit = () => {
    const newAttachments = $temporaryAttachments.map((attachment) => <Selectable>JSON.parse(attachment));

    $attachments = newAttachments;

    dispatch('change', newAttachments);

    getReferences(newAttachments);
    
    f7.popover.close();
  };

  const navigate = (url: string, extraProps?: Record<string, unknown>) => view.router.navigate(url, {
    props: {
      attachments: temporaryAttachments,
      multiple,
      submit,
      reference,
      ...extraProps,
      ...props,
    },
  });

  getReferences($attachments);

  onMount(() => {
    instance = (<{ instance: () => PopoverType.Popover | PopupType.Popup }>modalRef).instance();
    view = (<{ instance: () => ViewType.View }>(<unknown>viewRef)).instance();

    if (plugins.length === 1 && !forceMainScreen) navigate(`/${0}/`, { back: false });

    ['popoverOpen', 'popupOpen'].forEach((event) => {
      // @ts-expect-error
      instance?.on(event, () => {
        $temporaryAttachments = $attachments.map((attachment) => JSON.stringify(attachment));
      });
    });

    ['popoverClosed', 'popupClosed'].forEach((event) => {
      // @ts-expect-error
      instance?.on(event, () => {
        $temporaryAttachments = [];
        // For now, we should be able to safely assume that attachment pages
        // will not go beyond the root page so going back 1 page should bring us
        // to the main screen
        if (plugins.length !== 1) view?.router.back();
      });
    });
  });

</script>

<style lang="scss">
  $popoverWidth: 900px;
  $popoverHeight: 420px;

  :global(.ultra-component-attachments-popover) {
    width: $popoverWidth;
    height: $popoverHeight;

    & :global(.view) {
      height: $popoverHeight;

      & :global(.segmented) {
        width: 250px;
      }
    }
  }
</style>

<svelte:component
  class={`
    ultra-component-attachments-modal 
      ${openIn === 'Popover' ? 'ultra-component-attachments-popover' : 'ultra-component-attachments-popup'}
  `}
  this={component}
  bind:this={modalRef}
>
  <div class={`ultra-component-attachments-modal-${openIn}`}>
    <View
      stackPages
      iosSwipeBack={false}
      animate={false}
      bind:this={viewRef}
      routesAdd={plugins.map(({ plugin }, index) => ({
        path: `/${index}/`,
        component: plugin,
      }))}
    >
      <PageUI>
        <AppBarUI
          navigation={{ close: { popover: true } }}
          slot="fixed"
        >
          <svelte:fragment slot="right">
            <MenuButtonUI
              title={language().buttons.done.text}
              icon={{ f7: 'checkmark_circle_fill', color: 'purple' }}
              on:click={submit}
            />
          </svelte:fragment>
        </AppBarUI>
        <Tabs>
          <Tab tabActive={tab === AttachmentsTabs.Attachments}>
            <Block>
              <h4>Categories</h4>
              <Row class="no-gutter">
                {#each plugins as plugin, index}
                  <Col width="50" xlarge="33">
                    <InsetListUI>
                      <ListItem
                        title={plugin.properties?.title}
                        text={plugin.properties?.description ?? language().placeholders.noDescription.text}
                        link
                        on:click={() => navigate(`/${index}/`, { back: true })}
                      >
                        <BoxedUI
                          slot="media"
                          color={plugin.properties?.color ?? 'gray'}
                          constraints={BoxedUISizes.Small}
                          fill
                        >
                          <Icon {...plugin.properties?.icon} />
                        </BoxedUI>
                      </ListItem>
                    </InsetListUI>
                  </Col>
                {/each}
              </Row>
            </Block>
          </Tab>
          <Tab tabActive={tab === AttachmentsTabs.Attributes} />
        </Tabs>
      </PageUI>
    </View>
  </div>
</svelte:component>
