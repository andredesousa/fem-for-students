# FEM For Students

**FEM for Students** is a software for modeling and linear elastic structural analysis by Finite Element Method.
This software was developed by me in my Master's Thesis in Structural Engineering.

## Available gradle tasks

The tasks in [build.gradle](build.gradle) file were built with simplicity in mind to automate as much repetitive tasks as possible and help developers focus on what really matters.

The next tasks should be executed in a console inside the root directory:

- `./gradlew tasks` - Displays the tasks runnable from root project 'app'.
- `./gradlew run` - Runs this project as a JVM application
- `./gradlew check` - Runs all checks.
- `./gradlew test` - Runs the unit tests.
- `./gradlew checkstyle` - Runs several static code analysis.
- `./gradlew clean` - Deletes the build directory.
- `./gradlew javadoc` - Generates Javadoc API documentation for the main source code.
- `./gradlew build` - Assembles and tests this project.
- `./gradlew jar` - Assembles a jar archive containing the main classes.
- `./gradlew help` - Displays a help message.

For more details, read the [Command-Line Interface](https://docs.gradle.org/current/userguide/command_line_interface.html) documentation in the [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html).

## Development mode

After cloning the project, use `./gradlew run` to fetch its dependencies and start the application.

## Linting code

A linter is a static code analysis tool used to flag programming errors, bugs, stylistic errors and suspicious constructs.
Use `./gradlew checkstyle` to analyze the code with [Checkstyle](https://checkstyle.sourceforge.io/).
Checkstyle finds class design problems, method design problems, and others. It also has the ability to check code layout and formatting issues.

## Running unit tests

Use `./gradlew test` to execute the unit tests via [JUnit 5](https://junit.org/junit5/).
Use `./gradlew test -t` to keep executing unit tests in real time while watching for file changes in the background.
You can see the HTML report opening the [index.html](build/reports/tests/test/index.html) file in your web browser.

It's a common requirement to run subsets of a test suite, such as when you're fixing a bug or developing a new test case.
Gradle provides different mechanisms.
For example, the following command lines run either all or exactly one of the tests in the `SomeTestClass` test case:

```bash
./gradlew test --tests SomeTestClass
```

For more details, you can see the [Test filtering](https://docs.gradle.org/current/userguide/java_testing.html#test_filtering) section of the Gradle documentation.

## Debugging

You can debug the source code, add breakpoints, inspect variables and view the application's call stack.

You can customize the [log verbosity](https://docs.gradle.org/current/userguide/logging.html#logging) of gradle tasks using the `-i` or `--info` flag.

## Build and release

Use `./gradlew jar` to build the project. The artifact is stored in the `build/libs` directory.

## Contributing

You can contribute to this project.
Please read the [contribution guidelines](CONTRIBUTING.md).

## License

FEM for Students is [MIT licensed](LICENSE).
