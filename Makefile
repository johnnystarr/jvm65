GR = ./gradlew
BRANCH = $(shell git branch --show-current)

DEFAULT_GOAL: help

build: ## Gradle Build
	$(GR) build

test: ## Test Project
	$(GR) test

branch: ## Create an New Branch
	@ruby -e "print 'Branch Name: '; b=gets.downcase.gsub(' ', '-'); system(\"git checkout -b feature/#{b}\")"

push: ## Push to Origin
	@git push origin $(BRANCH)

push-main-build: ## Push Main to local Build
	@git push -u build main

pr-create: ## Create a Pull Request on 'develop' in Github
	@gh pr create

pr-merge: ## Merge a Pull Request
	@gh pr merge --delete-branch

commit: ## Commit changes to Git
	@printf "Commit Message: "; read message; git add -A; git commit -m "$$message"

clean: ## Gradle Clean
	$(GR) clean

changelog: ## Generate Changelog
	@git-chglog -o CHANGELOG.md

bump: ## Bump Version Across Files
	./.scripts/bump.sh

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: help build test
