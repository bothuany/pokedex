import React from "react";
import TypeFilter from "./TypeFilter";
import SortOptions from "./SortOptions";
import { VStack } from "@chakra-ui/react";
import LimitingFilters from "./LimitingFilters";

function PokemonFilters({
  type,
  setType,
  isCaught,
  setIsCaught,
  sortBy,
  setSortBy,
  sortDirection,
  setSortDirecton,
  isAll,
  setIsAll,
}) {
  return (
    <VStack spacing={4} align={"strecth"}>
      <TypeFilter type={type} setType={setType} />

      <SortOptions
        sortBy={sortBy}
        setSortBy={setSortBy}
        sortDirection={sortDirection}
        setSortDirecton={setSortDirecton}
      />
      <LimitingFilters
        isCaught={isCaught}
        setIsCaught={setIsCaught}
        isAll={isAll}
        setIsAll={setIsAll}
      />
    </VStack>
  );
}

export default PokemonFilters;
