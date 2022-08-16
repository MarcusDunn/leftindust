<script lang="ts">
  import type { templateForm, TemplateSchema } from '../..';
  import Section from '@/features/UI/components/Section/Section.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';

  import './TemplateSection.scss';
  
  import { TemplateIndex, Template } from '../../store';
  import { Icon } from 'framework7-svelte';

  import { _ } from 'svelte-i18n';
  import type { CreateSurveyTemplateSection } from '@/api/server';
  import TemplateSectionInputs from './TemplateSectionInputs.svelte';

  export let index: number;
  export let section: TemplateSchema['sections'][number];
  export let sections: TemplateSchema['sections'];

  export let errors: ReturnType<typeof templateForm>['errors'];
  export let data: ReturnType<typeof templateForm>['data'];

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

//TODO: Add back once type id is on server
  /*
  $: if ($data?.sections?.[index] && typeof $data?.sections?.[index]?.id === 'undefined')
    $data.sections[index].id = section.id;
    */
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
          bind:globalIndex
          {index}
          {errors}
          {data}
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
        bind:title={$Template.title}
        bind:subtitle={$Template.subtitle}
        bind:inputs={section.inputs}
        bind:globalIndex
        {errors}
        {data}
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
          {
            ...sections[index],
            // id: globalIndex,
            inputs: section.inputs.map((input) => {
              globalIndex += 1;
              return { ...input /* id: globalIndex */ };
            }),
          },
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