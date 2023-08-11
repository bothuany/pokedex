import React from "react";
import {
  FormLabel,
  FormErrorMessage,
  Input,
  FormControl,
} from "@chakra-ui/react";

function Field({ name, error, type, input, setInput, isDisabled, maxLength }) {
  const handleInputChange = (e) => setInput(e.target.value);

  const isError = error !== "";
  return (
    <FormControl isInvalid={isError} isDisabled={isDisabled}>
      <FormLabel>{name}</FormLabel>
      <Input
        type={type}
        value={input}
        onChange={handleInputChange}
        maxLength={maxLength ? maxLength : null}
      />
      {isError && <FormErrorMessage>{error}</FormErrorMessage>}
    </FormControl>
  );
}

export default Field;
