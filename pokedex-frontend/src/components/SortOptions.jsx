import { Badge, Divider, Switch, VStack, Select } from "@chakra-ui/react";
import React from "react";
import dir from "../config/dir.json";

function SortOptions({ sortBy, setSortBy, sortDirection, setSortDirecton }) {
  const sortByOptions = [
    { name: "Name", value: dir.sortByOptions.pokemons.name },
    { name: "Price", value: dir.sortByOptions.pokemons.price },
    { name: "HP", value: dir.sortByOptions.pokemons.stats.hp },
    { name: "Attack", value: dir.sortByOptions.pokemons.stats.attack },
    { name: "Defense", value: dir.sortByOptions.pokemons.stats.defense },
    {
      name: "Special Attack",
      value: dir.sortByOptions.pokemons.stats.specialAttack,
    },
    {
      name: "Special Defense",
      value: dir.sortByOptions.pokemons.stats.specialDefense,
    },
    { name: "Speed", value: dir.sortByOptions.pokemons.stats.speed },
    { name: "Total", value: dir.sortByOptions.pokemons.stats.total },
  ];

  return (
    <VStack>
      <Badge colorScheme="whiteAlpha" fontSize="2em">
        Sort
      </Badge>
      <Divider />

      <Select
        defaultValue={""}
        onChange={(e) => setSortBy(e.target.value)}
        color={"white"}
        maxW={"50%"}
      >
        {sortByOptions?.map((option, index) => (
          <option
            style={{ background: "rgb(43, 55, 68)" }}
            value={option.value}
            key={index}
          >
            {option.name}
          </option>
        ))}
      </Select>
      <Switch
        color={"white"}
        colorScheme="gray"
        value={sortDirection}
        mb={2}
        onChange={() => {
          if (dir.sortByOptions.sortDirection.asc === sortDirection) {
            setSortDirecton(dir.sortByOptions.sortDirection.desc);
          } else {
            setSortDirecton(dir.sortByOptions.sortDirection.asc);
          }
        }}
      >
        {dir.sortByOptions.sortDirection.asc === sortDirection
          ? "Ascending"
          : "Descending"}
      </Switch>
    </VStack>
  );
}

export default SortOptions;
