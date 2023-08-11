import React from "react";

function Pokeball({ turningActive }) {
  return (
    <img
      className={turningActive ? "pokeball" : ""}
      width="48"
      height="48"
      src="https://img.icons8.com/color/48/pokeball--v1.png"
      alt="pokeball--v1"
    />
  );
}

export default Pokeball;
