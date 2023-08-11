import { Box } from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import SearchThing from "./SearchThing";
import axios from "axios";
import dir from "../../config/dir.json";
import { useUser } from "../../contexts/UserContext";

function SearchThings({ keyword, searchedClass, setThing, searchBy, onClose }) {
  const [things, setThings] = useState([]);
  const { user } = useUser();

  const getThings = async () => {
    try {
      const config = {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
      };
      const { data } = await axios.get(
        dir.api + `/${searchedClass}s/search?${searchBy}=${keyword}`,
        config
      );
      setThings(data.content);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    getThings();
  }, [keyword]);

  return (
    <Box>
      {things.length > 0 &&
        things.map((thing, index) => (
          <SearchThing
            key={index}
            data={thing}
            name={thing[searchBy]}
            id={thing.id}
            setThing={setThing}
            onClose={onClose}
          />
        ))}
    </Box>
  );
}

export default SearchThings;
