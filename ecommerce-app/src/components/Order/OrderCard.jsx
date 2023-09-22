import { Button, Card, Grid, Typography } from "@mui/material";
import AdjustIcon from "@mui/icons-material/Adjust";
import React from "react";
import { useNavigate } from "react-router-dom";
import { formatCurrency, formatDate } from "../../helpers/utils";
import { useCancelOrder } from "./useCancelOrder";

const OrderCard = ({ order }) => {
  const navigate = useNavigate();
  const { cancelOrder } = useCancelOrder();
  const {
    id,
    createdAt,
    deliveredTime,
    adress: { city, country, adress },
    totalPrice,
    totalDiscountPrice,
    orderStatus,
    totalItem,
    lastName,
    firstName,
  } = order;

  return (
    <Card className="border" onClick={() => navigate(`/account/order/${id}`)}>
      <Grid container spacing={2} p={1}>
        <Grid item xs={7}>
          <div className="flex cursor-pointer gap-2">
            <img
              src="https://media.istockphoto.com/id/1225077798/photo/close-up-of-deliverer-attaching-data-label-on-cardboard-box-in-the-office.webp?b=1&s=612x612&w=0&k=20&c=6JiM6yKnOPV36EtO-NBfhGMyQYGylcjpOqOJclqhZtI="
              alt="Description for order status"
              className="object-cover object-center w-[4rem]  h-[4rem] md:w-[7rem] md:h-[7rem] aspect-square my-auto"
            />
            <div>
              <Typography
                variant="body2"
                sx={{ fontSize: { xs: "10px", sm: "12px", md: "14px" } }}
              >
                Total Items: {totalItem}
              </Typography>
              <Typography
                variant="body2"
                sx={{
                  fontSize: { xs: "10px", sm: "12px", md: "14px" },
                  marginTop: { md: "10px" },
                }}
              >
                Receiver: {firstName} {lastName}
              </Typography>
              <Typography
                variant="body2"
                sx={{
                  fontSize: { xs: "10px", sm: "12px", md: "14px" },
                  marginTop: { md: "10px" },
                  opacity: "92%",
                }}
              >
                Adress: {adress}
              </Typography>
              <Typography
                variant="body2"
                sx={{
                  fontSize: { xs: "10px", sm: "12px", md: "14px" },
                  opacity: "92%",
                }}
              >
                City: {city}
              </Typography>
              <Typography
                variant="body2"
                sx={{
                  fontSize: { xs: "10px", sm: "12px", md: "14px" },
                  opacity: "92%",
                }}
              >
                Country: {country}
              </Typography>
            </div>
          </div>
        </Grid>
        <Grid item xs={1} display={"flex"} justifyContent={"center"}>
          <Typography
            variant="body2"
            sx={{ fontSize: { xs: "10px", md: "12px", lg: "15px" } }}
          >
            {formatCurrency(totalPrice || totalDiscountPrice)}
          </Typography>
        </Grid>
        <Grid
          item
          xs={4}
          display={"flex"}
          flexDirection={"column"}
          alignItems={"flex-end"}
          sx={{ pr: { md: "20px" } }}
        >
          <Typography
            variant="body2"
            sx={{ fontSize: { xs: "10px", md: "12px", lg: "15px" } }}
          >
            <AdjustIcon
              sx={{
                width: { xs: "15px", md: "20px" },
                height: { xs: "15px", md: "20px" },
              }}
              className="text-green-600 mr-1 md:mr-2"
            />
            Ordered : {formatDate(createdAt)}
          </Typography>
          <Typography
            variant="body2"
            sx={{
              fontSize: { xs: "10px", md: "12px", lg: "15px" },
              marginTop: { xs: "10px", md: "5px" },
            }}
          >
            Expected Delivery {deliveredTime}
          </Typography>
          {false && (
            <Typography
              variant="body2"
              sx={{
                fontSize: { xs: "10px", md: "12px", lg: "15px" },
                marginTop: { xs: "10px", md: "5px" },
              }}
            >
              Delivered: {deliveredTime}
            </Typography>
          )}
          <Typography
            variant="body2"
            sx={{
              fontSize: { xs: "10px", md: "12px", lg: "15px" },
              marginTop: { xs: "10px", md: "5px" },
              fontWeight: "500",
            }}
          >
            Status: {orderStatus}
          </Typography>
          {orderStatus !== "CANCELED" && (
            <Button
              onClick={(e) => {
                e.stopPropagation();
                cancelOrder(id);
              }}
              variant="contained"
              size="small"
              sx={{ marginTop: "5px", fontSize: "10px" }}
            >
              Cancel Order
            </Button>
          )}
        </Grid>
      </Grid>
    </Card>
  );
};

export default OrderCard;
