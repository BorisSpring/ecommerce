import { useQuery } from '@tanstack/react-query';
import { getProductsByItemNameId } from '../../api/actions';

export function useGetProductsBySectionItemId(id) {
  const { data: products } = useQuery({
    queryKey: ['itemName', id],
    queryFn: async () => await getProductsByItemNameId(id),
  });
  return { products };
}
