import React, { useState } from "react";
import Navbar from "../components/common/Navbar";
import {
  Box,
  Container,
  Divider,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Tabs,
} from "@chakra-ui/react";
import ManagePokemon from "../components/ManagePokemon";
import ManageTrainer from "../components/ManageTrainer";
import ManageAdmin from "../components/ManageAdmin";
import ManageType from "../components/ManageType";
import dir from "../config/dir.json";

function AdminDashboard() {
  const [tabName, setTabName] = useState(dir.operations.add);
  return (
    <>
      <Navbar />
      <Container maxW="800px" centerContent>
        <Box
          p={4}
          bg="white"
          w="100%"
          m="40px 0 15px 0"
          borderRadius="lg"
          borderWidth="1px"
        >
          <Tabs isFitted variant="soft-rounded">
            <TabList>
              <Tab
                _selected={{ color: "white", bg: "green.500" }}
                onClick={() => setTabName(dir.operations.add)}
                key={1}
              >
                {dir.operations.add}
              </Tab>
              <Tab
                _selected={{ color: "white", bg: "blue.500" }}
                onClick={() => setTabName(dir.operations.update)}
                key={2}
              >
                {dir.operations.update}
              </Tab>
              <Tab
                _selected={{ color: "white", bg: "red.500" }}
                onClick={() => setTabName(dir.operations.delete)}
                key={3}
              >
                {dir.operations.delete}
              </Tab>
            </TabList>
          </Tabs>
          <Divider mt={2} mb={2} />
          <Tabs isFitted variant="soft-rounded" colorScheme="twitter">
            <TabList>
              <Tab key={4}>Manage Type</Tab>
              <Tab key={5}>Manage Pokemon</Tab>
              <Tab key={6}>Manage Trainer</Tab>
              <Tab key={7}>Manage Admin</Tab>
            </TabList>

            <TabPanels>
              <TabPanel>
                <ManageType operation={tabName} />
              </TabPanel>
              <TabPanel>
                <ManagePokemon operation={tabName} />
              </TabPanel>
              <TabPanel>
                <ManageTrainer operation={tabName} />
              </TabPanel>
              <TabPanel>
                <ManageAdmin operation={tabName} />
              </TabPanel>
            </TabPanels>
          </Tabs>
        </Box>
      </Container>
    </>
  );
}

export default AdminDashboard;
