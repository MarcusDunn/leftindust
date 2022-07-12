<script lang="ts">
  import { templateInputSelectOptions, type TemplateInput } from '../..';
  import { TemplateInputUploadType } from '../..';
  import { _ } from 'svelte-i18n';
  import { TemplateInputType } from '../..';
  import {
    Button,
    Col,
    Icon,
    ListItem,
    Row,
    Toggle,
  } from 'framework7-svelte';
  import Select from '@/features/Input/components/Select/Select.svelte';
  import Input from '@/features/Input/Input.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import './TemplateInput.scss';
  import TemplateInputSelect from './TemplateInputSelect.svelte';
  import Add from '@/features/Input/components/Add/Add.svelte';
  
  export let inputs: TemplateInput[];
  export let index: number;
  export let globalIndex: number;
  
  export let type: TemplateInputType = TemplateInputType.Text;
  export let label = '';
  export let placeholder = '';
  export let required = false;

  export let uploadAccept: TemplateInputUploadType | undefined = undefined;
  export let uploadMultiple: boolean | undefined = undefined;

  export let options: string[] = [''];

  export let dragger: ((event: Event) => void)| undefined = undefined;

  $: multiselect = (type === TemplateInputType.SingleSelect || type === TemplateInputType.MultiSelect);
  $: title = type === TemplateInputType.Title;
  $: compute = false;

  // https://github.com/sveltejs/svelte/issues/5162
  let optionText = $_('generics.options');
</script>

<div class="templates-template_input">
  {#if dragger}
    <div
      class="templates-template_input-controller"
      on:mousedown={dragger}
      on:touchstart={dragger}
    >
      <Icon f7="line_horizontal_3" class="text-color-gray" />
    </div>
  {/if}
  <div class="templates-template_input-inputs">
    <Row>
      <Col width="100">
        {#key index}
          <Select
            title={$_('generics.type')}
            placeholder={$_('examples.text')}
            options={templateInputSelectOptions}
            bind:value={type}
          />
        {/key}
      </Col>
      <Col width="100" small={(multiselect || title || compute ) ? 100 : 50}>
        <Input style="width: 100%">
          <svelte:fragment slot="title">{$_('generics.label')}</svelte:fragment>
          <input
            type="text"
            bind:value={label}
            placeholder={$_('examples.totalPlateletCount')}
          />
        </Input>
      </Col>
      {#if !multiselect}
        {#if !title && !compute}
          <Col width="100" small="50">
            <Input style="width: 100%">
              <svelte:fragment slot="title">{$_('generics.placeholder')}</svelte:fragment>
              <input
                type="text"
                bind:value={placeholder}
                placeholder={$_('examples.mcl')}
              />
            </Input>
          </Col>
        {/if}
      {:else}
        {#each options as _, index}
          <Col width="100">
            <TemplateInputSelect {index} title={index === 0 ? optionText : undefined} bind:options />
          </Col>
        {/each}
        <Col width="100" style="margin-top: 10px">
          <Add
            placeholder={$_('generics.addOption')}
            on:click={() => (options = [...options, ''])}
          />
        </Col>
      {/if}
      {#if type === TemplateInputType.Upload}
        <Col width="100">
          <br />
          <Select
            title={$_('generics.uploads')}
            options={[
              {
                text: $_('generics.all'),
                value: TemplateInputUploadType.All,
              },
              {
                text: $_('generics.images'),
                value: TemplateInputUploadType.Images,
              },
              {
                text: $_('generics.documents'),
                value: TemplateInputUploadType.Documents,
              },
            ]}
            bind:value={uploadAccept}
          />
        </Col>
        <Col width="100">
          <p />
          <Input>
            <ListItem class="aurora" slot="content">
              <span>{$_('generics.allowMultiple')}</span>
              <Toggle
                color="deeppurple"
                bind:checked={uploadMultiple}
              />
            </ListItem>
          </Input>
          <p />
        </Col>
      {/if}
      <Col width="100">
        <div class="display-flex" style="margin-top: 20px">
          {#if compute}
            <div style="width: 100%;margin-top: 10px">
              <Button color="deeppurple" round outline>Edit Algorithm</Button>
            </div>
          {/if}
          {#if !title && !compute}
            <Input style="width: 100%">
              <ListItem class="aurora" slot="content">
                <span>{$_('generics.required')}</span>
                <Toggle
                  color="deeppurple"
                  bind:checked={required}
                />
              </ListItem>
            </Input>
          {/if}
          <div class="flex-grow" />
          <div class="display-flex" style="padding-left: 15px">
            <MenuButton
              title={$_('generics.clone')}
              icon={{
                f7: 'square_on_square',
                color: 'gray',
              }}
              on:click={() => {
                inputs = [
                  ...inputs.slice(0, index),
                  {
                    id: globalIndex,
                    type,
                    label,
                    placeholder,
                    required,
                    options,
                  },
                  ...inputs.slice(index),
                ];
                globalIndex += 1;
              }}
            />
            <MenuButton
              title={$_('generics.delete')}
              icon={{
                f7: 'minus_circle_fill',
                color: 'red',
              }}
              on:click={() => {
                inputs = inputs.filter((_, i) => i !== index);
              }}
            />
          </div>
        </div>
      </Col>
    </Row>
  </div>
</div>
