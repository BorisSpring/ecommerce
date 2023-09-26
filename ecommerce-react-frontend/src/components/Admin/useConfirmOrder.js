import { useMutation, useQueryClient } from "@tanstack/react-query";
import { confirmOrder as confirmOrderApi } from "../../api/actions";
import { useLocation } from "react-router-dom";

export function useConfirmeOrder() {
  const queryClient = useQueryClient();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { mutate: confirmOrder, isLoading: isConfirming } = useMutation({
    mutationFn: async (orderId) => await confirmOrderApi(orderId),
    onSuccess: (data) => {
      if (data === true) {
        queryClient.invalidateQueries({
          queryKey: ["adminOrders", params?.get("page")],
        });
      }
    },
  });
  return { confirmOrder, isConfirming };
}
