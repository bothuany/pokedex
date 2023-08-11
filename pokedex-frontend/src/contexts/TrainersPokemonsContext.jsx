import { createContext, useState, useContext, useEffect } from "react";
import axios from "axios";
import dir from "../config/dir.json";
import { useUser } from "./UserContext";
import { useToast } from "@chakra-ui/react";

const TrainersPokemonsContext = createContext();

const TrainersPokemonsProvider = ({ children }) => {
  const toast = useToast();
  const [catchList, setCatchList] = useState([]);
  const [wishList, setWishList] = useState([]);

  const { user } = useUser();

  useEffect(() => {
    if (user && user.role === dir.roles.trainer && user.id) {
      const fetchCatchList = async () => {
        try {
          const config = {
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
              Authorization: `Bearer ${user.token}`,
            },
          };

          const { data } = await axios.get(
            dir.api + `/trainers/${user.id}/${dir.trainer.pageTypes.catchList}`,
            config
          );
          setCatchList(data.content);
        } catch (error) {
          toast({
            title: "Error Code: " + error?.response.data.code,
            description: error?.response.data.message,
            status: "error",
            duration: 5000,
            isClosable: true,
          });
        }
      };

      const fetchWishList = async () => {
        try {
          const config = {
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
              Authorization: `Bearer ${user.token}`,
            },
          };

          const { data } = await axios.get(
            dir.api + `/trainers/${user.id}/${dir.trainer.pageTypes.wishList}`,
            config
          );
          setWishList(data.content);
        } catch (error) {
          toast({
            title: "Error Code: " + error?.response.data.code,
            description: error?.response.data.message,
            status: "error",
            duration: 5000,
            isClosable: true,
          });
        }
      };

      fetchCatchList();
      fetchWishList();
    }
  }, [user]);

  const values = {
    catchList,
    setCatchList,
    wishList,
    setWishList,
  };
  return (
    <TrainersPokemonsContext.Provider value={values}>
      {children}
    </TrainersPokemonsContext.Provider>
  );
};

const useTrainersPokemons = () => useContext(TrainersPokemonsContext);

export { useTrainersPokemons, TrainersPokemonsProvider };
