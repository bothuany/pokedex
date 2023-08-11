import { Badge, Checkbox, VStack } from "@chakra-ui/react";
import React from "react";
import IsCaughtFilter from "./IsCaughtFilter";
import { Divider } from "@chakra-ui/react";

function LimitingFilters({ isAll, setIsAll, isCaught, setIsCaught }) {
  return (
    <VStack>
      <Badge colorScheme="whiteAlpha" fontSize="2em">
        Limit
      </Badge>
      <Divider />
      <Checkbox
        color={"white"}
        colorScheme="white"
        isChecked={isAll}
        onChange={(e) => setIsAll(e.target.checked)}
      >
        All
      </Checkbox>
      <IsCaughtFilter
        isCaught={isCaught}
        setIsCaught={setIsCaught}
        isDisabled={isAll}
      />
    </VStack>
  );
}

export default LimitingFilters;
