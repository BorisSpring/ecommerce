import { Box, Button, Grid, TextField, useMediaQuery } from '@mui/material';
import React from 'react';
import AdressCard from './AdressCard';
import { useGetLoggedUser } from '../Auth/useGetLoggedUser';
import { useTheme } from '@emotion/react';

const DeliveryAdressForm = ({ setActiveStep, setOrderRequest }) => {
  const { loggedUser } = useGetLoggedUser();
  const theme = useTheme();
  const isLargeScreen = useMediaQuery(theme.breakpoints.up('lg`'));
  function handleSubmit(e) {
    e.preventDefault();
    const data = new FormData(e.currentTarget);

    const orderRequest = {
      firstName: data.get('firstName'),
      lastName: data.get('lastName'),
      adres: {
        adress: data.get('adress'),
        city: data.get('city'),
        country: data.get('country'),
        state: data.get('state'),
        postalCode: data.get('postalCode'),
      },
      mobileNumber: data.get('phoneNumber'),
    };
    setOrderRequest(orderRequest);
    setActiveStep();
  }

  return (
    <Grid container mt={1} sx={{ marginBottom: '30px' }}>
      <div className='ovdeje'></div>
      <Grid
        item
        className='border rounded-e-md shadow-md h-[32rem] overflow-y-scroll'
        xs={12}
        lg={4}
        sx={{ mr: { lg: '100px' }, mb: '50px' }}
      >
        <div className=' cursor-pointer divide-y-2 divide-stone-200'>
          {loggedUser?.orders?.map?.(
            ({ adress, firstName, lastName, mobileNumber: { number }, id }) => (
              <AdressCard
                adres={adress}
                firstName={firstName}
                lastName={lastName}
                mobileNumber={number}
                key={id}
                id={id}
                setActiveStep={setActiveStep}
                setOrderRequest={setOrderRequest}
              />
            )
          )}
        </div>
      </Grid>

      <Grid item xs={12} lg={7}>
        <Box className='border rounded-s-md shadow-md  p-5'>
          <form onSubmit={(e) => handleSubmit(e)}>
            <Grid item container>
              <Grid item xs={12} md={6}>
                <TextField
                  size={isLargeScreen ? 'small' : 'large'}
                  required
                  id='firstName'
                  name='firstName'
                  label='First Name'
                  sx={{ width: { xs: '100%', md: '95%' } }}
                />
              </Grid>
              <Grid
                item
                xs={12}
                md={6}
                sx={{
                  justifyContent: { md: 'flex-end' },
                  width: { xs: '100%', md: '95%' },
                  mt: { xs: '20px', md: 0 },
                }}
                display={'flex'}
              >
                <TextField
                  size={isLargeScreen ? 'small' : 'large'}
                  name='lastName'
                  required
                  id='lastName'
                  autoComplete='given-name'
                  sx={{
                    width: { xs: '100%', md: '95%' },
                  }}
                  label='Last Name'
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  size={isLargeScreen ? 'small' : 'large'}
                  autoComplete='given-name'
                  name='adress'
                  id='adress'
                  label='Adress'
                  fullWidth
                  sx={{ width: { xs: '100%', md: '95%' }, mt: '20px' }}
                />
              </Grid>
              <Grid item xs={12} md={6} display='flex' justifyContent={'end'}>
                <TextField
                  size={isLargeScreen ? 'small' : 'large'}
                  autoComplete='given-name'
                  name='country'
                  id='country'
                  label='Country'
                  fullWidth
                  sx={{
                    width: { xs: '100%', md: '95%' },
                    mt: '20px',
                  }}
                />
              </Grid>
              <Grid item container sx={{ my: '20px' }}>
                <Grid xs={12} md={6} item>
                  <TextField
                    size={isLargeScreen ? 'small' : 'large'}
                    autoComplete='given-name'
                    label='City'
                    name='city'
                    id='city'
                    sx={{
                      width: { xs: '100%', md: '95%' },
                      mb: { xs: '20px', md: 0 },
                    }}
                  />
                </Grid>
                <Grid
                  item
                  xs={12}
                  md={6}
                  display={'flex'}
                  sx={{ justifyContent: { md: 'flex-end' } }}
                >
                  <TextField
                    size={isLargeScreen ? 'small' : 'large'}
                    autoComplete='given-name'
                    id='state'
                    name='state'
                    label='State/Province/Region'
                    sx={{
                      width: { xs: '100%', md: '95%' },
                    }}
                  />
                </Grid>
              </Grid>
              <Grid item container>
                <Grid item xs={12} md={6}>
                  <TextField
                    size={isLargeScreen ? 'small' : 'large'}
                    sx={{
                      width: { xs: '100%', md: '95%' },
                      mb: { xs: '20px', md: 0 },
                    }}
                    name='postalCode'
                    id='postalCode'
                    label=' Postal Code '
                    autoComplete='given-name'
                  />
                </Grid>
                <Grid
                  item
                  xs={12}
                  md={6}
                  display='flex'
                  sx={{ justifyContent: 'flex-end' }}
                >
                  <TextField
                    size={isLargeScreen ? 'small' : 'large'}
                    name='phoneNumber'
                    id='phoneNumber'
                    label='Phone Number'
                    sx={{
                      width: { xs: '100%', md: '95%' },
                      mb: { xs: '20px', md: 0 },
                    }}
                  />
                </Grid>
              </Grid>
              <Button
                variant='contained'
                color='primary'
                size='large'
                fullWidth
                type='submit'
                sx={{ marginTop: '20px' }}
              >
                Deliver Here
              </Button>
            </Grid>
          </form>
        </Box>
      </Grid>
    </Grid>
  );
};

export default DeliveryAdressForm;
