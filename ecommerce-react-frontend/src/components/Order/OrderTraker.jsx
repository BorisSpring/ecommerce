import { Step, StepLabel, Stepper } from "@mui/material";
import React from "react";

const steps = ["Order Confirmed", "Shipped", "Canceled", "Delivered"];

const OrderTraker = ({ activeStep }) => {
  return (
    <div className="w-full mt-5 md:mt-10">
      <Stepper activeStep={activeStep} alternativeLabel>
        {steps.map((step) => (
          <Step key={step}>
            <StepLabel>{step}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </div>
  );
};

export default OrderTraker;
