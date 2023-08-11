import { Route, Routes } from "react-router-dom";
import "./App.css";
import Login from "./pages/Login";
import Homepage from "./pages/Homepage";
import { useColorMode } from "@chakra-ui/react";
import { useState } from "react";
import AdminDashboard from "./pages/AdminDashboard";
import Settings from "./pages/Settings";
import TrainersPokemonPage from "./pages/TrainersPokemonPage";
import dir from "./config/dir.json";

function App() {
  const { colorMode, toggleColorMode } = useColorMode();

  useState(() => {
    if (colorMode === "dark") {
      toggleColorMode();
    }
  }, []);

  return (
    <div className="App">
      <Routes>
        <Route path="/login" element={<Login />} exact />
        <Route path="/" element={<Homepage />} exact />
        <Route path="/dashboard" element={<AdminDashboard />} />
        <Route path="/settings" element={<Settings />} />
        <Route
          path="/catch-list"
          element={
            <TrainersPokemonPage pageType={dir.trainer.pageTypes.catchList} />
          }
        />
        <Route
          path="/wish-list"
          element={
            <TrainersPokemonPage pageType={dir.trainer.pageTypes.wishList} />
          }
        />
      </Routes>
    </div>
  );
}

export default App;
