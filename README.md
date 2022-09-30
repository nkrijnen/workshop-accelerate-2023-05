# How the right architecture helps you make big changes

During the workshop, this repo will be updated with code that you will be working on.

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

### Take the blue or the red pill

Choose `kotlin` or `java`.

For the Kotlin version: `git checkout main`

For the Java version: `git checkout java/main`

### Validate your setup

After opening the project, IntelliJ will need some time to sync the maven project, download dependencies and compile
the code.

This project uses [Quarkus](https://quarkus.io), as it has a lot of advantages for these type of applications like:
automatically running unit tests, immediate feedback, auto-build on save, etc.

We recommend you validate this project by running it in `dev mode` in a separate command line window with the command:

```cd part-01; ./mvnw compile quarkus:dev```

Now start auto-running the tests by pressing `r`. One test should fail.

Last step: adjust the code in `GreetingController` to fix it.
On save, Quarkus will auto-run the test. The tests should pass.

> **_NOTE:_**  Quarkus ships with a Dev UI, which is available in dev mode at http://localhost:8080/q/dev/

#### Packaging and native executable

Quarkus also allows you to package the application, even as an optimized native executable. For this workshop you don't
need that.

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling

#### Related Guides

- [Quarkus Extension for Spring Web API](https://quarkus.io/guides/spring-web): Use Spring Web annotations to
  create your REST services
