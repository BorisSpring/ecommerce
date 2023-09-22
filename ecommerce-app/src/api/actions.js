import axios from "axios";

const API_URL = "http://localhost:8080";

const ecommerceApi = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("jwt")}`,
  },
});

const ecommerceApiNoJwt = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

const axiosWithLogging = axios.create({
  baseURL: API_URL,
});

axiosWithLogging.interceptors.request.use(
  (config) => {
    console.log("Request:", config);
    return config;
  },
  (error) => {
    console.error("Request Error:", error);
    return Promise.reject(error);
  }
);

export async function registerUser(registerRequest) {
  try {
    const { data } = await ecommerceApiNoJwt.post(
      "/auth/signup",
      registerRequest
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function loginUser(loginRequest) {
  try {
    const { data } = await ecommerceApiNoJwt.post("/auth/login", loginRequest);
    console.log(data, "Data");
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getLoggedUser() {
  try {
    const jwtToken = localStorage.getItem("jwt");

    const { data } = await axiosWithLogging.post(
      `/api/orders/userProfile`,
      null,
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function addCartItem(addCartItemRequest) {
  try {
    const { data } = await ecommerceApi.post(
      "/api/carts/addCartItem",
      addCartItemRequest
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function updateCartItem(updateCartItemRequest) {
  try {
    const { data } = await ecommerceApi.post(
      "/api/carts/updateCartItem",
      updateCartItemRequest
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function deleteCartItem(userId, cartItemId) {
  try {
    const { data } = await ecommerceApi.delete(
      `/api/carts/${userId}/${cartItemId}`
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function createOrder(orderRequest) {
  try {
    const { data } = await ecommerceApi.post(`/api/orders`, orderRequest);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function findSingleOrderById(orderId) {
  try {
    const { data } = await ecommerceApi.get(`/api/orders/${orderId}`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function findAllOrdersAdmin(params) {
  try {
    const { data } = await ecommerceApi.get("/api/orders", { params: params });
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function deleteOrderByIdAdmin(orderId) {
  try {
    const { data } = await ecommerceApi.delete(`/api/orders/${orderId}`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function cancelOrder(orderId) {
  try {
    const { data } = await ecommerceApi.post(`/api/orders/${orderId}/cancel`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function deliveredOrder(orderId) {
  try {
    const { data } = await ecommerceApi.post(
      `/api/orders/${orderId}/delivered`
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function shippedOrder(orderId) {
  try {
    const { data } = await ecommerceApi.post(`/api/orders/${orderId}/ship`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function confirmOrder(orderId) {
  try {
    const { data } = await ecommerceApi.post(`/api/orders/${orderId}/confirme`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getFilteredProducts(itemName, params) {
  try {
    const { data } = await ecommerceApi.get(
      `/api/products/category/${itemName}`,
      { params: params }
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getProduct(productId) {
  try {
    const { data } = await ecommerceApi.get(`/api/products/${productId}`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function createProduct(productRequest) {
  try {
    const { data } = await ecommerceApi.post(`/api/products`, productRequest);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getSearchedProducts(searchQuery) {
  try {
    const { data } = await ecommerceApi.get(`/api/products/${searchQuery}`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function deleteProductById(productID) {
  try {
    const { data } = await ecommerceApi.delete(`/api/products/${productID}`);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getCategories() {
  try {
    const res = await fetch(
      "http://localhost:8080/api/products/findCategories",
      {
        method: "POST",
      }
    );
    return await res.json();
  } catch (error) {
    console.log(error.message);
  }
}

export async function addProductRating(productRatingRequest) {
  try {
    const { data } = await ecommerceApi.post(
      `/api/ratings`,
      productRatingRequest
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function updateRating(productRatingRequest) {
  try {
    const { data } = await ecommerceApi.post(
      `/api/ratings`,
      productRatingRequest
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function deleteRating(ratingId) {
  try {
    const { data } = await ecommerceApi.delete(
      `/api/ratings?ratingId=${ratingId}`
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getAllProducts(params) {
  try {
    const { data } = await ecommerceApi.get("/api/products/all", {
      params: params,
    });
    return data;
  } catch (err) {
    console.log(err.message);
  }
}

export async function getSimmilarProducts(itemName) {
  try {
    const { data } = await ecommerceApi.post(
      `/api/products/similar/${itemName}/12`
    );
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getAllRatings(params) {
  try {
    const { data } = await ecommerceApi.get("/api/ratings/findAll", {
      params: params,
    });
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getAllReviews(params) {
  try {
    const { data } = await ecommerceApi.get(`/api/reviews`, { params: params });
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function addProductReview(reviewRequest) {
  try {
    const { data } = await ecommerceApi.post(`/api/reviews`, reviewRequest);
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function deleteReview(id) {
  try {
    const { data } = await ecommerceApi.delete("/api/reviews", {
      params: { id: id },
    });
    return data;
  } catch (error) {
    console.log(error.message);
  }
}

export async function getTotalRevenu() {
  try {
    const { data } = await ecommerceApi.post("/api/orders/revenue");
    return data;
  } catch (error) {
    console.log(error.message);
  }
}
