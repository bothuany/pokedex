import { Badge, Divider, Select, VStack } from "@chakra-ui/react";
import React, { useEffect } from "react";
import { useType } from "../contexts/TypeContext";

function TypeFilter({ type, setType }) {
  const { types } = useType();

  return (
    <VStack>
      <Badge colorScheme="whiteAlpha" fontSize="2em">
        Type
      </Badge>
      <Divider />

      <Select
        defaultValue={""}
        onChange={(e) => setType(e.target.value)}
        color={"white"}
        maxW={"50%"}
      >
        <option style={{ background: "rgb(43, 55, 68)" }} value={""}>
          All
        </option>

        {types?.map((type, index) => (
          <option
            style={{ background: "rgb(43, 55, 68)" }}
            value={type.name}
            key={index}
          >
            {type.name}
          </option>
        ))}
      </Select>
    </VStack>
  );
}

export default TypeFilter;
