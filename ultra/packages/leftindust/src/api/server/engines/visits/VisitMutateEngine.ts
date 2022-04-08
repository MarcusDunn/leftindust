import type { GraphQlVisitEditInput, Visit, VisitInput } from '../../schema/leftindust.schema';
import { client } from '../..';
import { sendTrigger } from '@/features/Triggers';
import visitMutation from '../../requests/mutations/visitMutation.graphql';
import visitEditMutation from '../../requests/mutations/visitEditMutation.graphql';

const VisitMutateEngine = async (visit: VisitInput | GraphQlVisitEditInput, edit = false): Promise<boolean> => {
  const result = await client.mutate<Record<'addVisit' | 'editVisit', Visit>>(edit ? visitEditMutation : visitMutation, {
    variables: {
      visit,
    },
  });

  if (result.data?.addVisit?.vid?.id || result.data?.editVisit?.vid?.id) {
    sendTrigger('refreshVisits');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;
};

export default VisitMutateEngine;
