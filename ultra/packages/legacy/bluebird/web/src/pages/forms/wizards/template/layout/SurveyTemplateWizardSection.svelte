<script lang="ts">
import type { FormFieldInput } from '@framework/schemas/leftindust.schema';
import type { Popover as PopoverType } from 'framework7/types';
import type { MenuUIItems } from '@framework/ui/controller/MenuUI/MenuUI.svelte';
import type { Swiper } from 'swiper';

import { DataType } from '@framework/schemas/leftindust.schema';

import {
  Row,
  Col,
  BlockFooter,
  Popover,
  List,
  ListItem,
} from 'framework7-svelte';

import AddUI from '@framework/ui/input/AddUI/AddUI.svelte';
import InputUI from '@framework/ui/input/InputUI/InputUI.svelte';
import SelectUI from '@framework/ui/input/SelectUI/SelectUI.svelte';
import DeleteButtonUI from '@framework/ui/button/DeleteButtonUI/DeleteButtonUI.svelte';
import SurveyTemplateWizardSelect from '../input/SurveyTemplateWizardSelect.svelte';
import { SurveyWizardForm } from '../store/index';
import { openPopover } from '@framework/modules/NavigationModule';
import { onMount } from 'svelte';
import language from '@framework/languages';

export let sectionIndex: number;
export let swiper: Swiper | undefined;

let fieldsTemplateMenuRef: Popover;
let fieldsTemplateMenu: PopoverType.Popover;

let fields: FormFieldInput[] = [];

let fieldTemplates: MenuUIItems[] = [
  {
    title: 'Severity',
    onClick: () => {
      fields = [
        ...fields,
        {
          title: '',
          dataType: DataType.SingleMuliSelect,
          multiSelectPossibilities: [
            language().form.severity.none.text,
            language().form.severity.mild.text,
            language().form.severity.moderate.text,
            language().form.severity.severe.text,
            language().form.severity.extreme.text,
          ],
          number: fields.length,
        },
      ];
    },
  },
  {
    title: 'Frequency',
    onClick: () => {
      fields = [
        ...fields,
        {
          title: '',
          dataType: DataType.SingleMuliSelect,
          multiSelectPossibilities: [
            language().form.frequency.never.text,
            language().form.frequency.rarely.text,
            language().form.frequency.sometimes.text,
            language().form.frequency.often.text,
            language().form.frequency.always.text,
          ],
          number: fields.length,
        },
      ];
    },
  },
];

const reRenderField = (index: number) => {
  const field = fields[index];

  switch(field.dataType) {
    case DataType.MultiMuliSelect:
    case DataType.SingleMuliSelect:
      if (!Array.isArray(field.multiSelectPossibilities))
        field.multiSelectPossibilities = [''];
      break;
    default:
      field.multiSelectPossibilities = undefined;
      break;
  }
};

$: $SurveyWizardForm.sections[sectionIndex].fields = fields;

// A true classic: a sudden patch that needed to be implimented because suddenly
// swiperjs decides it's ok to stop behaving in the same way.
$: (() => {
  $SurveyWizardForm;
  setTimeout(() => {
    swiper?.updateAutoHeight();
  }, 10);
})();

onMount(() => {
  fieldsTemplateMenu = fieldsTemplateMenuRef.instance();
});

</script>

<Popover bind:this={fieldsTemplateMenuRef}>
  <List>
    {#each fieldTemplates as template}
      <ListItem
        title={template.title}
        link
        noChevron
        popoverClose
        on:click={() => {
          if (template.onClick)
            template.onClick();
        }}
      />
    {/each}
  </List>
</Popover>

<h4 style="margin-bottom: 4px">Section {sectionIndex + 1}</h4>
{#if $SurveyWizardForm.sections[sectionIndex]}
  <InputUI>
    <input type="text" placeholder="Title" bind:value={$SurveyWizardForm.sections[sectionIndex].name} />
  </InputUI>
  <p />
  <InputUI>
    <textarea placeholder="Description (Optional)" bind:value={$SurveyWizardForm.sections[sectionIndex].description} />
  </InputUI>
  <br />
  <BlockFooter>
    Name your section to help your users differentiate the content you are providing to them in your form.
  </BlockFooter>
  <br />
{/if}
<h4 style="margin-bottom: 4px">Fields</h4>
{#each fields as field, index}
  <div class="display-flex">
    <div style="width: 100%">
      <InputUI clear>
        <input type="text" placeholder="Question" bind:value={field.title} />
      </InputUI>
      <p />
      <Row>
        <Col width={!(field.dataType === DataType.MultiMuliSelect || field.dataType === DataType.SingleMuliSelect) ? '100' : '33'}>
          <SelectUI
            title={!(field.dataType === DataType.MultiMuliSelect || field.dataType === DataType.SingleMuliSelect) ? 'Answer' : ''}
            options={[
              {
                text: 'Text',
                value: DataType.Text,
              },
              {
                text: 'Multi-Select',
                value: DataType.MultiMuliSelect,
              },
              {
                text: 'Single-Select',
                value: DataType.SingleMuliSelect,
              },
              {
                text: 'Number',
                value: DataType.Integer,
              },
              {
                text: 'Date',
                value: DataType.Date,
              },
            ]}
            bind:value={field.dataType}
            on:change={() => reRenderField(index)}
          />
        </Col>
        <Col width={!(field.dataType === DataType.MultiMuliSelect || field.dataType === DataType.SingleMuliSelect) ? '0' : '66'}>
          {#if Array.isArray(field.multiSelectPossibilities)}
            {#each field.multiSelectPossibilities as _, index}
              <SurveyTemplateWizardSelect {index} bind:options={field.multiSelectPossibilities} />
              <p />
            {/each}
            <AddUI
              title="Add Answer"
              on:click={() => {
                if (Array.isArray(field.multiSelectPossibilities))
                  field.multiSelectPossibilities = [...field.multiSelectPossibilities, ''];
              }}
            />
          {/if}
        </Col>
      </Row>
    </div>

    <div style="margin-top: 28px">
      <DeleteButtonUI on:delete={() => {
        fields = fields.filter((_, i) => i !== index);
      }} />
    </div>
  </div>
  <p />
{/each}
<p />
<Row>
  <Col width="50">
    <AddUI
      title="Blank Field"
      on:click={() => {
        fields = [
          ...fields,
          {
            title: '',
            dataType: DataType.Text,
            number: fields.length,
          },
        ];
      }}
    />
  </Col>
  <Col width="50">
    <AddUI
      title="Template"
      on:click={(event) => { openPopover(fieldsTemplateMenu, event); }}
    />
  </Col>
</Row>

<br />
<BlockFooter>
  Create question/answer style fields for your users can interact with.
</BlockFooter>
