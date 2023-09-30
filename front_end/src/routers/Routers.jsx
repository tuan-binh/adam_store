import { Route, Routes } from "react-router-dom";

import OutletPage from "../pages/home/OutletPage";
import React from "react";

function Routers() {
  return (
    <>
      <Routes>
        <Route path="/" element={<OutletPage />}></Route>
      </Routes>
    </>
  );
}

export default Routers;
