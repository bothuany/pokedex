import React, { useEffect, useState } from "react";
import Field from "./common/Field";
import { VStack } from "@chakra-ui/react";
import { Button } from "@chakra-ui/react";
import { useToast } from "@chakra-ui/react";

import axios from "axios";
import Pokeball from "./common/Pokeball";

import { useType } from "../contexts/TypeContext";
import SearchBar from "./common/SearchBar";
import dir from "../config/dir.json";
import { useUser } from "../contexts/UserContext";
import ColorPicker from "./common/ColorPicker";

function ManageType({ operation }) {
  const [isSearchRequired, setIsSearchRequired] = useState(false);
  const { types, updateTypes, setTypes } = useType();
  const { user } = useUser();

  const toast = useToast();
  const [name, setName] = useState("");
  const [color, setColor] = useState("#000000");
  const [currentName, setCurrentName] = useState("");
  const [currentId, setCurrentId] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [nameError, setNameError] = useState("");
  const [currentNameError, setCurrentNameError] = useState("");

  const [type, setType] = useState({});

  const [isDisabled, setIsDisabled] = useState(false);

  useEffect(() => {
    setIsSearchRequired(operation === "Update" || operation === "Delete");
    setType({});
    setName("");
  }, [operation]);

  useEffect(() => {
    if (type) {
      setName(type.name);
      setCurrentName(type.name);
      setCurrentId(type.id);
    }
  }, [type]);

  useEffect(() => {
    if (operation === "Delete") {
      setIsDisabled(true);
    } else if (operation === "Update" && type?.name == null) {
      setIsDisabled(true);
    } else {
      setIsDisabled(false);
    }
  }, [operation, type]);

  const handleSubmit = async () => {
    setIsLoading(true);
    if (operation === "Add") {
      handleAddType();
      setTypes([...types, { name }]);
    }
    if (operation === "Update") {
      handleUpdateType();
    }
    if (operation === "Delete") {
      handleDeleteType();
    }
    updateTypes();

    setIsLoading(false);
  };

  const handleAddType = async () => {
    if (!name) {
      setNameError("Name is required");
      return;
    }
    if (name) setNameError("");

    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.post(
        dir.api + "/types",
        {
          name,
          color,
        },
        config
      );

      toast({
        title: "Created",
        description: "You have successfully created a type.",
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

  const handleUpdateType = async () => {
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

    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };
      const { data } = await axios.put(
        dir.api + "/types/" + currentId,
        {
          name,
          color,
        },
        config
      );

      toast({
        title: "Updated",
        description: "You have successfully updated a type.",
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
  const handleDeleteType = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.delete(
        dir.api + "/types/" + currentId,
        config
      );

      toast({
        title: "Deleted",
        description: "You have successfully deleted a type.",
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
        <SearchBar searchedClass="type" searchBy="name" setThing={setType} />
      ) : (
        ""
      )}
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
        maxLength={8}
      />
      <ColorPicker
        selectedColor={color}
        setSelectedColor={setColor}
        isDisabled={isDisabled}
      />

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
        {operation}
      </Button>
    </VStack>
  );
}

export default ManageType;
