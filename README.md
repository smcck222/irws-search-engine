# CS7IS3: Assignment 2

## Description

The current (very basic) implementation of the indexer iterates over all of the files in the dataset and, for each file, does the following:

1. Load the file as a string
2. Clean up any tag or attribute irregularities with regular expressions
3. Convert the string into an XML object
4. Iterate over all `<doc>` nodes in the XML object and, for each node, add a document to the index containing its raw text content

This implementation takes a little over two minutes to index the entire corpus.

## Usage

### Compiling

`./build.sh`

### Running

`./run.sh`
