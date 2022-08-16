import type { Editor, EditorState } from 'function-junctions/types';
import type { SocketBlueprint } from 'function-junctions/types';
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
import { type CreateSurveyTemplate, type CreateSurveyTemplateCalculation, SurveyTemplateInputType, SurveyTemplateCategory, TemplateInputUploadType } from '@/api/server';

const language = get(_);

export type TemplateCalculationWithInstance = Omit<CreateSurveyTemplateCalculation, 'calculation'> & {
  editor?: Editor;
  deserializedCalculation: EditorState;
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

export const templatesSchema = yup.object({
  title: yup.string().required(),
  subtitle: yup.string().notRequired(),
  sections: yup.array(yup.object({
    id: yup.number().required(),
    title: yup.string().required(),
    subtitle: yup.string().nullable(),
    inputs: yup.array(yup.object({
      id: yup.number().required(),
      type: yup.mixed<keyof typeof SurveyTemplateInputType>().oneOf(Object.values(SurveyTemplateInputType)),
      label: yup.string().required(),
      options: yup.array().of(yup.string().required()),
      placeholder: yup.string(),
      required: yup.boolean(),
      category: yup.mixed<keyof typeof SurveyTemplateCategory>().oneOf(Object.values(SurveyTemplateCategory)),
      uploadMultiple: yup.boolean(),
      uploadAccept: yup.mixed<keyof typeof TemplateInputUploadType>().oneOf([null, ...Object.values(TemplateInputUploadType)]),
    })).required(),
  })).required(),
  calculations: yup.array(yup.object({
    label: yup.string().required(),
    inputType: yup.mixed<keyof typeof SurveyTemplateInputType>().oneOf(Object.values(SurveyTemplateInputType)).nullable(),
    showOnComplete: yup.boolean().required(),
    calculation: yup.string().required(),
  })),
});

export type TemplateSchema = yup.InferType<typeof templatesSchema>;

export const defaultTemplate: TemplateSchema = {
  title: '',
  subtitle: '',
  sections: [{
    title: '',
    subtitle: '',
    inputs: [],
    id: 0,
  }],
  calculations: [],
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

export const getTemplateSocketType = (inputType: SurveyTemplateInputType) => {
  let type: 'text' | 'number' | 'date' | 'text_array';

  switch (inputType) {
    case SurveyTemplateInputType.Text:
    case SurveyTemplateInputType.Title:
    case SurveyTemplateInputType.Paragraph:
    case SurveyTemplateInputType.SingleSelect:
      type = 'text';
      break;
    case SurveyTemplateInputType.Number:
      type = 'number';
      break;
    case SurveyTemplateInputType.Date:
      type = 'date';
      break;
    case SurveyTemplateInputType.MultiSelect:
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
    value: SurveyTemplateInputType.Text,
  },
  {
    text: language('generics.number'),
    value: SurveyTemplateInputType.Number,
  },
  {
    text: language('generics.date'),
    value: SurveyTemplateInputType.Date,
  },
  {
    text: language('generics.paragraph'),
    value: SurveyTemplateInputType.Paragraph,
  },
  {
    text: language('generics.singleSelect'),
    value: SurveyTemplateInputType.SingleSelect,
  },
  {
    text: language('generics.multiSelect'),
    value: SurveyTemplateInputType.MultiSelect,
  },
  /*
  {
    text: language('generics.upload'),
    value: TemplateInputType.Upload,
  },
  */
  {
    text: language('generics.title'),
    value: SurveyTemplateInputType.Title,
  },
];

export const templateForm = () => {


  return createForm<TemplateSchema>({
    initialValues: defaultTemplate,
    onSubmit: (form) => {
      console.log(form);
    },
    extend: [
      validator({ schema: templatesSchema }),
    ],
  });
};
