import { Card, CardMedia } from "@mui/material";
import React from "react";

const HomeSectionCard = ({ item: { imageUrl, title, description } }) => {
  return (
    <Card
      className="border cursor-pointer flex flex-col items-center bg-white rounded-lg  overflow-hidden  mx-auto   "
      sx={{
        width: { xs: "15rem" },
        height: "20rem",
      }}
    >
      <CardMedia
        component="img"
        src={imageUrl?.[0]}
        sx={{
          objectFit: "cover",
          objectPosition: "center",
          width: { xs: "9rem", xl: "10rem" },
          height: { xs: "10rem", xl: "13rem" },
        }}
        alt={title}
      />
      <div className="p-4 h-[10rem]">
        <h3 className="text-base font-medium text-gray-900">{title}</h3>
        <p className="mt-2 text-base md:text-sm text-gray-500">
          {description?.length > 200
            ? `${description.slice(0, 200)}...`
            : description}
        </p>
      </div>
    </Card>
  );
};

export default HomeSectionCard;
