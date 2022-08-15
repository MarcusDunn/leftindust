<script lang="ts">
  import { _ } from '@/language';
  import { Col, Row } from 'framework7-svelte';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import type { SurveyTemplateCategory, SurveyTemplateInput, CreateSurveyTemplateSection } from '@/api/server';
  
  export let sections: CreateSurveyTemplateSection[];

  let inputs: (SurveyTemplateInput & {
    originalSectionIndex: number;
  })[] = [];

  const getValue = (selectedCategory: SurveyTemplateCategory) => inputs.filter(({ category }) => category === selectedCategory)[0]?.id ?? 'default';
  
  const onChange = (value: string | number, selectedCategory: SurveyTemplateCategory, keepOld?: boolean) => {
    const resetInputIndex = inputs.findIndex(({ category }) => category === selectedCategory);

    if (resetInputIndex >= 0) {
      const resetSectionIndex = inputs[resetInputIndex].originalSectionIndex;
      const resetIndex = sections[resetSectionIndex].inputs.findIndex(({ category }) => category === selectedCategory);
  
      if (resetIndex >= 0 && !keepOld) sections[resetSectionIndex].inputs[resetIndex].category = undefined;
    }
    
    const inputIndex = inputs.findIndex(({ calculationId }) => calculationId === value);
    const sectionIndex = inputs[inputIndex]?.originalSectionIndex ?? -1;
    const index = sections[sectionIndex]?.inputs?.findIndex(({ id }) => id === value);

    if (index >= 0) sections[sectionIndex].inputs[index].category = selectedCategory;
  };

  $: inputs = sections.flatMap((section, sectionIndex) => section.inputs.map((input, inputIndex) => ({
    ...input,
    originalSectionIndex: sectionIndex,
    label: `${input.label || $_('generics.inputIndexed', { values: { number: inputIndex + 1 } })}${sections.length > 1
      ? ` (${$_('generics.sectionIndexed', { values: { number: sectionIndex + 1 } })})` : ''}`,
  })));
</script>

<Row>
  <Col width="100" small="50">
    <Select
      title={$_('generics.date')}
      options={[
        {
          text: $_('generics.creationDate'),
          value: 'default',
        },
        ...inputs
          .filter(({ category }) => category ? category === SurveyTemplateCategory.Date : !category)
          .filter(({ type }) =>
            type === TemplateInputType.Date,
          )
          .map((input) => ({
            text: input.label,
            value: input.id,
          })),
      ]}
      value={getValue(SurveyTemplateCategory.Date)}
      on:change={({ detail: value }) => onChange(value, SurveyTemplateCategory.Date)}
    />
    <br />
  </Col>
  <Col width="100" small="50">
    <Select
      title={$_('generics.title')}
      options={[
        {
          text: $_('generics.default'),
          value: 'default',
        },
        ...inputs
          .filter(({ category }) => category ? category === SurveyTemplateCategory.Title : !category)
          .filter(({ type }) =>
            type === TemplateInputType.Text
              || type === TemplateInputType.Number
              || type === TemplateInputType.SingleSelect
              || type === TemplateInputType.MultiSelect,
          )
          .map((input) => ({
            text: input.label,
            value: input.id,
          })),
      ]}
      value={getValue(SurveyTemplateCategory.Title)}
      on:change={({ detail: value }) => onChange(value, SurveyTemplateCategory.Title)}
    />
    <br />
  </Col>
  <Col width="100">
    <Select
      title={$_('generics.body')}
      options={[
        {
          text: $_('generics.default'),
          value: 'default',
        },
        ...inputs
          .filter(({ category }) => category ? category === SurveyTemplateCategory.Body : !category)
          .filter(({ type }) =>
            type === TemplateInputType.Text
              || type === TemplateInputType.Number
              || type === TemplateInputType.SingleSelect
              || type === TemplateInputType.MultiSelect
              || type === TemplateInputType.Paragraph,
          )
          .map((input) => ({
            text: input.label,
            value: input.id,
          })),
      ]}
      value={getValue(SurveyTemplateCategory.Body)}
      on:change={({ detail: value }) => onChange(value, SurveyTemplateCategory.Body)}
    />
    <br />
    <br />
  </Col>
</Row>
