import React, { useState } from "react";
import AliceCarousel from "react-alice-carousel";
import HomeSectionCard from "./HomeSectionCard";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import { Button } from "@mui/material";

const responsive = {
  0: { items: 1 },
  620: { items: 2 },
  820: { items: 3 },
  1200: { items: 4 },
  1400: { items: 6 },
};

const HomeSectionCarousel = ({ data, sectionName }) => {
  const [activeIndex, setActiveIndex] = useState(0);

  const items = data
    ?.slice(0, 10)
    .map((item) => <HomeSectionCard item={item} />);

  const slidePrev = () => {
    setActiveIndex((index) => index - 1);
  };
  const slideNext = () => {
    setActiveIndex((index) => index + 1);
  };

  const syncActiveIndex = ({ item }) => {
    setActiveIndex(() => item);
  };
  return (
    <div className="relative px-4 lg:py-4 lg:px-8 mt-10 home-section-card-shadow">
      <h2 className="text-2xl font-bold text-gray-600 pt-2 px-5 md:px-7 ">
        {sectionName}
      </h2>
      <div className="relative p-5">
        {activeIndex > 0 && (
          <Button
            onClick={slidePrev}
            className="z-50"
            variant="contained"
            sx={{
              position: "absolute",
              top: "8rem",
              left: "-2rem",
              transform: "translateY(50%) rotate(90deg)",
              bgcolor: "white",
            }}
            aria-label="left"
          >
            <ChevronLeftIcon
              sx={{ transform: "rotate(-90deg)", color: "black" }}
            />
          </Button>
        )}
        <AliceCarousel
          mouseTracking
          items={items}
          controlsStrategy="alternate"
          disableButtonsControls
          responsive={responsive}
          disableDotsControls
          onSlideChange={syncActiveIndex}
          activeIndex={activeIndex}
          className="product-carousel"
        />
        {activeIndex !== items?.length - 5 && (
          <Button
            onClick={slideNext}
            variant="contained"
            sx={{
              position: "absolute",
              top: "8rem",
              right: "-2rem",
              transform: "translateY(50%) rotate(90deg)",
              bgcolor: "white",
            }}
          >
            <ChevronRightIcon
              sx={{
                transform: "rotate(-90deg)",
                color: "black",
              }}
            />
          </Button>
        )}
      </div>
    </div>
  );
};

export default HomeSectionCarousel;
