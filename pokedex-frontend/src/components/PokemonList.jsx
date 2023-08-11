import React from "react";
import PokemonCard from "./PokemonCard";
import { Box, HStack } from "@chakra-ui/react";

function PokemonList({ pokemons, user, size, isAdmin, setUser }) {
  const chunkArray = (array, chunkSize) => {
    const result = [];
    for (let i = 0; i < array.length; i += chunkSize) {
      result.push(array.slice(i, i + chunkSize));
    }
    return result;
  };

  const chunkedPokemons = chunkArray(pokemons, size);

  return (
    <Box>
      {chunkedPokemons.map((chunk, index) => (
        <HStack key={index} justifyContent={"flex-start"}>
          {chunk.map((pokemon) => (
            <Box key={pokemon.id} flex="1">
              <PokemonCard
                key={pokemon.id}
                pokemon={pokemon}
                user={user}
                isAdmin={isAdmin}
                setUser={setUser}
              />
            </Box>
          ))}
        </HStack>
      ))}
    </Box>
  );
}

export default PokemonList;
