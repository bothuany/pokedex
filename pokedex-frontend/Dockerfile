# Use the official Node.js image
FROM node:18-alpine

# Set the working directory
WORKDIR /pokedex-frontend

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React app
RUN npm run build

# Expose the port your React app is running on
EXPOSE 3000

# Command to start the React app
CMD ["npm", "start"]
