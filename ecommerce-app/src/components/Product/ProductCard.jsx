import React from "react";
import "./ProductCard.css";
import { useNavigate } from "react-router-dom";
const Product = ({ product }) => {
  const navigate = useNavigate();
  return (
    <div
      className="productCard  w-[11rem] lg:w-[15rem] m-3 transition-all cursor-pointer hover:shadow-xl shadow-md flex flex-col gap-[10px]"
      onClick={() => navigate(`/product/${product.id}`)}
    >
      {/* <div className="h-[15rem] lg:h-[20rem]"> */}
      <img
        src={product.imageUrl}
        alt=""
        className="w-[11rem]  lg:w-[15rem]  h-[15rem] object-cover object-left-top"
      />
      {/* </div> */}
      <div className="textPart bg-white p-3 h-[100%] flex justify-between flex-col w-[11rem] lg:w[-15rem]">
        <div>
          <p className="font-bold opacity-60">{product.brand}</p>
          <p>{product.title}</p>
        </div>
        <div className="flex items-center space-x-2 whitespace-nowrap mt-2">
          <p className="font-semibold">{product.selling_price}</p>
          <p className="font-sembiold line-through text-gray-600 ">
            {product.price}
          </p>
          <p className="text-green-600 font-semibold">{product.disscount}</p>
        </div>
      </div>
    </div>
  );
};

export default Product;
