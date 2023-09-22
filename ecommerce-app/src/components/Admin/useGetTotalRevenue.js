import { useQuery } from "@tanstack/react-query";
import { getTotalRevenu } from "../../api/actions";

export function useGetTotalRevenue() {
  const { data: revenue, isLoading } = useQuery({
    queryFn: async () => await getTotalRevenu(),
    queryKey: ["revenue"],
  });
  return { revenue, isLoading };
}
