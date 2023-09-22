import { Button, Grid, Stack } from "@mui/material";
import React from "react";
import { formatCurrency } from "../../helpers/utils";
import StarBorderIcon from "@mui/icons-material/StarBorder";

const OrderDetailsCard = ({ orderItem }) => {
  const {
    product: { title, brand, imageUrl },
    size,
    color,
    quantity,
    price,
    discountPrice,
  } = orderItem;

  return (
    <>
      <Grid item xs={9} display={"flex"} alignItems={"center"} p={2} xl={10}>
        <Stack
          direction={"row"}
          alignItems={"center"}
          sx={{ gap: { xs: "8px", md: "12px", lg: "20px" } }}
        >
          <img
            src={imageUrl?.[0]}
            alt="Product from shop"
            className="w-[5rem] h-[5rem] object-cover object-center md:w-[7rem] md:h-[7rem]"
          />
          <div className="text-xs md:text-base flex justify-between flex-col space-y-1">
            <p>{title}</p>
            <div className="opacity-80 flex items-center gap-2 md:gap-3 text-xs">
              <p>Color: {color}</p>
              <p>Size: {size}</p>
            </div>
            <p className="text-sm opacity-70">Quantity: {quantity}</p>
            <p>Seller: {brand}</p>
            <p className="font-semibold">
              {formatCurrency(discountPrice || price)}
            </p>
          </div>
        </Stack>
      </Grid>
      <Grid item xs={3} xl={2} justifyContent={"flex-end"} display={"flex"}>
        <Button
          startIcon={<StarBorderIcon />}
          sx={{
            display: "flex",
            alignItems: "center",
            fontSize: { xs: "10px", md: "14px" },
          }}
        >
          Rate & Review Product
        </Button>
      </Grid>
    </>
  );
};

export default OrderDetailsCard;
