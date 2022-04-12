FROM node as build

WORKDIR ultra

COPY . .

RUN ["yarn", "install"]

RUN ["yarn", "run", "build:ultra"]

FROM nginx:alpine
EXPOSE 80
COPY --from=build ultra/packages/leftindust/build /usr/share/nginx/html