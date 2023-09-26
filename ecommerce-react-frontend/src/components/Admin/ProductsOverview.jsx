import {
  Box,
  Button,
  ButtonGroup,
  Card,
  CardHeader,
  CircularProgress,
  Pagination,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useFindAllProducts } from "./useFindAllProducts";
import { useQueryClient } from "@tanstack/react-query";
import { getAllProducts } from "../../api/actions";
import { useDeleteProduct } from "./useDeleteProduct";

const ProductsOverview = () => {
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const { allProducts, isLoading } = useFindAllProducts(params);
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { deleteProduct } = useDeleteProduct(handlePageChange, allProducts);

  if (params.get("page") < allProducts?.totalPages) {
    params.set("page", Number(params.get("page")) + 1);
    queryClient.prefetchQuery({
      queryFn: async () => await getAllProducts(params),
      queryKey: ["allProducts", params.get("page")],
    });
  }

  function handlePageChange(event, page) {
    const params = new URLSearchParams();
    params.set("page", page);

    navigate(`?${decodeURIComponent(params.toString())}`);
  }

  if (isLoading)
    return (
      <Box
        display="flex"
        alignItems="center"
        justifyContent="center"
        sx={{ width: "100%", height: "100%" }}
      >
        <CircularProgress size="3rem" />
      </Box>
    );

  return (
    <Card sx={{ mx: "20px", my: "40px" }}>
      <CardHeader title="All Products" />
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Image</TableCell>
              <TableCell align="right">Title</TableCell>
              <TableCell align="right">Price</TableCell>
              <TableCell align="right">Discount Price</TableCell>
              <TableCell align="right">Discount Percent</TableCell>
              <TableCell align="right">Quantity</TableCell>
              <TableCell align="right">Num. Rating</TableCell>
              <TableCell align="right">Num. Rewies</TableCell>
              <TableCell align="right">Delete</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allProducts?.content?.map(
              ({
                id,
                imageUrl,
                price,
                discountPrice,
                discountPercent,
                quantity,
                numOfRating,
                numOfRewies,
                title,
              }) => (
                <TableRow
                  key={id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <img
                      src={imageUrl?.[0]}
                      alt={title}
                      className="w-10 h-10 rounded-full"
                    />
                  </TableCell>
                  <TableCell align="right">{title}</TableCell>
                  <TableCell align="right">{price}</TableCell>
                  <TableCell align="right">{discountPrice}</TableCell>
                  <TableCell align="right">
                    {discountPercent > 0 ? discountPercent : "Not discounted"}
                  </TableCell>
                  <TableCell align="right">
                    {quantity > 0 ? quantity : "Out of Stock"}
                  </TableCell>
                  <TableCell align="right">{numOfRating}</TableCell>
                  <TableCell align="right">{numOfRewies}</TableCell>
                  <TableCell align="right">
                    <ButtonGroup size="small" variant="outlined">
                      <Button onClick={() => navigate(`updateProduct/${id}`)}>
                        Update
                      </Button>
                      <Button onClick={() => deleteProduct(id)}>Delete</Button>
                    </ButtonGroup>
                  </TableCell>
                </TableRow>
              )
            )}
          </TableBody>
        </Table>
      </TableContainer>
      {allProducts?.totalPages > 1 && (
        <Pagination
          count={allProducts?.totalPages}
          onChange={handlePageChange}
          color="primary"
          className="w-fit mx-auto my-3"
        />
      )}
    </Card>
  );
};

export default ProductsOverview;
