import React from "react";
import { useGetLoggedUser } from "../Auth/useGetLoggedUser";
import { Navigate, Outlet } from "react-router-dom";

const ProtectedAdminRoute = () => {
  const { loggedUser } = useGetLoggedUser();

  if (loggedUser?.authority?.authority !== "ADMIN") {
    return <Navigate to="/" />;
  } else {
    return <Outlet />;
  }
};

export default ProtectedAdminRoute;
