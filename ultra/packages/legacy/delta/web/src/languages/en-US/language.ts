/* eslint-disable max-len */
import CONFIGURATION from '../../configuration';
import type { Language, LanguageText, LanguageTemplate } from '../../languages';

const template: LanguageTemplate = {
  adverb: (adverb, text) => ({
    text: `${adverb} ${text}`,
  }),
  notFound: (replacement) => {
    if (replacement) {
      return {
        text: `No ${replacement} found...`,
        link: `Learn more about adding ${replacement}...`,
      };
    } else {
      return {
        text: 'Nothing was found...',
      };
    }
  },
  notFoundCreate: (replacement) => {
    if (replacement) {
      return {
        text: `No ${replacement}s found. To register a ${replacement}, open the "Create" menu
          in the top right and click "New ${replacement.charAt(0).toUpperCase() +
          replacement.slice(1)}."`,
        link: `Learn more about adding ${replacement}s...`,
      };
    } else {
      return {
        text: 'Nothing was found. To add something new, click "Create" in the top right.',
        link: 'Learn more about creating in your clinic...',
      };
    }
  },
  notFoundEdit: (replacement) => {
    if (replacement) {
      return {
        text: `No ${replacement}s found. To add a ${replacement}, click the "Edit" button in
        the top right`,
        link: `Learn more about adding ${replacement}s...`,
      };
    } else {
      return {
        text: 'Nothing was found. To add something new, click "Create" in the top right.',
        link: 'Learn more about creating in your clinic...',
      };
    }
  },
  addNewPlaceholder: (label, button, position) => ({
    text: `To add an ${label}, click "${button}" in the ${position}`,
  }),
  learnMoreAbout: (replacement) => ({
    text: `Learn more about ${replacement}...`,
  }),
  haveBeenRecorded: (replacement) => ({
    text: `No ${replacement} have been recorded`,
  }),
  haveBeenFound: (replacement) => ({
    text: `No ${replacement} have been recorded`,
  }),
  editPlaceholder: (replacement) => ({
    text: `Modify or edit this ${replacement}`,
  }),
  deletePlaceholder: (replacement) => ({
    text: `Delete this ${replacement}`,
  }),
};

