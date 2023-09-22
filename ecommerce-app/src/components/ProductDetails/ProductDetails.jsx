import React, { useEffect } from "react";
import { useState } from "react";
import { RadioGroup } from "@headlessui/react";
import {
  Box,
  Button,
  CircularProgress,
  Fade,
  Grid,
  LinearProgress,
  Modal,
  Rating,
  TextField,
  Typography,
} from "@mui/material";
import ProductReview from "./ProductReview";
import HomeSectionCard from "../HomeSection/HomeSectionCard";
import { useParams } from "react-router-dom";
import { useGetProductById } from "./useGetProductById";
import { useAddCartItem } from "../Cart/useAddCartItem";
import { useGetLoggedUser } from "../Auth/useGetLoggedUser";
import { useAddProductRating } from "./useAddProductRating";
import { formatCurrency } from "../../helpers/utils";
import { useGetSimilar } from "./useGetSimilar";
import { useAddProductReview } from "./useAddProductReview";
import { red } from "@mui/material/colors";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "white",
  boxShadow: 24,
  p: 4,
};

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

function ratingStatictis(ratings, greaterThen, lowerThen) {
  let percent = 0;

  const filteredRatings = ratings
    ?.map((r) => r.rating)
    .filter(
      (singleRating) => singleRating >= greaterThen && singleRating <= lowerThen
    );

  percent = ((filteredRatings?.length / ratings?.length) * 100).toFixed(2);

  return Number(percent);
}

