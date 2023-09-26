import {
  Box,
  Button,
  Card,
  CardHeader,
  CircularProgress,
  Divider,
  Menu,
  MenuItem,
  Pagination,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material';
import React, { useRef, useState } from 'react';
import { useFindAllOrdersAdmin } from './useFindAllOrdersAdmin';
import { useLocation, useNavigate } from 'react-router-dom';
import { useQueryClient } from '@tanstack/react-query';
import { findAllOrdersAdmin } from '../../api/actions';
import { formatDate } from '../../helpers/utils';
import { useConfirmeOrder } from './useConfirmOrder';
import { useDeleteOrderById } from './useDeleteOrderByIdAdmin';
import { useDeliveredOrder } from './useDeliveredOrder';
import { useShipOrder } from './useShipOrder';
import { useCancelOrder } from '../Order/useCancelOrder';

const OrdersOverwiev = () => {
  const { allOrdersAdmin, isLoading } = useFindAllOrdersAdmin();
  const navigate = useNavigate();
  const location = useLocation();
  const queryClient = useQueryClient();
  const [anchorEl, setAnchorEl] = useState(null);
  const ref = useRef(null);

  const { confirmOrder, isConfirming } = useConfirmeOrder();
  const { deleteOrderAdmin, isDeleting } = useDeleteOrderById(allOrdersAdmin);
  const { deliveredOrder, isSettingDelivered } = useDeliveredOrder();
  const { shipOrder, isSettingShipping } = useShipOrder();
  const { cancelOrder } = useCancelOrder();

  if (allOrdersAdmin?.last === false) {
    const params = new URLSearchParams();
    params.set('page', allOrdersAdmin.number + 2);
    queryClient.prefetchQuery({
      queryKey: ['adminOrders', String(allOrdersAdmin.number + 2)],
      queryFn: async () => await findAllOrdersAdmin(params),
    });
  }

  function onClose() {
    setAnchorEl(() => null);
  }

  function handleClick(e, id) {
    setAnchorEl({ el: e.currentTarget, id });
  }

  function handlePageChange(event, newValue) {
    const params = new URLSearchParams(location.search);
    params.set('page', newValue);
    navigate(`?${decodeURIComponent(params.toString())}`);

    if (ref.current) {
      ref.current.scrollIntoView({ behavior: 'auto' });
    }
  }

  if (isLoading)
    return (
      <Box
        display={'flex'}
        justifyContent={'center'}
        alignItems={'center'}
        height={'60dvh'}
      >
        <CircularProgress size='2.5rem' />
      </Box>
    );

  if (allOrdersAdmin?.totalPages === 0) {
    return (
      <Typography variant='h5' textAlign={'center'} my={4}>
        Doesnt have any orders yet
      </Typography>
    );
  }
  return (
    <Card
      sx={{ justifyContent: 'start !important', padding: '10px 20px' }}
      ref={ref}
    >
      <CardHeader title='All Orders' />
      <TableContainer>
        <Table aria-label='simple table'>
          <TableHead>
            <TableRow>
              <TableCell>Product Titles</TableCell>
              <TableCell align='center'>Quantity</TableCell>
              <TableCell align='center'>Discount</TableCell>
              <TableCell align='center'>Discount Price</TableCell>
              <TableCell align='center'>Ordered At</TableCell>
              <TableCell align='center'>Delivered</TableCell>
              <TableCell align='center'>Status</TableCell>
              <TableCell align='right'>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allOrdersAdmin?.content?.map(
              ({
                id,
                orderItems,
                discount,
                orderStatus,
                totalDiscountedPrice,
                createdAt,
                deliveredTime,
              }) => {
                return (
                  <TableRow
                    key={id}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell>
                      {orderItems?.map((orderItem, index) => (
                        <p className='mb-[px]' key={orderItem?.id}>
                          {index + 1}. {orderItem?.product?.title}
                        </p>
                      ))}
                    </TableCell>
                    <TableCell align='center'>
                      {orderItems?.reduce((acc, cur) => acc + cur.quantity, 0)}
                    </TableCell>
                    <TableCell align='center'>{discount}</TableCell>
                    <TableCell align='center'>{totalDiscountedPrice}</TableCell>
                    <TableCell align='center'>
                      {formatDate(createdAt)}
                    </TableCell>
                    <TableCell align='center'>
                      {deliveredTime
                        ? formatDate(deliveredTime)
                        : 'Not delivered'}
                    </TableCell>
                    <TableCell align='center' style={{ fontWeight: '600' }}>
                      {orderStatus}
                    </TableCell>
                    <TableCell align='right'>
                      <Button
                        onClick={(e) => handleClick(e, id)}
                        variant='contained'
                        size='small'
                        disabled={
                          isDeleting ||
                          isConfirming ||
                          isSettingDelivered ||
                          isSettingShipping
                        }
                      >
                        Action
                      </Button>
                      <Menu
                        anchorEl={anchorEl?.el}
                        open={Boolean(anchorEl?.el)}
                        onClose={onClose}
                        sx={{
                          '& .MuiPaper-root.MuiPaper-elevation': {
                            boxShadow:
                              'rgba(0, 0, 0, 0.1) 0px 2px 3px !important',
                          },
                        }}
                      >
                        <MenuItem
                          onClick={() => {
                            confirmOrder(anchorEl?.id);
                          }}
                        >
                          Confirm
                        </MenuItem>
                        <Divider />
                        <MenuItem
                          onClick={() => {
                            onClose();
                            shipOrder(anchorEl?.id);
                          }}
                        >
                          Shipped
                        </MenuItem>
                        <Divider />
                        <MenuItem
                          onClick={() => {
                            onClose();
                            deliveredOrder(anchorEl?.id);
                          }}
                        >
                          Delivered
                        </MenuItem>
                        <Divider />
                        <MenuItem
                          onClick={() => {
                            onClose();
                            cancelOrder(anchorEl?.id);
                          }}
                        >
                          Cancel
                        </MenuItem>
                        <Divider />
                        <MenuItem
                          onClick={() => {
                            onClose();
                            deleteOrderAdmin(anchorEl?.id);
                          }}
                        >
                          Delete
                        </MenuItem>
                      </Menu>
                    </TableCell>
                  </TableRow>
                );
              }
            )}
          </TableBody>
        </Table>
      </TableContainer>
      {allOrdersAdmin?.totalPages > 1 && (
        <Pagination
          count={allOrdersAdmin?.totalPages}
          onChange={handlePageChange}
          color='primary'
          className='w-fit mx-auto my-5'
        />
      )}
    </Card>
  );
};

export default OrdersOverwiev;
