import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addProductRating } from "../../api/actions";

export function useAddProductRating(productId) {
  const queryClient = useQueryClient();

  const { mutate: addRating, isLoading: isPlacingRating } = useMutation({
    mutationFn: (productRatingRequest) =>
      addProductRating(productRatingRequest),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["product", productId],
      });
    },
  });

  return { addRating, isPlacingRating };
}
