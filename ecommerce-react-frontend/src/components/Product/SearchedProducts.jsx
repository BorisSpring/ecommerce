import React from 'react';
import { useGetSerachedProducts } from './useGetSearchedProducts';
import Product from './Product';
import { useLocation, useParams } from 'react-router-dom';
import { useQueryClient } from '@tanstack/react-query';
import { getSearchedProducts } from '../../api/actions';

const SearchedProducts = () => {
  const { searchedProducts } = useGetSerachedProducts();
  const { searchQuery } = useParams();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const queryClient = useQueryClient();

  if (params.get('page') <= searchedProducts?.totalPages) {
    params.set('page', Number(params.get('page')) + 1);
    queryClient.prefetchQuery({
      queryKey: ['products', params.toString(), searchQuery],
      queryFn: async () => await getSearchedProducts(params, searchQuery),
    });
  }

  return <Product data={searchedProducts} />;
};

export default SearchedProducts;
