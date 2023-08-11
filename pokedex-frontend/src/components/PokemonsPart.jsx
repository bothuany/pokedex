import { Grid, GridItem, useToast } from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import PokemonList from "./PokemonList";
import axios from "axios";
import { useUser } from "../contexts/UserContext";
import PokemonPagination from "./PokemonPagination";
import BasicSearchBar from "./common/BasicSearchBar";
import PokemonFilters from "./PokemonFilters";
import dir from "../config/dir.json";

function PokemonsPart() {
  const toast = useToast();
  const { user, setUser } = useUser();

  const [pokemons, setPokemons] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);

  //Filters
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(4);
  const [pagesQuantity, setPagesQuantity] = useState(10);
  const [search, setSearch] = useState("");
  const [type, setType] = useState("");
  const [sortBy, setSortBy] = useState(dir.sortByOptions.pokemons.name);
  const [sortDirection, setSortDirecton] = useState(
    dir.sortByOptions.sortDirection.asc
  );
  const [isAll, setIsAll] = useState(true);
  const [isCaught, setIsCaught] = useState(false);

  useEffect(() => {
    if (user?.role === dir.roles.admin) {
      setIsAdmin(true);
    } else {
      setIsAdmin(false);
    }
  }, [user]);

  useEffect(() => {
    if (user) {
      const getPokemons = async () => {
        try {
          const config = {
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
              Authorization: `Bearer ${user.token}`,
            },
          };

          const { data } = await axios.get(
            dir.api +
              `/pokemons/search?page=${
                page - 1
              }&size=${size}&name=${search}&type=${type}&isCaught=${isCaught}&sortBy=${sortBy}&sortDirection=${sortDirection}&isAll=${isAll}`,
            config
          );
          setPagesQuantity(data.totalPages);
          setPokemons(data.content);
        } catch (error) {}
      };
      getPokemons();
    }
  }, [user, page, size, search, type, isCaught, sortBy, sortDirection, isAll]);
  useEffect(() => {
    setPage(1);
  }, [search, type]);

  return (
    <Grid
      templateAreas={`"types search"
                  "types pokemons"
                  "pagination pagination"`}
      gridTemplateRows={"5% 1fr 10%"}
      gridTemplateColumns={"15% 1fr"}
      h="89vh"
      gap="1"
    >
      <GridItem pl="2" area={"types"}>
        <PokemonFilters
          type={type}
          setType={setType}
          isCaught={isCaught}
          setIsCaught={setIsCaught}
          sortBy={sortBy}
          setSortBy={setSortBy}
          sortDirection={sortDirection}
          setSortDirecton={setSortDirecton}
          isAll={isAll}
          setIsAll={setIsAll}
        />
      </GridItem>
      <GridItem pl="2" area={"search"}>
        <BasicSearchBar searchKeyword={search} setSearchKeyword={setSearch} />
      </GridItem>
      <GridItem pl="2" area={"pokemons"}>
        <PokemonList
          pokemons={pokemons}
          user={user}
          size={size}
          isAdmin={isAdmin}
          setUser={setUser}
        />
      </GridItem>
      <GridItem pl="2" area={"pagination"}>
        <PokemonPagination
          page={page}
          setPage={setPage}
          totalPageNumber={pagesQuantity}
        />
      </GridItem>
    </Grid>
  );
}

export default PokemonsPart;
