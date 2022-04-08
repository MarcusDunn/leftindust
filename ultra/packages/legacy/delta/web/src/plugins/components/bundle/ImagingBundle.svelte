<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';

  import {
    Button,
    Col,
    Row,
    Swiper,
    SwiperSlide,
  } from 'framework7-svelte';

  import mri from '../../../assets/temp/mri.jpg';
  import xray from '../../../assets/temp/xray.jpg';
  import language from '../../../languages/index';

  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';

  export let selectable: Selectable;
  export let dragger: () => void | undefined;
  export let properties: NonNullable<Plugin['properties']>;

  const theme = 'lightblue';
</script>

<style lang="scss">
  div {
    display: flex;
    justify-content: center;
    margin-top: 10px;
    width: 100%;

    & :global(.swiper) {
      width: 100%;
      height: auto;
      margin: 0px 5px;
      overflow-y: scroll;
    }

    & :global(.swiper-wrapper) {
      width: 100%;
    }

    img {
      border-radius: 8px;
      width: 100%;
      height: 180px;
      object-fit: cover;
      margin-bottom: 20px;
    }
  }
</style>

<PluginBundleUI
  title={properties.title}
  icon={properties.icon}
  color={properties.color}
  {dragger}
  shadow
>
  <div>
    <Swiper
      slidesPerView={1}
      spaceBetween={20}
    >
      <SwiperSlide>
        <img src={xray} alt="XRAY" />
      </SwiperSlide>
      <SwiperSlide>
        <img src={mri} alt="MRI" />
      </SwiperSlide>
    </Swiper>
  </div>
  <Row slot="controls">
    <Col width="50">
      <Button
        color={properties.color}
        fill
        round
      >
        {language().records.imaging.buttons.newImage.text}
      </Button>
    </Col>
    <Col width="50">
      <Button
        color={properties.color}
        round
        outline
      >
        {language().buttons.viewAll.text}
      </Button>
    </Col>
  </Row>
</PluginBundleUI>
