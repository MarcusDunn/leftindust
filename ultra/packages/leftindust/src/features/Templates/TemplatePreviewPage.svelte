<script lang="ts">
  import { TemplateInputItems, TemplateSelectedTab } from './store';
  import { _ } from '@/language';
  import { TemplateInputType, TemplateInputUploadType } from '.';
  import TemplatePreviewCard from './components/TemplateCard/TemplatePreviewCard.svelte';
  import {
    Block,
    BlockFooter,
    Tab,
    Tabs,
} from 'framework7-svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import Date from '../Input/components/Date/Date.svelte';
  import Input from '../Input/Input.svelte';
  import Checkbox from '../Input/components/Checkbox/Checkbox.svelte';
  import Add from '../Input/components/Add/Add.svelte';

  const getUploadLabel = (accept?: TemplateInputUploadType, multiple?: boolean) => $_('generics.uploadWithLabel', {
    values: {
      label: (() => {
        if (accept === TemplateInputUploadType.Documents) return multiple ? $_('generics.documents') : $_('generics.document');
        if (accept === TemplateInputUploadType.Images) return multiple ? $_('generics.images') : $_('generics.image');
        return multiple ? $_('generics.files') : $_('generics.file');
      })(),
    },
  });


</script>

<Page style="height: 100%; overflow: hidden">
  <Appbar slot="fixed" />
  <p />
  <Tabs style="height: 100%">
    <Tab tabActive={$TemplateSelectedTab === 'input'}>
      <Block class="no-margin-top">
        <h2>{$TemplateInputItems.title}</h2>
        <p>{$TemplateInputItems.subtitle ?? ''}</p>
        <p />
        {#each $TemplateInputItems.items as {
          type,
          label,
          options,
          placeholder,
          uploadAccept,
          uploadMultiple,
          required,
        }}
          {#if type === TemplateInputType.Title}
            <h4 style="padding-top: 20px">{label}</h4>
          {:else}
            <div style="margin-bottom: 20px;">
              {#if type === TemplateInputType.Text || type === TemplateInputType.Number}
                <Input title={label} clear>
                  <input type="text" {placeholder} />
                </Input>
              {:else if type === TemplateInputType.Date}
                <Date title={label} {placeholder} />
              {:else if type === TemplateInputType.Paragraph}
                <Input title={label} clear>
                  <textarea {placeholder} />
                </Input>
              {:else if type === TemplateInputType.Upload}
                <Add title="{label}" placeholder={placeholder || getUploadLabel(uploadAccept, uploadMultiple)} />
              {:else if type === TemplateInputType.MultiSelect || type === TemplateInputType.SingleSelect}
                {#if options}
                  <div style="margin-top: 10px; margin-bottom: -10px; font-size: 14px">{label}</div>
                  {#each options as option}
                    <Checkbox
                      title={option}
                      multiple={type === TemplateInputType.MultiSelect}
                      slot="content"
                    />
                  {/each}
                {/if}
              {/if}
            </div>
          {/if}
        {/each}
      </Block>
    </Tab>
    <Tab style="height: 100%" tabActive={$TemplateSelectedTab === 'output'}>
      <div style="height: 100%" class="middle-of-page">
        <div style="height: 190px; width: 340px">
          <TemplatePreviewCard />
        </div>
      </div>
    </Tab>
  </Tabs>
</Page>