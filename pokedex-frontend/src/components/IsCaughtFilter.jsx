import { Switch } from "@chakra-ui/react";
import React from "react";

function IsCaughtFilter({ isCaught, setIsCaught, isDisabled }) {
  return (
    <Switch
      color={"white"}
      colorScheme="gray"
      value={isCaught}
      onChange={() => setIsCaught(!isCaught)}
      isDisabled={isDisabled}
    >
      {isCaught ? "Caught" : "Uncaught"}
    </Switch>
  );
}

export default IsCaughtFilter;
