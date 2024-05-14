### STAGE 1: Build ###
FROM node:20.13.1-alpine3.19 AS build
WORKDIR /usr/src/app
COPY . .
RUN npm install
RUN npm run build

### STAGE 2: Run ###
FROM nginx:stable-alpine3.19
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/frontend-trimons-dcim/browser /usr/share/nginx/html
EXPOSE 80
