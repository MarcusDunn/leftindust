import type { SetStorageParameters } from '../types/StorageModuleTypes';

const set = (store: SetStorageParameters): Promise<unknown> =>
  new Promise((resolve, reject) => {
    if (store.defaults) {
      if (localStorage.getItem(store.key) === null) {
        localStorage.setItem(
          store.key,
          JSON.stringify(store.defaults),
        );
      }
    } else {
      if (store.value) {
        localStorage.setItem(
          store.key,
          JSON.stringify(store.value),
        );
      } else {
        reject(
          'No value: default or set, was passed for the assignment of ' +
          store.key,
        );
      }
    }

    resolve(JSON.parse(localStorage.getItem(store.key) || '{}'));
  });

const get = (store: { key: string }): Promise<unknown> =>
  new Promise((resolve) => {
    const item = localStorage.getItem(store.key);
    if (item) {
      resolve(JSON.parse(item));
      return;
    }
    resolve(null);
  });

const remove = (store: { key: string }): Promise<unknown> =>
  new Promise((resolve) => {
    localStorage.removeItem(store.key);
    resolve(localStorage.getItem(store.key));
  });

export default {
  set,
  get,
  remove,
};
