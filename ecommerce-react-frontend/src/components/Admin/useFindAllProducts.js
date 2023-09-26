import { useQuery } from "@tanstack/react-query";
import { getAllProducts as getAllProductsApi } from "../../api/actions";

export function useFindAllProducts(params) {
  const { data: allProducts, isLoading } = useQuery({
    queryFn: async () => getAllProductsApi(params),
    queryKey: ["allProducts", params.get("page")],
  });
  return { allProducts, isLoading };
}
