import React from "react";
import MainCarousel from "../components/HomeCarousel/MainCarousel";
import HomeSectionCarousel from "../components/HomeSection/HomeSectionCarousel";
import { kurtaPage1 as data } from "../data/kurta.js";
const HomePage = () => {
  return (
    <div>
      <div className="">
        <MainCarousel />
      </div>
      <div className="py-20 space-y-20 flex flex-col justify-center px-5 lg:px-10">
        <HomeSectionCarousel data={data} sectionName="Womens dress" />
        <HomeSectionCarousel data={data} sectionName="Womens dress" />
      </div>
    </div>
  );
};

export default HomePage;
