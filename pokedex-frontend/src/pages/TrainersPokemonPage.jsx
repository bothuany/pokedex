import React, { useEffect } from "react";
import { useUser } from "../contexts/UserContext";
import { useNavigate } from "react-router-dom";
import dir from "../config/dir.json";
import PokemonList from "../components/PokemonList";
import { useTrainersPokemons } from "../contexts/TrainersPokemonsContext";
import Navbar from "../components/common/Navbar";
import { Box, Container, Img, Text } from "@chakra-ui/react";
import pikachu from "../assets/imgs/laying-down-pikachu.png";

function TrainersPokemonPage({ pageType }) {
  const { catchList, wishList } = useTrainersPokemons();
  const { user, setUser } = useUser();
  const navigate = useNavigate();

  useEffect(() => {
    if (user && user.role !== dir.roles.trainer) {
      navigate("/");
    }
  }, [user]);

  return (
    <>
      <Navbar />
      <Text
        className="pokemon-header"
        fontSize={50}
        mb={2}
        textAlign={"center"}
      >
        {pageType === dir.trainer.pageTypes.catchList
          ? "My Pokemons"
          : "My Favorite Pokemons"}
      </Text>
      {pageType === dir.trainer.pageTypes.catchList &&
      catchList.length !== 0 ? (
        <PokemonList
          pokemons={catchList}
          user={user}
          setUser={setUser}
          isAdmin={false}
          size={5}
        />
      ) : (
        ""
      )}
      {pageType === dir.trainer.pageTypes.catchList &&
      catchList.length === 0 ? (
        <Container alignItems={"center"}>
          <Img src={pikachu} maxH={"50%"} />
          <Text fontSize="5xl">It's pretty quiet here...</Text>
        </Container>
      ) : (
        ""
      )}
      {pageType === dir.trainer.pageTypes.wishList && wishList.length === 0 ? (
        <Container alignItems={"center"}>
          <Img src={pikachu} maxH={"50%"} />
          <Text fontSize="5xl">It's pretty quiet here...</Text>
        </Container>
      ) : (
        ""
      )}
      {pageType === dir.trainer.pageTypes.wishList && wishList.length !== 0 ? (
        <PokemonList
          pokemons={wishList}
          user={user}
          setUser={setUser}
          isAdmin={false}
          size={5}
        />
      ) : (
        ""
      )}
    </>
  );
}

export default TrainersPokemonPage;
