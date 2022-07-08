<script lang="ts">
  import type { TemplateSection } from '../..';
  import Section from '@/features/UI/components/Section/Section.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';

  import './TemplateSection.scss';
  
  import { TemplateIndex, TemplateInputItems } from '../../store';
  import { Icon } from 'framework7-svelte';

  import { _ } from 'svelte-i18n';
  import TemplateSectionInputs from './TemplateSectionInputs.svelte';

  export let index: number;
  export let section: TemplateSection;
  export let sections: TemplateSection[];

  export let globalIndex: number;

  export let dragger: ((event: Event) => void)| undefined = undefined;

  // TODO: Focus was disabled because it causes issues with the way active indexing is handled 
  // when sections are re-ordered
  const focus = (node: HTMLElement, index: number): void => {
    document.addEventListener('focusin', () => {
      if (node.contains(document.activeElement)) {
        $TemplateIndex = index;
      }
    }, true);
  };
</script>

<div
  class="templates-template_section"
  on:click={() => ($TemplateIndex = index)}
>
  {#if dragger}
    <div
      class="templates-template_section-controller"
      on:mousedown={dragger}
      on:touchstart={dragger}
    >
      <Icon f7="line_horizontal_3" class="text-color-gray" />
    </div>
  {/if}
  <div class="templates-template_section-section">
    {#if sections.length > 1}
      <Section
        title={$_('generics.sectionIndexed', { values: { number: index + 1 } })}
        active={$TemplateIndex === index}
      >
        <TemplateSectionInputs
          bind:title={section.title}
          bind:subtitle={section.subtitle}
          bind:inputs={section.inputs}
          {index}
        />
      </Section>
    {/if}

    <!--
      Display none is used here instead of a conditional statment because for whatever reason
      svelte will model store values to be undefined on re-entering the DOM leading to undefined values
      where there shouldn't be
    -->
    <div style={sections.length === 1 ? '' : 'display: none;'}>
      <TemplateSectionInputs
        bind:title={$TemplateInputItems.title}
        bind:subtitle={$TemplateInputItems.subtitle}
        bind:inputs={section.inputs}
      />
    </div>
  </div>
</div>
<br />
{#if sections.length > 1}
  <div class="display-flex">
    <div class="flex-grow" />
    <MenuButton
      title={$_('generics.clone')}
      icon={{
        f7: 'square_on_square',
        color: 'gray',
      }}
      on:click={() => {
        sections = [
          ...sections.slice(0, index),
          { ...sections[index], id: globalIndex },
          ...sections.slice(index),
        ];

        globalIndex += 1;
      }}
    />
    <MenuButton
      title={$_('generics.delete')}
      disabled={sections.length <= 1}
      icon={{
        f7: 'minus_circle_fill',
        color: 'red',
      }}
      on:click={() => {
        sections = sections.filter((_, i) => i !== index);
      }}
    />
  </div>
{/if}