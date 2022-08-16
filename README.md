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
  - The credentials for connecting to the dbms are specified in src/main/resources/application.properties or application-dev.properties, along with the database url
  - Make sure that the dbms contains a database with the same name as specified in the url. The database can be empty, since hibernate will create or update the schema automatically

You can run the webapp inside the IDE for debugging. It will use an integrated server listening on the port specified under server.port in application.properties file.

DEPLOY:\
This webapp can be deployed to an instance of Tomcat using the tomcat7 for maven plugin.
This plugin supports Tomcat7 or Tomcat8. Versions of Tomcat above 8 will not work.
You must specify the Tomcat server identifier and credentials in the maven config file %USER_DIR%\.m2\settings.xml
This is a sample of the configuration:

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		<server>
			<id>TomcatServer</id>
			<username>user-with-manager-script-role</username>
			<password>pwd</password>
		</server>
	</servers>
</settings>

Note that Tomcat users must be configured properly. The config file is <tomcat-install-dir>\conf\tomcat-users.xml
A good configuration could be the following:

<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="pwd1" roles="manager-gui" />
<user username="admin-script" password="pwd2" roles="manager-script" />
</tomcat-users>

It is necessary to change the max file size you can upload during the deploy operation. This can be done in <tomcat-install-dir>\webapps\manager\WEB-INF\web.xml. This info is specified under <multipart-config> element.

If you want to deploy from a remote ip (other than localhost), you must tell Tomcat to allow connections from other ips.
Go to file <tomcat-install-dir>\manager\META-INF\context.xml and modify the <Valve> element. If allowing connections from any ip is ok, you can comment/remove the entire element.

In the pom.xml file you have to adjsut the tomcat7 plugin configuration to point at the desired target: server url, server identifier (same as %USER_DIR%\.m2\settings.xml maven conf file) and the final web path.