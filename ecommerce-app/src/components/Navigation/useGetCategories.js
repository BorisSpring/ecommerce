import { useQuery } from "@tanstack/react-query";
import { getCategories } from "../../api/actions";

export function useGetCategories() {
  const { data: categories } = useQuery({
    queryFn: async () => await getCategories(),
    queryKey: ["categories"],
  });

  return { categories };
}
