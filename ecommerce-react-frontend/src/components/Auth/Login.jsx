import { AccountCircle, Visibility, VisibilityOff } from "@mui/icons-material";
import {
  Box,
  Button,
  FormControl,
  IconButton,
  InputAdornment,
  InputLabel,
  Modal,
  OutlinedInput,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { useLoginUser } from "./useLoginUser";
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
    md: "400px",
  },
};

const Login = ({ handleClose, open }) => {
  const [showPassword, setShowPassword] = React.useState(false);
  const [message, setMessage] = useState("");
  const { loginUser, isLogging } = useLoginUser(setMessage, handleClose);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  function handleSubmit(e) {
    e.preventDefault();

    const data = new FormData(e.currentTarget);

    const userCredentials = {
      email: data.get("email"),
      password: data.get("password"),
    };
    loginUser(userCredentials);
  }

  return (
    <>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box
          sx={style}
          component="form"
          autoComplete="nope"
          onSubmit={(e) => handleSubmit(e)}
        >
          <Typography
            id="modal-modal-title"
            className="text-indigo-600 text-center"
            variant="h6"
            component="h2"
            sx={{ fontSize: { xs: "12px", md: "15px", lg: "18px" } }}
          >
            Welcome to shop, Please Login
          </Typography>
          {message && (
            <Typography
              variant="body1"
              color="error"
              textAlign="center"
              sx={{
                fontSize: { xs: "12px", md: "14px", lg: "18px" },
                padding: "10px 5px",
                mt: 1,
                bgcolor: red[50],
                borderRadius: "10px",
              }}
            >
              {message}
            </Typography>
          )}
          <TextField
            required
            label="Email"
            name="email"
            fullWidth
            sx={{ marginTop: { xs: "10px", md: "15px", lg: "20px" } }}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <AccountCircle />
                </InputAdornment>
              ),
            }}
          />
          <FormControl
            fullWidth
            variant="outlined"
            sx={{ marginTop: { xs: "10px", md: "15px", lg: "20px" } }}
          >
            <InputLabel htmlFor="outlined-adornment-password">
              Password
            </InputLabel>
            <OutlinedInput
              type={showPassword ? "text" : "password"}
              startAdornment={
                <InputAdornment position="start">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={handleClickShowPassword}
                    onMouseDown={handleMouseDownPassword}
                    edge="end"
                  >
                    {showPassword ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              }
              label="Password"
              name="password"
            />
          </FormControl>
          <Button
            type="submit"
            size="medium"
            variant="contained"
            sx={{ marginTop: { xs: "10px", md: "20px" } }}
            fullWidth
            disabled={isLogging}
          >
            Login
          </Button>
        </Box>
      </Modal>
    </>
  );
};

export default Login;
