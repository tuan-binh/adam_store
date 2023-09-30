import "./categories.css"

import Box from '@mui/material/Box'
import { Grid } from '@mui/material'
import React from 'react'

function Categories() {
  return (
    <Box sx={{ padding: '50px 200px' }}>
      <h2 className="section-title position-relative text-uppercase mb-4"><span style={{ backgroundColor: '#fff', paddingRight: '20px' }}>Categories</span></h2>
      <Grid container spacing={4} >
        <Grid item xs={3} >
          <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: '#fff', borderRadius: '8px' }}>
            <Box sx={{ overflow: 'auto', borderTopLeftRadius: '8px', borderBottomLeftRadius: '8px' }}>
              <img width={80} height={80} style={{ objectFit: 'cover' }} src="https://demo.htmlcodex.com/1479/online-shop-website-template/img/cat-1.jpg" alt="" />
            </Box>
            <Box sx={{ flex: '1', alignSelf: 'center', paddingLeft: '50px', fontSize: '22px' }}><p>Quần</p></Box>
          </Box>
        </Grid>

        <Grid item xs={3}>
          <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: '#fff', borderRadius: '8px' }}>
            <Box sx={{ overflow: 'auto', borderTopLeftRadius: '8px', borderBottomLeftRadius: '8px' }}>
              <img width={80} height={80} style={{ objectFit: 'cover' }} src="https://demo.htmlcodex.com/1479/online-shop-website-template/img/cat-1.jpg" alt="" />
            </Box>
            <Box sx={{ flex: '1', alignSelf: 'center', paddingLeft: '50px', fontSize: '22px' }}><p>Quần</p></Box>
          </Box>
        </Grid>

        <Grid item xs={3}>
          <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: '#fff', borderRadius: '8px' }}>
            <Box sx={{ overflow: 'auto', borderTopLeftRadius: '8px', borderBottomLeftRadius: '8px' }}>
              <img width={80} height={80} style={{ objectFit: 'cover' }} src="https://demo.htmlcodex.com/1479/online-shop-website-template/img/cat-1.jpg" alt="" />
            </Box>
            <Box sx={{ flex: '1', alignSelf: 'center', paddingLeft: '50px', fontSize: '22px' }}><p>Quần</p></Box>
          </Box>
        </Grid>

        <Grid item xs={3}>
          <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: '#fff', borderRadius: '8px' }}>
            <Box sx={{ overflow: 'auto', borderTopLeftRadius: '8px', borderBottomLeftRadius: '8px' }}>
              <img width={80} height={80} style={{ objectFit: 'cover' }} src="https://demo.htmlcodex.com/1479/online-shop-website-template/img/cat-1.jpg" alt="" />
            </Box>
            <Box sx={{ flex: '1', alignSelf: 'center', paddingLeft: '50px', fontSize: '22px' }}><p>Quần</p></Box>
          </Box>
        </Grid>

        <Grid item xs={3}>
          <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: '#fff', borderRadius: '8px' }}>
            <Box sx={{ overflow: 'auto', borderTopLeftRadius: '8px', borderBottomLeftRadius: '8px' }}>
              <img width={80} height={80} style={{ objectFit: 'cover' }} src="https://demo.htmlcodex.com/1479/online-shop-website-template/img/cat-1.jpg" alt="" />
            </Box>
            <Box sx={{ flex: '1', alignSelf: 'center', paddingLeft: '50px', fontSize: '22px' }}><p>Quần</p></Box>
          </Box>
        </Grid>

      </Grid>
    </Box >
  )
}

export default Categories