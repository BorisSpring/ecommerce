import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteProductById } from "../../api/actions";
import { useLocation, useNavigate } from "react-router-dom";

export function useDeleteProduct(handlePageChange, allProducts) {
  const queryClient = useQueryClient();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const navigate = useNavigate();

  const { mutate: deleteProduct } = useMutation({
    mutationFn: (productId) => deleteProductById(productId),
    onSuccess: () => {
      if (allProducts?.totalElements === 1 && allProducts?.pageNumber !== 0) {
        navigate(`?${decodeURIComponent(params.toString())}`);
      } else {
        queryClient.invalidateQueries({
          queryKey: ["allProducts", params?.get("page")],
        });
      }
    },
  });
  return { deleteProduct };
}
