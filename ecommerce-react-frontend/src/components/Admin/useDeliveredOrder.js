import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deliveredOrder as deliveredOrderApi } from "../../api/actions";
import { useLocation } from "react-router-dom";

export function useDeliveredOrder() {
  const queryClient = useQueryClient();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { mutate: deliveredOrder, isLoading: isSettingDelivered } = useMutation(
    {
      mutationFn: (orderId) => deliveredOrderApi(orderId),
      onSuccess: (data) => {
        if (data === true) {
          queryClient.invalidateQueries({
            queryKey: ["adminOrders", params?.get("page")],
          });
        }
      },
    }
  );
  return { deliveredOrder, isSettingDelivered };
}
