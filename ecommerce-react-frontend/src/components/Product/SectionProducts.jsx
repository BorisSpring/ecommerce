import React from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { useGetProductWithFilter } from './useGetProductsWithFilter';
import { useQueryClient } from '@tanstack/react-query';
import { getFilteredProducts } from '../../api/actions';
import Product from './Product';

const SectionProducts = () => {
  const location = useLocation();
  const { itemName } = useParams();
  const { filteredProducts } = useGetProductWithFilter(itemName);
  const queryClient = useQueryClient();

  const params = new URLSearchParams(location.search);

  if (filteredProducts?.number < filteredProducts?.totalPages) {
    params.set('page', Number(filteredProducts.number) + 2);
    queryClient.prefetchQuery({
      queryKey: [
        'products',
        window.location.href.replace(
          `page=${filteredProducts.number + 1}`,
          `page=${filteredProducts.number + 2}`
        ),
      ],
      queryFn: async () => await getFilteredProducts(itemName, params),
    });
  }

  return <Product data={filteredProducts} />;
};

export default SectionProducts;
