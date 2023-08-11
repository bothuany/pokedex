import { createContext, useState, useContext, useEffect } from "react";
import axios from "axios";
import dir from "../config/dir.json";
import { useUser } from "./UserContext";

const TypeContext = createContext();

const TypeProvider = ({ children }) => {
  const [types, setTypes] = useState([]);
  const { user } = useUser();
  const [updateTypeVariable, setUpdateTypeVarialbe] = useState(false);

  const updateTypes = () => {
    setUpdateTypeVarialbe(!updateTypeVariable);
  };

  useEffect(() => {
    const interval = setInterval(() => {
      updateTypes();
    }, 300000); // 5 minutes
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    if (user) {
      const fetchTypes = async () => {
        const config = {
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: `Bearer ${user.token}`,
          },
        };
        try {
          const { data } = await axios.get(dir.api + "/types", config);
          setTypes(data.content);
        } catch (error) {}
      };

      fetchTypes();
    }
  }, [user, updateTypeVariable]);

  const values = { types, setTypes, updateTypes };
  return <TypeContext.Provider value={values}>{children}</TypeContext.Provider>;
};

const useType = () => useContext(TypeContext);

export { useType, TypeProvider };
