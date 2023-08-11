import React, { useEffect, useState } from "react";
import Field from "./common/Field";
import { HStack, VStack } from "@chakra-ui/react";
import { Button } from "@chakra-ui/react";
import { useToast } from "@chakra-ui/react";
import axios from "axios";
import Pokeball from "./common/Pokeball";
import SearchBar from "./common/SearchBar";
import dir from "../config/dir.json";
import { useUser } from "../contexts/UserContext";

function ManageTrainer({ operation }) {
  const [isSearchRequired, setIsSearchRequired] = useState(false);

  const toast = useToast();
  const { user } = useUser();

  const [name, setName] = useState("");
  const [currentUsername, setCurrentUsername] = useState("");
  const [currentId, setCurrentId] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [nameError, setNameError] = useState("");
  const [currentNameError, setCurrentNameError] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const [trainer, setTrainer] = useState({});

  const [isDisabled, setIsDisabled] = useState(false);

  useEffect(() => {
    setIsSearchRequired(operation === "Update" || operation === "Delete");
    setTrainer({});
    setUsername("");
    setEmail("");
    setPassword("");
    setName("");
  }, [operation]);

  useEffect(() => {
    if (trainer) {
      setName(trainer.name);
      setUsername(trainer.username);
      setEmail(trainer.email);
      setPassword(trainer.password);
      setCurrentUsername(trainer.username);
      setCurrentId(trainer.id);
    }
  }, [trainer]);

  useEffect(() => {
    if (operation === "Delete") {
      setIsDisabled(true);
    } else if (operation === "Update" && trainer?.username == null) {
      setIsDisabled(true);
    } else {
      setIsDisabled(false);
    }
  }, [operation, trainer]);

  const handleSubmit = async () => {
    setIsLoading(true);
    if (operation === "Add") {
      handleAddTrainer();
    }
    if (operation === "Update") {
      handleUpdateTrainer();
    }
    if (operation === "Delete") {
      handleDeleteTrainer();
    }

    setIsLoading(false);
  };

  const handleAddTrainer = async () => {
    if (!name) {
      setNameError("Name is required");
      return;
    }
    if (name) setNameError("");
    if (!username) {
      setUsernameError("Username is required");
      return;
    }
    if (username) setUsernameError("");
    if (!email) {
      setEmailError("Email is required");
      return;
    }
    if (email) setEmailError("");
    if (!password) {
      setPasswordError("Password is required");
      return;
    }
    if (password) setPasswordError("");

    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.post(
        dir.api + "/trainers",
        {
          name,
          username,
          email,
          password,
        },
        config
      );

      toast({
        title: "Created",
        description: "You have successfully created an trainer.",
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

  const handleUpdateTrainer = async () => {
    if (!name) {
      setNameError("Name is required");
      return;
    }
    if (name) setNameError("");
    if (!username) {
      setUsernameError("Username is required");
      return;
    }
    if (username) setUsernameError("");
    if (!email) {
      setEmailError("Email is required");
      return;
    }
    if (email) setEmailError("");
    if (!password) {
      setPasswordError("Password is required");
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
        dir.api + "/trainers/" + currentUsername,
        {
          name,
          username,
          email,
          password,
        },
        config
      );

      toast({
        title: "Created",
        description: "You have successfully updated an trainer.",
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

  const handleDeleteTrainer = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };
      const { data } = await axios.delete(
        dir.api + "/trainers/" + currentUsername,
        config
      );

      toast({
        title: "Deleted",
        description: "You have successfully deleted an trainer.",
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
          searchedClass="trainer"
          searchBy="username"
          setThing={setTrainer}
        />
      ) : (
        ""
      )}

      <HStack>
        {operation === "Update" || operation === "Delete" ? (
          <Field
            name="Current Username"
            error={currentNameError}
            type="currentUsername"
            input={currentUsername}
            setInput={setCurrentUsername}
            isDisabled={true}
          />
        ) : (
          ""
        )}
        <Field
          name="Username"
          error={usernameError}
          type="text"
          input={username}
          setInput={setUsername}
          isDisabled={isDisabled}
        />
        <Field
          name="Name"
          error={nameError}
          type="text"
          input={name}
          setInput={setName}
          isDisabled={isDisabled}
        />
      </HStack>
      <HStack>
        <Field
          name="Email"
          error={emailError}
          type="email"
          input={email}
          setInput={setEmail}
          isDisabled={isDisabled}
        />
        <Field
          name="Password"
          error={passwordError}
          type="password"
          input={password}
          setInput={setPassword}
          isDisabled={isDisabled}
        />
      </HStack>

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

export default ManageTrainer;
