# CS7IS3: Assignment 2

## Description

The current (very basic) implementation of the indexer iterates over all of the files in the dataset and, for each file, does the following:

1. Load the file as a string
2. Convert the string into a JSoup object
3. Iterate over all `<doc>` elements in the JSoup object and, for each element, add a document to the index containing its raw text content

This implementation takes a little over two minutes to index the entire corpus.

## Usage

### Compiling

`./build.sh`

### Running

`./run.sh`
