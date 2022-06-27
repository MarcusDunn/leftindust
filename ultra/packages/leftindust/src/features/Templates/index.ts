import type { EditorState, SocketBlueprint } from 'function-junctions/types';
import arraySocket from '../Socket/components/ArraySocket';
import dateSocket from '../Socket/components/DateSocket';
import numberSocket from '../Socket/components/NumberSocket';
import textSocket from '../Socket/components/TextSocket';

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
  calculation: EditorState;
}

export type TemplateCalculationSockets = {
  number: SocketBlueprint;
  text: SocketBlueprint;
  date: SocketBlueprint;
  array: SocketBlueprint;
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
  array: arraySocket,
};

export const getTemplateSocketType = (inputType: TemplateInputType) => {
  let type: 'text' | 'number' | 'date' | 'array';

  switch (inputType) {
    case TemplateInputType.Text:
    case TemplateInputType.Title:
    case TemplateInputType.Paragraph:
      type = 'text';
      break;
    case TemplateInputType.Number:
      type = 'number';
      break;
    case TemplateInputType.Date:
      type = 'date';
      break;
    case TemplateInputType.SingleSelect:
    case TemplateInputType.MultiSelect:
      type = 'array';
      break;
  }

  // TS being weird
  // @ts-expect-error
  return type;
};