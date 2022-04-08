import { client } from '../../server';
import type { FormTemplateId, PatientId, PatientIdInput } from '../../schemas/leftindust.schema';
import assignSurveyTemplateMutation
  from '../../requests/mutations/assignSurveyTemplateMutation.graphql';
import { sendTrigger } from '../../modules/TriggerModule';

const SurveyTemplateAssignEngine = async (variables: { patients: PatientIdInput[], survey: FormTemplateId }): Promise<boolean> => {
  const result = await client.mutate<Record<'assignSurvey', { pid: PatientId }[]>>(assignSurveyTemplateMutation, {
    variables,
  });

  if (result.data?.assignSurvey[0]?.pid?.id) {
    sendTrigger('refreshSurveys');
    sendTrigger('refreshPatients');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;
};

export default SurveyTemplateAssignEngine;
