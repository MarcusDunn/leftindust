import type { Action } from '../types/helpers';

/* Available languages */
import enUS from './en-US/language';

export type LanguageText = {
  text: string;
  link?: string;
};

export type ReplacementLanguageText = Action<string[], LanguageText>;

export type LanguageTemplate = {
  adverb(adverb: string, text: string): LanguageText;
  notFound(replacement?: string): LanguageText;
  notFoundCreate(replacement?: string): LanguageText;
  notFoundEdit(replacement?: string): LanguageText;
  addNewPlaceholder(label: string, button: string, position: string): LanguageText;
  learnMoreAbout(replacement: string): LanguageText;
  haveBeenRecorded(replacement: string): LanguageText;
  haveBeenFound(replacement: string): LanguageText;
  editPlaceholder(replacement: string): LanguageText;
  deletePlaceholder(replacement: string): LanguageText;
}

export type Language = {
  template: LanguageTemplate;
  appUpdates: {
    updateAvailable: LanguageText;
    updateUpToDate: LanguageText;
    updateReady: LanguageText;
  };
  app: {
    title: LanguageText;
    sidebar: {
      clinicHeader: LanguageText;
      myClinicHeader: LanguageText;
      dashboard: LanguageText;
      calendar: LanguageText;
      clients: LanguageText;
      forms: LanguageText;
      sharingHeader: LanguageText;
      administrationHeader: LanguageText;
      users: LanguageText;
    };
  };
  vocabulary: {
    strongly: LanguageText;
  };
  dates: {
    month: LanguageText;
    day: LanguageText;
    year: LanguageText;
    week: LanguageText;
  };
  placeholders: {
    noRecents: LanguageText;
    noResults: LanguageText;
    noDescription: LanguageText;
    noInformation: LanguageText;
    noContactInformation: LanguageText;
    noContacts: LanguageText;
    noPinned: LanguageText;
    addPinnedPlaceholder: LanguageText;
    learnMoreAboutPinning: LanguageText;
    notFound: ReplacementLanguageText;
    preRelease: LanguageText;
  };
  headers: {
    recents: LanguageText;
    clinic: LanguageText;
    today: LanguageText;
    pinned: LanguageText;
    description: LanguageText;
    unsavedChanges: LanguageText;
    alpha: LanguageText;
  };
  positions: {
    top: LanguageText;
    bottom: LanguageText;
    left: LanguageText;
    right: LanguageText;
  };
  survey: {
    title: LanguageText;
    headers: {
      surveys: LanguageText;
    };
    placeholders: {
      noSurveys: LanguageText;
      searchDescription: LanguageText;
      addSurveyPlaceholder: ReplacementLanguageText;
      learnMore: LanguageText;
    };
    complete: {
      header: LanguageText;
      description: LanguageText;
    };
  };
  wizard: {
    placeholders: {
      unsavedChanges: LanguageText;
    };
  };
  form: {
    email: LanguageText;
    phone: LanguageText;
    address: LanguageText;
    password: LanguageText;
    agreeability: {
      likely: LanguageText;
      unlikely: LanguageText;
      agree: LanguageText;
      disagree: LanguageText;
      neitherAgreeNorDisagree: LanguageText;
    };
    severity: {
      none: LanguageText;
      mild: LanguageText;
      moderate: LanguageText;
      severe: LanguageText;
      extreme: LanguageText;
    };
    frequency: {
      never: LanguageText;
      rarely: LanguageText;
      sometimes: LanguageText;
      often: LanguageText;
      always: LanguageText;
    };
  };
  buttons: {
    ok: LanguageText;
    tryAgain: LanguageText;
    close: LanguageText;
    cancel: LanguageText;
    forgotPassword: LanguageText;
    signOut: LanguageText;
    report: LanguageText;
    create: LanguageText;
    filter: LanguageText;
    viewAll: LanguageText;
    complete: LanguageText;
    back: LanguageText;
    next: LanguageText;
    done: LanguageText;
    quicklook: LanguageText;
    delete: LanguageText;
    learnMore: LanguageText;
    continue: LanguageText;
    viewFile: LanguageText;
    more: LanguageText;
    compare: LanguageText;
    submit: LanguageText;
    edit: LanguageText;
  };
  errors: {
    internalError: LanguageText;
    internalErrorInstructions: LanguageText;
    internalLoginError: LanguageText;
    request: LanguageText;
    offline: LanguageText;
    loginEmptyFields: LanguageText;
    loginIncorrectFields: LanguageText;
  };
  insurance: {
    title: LanguageText;
    healthCard: {
      title: LanguageText;
    };
  };
  icds: {
    title: LanguageText;
    description: LanguageText;
    search: LanguageText;
    placeholders: {
      search: LanguageText;
    };
  };
  records: {
    imaging: {
      title: LanguageText;
      buttons: {
        newImage: LanguageText;
      }
    };
  };
  forms: {
    title: LanguageText;
    headers: {
      newForm: LanguageText;
      newSurvey: LanguageText;
      form: LanguageText;
      survey: LanguageText;
    };
    placeholders: {
      addFormDescription: LanguageText;
      addSurveyDescription: LanguageText;
    };
  };
  events: {
    title: LanguageText;
    buttons: {
      newEvent: LanguageText;
      viewEvent: LanguageText;
      seeInCalendar: LanguageText;
    };
    placeholders: {
      noEvents: LanguageText;
      addEventPlaceholder: ReplacementLanguageText;
      editEventDescription: ReplacementLanguageText;
      learnMore: LanguageText;
      searchDescription: LanguageText;
    };
    descriptions: {
      createDescription: LanguageText;
      editDescription: LanguageText;
      deleteDescription: LanguageText;
    };
    headers: {
      calendar: LanguageText;
      events: LanguageText;
      event: LanguageText;
      editEvent: LanguageText;
    };
  };
  visits: {
    title: LanguageText;
    buttons: {
      viewVisit: LanguageText;
      newVisit: LanguageText;
    };
    headers: {
      visit: LanguageText;
      editVisit: LanguageText;
    };
    descriptions: {
      createDescription: LanguageText;
    };
    placeholders: {
      noVisits: LanguageText;
      addVisitPlaceholder: ReplacementLanguageText;
      editVisitDescription: ReplacementLanguageText;
      learnMore: LanguageText;
    };
  };
  clients: {
    headers: {
      overview: LanguageText;
      documents: LanguageText;
      records: LanguageText;
      contacts: LanguageText;
    };
    people: {
      title: LanguageText;
      placeholders: {
        notFound: LanguageText;
      };
    };
    patients: {
      title: LanguageText;
      placeholders: {
        addPatientDescription: LanguageText;
        editPatientDescription: ReplacementLanguageText;
        searchDescription: LanguageText;
        noPatients: LanguageText;
        notFound: LanguageText;
        notFoundCreate: LanguageText;
        notFoundEdit: LanguageText;
      },
      search: LanguageText;
      headers: {
        newPatient: LanguageText;
        editPatient: LanguageText;
        patient: LanguageText;
      };
      buttons: {
        viewPatient: LanguageText;
      };
    };
    doctors: {
      title: LanguageText;
      search: LanguageText;
      headers: {
        newDoctor: LanguageText;
        editDoctor: LanguageText;
        doctor: LanguageText;
      };
      placeholders: {
        addDoctorDescription: LanguageText;
        editDoctorDescription: ReplacementLanguageText;
        searchDescription: LanguageText;
        noDoctors: LanguageText;
        notFound: LanguageText;
        notFoundCreate: LanguageText;
        notFoundEdit: LanguageText;
      };
      buttons: {
        viewDoctor: LanguageText;
      };
    };
    emergencyContacts: {
      title: LanguageText;
      placeholders: {
        noEmergencyContacts: LanguageText;
        notFound: LanguageText;
        notFoundEdit: LanguageText;
      };
    };
  };
  user: {
    title: LanguageText;
    headers: {
      users: LanguageText;
      newUser: LanguageText;
    };
    placeholders: {
      addUserDescription: LanguageText;
      registerUserDescription: ReplacementLanguageText;
      searchDescription: LanguageText;
    },
    pending: {
      title: LanguageText;
      search: LanguageText;
      placeholders: {
        notFoundCreate: LanguageText;
      }
    };
    registered: {
      title: LanguageText;
      search: LanguageText;
    };
    signIn: {
      title: LanguageText;
      login: {
        description: LanguageText;
        alphaDescription: LanguageText;
      };
    };
    settings: {
      account: {
        title: LanguageText;
      };
      options: {
        title: LanguageText;
      };
      theme: {
        title: LanguageText;
      };
      about: {
        title: LanguageText;
      };
    };
  };
  legal: {
    alpha: {
      copyright: LanguageText;
      agreement: LanguageText;
      confidential: LanguageText;
    };
    placeholders: {
      byClickingNext: LanguageText;
    }
  };
};

export const LANGUAGES: Record<string, Readonly<Language>> = {
  'en-US': enUS,
};

const language = (locale?: string): Language =>
  LANGUAGES[locale || 'en-US'] || LANGUAGES['en-US'];

export default language;
