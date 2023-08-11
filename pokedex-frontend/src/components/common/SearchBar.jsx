import { SearchIcon, SmallCloseIcon } from "@chakra-ui/icons";
import {
  IconButton,
  Input,
  InputGroup,
  InputLeftElement,
  InputRightElement,
  useDisclosure,
} from "@chakra-ui/react";
import React, { useState } from "react";
import SearchModal from "./SearchModal";

function SearchBar({ searchedClass, setThing, searchBy }) {
  const [searchKeyword, setSearchKeyword] = useState("");
  const {
    isOpen: isSearchOpen,
    onOpen: onSearchOpen,
    onClose: onSearchClose,
  } = useDisclosure();

  function onChangeInput(event) {
    setSearchKeyword(event.target.value);
  }
  return (
    <>
      <InputGroup width="65%">
        <InputLeftElement
          pointerEvents="none"
          children={<SearchIcon color="gray.700" />}
        />
        <Input
          color="gray.700"
          onFocus={() => {
            onSearchOpen();
          }}
          onChange={onChangeInput}
          type="text"
          placeholder={`Search ${searchedClass} by ${searchBy}`}
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
      <SearchModal
        isOpen={isSearchOpen}
        onOpen={onSearchOpen}
        onClose={onSearchClose}
        keyword={searchKeyword}
        setThing={setThing}
        searchedClass={searchedClass}
        searchBy={searchBy}
      />
    </>
  );
}

export default SearchBar;
