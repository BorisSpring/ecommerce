import { useQuery } from "@tanstack/react-query";
import { useLocation } from "react-router-dom";
import { getAllRatings } from "../../api/actions";

export function useGetAllRatings() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { data: allRatings, isLoading } = useQuery({
    queryKey: ["allRatings", Number(params.get("page"))],
    queryFn: async () => await getAllRatings(params),
  });

  return { allRatings, isLoading };
}
