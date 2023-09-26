import { useQuery } from "@tanstack/react-query";
import { useLocation } from "react-router-dom";
import { getAllReviews } from "../../api/actions";

export function useGetAllReviews() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { data: allReviews, isLoading } = useQuery({
    queryKey: ["allReviews", params.toString()],
    queryFn: async () => await getAllReviews(params),
  });
  return { allReviews, isLoading };
}
