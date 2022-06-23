import type { EditorState, NodeState } from 'function-junctions/types';

export enum TemplateInputType {
  Text = 'text',
  Number = 'number',
  Date = 'date',
  Paragraph = 'paragraph',
  Upload = 'upload',
  SingleSelect = 'single-select',
  MultiSelect = 'multi-select',
  Title = 'title',
  Compute = 'compute'
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

export type TemplateInput = {
  id: number;
  type: TemplateInputType;
  label: string;
  options?: string[];
  placeholder?: string;
  required?: boolean;
  category?: TemplateCategory;
  uploadMultiple?: boolean;
  uploadAccept?: TemplateInputUploadType;
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

export type TemplateComputation = {
  title: string;
  computation: EditorState;
}

export const DefaultTemplate: Template = {
  title: '',
  sections: [{
    title: '',
    inputs: [],
    id: 0,
  }],
};
