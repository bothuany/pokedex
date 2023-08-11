import jwt from "jwt-decode";
import { createContext, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import dir from "../config/dir.json";
import axios from "axios";

const UserContext = createContext();

const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("userInfo") === null) {
      navigate("/login");
      return;
    }
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    const { sub, role } = jwt(userInfo?.token);
    setUser({ username: sub, role, token: userInfo?.token });
  }, [navigate]);

  useEffect(() => {
    if (user && user.email === undefined) {
      const fetchUser = async () => {
        const config = {
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: `Bearer ${user.token}`,
          },
        };

        try {
          const { data } = await axios.get(
            dir.api +
              `/${user?.role?.substring(5).toLowerCase()}s/username/${
                user.username
              }`,
            config
          );
          setUser({ ...user, ...data });
        } catch (error) {}
      };
      fetchUser();
    }
  }, [user]);

  const values = { user, setUser };
  return <UserContext.Provider value={values}>{children}</UserContext.Provider>;
};

const useUser = () => useContext(UserContext);

export { useUser, UserProvider };
