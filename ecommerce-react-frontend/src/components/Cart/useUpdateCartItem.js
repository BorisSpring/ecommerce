import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateCartItem } from "../../api/actions";

export function useUpdateCartItem() {
  const queryClient = useQueryClient();

  const { mutate: updateItem } = useMutation({
    mutationFn: (updateRequest) => updateCartItem(updateRequest),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["loggedUser"],
      });
    },
  });
  return { updateItem };
}
