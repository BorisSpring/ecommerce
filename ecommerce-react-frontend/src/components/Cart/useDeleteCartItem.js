import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteCartItem } from "../../api/actions";

export function useDeleteCartItem() {
  const queryClient = useQueryClient();

  const { mutate: deleteItem } = useMutation({
    mutationFn: ({ userId, itemId }) => deleteCartItem(userId, itemId),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: ["loggedUser"],
      });
    },
  });
  return { deleteItem };
}
