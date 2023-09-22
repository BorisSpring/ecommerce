export function formatCurrency(value) {
  const formatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "EUR",
  });
  return formatter.format(value);
}

export function formatDate(date) {
  const formatter = new Intl.DateTimeFormat("en-US", {
    day: "2-digit",
    month: "long",
    year: "numeric",
  });

  return formatter.format(new Date(date));
}
