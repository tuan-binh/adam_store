import Carousel from 'react-bootstrap/Carousel';
import React from 'react'

function Banner() {
  return (
    <Carousel data-bs-theme="dark">
      <Carousel.Item>
        <img
          className="d-block w-100"
          src="https://theme.hstatic.net/1000333436/1001040510/14/slideshow_1_master.jpg?v=141"
          alt="First slide"
        />
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100"
          src="https://theme.hstatic.net/1000333436/1001040510/14/slideshow_2_master.jpg?v=141"
          alt="Second slide"
        />
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100"
          src="https://theme.hstatic.net/1000333436/1001040510/14/slideshow_3_master.jpg?v=141"
          alt="Third slide"
        />
      </Carousel.Item>
    </Carousel>
  )

}

export default Banner