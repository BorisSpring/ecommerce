import * as React from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import DeliveryAdressForm from './DeliveryAdressForm';
import OrderSummary from '../../components/Checkout/OrderSummary';
import StripeForm from '../Stripe/StripeForm';
import { useTheme } from '@emotion/react';
import { useMediaQuery } from '@mui/material';

const steps = ['Login', 'Add delivery adress', 'Order summary', 'Payment'];

const Checkout = () => {
  const [orderRequest, setOrderRequest] = React.useState({});
  const theme = useTheme();
  const isMediumScren = useMediaQuery(theme.breakpoints.up('md'));
  {
    const [activeStep, setActiveStep] = React.useState(1);

    return (
      <Box
        sx={{
          width: '100%',
          flexGrow: 1,
          px: { sm: '40px' },
          my: '50px',
        }}
      >
        <Stepper activeStep={activeStep}>
          {steps.map((label, index) => {
            const stepProps = {};
            const labelProps = {};
            return (
              <Step key={label} {...stepProps}>
                <StepLabel {...labelProps}>{isMediumScren && label}</StepLabel>
              </Step>
            );
          })}
        </Stepper>
        <Box
          sx={{
            width: '100%',
            px: { xs: '40px', lg: '40px' },
            mt: '50px',
          }}
        >
          {activeStep === 1 && (
            <DeliveryAdressForm
              setOrderRequest={setOrderRequest}
              setActiveStep={() => setActiveStep((prev) => prev + 1)}
            />
          )}
          {activeStep === 2 && (
            <OrderSummary
              orderRequest={orderRequest}
              setActiveStep={() => setActiveStep((prev) => prev + 1)}
            />
          )}
          {activeStep === 3 && <StripeForm orderRequest={orderRequest} />}
        </Box>
      </Box>
    );
  }
};

export default Checkout;
