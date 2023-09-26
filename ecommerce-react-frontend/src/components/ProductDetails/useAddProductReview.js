import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addProductReview } from "../../api/actions";

export function useAddProductReview(productId) {
  const queryClient = useQueryClient();
  const { mutate: addReview, isLoading: isAdding } = useMutation({
    mutationFn: async (reviewRequest) => await addProductReview(reviewRequest),
    onSuccess: (data) => {
      if (data) {
        queryClient.invalidateQueries({
          queryKey: ["product", productId],
        });
      }
    },
  });
  return { addReview, isAdding };
}
