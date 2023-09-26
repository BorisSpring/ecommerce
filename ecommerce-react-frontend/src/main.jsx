import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider, createTheme } from "@mui/material";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";

const theme = createTheme({
  palette: {
    primary: {
      main: "#4F46E5",
    },
  },
});

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 120 * 1000,
    },
  },
});

const stripePromise = loadStripe(
  "pk_test_51Nqs5LHeveq0G9DVJOo9nnBgo1ocmJo9kvtoWw5tHPLneK8JOdLwUzmyjDFpfsLMY1Z9JwBDK3rreYsXbmONgwvC000TS6aEbI"
);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <QueryClientProvider client={queryClient}>
          <ReactQueryDevtools initialIsOpen={false} />
          <Elements stripe={stripePromise}>
            <App />
          </Elements>
        </QueryClientProvider>
      </ThemeProvider>
    </BrowserRouter>
  </React.StrictMode>
);
