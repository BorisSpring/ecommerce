import {
  Box,
  Button,
  ButtonGroup,
  Card,
  CircularProgress,
  FormControl,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Pagination,
  Select,
  Table,
  TableBody,
  TableHead,
  TableRow,
  Typography,
  styled,
} from "@mui/material";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import React, { useRef, useState } from "react";
import { useGetAllReviews } from "./useGetAllReviews";
import { formatDate } from "../../helpers/utils";
import { useLocation, useNavigate } from "react-router-dom";
import { useQueryClient } from "@tanstack/react-query";
import { getAllReviews } from "../../api/actions";
import { useDeleteReview } from "./useDeleteReview";

const sort = ["Sort By Date ASC", "Sort By Date DESC"];

const ITEM_HEIGHT = 28;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

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

const ReviewOverview = () => {
  const { allReviews, isLoading } = useGetAllReviews();
  const [sortBy, setsortBy] = useState([]);
  const { deleteReview, isDeleting } = useDeleteReview(
    allReviews?.totalPages,
    allReviews?.number,
    allReviews?.numberOfElements
  );
  const queryClient = useQueryClient();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const navigate = useNavigate();
  const ref = useRef(null);

  function handlePageChange(e, pageNumber) {
    params.set("page", pageNumber);
    navigate(`?${decodeURIComponent(params.toString())}`);
    ref.current.scrollIntoView({ behavior: "auto" });
  }

  function handleSortChange(value) {
    value === "Sort By Date ASC"
      ? params.set("sortByDate", "ASC")
      : params.set("sortByDate", "DESC");
    setsortBy(() => [value]);
    navigate(`?${decodeURIComponent(params.toString())}`);
  }

  if (allReviews?.totalPages > params.get("page")) {
    params.set("page", allReviews?.number + 2);
    queryClient.prefetchQuery({
      queryKey: ["allReviews", params.toString()],
      queryFn: async () => await getAllReviews(params),
    });
  }

  if (isLoading)
    return (
      <Box
        display={"flex"}
        justifyContent={"center"}
        alignItems={"center"}
        sx={{
          height: "50vh",
          width: "100%",
        }}
      >
        <CircularProgress size="2.5rem" color="primary" />
      </Box>
    );
  return (
    <>
      <Card className="mx-5 lg:mx-10 my-5 lg:my-10" ref={ref}>
        <Box
          display={"flex"}
          alignItems={"center"}
          justifyContent={"space-between"}
        >
          <Typography variant="h6" component="h2" className="">
            All Reviews
          </Typography>
          <FormControl sx={{ m: 1, width: 200 }} size="small">
            <InputLabel id="sort">Sort By</InputLabel>
            <Select
              labelId="sort"
              id="demo-multiple-name"
              multiple
              value={sortBy}
              input={<OutlinedInput label="Sort By" />}
              MenuProps={MenuProps}
            >
              {sort.map((sort) => (
                <MenuItem
                  key={sort}
                  value={sort}
                  onClick={(e) => handleSortChange(sort)}
                >
                  {sort}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Id</StyledTableCell>
              <StyledTableCell align="left">Review</StyledTableCell>
              <StyledTableCell align="left">Product Title</StyledTableCell>
              <StyledTableCell align="right">User Email</StyledTableCell>
              <StyledTableCell align="right">Created At</StyledTableCell>
              <StyledTableCell align="right">Actions</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allReviews?.content?.map?.(
              ({ id, reviewContent, userEmail, createdAt, productTitle }) => (
                <StyledTableRow key={id}>
                  <StyledTableCell component="th" scope="row">
                    {id}
                  </StyledTableCell>
                  <StyledTableCell align="left">
                    {reviewContent}
                  </StyledTableCell>
                  <StyledTableCell align="left">{productTitle}</StyledTableCell>
                  <StyledTableCell align="right">{userEmail}</StyledTableCell>
                  <StyledTableCell align="right">
                    {formatDate(createdAt)}
                  </StyledTableCell>
                  <StyledTableCell align="right">
                    <ButtonGroup size="small" variant="outlined">
                      <Button
                        onClick={() => deleteReview(id)}
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
      {allReviews?.totalPages > 1 && (
        <Pagination
          className="mx-auto w-fit  mb-10"
          count={allReviews?.totalPages}
          page={allReviews?.number + 1}
          onChange={handlePageChange}
          color="primary"
          size="small"
        />
      )}
    </>
  );
};

export default ReviewOverview;
