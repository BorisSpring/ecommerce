import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createProduct as createProductApi } from "../../api/actions";

export function useCreateProduct() {
  const queryClinet = useQueryClient();

  const { mutate: createProduct } = useMutation({
    mutationFn: async (productRequest) => createProductApi(productRequest),
    onSuccess: () => {
      queryClinet.invalidateQueries({
        queryKey: ["products"],
      });
    },
  });
  return { createProduct };
}
