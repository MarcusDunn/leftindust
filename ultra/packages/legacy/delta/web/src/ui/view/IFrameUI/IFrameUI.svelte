<script lang="ts">
  import type { Path } from '../../../modules/NavigationModule';
  import type { Swiper as SwiperType } from 'swiper';
  import {
    Swiper,
    SwiperSlide,
    View,
    Link,
    Icon,
  } from 'framework7-svelte';
    
  export let views: {
    url: (Path | string);
    selected?: boolean;
  }[];

  export let url: Path | string | undefined = undefined;

  let swiper: SwiperType | undefined;
  const setSwiper = (event: CustomEvent<[SwiperType]>) => [swiper] = event.detail;

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

<div class="ultra-iframe">
  <div class="ultra-iframe-container">
    {#if views.length > 1}
      <Link
        class={`ultra-iframe-up ${isBeginning ? 'disabled' : ''}`}
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
            name={'test'}
          />
        </SwiperSlide>
      {/each}
    </Swiper>
    {#if views.length > 1}
      <Link
        class={`ultra-iframe-down ${isEnd ? 'disabled' : ''}`}
        on:click={() => swiper?.slideNext()}
      >
        <Icon f7="chevron_compact_down" />
      </Link>
    {:else}
      <p />
    {/if}
  </div>
</div>
