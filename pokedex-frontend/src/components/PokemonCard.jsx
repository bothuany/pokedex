import { useEffect, useState } from "react";
import {
  Box,
  Heading,
  Text,
  Center,
  useColorModeValue,
  HStack,
  Divider,
  Badge,
  Img,
  Tooltip,
} from "@chakra-ui/react";
import { GiHand } from "react-icons/gi";
import { MdCatchingPokemon } from "react-icons/md";
import { AiFillStar } from "react-icons/ai";
import { LuStarOff } from "react-icons/lu";
import axios from "axios";
import { useToast } from "@chakra-ui/react";
import PokemonStats from "./PokemonStats";
import dir from "../config/dir.json";
import { useTrainersPokemons } from "../contexts/TrainersPokemonsContext";

export default function PokemonCard({ pokemon, isAdmin, user, setUser }) {
  const [pokemonImage, setPokemonImage] = useState("");
  const [isCaught, setIsCaught] = useState(false);

  const [isOwned, setIsOwned] = useState(false);
  const [isFavorite, setIsFavorite] = useState(false);
  const { catchList, setCatchList, wishList, setWishList } =
    useTrainersPokemons();

  const toast = useToast();

  useEffect(() => {
    if (!isAdmin) {
      if (user) {
        catchList?.map((usersPokemon) => {
          if (pokemon.id === usersPokemon.id) {
            setIsOwned(true);
            return;
          }
        });
        wishList?.map((usersPokemon) => {
          if (pokemon.id === usersPokemon.id) {
            setIsFavorite(true);
            return;
          }
        });
      }
    }
  }, [isAdmin, user]);

  useEffect(() => {
    setIsCaught(pokemon.isCaught);
  }, [pokemon]);

  useEffect(() => {
    async function fetchPokemon() {
      try {
        const config = {
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: `Bearer ${user.token}`,
          },
        };

        const { data } = await axios.get(
          dir.api + `/images/${pokemon.cloudinaryPublicId}`,
          config
        );

        setPokemonImage(data);
      } catch (error) {
        console.log(error);
      }
    }
    fetchPokemon();
  }, []);

  const handleCatch = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      await axios.patch(
        dir.api + `/trainers/${user.username}/catch-pokemon/${pokemon.name}`,
        {},
        config
      );

      setIsCaught(true);
      setIsOwned(true);
      setCatchList([...catchList, pokemon]);
      pokemon.trainer = { username: user.username };
      setUser({ ...user, balance: user.balance - pokemon.price });

      toast({
        title: "Success",
        description: "You have successfully caught the pokemon.",
        status: "success",
        duration: dir.toast.duration,
        isClosable: true,
      });
    } catch (error) {
      toast({
        title: "Error Code: " + error?.response.data.code,
        description: error?.response.data.message,
        status: "error",
        duration: dir.toast.duration,
        isClosable: true,
      });
    }
  };
  const handleRelease = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      await axios.patch(
        dir.api + `/trainers/${user.username}/release-pokemon/${pokemon.name}`,
        {},
        config
      );

      setIsCaught(false);
      setIsOwned(false);
      setCatchList(catchList.filter((p) => p.id !== pokemon.id));
      pokemon.trainer = null;
      setUser({ ...user, balance: user.balance + pokemon.price });

      toast({
        title: "Success",
        description: "You have successfully caught the pokemon.",
        status: "success",
        duration: dir.toast.duration,
        isClosable: true,
      });
    } catch (error) {
      toast({
        title: "Error Code: " + error?.response.data.code,
        description: error?.response.data.message,
        status: "error",
        duration: dir.toast.duration,
        isClosable: true,
      });
    }
  };
  const handleWish = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      await axios.patch(
        dir.api + `/trainers/${user.username}/wish-pokemon/${pokemon.name}`,
        {},
        config
      );

      setIsFavorite(true);
      setWishList([...wishList, pokemon]);

      toast({
        title: "Success",
        description: "You have successfully caught the pokemon.",
        status: "success",
        duration: dir.toast.duration,
        isClosable: true,
      });
    } catch (error) {
      toast({
        title: "Error Code: " + error?.response.data.code,
        description: error?.response.data.message,
        status: "error",
        duration: dir.toast.duration,
        isClosable: true,
      });
    }
  };
  const handleDislike = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      await axios.patch(
        dir.api + `/trainers/${user.username}/dislike-pokemon/${pokemon.name}`,
        {},
        config
      );

      setIsFavorite(false);
      setWishList(wishList.filter((p) => p.id !== pokemon.id));

      toast({
        title: "Success",
        description: "You have successfully caught the pokemon.",
        status: "success",
        duration: dir.toast.duration,
        isClosable: true,
      });
    } catch (error) {
      toast({
        title: "Error Code: " + error?.response.data.code,
        description: error?.response.data.message,
        status: "error",
        duration: dir.toast.duration,
        isClosable: true,
      });
    }
  };

  return (
    <Center py={6}>
      <Box
        w="xs"
        rounded={"sm"}
        my={5}
        mx={[0, 5]}
        overflow={"hidden"}
        bg="white"
        border={"1px"}
        borderColor="black"
        boxShadow={useColorModeValue("6px 6px 0 black", "6px 6px 0 cyan")}
      >
        <Box h={"200px"} borderBottom={"1px"} borderColor="black">
          <Img
            src={pokemonImage}
            roundedTop={"sm"}
            objectFit="cover"
            h="full"
            w="full"
            alt={"Blog Image"}
          />
        </Box>
        <Box p={4}>
          <Box
            bg={pokemon.type1.color ? pokemon.type1.color : "gray.600"}
            display={"inline-block"}
            px={2}
            py={1}
            color="white"
            mb={2}
          >
            <Text fontSize={"xs"} fontWeight="medium">
              {pokemon.type1.name}
            </Text>
          </Box>
          {pokemon?.type2 && (
            <Box
              bg={pokemon.type2.color ? pokemon.type2.color : "gray.600"}
              display={"inline-block"}
              px={2}
              py={1}
              color="white"
              mb={2}
              ml={2}
            >
              <Text fontSize={"xs"} fontWeight="medium">
                {pokemon.type2.name}
              </Text>
            </Box>
          )}
          <HStack justifyContent={"space-between"}>
            <Heading color={"black"} fontSize={"2xl"} noOfLines={1}>
              {pokemon.name}
            </Heading>
            <HStack color="black">
              <MdCatchingPokemon size={32} />
              <Text fontWeight="bold" fontSize="2xl">
                {pokemon.price}
              </Text>
            </HStack>
          </HStack>
          <PokemonStats
            hp={pokemon.stats.hp}
            attack={pokemon.stats.attack}
            defense={pokemon.stats.defense}
            specialAttack={pokemon.stats.specialAttack}
            specialDefense={pokemon.stats.specialDefense}
            speed={pokemon.stats.speed}
            total={pokemon.stats.total}
          />

          <Divider mt={8} mb={3} />
          <Box minH={"100px"}>
            <Text fontWeight="bold">Description: </Text>
            <Text>{pokemon.description}</Text>
          </Box>
          <HStack color="black" justifyContent={"space-evenly"}>
            {isOwned ? (
              <Tooltip label="Release">
                <Box
                  p={2}
                  border={"1px"}
                  alignItems="center"
                  roundedBottom={"sm"}
                  cursor="pointer"
                  onClick={handleRelease}
                >
                  <GiHand color="rgb(204, 42, 33)" size={32} />
                </Box>
              </Tooltip>
            ) : (
              ""
            )}
            {isCaught ? (
              <Badge colorScheme="orange">
                Trainer: {pokemon?.trainer?.username}
              </Badge>
            ) : (
              ""
            )}
            {!isAdmin && !isCaught ? (
              <Tooltip label="Catch">
                <Box
                  p={2}
                  border={"1px"}
                  alignItems="center"
                  roundedBottom={"sm"}
                  cursor="pointer"
                  onClick={handleCatch}
                >
                  <MdCatchingPokemon color="rgb(204, 42, 33)" size={32} />
                </Box>
              </Tooltip>
            ) : (
              ""
            )}
            {!isAdmin && !isFavorite ? (
              <Tooltip label="Wish">
                <Box
                  p={2}
                  border={"1px"}
                  alignItems="center"
                  roundedBottom={"sm"}
                  borderLeft={"1px"}
                  cursor="pointer"
                  onClick={handleWish}
                >
                  <AiFillStar color="rgb(248, 204, 6)" size={32} />
                </Box>
              </Tooltip>
            ) : (
              ""
            )}

            {!isAdmin && isFavorite ? (
              <Tooltip label="Dislike">
                <Box
                  p={2}
                  border={"1px"}
                  alignItems="center"
                  roundedBottom={"sm"}
                  cursor="pointer"
                  onClick={handleDislike}
                >
                  <LuStarOff color="rgb(248, 204, 6)" size={32} />
                </Box>
              </Tooltip>
            ) : (
              ""
            )}
          </HStack>
        </Box>
      </Box>
    </Center>
  );
}
