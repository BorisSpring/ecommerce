import {
  BrandingWatermark,
  Category,
  Description,
  Euro,
  Title,
} from "@mui/icons-material";
import {
  Button,
  FormControl,
  Grid,
  InputAdornment,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
  Typography,
} from "@mui/material";
import ProductionQuantityLimitsIcon from "@mui/icons-material/ProductionQuantityLimits";
import React, { useState } from "react";
import { useTheme } from "@emotion/react";
import { useCreateProduct } from "../Product/useCreateProduct";
import { useGetProductById } from "../ProductDetails/useGetProductById";
import { useParams } from "react-router-dom";

const names = [
  "black",
  "orange",
  "white",
  "purple",
  "yellow",
  "red",
  "dark",
  "green",
];

const ITEM_HEIGHT = 38;
const ITEM_PADDING_TOP = 4;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function getStyles(name, colors, theme) {
  return {
    fontWeight:
      colors.indexOf(name) === -1
        ? theme.typography.fontWeightRegular
        : theme.typography.fontWeightMedium,
  };
}

const AddProductForm = () => {
  const theme = useTheme();
  const { id } = useParams();
  const { productById } = useGetProductById(id);
  const [colors, setColors] = useState(
    productById ? productById?.product?.colors : []
  );
  const [images, setImages] = useState(
    productById ? productById?.product?.imageUrl : []
  );
  const [highlight, setHighlight] = useState("");
  const [highlights, setHighlights] = useState(
    () => productById?.product?.highlights || []
  );
  const { createProduct } = useCreateProduct();

  async function handleFileChange(e) {
    const file = e.target.files[0];

    if (file) {
      const data = new FormData();
      data.append("file", file);
      data.append("upload_preset", "whatsapp");
      data.append("cloud_name", "dhz5j4vsc");
      fetch("https://api.cloudinary.com/v1_1/dhz5j4vsc/image/upload", {
        method: "POST",
        body: data,
      })
        .then((res) => res.json())
        .then((data) => {
          setImages((prev) => [...prev, data?.url]);
        });
    }
  }

  function handleChange(event) {
    const { value } = event.target;
    const lastItem = value[value.length - 1];

    setColors((prevColors) => {
      if (prevColors.includes(lastItem)) {
        return prevColors.filter((color) => color !== lastItem);
      } else {
        return [...prevColors, lastItem];
      }
    });
  }

  function handleSubmit(e) {
    e.preventDefault();

    const formData = new FormData(e.target);
    const sizes = new Set();
    sizes.add({
      size: "S",
      quantity:
        parseInt(formData.get("small")) ||
        Number(productById?.product?.sizes?.[0].quantity),
    });
    sizes.add({
      size: "M",
      quantity:
        parseInt(formData.get("medium")) ||
        Number(productById?.product?.sizes?.[0].quantity),
    });
    sizes.add({
      size: "L",
      quantity:
        parseInt(formData.get("large")) ||
        Number(productById?.product?.sizes?.[0].quantity),
    });
    sizes.add({
      size: "XL",
      quantity:
        parseInt(formData.get("extraLarge")) ||
        Number(productById?.product?.sizes?.[0].quantity),
    });

    const product = {
      title: formData.get("title") || productById?.product.title,
      price: parseInt(formData.get("price")) || productById?.product.price,
      description:
        formData.get("description") || productById?.product.description,
      sizes: Array.from(sizes) || productById?.product.sizes,
      discountPrecent:
        parseInt(formData.get("discountPrecent")) ||
        productById?.product?.discountPrecent,
      brand: formData.get("brand") || productById?.product.brand,
      topLevelCategory:
        formData.get("topLevelCategory") || productById?.categoryName,
      secondLevelCategory:
        formData.get("secondLevelCategory") || productById?.sectionName,
      thirdLevelCategory:
        formData.get("thirdLevelCategory") ||
        productById?.product?.sectionItem?.itemName,
      name: formData.get("name") || productById?.product.name,
      highlights:
        highlights?.length > 0 ? highlights : productById?.product.highlights,
      color: colors?.length > 0 ? colors : productById?.product.colors,
      imageUrl: images?.length > 0 ? images : productById?.product.imageUrl,
    };

    if (id > 0) {
      product.id = Number(id);
    }
    console.log(product);
    createProduct(product);
  }

  return (
    <>
      <Typography
        variant="h6"
        textAlign={"center"}
        sx={{ mt: { xs: "15px", md: "25px", lg: "40px" } }}
        color={"primary"}
      >
        Product Form
      </Typography>

      <Grid container>
        <Grid item xs={12} lg={8.5} mx={"auto"} my={2}>
          <form encType="multipart/form-data">
            <input
              id="fileInput"
              type="file"
              className="hidden"
              accept="image/**"
              onChange={(e) => handleFileChange(e)}
            />
            <label
              htmlFor="fileInput"
              className="text-white bg-indigo-500 px-2 py-1 rounded-md cursor-pointer md:py-2 lg:px-4"
            >
              Upload Product Pictures
            </label>
          </form>
        </Grid>
        <Grid item xs={12} lg={9} mx="auto" my={2}>
          <div className="flex flex-col gap-2">
            {highlights?.map((highlight, index) => (
              <p className="bg-slate-100 px-2 py-1 rounded-md w-fit">
                <span className="font-semibold mr-1">{index + 1}.</span>
                {highlight}
              </p>
            ))}
          </div>
        </Grid>
        <TextField
          name="highlights"
          label="Product highlights"
          placeholder="Hightlights"
          required={id === 0}
          value={highlight}
          onChange={(e) => setHighlight(() => e.target.value)}
          multiline
          rows={3}
          xs={10}
          sx={{ width: "72.5%", mx: "auto", my: "10px" }}
          fullWidth
          type="text"
          size="small"
          InputProps={{
            endAdornment: (
              <InputAdornment>
                <Button
                  variant="contained"
                  size="small"
                  onClick={(e) => {
                    setHighlights((prev) => [...prev, highlight]);
                    setHighlight(() => "");
                  }}
                >
                  Add
                </Button>
              </InputAdornment>
            ),
          }}
        />
      </Grid>
      <Grid
        container
        sx={{ padding: { xs: "10px 20px", md: "20px 40px" } }}
        spacing={4}
        component={"form"}
        onSubmit={(e) => handleSubmit(e)}
      >
        <Grid item xs={12} md={6} display={"flex"} justifyContent={"center"}>
          <TextField
            id="title"
            name="title"
            label="Title"
            required={id === 0}
            type="text"
            placeholder="title"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <Title />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} md={6} display={"flex"} justifyContent={"center"}>
          <TextField
            id="name"
            name="name"
            label="Name"
            required={id === 0}
            type="text"
            placeholder="Product name"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <Title />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} md={6} display={"flex"} justifyContent={"center"}>
          <TextField
            id="price"
            name="price"
            label="Price"
            placeholder="Product price"
            required={id === 0}
            type="number"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <Euro />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={6} display="flex" justifyContent={"center"}>
          <TextField
            id="small"
            name="small"
            label="Size S Quantity"
            placeholder="Quantity S"
            required={id === 0}
            type="number"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <ProductionQuantityLimitsIcon />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={6} display="flex" justifyContent={"center"}>
          <TextField
            id="medium"
            name="medium"
            label="Size M Quantity"
            placeholder="Quantity M"
            required={id === 0}
            type="number"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <ProductionQuantityLimitsIcon />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={6} display="flex" justifyContent={"center"}>
          <TextField
            id="large"
            name="large"
            required={id === 0}
            type="number"
            placeholder="Quantity L"
            label="Size L Quantity"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <ProductionQuantityLimitsIcon />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={6} display="flex" justifyContent={"center"}>
          <TextField
            id="extraLarge"
            name="extraLarge"
            label="Size XL Quantity"
            required={id === 0}
            type="number"
            placeholder="Quantity XL"
            size="small"
            sx={{
              width: { xs: "78%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <ProductionQuantityLimitsIcon />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} lg={6} display="flex" justifyContent={"center"}>
          <TextField
            id="discountPrecent"
            name="discountPrecent"
            label="Discount Precent"
            required={id === 0}
            type="number"
            placeholder="Discount precent"
            size="small"
            sx={{
              width: { xs: "90%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <ProductionQuantityLimitsIcon />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} lg={6} display="flex" justifyContent={"center"}>
          <TextField
            id="brand"
            name="brand"
            label="Brand"
            required={id === 0}
            type="text"
            placeholder="Discount precent"
            size="small"
            sx={{
              width: { xs: "90%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <BrandingWatermark />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} lg={6} display="flex" justifyContent={"center"}>
          <TextField
            id="topLevelCategory"
            name="topLevelCategory"
            label="Top Category"
            required={id === 0}
            type="text"
            placeholder="Top Level Category"
            size="small"
            sx={{
              width: { xs: "90%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <Category />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} lg={6} display="flex" justifyContent={"center"}>
          <TextField
            id="secondLevelCategory"
            name="secondLevelCategory"
            label="Second Category"
            required={id === 0}
            type="text"
            placeholder="Second Level Category"
            size="small"
            sx={{
              width: { xs: "90%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <Category />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} lg={6} display="flex" justifyContent={"center"}>
          <TextField
            id="thirdLevelCategory"
            name="thirdLevelCategory"
            label="Third Level Category"
            required={id === 0}
            type="text"
            placeholder="Third Level Category"
            size="small"
            sx={{
              width: { xs: "90%", lg: "50%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment>
                  <Category />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid item xs={12} lg={6} display={"flex"} justifyContent={"center"}>
          <FormControl
            sx={{ m: 1, width: { xs: "90%", lg: "50%" } }}
            size="small"
          >
            <InputLabel id="demo-multiple-name-label">Colors</InputLabel>
            <Select
              labelId="demo-multiple-name-label"
              id="demo-multiple-name"
              multiple
              value={colors}
              onChange={handleChange}
              input={<OutlinedInput label="Name" />}
              MenuProps={MenuProps}
            >
              {names.map((name) => (
                <MenuItem
                  key={name}
                  value={name}
                  style={getStyles(name, colors, theme)}
                >
                  {name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} display="flex" justifyContent={"center"}>
          <TextField
            id="description"
            name="description"
            type="text"
            required={id === 0}
            placeholder="Product description..."
            label="Description"
            value={productById && productById.product.description}
            size="small"
            rows={3}
            multiline
            sx={{
              width: { xs: "90%", lg: "75.5%" },
              margin: "auto",
            }}
            mt={1}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Description />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid
          item
          xs={12}
          lg={9.1}
          display="flex"
          justifyContent={"center"}
          margin={"auto"}
        >
          <Button type="submit" variant="contained" size="large" fullWidth>
            {id > 0 ? "Update Product" : "Add Product"}
          </Button>
        </Grid>
      </Grid>
    </>
  );
};

export default AddProductForm;
