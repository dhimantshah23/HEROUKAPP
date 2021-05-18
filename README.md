# HEROUKAPP

#Assessment Testing Project

CONTENTS OF THIS FILE
---------------------
 * Introduction
 * Tools Used
 * Reporting
 * Logging
 * Test Data
 * Inheritance
 * CLI
 * FAQ
 * Maintainers
 

INTRODUCTION
------------
This is a REST Assured Framework to be used for API testing
Clone the project from GitLab(https://github.com/shahda/HEROUKAPP.git.git) and open it in your favourite IDE as a
Maven Project.

TOOLS USED
----------
Apache Maven 3.6.1
Java 1.8
Cucumber-JVM 1.2.5
JUnit 4.12
rest-assured 4.0.0

REPORTING
---------
Reports are written in the following directories after a successful run:
JSON - /target/cucumber-reports/report.html

LOGGING
-------
SLF4J is used for logging/debugging

TEST DATA
---------
config.properties is used to specify the environment and endPoint
Dynamic data is held in Example tables.

COMMAND LINE EXECUTION
----------------------
Go to the root folder of the project in terminal and type "mvn clean verify "
To run specific scenarios use command mvn test -Dcucumber.options="--tags @ScenarioTag"

MAINTAINERS
-----------
Current maintainers:
dhimant.shah - dhimantshah23@yahoo.com
