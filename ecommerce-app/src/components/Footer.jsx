import { Grid, Stack, Typography } from "@mui/material";
import React from "react";

const Footer = () => {
  return (
    <div className="mt-auto">
      <Grid
        className="bg-black text-white text-center py-3 lg:py-5  my-auto"
        container
      >
        <Grid item xs={12} sm={6} md={3}>
          <Typography
            className="pb-5"
            sx={{ fontSize: { xs: "10px", md: "12px" } }}
            variant="h5"
          >
            Company
          </Typography>
          <Stack>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              About
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Blog
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Press
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Jobs
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Partners
            </Typography>
          </Stack>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Typography
            className="pb-5"
            sx={{ fontSize: { xs: "10px", md: "12px" } }}
            variant="h5"
          >
            Solutions
          </Typography>
          <Stack>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Marketing
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Analytics
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Commerce
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Insights
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Support
            </Typography>
          </Stack>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Typography
            className="pb-5"
            sx={{ fontSize: { xs: "10px", md: "12px" } }}
            variant="h5"
          >
            Documentation
          </Typography>
          <Stack>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Guides
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              API status
            </Typography>
          </Stack>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Typography
            className="pb-5"
            sx={{ fontSize: { xs: "10px", md: "12px" } }}
            variant="h5"
          >
            Legal
          </Typography>
          <Stack>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Claim
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Privacy
            </Typography>
            <Typography
              className="pb-2"
              sx={{ fontSize: { xs: "10px", md: "12px" } }}
              variant="subtitle2"
            >
              Terms
            </Typography>
          </Stack>
        </Grid>
        <Grid margin={"auto"} pt={1}>
          <Typography
            variant="subtitle2 "
            sx={{ fontSize: "8px" }}
            textAlign={"center"}
          >
            &copy; 2023 Boris Dimitrijevic . All rights reserved
          </Typography>
          <Typography
            variant="subtitle2"
            sx={{ fontSize: "8px" }}
            textAlign={"center"}
          >
            Made with love By Me :)
          </Typography>
        </Grid>
      </Grid>
    </div>
  );
};

export default Footer;
