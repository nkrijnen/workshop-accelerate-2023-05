# How the right architecture helps you make big changes

During the workshop, this repo will be updated with code that you will be working on.

## Assignments

Go to [part-01/README.md](part-01/README.md) for the first exercises.

## Workshop preparation

**TLDR:** Install IntelliJ + clone repo + check that tests run.

There are a few things you can do to make sure you have a running start and don't lose any time on basic setup during
the workshop.

### Install the latest IntelliJ IDEA

The [free IntelliJ Community edition](https://www.jetbrains.com/idea/download/) is sufficient for this workshop.

If you already have IntelliJ Community or Ultimate installed, that is fine, but do make sure you update it to a recent
version.
The easiest way to manage your IntelliJ installation is using the [Toolbox App](https://www.jetbrains.com/toolbox-app/).

### Clone this repo

You can do this directly from IntelliJ using `File`, `New`, `Project from Version Control...`, and entering the url
which you can find under the `Code` button on the GitHub page.

### Validate your setup

After opening the project, IntelliJ will need some time to sync the gradle project, download dependencies and compile
the code. At this point, you may run into some of the issues mentioned below.

We have choosen to use [Quarkus](https://quarkus.io), as it has a lot of advantages in this type of applications. Automatically running unite test, immediate feedback, build on safe, etc. 

We recommend validate your project by running it in dev mode in a separate command line window. Re-run the tests. If one of them fail, fix it. Quarkus will run the test for you.

#### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

#### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

#### Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/part-01-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

#### Related Guides

- Quarkus Extension for Spring Web API ([guide](https://quarkus.io/guides/spring-web)): Use Spring Web annotations to create your REST services

