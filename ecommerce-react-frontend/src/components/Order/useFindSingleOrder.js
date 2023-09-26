import { useQuery } from "@tanstack/react-query";
import { findSingleOrderById } from "../../api/actions";

export function useFindSingleOrder(id) {
  const { data: singleOrder, isLoading } = useQuery({
    queryFn: async () => await findSingleOrderById(id),
    queryKey: ["singleOrder", id],
  });

  return { singleOrder, isLoading };
}
