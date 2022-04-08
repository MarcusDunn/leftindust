<script lang="ts">
  import type { Swiper as SwiperType } from 'swiper';
  import {
    Segmented,
    Button,
    Tabs,
    Tab,
    Swiper,
    SwiperSlide,
  } from 'framework7-svelte';
  import { _ } from 'svelte-i18n';
  import { TemplateInputItems, TemplateSelectedTab } from './store';
  import Input from '../Input/Input.svelte';
  
  import IFrame from '../View/components/IFrame/IFrame.svelte';
  import WizardSplit from '../Wizard/components/WizardSplit/WizardSplit.svelte';
  import TemplateInputs from './components/TemplateInputs/TemplateInputs.svelte';
  import TemplateCategoryInputs from './components/TemplateInputs/TemplateCategoryInputs.svelte';
  import Add from '../Input/components/Add/Add.svelte';

  let swiper: SwiperType | undefined;
  const setSwiper = (event: CustomEvent<[SwiperType]>) => [swiper] = event.detail;

</script>

<WizardSplit
  title="New Template"
  subtitle="Create  a new template"
  color="deeppurple"
>
  <svelte:fragment slot="appbar">
    <Segmented strong style="width: 200px;">
      <Button
        active={$TemplateSelectedTab === 'input'}
        on:click={() => ($TemplateSelectedTab = 'input')}
      >
        {$_('generics.input')}
      </Button>
      <Button
        active={$TemplateSelectedTab === 'output'}
        on:click={() => ($TemplateSelectedTab = 'output')}
      >
        {$_('generics.output')}
      </Button>
    </Segmented>
  </svelte:fragment>
  <Input
    title={$_('generics.title')}
    clear
    style="width: 100%"
  >
    <input type="text" placeholder="Eg. Blood Sample" bind:value={$TemplateInputItems.title} />
  </Input>
  <br />
  <Input
    title={$_('generics.description')}
    clear
  >
    <input type="text" placeholder="Additional" bind:value={$TemplateInputItems.subtitle} />
  </Input>
  <Tabs>
    <Tab tabActive={$TemplateSelectedTab === 'input'}>
      <br />
      <br />
      <Swiper
        allowTouchMove={false}
        observer
        observeParents
        autoHeight
        on:swiper={setSwiper}
      >
        <SwiperSlide>
          <TemplateInputs bind:items={$TemplateInputItems.items} />
        </SwiperSlide>
      </Swiper>
      <br />
      <br />
      <br />
      <Add placeholder="Add Section" />
    </Tab>
    <Tab tabActive={$TemplateSelectedTab === 'output'}>
      <br />
      <br />
      <TemplateCategoryInputs bind:inputs={$TemplateInputItems.items} />
    </Tab>
  </Tabs>
  
  <svelte:fragment slot="detail">
    <IFrame
      views={[{
        url: '/template/preview/',
        selected: true,
      }]}
    />
  </svelte:fragment>
</WizardSplit>