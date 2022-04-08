export const email = (addresses: string[]): void => {
  const mailTo = JSON.stringify(addresses)
    .replace(/[[\]&"]+/g, '');

  window.location.href = `mailto:?bcc=${mailTo}`;
};