const language: Language = {
  template,
  appUpdates: {
    updateAvailable: {
      text: 'Updates are available. Click to download...',
    },
    updateUpToDate: {
      text: `You are running the latest version of ${CONFIGURATION.app.name}`,
    },
    updateReady: {
      text: 'Updates have successfully been installed. Click to restart...',
    },
  },
  vocabulary: {
    strongly: {
      text: 'Strongly',
    },
  },
  app: {
    title: {
      text: 'MedIQ',
    },
    sidebar: {
      clinicHeader: {
        text: 'This Clinic',
      },
      myClinicHeader: {
        text: 'My Clinic',
      },
      dashboard: {
        text: 'Dashboard',
      },
      calendar: {
        text: 'Calendar',
      },
      clients: {
        text: 'Clients',
      },
      forms: {
        text: 'Forms',
      },
      sharingHeader: {
        text: 'Sharing',
      },
      administrationHeader: {
        text: 'Administration',
      },
      users: {
        text: 'Users & Groups',
      },
    },
  },
  survey: {
    title: {
      text: 'Survey',
    },
    headers: {
      surveys: {
        text: 'Surveys',
      },
    },
    placeholders: {
      noSurveys: template.haveBeenRecorded('surveys'),
      addSurveyPlaceholder: (position: string): LanguageText =>
        template.addNewPlaceholder('survey', 'Add Survey', position),
      learnMore: template.learnMoreAbout('surveys'),
      searchDescription: {
        text: 'Search for available survey templates',
      },
    },
    complete: {
      header: {
        text: 'Thank you for completing this survey!',
      },
      description: {
        text: 'This survey has successfully been submitted to your doctor. This data will be kept private, and will be anonymous if it is used for any third-party studies or research projects.',
        link: 'Learn more about survey policy...',
      },
    },
  },
  wizard: {
    placeholders: {
      unsavedChanges: {
        text: 'Continuing will close the wizard and clear your unsaved changes',
      },
    },
  },
  dates: {
    month: {
      text: 'Month',
    },
    day: {
      text: 'Day',
    },
    year: {
      text: 'Year',
    },
    week: {
      text: 'Week',
    },
  },
  form: {
    email: {
      text: 'Email',
    },
    phone: {
      text: 'Phone',
    },
    address: {
      text: 'Address',
    },
    password: {
      text: 'Password',
    },
    agreeability: {
      likely: {
        text: 'Likely',
      },
      unlikely: {
        text: 'Unlikely',
      },
      agree: {
        text: 'Agree',
      },
      disagree: {
        text: 'Disagree',
      },
      neitherAgreeNorDisagree: {
        text: 'Neither Agree Nor Disagree',
      },
    },
    severity: {
      none: {
        text: 'None',
      },
      mild: {
        text: 'Mild',
      },
      moderate: {
        text: 'Moderate',
      },
      severe: {
        text: 'Severe',
      },
      extreme: {
        text: 'Extreme',
      },
    },
    frequency: {
      never: {
        text: 'Never',
      },
      rarely: {
        text: 'Rarely',
      },
      sometimes: {
        text: 'Sometimes',
      },
      often: {
        text: 'Often',
      },
      always: {
        text: 'Always',
      },
    },
  },
  positions: {
    top: {
      text: 'top',
    },
    bottom: {
      text: 'bottom',
    },
    left: {
      text: 'left',
    },
    right: {
      text: 'right',
    },
  },
  buttons: {
    ok: {
      text: 'OK',
    },
    tryAgain: {
      text: 'Try Again',
    },
    cancel: {
      text: 'Cancel',
    },
    close: {
      text: 'Close',
    },
    forgotPassword: {
      text: 'Forgot Password',
    },
    signOut: {
      text: 'Sign Out',
    },
    report: {
      text: 'Report',
    },
    create: {
      text: 'Create',
    },
    filter: {
      text: 'Filter',
    },
    viewAll: {
      text: 'View All...',
    },
    complete: {
      text: 'Complete',
    },
    back: {
      text: 'Back',
    },
    next: {
      text: 'Next',
    },
    done: {
      text: 'Done',
    },
    quicklook: {
      text: 'Quick Look',
    },
    delete: {
      text: 'Delete',
    },
    learnMore: {
      text: 'Learn More',
    },
    continue: {
      text: 'Continue',
    },
    viewFile: {
      text: 'View File',
    },
    more: {
      text: 'More',
    },
    compare: {
      text: 'Compare',
    },
    submit: {
      text: 'Submit',
    },
    edit: {
      text: 'Edit',
    },
  },
  headers: {
    recents: {
      text: 'Recents',
    },
    today: {
      text: 'Today',
    },
    clinic: {
      text: 'This Clinic',
    },
    pinned: {
      text: 'Pinned',
    },
    unsavedChanges: {
      text: 'Unsaved Changes',
    },
    description: {
      text: 'Description',
    },
    alpha: {
      text: 'alpha',
    },
  },
  placeholders: {
    preRelease: {
      text: 'Welcome to the pre-release version of MedIQ!',
    },
    noRecents: {
      text: 'No recents found...',
    },
    noResults: {
      text: 'No results found...',
    },
    noPinned: {
      text: 'No items have been pinned',
    },
    addPinnedPlaceholder: {
      text: 'Any data that you pin to this file will show up here',
    },
    learnMoreAboutPinning: template.learnMoreAbout('pinning'),
    noDescription: {
      text: 'No description provided...',
    },
    noContactInformation: {
      text: 'No contact information found...',
    },
    noContacts: {
      text: 'No contacts found...',
    },
    noInformation: {
      text: 'No additional information found...',
    },
    notFound: (replacement?: string): LanguageText =>
      template.notFound(replacement),
  },
  errors: {
    internalError: {
      text: 'Something went wrong',
    },
    internalErrorInstructions: {
      text: 'We\'re not sure how this happened, but we can look into the issue if you click "Report."',
    },
    internalLoginError: {
      text: `We're having issues connecting to ${CONFIGURATION.app.name}. If your clinic is registered to a VPN, ensure you are connected and try again.`,
    },
    request: {
      text: 'Check to see if you are still online, or if you are connected to a VPN, make sure you are still logged in',
    },
    offline: {
      text: 'You are currently offline. Check your internet connection and try again.',
    },
    loginEmptyFields: {
      text: 'Please enter a valid email and password',
    },
    loginIncorrectFields: {
      text: 'Incorrect email or password. You can reset your password by contacting your administrator',
    },
  },
  insurance: {
    title: {
      text: 'Billing & Insurance',
    },
    healthCard: {
      title: {
        text: 'Health Card',
      },
    },
  },
  icds: {
    title: {
      text: 'ICD Codes',
    },
    description: {
      text: 'Search diagnosis and illnesses with ICD-11',
    },
    search: {
      text: 'Search ICD-11',
    },
    placeholders: {
      search: {
        text: 'Search for ICD-11\'s using information provided by the WHO',
      },
    },
  },
  records: {
    imaging: {
      title: {
        text: 'Imaging',
      },
      buttons: {
        newImage: {
          text: 'New Image',
        },
      },
    },
  },
  forms: {
    title: {
      text: 'Forms',
    },
    headers: {
      form: {
        text: 'Form',
      },
      newForm: {
        text: 'New Form',
      },
      survey: {
        text: 'Survey',
      },
      newSurvey: {
        text: 'New Survey',
      },
    },
    placeholders: {
      addFormDescription: {
        text: 'Create a new form for your patients',
      },
      addSurveyDescription: {
        text: 'Create a new survey for your patients',
      },
    },
  },
  events: {
    title: {
      text: 'Events & Appointments',
    },
    headers: {
      calendar: {
        text: 'Calendar',
      },
      events: {
        text: 'Events',
      },
      event: {
        text: 'Event',
      },
      editEvent: {
        text: 'Edit Event',
      },
    },
    descriptions: {
      createDescription: {
        text: 'Create a new appointment',
      },
      editDescription: template.editPlaceholder('event'),
      deleteDescription: template.deletePlaceholder('event'),
    },
    placeholders: {
      noEvents: template.haveBeenRecorded('events'),
      addEventPlaceholder: (position: string): LanguageText =>
        template.addNewPlaceholder('event', 'New Event', position),
      editEventDescription: (text) => ({
        text: `Edit ${text}`,
      }),
      learnMore: template.learnMoreAbout('events'),
      searchDescription: {
        text: 'Search for appointments and calendar events',
      },
    },
    buttons: {
      newEvent: {
        text: 'New Event',
      },
      viewEvent: {
        text: 'View Event',
      },
      seeInCalendar: {
        text: 'Show Event in Calendar',
      },
    },
  },
  visits: {
    title: {
      text: 'Visits & Calls',
    },
    headers: {
      visit: {
        text: 'Visit',
      },
      editVisit: {
        text: 'Edit Visit',
      },
    },
    descriptions: {
      createDescription: {
        text: 'Document a new visit or call',
      },
    },
    placeholders: {
      noVisits: template.haveBeenRecorded('visits'),
      addVisitPlaceholder: (position) =>
        template.addNewPlaceholder('visit', 'New Visit', position),
      editVisitDescription: (text) => ({
        text: `Edit ${text}`,
      }),
      learnMore: template.learnMoreAbout('visits'),
    },
    buttons: {
      newVisit: {
        text: 'New Visit',
      },
      viewVisit: {
        text: 'View Visit',
      },
    },
  },
  clients: {
    headers: {
      overview: {
        text: 'Overview',
      },
      documents: {
        text: 'Documents',
      },
      records: {
        text: 'Records',
      },
      contacts: {
        text: 'Contacts',
      },
    },
    people: {
      title: {
        text: 'People',
      },
      placeholders: {
        notFound: template.notFound('people'),
      },
    },
    patients: {
      title: {
        text: 'Patients',
      },
      placeholders: {
        addPatientDescription: {
          text: 'Add a new patient to this clinic',
        },
        editPatientDescription: (text) => ({
          text: `Edit ${text}'s information`,
        }),
        searchDescription: {
          text: 'Search for registered patients in this clinic',
        },
        notFoundCreate: template.notFoundCreate('patient'),
        notFoundEdit: template.notFoundEdit('patient'),
        notFound: template.notFound('patients'),
        noPatients: template.haveBeenFound('patients'),
      },
      search: {
        text: 'Search for Patients',
      },
      headers: {
        patient: {
          text: 'Patient',
        },
        editPatient: {
          text: 'Edit Patient',
        },
        newPatient: {
          text: 'New Patient',
        },
      },
      buttons: {
        viewPatient: {
          text: 'View Patient',
        },
      },
    },
    doctors: {
      title: {
        text: 'Doctors',
      },
      search: {
        text: 'Search for Doctors',
      },
      headers: {
        doctor: {
          text: 'Doctor',
        },
        editDoctor: {
          text: 'Edit Doctor',
        },
        newDoctor: {
          text: 'New Doctor',
        },
      },
      buttons: {
        viewDoctor: {
          text: 'View Doctor',
        },
      },
      placeholders: {
        addDoctorDescription: {
          text: 'Add a new doctor to this clinic',
        },
        notFoundCreate: template.notFoundCreate('doctor'),
        editDoctorDescription: (text) => ({
          text: `Edit Dr. ${text}'s information`,
        }),
        searchDescription: {
          text: 'Search for doctors in this clinic',
        },
        noDoctors: template.haveBeenFound('doctors'),
        notFoundEdit: template.notFoundEdit('doctor'),
        notFound: template.notFound('doctors'),
      },
    },
    emergencyContacts: {
      title: {
        text: 'Emergency Contacts',
      },
      placeholders: {
        noEmergencyContacts: template.haveBeenFound('contacts'),
        notFoundEdit: template.notFoundEdit('contact'),
        notFound: template.notFound('contacts'),
      },
    },
  },
  user: {
    title: {
      text: 'User',
    },
    headers: {
      newUser: {
        text: 'New User',
      },
      users: {
        text: 'Users',
      },
    },
    placeholders: {
      addUserDescription: {
        text: 'Add a new user to your clinic',
      },
      registerUserDescription: (text) => ({
        text: `Register ${text} to your clinic`,
      }),
      searchDescription: {
        text: 'Search for users in your clinic',
      },
    },
    registered: {
      title: {
        text: 'Registered',
      },
      search: {
        text: 'Search for Registered Users',
      },
    },
    pending: {
      title: {
        text: 'Pending',
      },
      placeholders: {
        notFoundCreate: template.notFoundCreate('user'),
      },
      search: {
        text: 'Search for Pending Users',
      },
    },
    signIn: {
      title: {
        text: 'Sign In',
      },
      login: {
        description: {
          text: 'Sign in below to access your clinic\'s database',
        },
        alphaDescription: {
          text: 'You are currently running a alpha version of our software. Contact one of our representatives if you do not have an account',
        },
      },
    },
    settings: {
      account: {
        title: {
          text: 'Account',
        },
      },
      options: {
        title: {
          text: 'Options',
        },
      },
      theme: {
        title: {
          text: 'Theme',
        },
      },
      about: {
        title: {
          text: 'About',
        },
      },
    },
  },
  legal: {
    alpha: {
      copyright: {
        text: 'Copyright (c) 2022 leftindust - All Rights Reserved',
      },
      agreement: {
        text: `You have access to this software because an authorized employee of leftindust
        gave you explicit permission to use this software. Under no circumstances can you:
        distribute MedIQ, modify or copy the contents of MedIQ, capture or digitally record any contents of MedIQ,
        or authorize anybody to use MedIQ without our explicit permission.`,
      },
      confidential: {
        text: 'This software is proprietary and confidential',
      },
    },
    placeholders: {
      byClickingNext: {
        text: 'By clicking continue, you are agreeing to these terms.',
      },
    },
  },
};

export default language;
