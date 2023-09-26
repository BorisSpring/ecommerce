import React from 'react';
import { Fragment, useState } from 'react';
import { Dialog, Popover, Tab, Transition } from '@headlessui/react';
import {
  Bars3Icon,
  ShoppingBagIcon,
  XMarkIcon,
} from '@heroicons/react/24/outline';
import {
  Avatar,
  Button,
  Divider,
  FormControl,
  IconButton,
  Input,
  InputAdornment,
  InputLabel,
  Menu,
  MenuItem,
} from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import Login from '../Auth/Login';
import Register from '../Auth/Register';
import { useGetLoggedUser } from '../Auth/useGetLoggedUser';
import { useQueryClient } from '@tanstack/react-query';
import { useGetCategories } from './useGetCategories';
import { Search } from '@mui/icons-material';

const Navigation = () => {
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState();
  const isOpen = Boolean(anchorEl);
  const navigate = useNavigate();
  const [openLoginModal, setOpenLoginModal] = useState(false);
  const [signUpModal, setSignUpModal] = useState(false);
  const { loggedUser } = useGetLoggedUser();
  const queryClient = useQueryClient();
  const { categories } = useGetCategories();
  const [searchQuery, setSearchQuery] = useState('');

  function handleClick(e) {
    setAnchorEl(() => e.currentTarget);
  }

  function onClose() {
    setAnchorEl(() => null);
  }

  function classNames(...classes) {
    return classes.filter(Boolean).join(' ');
  }
  return (
    <>
      <div className='bg-white z-50'>
        <Transition.Root show={open} as={Fragment}>
          <Dialog
            as='div'
            className='relative z-40 lg:hidden'
            onClose={setOpen}
          >
            <Transition.Child
              as={Fragment}
              enter='transition-opacity ease-linear duration-300'
              enterFrom='opacity-0'
              enterTo='opacity-100'
              leave='transition-opacity ease-linear duration-300'
              leaveFrom='opacity-100'
              leaveTo='opacity-0'
            >
              <div className='fixed inset-0 bg-black bg-opacity-25' />
            </Transition.Child>

            <div className='fixed inset-0 z-40 flex'>
              <Transition.Child
                as={Fragment}
                enter='transition ease-in-out duration-300 transform'
                enterFrom='-translate-x-full'
                enterTo='translate-x-0'
                leave='transition ease-in-out duration-300 transform'
                leaveFrom='translate-x-0'
                leaveTo='-translate-x-full'
              >
                <Dialog.Panel className='relative flex w-full max-w-xs flex-col overflow-y-auto bg-white pb-12 shadow-xl'>
                  <div className='flex px-4 pb-2 pt-5'>
                    <button
                      type='button'
                      className='relative -m-2 inline-flex items-center justify-center rounded-md p-2 text-gray-400'
                      onClick={() => setOpen(false)}
                    >
                      <span className='absolute -inset-0.5' />
                      <span className='sr-only'>Close menu</span>
                      <XMarkIcon className='h-6 w-6' aria-hidden='true' />
                    </button>
                  </div>

                  <Tab.Group as='div' className='mt-2'>
                    <div className='border-b border-gray-200'>
                      <Tab.List className='-mb-px flex space-x-8 px-4'>
                        {categories?.map((category) => (
                          <Tab
                            key={category?.categoryName}
                            className={({ selected }) =>
                              classNames(
                                selected
                                  ? 'border-indigo-600 text-indigo-600'
                                  : 'border-transparent text-gray-900',
                                'flex-1 whitespace-nowrap border-b-2 px-1 py-4 text-base font-medium'
                              )
                            }
                          >
                            {category?.categoryName}
                          </Tab>
                        ))}
                      </Tab.List>
                    </div>
                    <Tab.Panels as={Fragment}>
                      {categories?.map((category) => (
                        <Tab.Panel
                          key={category?.categoryName}
                          className='space-y-10 px-4 pb-8 pt-10'
                        >
                          {/* <div className="grid grid-cols-2 gap-x-4">
                          {category.featured.map((item) => (
                              <div
                                key={item.name}
                                className="group relative text-sm"
                              >
                                <div className="aspect-h-1 aspect-w-1 overflow-hidden rounded-lg bg-gray-100 group-hover:opacity-75">
                                  <img
                                    src={item.imageSrc}
                                    alt={item.imageAlt}
                                    className="object-cover object-center"
                                  />
                                </div>
                                <a
                                  href={item.href}
                                  className="mt-6 block font-medium text-gray-900"
                                >
                                  <span
                                    className="absolute inset-0 z-10"
                                    aria-hidden="true"
                                  />
                                  {item.name}
                                </a>
                                <p aria-hidden="true" className="mt-1">
                                  Shop now
                                </p>
                              </div>
                            ))}
                          </div> */}
                          {category?.sections?.map?.((section) => (
                            <div key={section?.sectionName}>
                              <p
                                id={`${category.id}-${section.id}-heading-mobile`}
                                className='font-medium text-gray-900'
                              >
                                {section?.sectionName}
                              </p>
                              <ul
                                aria-labelledby={`${category.categoryName}-${section.sectionName}-heading-mobile`}
                                className='mt-6 flex flex-col space-y-6'
                              >
                                {section?.items?.map((item) => (
                                  <li
                                    key={item.itemName}
                                    className='flow-root'
                                    onClick={() => {
                                      navigate(
                                        `/${category.categoryName}/${section.sectionName}/${item.itemName}?page=1`
                                      );
                                      setOpen(() => false);
                                    }}
                                  >
                                    {item.itemName}
                                  </li>
                                ))}
                              </ul>
                            </div>
                          ))}
                        </Tab.Panel>
                      ))}
                    </Tab.Panels>
                  </Tab.Group>

                  <Link
                    to='/'
                    className='flex items-center text-sm font-medium text-gray-700 hover:text-gray-800'
                  >
                    Home
                  </Link>

                  <div className='space-y-6 border-t border-gray-200 px-4 py-6'>
                    {!!loggedUser?.lastName ? (
                      <span>
                        <Button onClick={handleClick}>
                          <Avatar>
                            {loggedUser.firstName.slice(0, 1)}
                            {loggedUser.lastName.slice(0, 1)}
                          </Avatar>
                        </Button>
                        <Menu
                          open={isOpen}
                          anchorEl={anchorEl}
                          onClose={onClose}
                        >
                          {loggedUser?.authority?.authority === 'ADMIN' ? (
                            <span>
                              <MenuItem
                                onClick={() => {
                                  navigate(`/admin/products?page=1`);
                                  onClose();
                                }}
                              >
                                Products
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() =>
                                  navigate('/admin/reviews?page=1')
                                }
                              >
                                Reviews
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() => {
                                  onClose();
                                  navigate('/admin/ratings?page=1');
                                }}
                              >
                                Ratings
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() => {
                                  navigate(`/admin/addProduct`);
                                  onClose();
                                }}
                              >
                                Add Product
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() => {
                                  navigate(`/admin/orders?page=1`);
                                  onClose();
                                }}
                              >
                                Orders
                              </MenuItem>
                            </span>
                          ) : (
                            <MenuItem
                              onClick={() => navigate(`/account/order`)}
                            >
                              Orders
                            </MenuItem>
                          )}
                          <Divider />
                          <MenuItem
                            onClick={() => {
                              queryClient.setQueryData(['loggedUser'], null);

                              localStorage.removeItem('jwt');
                              onClose();
                            }}
                          >
                            Log out
                          </MenuItem>
                        </Menu>
                      </span>
                    ) : (
                      <>
                        <div className='flow-root'>
                          <button
                            onClick={() => setOpenLoginModal(() => true)}
                            className='-m-2 block p-2 font-medium text-gray-900'
                          >
                            Sign in
                          </button>
                        </div>
                        <div className='flow-root'>
                          <button
                            onClick={() => setSignUpModal(() => true)}
                            href='#'
                            className='-m-2 block p-2 font-medium text-gray-900'
                          >
                            Create account
                          </button>
                        </div>
                      </>
                    )}
                  </div>

                  <div className='border-t border-gray-200 px-4 py-6'>
                    <img
                      src='https://tailwindui.com/img/flags/flag-canada.svg'
                      alt=''
                      className='block h-auto w-5 flex-shrink-0 ml-1'
                    />
                    <span className='ml-0 my-2 block text-base font-medium text-gray-900'>
                      EUR
                    </span>
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </Dialog>
        </Transition.Root>

        <header className='relative bg-white z-50'>
          <p className='flex h-10 items-center justify-center bg-indigo-600 px-4 text-sm font-medium text-white sm:px-6 lg:px-8'>
            Get free delivery on orders over $100
          </p>

          <nav aria-label='Top' className='mx-auto px-4 sm:px-6 lg:px-8'>
            <div className='border-b border-gray-200'>
              <div className='flex h-16 items-center'>
                <button
                  type='button'
                  className='relative rounded-md bg-white p-2 text-gray-400 lg:hidden'
                  onClick={() => setOpen(true)}
                >
                  <span className='absolute -inset-0.5' />
                  <span className='sr-only'>Open menu</span>
                  <Bars3Icon className='h-6 w-6' aria-hidden='true' />
                </button>

                <Popover.Group className='hidden lg:ml-8 lg:block lg:self-stretch'>
                  <div className='flex h-full space-x-8'>
                    {categories?.map?.((category) => (
                      <Popover key={category.categoryName} className='flex'>
                        {({ open }) => (
                          <>
                            <div className='relative flex '>
                              <Popover.Button
                                className={classNames(
                                  open
                                    ? 'border-indigo-600 text-indigo-600  focus:ring focus:ring-indigo-400 focus:ring-offset-2 outline-none'
                                    : 'border-transparent text-gray-700 hover:text-gray-800  focus:ring focus:ring-indigo-400 focus:ring-offset-2 outline-none ',
                                  'relative z-10 -mb-px flex items-center border-b-2 pt-px text-sm font-medium transition-colors duration-200 ease-out focus:ring-4 focus:ring-opacity-50 focus:ring-indigo-500 focus:ring-offset'
                                )}
                              >
                                {category?.categoryName}
                              </Popover.Button>
                            </div>

                            <Transition
                              as={Fragment}
                              enter='transition ease-out duration-200'
                              enterFrom='opacity-0'
                              enterTo='opacity-100'
                              leave='transition ease-in duration-150'
                              leaveFrom='opacity-100'
                              leaveTo='opacity-0'
                            >
                              <Popover.Panel className='absolute inset-x-0 top-full text-sm text-gray-500'>
                                {/* Presentational element used to render the bottom shadow, if we put the shadow on the actual panel it pokes out the top, so we use this shorter element to hide the top of the shadow */}
                                <div
                                  className='absolute inset-0 top-1/2 bg-white shadow'
                                  aria-hidden='true'
                                />

                                <div className='relative bg-white'>
                                  <div className='mx-auto max-w-7xl px-8'>
                                    <div className='grid grid-cols-2 gap-x-8 gap-y-10 py-16'>
                                      <div className='col-start-2 grid grid-cols-2 gap-x-8'>
                                        {/* {category.featured.map((category) => (
                                          <div
                                            key={category.name}
                                            className="group relative text-base sm:text-sm"
                                          >
                                            <div className="aspect-h-1 aspect-w-1 overflow-hidden rounded-lg bg-gray-100 group-hover:opacity-75">
                                              <img
                                                src={category.imageSrc}
                                                alt={category.imageAlt}
                                                className="object-cover object-center"
                                              />
                                            </div>
                                            <a
                                              href={category.href}
                                              className="mt-6 block font-medium text-gray-900"
                                            >
                                              <span
                                                className="absolute inset-0 z-10"
                                                aria-hidden="true"
                                              />
                                              {category.name}
                                            </a>
                                            <p
                                              aria-hidden="true"
                                              className="mt-1"
                                            >
                                              Shop now
                                            </p>
                                          </div>
                                        ))} */}
                                      </div>
                                      <div className='row-start-1 grid grid-cols-3 gap-x-8 gap-y-10 text-sm'>
                                        {category?.sections?.map?.(
                                          (section) => (
                                            <div key={section?.sectionName}>
                                              <p
                                                id={`${section?.name}-heading`}
                                                className='font-medium text-gray-900'
                                              >
                                                {section?.sectionName}
                                              </p>
                                              <ul
                                                aria-labelledby={`${section?.name}-heading`}
                                                className='mt-6 space-y-6 sm:mt-4 sm:space-y-4'
                                              >
                                                {section?.items?.map?.(
                                                  (item) => (
                                                    <li
                                                      key={item?.itemName}
                                                      className='flex cursor-pointer'
                                                      onClick={() => {
                                                        navigate(
                                                          `/${category.categoryName}/${section.sectionName}/${item.itemName}?page=1`
                                                        );
                                                        setOpen(() => false);
                                                      }}
                                                    >
                                                      {item.itemName}
                                                    </li>
                                                  )
                                                )}
                                              </ul>
                                            </div>
                                          )
                                        )}
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </Popover.Panel>
                            </Transition>
                          </>
                        )}
                      </Popover>
                    ))}

                    <Link
                      to='/'
                      className='flex items-center text-sm font-medium text-gray-700 hover:text-gray-800'
                    >
                      Home
                    </Link>
                  </div>
                </Popover.Group>

                <div className='ml-auto flex items-center'>
                  <div className='hidden lg:flex lg:flex-1 lg:items-center lg:justify-end lg:space-x-6'>
                    {!!loggedUser?.lastName ? (
                      <>
                        <Button onClick={handleClick}>
                          <Avatar>
                            {loggedUser.firstName.slice(0, 1)}
                            {loggedUser.lastName.slice(0, 1)}
                          </Avatar>
                        </Button>
                        <Menu
                          open={isOpen}
                          anchorEl={anchorEl}
                          onClose={onClose}
                        >
                          {loggedUser?.authority?.authority === 'ADMIN' ? (
                            <span>
                              <MenuItem
                                onClick={() => {
                                  navigate(`/admin/products?page=1`);
                                  onClose();
                                }}
                              >
                                Products
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() =>
                                  navigate('/admin/reviews?page=1')
                                }
                              >
                                Reviews
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() => {
                                  onClose();
                                  navigate('/admin/ratings?page=1');
                                }}
                              >
                                Ratings
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() => {
                                  navigate(`/admin/addProduct`);
                                  onClose();
                                }}
                              >
                                Add Product
                              </MenuItem>
                              <Divider />
                              <MenuItem
                                onClick={() => {
                                  navigate(`/admin/orders?page=1`);
                                  onClose();
                                }}
                              >
                                Orders
                              </MenuItem>
                            </span>
                          ) : (
                            <MenuItem
                              onClick={() => navigate(`/account/order`)}
                            >
                              Orders
                            </MenuItem>
                          )}
                          <Divider />
                          <MenuItem
                            onClick={() => {
                              queryClient.setQueryData(['loggedUser'], null);

                              localStorage.removeItem('jwt');
                              onClose();
                            }}
                          >
                            Log out
                          </MenuItem>
                        </Menu>
                      </>
                    ) : (
                      <>
                        <button
                          onClick={() => setOpenLoginModal(() => true)}
                          className='text-sm font-medium text-gray-700 hover:text-gray-800 cursor-pointer outline-none focus:ring-2 focus:ring-opacity-50 focus:ring-indigo-500'
                        >
                          Sign in
                        </button>
                        <span
                          className='h-6 w-px bg-gray-200'
                          aria-hidden='true'
                        />
                        <button
                          onClick={() => setSignUpModal(() => true)}
                          className=' whitespace-nowrap text-sm font-medium text-gray-700 hover:text-gray-800 cursor-pointer outline-none focus:ring-opacity-50 focus:ring focus:ring-indigo-500'
                        >
                          Sign up
                        </button>
                      </>
                    )}
                  </div>

                  <div className='hidden lg:ml-8 lg:flex'>
                    <img
                      src='https://media.istockphoto.com/id/1173374368/photo/european-union-flag-as-background.jpg?s=612x612&w=0&k=20&c=F6XARrTQGpZD9DVr0dZQCU2jlqc53bLTpp76nkyBESI='
                      alt='Euro currency flag'
                      className='block h-auto w-5 flex-shrink-0'
                    />
                    <span className='ml-3 block text-sm font-medium'>EUR</span>
                  </div>

                  <div className='flex lg:ml-6'>
                    <div className='flex items-end '>
                      <FormControl
                        sx={{ m: 1, width: '25ch' }}
                        variant='standard'
                      >
                        <InputLabel htmlFor='standard-adornment-password'>
                          Search Products By Title
                        </InputLabel>
                        <Input
                          id='standard-adornment-password'
                          type='text'
                          value={searchQuery}
                          onChange={(e) => setSearchQuery(e.target.value)}
                          endAdornment={
                            <InputAdornment position='end'>
                              <IconButton
                                aria-label='toggle password visibility'
                                onClick={(e) => {
                                  e.preventDefault();
                                  if (searchQuery.trim()?.length > 0) {
                                    navigate(
                                      `/search/${searchQuery.replaceAll(
                                        ' ',
                                        '-'
                                      )}?page=1`
                                    );
                                  }
                                  setSearchQuery('');
                                }}
                              >
                                <Search />
                              </IconButton>
                            </InputAdornment>
                          }
                        />
                      </FormControl>
                    </div>
                  </div>

                  {loggedUser?.cart?.totalQuantity > 0 && (
                    <Link to='/cart' className='ml-4 flow-root lg:ml-6'>
                      <div
                        href='#'
                        className='group -m-2 flex items-center p-2'
                      >
                        <ShoppingBagIcon
                          className='h-6 w-6 flex-shrink-0 text-gray-400 group-hover:text-gray-500'
                          aria-hidden='true'
                        />
                        <span className='ml-2 text-sm font-medium text-gray-700 group-hover:text-gray-800'>
                          {loggedUser?.cart?.totalQuantity}
                        </span>
                        <span className='sr-only'>items in cart, view bag</span>
                      </div>
                    </Link>
                  )}
                </div>
              </div>
            </div>
          </nav>
        </header>
      </div>
      <Login
        handleClose={() => setOpenLoginModal(() => false)}
        open={openLoginModal}
      />
      <Register
        handleClose={() => setSignUpModal(() => false)}
        open={signUpModal}
      />
    </>
  );
};

export default Navigation;
