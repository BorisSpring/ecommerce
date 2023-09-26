import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteOrderByIdAdmin } from "../../api/actions";
import { useLocation, useNavigate } from "react-router-dom";

export function useDeleteOrderById(allOrderAdmin) {
  const queryClient = useQueryClient();
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);

  const { mutate: deleteOrderAdmin, isLoading: isDeleting } = useMutation({
    mutationFn: async (id) => await deleteOrderByIdAdmin(id),
    onSuccess: (data) => {
      if (data === true) {
        if (
          allOrderAdmin?.numberOfElements === 1 &&
          allOrderAdmin?.number !== 0
        ) {
          params.set("page", allOrderAdmin.number);
          navigate(`?${decodeURIComponent(params.toString())}`);
        }
        queryClient.invalidateQueries({
          queryKey: ["adminOrders", params?.get("page")],
        });
      }
    },
  });

  return { deleteOrderAdmin, isDeleting };
}
