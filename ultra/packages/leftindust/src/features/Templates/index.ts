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
