import React from 'react';
import './ProductCard.css';
import { useNavigate } from 'react-router-dom';
const Product = ({
  product: {
    brand,
    id,
    imageUrl,
    title,
    discountPrecent,
    discountPrice,
    price,
  },
}) => {
  const navigate = useNavigate();
  return (
    <div
      className='productCard  w-[11rem] lg:w-[15rem] m-3 transition-all cursor-pointer hover:shadow-xl shadow-md flex flex-col gap-[10px]'
      onClick={() => navigate(`/product/${id}`)}
    >
      <img
        src={imageUrl?.[0]}
        alt=''
        className='w-[11rem]  lg:w-[15rem]  h-[15rem] object-cover object-left-top'
      />
      <div className='textPart bg-white p-3 h-[100%] flex justify-between flex-col w-full'>
        <div>
          <p className='whitespace-normal'>{title}</p>
          <p className='font-bold opacity-60'>{brand}</p>
        </div>
        <div className='flex items-center space-x-2 whitespace-nowrap mt-2'>
          <p className='font-semibold'>{discountPrice}&euro;</p>
          <p className='font-sembiold line-through text-gray-600 '>
            {price}&euro;
          </p>
          <p className='text-green-600 font-semibold'>{discountPrecent}%</p>
        </div>
      </div>
    </div>
  );
};

export default Product;
