import React from "react";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";
import { mainCarouselData } from "./MainCarouselData";
import { useNavigate } from "react-router-dom";
import "./MainCarousel.css";
const MainCarousel = () => {
  const navigate = useNavigate();

  const items = mainCarouselData.map((item) => (
    <img
      src={item.image}
      alt="Ecommerce items"
      className="cursor-pointer w-full object-cover object-center carousel-images h-[200px] sm:h-[300px]  md:h-[400px] xl:h-[650px]"
      onClick={() => navigate}
    />
  ));
  return (
    <AliceCarousel
      mouseTracking
      items={items}
      controlsStrategy="alternate"
      disableButtonsControls
      disableDotsControls
      autoPlay
      autoPlayInterval={5000}
      infinite
    />
  );
};

export default MainCarousel;
