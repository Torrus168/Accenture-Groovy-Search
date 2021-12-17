# Accenture-Groovy-Search
Application for replacing text in files under a specified directory and its subdirectories.

## Prerequesites
Installed **Java 17, Groovy and Gradle**

## How to Build and Run

```bash
gradle clean build
gradle run --args='<dir> <searchStr> <replaceTo> [<logFileName>]'
```

- `dir` - directory to process 

- `searchStr` - string to search in files

- `replaceTo` - string to replace the search string

- `logFileName` - optional parameter. A name of log file where an information about changed files written. This file is created if it doesn't exist.
