import React from "react";
import {
  Box,
  Flex,
  Avatar,
  HStack,
  Text,
  IconButton,
  Button,
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  MenuDivider,
  useDisclosure,
  useColorModeValue,
  Stack,
  VStack,
} from "@chakra-ui/react";
import { HamburgerIcon, CloseIcon, SettingsIcon } from "@chakra-ui/icons";
import trainerAvatar from "../../assets/imgs/trainer-avatar.jpg";
import adminAvatar from "../../assets/imgs/admin-avatar.png";
import { useUser } from "../../contexts/UserContext";
import { FiLogOut } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import dir from "../../config/dir.json";
import { MdCatchingPokemon } from "react-icons/md";

const NavLink = (props) => {
  const { children } = props;

  return (
    <Box
      as="a"
      px={2}
      py={1}
      rounded={"md"}
      color={"gray.100"}
      _hover={{
        textDecoration: "none",
        bg: useColorModeValue("gray.600", "gray.600"),
      }}
      href={children.link}
    >
      {children.name}
    </Box>
  );
};

export default function Navbar() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const navigate = useNavigate();
  const { user } = useUser();

  const avatar = user?.role === dir.roles.admin ? adminAvatar : trainerAvatar;
  let role = user?.role;

  let Links = [];

  if (role === dir.roles.admin) {
    Links = [
      {
        name: "Dashboard",
        link: "/dashboard",
      },
      {
        name: "Pokemons",
        link: "/",
      },
    ];
  } else if (role === dir.roles.trainer) {
    Links = [
      {
        name: "Pokemons",
        link: "/",
      },
      {
        name: "Wish List",
        link: "/wish-list",
      },
      {
        name: "Catch List",
        link: "/catch-list",
      },
    ];
  }

  const handleLogout = () => {
    localStorage.removeItem("userInfo");
    window.location.reload();
  };

  return (
    <>
      <Box bg={"rgb(27, 36, 48)"} px={4}>
        <Flex h={16} alignItems={"center"} justifyContent={"space-between"}>
          <IconButton
            size={"md"}
            icon={isOpen ? <CloseIcon /> : <HamburgerIcon />}
            aria-label={"Open Menu"}
            display={{ md: "none" }}
            onClick={isOpen ? onClose : onOpen}
          />
          <HStack spacing={8} alignItems={"center"}>
            <Text
              className="pokemon-header"
              fontSize={32}
              mb={2}
              onClick={() => navigate("/")}
              cursor="pointer"
            >
              Pokédex
            </Text>
            <HStack
              as={"nav"}
              spacing={4}
              display={{ base: "none", md: "flex" }}
            >
              {Links.map((link) => (
                <NavLink key={link}>{link}</NavLink>
              ))}
            </HStack>
          </HStack>
          <Flex alignItems={"center"}>
            {user?.role === dir.roles.trainer ? (
              <HStack mr={5}>
                <Text color={"gray.100"} fontWeight="bold">
                  Balance:
                </Text>
                <Text color={"gray.100"}>{user?.balance}</Text>
                <MdCatchingPokemon color="white" size={32} />
              </HStack>
            ) : (
              ""
            )}
            <VStack mr={5}>
              <HStack>
                <Text color={"gray.100"} fontWeight="bold">
                  Username:
                </Text>
                <Text color={"gray.100"}>{user?.username}</Text>
              </HStack>
              <HStack>
                <Text color={"gray.100"} fontWeight="bold">
                  Role:
                </Text>
                <Text color={"gray.100"}>
                  {user?.role?.charAt(5)}
                  {user?.role?.substring(6).toLowerCase()}
                </Text>
              </HStack>
            </VStack>

            <Menu>
              <MenuButton
                as={Button}
                rounded={"full"}
                variant={"link"}
                cursor={"pointer"}
                minW={0}
              >
                <Avatar size={"md"} src={avatar} />
              </MenuButton>
              <MenuList>
                <MenuItem
                  icon={<SettingsIcon />}
                  onClick={() => navigate("/settings")}
                  key={"profile-settings"}
                >
                  Profile Settings
                </MenuItem>
                <MenuDivider />
                <MenuItem
                  icon={<FiLogOut />}
                  onClick={handleLogout}
                  key={"logout"}
                >
                  Logout
                </MenuItem>
              </MenuList>
            </Menu>
          </Flex>
        </Flex>

        {isOpen ? (
          <Box pb={4} display={{ md: "none" }}>
            <Stack as={"nav"} spacing={4}>
              {Links.map((link) => (
                <NavLink key={link}>{link}</NavLink>
              ))}
            </Stack>
          </Box>
        ) : null}
      </Box>
    </>
  );
}
