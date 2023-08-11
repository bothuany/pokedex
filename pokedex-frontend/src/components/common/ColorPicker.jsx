import { FormControl, FormLabel, Input } from "@chakra-ui/react";
import React from "react";

const ColorPicker = ({ selectedColor, setSelectedColor, isDisabled }) => {
  const handleColorChange = (event) => {
    setSelectedColor(event.target.value);
  };

  return (
    <FormControl isDisabled={isDisabled}>
      <FormLabel>Color</FormLabel>
      <Input
        maxW={"70px"}
        type="color"
        value={selectedColor}
        onChange={handleColorChange}
      />
    </FormControl>
  );
};

export default ColorPicker;
