const euro = "\u20AC";

export const sortOptions = [
  { name: "Most Popular", value: "most-popular" },
  { name: "Best Rating", value: "best-rating" },
  { name: "Newest", value: "newest" },
  { name: "Price: Low to High", value: "low-to-high" },
  { name: "Price: High to Low", value: "high-to-low" },
];
export const filters = [
  {
    id: "color",
    name: "Color",
    options: [
      { value: "white", label: "White", checked: false },
      { value: "beige", label: "Beige", checked: false },
      { value: "blue", label: "Blue", checked: true },
      { value: "brown", label: "Brown", checked: false },
      { value: "green", label: "Green", checked: false },
      { value: "purple", label: "Purple", checked: false },
    ],
  },
  {
    id: "discountRange",
    name: "Discount range",
    options: [
      { value: "20", label: "20% And Above" },
      { value: "30", label: "30% And Above" },
      { value: "40", label: "40% And Above" },
      { value: "50", label: "50% And Above" },
      { value: "60", label: "60% And Above" },
      { value: "70", label: "70% And Above" },
      { value: "80", label: "80% And Above" },
    ],
  },
  {
    id: "price",
    name: "price",
    options: [
      { value: "199-399", label: `199${euro} to 399${euro}` },
      { value: "399-599", label: `399${euro} to 599${euro}` },
      { value: "599-999", label: `599${euro} to 999${euro}` },
      { value: "999-1299", label: `999${euro} to 1299${euro}` },
    ],
  },
  {
    id: "size",
    name: "Size",
    options: [
      { value: "S", label: "S", checked: false },
      { value: "M", label: "M", checked: false },
      { value: "L", label: "L", checked: false },
      { value: "XL", label: "XL", checked: false },
    ],
  },
];
