# Example Java Voting App

A simple distributed Java application running across multiple Docker containers.

## Getting started

Download [Docker Desktop](https://www.docker.com/products/docker-desktop) for Mac or Windows. [Docker Compose](https://docs.docker.com/compose) will be automatically installed. On Linux, make sure you have the latest version of [Compose](https://docs.docker.com/compose/install/).

This solution uses Java, Spring, SpringBoot, Node.js, Redis for messaging and Postgres for storage.

Run in this directory to build and run the app:

```shell
docker compose up
```

The `vote` app (api + static html page) will be running at [http://localhost:8080](http://localhost:5000), and the `results` will be at [http://localhost:5001](http://localhost:5001).

## TODO or in progress...
- app was tested and worked on AWS free tier t2.micro but due to limited resources from time to time server had to be restarted
- k8s deployment should be working soon...
- terraform or aws cloud formation will be provided soon...
- vote ui needs some tweeks and switch to new angular along with better REST api
- more units/integration testing would be nice...
- there are still more small issues that's why not published anywhere

## Notes
This is just an example of distributed application that uses java as main backend language.
Shows how different technologies could work together in distributed manner e.g. queues, persitence, ui, rest api and docker.
