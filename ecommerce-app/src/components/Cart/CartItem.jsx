import React from "react";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import { Button, IconButton } from "@mui/material";
import { useUpdateCartItem } from "./useUpdateCartItem";
import { useDeleteCartItem } from "./useDeleteCartItem";
import { formatCurrency } from "../../helpers/utils";
const CartItem = ({ cartItem, userId }) => {
  const { updateItem } = useUpdateCartItem();
  const { deleteItem } = useDeleteCartItem();

  const {
    product: { imageUrl, title, discountPrice, price, discountPrecent, brand },
    size,
    color,
    quantity,
    id,
  } = cartItem;

  return (
    <div className="p-5 shadow-md m-2 border rounded-md flex flex-col ">
      <div className="flex">
        <div className="w-[5.5rem] h-[5.5rem] lg:w-[10rem] lg:h-[10em]">
          <img
            src={imageUrl}
            alt="Ecommerce store cart item"
            className="object-cover object-center w-full h-full"
          />
        </div>
        <div className="ml-5 space-y-1 pt-2 flex flex-col justify-between">
          <div className="text-sm md:text-lg">
            <p className="font-semibold">{title}</p>
            <p className="opacity-70  text-gray-900">
              Size: {size}, {color} White
            </p>
            <p className="opacity-70 text-gray-700">Seller: {brand}</p>
          </div>
          <p className="text-small md:text-lg text-gray-900 space-x-2">
            {discountPrice > 0 ? (
              <>
                <span className="opacity-70 line-through text-gray-900">
                  {formatCurrency(price)}
                </span>
                <span className="font-semibold">
                  {formatCurrency(discountPrice)}
                </span>
                <span className="text-green-600 font-semibold">
                  {discountPrecent}%
                </span>
              </>
            ) : (
              <span>{formatCurrency(price)}</span>
            )}
          </p>
        </div>
      </div>
      <div className=" py-1 md:py-3 flex items-center">
        <IconButton
          color="primary"
          onClick={() =>
            updateItem({
              userId: userId,
              cartItemId: id,
              quantity: quantity - 1,
              size: size,
            })
          }
        >
          <RemoveCircleOutlineIcon />
        </IconButton>
        <div className="w-8 flex justify-center mx-3 border px-7 border-indigo-100 font-semibold">
          {quantity}
        </div>
        <IconButton
          color="primary"
          onClick={() =>
            updateItem({
              userId: userId,
              cartItemId: id,
              quantity: quantity + 1,
              size: size,
            })
          }
        >
          <AddCircleOutlineIcon />
        </IconButton>
        <Button
          variant="outlined"
          size="small"
          color="primary"
          sx={{ marginLeft: "auto", marginRight: "30px" }}
          onClick={() => {
            deleteItem({ userId: userId, itemId: id });
          }}
        >
          Remove
        </Button>
      </div>
    </div>
  );
};

export default CartItem;
