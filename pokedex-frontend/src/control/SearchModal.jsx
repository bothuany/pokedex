import { Modal, ModalBody, ModalContent } from "@chakra-ui/react";
import React from "react";

function SearchModal({ isOpen, onClose, keyword, searchedClass }) {
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
          <SearchThings keyword={keyword} />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
}

export default SearchModal;
