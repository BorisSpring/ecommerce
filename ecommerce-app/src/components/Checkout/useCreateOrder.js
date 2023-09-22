import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createOrder as createOrderApi } from "../../api/actions";
import { useNavigate } from "react-router-dom";

export function useCreateOrder() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { mutate: createOrder } = useMutation({
    mutationFn: (orderRequest) => createOrderApi(orderRequest),
    onSuccess: (data) => {
      if (data?.id) {
        queryClient.invalidateQueries({
          queryKey: ["loggedUser"],
        });
        queryClient.invalidateQueries({
          queryKey: ["adminOrders"],
        });
        navigate(`/account/order/${data.id}`);
      }
    },
  });
  return { createOrder };
}
