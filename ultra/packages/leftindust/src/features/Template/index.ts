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
import { type CreateSurveyTemplateCalculation, SurveyTemplateInputType, SurveyTemplateCategory, TemplateInputUploadType, client, SurveyTemplateMutationDocument } from '@/api/server';
import { Template, TemplateCalculations } from './store';
import getNativeAPI from '@/api/bridge';

const language = get(_);
const { Dialog } = getNativeAPI();
 
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

export type Template = {
  title: string;
  subtitle?: string;
  sections: {
    id: number;
    title: string;
    subtitle?: string;
    inputs: {
      id: number;
      type: SurveyTemplateInputType;
      label: string;
      options: string[];
      placeholder?: string;
      required?: boolean;
      category?: SurveyTemplateCategory;
      uploadMultiple: boolean;
      uploadAccept: TemplateInputUploadType;
      value: unknown;
    }[];
  }[];
}

export const templatesSchema = yup.object({
  title: yup.string().required(),
  subtitle: yup.string(),
  sections: yup.array(yup.object({
    title: yup.string().required(),
    subtitle: yup.string(),
    inputs: yup.array(yup.object({
      type: yup.mixed<keyof typeof SurveyTemplateInputType>().oneOf(Object.values(SurveyTemplateInputType)),
      label: yup.string().required(),
      options: yup.array().of(yup.string().required()),
      placeholder: yup.string(),
      // TODO: weird bug that causes felte to bind to some toggles and retrieve the value as ['', '', '']
      // Probably will move to a different lib in the future
      required: yup.mixed(),
      category: yup.mixed<keyof typeof SurveyTemplateCategory>().oneOf(Object.values(SurveyTemplateCategory)),
      uploadMultiple: yup.boolean(),
      uploadAccept: yup.mixed<keyof typeof TemplateInputUploadType>().oneOf([null, ...Object.values(TemplateInputUploadType)]),
    })),
  })).required(),
  calculations: yup.array(yup.object({
    label: yup.string().required(),
    inputType: yup.mixed<keyof typeof SurveyTemplateInputType>().oneOf(Object.values(SurveyTemplateInputType)).nullable(),
    showOnComplete: yup.boolean().required(),
  })),
});

export type TemplateSchema = yup.InferType<typeof templatesSchema>;

export const defaultTemplate: Template = {
  title: '',
  subtitle: undefined,
  sections: [{
    title: '',
    subtitle: undefined,
    inputs: [],
    id: 0,
  }],
};

export const defaultTemplateSchema: TemplateSchema = {
  title: '',
  subtitle: undefined,
  sections: [{
    title: '',
    subtitle: undefined,
    inputs: [],
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

export const templateForm = (closeWizardHandler: () => void) => createForm<TemplateSchema>({
  initialValues: defaultTemplateSchema,
  onSubmit: () => {
    const form = get(Template);
    const formCalculations = get(TemplateCalculations);

    const surveyTemplate = {
      title: form.title,
      subtitle: form.subtitle,
      sections: form.sections.map((section) => ({
        title: section.title,
        subtitle: section.subtitle,
        calculationId: section.id,
        inputs: section.inputs.map((input) => ({
          calculationId: input.id,
          category: input.category as SurveyTemplateCategory,
          label: input.label,
          options: input.options,
          placeholder: input.placeholder,
          required: input.required || false,
          type: input.type,
          uploadAccept: input.uploadAccept,
          uploadMultiple: input.uploadMultiple,
        })),
      })),
      calculations: formCalculations.map((calculation) => ({
        label: calculation.label,
        calculation: JSON.stringify(calculation.deserializedCalculation),
        inputType: calculation.inputType,
        showOnComplete: calculation.showOnComplete,
      })),
    };

    console.log(surveyTemplate);

    const showTemplateError = (error: string) => {
      void Dialog.alert({
        message: 'Something went wrong',
        detail: error,
        buttons: [language('generics.ok')],
        defaultId: 0,
      });
    };

    client.mutation(SurveyTemplateMutationDocument, {
      surveyTemplate,
    })
      .toPromise()
      .then(({ data, error }) => {
        if (data?.addSurveyTemplate) {
          closeWizardHandler();
          return;
        }
        
        showTemplateError(error?.message ?? 'Unknown error');
      })
      .catch((error) => showTemplateError(error));
  },
  extend: [
    validator({ schema: templatesSchema }),
  ],
});

export const getYupInputTypeFromTemplateCategory = (type: SurveyTemplateInputType) => {
  switch (type) {
    case SurveyTemplateInputType.Text:
    case SurveyTemplateInputType.Paragraph:
      return yup.string();
    case SurveyTemplateInputType.Number:
    case SurveyTemplateInputType.Date:
      return yup.number();
    case SurveyTemplateInputType.SingleSelect:
    case SurveyTemplateInputType.MultiSelect:
      return yup.array(yup.string());
    default:
      return yup.string();
  }
};
