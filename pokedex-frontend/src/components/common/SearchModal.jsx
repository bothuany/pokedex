import { Modal, ModalBody, ModalContent } from "@chakra-ui/react";
import React from "react";
import SearchThings from "./SearchThings";

function SearchModal({
  isOpen,
  onClose,
  keyword,
  searchedClass,
  setThing,
  searchBy,
}) {
  return (
    <Modal
      blockScrollOnMount={false}
      isOpen={isOpen}
      onClose={onClose}
      trapFocus={false}
      scrollBehavior="inside"
    >
      <ModalContent maxHeight="50%">
        <ModalBody>
          <SearchThings
            keyword={keyword}
            searchedClass={searchedClass}
            searchBy={searchBy}
            setThing={setThing}
            onClose={onClose}
          />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
}

export default SearchModal;
