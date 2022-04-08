import type { FormTemplateInput } from '@framework/schemas/leftindust.schema';

import { writable } from 'svelte/store';
import deepmerge from 'deepmerge';

export const SurveyWizardFormTemplate: FormTemplateInput = {
  name: '',
  sections: [{
    description: undefined,
    name: '',
    number: 0,
    fields: [],
  }],
};

export const SurveyWizardForm = writable<FormTemplateInput>(deepmerge({}, SurveyWizardFormTemplate));

export const SurveyWizardActiveSectionIndex = writable(0);
