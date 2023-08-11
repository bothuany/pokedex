import React, { useState } from "react";
import {
  Box,
  Container,
  FormLabel,
  Image,
  Input,
  InputGroup,
  InputRightElement,
  Text,
  VStack,
  FormControl,
  IconButton,
} from "@chakra-ui/react";
import { Button } from "@chakra-ui/react";
import { useToast } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Field from "../components/common/Field";
import Pokeball from "../components/common/Pokeball";
import dir from "../config/dir.json";
import closedEyes from "../assets/imgs/closed-eyes.jpg";
import openEyes from "../assets/imgs/opened-eyes.jpg";
import { AiFillEye, AiFillEyeInvisible } from "react-icons/ai";

function Login() {
  const toast = useToast();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const [show, setShow] = useState(false);

  const handleSubmit = async () => {
    if (!username) {
      setUsernameError("Username is required");
      return;
    }
    if (username) setUsernameError("");

    if (!password) {
      setPasswordError("Password is required");
      return;
    }
    if (password) setPasswordError("");
    setIsLoading(true);
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      };

      const { data } = await axios.post(
        dir.api + "/auth/login",
        { username, password },
        config
      );

      localStorage.setItem("userInfo", JSON.stringify(data));

      navigate("/");
      toast({
        title: "Logged in",
        description: "You have successfully logged in.",
        status: "success",
        duration: dir.toast.duration,
        isClosable: true,
      });
    } catch (error) {
      toast({
        title: "Error",
        description: "Login failed.",
        status: "error",
        duration: dir.toast.duration,
        isClosable: true,
      });

      setUsernameError("Username or password is incorrect");
      setPasswordError("Username or password is incorrect");
    }

    setIsLoading(false);
  };

  return (
    <Container maxW="550px" centerContent>
      <Box
        display="flex"
        justifyContent="center"
        p={3}
        bg="white"
        w="100%"
        m="30px 0 15px 0"
        borderRadius="lg"
        borderWidth="1px"
      >
        <Text className="pokemon-header" fontSize={70} mb={8}>
          Pokédex
        </Text>
      </Box>
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
          <FormControl>
            <FormLabel>Password</FormLabel>
            <InputGroup size="md">
              <Input
                pr="4.5rem"
                type={show ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <InputRightElement width="4.5rem">
                <IconButton
                  isRound={true}
                  variant="solid"
                  colorScheme="blue"
                  aria-label="Done"
                  fontSize="20px"
                  size={"sm"}
                  onClick={() => setShow(!show)}
                  icon={show ? <AiFillEyeInvisible /> : <AiFillEye />}
                />
              </InputRightElement>
            </InputGroup>
          </FormControl>

          {show ? <Image src={closedEyes} /> : <Image src={openEyes} />}

          <Button
            leftIcon={<Pokeball turningActive={true} />}
            width="100%"
            height={50}
            textColor={"rgb(65, 93, 171)"}
            isLoading={isLoading}
            colorScheme="yellow"
            variant="solid"
            onClick={handleSubmit}
          >
            Login
          </Button>
        </VStack>
      </Box>
    </Container>
  );
}

export default Login;
