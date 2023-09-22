import React from "react";
import AdressCard from "./AdressCard";
import { Button, Divider } from "@mui/material";
import CartItem from "../Cart/CartItem";
import { useGetLoggedUser } from "../Auth/useGetLoggedUser";
import { formatCurrency } from "../../helpers/utils";

const OrderSummary = ({
  orderRequest: { firstName, lastName, adres, mobileNumber },
  setActiveStep,
}) => {
  const {
    loggedUser: {
      cart: {
        cartItems,
        totalPrice,
        totalQuantity,
        totalDiscountPrice,
        discount,
      },
      id,
    },
  } = useGetLoggedUser();

  return (
    <div>
      <div className="p-5 shadow-lg rounded-md border">
        <AdressCard
          firstName={firstName}
          lastName={lastName}
          adres={adres}
          mobileNumber={mobileNumber}
          isFinalStep={true}
        />
      </div>
      <div>
        <div>
          <div className="lg:grid grid-cols-3 relative">
            <div className="col-span-2 space-y-2 md:space-y-5">
              {cartItems?.map((cartItem) => (
                <CartItem cartItem={cartItem} userId={id} key={cartItem.id} />
              ))}
            </div>
            <div className=" sticky top-0 h-[100vh]  mt-5 lg:mt-0">
              <div className="border mt-2 px-3 py-2 rounded-md shadow-md">
                <p className="uppercase font-bold opacity-60 pb-4">
                  PRICE DETAILS
                </p>
                <Divider />
                <div className="space-y-3 font-semibold mb-2">
                  <div className="flex justify-between pt-3 text-black">
                    <span>Price</span>
                    <span className="font-sembiold text-green-700">
                      {formatCurrency(totalPrice)}
                    </span>
                  </div>
                  <div className="flex justify-between text-black pt-3">
                    <span>Discount </span>
                    <span className="font-semibold text-green-700">
                      {formatCurrency(discount)}
                    </span>
                  </div>
                  <div className="flex justify-between text-black pt-3">
                    <span>Delivery Charges</span>
                    <span className="font-semibold text-green-700">
                      {totalDiscountPrice > 99 ? "Free" : formatCurrency(20)}
                    </span>
                  </div>
                  <p className="text-lg my-2 text-slate-700">
                    Total quantity: {totalQuantity}
                  </p>
                </div>
                <Divider />
                <p className="flex justify-between mt-5">
                  <span className="text-black font-semibold text-xl">
                    Total Amount
                  </span>
                  <span className="text-green-700 font-semibold">
                    {formatCurrency(totalDiscountPrice)}
                  </span>
                </p>
                <Button
                  variant="contained"
                  color="primary"
                  fullWidth
                  size="large"
                  sx={{ marginTop: "15px", marginBottom: "10px" }}
                  onClick={() => setActiveStep()}
                >
                  Check Out
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrderSummary;
