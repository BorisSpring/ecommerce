import { Avatar, Grid } from "@mui/material";
import React from "react";

const ProductReview = ({ review, userName }) => {
  return (
    <Grid container spacing={1} gap={1} p={1}>
      <Grid item xs={2} md={1}>
        <Avatar
          sx={{
            text: "white",
            width: "48px",
            height: "48px",
            bgcolor: "#9155fd",
            marginRight: "10px",
          }}
        >
          R
        </Avatar>
      </Grid>
      <Grid item xs={9}>
        <div>
          <div className="font-semibold text-lg">{userName}</div>
          <div className="opacity-70">
            {" "}
            {new Date(review?.createdAt).toDateString()}
          </div>
        </div>
        <div>
          <p>{review?.review}</p>
        </div>
      </Grid>
    </Grid>
  );
};

export default ProductReview;
