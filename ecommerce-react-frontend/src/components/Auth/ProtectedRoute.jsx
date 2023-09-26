import React from 'react';
import { useGetLoggedUser } from './useGetLoggedUser';
import { Navigate, Outlet } from 'react-router-dom';

const ProtectedRoute = () => {
  const { loggedUser } = useGetLoggedUser();

  if (!loggedUser?.lastName) {
    return <Navigate to='/' />;
  } else {
    return <Outlet />;
  }
};

export default ProtectedRoute;
