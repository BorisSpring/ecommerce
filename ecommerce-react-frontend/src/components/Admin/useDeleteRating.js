import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteRating as deleteRatingApi } from "../../api/actions";
import { useNavigate } from "react-router-dom";

export function useDeleteRating(pageable) {
  const navigate = useNavigate();
  const queryclient = useQueryClient();

  const { mutate: deleteRating, isLoading: isDeleting } = useMutation({
    mutationFn: (productRatingId) => deleteRatingApi(productRatingId),
    onSuccess: () => {
      if (pageable?.numberOfElements === 1 && pageable.number !== 0) {
        navigate(`?page=${pageable.number}`);
      } else {
        queryclient.invalidateQueries({
          queryKey: ["allRatings", Number(pageable.number + 1)],
        });
      }
    },
  });
  return { deleteRating, isDeleting };
}
