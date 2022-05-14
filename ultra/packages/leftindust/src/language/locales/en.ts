import type { Dictionary } from '..';

const language: Dictionary = {
  descriptions: {
    signIn: 'Sign in below to access your clinic\'s database.',
    alphaLoginNotice: 'You are currently running a alpha version of our software. Contact one of our representatives if you do not have an account.',
    patientsSearch: 'Search for registered patients in this clinic',
    addPatient: 'Add a new patient to this clinic',
    addDoctor: 'Add a new doctor to this clinic',
    noRecents: 'No recents found...',
    nda: `You have access to this software because an authorized employee of leftindust
        gave you explicit permission to use this software. Under no circumstances can you:
        distribute MedIQ, modify or copy the contents of MedIQ, capture or digitally record any contents of MedIQ,
        or authorize anybody to use MedIQ without our explicit permission.`,
    copyright: 'Copyright (c) 2022 leftindust - All Rights Reserved',
    confidential: 'This software is proprietary and confidential',
    byClickingNext: 'By clicking continue, you are agreeing to these terms.',
    noContactInformation: 'No contact information found...',
    addPinned: 'Any data that you pin to this file will show up here.',
    learnMorePinning: 'Learn more about pinning...',
  },
  errors: {
    connectionError: 'We\'re having issues connecting to MedIQ. If your clinic is registered to a VPN, ensure you are connected and try again.',
    runtimeError: 'The application has run into an unexpected issue. Click "Reload" to refresh the application, or "Report" for us to take a look.',
    internalError: 'Something went wrong.',
    loginIncorrectFields: 'Incorrect email or password. You can reset your password by contacting your administrator.',
    loginEmptyFields: 'Please enter a valid email and password.',
    offline: 'You are currently offline. Check your internet connection and try again.',
    requiredField: 'This is a required field',
    invalidEmail: 'Please enter a valid email',
  },
  generics: {
    tryAgain: 'Try Again',
    ok: 'OK',
    report: 'Report',
    signIn: 'Sign In',
    signOut: 'Sign Out',
    back: 'Back',
    next: 'Next',
    close: 'Close',
    more: 'More',
    reload: 'Reload',
    create: 'Create',
    delete: 'Delete',
    filter: 'Filter',
    account: 'Account',
    options: 'Options',
    about: 'About',
    edit: 'Edit',
    pinned: 'Pinned',
    viewFile: 'View File',
    learnMore: 'Learn More',
    quicklook: 'Quick Look',
    viewPatient: 'View Patient',
    viewDoctor: 'View Doctor',
    viewUser: 'View User',
    returnToHome: 'Return to home page',
    cancel: 'Cancel',
    done: 'Done',
    input: 'Input',
    output: 'Output',
    clone: 'Clone',
    remove: 'Remove',
    addInput: 'Add Input',
    addOption: 'Add Option',
    email: 'Email',
    emailPlaceholder: 'me@domain.com',
    password: 'Password',
    phone: 'Phone',
    address: 'Address',
    patientsSearch: 'Search for Patients',
    doctorsSearch: 'Search for Doctors',
    text: 'Text',
    number: 'Number',
    date: 'Date',
    singleSelect: 'Single-Select',
    multiSelect: 'Multi-Select',
    upload: 'Upload',
    required: 'Required',
    label: 'Label',
    placeholder: 'Placeholder',
    types: 'Types',
    optionIndexed: 'Option {index}',
    title: 'Title',
    selectDate: 'Select Date',
    default: 'Default',
    additional: 'Additional',
    body: 'Body',
    paragraph: 'Paragraph',
    showDateHeader: 'Show Date Header',
    header: 'Header',
    creationDate: 'Creation Date',
    type: 'Type',
    app: 'MedIQ',
    alpha: 'alpha',
    thisClinic: 'This Clinic',
    myClinic: 'My Clinic',
    clinic: 'Clinic',
    dashboard: 'Dashboard',
    calendar: 'Calendar',
    clients: 'Clients',
    administration: 'Administration',
    users: 'Users',
    usersAndGroups: 'Users & Groups',
    patients: 'Patients',
    doctors: 'Doctors',
    patient: 'Patient',
    doctor: 'Doctor',
    recents: 'Recents',
    documents: 'Documents',
    records: 'Records',
    contacts: 'Contacts',
    bundled: 'Bundled',
    stacked: 'Stacked',
    templates: 'Templates',
    forms: 'Forms & Surveys',
    analytics: 'Analytics',
    samples: 'Samples',
    settings: 'Settings',
    goodMorning: 'Good morning, {name}.',
    goodAfternoon: 'Good afternoon, {name}.',
    goodEvening: 'Good evening, {name}.',
    noPinned: 'No items have been pinned',
    allTemplates: 'All Templates',
    summary: 'Summary',
    files: 'Files',
    file: 'File',
    images: 'Images',
    image: 'Image',
    document: 'Document',
    uploadWithLabel: 'Upload {label}',
    all: 'All',
    allowMultiple: 'Allow Multiple',
    uploads: 'Uploads',
    inputOf: 'Input of "{label}"',
    description: 'Description',
    instructions: 'Instructions',
    subtitle: 'Subtitle',
    addSection: 'Add Section',
    section: 'Section',
    view: 'View {label}',
    sectionIndexed: 'Section {number}',
    inputIndexed: 'Input {number}',
    viewAll: 'View All',
    newDoctor: 'New Doctor',
  },
  examples: {
    totalPlateletCount: 'Eg. Total platelet count',
    mcl: 'Eg. mcL',
    text: 'Eg. Text',
    section: 'Eg. Section {number}',
  },
};

export default language;
