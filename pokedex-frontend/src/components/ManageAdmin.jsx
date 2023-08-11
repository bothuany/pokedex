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

function ManageAdmin({ operation }) {
  const [isSearchRequired, setIsSearchRequired] = useState(false);
  const { user } = useUser();

  const toast = useToast();
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

  const [admin, setAdmin] = useState({});

  const [isDisabled, setIsDisabled] = useState(false);

  useEffect(() => {
    setIsSearchRequired(operation === "Update" || operation === "Delete");
    setAdmin({});
    setUsername("");
    setEmail("");
    setPassword("");
    setName("");
  }, [operation]);

  useEffect(() => {
    if (admin) {
      setName(admin.name);
      setUsername(admin.username);
      setEmail(admin.email);
      setPassword(admin.password);
      setCurrentUsername(admin.username);
      setCurrentId(admin.id);
    }
  }, [admin]);

  useEffect(() => {
    if (operation === "Delete") {
      setIsDisabled(true);
    } else if (operation === "Update" && admin?.username == null) {
      setIsDisabled(true);
    } else {
      setIsDisabled(false);
    }
  }, [operation, admin]);

  const handleSubmit = async () => {
    setIsLoading(true);
    if (operation === "Add") {
      handleAddAdmin();
    }
    if (operation === "Update") {
      handleUpdateAdmin();
    }
    if (operation === "Delete") {
      handleDeleteAdmin();
    }

    setIsLoading(false);
  };

  const handleAddAdmin = async () => {
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
        dir.api + "/admins",
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
        description: "You have successfully created an admin.",
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

  const handleUpdateAdmin = async () => {
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
        dir.api + "/admins/" + currentUsername,
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
        description: "You have successfully updated an admin.",
        status: "success",
        duration: 5000,
        isClosable: true,
      });
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

  const handleDeleteAdmin = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };

      const { data } = await axios.delete(
        dir.api + "/admins/" + currentUsername,
        config
      );

      toast({
        title: "Deleted",
        description: "You have successfully deleted an admin.",
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
          searchedClass="admin"
          searchBy="username"
          setThing={setAdmin}
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

export default ManageAdmin;
