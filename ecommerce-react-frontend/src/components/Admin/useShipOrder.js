import { useMutation, useQueryClient } from "@tanstack/react-query";
import { shippedOrder } from "../../api/actions";
import { useLocation } from "react-router-dom";

export function useShipOrder() {
  const queryClient = useQueryClient();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { mutate: shipOrder, isLoading: isSettingShipping } = useMutation({
    mutationFn: async (id) => await shippedOrder(id),
    onSuccess: (data) => {
      if (data === true) {
        queryClient.invalidateQueries({
          queryKey: ["adminOrders", params?.get("data")],
        });
      }
    },
  });
  return { shipOrder, isSettingShipping };
}
