import * as React from "react";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import {
  Button,
  ButtonGroup,
  Card,
  CardHeader,
  CircularProgress,
  Pagination,
} from "@mui/material";
import { useGetAllRatings } from "./useGetAllRatings";
import { useDeleteRating } from "./useDeleteRating";
import { useQueryClient } from "@tanstack/react-query";
import { getAllRatings } from "../../api/actions";
import { useNavigate } from "react-router-dom";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

export default function RatingOverview() {
  const { allRatings, isLoading } = useGetAllRatings();
  const { deleteRating, isDeleting } = useDeleteRating(allRatings);

  const queryClient = useQueryClient();
  const navigate = useNavigate();

  function handlePageChange(e, newValue) {
    navigate(`?page=${newValue}`);
  }

  if (
    allRatings?.totalPages > 1 &&
    allRatings.number + 1 !== allRatings.totalPages
  ) {
    queryClient.prefetchQuery({
      queryKey: ["allRatings", Number(allRatings?.number + 2)],
      queryFn: async () => await getAllRatings(),
    });
  }

  if (isLoading)
    return (
      <CircularProgress size="2.5rem" color="primary" sx={{ m: "auto" }} />
    );

  return (
    <>
      <Card className="mx-5 lg:mx-10 my-5 lg:my-10">
        <CardHeader title="All Ratings" />
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Id</StyledTableCell>
              <StyledTableCell align="right">Rating</StyledTableCell>
              <StyledTableCell align="right">User Email</StyledTableCell>
              <StyledTableCell align="right">Product Title</StyledTableCell>
              <StyledTableCell align="right">Actions</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allRatings?.content?.map?.(
              ({ id, productTitle, userEmail, rating }) => (
                <StyledTableRow key={id}>
                  <StyledTableCell component="th" scope="row">
                    {id}
                  </StyledTableCell>
                  <StyledTableCell align="right">{rating}</StyledTableCell>
                  <StyledTableCell align="right">
                    {productTitle}
                  </StyledTableCell>
                  <StyledTableCell align="right">{userEmail}</StyledTableCell>
                  <StyledTableCell align="right">
                    <ButtonGroup size="small" variant="outlined">
                      <Button
                        onClick={() => deleteRating(id)}
                        disabled={isDeleting}
                      >
                        Delete
                      </Button>
                    </ButtonGroup>
                  </StyledTableCell>
                </StyledTableRow>
              )
            )}
          </TableBody>
        </Table>
      </Card>
      {allRatings?.totalPages > 1 && (
        <Pagination
          className="mb-5 w-fit mx-auto"
          count={allRatings.totalPages}
          size="small"
          color="primary"
          onChange={handlePageChange}
        />
      )}
    </>
  );
}
