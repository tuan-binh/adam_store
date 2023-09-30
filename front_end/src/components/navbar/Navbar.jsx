import './navbar.css'

import Box from "@mui/material/Box";
import FavoriteIcon from '@mui/icons-material/Favorite';
import { Link } from "react-router-dom";
import PersonIcon from '@mui/icons-material/Person';
import React from "react";
import SearchIcon from '@mui/icons-material/Search';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

function Navbar() {
  return (
    <Box>
      {/* top */}
      <Box sx={{ display: "flex", justifyContent: "flex-end", alignItems: "center", height: "30px", bgcolor: '#3d464d', color: '#fff', padding: '5px 15px' }}>
        <Box sx={{ display: "flex", gap: 2 }}>
          <p>Phone: 0987654321</p>
          <p>Email: example@gmail.com</p>
        </Box>
      </Box>
      {/* navbar */}
      <Box sx={{ position: 'sticky', top: '0', display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px 30px' }}>
        {/* logo */}
        <div>
          <Link to={"/"} style={{ textDecoration: 'none', color: '#000' }}>
            <span style={{ textTransform: "uppercase", padding: '6px', backgroundColor: 'black', color: '#ffd333', fontWeight: 600 }}>Adam</span>
            <span style={{ textTransform: "uppercase", padding: '6px', backgroundColor: '#ffd333', color: 'black', fontWeight: 600 }}>Store</span>
          </Link>
        </div>
        {/* information */}
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          <div class="input-wrapper">
            <button class="icon">
              <SearchIcon sx={{
                color: '#000',
                transition: 'all 0.2s ease',
                '&:hover': {
                  color: '#ffd333'
                }
              }} />
            </button>
            <input placeholder="search.." class="input" name="text" type="text" />
          </div>
          <FavoriteIcon sx={{ transition: '0.2s all ease', '&:hover': { color: 'red' } }} />
          <ShoppingCartIcon sx={{ transition: '0.2s all ease', '&:hover': { color: '#ffd333' } }} />
          <PersonIcon sx={{ transition: '0.2s all ease', '&:hover': { color: '#3498db' } }} />
        </Box>
      </Box>
    </Box>
  );
}

export default Navbar;
