import {
  AccountCircle,
  DateRange,
  Email,
  Visibility,
  VisibilityOff,
} from "@mui/icons-material";
import {
  Box,
  Button,
  Grid,
  IconButton,
  InputAdornment,
  Modal,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { useRegisterUser } from "./useRegisterUser";
import { red } from "@mui/material/colors";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  bgcolor: "background.paper",
  border: "4px solid rgb(79 70 229)",
  boxShadow: 24,
  padding: "10px 20px",
  p: 4,
  width: {
    xs: "90%",
    sm: "400px",
    md: "900px",
  },
};

const Register = ({ open, handleClose }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [showRepeatedPassword, setShowRepeatedPassword] = useState(false);
  const [formErrors, setformErrors] = useState({
    password: "",
  });
  const [message, setMessage] = useState("");
  const { registerUser, isRegistering } = useRegisterUser(
    handleClose,
    setMessage
  );
  const handleShowRepeatedPassword = () =>
    setShowRepeatedPassword((prev) => !prev);
  const handleShowPassowrd = () => setShowPassword((prev) => !prev);

  function handleSubmit(e) {
    e.preventDefault();
    setformErrors({ password: "" });
    const data = new FormData(e.currentTarget);

    const userData = {
      firstName: data.get("firstName"),
      lastName: data.get("lastName"),
      birth: data.get("dateOfBirth"),
      email: data.get("email"),
      password: data.get("password"),
    };
    const repeatedPwd = data.get("repeatPassword");

    if (userData.password !== repeatedPwd) {
      setformErrors({ ...formErrors, password: "Password must match" });
    } else {
      registerUser(userData);
    }
  }
  return (
    <>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style} autoComplete="nope">
          <Box
            component="form"
            onSubmit={(e) => handleSubmit(e)}
            sx={{
              width: { xs: "90%", md: "70%", lg: "60%" },
              marginTop: { xs: "20px", md: "30px", lg: "50px" },
              mx: "auto",
            }}
          >
            <Typography
              sx={{
                my: { xs: 1, sm: 2, md: 3 },
                fontSize: {
                  xs: "14px",
                  sm: "17px",
                  md: "20px",
                  fontWeight: "500",
                },
              }}
              textAlign="center"
              color="primary"
            >
              Sign Up Form
            </Typography>
            {message && (
              <Typography
                textAlign={"center"}
                sx={{
                  bgcolor: red[50],
                  borderRadius: "10px",
                  padding: "10px 5px",
                  marginBottom: { xs: "10px", md: "20px", lg: "25px" },
                }}
                color="error"
              >
                {message}
              </Typography>
            )}
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <TextField
                  id="firstName"
                  name="firstName"
                  required
                  fullWidth
                  autoComplete="given-name"
                  label="First Name"
                  placeholder="First Name"
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <AccountCircle />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  id="lastName"
                  name="lastName"
                  placeholder="Last Name"
                  required
                  fullWidth
                  label="Last Name"
                  autoComplete="given-name"
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <AccountCircle />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  id="dateOfBirth"
                  name="dateOfBirth"
                  type="date"
                  required
                  autoComplete="given-name"
                  fullWidth
                  placeholder="Date of birth"
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <DateRange />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  id="email"
                  name="email"
                  label="Email"
                  required
                  type="email"
                  placeholder="Email adress"
                  fullWidth
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Email />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  id="password"
                  name="password"
                  label="Password"
                  error={formErrors?.password?.length > 0}
                  helperText={formErrors?.password}
                  placeholder="Password"
                  type={showPassword ? "text" : "password"}
                  fullWidth
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <IconButton onClick={handleShowPassowrd}>
                          {showPassword ? <Visibility /> : <VisibilityOff />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  id="repeatPassword"
                  name="repeatPassword"
                  type={showRepeatedPassword ? "text" : "password"}
                  fullWidth
                  label="Repeat Password"
                  placeholder="Repeat Password"
                  error={formErrors?.password?.length > 0}
                  helperText={formErrors?.password}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <IconButton onClick={handleShowRepeatedPassword}>
                          {showRepeatedPassword ? (
                            <Visibility />
                          ) : (
                            <VisibilityOff />
                          )}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <Button
                  variant="contained"
                  fullWidth
                  size="large"
                  type="submit"
                  disabled={isRegistering}
                >
                  Register
                </Button>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Modal>
    </>
  );
};

export default Register;
