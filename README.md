# CS7IS3: Assignment 2

## Basic Usage

All scripts should be executed from within the `cs7is3-assign2` directory.

**Note:** it is not absolutely necessary to build the program or re-index the corpus as these tasks have already been carried out. Nonetheless, instructions for how to do so are provided below.

To generate a file containing search results for each of the topics you can execute `./run.sh SEARCH`. The results file, which will contain the top 1000 results for each query, will be `./data/results/CustomCustomMultiLongNone`.

To evaluate this results file against a QRel file you can run either of the following commands:

`./eval.sh CustomCustomMultiLongNone`

`./trec_eval-9.0.7/trec_eval qrels-assignment2.part1 ./data/results/CustomCustomMultiLongNone`

## Building the Program

There are two ways to compile the program:

**Using the provided script:**

```
./build.sh
```

**Manually:**

```
mvn compile
mvn package
```

## Running the Program

### Modes of Operation

The program has two modes of operation:

1. Indexing (`--mode INDEX`): creates an index out of the documents in the corpus.
2. Searching (`--mode SEARCH`): searches the engine using the topics and writes the results to an output file in the `./data/results/` directory.

To run the program in index mode (using the default parameters) run either of the following commands:

```
./run.sh INDEX
```

```
java -jar target/searchengine-1.0.jar --mode INDEX
```

To run the program in search mode (using the default parameters) run either of the following commands:

```
./run.sh SEARCH
```

```
java -jar target/searchengine-1.0.jar --mode SEARCH
```

### Additional Parameters

By default, the program will use our custom analyzer, the long list of stopwords and no synonym mapping for processing the corpus and a tweaked version of *BM25* and multi-field query parsing for scoring results.

Other options can be specified via the following parameters:

- `--analyzer`
    - `STANDARD`: *Lucene's* standard analyzer
    - `ENGLISH`: *Lucene's* English language analyzer
    - `CUSTOM`: our custom analyzer

- `--scorer`
    - `BM25`: *BM25* similarity
    - `CLASSIC`: classic (VSM) similarity
    - `BOOLEAN`: boolean similarity
    - `CUSTOM`: *BM25* similarity with tweaked parameters

- `--parser`
    - `DEFAULT`: standard query parser
    - `MULTI`: multi-field query parser

- `--stopwords` (only applies when `--analyzer CUSTOM` is specified)
    - `TINY`: the default 33 stopwords used by *Lucene*
    - `SHORT`: a list of 174 stopwords from [*Ranks NL*](https://www.ranks.nl/stopwords)
    - `LONG`: a list of 667 stopwords from [*Ranks NL*](https://www.ranks.nl/stopwords)

- `--synonyms` (only applies when `--analyzer CUSTOM` is specified)
    - `NONE`: no synonym mapping
    - `GEO`: geographic synonym mapping
    - `WORDNET`: synonym mapping using synonyms from [*WordNet*](https://wordnet.princeton.edu/)
    - `BOTH`: both geographic and [*WordNet*](https://wordnet.princeton.edu/) synonym mapping

**Note:** when running the program in search mode the exact same parameters that were specified when running the program in index mode must be used.

## Evaluating the Results

To evaluate the results against a QRels file using `trec_eval` run either of the following commands:

```
./eval.sh <QRELS_FILE> <RESULTS_FILE>
```

```
./trec_eval-9.0.7/trec_eval -m map -m set_recall <QRELS_FILE> <RESULTS_FILE>
```

## MAP Scores (part 1)

Best score = `0.3514`
