import React from 'react';
import MainCarousel from '../components/HomeCarousel/MainCarousel';
import HomeSectionCarousel from '../components/HomeSection/HomeSectionCarousel';
import { useGetCategories } from '../components/Navigation/useGetCategories';
const HomePage = () => {
  const { categories } = useGetCategories();
  return (
    <div>
      <div>
        <MainCarousel />
      </div>
      <div className='py-20 space-y-20 flex flex-col justify-center px-5 lg:px-10'>
        {categories?.map((category) =>
          category?.sections?.map((section) =>
            section?.items?.map((item) => (
              <HomeSectionCarousel
                item={item}
                categoryName={category?.categoryName}
                key={item.id}
              />
            ))
          )
        )}
      </div>
    </div>
  );
};

export default HomePage;
