import { Grid, GridItem, HStack, Heading } from "@chakra-ui/react";
import React from "react";
import {
  GiBiceps,
  GiCheckedShield,
  GiDrippingBlade,
  GiEdgedShield,
  GiGlassHeart,
  GiLeatherBoot,
  GiRelicBlade,
} from "react-icons/gi";

import StatsBar from "./common/StatsBar";
import dir from "../config/dir.json";
function PokemonStats({
  hp,
  attack,
  defense,
  specialAttack,
  specialDefense,
  speed,
  total,
}) {
  return (
    <Grid
      templateAreas={`"hp hpBar"
                  "attack attackBar"
                  "defense defenseBar"
                  "specialAttack specialAttackBar"
                  "specialDefense specialDefenseBar"
                  "speed speedBar"
                  "total totalBar"`}
      gridTemplateRows={"14% 14% 14% 14% 14% 14% 1fr"}
      gridTemplateColumns={"45% 1fr"}
      h="%100"
      w="%100"
      gap="1"
      color="blackAlpha.700"
      fontWeight="bold"
    >
      <GridItem area={"hp"}>
        <HStack>
          <GiGlassHeart size={20} color={"red"} />
          <Heading fontSize={"md"} noOfLines={1}>
            HP: {hp}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"hpBar"}>
        <StatsBar stat={hp} color={"red"} />
      </GridItem>
      <GridItem area={"attack"}>
        <HStack>
          <GiRelicBlade size={20} color={"rgb(220, 150, 0)"} />
          <Heading fontSize={"md"} noOfLines={1}>
            Attack: {attack}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"attackBar"}>
        <StatsBar stat={attack} color={"rgb(220, 150, 0)"} />
      </GridItem>
      <GridItem area={"defense"}>
        <HStack>
          <GiCheckedShield size={20} color={"rgb(0, 160, 0)"} />
          <Heading fontSize={"md"} noOfLines={1}>
            Defense: {defense}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"defenseBar"}>
        <StatsBar stat={defense} color={"rgb(0, 160, 0)"} />
      </GridItem>
      <GridItem area={"specialAttack"}>
        <HStack>
          <GiDrippingBlade size={20} color={"rgb(0, 0, 255)"} />
          <Heading fontSize={"md"} noOfLines={1}>
            Spe. Att.: {specialAttack}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"specialAttackBar"}>
        <StatsBar stat={specialAttack} color={"rgb(0, 0, 255)"} />
      </GridItem>
      <GridItem area={"specialDefense"}>
        <HStack>
          <GiEdgedShield size={20} color={"rgb(247, 102, 180)"} />
          <Heading fontSize={"md"} noOfLines={1}>
            Spe. Def.: {specialDefense}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"specialDefenseBar"}>
        <StatsBar stat={specialDefense} color={"rgb(247, 102, 180)"} />
      </GridItem>
      <GridItem area={"speed"}>
        <HStack>
          <GiLeatherBoot size={20} color={"rgb(161, 110, 250)"} />
          <Heading fontSize={"md"} noOfLines={1}>
            Speed: {speed}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"speedBar"}>
        <StatsBar stat={speed} color={"rgb(161, 110, 250)"} />
      </GridItem>
      <GridItem area={"total"}>
        <HStack>
          <GiBiceps size={20} color={"black"} />
          <Heading fontSize={"md"} noOfLines={1}>
            Total: {total}
          </Heading>
        </HStack>
      </GridItem>
      <GridItem area={"totalBar"}>
        <StatsBar
          stat={total}
          maxStat={dir.maxStat * 6}
          color={"rgb(49, 46, 40)"}
        />
      </GridItem>
    </Grid>
  );
}

export default PokemonStats;
