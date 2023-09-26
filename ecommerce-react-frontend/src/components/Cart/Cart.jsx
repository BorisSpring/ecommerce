import React from 'react';
import CartItem from './CartItem';
import { Button, Divider, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useGetLoggedUser } from '../Auth/useGetLoggedUser';
import { formatCurrency } from '../../helpers/utils';

const Cart = () => {
  const navigate = useNavigate();

  const {
    loggedUser: {
      cart: { cartItems, totalPrice, totalDiscountPrice, discount },
      id,
    },
  } = useGetLoggedUser() || {};

  if (!!cartItems?.length < 1) {
    return (
      <Typography variant='h4' align='center' sx={{ margin: '50px 0' }}>
        Your cart is empty.
      </Typography>
    );
  }

  return (
    <div className='lg:grid grid-cols-3 relative mt-[25px] md:mt-[50px]'>
      <div className='col-span-2 space-y-2 md:space-y-5'>
        {cartItems
          ?.sort?.((a, b) => a.id - b.id)
          .map((cartItem) => (
            <CartItem cartItem={cartItem} userId={id} key={cartItem?.id} />
          ))}
      </div>
      <div className='px-5 sticky top-0 h-[100vh]  mt-5 lg:mt-0'>
        <div className='border mt-2 px-3 py-2 rounded-md shadow-md'>
          <p className='uppercase font-bold opacity-60 pb-4'>Price Details</p>
          <Divider />
          <div className='space-y-3 font-semibold mb-2'>
            <div className='flex justify-between pt-3 text-black'>
              <span>Price</span>
              <span className='font-sembiold'>
                {formatCurrency(totalPrice)}
              </span>
            </div>
            <div className='flex justify-between text-black pt-3'>
              <span>Discount </span>
              <span className='font-semibold text-green-700'>
                {formatCurrency(discount)}
              </span>
            </div>
            <div className='flex justify-between text-black pt-3'>
              <span>Delivery Charges</span>
              <span className='font-semibold text-green-700'>
                {totalDiscountPrice > 99 ? 'Free' : formatCurrency(20)}
              </span>
            </div>
          </div>
          <Divider />
          <p className='flex justify-between mt-5'>
            <span className='text-black font-semibold text-xl'>
              Total Amount
            </span>
            <span className='text-green-700 font-semibold'>
              {formatCurrency(totalDiscountPrice)}
            </span>
          </p>
          <Button
            variant='contained'
            color='primary'
            fullWidth
            size='large'
            sx={{ marginTop: '15px', marginBottom: '10px' }}
            onClick={() => navigate('/checkout')}
          >
            Check Out
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Cart;
