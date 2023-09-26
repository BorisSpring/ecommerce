import { Grid } from "@mui/material";
import React, { useState } from "react";
import OrderCard from "./OrderCard";
import { useGetLoggedUser } from "../Auth/useGetLoggedUser";

const orderStatus = [
  { label: "On The Way", value: "shipped" },
  { label: "Delivered", value: "delivered" },
  { label: "Cancelled", value: "cancelled" },
  { label: "Returned", value: "returned" },
];

const Order = () => {
  const orders = useGetLoggedUser()?.loggedUser?.orders || [];

  const [filterOption, setFilterOption] = useState("all");

  let filteredOrders;

  switch (filterOption) {
    case "all":
      filteredOrders = orders;
      break;
    case "shipped":
      filteredOrders = orders?.filter?.(
        (order) => order.orderStatus === "SHIPPED"
      );
      break;
    case "delivered":
      filteredOrders = orders?.filter?.(
        (order) => order.orderStatus === "DELIVERED"
      );
      break;
    case "cancelled":
      filteredOrders = orders?.filter?.(
        (order) => order.orderStatus === "CONFIRMED"
      );
      break;
    case "returned":
      filteredOrders = orders?.filter?.(
        (order) => order.orderStatus === "RETURNED"
      );
      break;
    default:
      break;
  }

  return (
    <Grid container sx={{ justifyContent: "space-between" }} mt={1}>
      <Grid item xs={12} sm={2.5}>
        <div className="h-auto shadow-sm md:shadow-md bg-white p-5 sticky top-5">
          <h1 className="font-bold text-lg">Filter</h1>
          <div className="space-y-4 mt-10"></div>
          <h1 className="font-semibold text-sm">Order status</h1>
          <div className="flex  space-y-1 mt-1 flex-col">
            {orderStatus.map((option) => (
              <div className="flex items-center gap-[4px] md:gap-2 md:text-base text-xs">
                <input
                  value={filterOption}
                  onClick={() =>
                    setFilterOption((prev) =>
                      prev === option.value ? "all" : option.value
                    )
                  }
                  type="checkbox"
                  className="h-4 w-4 border-gray-300 text-indigo-600 accent-indigo-600 focus:ring-indigo-500 outline-none"
                />
                <label htmlFor={option.value}>{option.label}</label>
              </div>
            ))}
          </div>
        </div>
      </Grid>
      <Grid item xs={12} sm={9.5} p={1} className="space-y-2 lg:space-y-3">
        {filteredOrders?.map?.((order) => (
          <OrderCard order={order} key={order.id} />
        ))}
      </Grid>
    </Grid>
  );
};

export default Order;
