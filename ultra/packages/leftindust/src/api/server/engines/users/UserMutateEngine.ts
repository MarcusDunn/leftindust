import type { User, UserInput } from '../../schema/leftindust.schema';
import { client } from '../..';
import userMutation from '../../requests/mutations/userMutation.graphql';
import userEditMutation from '../../requests/mutations/userEditMutation.graphql';
import { sendTrigger } from '@/features/Triggers';

const UserMutateEngine = async (user: UserInput, edit = false): Promise<boolean> => {
  const result = await client.mutate<Record<'addUser' | 'editUser', User>>(edit
    ? userEditMutation : userMutation, {
    variables: {
      user,
    },
  });

  if (result.data?.addUser?.uid || result.data?.editUser?.uid) {
    sendTrigger('refreshUsers');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;
};

export default UserMutateEngine;
