import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { getSimmilarProducts } from '../../api/actions';

export function useGetSimilar(itemName) {
  const innerWidth = window.innerWidth;
  let pageSize;

  switch (true) {
    case innerWidth < 530:
      pageSize = 5;
      break;
    case innerWidth < 801:
      pageSize = 12;
      break;
    case innerWidth < 1101:
      pageSize = 15;
      break;
    case innerWidth < 1462:
      pageSize = 20;
      break;
    case innerWidth < 1619:
      pageSize = 18;
      break;
    case innerWidth > 1880:
      pageSize = 21;
      break;
    default:
      pageSize = (innerWidth / 250) * 3;
      break;
  }
  console.log(pageSize);
  const { productId } = useParams();
  const { data: similar, isLoading: isGettingSimilar } = useQuery({
    queryKey: [itemName, productId],
    queryFn: async ({ queryKey }) => {
      const [itemName] = queryKey;
      return await getSimmilarProducts(itemName, pageSize);
    },
    enabled: !!itemName,
  });
  return { similar, isGettingSimilar };
}
