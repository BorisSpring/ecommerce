import React from "react";
import AdressCard from "../Checkout/AdressCard";
import { Box, Card, CircularProgress, Grid } from "@mui/material";
import OrderTraker from "./OrderTraker";
import { useParams } from "react-router-dom";
import { useFindSingleOrder } from "./useFindSingleOrder";
import OrderDetailsCard from "./OrderDetailsCard";
const OrderDetails = () => {
  const { orderId } = useParams();
  const { singleOrder, isLoading } = useFindSingleOrder(orderId);
  if (isLoading)
    return (
      <Box
        display={"flex"}
        position={"absolute"}
        justifyContent={"center"}
        alignItems={"center"}
      >
        <CircularProgress size="3rem" />
      </Box>
    );

  const { orderItems, lastName, firstName, adress, orderStatus, mobileNumber } =
    singleOrder;
  let activeStep;

  switch (orderStatus) {
    case "PENDING":
      activeStep = 0;
      break;
    case "CONFIRMED":
      activeStep = 1;
      break;
    case "DELIVERED":
      activeStep = 4;
      break;
    case "CANCELED":
      activeStep = 3;
      break;
    case "SHIPPED":
      activeStep = 2;
      break;
    default:
      break;
  }
  return (
    <div className="px-3 md:px-8 lg:md:px-10">
      <Card className="border mx-2 md:mx-3 mt-5 ">
        <h1 className="md:text-lg px-4 font-semibold  mt-5">Delivery Adress</h1>
        <AdressCard
          adres={adress}
          firstName={firstName}
          lastName={lastName}
          isFinalStep={true}
          mobileNumber={mobileNumber.number}
        />
      </Card>
      <OrderTraker activeStep={activeStep} />
      <Box className="space-y-5 mt-5 md:mt-10">
        <Grid
          container
          className="space-y-4 md:space-y-3 lg:space-y-5 shadow-md md:p-3 border"
          sx={{ alignItems: "center", justifyContent: "space-between" }}
        >
          {orderItems?.map((orderItem) => (
            <OrderDetailsCard orderItem={orderItem} key={orderItem.id} />
          ))}
        </Grid>
      </Box>
    </div>
  );
};

export default OrderDetails;
