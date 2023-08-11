import React from "react";
import {
  FormLabel,
  FormControl,
  Select,
  FormErrorMessage,
} from "@chakra-ui/react";

function CustomDropdownInput({
  name,
  error,
  input,
  setInput,
  options,
  isRequired,
  isDisabled,
}) {
  const handleInputChange = (e) => setInput(e.target.value);

  const isError = error !== "";
  return (
    <FormControl
      isRequired={isRequired}
      isInvalid={isError}
      isDisabled={isDisabled}
    >
      <FormLabel>{name}</FormLabel>
      <Select
        value={input}
        onChange={handleInputChange}
        placeholder={"Select " + name}
      >
        {options?.map((option, index) => {
          return (
            <option key={index} value={option}>
              {option}
            </option>
          );
        })}
      </Select>
      {isError && <FormErrorMessage>{error}</FormErrorMessage>}
    </FormControl>
  );
}

export default CustomDropdownInput;
