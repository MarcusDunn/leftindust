import type { NamesFragment } from '../requests/fragments';
import language from '../languages';

export type MultipleChoiceInput = {
  value: string;
  abbreviation?: string;
}

const {
  none,
  mild,
  moderate,
  severe,
  extreme,
} = language().form.severity;

const {
  never,
  rarely,
  sometimes,
  often,
  always,
} = language().form.frequency;

export const severityScale: MultipleChoiceInput[] = [
  {
    value: none.text,
    abbreviation: none.text,
  },
  {
    value: mild.text,
    abbreviation: mild.text,
  },
  {
    value: moderate.text,
    abbreviation: moderate.text,
  },
  {
    value: severe.text,
    abbreviation: severe.text,
  },
  {
    value: extreme.text,
    abbreviation: extreme.text,
  },
];

export const frequencyScale: MultipleChoiceInput[] = [
  {
    value: never.text,
    abbreviation: never.text,
  },
  {
    value: rarely.text,
    abbreviation: rarely.text,
  },
  {
    value: sometimes.text,
    abbreviation: sometimes.text,
  },
  {
    value: often.text,
    abbreviation: often.text,
  },
  {
    value: always.text,
    abbreviation: always.text,
  },
];

export const agreeabilityScale: MultipleChoiceInput[] = [
  {
    value: language().template.adverb(
      language().vocabulary.strongly.text,
      language().form.agreeability.disagree.text,
    ).text,
  },
  {
    value: language().form.agreeability.disagree.text,
  },
  {
    value: language().form.agreeability.neitherAgreeNorDisagree.text,
  },
  {
    value: language().form.agreeability.agree.text,
  },
  {
    value: language().template.adverb(
      language().vocabulary.strongly.text,
      language().form.agreeability.agree.text,
    ).text,
  },
];

export const getTitleFromNames = (names: NamesFragment[], defaultTitle: string): string => {
  let title = defaultTitle;

  const getName = (first: string, last: string, middle?: string) =>
    `${first} ${middle?.charAt(0) ? middle?.charAt(0) + '. ' : ' '}${last}`;

  switch (names.length) {
    case 1:
      // eslint-disable-next-line no-case-declarations
      const { firstName, middleName, lastName } = names[0];
      title = getName(firstName, lastName, middleName);
      break;
    case 2:
      title = `${getName(names[0].firstName, names[0].lastName, names[0].middleName)} & ${getName(names[1].firstName, names[1].lastName, names[1].middleName)}`;
      break;
    default:
      if (names.length >= 3) {
        const { firstName, middleName, lastName } = names[0];
        title = `${getName(firstName, lastName, middleName)} +${names.length - 1} others`;
      }
      break;
  }

  return title;
};

export const getTitleFromText = (values: string[], defaultTitle: string): string => {
  let title = defaultTitle;
  switch (values.length) {
    case 1:
      title = values[0];
      break;
    case 2:
      title = `${values[0]} & ${values[1]}`;
      break;
    default:
      if (values.length >= 3) {
        title = `${values[0]} +${values.length - 1} more`;
      } else {
        title = '';
      }
      break;
  }
  return title;
};
