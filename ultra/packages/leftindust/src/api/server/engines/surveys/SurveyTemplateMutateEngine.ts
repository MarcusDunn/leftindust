import { client } from '../..';
import type { FormTemplateInput, GraphQlFormTemplate } from '../../schema/leftindust.schema';
import surveyTemplateMutation from '../../requests/mutations/surveyTemplateMutation.graphql';
import { sendTrigger } from '@/features/Triggers';

const SurveyTemplateMutateEngine = async (survey: FormTemplateInput): Promise<boolean> => {
  const result = await client.mutate<Record<'addSurveyTemplate', GraphQlFormTemplate>>(surveyTemplateMutation, {
    variables: {
      survey,
    },
  });

  if (result.data?.addSurveyTemplate?.id?.id) {
    sendTrigger('refreshSurveys');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;
};

export default SurveyTemplateMutateEngine;
