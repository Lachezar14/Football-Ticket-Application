
import { Box } from '@mui/system';
import homeImage from '../images/homeImage.jpg';
import * as React from 'react';

const HomeImage = () => {
    return (    
        <Box 
            sx={{
                height: '100vh', 
                width: '100%', 
                backgroundImage: `url(${homeImage})`, 
                backgroundSize: 'cover', 
                backgroundPosition: 'center', 
                backgroundRepeat: 'no-repeat'
            }}>
            
        </Box>
    )};
export default HomeImage;
