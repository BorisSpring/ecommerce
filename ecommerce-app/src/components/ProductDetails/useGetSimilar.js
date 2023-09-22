import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { getSimmilarProducts } from "../../api/actions";

export function useGetSimilar(itemName) {
  const { productId } = useParams();
  const { data: similar, isLoading: isGettingSimilar } = useQuery({
    queryKey: [itemName, productId],
    queryFn: async ({ queryKey }) => {
      const [itemName] = queryKey;
      return await getSimmilarProducts(itemName);
    },
    enabled: !!itemName,
  });
  return { similar, isGettingSimilar };
}
