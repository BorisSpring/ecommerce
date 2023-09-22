import { useQuery } from "@tanstack/react-query";
import { findAllOrdersAdmin } from "../../api/actions";
import { useLocation } from "react-router-dom";

export function useFindAllOrdersAdmin() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { data: allOrdersAdmin, isLoading } = useQuery({
    queryFn: async () => await findAllOrdersAdmin(params),
    queryKey: ["adminOrders", params.get("page")],
  });
  return { allOrdersAdmin, isLoading };
}
