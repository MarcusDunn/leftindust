import type { Editor } from 'function-junctions/types';
import type { EditorState, SocketBlueprint } from 'function-junctions/types';
import { get } from 'svelte/store';
import dateSocket from '../Socket/components/DateSocket';
import numberSocket from '../Socket/components/NumberSocket';
import textSocket from '../Socket/components/TextSocket';
import numberArraySocket from '../Socket/components/NumberArraySocket';
import textArraySocket from '../Socket/components/TextArraySocket';
import dateArraySocket from '../Socket/components/DateArraySocket';
import twoDimensionalTextArraySocket from '../Socket/components/2DTextArraySocket';
import { _ } from '@/language';
import * as yup from 'yup';
import { createForm } from 'felte';
import { validator } from '@felte/validator-yup';

const language = get(_);

export enum TemplateInputType {
  Text = 'text',
  Number = 'number',
  Date = 'date',
  Paragraph = 'paragraph',
  Upload = 'upload',
  SingleSelect = 'single-select',
  MultiSelect = 'multi-select',
  Title = 'title'
}

export enum TemplateCategory {
  Date = 'date',
  Title = 'title',
  Body = 'body',
}

export enum TemplateInputUploadType {
  All = '*',
  Images = 'image/*',
  Documents = '.txt,.pdf,.rtf,.doc,.docx'
}

export type TemplateInput<T = unknown> = {
  id: number;
  type: TemplateInputType;
  label: string;
  options?: string[];
  placeholder?: string;
  required?: boolean;
  category?: TemplateCategory;
  uploadMultiple?: boolean;
  uploadAccept?: TemplateInputUploadType;
  value?: T;
}

export type TemplateSection = {
  id: number;
  title: string;
  subtitle?: string;
  inputs: TemplateInput[];
}

export type Template = {
  title: string;
  subtitle?: string;
  sections: TemplateSection[];
};

export type TemplateCalculation = {
  label: string;
  type: TemplateInputType;
  showOnComplete: boolean;
  calculation: EditorState;
}

export type TemplateCalculationWithInstance = TemplateCalculation & {
  editor?: Editor;
}

export type TemplateCalculationSockets = {
  number: SocketBlueprint;
  text: SocketBlueprint;
  date: SocketBlueprint;
  number_array: SocketBlueprint;
  text_array: SocketBlueprint;
  date_array: SocketBlueprint;
  text_array_array: SocketBlueprint;
}

export const defaultTemplate: Template = {
  title: '',
  sections: [{
    title: '',
    inputs: [],
    id: 0,
  }],
};

export const templateCalculationSockets: TemplateCalculationSockets = {
  number: numberSocket,
  text: textSocket,
  date: dateSocket,
  number_array: numberArraySocket,
  text_array: textArraySocket,
  date_array: dateArraySocket,
  text_array_array: twoDimensionalTextArraySocket,
};

export const getTemplateSocketType = (inputType: TemplateInputType) => {
  let type: 'text' | 'number' | 'date' | 'text_array';

  switch (inputType) {
    case TemplateInputType.Text:
    case TemplateInputType.Title:
    case TemplateInputType.Paragraph:
    case TemplateInputType.SingleSelect:
      type = 'text';
      break;
    case TemplateInputType.Number:
      type = 'number';
      break;
    case TemplateInputType.Date:
      type = 'date';
      break;
    case TemplateInputType.MultiSelect:
      type = 'text_array';
      break;
  }

  // TS being weird
  // @ts-expect-error
  return type;
};

export const templateInputSelectOptions = [
  {
    text: language('generics.text'),
    value: TemplateInputType.Text,
  },
  {
    text: language('generics.number'),
    value: TemplateInputType.Number,
  },
  {
    text: language('generics.date'),
    value: TemplateInputType.Date,
  },
  {
    text: language('generics.paragraph'),
    value: TemplateInputType.Paragraph,
  },
  {
    text: language('generics.singleSelect'),
    value: TemplateInputType.SingleSelect,
  },
  {
    text: language('generics.multiSelect'),
    value: TemplateInputType.MultiSelect,
  },
  /*
  {
    text: language('generics.upload'),
    value: TemplateInputType.Upload,
  },
  */
  {
    text: language('generics.title'),
    value: TemplateInputType.Title,
  },
];

export const templateForm = () => {
  const schema = yup.object({
    title: yup.string().required(),
    subtitle: yup.string(),
    sections: yup.array().of(yup.object({
      id: yup.number().required(),
      title: yup.string().required(),
      subtitle: yup.string(),
      inputs: yup.array().of(yup.object({
        id: yup.number().required(),
        type: yup.string().required(),
        label: yup.string().required(),
        options: yup.array().of(yup.string()),
        placeholder: yup.string(),
        required: yup.boolean(),
        category: yup.string(),
        uploadMultiple: yup.boolean(),
        uploadAccept: yup.string(),
      })).required(),
    })).required(),
    calculations: yup.array().of(yup.object({
      label: yup.string().required(),
      type: yup.string().required(),
      showOnComplete: yup.boolean().required(),
      calculation: yup.string().required(),
    })).required(),
  });

  return createForm<yup.InferType<typeof schema>>({
    onSubmit: (form) => {
      console.log(form);
    },
    extend: [
      validator({ schema }),
    ],
  });
};
