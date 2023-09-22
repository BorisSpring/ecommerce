import { Card, TextField, Typography } from "@mui/material";
import {
  CardElement,
  IdealBankElement,
  useElements,
  useStripe,
} from "@stripe/react-stripe-js";
import React, { useState } from "react";
import Messages from "./Messages";
import { useGetLoggedUser } from "../Auth/useGetLoggedUser";
import { useCreateOrder } from "../Checkout/useCreateOrder";

const StripeForm = ({ orderRequest }) => {
  const elements = useElements();
  const stripe = useStripe();
  const [messages, setMessages] = useState([]);
  const { loggedUser } = useGetLoggedUser();
  const { createOrder } = useCreateOrder();

  if (!stripe || !elements) return;

  async function onSubmit(e) {
    e.preventDefault();
    if (!stripe || !elements) return;

    setMessages((prev) => [...prev, "Creating Payment Intent"]);
    const { clientSecret } = await fetch(
      `http://localhost:8080/api/stripe/create-payment-intent`,
      {
        method: "POST",
        body: JSON.stringify(loggedUser?.cart?.totalDiscountPrice),
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          "Content-Type": "application/json",
        },
      }
    ).then((res) => res.json());

    setMessages((prev) => [...prev, "Payment Intent created"]);

    const { paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
      payment_method: {
        card: elements.getElement(CardElement),
      },
    });

    setMessages((prev) => [
      ...prev,
      `Payment Intent: ${paymentIntent?.status} (${paymentIntent?.id}) `,
    ]);
    paymentIntent?.status === "succeeded" && createOrder(orderRequest);
  }

  return (
    <Card sx={{ width: "40%", mx: "auto", padding: "20px", my: "30px" }}>
      <Typography align="center" variant="h5">
        Payment Details
      </Typography>
      <form onSubmit={(e) => onSubmit(e)} id="payment-form flex flex-col">
        <TextField
          size="small"
          label="Card Holder Name"
          required
          fullWidth
          name="cardHolderName"
          variant="standard"
          sx={{ my: 2 }}
        />
        <IdealBankElement className="py-4" />
        <label htmlFor="card-element">Card</label>
        <CardElement id="Cart-element" />
        <button className="border-none bg-indigo-600 hover:bg-indigo-500 text-white px-2 py-1 md:px-3 lg:px-4  rounded-md my-5">
          Pay Order
        </button>
      </form>
      {messages?.length > 0 && <Messages messages={messages} />}
    </Card>
  );
};

export default StripeForm;
