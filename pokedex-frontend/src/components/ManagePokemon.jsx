import React, { useEffect, useState } from "react";
import Field from "./common/Field";
import {
  Divider,
  FormLabel,
  HStack,
  Input,
  InputGroup,
  InputLeftAddon,
  VStack,
} from "@chakra-ui/react";
import { Button } from "@chakra-ui/react";
import { useToast } from "@chakra-ui/react";
import axios from "axios";
import Pokeball from "./common/Pokeball";
import CustomNumberInput from "../components/common/CustomNumberInput";
import CustomDropdownInput from "../components/common/CustomDropdownInput";
import { useType } from "../contexts/TypeContext";
import SearchBar from "./common/SearchBar";
import dir from "../config/dir.json";
import { useUser } from "../contexts/UserContext";

function ManagePokemon({ operation }) {
  const [isSearchRequired, setIsSearchRequired] = useState(false);

  const toast = useToast();

  const { types } = useType();
  const { user } = useUser();

  const [name, setName] = useState("");
  const [currentName, setCurrentName] = useState("");
  const [currentId, setCurrentId] = useState("");
  const [price, setPrice] = useState(0);
  const [hp, setHp] = useState(0);
  const [attack, setAttack] = useState(0);
  const [defense, setDefense] = useState(0);
  const [specialAttack, setSpecialAttack] = useState(0);
  const [specialDefense, setSpecialDefense] = useState(0);
  const [speed, setSpeed] = useState(0);
  const [image, setImage] = useState({});
  const [description, setDescription] = useState("");
  const [type1, setType1] = useState("");
  const [type2, setType2] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [nameError, setNameError] = useState("");
  const [currentNameError, setCurrentNameError] = useState("");
  const [priceError, setPriceError] = useState("");
  const [statError, setStatError] = useState("");
  const [typeError, setTypeError] = useState("");
  const [imageError, setImageError] = useState("");
  const [pokemon, setPokemon] = useState({});

  const [isDisabled, setIsDisabled] = useState(false);

  useEffect(() => {
    setIsSearchRequired(operation === "Update" || operation === "Delete");
    setPokemon({});
    setName("");
    setPrice(0);
    setHp(0);
    setAttack(0);
    setDefense(0);
    setSpecialAttack(0);
    setSpecialDefense(0);
    setSpeed(0);
    setDescription("");
    setType1("");
    setType2("");
  }, [operation]);

  useEffect(() => {
    if (pokemon) {
      setName(pokemon.name);
      setPrice(pokemon.price);
      setHp(pokemon.stats?.hp);
      setAttack(pokemon.stats?.attack);
      setDefense(pokemon.stats?.defense);
      setSpecialAttack(pokemon.stats?.specialAttack);
      setSpecialDefense(pokemon.stats?.specialDefense);
      setSpeed(pokemon.stats?.speed);
      setDescription(pokemon.description);
      setType1(pokemon.type1?.name);
      setType2(pokemon.type2?.name);
      setCurrentName(pokemon.name);
      setCurrentId(pokemon.id);
    }
  }, [pokemon]);

  useEffect(() => {
    if (operation === "Delete") {
      setIsDisabled(true);
    } else if (operation === "Update" && pokemon?.name == null) {
      setIsDisabled(true);
    } else {
      setIsDisabled(false);
    }
  }, [operation, pokemon]);

  const handleImageChange = (event) => {
    setImage({ file: event.target.files[0] });
  };

  const handleSubmit = async () => {
    setIsLoading(true);
    if (operation === "Add") {
      handleAddPokemon();
      handleImageUpload();
    }
    if (operation === "Update") {
      handleUpdatePokemon();
    }
    if (operation === "Delete") {
      handleDeletePokemon();
    }

    setIsLoading(false);
  };

  useEffect(() => {
    console.log(pokemon);
  }, [pokemon]);

  const handleImageUpload = () => {
    if (image.file) {
      const formData = new FormData();
      formData.append("image", image?.file, image?.file.name);
      formData.append("publicId", `public_id_${name}`);

      const config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${user.token}`,
        },
      };

      axios
        .post(`${dir.api}/images/upload`, formData, config)
        .then((response) => {
          console.log("Image uploaded successfully!", response);
        })
        .catch((error) => {
          console.error("Error uploading image:", error);
        });
    }
  };

  const handleAddPokemon = async () => {
    if (!name) {
      setNameError("Name is required");
      return;
    }
    if (name) setNameError("");
    if (!price) {
      setPriceError("Price is required");
      return;
    }

    if (!type1) {
      setTypeError("Type 1 is required");
      return;
    }
    if (type1) setTypeError("");

    if (type1 === type2) {
      setTypeError("Type 1 and Type 2 cannot be the same");
      return;
    }

    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.post(
        dir.api + "/pokemons",
        {
          name,
          price,
          stats: {
            hp,
            attack,
            defense,
            specialAttack,
            specialDefense,
            speed,
          },
          cloudinaryPublicId: `public_id_${name}`,
          description,
          type1Name: type1,
          type2Name: type2 ? type2 : null,
        },
        config
      );

      toast({
        title: "Created",
        description: "You have successfully created a pokemon.",
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

  const handleUpdatePokemon = async () => {
    if (!name) {
      setNameError("Name is required");
      return;
    }
    if (name) setNameError("");
    if (!currentName) {
      setCurrentNameError("Current name is required");
      return;
    }
    if (currentName) setCurrentNameError("");
    if (!price) {
      setPriceError("Price is required");
      return;
    }

    if (!type1) {
      setTypeError("Type 1 is required");
      return;
    }
    if (type1) setTypeError("");

    if (type1 === type2) {
      setTypeError("Type 1 and Type 2 cannot be the same");
      return;
    }

    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.put(
        dir.api + "/pokemons/" + currentId,
        {
          name,
          price,
          stats: {
            hp,
            attack,
            defense,
            specialAttack,
            specialDefense,
            speed,
          },
          description,
          type1Name: type1,
          type2Name: type2 ? type2 : null,
        },
        config
      );

      toast({
        title: "Created",
        description: "You have successfully updated a pokemon.",
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

  const handleDeletePokemon = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.delete(
        dir.api + "/pokemons/" + currentId,
        config
      );

      toast({
        title: "Deleted",
        description: "You have successfully deleted a pokemon.",
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
    <VStack spacing="20px">
      {isSearchRequired ? (
        <SearchBar
          searchedClass="pokemon"
          searchBy="name"
          setThing={setPokemon}
        />
      ) : (
        ""
      )}

      <HStack>
        {operation === "Update" || operation === "Delete" ? (
          <Field
            name="Current Name"
            error={currentNameError}
            type="text"
            input={currentName}
            setInput={setCurrentName}
            isDisabled={true}
          />
        ) : (
          ""
        )}
        <Field
          name="Name"
          error={nameError}
          type="text"
          input={name}
          setInput={setName}
          isDisabled={isDisabled}
          maxLength={15}
        />
        <CustomNumberInput
          name="Price"
          error={priceError}
          type="number"
          input={price}
          setInput={setPrice}
          isDisabled={isDisabled}
          range={[1, 1000000]}
        />
      </HStack>
      <Divider />
      <VStack>
        <HStack>
          <CustomNumberInput
            name="HP"
            error={statError}
            type="number"
            input={hp}
            setInput={setHp}
            isDisabled={isDisabled}
            range={[1, 255]}
          />
          <CustomNumberInput
            name="Attack"
            error={statError}
            type="number"
            input={attack}
            setInput={setAttack}
            isDisabled={isDisabled}
            range={[1, 255]}
          />
          <CustomNumberInput
            name="Defense"
            error={statError}
            type="number"
            input={defense}
            setInput={setDefense}
            isDisabled={isDisabled}
            range={[1, 255]}
          />
        </HStack>
        <HStack>
          <CustomNumberInput
            name="Special Attack"
            error={statError}
            type="number"
            input={specialAttack}
            setInput={setSpecialAttack}
            isDisabled={isDisabled}
            range={[1, 255]}
          />
          <CustomNumberInput
            name="Special Defense"
            error={statError}
            type="number"
            input={specialDefense}
            setInput={setSpecialDefense}
            isDisabled={isDisabled}
            range={[1, 255]}
          />
          <CustomNumberInput
            name="Speed"
            error={statError}
            type="number"
            input={speed}
            setInput={setSpeed}
            isDisabled={isDisabled}
            range={[1, 255]}
          />
        </HStack>

        {operation === dir.operations.add ? (
          <>
            <label
              class="block mb-2 text-sm font-medium text-gray-900  "
              for="file_input"
            ></label>
            <input
              class={`block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none ${
                isDisabled ? "opacity-50" : ""
              }
          ${isDisabled ? "cursor-not-allowed" : "hover:border-gray-400"}`}
              aria-describedby="file_input_help"
              id="file_input"
              type="file"
              onChange={handleImageChange}
              accept="image/png, image/jpeg, image/jpg, image/gif, image/svg+xml"
            />
            <p class="mt-1 text-sm text-gray-500 " id="file_input_help">
              SVG, PNG, JPG or GIF (MAX. 800x400px).
            </p>
          </>
        ) : (
          ""
        )}

        <Field
          name="Description"
          error={""}
          type="text"
          input={description}
          setInput={setDescription}
          isDisabled={isDisabled}
          maxLength={100}
        />
        <Divider />

        <HStack>
          <CustomDropdownInput
            name="Type 1"
            error={typeError}
            input={type1}
            setInput={setType1}
            options={types.map((type) => type?.name)}
            isDisabled={isDisabled}
          />
          <CustomDropdownInput
            name="Type 2"
            error={typeError}
            input={type2}
            setInput={setType2}
            options={types.map((type) => type?.name)}
            isDisabled={isDisabled}
          />
        </HStack>
      </VStack>
      <Button
        leftIcon={<Pokeball turningActive={true} />}
        width="100%"
        height={50}
        textColor={"rgb(65, 93, 171)"}
        isLoading={isLoading}
        colorScheme="yellow"
        variant="solid"
        onClick={handleSubmit}
        isDisabled={isDisabled && operation !== "Delete"}
      >
        {operation ? operation : "Add"}
      </Button>
    </VStack>
  );
}

export default ManagePokemon;
