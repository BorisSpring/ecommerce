import { useMutation, useQueryClient } from "@tanstack/react-query";
import { cancelOrder as cancelOrderApi } from "../../api/actions";

export function useCancelOrder() {
  const queryClient = useQueryClient();

  const { mutate: cancelOrder } = useMutation({
    mutationFn: async (orderId) => await cancelOrderApi(orderId),
    onSuccess: (data) => {
      if (data === true) {
        queryClient.invalidateQueries({
          // queryKey: ["loggedUser", localStorage.getItem("jwt")],
          queryKey: ["loggedUser"],
        });
        queryClient.invalidateQueries({
          queryKey: ["adminOrders"],
        });
      }
    },
  });
  return { cancelOrder };
}
