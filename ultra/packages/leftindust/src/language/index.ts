import {
  init,
  getLocaleFromNavigator,
  locale,
  register,
  _,
} from 'svelte-i18n';
import { get } from 'svelte/store';

import * as yup from 'yup';

export type Dictionary = {
  descriptions: {
    signIn: string;
    alphaLoginNotice: string;
    patientsSearch: string;
    addPatient: string;
    addDoctor: string;
    noRecents: string;
    noPatients: string;
    noDoctors: string;
    nda: string;
    copyright: string;
    confidential: string;
    byClickingNext: string;
    noContactInformation: string;
    addPinned: string;
    learnMorePinning: string;
    learnMorePatients: string;
    learnMoreDoctors: string;
  };
  errors: {
    connectionError: string;
    runtimeError: string;
    internalError: string;
    loginIncorrectFields: string;
    loginEmptyFields: string;
    offline: string;
    requiredField: string;
    invalidEmail: string;
  };
  generics: {
    tryAgain: string;
    ok: string;
    report: string;
    signIn: string;
    signOut: string;
    back: string;
    next: string;
    close: string;
    more: string;
    reload: string;
    create: string;
    delete: string;
    filter: string;
    account: string;
    options: string;
    about: string;
    edit: string;
    pinned: string;
    viewFile: string;
    learnMore: string;
    quicklook: string;
    viewPatient: string;
    viewDoctor: string;
    viewUser: string;
    returnToHome: string;
    cancel: string;
    done: string;
    input: string;
    output: string;
    clone: string;
    remove: string;
    addInput: string;
    addOption: string;
    email: string;
    emailPlaceholder: string;
    password: string;
    phone: string;
    address: string;
    patientsSearch: string;
    doctorsSearch: string;
    text: string;
    number: string;
    date: string;
    singleSelect: string;
    multiSelect: string;
    upload: string;
    required: string;
    label: string;
    placeholder: string;
    types: string;
    optionIndexed: string;
    title: string;
    selectDate: string;
    default: string;
    additional: string;
    body: string;
    paragraph: string;
    showDateHeader: string;
    header: string;
    creationDate: string;
    type: string;
    app: string;
    alpha: string;
    thisClinic: string;
    myClinic: string;
    clinic: string;
    dashboard: string;
    calendar: string;
    clients: string;
    administration: string;
    users: string;
    usersAndGroups: string;
    patients: string;
    doctors: string;
    patient: string;
    doctor: string;
    recents: string;
    documents: string;
    records: string;
    contacts: string;
    bundled: string;
    stacked: string;
    templates: string;
    forms: string;
    analytics: string;
    samples: string;
    settings: string;
    goodMorning: string;
    goodAfternoon: string;
    goodEvening: string;
    noPinned: string;
    noPatients: string;
    noDoctors: string;
    allTemplates: string;
    summary: string;
    files: string;
    file: string;
    images: string;
    image: string;
    document: string;
    uploadWithLabel: string;
    all: string;
    allowMultiple: string;
    uploads: string;
    inputOf: string;
    description: string;
    instructions: string;
    subtitle: string;
    addSection: string;
    section: string;
    view: string;
    sectionIndexed: string;
    inputIndexed: string;
    viewAll: string;
    newDoctor: string;
    newPatient: string;
    compute: string;
    poweredByDust: string;
  };
  examples: {
    totalPlateletCount: string;
    mcl: string;
    text: string;
    section: string;
    calculation: string;
  };
}

const setupI18n = async (withLocale = getLocaleFromNavigator() ?? 'en'): Promise<void> => {
  // Svelte i18n
  register('en', () => import('./locales/en'));

  await init({
    fallbackLocale: 'en',
    initialLocale: withLocale,
  });

  await locale.set(withLocale);

  // yup
  const language = get(_);
  
  yup.setLocale({
    mixed: {
      required: language('errors.requiredField'),
    },
    string: {
      email: language('errors.invalidEmail'),
    },
  });
};

export { _, setupI18n };