import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import SportsSoccerIcon from '@mui/icons-material/SportsSoccer';
import Api from "../services/api";
import {Link} from "react-router-dom";

const pages =
    (() => {
        if (Api.isAuthenticated()) {
            const user = Api.getCurrentUser();
            if (user.role === 'USER') {
                return (
                        [{name: 'Home', href: '/'},
                        {name: 'Matches', href: '/matches'}, 
                            {name: 'Chat', href: '/chat'},    
                        {name: 'Profile', href: '/profile'}])
            } else if (user.role === 'ADMIN') {
                return (
                        [{name: 'Home', href: '/'},
                        {name: 'Matches', href: '/matches'},
                        {name: 'Admin', href: '/admin'}])
            }
        } else {
            return (
                    [{name: 'Home', href: '/'},
                    {name: 'Matches', href: '/matches'},
                    {name: 'Log In', href: '/login'},
                    {name: 'Register', href: '/register'}])
        }
    })();

const ResponsiveAppBar = () => {
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  
  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  
  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };
  
  return (
    <AppBar position="sticky" sx={{height: 85,boxShadow: 3}}>
      <Container maxWidth="xl">
        <Toolbar disableGutters>
            <Link
                to="/"
            >
                <SportsSoccerIcon
                    sx={{ display: { xs: 'none', md: 'flex' }, color: 'white' ,mr: 1, fontSize: 40}}
                />
            </Link>
          <Typography
            variant="h5"
            noWrap
            component={Link}
            to="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            ULTRAS
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{display: { xs: 'block', md: 'none' },color: 'white' ,mr: 1, fontSize: 40}}
            >
              {pages.map((page) => (
                <MenuItem key={page.name} onClick={handleCloseNavMenu}>
                  <Typography textAlign="center">{page.name}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
            <Link to="/">
                <SportsSoccerIcon
                    sx={{ display: { xs: 'flex', md: 'none' }, mr: 1, fontSize: 40 }}
                />
            </Link>
          <Typography
            variant="h5"
            noWrap
            component={Link}
            to="/"
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            ULTRAS
          </Typography>
          <Box
            sx={{
              flexGrow: 1,
              justifyContent: 'flex-end',
              display: { xs: 'none', md: 'flex' },
            }}
          >
            {pages.map((page) => (
              <Button component={Link}
                to={page.href}  
                key={page.name}
                onClick={handleCloseNavMenu}
                sx={{
                  my: 2,
                  color: 'white',
                  display: 'block',
                }}
              >
                {page.name}
              </Button>
            ))}
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
};
export default ResponsiveAppBar;
