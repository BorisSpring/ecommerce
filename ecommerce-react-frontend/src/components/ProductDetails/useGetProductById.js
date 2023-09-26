import { useQuery } from "@tanstack/react-query";
import { getProduct as getProductApi } from "../../api/actions";
import { useParams } from "react-router-dom";

export function useGetProductById() {
  const { productId } = useParams();
  const { data: productById, isLoading } = useQuery({
    queryFn: async () => await getProductApi(productId),
    queryKey: ["product", productId],
  });

  return { productById, isLoading };
}
