import { SearchIcon, SmallCloseIcon } from "@chakra-ui/icons";
import {
  Flex,
  IconButton,
  Input,
  InputGroup,
  InputLeftElement,
  InputRightElement,
} from "@chakra-ui/react";
import React from "react";

function BasicSearchBar({ searchKeyword, setSearchKeyword }) {
  function onChangeInput(event) {
    setSearchKeyword(event.target.value);
  }

  return (
    <Flex justifyContent={"center"}>
      <InputGroup width="65%">
        <InputLeftElement
          pointerEvents="none"
          children={<SearchIcon color="gray.700" />}
        />
        <Input
          color="gray.700"
          onChange={onChangeInput}
          type="text"
          placeholder={`Search`}
          value={searchKeyword}
        />

        <InputRightElement>
          {searchKeyword.length > 0 && (
            <IconButton
              height="80%"
              mr="5px"
              color="gray.700"
              aria-label="Clear search box"
              icon={<SmallCloseIcon />}
              variant="unstyled"
              onClick={() => {
                setSearchKeyword("");
              }}
            />
          )}
        </InputRightElement>
      </InputGroup>
    </Flex>
  );
}

export default BasicSearchBar;
