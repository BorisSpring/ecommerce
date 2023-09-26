import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addCartItem } from "../../api/actions";

export function useAddCartItem() {
  const client = useQueryClient();

  const { mutate: addItemToCart, isLoading: isAddingCartItem } = useMutation({
    mutationFn: async (addCartItemRequest) =>
      await addCartItem(addCartItemRequest),
    onSuccess: (data) => {
      if (data === true) {
        client.invalidateQueries({
          queryKey: ["loggedUser"],
        });
      }
    },
  });
  return { addItemToCart, isAddingCartItem };
}
