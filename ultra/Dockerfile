FROM node as build
RUN ["npm", "install", "yarn"]
RUN ["yarn", "install"]
RUN ["yarn", "build"]

FROM nginx
COPY --from=build build /usr/share/nginx/html