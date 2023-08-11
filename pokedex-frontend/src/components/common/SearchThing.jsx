import React from "react";
import { Avatar, Box, Text, HStack } from "@chakra-ui/react";

function SearchThing({ name, id, setThing, data, onClose }) {
  return (
    <Box
      backgroundColor="rgb(237,242,247)"
      p="10px 5px"
      mt="3px"
      borderRadius="md"
      cursor="pointer"
      onClick={() => {
        setThing(data);
        onClose();
      }}
    >
      <HStack d="flex" justify="space-between">
        <Avatar size="sm" name={name} />
        <Text fontWeight={500}>{name}</Text>
      </HStack>
    </Box>
  );
}

export default SearchThing;
