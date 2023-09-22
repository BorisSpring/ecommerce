import { useQuery } from "@tanstack/react-query";
import { getSearchedProducts } from "../../api/actions";

export function useGetSerachedProducts(query) {
  const { data: searchedProducts } = useQuery({
    queryFn: getSearchedProducts(query),
    queryKey: ["products", query],
  });

  return { searchedProducts };
}
