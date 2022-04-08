import React from "react";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Inicio from "@pages/Home";
import Crud from "@pages/CRUD";
import Topbar from "@components/Topbar";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Topbar />
        <Routes>
          <Route element={ <Inicio /> } path="/" exact />
          <Route element={ <Crud /> } path="/CRUD" />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
