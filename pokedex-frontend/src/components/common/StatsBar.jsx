import React from "react";
import dir from "../../config/dir.json";
import { Box } from "@chakra-ui/react";

const statsBarStyles = {
  statsBar: {
    boxSizing: "border-box",
    width: "100%",
    height: "20px",
    padding: "5px",
    background: "#ddd",
    borderRadius: "5px",
    position: "relative",
    fontSize: "10px",
    color: "white",
  },
  bar: {
    background: "black",
    width: "100%",
    height: "10px",
    position: "relative",
    transition: "width 0.6s cubic-bezier(0.47, 1.64, 0.41, 0.8)",
  },
  hit: {
    background: "rgba(223, 87, 87, 0.6)",
    position: "absolute",
    top: "5px",
    right: "5px",
    bottom: "5px",
    left: "5px",
    transition: "width 0.5s linear",
  },
};

function StatsBar({ maxStat = dir.maxStat, stat = 100, color = "black" } = {}) {
  const barWidth = (stat / maxStat) * 100;

  return (
    <Box style={statsBarStyles.statsBar}>
      <Box
        style={{
          ...statsBarStyles.bar,
          width: `${barWidth}%`,
          background: `${color}`,
        }}
      ></Box>
      <Box style={{ ...statsBarStyles.hit, width: `${0}%` }}></Box>

      <Box
        style={{
          position: "absolute",
          top: "5px",
          left: 0,
          right: 0,
          textAlign: "center",
        }}
      >
        {stat} / {maxStat}
      </Box>
    </Box>
  );
}

export default StatsBar;
