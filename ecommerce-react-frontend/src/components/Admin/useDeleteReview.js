import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useLocation, useNavigate } from "react-router-dom";
import { deleteReview as deleteReviewApi } from "../../api/actions";

export function useDeleteReview(totalPage = 0, currentPage, numberOfElements) {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { mutate: deleteReview, isLoading: isDeleting } = useMutation({
    mutationFn: (id) => deleteReviewApi(id),
    onSuccess: () => {
      if (numberOfElements === 1) {
        queryClient.removeQueries({
          queryKey: ["allReviews", window.location.href],
        });
        if (currentPage > 1) {
          params.set("page", currentPage);
          navigate(`?${decodeURIComponent(params.toString())}`);
        }
      } else {
        console.log(window.location.href);
        queryClient.invalidateQueries({
          queryKey: ["allReviews", params.toString()],
        });
      }
    },
  });

  return { deleteReview, isDeleting };
}
