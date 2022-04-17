<script lang="ts">
  import type { Swiper as SwiperType } from 'swiper';
  import {
    Swiper,
    SwiperSlide,
    View,
    Link,
    Icon,
  } from 'framework7-svelte';

  import './IFrame.scss';
    
  export let views: {
    url: string;
    selected?: boolean;
  }[];

  export let url: string | undefined = undefined;

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
  
  $: swiper?.on('slideChange', () => {
    updateSwiperButtons();
  });

  // @ts-expect-error
  $: swiper, updateSwiperButtons();

  let index = views.map((view) => view.selected).indexOf(true);
</script>

<div class="view-iframe">
  <div class="view-iframe-container">
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
      {#each views as view}
        <SwiperSlide>
          <View
            url={view.selected && url ? url : view.url}
            stackPages={true}
            iosSwipeBack={false}
            animate={false}
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
