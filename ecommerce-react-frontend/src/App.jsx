import React from 'react';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import HomePage from './Pages/HomePage';
import Navigation from './components/Navigation/Navigation';
import Footer from './components/Footer';
import ProductDetails from './components/ProductDetails/ProductDetails';
import Cart from './components/Cart/Cart';
import Checkout from './components/Checkout/Checkout';
import Order from './components/Order/Order';
import OrderDetails from './components/Order/OrderDetails';
import { Route, Routes } from 'react-router-dom';
import AddProductForm from './components/Admin/AddProductForm';
import OrdersOverwiev from './components/Admin/OrdersOverwiev';
import ProdcutsOverwiev from './components/Admin/ProductsOverview';
import RatingOverview from './components/Admin/RatingOverview';
import ReviewOverview from './components/Admin/ReviewOverview';
import ProtectedAdminRoute from './components/Admin/ProtectedAdminRoute';
import ProtectedRoute from './components/Auth/ProtectedRoute';
import SectionProducts from './components/Product/SectionProducts';
import SearchedProducts from './components/Product/SearchedProducts';

const App = () => {
  return (
    <div className='min-h-screen flex flex-col'>
      <Navigation />
      <Routes>
        <Route path='/product/:productId' element={<ProductDetails />} />
        <Route
          path='/:category/:section/:itemName'
          element={<SectionProducts />}
        />
        <Route path='/search/:searchQuery' element={<SearchedProducts />} />
        <Route path='/' element={<HomePage />} />
        <Route element={<ProtectedRoute />}>
          <Route path='/cart' element={<Cart />} />
          <Route path='/account/order' element={<Order />} />
          <Route path='/account/order/:orderId' element={<OrderDetails />} />
          <Route path='/checkout' element={<Checkout />} />
        </Route>
        <Route element={<ProtectedAdminRoute />}>
          <Route path='/admin/addProduct' element={<AddProductForm />} />
          <Route path='/admin/orders' element={<OrdersOverwiev />} />
          <Route path='/admin/products' element={<ProdcutsOverwiev />} />
          <Route
            path='/admin/products/updateProduct/:id'
            element={<AddProductForm />}
          />
          <Route path='/admin/reviews' element={<ReviewOverview />} />
          <Route path='/admin/ratings' element={<RatingOverview />} />
        </Route>
      </Routes>
      <Footer />
    </div>
  );
};

export default App;
