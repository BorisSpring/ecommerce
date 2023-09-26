import { useQuery } from '@tanstack/react-query';
import { getFilteredProducts } from '../../api/actions';
import { useLocation } from 'react-router-dom';

export function useGetProductWithFilter(itemName) {
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const { data: filteredProducts } = useQuery({
    queryFn: async () => await getFilteredProducts(itemName, params),
    queryKey: ['products', window.location.href],
  });

  return { filteredProducts };
}
