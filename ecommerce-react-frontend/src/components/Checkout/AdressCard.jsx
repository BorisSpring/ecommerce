import { Button } from '@mui/material';
import React from 'react';

const AdressCard = ({
  firstName,
  lastName,
  mobileNumber,
  isFinalStep,
  setActiveStep,
  setOrderRequest,
  adres,
}) => {
  const { city, country, adress, state, postalCode, id } = adres;
  return (
    <div className=' px-2 py-4 rounded-md m-2 space-y-2'>
      <div>
        <p className='font-semibold'>
          {firstName} {lastName}
        </p>
        <p>
          {city} ,&nbsp;{country},&nbsp;{postalCode}
        </p>
        <p>
          {state}, &nbsp; {adress}
        </p>
      </div>
      <div>
        <p className='font-semibold'>Phone Number</p>
        <p>{mobileNumber}</p>
      </div>
      {!isFinalStep && (
        <Button
          size='small'
          variant='contained'
          onClick={() => {
            setActiveStep();
            console.log(id, 'id log');
            const orderRequest = {
              id,
              adres,
              lastName,
              firstName,
              mobileNumber,
            };
            setOrderRequest(orderRequest);
          }}
        >
          Deliver Here
        </Button>
      )}
    </div>
  );
};

export default AdressCard;
