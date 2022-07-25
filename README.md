# SnakeServer

This is the web platform for Snake Reborn game. The game itself pushes all the match results to this server, so you can view the statistics.

This project is born as a very simple exercise during university.
In this version I just updated all libreries from old/incompatible/deprecated versions and removed referencies to the previous university project.

This project is based on Spring Boot framework.

DEVELOPMENT:\
To build your development environment you'll need:
- A Spring-capable IDE (the Eclipse-based Spring Tools, or the one I'm using VSCode with the right Spring plugins)
- A JRE/JDK with the same version as specified in pom.xml under properties -> java.version
- A Postgres server. Check compatibility between versions of Postgres and libreries
  - The credentials for connecting to the dbms are specified in src/main/resources/application.properties, along with the database url
  - Make sure that the dbms contains a database with the same name as specified in the url. The database can be empty, since the code will create all the required data automatically

DEPLOY:\
//todo
