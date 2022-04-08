import { client } from '../../server';
import type { FormTemplateInput, GraphQlFormTemplate } from '../../schemas/leftindust.schema';
import surveyTemplateMutation from '../../requests/mutations/surveyTemplateMutation.graphql';
import { sendTrigger } from '../../modules/TriggerModule';

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
