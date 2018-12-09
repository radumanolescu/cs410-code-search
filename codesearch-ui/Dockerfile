FROM node:8.14-alpine
RUN npm install -g yarn vue-cli
RUN mkdir /app
ADD . /app
WORKDIR /app
RUN yarn build
