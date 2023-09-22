import React from "react";
import styled from "@emotion/styled";
import {
  Box,
  Button,
  Card,
  CardContent,
  CircularProgress,
  Typography,
} from "@mui/material";
import { useGetTotalRevenue } from "./useGetTotalRevenue";

const TrophyImage = styled("img")({
  right: 36,
  bottom: 20,
  height: 98,
  position: "absolute",
});

const Achivment = () => {
  const { revenue, isLoading } = useGetTotalRevenue();
  if (isLoading)
    return (
      <Box
        display={"flex"}
        height={"100px"}
        justifyContent={"center"}
        alignItems={"center"}
        width={"100%"}
      >
        <CircularProgress size="1.5rem" />
      </Box>
    );

  return (
    <Card
      sx={{
        position: "relative",
        bgcolor: "#343a40",
        display: "flex",
        height: "100%",
      }}
    >
      <CardContent>
        <Typography
          variant="h6"
          sx={{ letterSpacing: ".25px", color: "white" }}
        >
          Shop with Boris
        </Typography>
        <Typography variant="body" gutterBottom sx={{ color: "white" }}>
          Congratulations ✔
        </Typography>
        <Typography variant="h5" sx={{ color: "white", my: 2 }}>
          {" "}
          {(revenue / 1000).toFixed(1)}k Total Order Price
        </Typography>
        <Button size="small" sx={{ marginTop: "10px" }} variant="contained">
          View sales
        </Button>
        <TrophyImage src="https://cdn.pixabay.com/photo/2022/12/08/07/11/gold-trophy-cup-image-7642658_640.jpg" />
      </CardContent>
    </Card>
  );
};

export default Achivment;
