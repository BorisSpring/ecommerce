import { useQuery } from '@tanstack/react-query';
import { getSearchedProducts } from '../../api/actions';
import { useLocation, useParams } from 'react-router-dom';

export function useGetSerachedProducts() {
  const { searchQuery } = useParams();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { data: searchedProducts, isLoading } = useQuery({
    queryFn: async () => await getSearchedProducts(params, searchQuery),
    queryKey: ['products', params.toString(), searchQuery],
  });

  return { searchedProducts, isLoading };
}
