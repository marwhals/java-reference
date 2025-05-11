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

## Common Maven Plugins

### Overview Maven Lifecycle Plugins

Maven clean plugin
- Build Lifecycle - CLEAN
- Has only one goal - 'clean'
- Purpose is to remove files generated during the build process.
- By default, removes / target directory project root and submodule folders

Maven Compiler Plugin
- Build Lifecycle - DEFAULT
- Has two goals - compiler:compile,  compiler:testCompile
- By Default uses the compiler 'javax.tools.JavaCompiler'
  - Can be configured to use javac if needed
- Default source and target language levels are Java 1.6
  - Apache team encourages these values to be set

Maven Resource Plugin
- Build Lifecycle - DEFAULT
- Has 3 goals - resource:resources, resources: testResources, resources:copy-resources
- Purpose is to copy project resources to output directory (target dir)
- Can be configured for encoding, source and target directories
- Rather versatile configuration option for copying files during build processing

Maven Surefire Plugin
- Build Lifecycle - DEFAULT
- Has one goal: surefire:test
- The Surefire plugin is used to execute unit test of the project
- By default, supports JUnit 3, JUnit 4, JUnit 5 and TestNG
  - Cucumber runs under JUnit, Spock compiles to JUnit byte code
- By default, includes classes named
  - **/Test*.java; **/*Test.java; **/*Test.java; **/*TestCase.java

Maven jar plugin
- Build Lifecycle - DEFAULT
- Has two goals: jar:jar, jar:test-jar
- Purpose is to build jars from compiled artifacts and project resources
- Can be configured for custom manifests and to make executable jars.

Maven Deploy Plugin
- Build Lifecycle - DEFAULT
- Has two goals - deploy:deploy , deploy:deploy-file
- Purpose is to deploy project artifacts to remote Maven repositories
- Often done in CI
- Configuration is typically part of the Maven POM

Maven Site Plugin
- Build Lifecycle - SITE
- Has 7 goals:
  - site:site - Generate site for project
  - site:deploy - Deploy site via Wagon
  - site:run - Run Site locally using Jetty as web server
  - site:stage - generate site to a local staging directory
  - site:stage-deploy - Deploy site to remote staging location
  - site:attach-descriptor - adds site.xml (site map file used by search engines) to files for deployment
  - site:jar - bundles site into a jar for deployment to a repository
  - site:effective-site - generates the site.xml file

INSERT LINK to rebel labs cheat sheets 

---

## Overview of Alternate JVM Languages with Maven

- Most major alternate JVM languages can be compiled to Java byte code with Maven
- Typically done via plugins hooking into the 'compile' phase
- Each JVM flavor is a little different in terms of capabilities
- Most can be compiled with Java in the same project
- More than 2 languages in one project is generally not a good idea, nor supported 👀

## Testing with Maven

### Overview of Testing with Maven
- Unit Testing is completed by the Maven Surefire Plugin ⚡
- Integration Testing is completed by the Maven Failsafe Plugin
- Surefire / Failsafe support:
  - POJO tests
  - Junit version 3-5
  - TestNG

[//]: # (*TODO Research coding coverage with JaCoCo*)

---

## Multi-Module Projects

*Standard in enterprise projects*

[//]: # (TODO insert diagram)

### Maven Reactor
- Known simply as 'The Reactor'
- The reactor is what builds each module if the Maven project
- The reactor collects the modules to build
- The reactor will then run selected build lifecycles against each module
- The reactor will determine the build order of the modules
- By default The Reactor will build modules sequentially
  - Optionally can use threads to build modules in parallel

### Reactor Build Order
- Factors determining the build order used by Reactor:
  - Project dependencies - modules use by other modules in the project
  - Plugin declaration - i.e if module is a plugin used by other modules
  - Order of modules declared in 'modules' section of POM

### Multi-Module Code Smells
- Try to use modules only when needed
- Do not over-use modules
  - Multiple modules will slow down your build
- Code Smells with Modules
  - One class or interface in a module
  - Many small modules which could be combined

### Maven BOM
- BOM - Bill of Material
  - Manufacturing term meaning effectively a recipe of components required to produce a widget
- In Maven terminology, a BOM has become to mean dependencies declared within the dependencyMangement section of the POM
- Full qualified dependencies are listed under the dependencyManagement section of the POM
- Dependencies declared under the dependencies section of the POM inherit from dependencyManagement (version / packaging)
- Typically used to standardised versions

### Transitive Dependencies and BOM
- Any dependencies declared in the dependencyManagement section of the POM DO NOT become transitive dependencies for the artifact
- Dependencies declared under the dependencies section of the POM DO become transitive dependencies for the artifact
  - These DO inherit attributes such as version from dependencyMangement

### BOM Approaches
- Typical is to declare a dependencyMangement in the parent POM of the project
- Can also be defined in a remote parent POM
  - Spring Boot uses this approach
- Can be defined as a standalone POM, then "imported" into the dependenciesManagement section
  - Spring Cloud uses this approach

---

## Apache Maven for Spring Boot

[//]: # (TODO - add more details to the diagram)

Your Spring Boot Project ----> Spring Boot Starter Parent ----> Spring Boot Dependencies -----> Spring Boot Build ------> Maven Super POM

---

## Maven Repositories

### Configuration of Maven Repositories

Maven Repositories Search Order
- When resolving an artifact, Maven will:
  - First: Check in the local repository (aka cache) located under <user home>/.m2/repository/
  - Next: Maven Central
  - Next: any additional repositories configured
- Search order of additional repositories
  - Typically not important
  - Is Alphabetical by repository id value

Repository Mirrors
- Mirrors are used to override project defined repository values
- Mirrors are configured in settings.xml
  - Default location is in <user home>/.m2 directory
- A mirror will override the URL of the repository
- Can be used to improve performance by directing to regional servers
- Or to redirect to internal repository manager
- Values set in settings.xml will apply to all Maven project executed on system

Defining Additional Remote Repositories
- Repositories can be defined in the repositories element of the POM, or in the repositories element in the settings.xml file
  - POM definitions will be specific to the Maven project
  - settings.xml definitions are system-wide

Repository Element
- id - unique value required
- name - human-readable name
- url - URL for repository
- layout - legacy or default (Default is generally used)
- releases - Repository Policy for handling downloading of releases
- snapshots - Repository Policy for handling downloading of snapshots

Repository Policy
- Used for release and snapshot elements of Repository definitions
- enabled - true / false
- updatePolicy - always, daily (default), interval:XXX (xxx in minutes), never
- checksumPolicy - What to do if verification of artifact fails
  - values: ignore, fail, warn

### Common Public Maven Repositories

Maven Central
- Established in 2002, considered the 'grand-daddy' 🤣 of Maven repositories
- Default repository use by Apache Maven, sbt and others.
- 3.3M indexed jars
- [URL] : http://central.maven.org/maven2/

Sonatype
- Sonatype - Staging Repository used to publish artifacts to Maven Central
- Hosted by company sonatype
- Typically not used directly
- 2M+ Indexed JARs
- [URL]

JCenter
- Has Maven Central Artifacts and more
- Default for Android Studio and Gradle
- Has support for HTTPS
- Uses different CDN that Maven Central. May be more performant in different countries
  - Depends on geo-location of location
- [URL] : https://jcenter.bintry.com/

JBoss
- Artifacts for the JBoss community
- Has repositories for releases, third party releases and snapshotes
- URL: https://repository.jboss.org/nexus/content/repositories/releases/

Atlassian
.....Artifacts for Atlassian Plugin Developers

Oracle
....

Spring Framework
- Hosted by Pivotal, the company supporting the Spring Framework
- Provides Releases, Milestone Releases and Snapshots
- Helpful for developing against leading edge versions of Spring Framework Components
- Releases are also published to Maven Central
- [URL] : https://repo.spring.io

### Maven Settings

- User Settings - kept in <user home>/.m2/setting.xml
  - Can be overridden with command line parameter -s <path/ filename>
  - Common to override in CI builds
- Global Settings - Kept in Maven home /conf/settings.xml
  - Applies to all users using Maven from that directory
  - Can be overridden with command line parameter -gs <path /  filename>
  - Rarely used, user settings typically used instead

### Settings Elements
- localRepository - Allows you to override the location of the local Maven repository
- interactiveMode - Allows you to set interactive / batch mode. Defaults to interactive
- usePluginRegistry - Maven 2.0 - no longer used in Maven 3.0
- offline - defaults to false, if true Maven will not try to connect to remote repositories
- pluginGroups - List plugin groups ids to allow abbreviated plugin goals
- servers - element allows you to set user credentials for servers which Maven connects to
- mirrors - Allow you to configure mirrors for repositories
- proxies - Define network proxy information
- profiles - Define build profiles
- activeProfiles - define active build profiles

### Oracle Maven Repository
- Requires authentication
- See setup tutorial

---

## Deploying to package cloud

---

## Deploying to Apache Maven projects to Nexus

---

## Maven Build Profiles

### Maven Build Profiles

- Maven Build Profiles allow you to specify a set of build configuration values
- Values you can specify are largely the same data elements found in the POM
- Values set in build profiles:
  - Can be in addition to what is in the project POM
  - OR used to override POM values
- More than one profile can be set to active at a time
- Caution: No priority is available for multiple active profiles
  - Duplicate Property resolution is random

Why Use Build Profiles?
- Build Profiles are a very powerful feature which allows you to shape the behaviour of your build
- Build Behaviour can be changed
  - Based on your desired outcome of the build
  - Automatically based on the run time environment
  - Automatically to adapt to different operating systems
  - To optionally activate specific plugins
  - To provide alternative build time configuration values

Declaring Build Profiles
- Per Project
  - Defined in pom.xml
  - Command Line - mvn package -S <path to settings file>
- Per User
  - Defined in <user home>/.m2/settings.xml
- Global
  - Defined in <Maven Home>/conf/settings.xml
- Profile Descriptor
  - Defined in profiles.xml
  - Not supported by Maven 3.0
  - profiles.xml will not be covered in this course
  - Documentation is available in the archive section on the Apache Maven website

Which Declaration Method to Use?
- Use Project POM when:
  - Your build needs to be portable to other computers (CI servers, other developers)
  - When others may or may not need build features
- Use Setting.xml when:
  - You want the profile available to many projects
  - Frequently used for configuration of repository managers
  - Protection of secrets
  - Environment Config values

Combo of Declaration Methods?
- Not recommended

Activating Build Profiles
- In the profile configuration under activation attribute:
  - setting activeByDefault property to true
  - default activation for OS, JDK versions
  - existence of system properties
  - specific values of system properties
  - missing files (i.e build artifact not in target dir)
- Command line : mvn package -P <pofile-1>,<profile-2>
- In settings.xml file - activeProfiles section

Deactivating Profiles
- Profiles can be deactivated from the command line:
  - mvn package -P !<profile-1>, !<profile-2>
  - mvn package -P -<profile-1>,-<profile-2>

POM Elements in Profiles

[//]: # (TODO add list)

### View Active Profiles
- Command `mvn help:active-profiles`
- Note should be used with relevant CLI options
  - `mvn help:active-profiles -P <profile-1>`
    - would show profile-1 active

---

## Maven Release Plugin

### Maven Release Process Overview
- The Maven Release Plugin can be used to release versions of your project
- High level release process:
  - Version 1.0.0-SNAPSHOT
  - Version 1.0.0
  - Version 1.0.1-SNAPSHOT

### Release Considerations
- Snapshots are considered development versionsa and source changes are not closely tracked
- Releases however are formal releases of software projects
  - Source code should be tracked
  - Auditors in some industries will require source code of a release to be tracked
  - You should be able to re-create the released artifact from your SCM
  - Best practice is to tag SCM with release
    - Allows you to definitively know what is in the `release`

### Maven Release Plugin Goals

[//]: # (TODO - go into details later)

- Prepare Release
  - ........
- Perform Release
  - ..........

Release Rollback
- Typically used if errors have occurred in release:prepare goal
  - Will revert POMs back to pre-release state
  - SCM Tag may be removed
    - Tag removal is not fully implemented (depends on SCM)
    - SourceTree is a handy tool for GIT
  - Will not work if goal release:clean has been run

CLean Release
- Cleans up release files
  - removes release.properties
  - removes any backup POMs
- Normally removed with release:perform

Update POM Versions
- Goal can be used to update POM versions in multi-model projects
- Handy tool if default release process does not meet your needs
- Not shown in course
  - `command: mvn release:update-versions -DautoVersionSubmodules=true`

### SCM Configuration
- Maven SCM Plugin
  - SCM - Software Configuration Management or Source Control Management
  - Under the covers, the Maven Release Plugin - is using the Maven SCM Plugin to interact with the project's SCM
  - The Maven SCM Plugin supports most major SCMs
  - The Maven SCM Plugin Supports: Git, CVS, Subversion, TFS, Mercurial, Perforce and others.
  - Exact abilities vary by SCM

### SCM Configuration
- The SCM is defined in the SCM section of the POM
- The 'connection' elements defines the read URL
- The 'developerConnection' element defines the write url
- The URL is prefixed with 'scm:[scm-provider]:[SCM URL]'
- The tag element defines the tag project is under (default to HEAD)
- The url element defines the public repository URL

### Git Hub Authentication
.....



---

## Maven in the Real World

---

## Enterprise Dependency Management

---

