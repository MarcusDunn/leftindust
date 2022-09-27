<script lang="ts">
  import type { Swiper as SwiperType } from 'swiper';
  import {
    Swiper,
    SwiperSlide,
    View,
    Link,
    Icon,
  } from 'framework7-svelte';
  import type { View as ViewType } from 'framework7/types';
  import { onMount } from 'svelte';

  import './IFrame.scss';
    
  export let views: {
    url: string;
    props?: Record<string, unknown>;
    selected?: boolean;
  }[];

  export let url: string | undefined = undefined;

  export let props: Record<string, unknown> = {};

  export let style = '';

  let viewRefs: (View | undefined)[] = views.map(() => undefined);
  let viewInstances: (ViewType.View | undefined)[] = views.map(() => undefined);

  let swiper: SwiperType | undefined;
  // TODO: Bumped F7 to v7 and type error came up. Investigate later
  // @ts-expect-error
  const setSwiper = (event: CustomEvent<void>) => ([swiper] = event.detail);

  let isBeginning: boolean;
  let isEnd: boolean;

  const updateSwiperButtons = () => {
    isBeginning = swiper?.isBeginning ?? true;
    isEnd = swiper?.isEnd ?? true;
  };

  const navigate = (options: { instance: ViewType.View, url: string, props: Record<string, unknown> }) => {
    const { instance, url, props } = options;
    instance.router.navigate(url, { props });
  };
  
  $: swiper?.on('slideChange', () => {
    updateSwiperButtons();
  });

  // @ts-expect-error
  $: swiper, updateSwiperButtons();

  let index = views.map((view) => view.selected).indexOf(true);

  onMount(() => {
    views.forEach((view, index) => {
      viewInstances[index] = (<{ instance: () => ViewType.View }>(<unknown>viewRefs[index])).instance();

      const currentInstance = viewInstances[index];

      if (currentInstance) {
        if (view.selected && url) {
          navigate({
            instance: currentInstance,
            url,
            props: { ...props, ...view.props },
          });
        } else {
          navigate({
            instance: currentInstance,
            url: view.url,
            props: { ...props, ...view.props },
          });
        }
      }
    });
  });     
</script>

<div class="view-iframe">
  <div class="view-iframe-container" {style}>
    {#if views.length > 1}
      <Link
        class={`view-iframe-up ${isBeginning ? 'disabled' : ''}`}
        on:click={() => swiper?.slidePrev()}
      >
        <Icon f7="chevron_compact_up" />
      </Link>
    {:else}
      <p />
    {/if}
    <Swiper
      direction="vertical"
      allowTouchMove={false}
      initialSlide={index}
      on:swiper={setSwiper}
    >
      {#each views as _, index}
        <SwiperSlide>
          <View
            stackPages
            iosSwipeBack
            animate={false}
            bind:this={viewRefs[index]}
          />
        </SwiperSlide>
      {/each}
    </Swiper>
    {#if views.length > 1}
      <Link
        class={`view-iframe-down ${isEnd ? 'disabled' : ''}`}
        on:click={() => swiper?.slideNext()}
      >
        <Icon f7="chevron_compact_down" />
      </Link>
    {:else}
      <p />
    {/if}
  </div>
</div>
