import { Box, Button, Container, VStack, useToast } from "@chakra-ui/react";
import Navbar from "../components/common/Navbar";
import React, { useState } from "react";
import Field from "../components/common/Field";
import axios from "axios";
import { useUser } from "../contexts/UserContext";
import dir from "../config/dir.json";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Settings() {
  const { user, setUser } = useUser();
  const navigate = useNavigate();
  const toast = useToast();

  const [username, setUsername] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const [password, setPassword] = useState("");

  const [usernameError, setUsernameError] = useState("");
  const [nameError, setNameError] = useState("");
  const [emailError, setEmailError] = useState("");

  const [passwordError, setPasswordError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const [roleName, setRoleName] = useState("");

  useEffect(() => {
    setUsername(user?.username);
    setName(user?.name);
    setEmail(user?.email);

    if (user?.role === dir.roles.admin) {
      setRoleName("admin");
    } else if (user?.role === dir.roles.trainer) {
      setRoleName("trainer");
    }
  }, [user]);

  const handleUpdate = async () => {
    if (!username || !name || !email) {
      setUsernameError(username ? "" : "Username is required");
      setNameError(name ? "" : "Name is required");
      setEmailError(email ? "" : "Email is required");

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
        dir.api + `/${roleName}s`,
        {
          username,
          name,
          email,
          password: password,
        },
        config
      );

      toast({
        title: "Success",
        description: `${user.username} has been updated`,
        status: "success",
        duration: dir.toast.duration,
        isClosable: true,
      });

      setIsLoading(false);
      localStorage.removeItem("userInfo");
      setUser(null);
      navigate("/login");
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

  const handleDelete = async () => {
    if (!username) {
      setUsernameError(username ? "" : "Username is required");

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

      const { data } = await axios.delete(dir.api + `/${roleName}s`, config);

      toast({
        title: "Success",
        description: `${user.username} has been deleted`,
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
    setIsLoading(false);
    localStorage.removeItem("userInfo");
    setUser(null);
    navigate("/login");
  };

  return (
    <>
      <Navbar />
      <Container maxW="550px" centerContent>
        <Box
          p={4}
          bg="white"
          w="100%"
          m="40px 0 15px 0"
          borderRadius="lg"
          borderWidth="1px"
        >
          <VStack spacing="20px">
            <Field
              name="Username"
              error={usernameError}
              type="username"
              input={username}
              setInput={setUsername}
            />
            <Field
              name="Name"
              error={nameError}
              type="name"
              input={name}
              setInput={setName}
            />
            <Field
              name="Email"
              error={emailError}
              type="email"
              input={email}
              setInput={setEmail}
            />

            <Field
              name="Password"
              error={passwordError}
              type="password"
              input={password}
              setInput={setPassword}
            />

            <Button
              width="100%"
              height={50}
              textColor={"rgb(65, 93, 171)"}
              isLoading={isLoading}
              colorScheme="yellow"
              variant="solid"
              onClick={handleUpdate}
            >
              Save
            </Button>
            <Button
              width="100%"
              height={50}
              textColor={"white"}
              isLoading={isLoading}
              colorScheme="red"
              variant="solid"
              onClick={handleDelete}
            >
              Delete
            </Button>
          </VStack>
        </Box>
      </Container>
    </>
  );
}

export default Settings;
