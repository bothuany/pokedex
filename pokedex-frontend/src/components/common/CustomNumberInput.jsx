import React from "react";
import {
  FormLabel,
  FormErrorMessage,
  FormControl,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  NumberInput,
} from "@chakra-ui/react";

function CustomNumberInput({
  name,
  error,
  type,
  input,
  setInput,
  isDisabled,
  range,
}) {
  const handleInputChange = (valueString) => setInput(valueString);

  const isError = error !== "";
  return (
    <FormControl isInvalid={error} isDisabled={isDisabled}>
      <FormLabel>{name}</FormLabel>
      <NumberInput
        type={type}
        value={input}
        onChange={handleInputChange}
        min={range[0]}
        max={range[1]}
      >
        <NumberInputField />
        <NumberInputStepper>
          <NumberIncrementStepper />
          <NumberDecrementStepper />
        </NumberInputStepper>
      </NumberInput>
      {isError && <FormErrorMessage>{error}</FormErrorMessage>}
    </FormControl>
  );
}

export default CustomNumberInput;
