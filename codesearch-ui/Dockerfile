FROM node:8.14-alpine
RUN apk add --update make
RUN npm install -g yarn vue-cli vue
RUN mkdir /app
ADD . /app
WORKDIR /app
RUN yarn install
