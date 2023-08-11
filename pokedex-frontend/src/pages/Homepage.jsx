import { Grid, GridItem } from "@chakra-ui/react";
import React from "react";
import Navbar from "../components/common/Navbar";

import PokemonsPart from "../components/PokemonsPart";

function Homepage() {
  return (
    <Grid
      templateAreas={`"navbar navbar"
                      "pokemonspart pokemonspart"`}
      gridTemplateRows={"10%  1fr"}
      gridTemplateColumns={"15% 1fr"}
      h="100vh"
    >
      <GridItem area={"navbar"}>
        <Navbar />
      </GridItem>
      <GridItem pl="2" area={"pokemonspart"}>
        <PokemonsPart />
      </GridItem>
    </Grid>
  );
}

export default Homepage;
