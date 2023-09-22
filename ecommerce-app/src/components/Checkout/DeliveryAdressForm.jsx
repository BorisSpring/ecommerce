import { Box, Button, Grid, TextField } from "@mui/material";
import React from "react";
import AdressCard from "./AdressCard";
import { useGetLoggedUser } from "../Auth/useGetLoggedUser";

const DeliveryAdressForm = ({ setActiveStep, setOrderRequest }) => {
  const { loggedUser } = useGetLoggedUser();

  function handleSubmit(e) {
    e.preventDefault();
    const data = new FormData(e.currentTarget);

    const orderRequest = {
      firstName: data.get("firstName"),
      lastName: data.get("lastName"),
      adres: {
        adress: data.get("adress"),
        city: data.get("city"),
        country: data.get("country"),
        state: data.get("state"),
        postalCode: data.get("postalCode"),
      },
      mobileNumber: data.get("phoneNumber"),
    };
    setOrderRequest(orderRequest);
    setActiveStep();
  }

  return (
    <Grid container spacing={4} mt={1} sx={{ marginBottom: "30px" }}>
      <Grid
        item
        className="border rounded-e-md shadow-md h-[32rem] overflow-y-scroll"
        xs={12}
        lg={5}
      >
        <div className=" cursor-pointer">
          {loggedUser?.orders?.map?.(
            ({ adress, firstName, lastName, mobileNumber: { number }, id }) => (
              <AdressCard
                adres={adress}
                firstName={firstName}
                lastName={lastName}
                mobileNumber={number}
                key={id}
                setActiveStep={setActiveStep}
                setOrderRequest={setOrderRequest}
              />
            )
          )}
        </div>
      </Grid>

      <Grid item xs={12} lg={7}>
        <Box className="border rounded-s-md shadow-md  p-5">
          <form onSubmit={(e) => handleSubmit(e)}>
            <Grid item container>
              <Grid item xs={12} lg={6}>
                <TextField
                  required
                  id="firstName"
                  name="firstName"
                  label="First Name"
                  sx={{ width: "95%" }}
                />
              </Grid>
              <Grid
                item
                xs={12}
                lg={6}
                sx={{ justifyContent: { lg: "flex-end" } }}
                display={"flex"}
              >
                <TextField
                  name="lastName"
                  required
                  id="lastName"
                  autoComplete="given-name"
                  sx={{ width: "95%" }}
                  label="Last Name"
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  autoComplete="given-name"
                  name="adress"
                  id="adress"
                  label="Adress"
                  fullWidth
                  sx={{ width: "95%", mt: "20px" }}
                />
              </Grid>
              <Grid item xs={12} md={6} display="flex" justifyContent={"end"}>
                <TextField
                  autoComplete="given-name"
                  name="country"
                  id="country"
                  label="Country"
                  fullWidth
                  sx={{ width: "95%", mt: "20px" }}
                />
              </Grid>
              <Grid item container sx={{ my: "20px" }}>
                <Grid xs={12} lg={6} item>
                  <TextField
                    autoComplete="given-name"
                    label="City"
                    name="city"
                    id="city"
                    sx={{ width: "95%" }}
                  />
                </Grid>
                <Grid
                  item
                  xs={12}
                  lg={6}
                  display={"flex"}
                  sx={{ justifyContent: { lg: "flex-end" } }}
                >
                  <TextField
                    autoComplete="given-name"
                    id="state"
                    name="state"
                    label="State/Province/Region"
                    sx={{ width: "95%" }}
                  />
                </Grid>
              </Grid>
              <Grid item container>
                <Grid item xs={12} lg={6}>
                  <TextField
                    sx={{ width: "95%" }}
                    name="postalCode"
                    id="postalCode"
                    label=" Postal Code "
                    autoComplete="given-name"
                  />
                </Grid>
                <Grid
                  item
                  xs={12}
                  lg={6}
                  display="flex"
                  sx={{ justifyContent: { lg: "flex-end" } }}
                >
                  <TextField
                    name="phoneNumber"
                    id="phoneNumber"
                    label="Phone Number"
                    sx={{ width: "95%" }}
                  />
                </Grid>
              </Grid>
              <Button
                variant="contained"
                color="primary"
                size="large"
                fullWidth
                type="submit"
                sx={{ marginTop: "20px" }}
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
