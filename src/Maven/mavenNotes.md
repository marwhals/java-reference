# Apache Maven

*Starting point*

[//]: # (    *TODO map knowledge form Maven to other build tools "gradle etc"*)

---

## Why choose Apache Maven

- Popular - other alternatives: Gradle, Ant, SBT
- Maven - So Stable Its Boring

### Advantages of Maven
- Quick Project Setup
  - Maven brings conventions over configuration, thus reducing setup time
- Popular are modular
- Mature Dependency Management
- Mature Project Build Life Cycle
- Robust plugin community

### Apache Maven - De Facto Standard
- Apache Maven has established 'standards' use by other build tools.
  - Maven Standard Directory Layout - In most part adapted by other tools such as gradle
  - Artifact Naming - Apache Maven helped establish how Java artifacts are named.
  - Artifact Repository - Apache Maven established the structure of artifact repositories
- Prior to Maven these 'standards' did not exist
- New build tools are compatible with these 'standards'

### Maven Disadvantages
- Projects are described in and XML document, constrained by an XML Schema
  - Some consider XML 'dated'
- Gradle uses a groovy DSL, which can offer greater build flexibility.
  - Rare the additional flexibility is needed

---

## Java Compiler Process

Source Files ---Java Compiler---> Class Files ---> The JVM Runtime

## Packaging Java Application

Many Class files ---Zip compression---> *.jar, *.war, *.ear ---Unzip---> JVM

## Java Packaging
- *.jar - Java ARchive - Zip file containing one or more Java class files.
- *.war - Web Application aRchive - Zip file containing web application. Includes one or more jar files, Java class files and web resources.
- *.ear - Enterprise aRchive - Zip file containing one or more WAR files
- Fat JAR (aka Uber JAR) - Executiable Jar containing all dependencies. (Use by Spring Boot)
- Docker Container - Docker Image containing runtime environment, JVM and Java package

## Java Deployment
- Simple JAR files are typically collection of class files to compose applications.
  - Typically, not a complete application
- WAR, EAR files are typically complete applications which are deployed to application servers
  - Tomcat / Weboss, Websphere etc
- Fat / Uber Jars are typically complete application which contain embedded application servers
  - Can be deployed stand alone
- Docker images are complete applications which can be deployed stand alone

---

## Maven 

### Maven Coordinates

- Maven Coordinates are used to identify artifacts
- Together, they indentify a 'location' in a Maven repository
  - `groupId` - Typically unique to an organisation. Often the organisation reverse domain is used. Not always. Can be just 'junit'
  - `artifiact` - typically the project name. A descriptor for the artifact
  - 'version' - refers to a specific version of the project
- `groupId` and 'version' can be inherited from a parent POM
 
Snapshot Versions
- SNAPSHOT suffix is an important qualifier to Maven behaviour
  - Tells Maven this is a development version
  - Not stable and Maven should check for newer versions
  - Maven will first check locally, then check remote repositories
  - By default, Maven will check remote repositories once per day
    - Option is configurable

### Maven Repositories

- Maven Repositories are a location where project artifacts are stored
- Types:
  - Local - Repository on local file system - <user home>/.m2/
  - Central - Public repository hosted by Maven community - https://repo1.maven.org/maven2
  - Remote - Other locations which can be public or private
    - JBoss, Oracle, Atlassian, Google Android
    - Private - hosted by companies for internal artifacts

Project -> Local in /.m2 -> Central / Others /// TODO turn into mermaid diagram

### Maven Wagon

- Maven Wagon is a unified Maven API
- Maven Wagon is a transport abstraction for abstraction for artifact and repository handling code
- Allows for different providers to be used for communications with Maven Repositories
- Majority of the time HTTP / HTTPs is used, no configuration is needed.
- Majority of the time HTTP / HTTPS is used, no configuration is needed
- In some corporate environments you may need to configure Wagon for Proxy settings

### Maven Wagon Providers

- Available providers
  - File
  - HTTP / HTTP Lightweight
  - FTP
  - SSH / SCP
  - WebDAV
  - SCM (In Progress)

### Maven POM

- POM - Project Object Model
- The pom.xml is a XML document which describes a Maven Project
- Must comply with the maven-4.0.0.xsd
  - XML Schema defining the 'rules' for the XML Document
- POMs can inherit properties from a parent POM
  - sub-modules also inherit
- 'Effective POM' - is the POM complete with inherited properties
  - `mvn help:effective-pom`

###  Maven Dependencies

- Dependencies - A dependency is an artifact which your Maven project depends upon
  - Typically, a jar or pom.
- Transitive Dependency - A dependency of an artifact which your project depends on.
  - Can go many MANY layers deep.
  - Cyclic dependencies are not supported (A depends on B, B depends on A)
- Dependency Management - allows project authors to specify the versions of the artifacts to be used.
- Dependency Mediation - Determines what version to use when multiple versions of the same dependency are encountered
  - 'Nearest Definition' in dependency tree is used
  - Example: A -> B; A -> D 2.0; B -> D 1.5
    - D version 2.0 would be included
  - Excluded Dependencies - Ability to excluding specific dependencies
  - Optional Dependencies - Ability to make dependencies optional. (excluded by default for downstream projects)

### Dependency Scope

- Compile - Default. Available on all class paths of project. Also, propagated to downstream projects.
- Provided - Like compile, but expected to be provided by JDK or container at runtime.
- Runtime - Not required for compile, but needed for runtime. On runtime and test class paths, not compile.
- Test - Only available on test classpath, not transitive.
- System - similar to provided but JAR is added to system explicitly. (via file path)
- Import - Imports dependency of POM

### Dependency are managed by the Maven Dependency Plugin

- Dependencies are managed by the Maven Dependency Plugin
- Important Goals:
  - dependency:tree - show the dependency tree. Useful for resolving conflicts
  - dependency:go-offline - Resolve all, prepare to go offline.
  - dependency:purge-local-repository - Clear artifacts from local repository
  - dependency:sources - get sources for all dependencies

### Maven Build Lifecycles

- Maven is based on the concept of build lifecycles
- A lifecycle is a pre-defined group of build steps called phases
- Each phase can be bound to one or more plugin goals
- Keep in mind all work done in Maven is done by plugins
- Life cycles and phrases provide the framework to call plugin goals in a sequence.
- Maven has three pre-defined lifecycles: clean, default, site
- `Clean`
  - Does a clean of the project, removes all build artifacts from working directory
  - Defined with plugin bindings
- `Default`
  - Does the build and deployment of your project
  - Defined without plugin bindings, binding are defined for each packaging.

### Site Build Lifecycle

- Site
  - Creates a website for your project
  - Defined with plugin bindings
  - Least used in the enterprise
  - See any of the Maven websites for examples
    - All are built using the Maven site lifecycle

### Clean Lifecycle
???

### Default Lifecycle - High-Level
- Validate - Verify project is correct
- Compile - Compile Source Code
- Test - Test Compiled Source code
- Package - Package compiled files to packaging type
- Verifying - Run Integration Tests
- Install - Install to local Maven Repository
- Deploy - Deploy to shared Maven Repository

### Default Lifecycle All phases / Maven Lifecycle Phases - *AI Assitant Generated*

[//]: # (TODO --- Check and fix)

| **Lifecycle**       | **Phase**               | **Description**                                                                                                         |
|---------------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------|
| **Clean**           | `pre-clean`             | Executes processes needed prior to the actual project cleaning.                                                         |
|                     | `clean`                 | Removes all files generated by the previous build.                                                                      |
|                     | `post-clean`            | Executes processes to finalize the cleaning process.                                                                    |
| **Default (Build)** | `validate`              | Validates the project is correct and all necessary information is available.                                            |
|                     | `compile`               | Compiles the source code of the project.                                                                                |
|                     | `test-compile`          | Compiles the test source code.                                                                                          |
|                     | `test`                  | Runs tests using a suitable unit testing framework. These tests should not require the code to be packaged or deployed. |
|                     | `package`               | Packages the compiled code into its distributable format (e.g., JAR, WAR).                                              |
|                     | `pre-integration-test`  | Executes processes needed prior to integration testing.                                                                 |
|                     | `integration-test`      | Processes and deploys the package if necessary into an environment where tests can be run.                              |
|                     | `post-integration-test` | Executes processes needed after integration testing.                                                                    |
|                     | `verify`                | Runs checks to verify the package is valid and meets quality criteria.                                                  |
|                     | `install`               | Installs the package into the local Maven repository for use as a dependency in other projects locally.                 |
|                     | `deploy`                | Copies the final package to the remote repository for sharing with other developers and projects.                       |
| **Site**            | `pre-site`              | Executes processes needed prior to generating the project site documentation.                                           |
|                     | `site`                  | Generates the project's site documentation.                                                                             |
|                     | `post-site`             | Executes processes needed after the site generation.                                                                    |
|                     | `site-deploy`           | Deploys the generated site documentation to the specified web server.                                                   |

### Default Lifecycle - JAR Packaging

- Phase: process-resources - Plugin: maven-resources-plugin : resources
- Phase: compile - Plugin: maven-compile-plugin : compile
- Phase: process-test-resources - Plugin: maven-resources-plugin : testResources
- Phase: test-compile - Plugins : maven-compiler-plugin : testCompile
- Phase: test - Plugin: maven-surefire-plugin : test           🔥🔥🔥🔥
- Phase: package - Plugin: maven-jar-plugin : jar
- Phase: install - Plugin: maven-install-plugin : install
- Phase: deploy - Plugin: maven-deploy-plugin : deploy

### Site Build Lifecycle

- Site Lifecycle Phases
  - Phase: Pre-site - Plugin: none
  - Phase: Site - Plugin: maven-site-plugin : site
  - Phase: Post-site - Plugin: none
  - Phase: Site-Deploy - Plugins: maven-site-plugin: deploy

### Maven Archetypes

- Maven Archetypes are project templates
- Apache Maven provides a variety of standard archetypes to serve as started for common Java projects
- Maven Archetypes are also available from a variety of 3rd parties
- Some of the archetypes are dated
  - i.e - J2EE

---