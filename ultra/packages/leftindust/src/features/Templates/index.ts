import type { Editor } from 'function-junctions/types';
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
import { type CreateSurveyTemplate, type CreateSurveyTemplateCalculation, SurveyTemplateInputType } from '@/api/server';

const language = get(_);

export type TemplateCalculationWithInstance = CreateSurveyTemplateCalculation & {
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

export const defaultTemplate: CreateSurveyTemplate = {
  title: '',
  sections: [{
    title: '',
    inputs: [],
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
  const schema = yup.object({
    title: yup.string().required(),
    subtitle: yup.string(),
    sections: yup.array(yup.object({
      id: yup.number().required(),
      title: yup.string().required(),
      subtitle: yup.string(),
      inputs: yup.array(yup.object({
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
    calculations: yup.array(yup.object({
      label: yup.string().required(),
      inputType: yup.mixed<keyof typeof SurveyTemplateInputType>().oneOf(Object.values(SurveyTemplateInputType)),
      showOnComplete: yup.boolean().required(),
      calculation: yup.string().required(),
    })),
  });

  return createForm<yup.InferType<typeof schema>>({
    initialValues: defaultTemplate,
    onSubmit: (form) => {
      console.log(form);
    },
    extend: [
      validator({ schema }),
    ],
  });
};
