# Use the official Node.js 19 alpine image as the base image
FROM node:19-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package.json .

# Install project dependencies
RUN npm install

# Copy the entire project to the container
COPY . .

# Start the React aoo
CMD [ "npm","start" ]