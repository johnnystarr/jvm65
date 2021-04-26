GR = ./gradlew
BRANCH = $(shell git branch --show-current)

DEFAULT_GOAL: help

build: ## Build JAR
	$(GR) jar

test: ## Test Project
	$(GR) test

push-branch: ## Push Branch to Remotes
	@git push origin $(BRANCH)

push-main-build: ## Push Main to local Build
	@git push -u build main

pr-create: ## Create a Pull Request on Github
	@gh pr create

pr-merge: ## Merge a Pull Request
	@gh pr merge --delete-branch

commit: ## Commit changes to Git
	@printf "Commit Message: "; read message; git add -A; git commit -m "$$message"

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: help build test