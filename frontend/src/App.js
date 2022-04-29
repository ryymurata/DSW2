import React from "react";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Inicio from "@pages/Home";
import Crud from "@pages/CRUD";
import ListarClientes from "@pages/CRUD/ListarClientes";
import ListarLojas from "@pages/CRUD/ListarLojas";
import Comprar from "@pages/Comprar";
import Topbar from "@components/Topbar";

/* uso de router DOM para mudança de páginas */
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Topbar msg="Olá, admin!"/>
        <Routes>
          <Route element={ <Inicio /> } path="/" exact />
          <Route element={ <Crud /> } path="/CRUD" />
          <Route element={ <ListarClientes /> } path="/CRUD/clientes" />
          <Route element={ <ListarLojas /> } path="/CRUD/lojas" />
          <Route element={ <Comprar /> } path="/comprar/:id" />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
