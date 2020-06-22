# Contributing to Craftle

Thank you for considering to contribute to the Craftle project.

The following is a set of guidelines for contributing to Craftle and its packages, which are hosted in the [Craftle repo](https://github.com/kalindudc/craftle) 
on GitHub. These are mostly guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

## Code of Conduct

This project and everyone participating in it is governed by the [Craftle of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.

## What to understand before getting started?

Pull requests are the best way to propose changes to the codebase (we use [Github Flow](https://guides.github.com/introduction/flow/index.html)). 
Before starting with any code changes please maintain the following guidelines.
 
### Git repository structure

Craftle is seperated in to the following branches

```sh
# Current active branch for development, all incomming pull requests must be directed to this branch.
- develop

# Release branch for the minecraft version 1.xx.x. At the end of the release cycle all code from develop will be merged to 1.xx.x and a new Craftle build will be released.
- 1.xx.x

# up to date with the latest release of Craftle and holds all documentation for the github pages.
- master
```

### License aggreement

Craftle is licensed under CC-NC-SA 4.0 and any contributions you make will that license. So any code changes you submit must adhear to the [Craftle License](LICENSE.md) agreement.

## How can I make a contribution?

### Code changes 

1. Fork the repo and create your branch from `master`.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs or have added new Objects within the mod, update the documentation under docs/.
4. Ensure the test suite passes.
5. Make sure your code lints.
6. Issue that pull request!

#### Commit message format

1. Start the subject line with a verb (e.g. Change header styles)
2. Use the imperative mood in the subject line (e.g. Fix, not Fixed or Fixes header styles)
3. Limit the subject line to about 50 characters
4. Do not end the subject line with a period
5. Separate subject from body with a blank line
6. Wrap the body at 72 characters

#### Additional Notes

1. Craftle follows [Forge versioning convensions](https://mcforge.readthedocs.io/en/1.14.x/conventions/versioning/). If your changes affect 
any of the MAJORMOD/MAJORAPI/MINOR conventions as described in the Forge documentation, please indicate it within the pull reques.

2. All pull requests must come attached to a github [issue](https://github.com/kalindudc/craftle/issues). If your code change resolves an existing issue, please
indicate that within the pull request description. If your code change partially affects an existing issue, please create a new issue that covers your code change
and attach both issues to the pull request via the description. Finally if your code change does not relate to any existing issues, please create a new issue and 
wait for approval from the Craftle team before submitting your pull request.

### Enhancement requests and Bug reports

Craftle uses github issues to track enhancements and bug repots. Report a bug or suggest an enhancement by 
[opening a new issue](https://github.com/kalindudc/craftle/issues); it's that easy!

Note: All enhancement requests and bug reports mut be fully detailed by following the provided issue templates. If you do not wish to follow these templates, that 
is ok! as long the submitted issues are clearly described in great detail for anyone to understand. Any issues without any proper details will be rejected by the 
Craftle team.

Consider the following as an example template for any issue

- A quick summary and/or background
- Detailed description (if enhancement)
- Steps to reproduce (if bug)
  - Be specific!
  - Give sample code if you can.
- What you expected would happen
- What actually happens (if bug)
- Additional notes

## Use a Consistent Coding Style

* Craftle uses the Google Style guide with indentations set to 4 spaces rather than 2
* Use the provided [code style](docs/code-style.xml) for IntelIJ users
* For other IDEs please use the [Google Java Style](https://google.github.io/styleguide/javaguide.html) guide with 4 spaces  for indentation

##  Credits

This contributing document adapted using the following templates
- https://gist.github.com/briandk/3d2e8b3ec8daf5a27a62
- https://github.com/atom/atom/blob/master/CONTRIBUTING.md
- https://github.com/github-tools/github-release-notes