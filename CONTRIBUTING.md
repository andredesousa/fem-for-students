# How to Contribute

As a contributor, here are the guidelines we would like you to follow:

- [Project Structure](#project-structure)
- [Development Workflow](#development-workflow)
- [Commit Messages Convention](#commit-messages-convention)
- [Submitting a Pull Request](#submitting-a-pull-request)
- [Reviewing a Pull Request](#reviewing-a-pull-request)
- [Further Help](#further-help)

## Project Structure

When working in a large team with many developers that are responsible for the same codebase, having a common understanding of how the application should be structured is vital.
Based on Java best practices, this project looks like this:

```html
├── docs
|  ├── user-manual-en.pdf
|  └── user-manual-pt.pdf
├── gradle
├── src
|  ├── main
|  |  ├── java
|  |  |  └── app
|  |  |    ├── backend
|  |  |    ├── calculations
|  |  |    ├── finiteelement
|  |  |    ├── frontend
|  |  |    ├── gausslegendre
|  |  |    ├── gausslobatto
|  |  |    ├── matrices
|  |  |    ├── shapefunctions
|  |  |    ├── variablesubstitution
|  |  |    └── FEM.java
|  |  └── resources
|  |    └── images
|  └── test
├── build.gradle
├── checkstyle.xml
├── CONTRIBUTING.md
├── gradle.properties
├── gradlew
├── README.md
└── settings.gradle
```

All of the app's code goes in a folder named `src/main`.
The unit tests are in the `src/test` folder.
Static files are placed in `src/main/resources` folder.

## Development Workflow

After cloning the project, use `./gradlew run` to fetch its dependencies and start the application.

The project uses [Checkstyle](https://checkstyle.sourceforge.io/) and [EditorConfig](https://editorconfig.org/).
It is recommend running `./gradlew check` to make sure you don't introduce any regressions as you work on your change.

This project follows [conventional commits](https://www.conventionalcommits.org/).
It is recommended to follow these guidelines when creating your branch and submitting your changes.

## Commit messages convention

In order to have a consistent git history every commit must follow a specific template. Here's the template:

```bash
<type>(<ITEM ID>?): <subject>
```

### Type

Must be one of the following:

- **build**: Changes that affect the build system or external dependencies (example scopes: Gradle, Maven)
- **ci**: Changes to our CI configuration files and scripts (example scopes: Jenkins, Travis, Circle, SauceLabs)
- **chore**: Changes to the build process or auxiliary tools and libraries such as documentation generation
- **docs**: Documentation only changes
- **feat**: A new feature
- **fix**: A bug fix
- **perf**: A code change that improves performance
- **refactor**: A code change that neither fixes a bug nor adds a feature
- **revert**: A commit that reverts a previous one
- **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc.)
- **test**: Adding missing tests or correcting existing tests

### ITEM ID

The related **issue** or **user story** or even **defect**.

- For **user stories**, you shoud use `US-` as prefix. Example: `feat(US-4321): ...`
- For **no related issues** or **defects** you should leave it blank. Example: `feat: ...`

### Subject

The subject contains a succinct description of the change.

## Submitting a Pull Request

Before submitting a pull request, please make sure the following is done:

- Fork the repository and create your branch from **main**.
- Make sure your code lints (`./gradlew checkstyle`).
- Make sure your changes use a descriptive commit message that follows the [Commit Message Convention](#commit-messages-convention).
- You must [squash and rebase](https://levelup.gitconnected.com/squash-and-rebase-my-method-for-merging-git-branches-3b43c52675b6) your commits.
- Finally, submit all changes directly to the **main** branch.

## Reviewing a Pull Request

The project maintainer will review your pull request.
He will review your pull request and either merge it, request changes to it, or close it with an explanation.
After your pull request is merged, you can safely delete your branch.

## Further Help

If you need help, contact the [project maintainer](mailto:asousafilipe@hotmail.com).