export default function ProductDetails() {
  const [selectedColor, setSelectedColor] = useState("");
  const [selectedSize, setSelectedSize] = useState("");
  const [errorMessage, setErrorMessage] = useState();
  const [review, setReview] = useState("");
  const [open, setOpen] = useState(false);
  const [image, setImage] = useState("");
  const [averageRating, setAverageRating] = useState(0);

  const { productId } = useParams();
  const { loggedUser, isLoading } = useGetLoggedUser();
  const { productById } = useGetProductById(productId);
  const { addItemToCart, isAddingCartItem } = useAddCartItem();
  const { addRating, isPlacingRating } = useAddProductRating(productId);
  const { addReview, isAdding } = useAddProductReview(productId);
  const { similar, isGettingSimilar } = useGetSimilar(
    productById?.product?.sectionItem?.itemName
  );

  function handleClose() {
    setOpen((prev) => !prev);
  }

  useEffect(() => {
    if (productById?.ratings?.length > 0) {
      setAverageRating(
        () =>
          productById.ratings
            .map((rating) => rating.rating)
            .reduce((acc, cur) => acc + cur, 0) / productById.ratings.length
      );
    }
  }, [productById]);

  if (isLoading || !productById || isGettingSimilar)
    return (
      <Box
        display={"flex"}
        justifyContent={"center"}
        alignItems={"center"}
        position={"absolute"}
        width={"100%"}
        height={"80%"}
      >
        <CircularProgress size="2.5rem" />
      </Box>
    );

  const {
    ratings,
    categoryName,
    sectionName,
    product: {
      sectionItem: { itemName },
      imageUrl,
      name,
      title,
      discountPrice,
      price,
      discountPrecent,
      id,
      numOfRating,
      numOfRewies,
      sizes,
      colors,
      description,
      highlights,
      details,
    },

    reviews,
    userNames,
  } = productById || {};

  const excellent = ratingStatictis(ratings, 4, 5);
  const good = ratingStatictis(ratings, 3, 4);
  const average = ratingStatictis(ratings, 2, 3);
  const bad = ratingStatictis(ratings, 1, 2);
  const poor = ratingStatictis(ratings, 0, 1);

  return (
    <>
      <div className="bg-white">
        <div className="pt-6">
          <nav aria-label="Breadcrumb">
            <ol className="mx-auto flex max-w-2xl items-center space-x-2 px-4 sm:px-6 lg:max-w-7xl lg:px-8">
              <li key={categoryName}>
                <div className="flex items-center">
                  <a
                    href="nema jos trenutno"
                    className="mr-2 text-sm font-medium text-gray-900"
                  >
                    {categoryName}
                  </a>
                  <svg
                    width={16}
                    height={20}
                    viewBox="0 0 16 20"
                    fill="currentColor"
                    aria-hidden="true"
                    className="h-5 w-4 text-gray-300"
                  >
                    <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                  </svg>
                </div>
              </li>
              <li key={sectionName}>
                <div className="flex items-center">
                  <a
                    href="nema jos trenutno"
                    className="mr-2 text-sm font-medium text-gray-900"
                  >
                    {sectionName}
                  </a>
                  <svg
                    width={16}
                    height={20}
                    viewBox="0 0 16 20"
                    fill="currentColor"
                    aria-hidden="true"
                    className="h-5 w-4 text-gray-300"
                  >
                    <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                  </svg>
                </div>
              </li>
              <li>
                <div className="flex items-center">
                  <a
                    href="nema jos trenutno"
                    className="mr-2 text-sm font-medium text-gray-900"
                  >
                    {itemName}
                  </a>
                  <svg
                    width={16}
                    height={20}
                    viewBox="0 0 16 20"
                    fill="currentColor"
                    aria-hidden="true"
                    className="h-5 w-4 text-gray-300"
                  >
                    <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                  </svg>
                </div>
              </li>
            </ol>
          </nav>
          <section className="grid grid-cols-1 lg:grid-cols-2 gap-x-8 gap-y-10">
            {/* Image gallery */}
            <div className="flex flex-col items-center">
              <div className="overflow-hidden rounded-lg max-w-[30rem] lg:h-[35rem]  w-full h-[20rem]">
                <img
                  src={image || imageUrl?.[0]}
                  alt="Ecommerce shop item"
                  className="h-full w-full object-cover object-center my-5 aspect-square"
                />
              </div>
              <div className="flex flex-wrap space-x-5 justify-between mt-10">
                {imageUrl?.map?.((imageUrl) => (
                  <div
                    className="aspect-h-2 aspect-w-3 overflow-hidden rounded-lg max-w-[4.5rem] max-h-[5rem] lg:max-w-[5rem]"
                    onClick={() => setImage(() => imageUrl)}
                    key={imageUrl}
                  >
                    <img
                      src={imageUrl}
                      alt="Ecommerce shop item"
                      className="h-full w-full object-cover object-center"
                    />
                  </div>
                ))}
              </div>
            </div>

            {/* Product info */}
            <div className="lg-col-span-1 maxt-auto max-w-2xl px-4 pb-16 sm:px-6 lg:max-w-7xl lg:px-8 lg:pb-24 lg:mr-[100px] xl:mr-[200px]">
              <div className="lg:col-span-2 ">
                <h1 className="text-xl font-semibold text-gray-900 ">{name}</h1>
                <h1 className="text-xl text-gra-900 opacity-80">{title}</h1>
              </div>

              {/* Options */}
              <div className="mt-4 lg:row-span-3 lg:mt-0">
                <h2 className="sr-only">Product information</h2>
                <div className="flex space-x-5 items-center text-lg lg:text-xl mt-6">
                  {discountPrecent > 0 ? (
                    <>
                      <p className="font-sembiold line-through opacity-50">
                        {formatCurrency(price)}
                      </p>
                      <p className="font-sembiold">
                        {formatCurrency(discountPrice)}
                      </p>
                      <p className="text-green-600 font-semibold">
                        {discountPrecent}% Off
                      </p>
                    </>
                  ) : (
                    <p className="font-medium">{formatCurrency(price)}</p>
                  )}
                </div>
                {/* Reviews */}
                <div className="mt-6">
                  {<h3 className="sr-only">Reviews</h3>}
                  <div className="flex items-center">
                    <div className="flex items-center gap-2">
                      <Rating
                        value={averageRating || 0}
                        disabled={isPlacingRating}
                        precision={0.1}
                        onChange={(event, newValue) => {
                          if (loggedUser) {
                            addRating({
                              userId: loggedUser?.id,
                              productId: productId,
                              rating: newValue,
                            });
                          }
                        }}
                      />
                      <span className=" font-semibold text-indigo-600">
                        {numOfRating} Ratings{" "}
                      </span>

                      <p className="hover:text-indigo-500 text-sm font-medium text-indigo-600"></p>
                      <a
                        href={reviews.href}
                        className="ml-3 text-base font-medium text-indigo-600 hover:text-indigo-500 "
                      >
                        {loggedUser?.id > 0 ? (
                          <Button
                            onClick={() => setOpen(() => true)}
                            size="small"
                            variant="outlined"
                          >
                            {numOfRewies > 0
                              ? `Add Review (${numOfRewies})`
                              : "Be The Frist Who Review"}
                          </Button>
                        ) : (
                          "Login To Post Review"
                        )}
                      </a>
                    </div>
                  </div>
                </div>

                <form className="mt-10">
                  {/* Colors */}
                  <div>
                    <h3 className="text-sm font-medium text-gray-900">Color</h3>

                    <RadioGroup
                      value={selectedColor}
                      onChange={setSelectedColor}
                      className="mt-4"
                    >
                      <RadioGroup.Label className="sr-only">
                        Choose a color
                      </RadioGroup.Label>
                      <div className="flex items-center space-x-3">
                        {colors?.map((color) => (
                          <RadioGroup.Option
                            key={color}
                            value={color}
                            className={({ active, checked }) =>
                              classNames(
                                color.selectedClass,
                                active && checked ? "ring ring-offset-1" : "",
                                !active && checked ? "ring-2" : "",
                                "relative -m-0.5 flex cursor-pointer items-center justify-center rounded-full p-0.5 focus:outline-none"
                              )
                            }
                          >
                            <RadioGroup.Label as="span" className="sr-only">
                              {color}
                            </RadioGroup.Label>
                            <span
                              aria-hidden="true"
                              className={classNames(
                                `ring-${color}-500 bg-${color}-500`,
                                "h-8 w-8 rounded-full border border-black border-opacity-10"
                              )}
                            />
                          </RadioGroup.Option>
                        ))}
                      </div>
                    </RadioGroup>
                  </div>

                  {/* Sizes */}
                  <div className="mt-10">
                    <div className="flex items-center justify-between">
                      <h3 className="text-sm font-medium text-gray-900">
                        Size
                      </h3>
                      <button className="text-sm font-medium text-indigo-600 hover:text-indigo-500">
                        Size guide
                      </button>
                    </div>

                    <RadioGroup
                      value={selectedSize}
                      onChange={setSelectedSize}
                      className="mt-4"
                    >
                      <RadioGroup.Label className="sr-only">
                        Choose a size
                      </RadioGroup.Label>
                      <div className="grid grid-cols-4 gap-4 sm:grid-cols-8 lg:grid-cols-4">
                        {sizes.map(({ size, quantity }) => (
                          <RadioGroup.Option
                            key={size}
                            value={size}
                            disabled={quantity < 1}
                            className={({ active }) =>
                              classNames(
                                quantity > 0
                                  ? "cursor-pointer bg-white text-gray-900 shadow-sm"
                                  : "cursor-not-allowed bg-gray-50 text-gray-200",
                                active ? "ring-2 ring-indigo-500" : "",
                                "group relative flex items-center justify-center rounded-md border py-3 px-4 text-sm font-medium uppercase hover:bg-gray-50 focus:outline-none sm:flex-1 sm:py-6"
                              )
                            }
                          >
                            {({ active, checked }) => (
                              <>
                                <RadioGroup.Label as="span">
                                  {size}
                                </RadioGroup.Label>
                                {quantity > 0 ? (
                                  <span
                                    className={classNames(
                                      active ? "border" : "border-2",
                                      checked
                                        ? "border-indigo-500"
                                        : "border-transparent",
                                      "pointer-events-none absolute -inset-px rounded-md"
                                    )}
                                    aria-hidden="true"
                                  />
                                ) : (
                                  <span
                                    aria-hidden="true"
                                    className="pointer-events-none absolute -inset-px rounded-md border-2 border-gray-200"
                                  >
                                    <svg
                                      className="absolute inset-0 h-full w-full stroke-2 text-gray-200"
                                      viewBox="0 0 100 100"
                                      preserveAspectRatio="none"
                                      stroke="currentColor"
                                    >
                                      <line
                                        x1={0}
                                        y1={100}
                                        x2={100}
                                        y2={0}
                                        vectorEffect="non-scaling-stroke"
                                      />
                                    </svg>
                                  </span>
                                )}
                              </>
                            )}
                          </RadioGroup.Option>
                        ))}
                      </div>
                    </RadioGroup>
                  </div>

                  <Button
                    variant="contained"
                    fullWidth
                    size="large"
                    color="primary"
                    type="submit"
                    disabled={isAddingCartItem}
                    sx={{ my: 2 }}
                    onClick={(e) => {
                      e.preventDefault();
                      const request = {
                        userId: loggedUser.id,
                        productId: productId,
                        size: selectedSize,
                        quantity: 1,
                        color: selectedColor,
                      };
                      addItemToCart(request);
                    }}
                  >
                    Add to cart
                  </Button>
                </form>
              </div>

              <div className="py-10 text-ini lg:col-span-2 lg:col-start-1 lg:border-r lg:border-gray-200 lg:pb-16 lg:pr-8 lg:pt-6">
                {/* Description and details */}
                <div>
                  <h3 className="sr-only">Description</h3>

                  <div className="space-y-6">
                    <p className="text-base text-gray-900">{description}</p>
                  </div>
                </div>

                <div className="mt-10">
                  <h3 className="text-sm font-medium text-gray-900">
                    Highlights
                  </h3>

                  <div className="mt-4">
                    <ul className="list-disc space-y-2 pl-4 text-sm">
                      {highlights?.map((highlight) => (
                        <li key={highlight} className="text-gray-400">
                          <span className="text-gray-600">{highlight}</span>
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>

                <div className="mt-10">
                  <h2 className="text-sm font-medium text-gray-900">Details</h2>

                  <div className="mt-4 space-y-6">
                    <p className="text-sm text-gray-600">
                      {details} dodati i details posle{" "}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <section className="px-6 lg:px-8">
            <h1 className="font-semibold text-lg pb-4">
              Recent Reviews & Rating
            </h1>
            <div className="border p-2 lg:py-5">
              <Grid container>
                <Grid
                  item
                  xs={12}
                  md={7}
                  sx={{
                    height: { xs: "250px", md: "350px", xl: "400px" },
                    overflow: "scroll",
                  }}
                  className="divide-y space-y-1 divide-stone-300"
                >
                  {reviews?.length === 0 && (
                    <Typography textAlign={"center"} fontSize={"20px"}>
                      Product Doesnt Have Any Review!
                    </Typography>
                  )}
                  {reviews
                    ?.sort((a, b) => a.id - b.id)
                    .map((review, index) => (
                      <ProductReview
                        key={review?.id}
                        review={review}
                        userName={userNames?.[index]}
                      />
                    ))}
                </Grid>
                {numOfRating > 0 ? (
                  <Grid
                    item
                    xs={12}
                    md={5}
                    sx={{ paddingLeft: { md: "30px" } }}
                  >
                    <h1 className="text-xl font-semibold pb-1">
                      Product Ratings
                    </h1>
                    <div className="flex items-center gap-1">
                      <Rating readOnly value={averageRating} precision={0.1} />
                      <p className="opacity-60 text-base">
                        {numOfRating} ratings (Average{" "}
                        {averageRating.toFixed(2)})
                      </p>
                    </div>
                    <Box p={1}>
                      <Grid
                        item
                        container
                        sx={{
                          display: "flex",
                          alignItems: "center",
                        }}
                      >
                        <Grid item xs={2}>
                          <Typography>Excellent</Typography>
                        </Grid>
                        <Grid item xs={9} ml={"auto"}>
                          <LinearProgress
                            color="success"
                            variant="determinate"
                            sx={{
                              height: "10px",
                              borderRadius: "10px",
                              bgcolor: "white",
                            }}
                            value={excellent || 0}
                          />
                        </Grid>
                      </Grid>
                    </Box>
                    <Box p={1}>
                      <Grid
                        item
                        container
                        sx={{
                          display: "flex",
                          alignItems: "center",
                        }}
                      >
                        <Grid item xs={2}>
                          <Typography>Good</Typography>
                        </Grid>
                        <Grid item xs={9} ml={"auto"}>
                          <LinearProgress
                            color="info"
                            variant="determinate"
                            sx={{
                              height: "10px",
                              borderRadius: "10px",
                              bgcolor: "white",
                            }}
                            value={good || 0}
                          />
                        </Grid>
                      </Grid>
                    </Box>
                    <Box p={1}>
                      <Grid
                        item
                        container
                        sx={{
                          display: "flex",
                          alignItems: "center",
                        }}
                      >
                        <Grid item xs={2}>
                          <Typography>Average</Typography>
                        </Grid>
                        <Grid item xs={9} ml={"auto"}>
                          <LinearProgress
                            color="primary"
                            variant="determinate"
                            sx={{
                              height: "10px",
                              borderRadius: "10px",
                              bgcolor: "white",
                            }}
                            value={average || 0}
                          />
                        </Grid>
                      </Grid>
                    </Box>
                    <Box p={1}>
                      <Grid
                        item
                        container
                        sx={{
                          display: "flex",
                          alignItems: "center",
                        }}
                      >
                        <Grid item xs={2}>
                          <Typography>Bad</Typography>
                        </Grid>
                        <Grid item xs={9} ml={"auto"}>
                          <LinearProgress
                            color="warning"
                            variant="determinate"
                            sx={{
                              height: "10px",
                              borderRadius: "10px",
                              bgcolor: "white",
                            }}
                            value={bad || 0}
                          />
                        </Grid>
                      </Grid>
                    </Box>
                    <Box p={1}>
                      <Grid
                        item
                        container
                        sx={{
                          display: "flex",
                          alignItems: "center",
                        }}
                      >
                        <Grid item xs={2}>
                          <Typography>Poor</Typography>
                        </Grid>
                        <Grid item xs={9} ml={"auto"}>
                          <LinearProgress
                            color="error"
                            variant="determinate"
                            sx={{
                              height: "10px",
                              borderRadius: "10px",
                              bgcolor: "white",
                            }}
                            value={poor || 0}
                          />
                        </Grid>
                      </Grid>
                    </Box>
                  </Grid>
                ) : (
                  <Typography text-align="center" fontSize={"20px"}>
                    Product not rated yet
                  </Typography>
                )}
              </Grid>
            </div>
          </section>
          <div className="px-5 lg:px-10 my-10">
            <h1 className="my-5 font-semibold text-2xl opacity-90 text-gray-900">
              Similar Products
            </h1>
            <div className="flex flex-wrap gap-5 mx-auto space-y-5 ">
              {similar?.map((item, index) => (
                <HomeSectionCard item={item} key={index} />
              ))}
            </div>
          </div>
        </div>
      </div>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        open={open}
        keepMounted
        onClose={handleClose}
        closeAfterTransition
        slotProps={{
          backdrop: {
            timeout: 500,
          },
        }}
      >
        <Fade in={open}>
          <Box sx={style}>
            <Typography
              id="transition-modal-title"
              variant="h6"
              component="h2"
              align="center"
            >
              Review Form
            </Typography>
            {errorMessage && (
              <Typography
                id="transition-modal-title"
                variant="subtitle"
                component="h2"
                align="center"
                sx={{
                  bgcolor: red[50],
                  color: red[400],
                  borderRadius: "10px",
                  p: 1,
                  my: 1,
                }}
              >
                {errorMessage}
              </Typography>
            )}
            <TextField
              value={review}
              onChange={(e, value) => setReview(() => e.target.value)}
              name="review"
              id="review"
              label="review"
              multiline
              rows={3}
              required
              fullWidth
              sx={{ my: 2 }}
            />
            <Button
              disabled={isAdding}
              onClick={() => {
                setErrorMessage("");
                if (review.trim().length > 4) {
                  addReview({
                    userId: loggedUser.id,
                    productId: id,
                    content: review,
                  });
                  setReview("");
                  handleClose();
                } else {
                  setErrorMessage(
                    () => "Incorrect Input. Must be above 4 charachters."
                  );
                }
              }}
            >
              Add Review
            </Button>
          </Box>
        </Fade>
      </Modal>
    </>
  );
}
