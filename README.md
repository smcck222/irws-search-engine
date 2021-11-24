# CS7IS3: Assignment 2

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
    - `WORDNET` synonym mapping using synonyms from [*WordNet*](https://wordnet.princeton.edu/)

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

Best score = `0.3424`

| Analyzer | Scoring | Parser | Stopwords (none/tiny) | Stopwords (short) | Stopwords (long) |
| - | - | - | - | - | - |
| Standard | BM25 | Default | `0.2689` |  |  |
| Standard | BM25 | Multi | `0.2692` |  |  |
| Standard | Classic | Default | `0.1951` |  |  |
| Standard | Classic | Multi | `0.1941` |  |  |
| Standard | Boolean | Default | `0.0695` |  |  |
| Standard | Boolean | Multi | `0.0703` |  |  |
| Standard | Custom | Default | `0.2743` |  |  |
| Standard | Custom | Multi | `0.2760` |  |  |
| English | BM25 | Default | `0.3259` |  |  |
| English | BM25 | Multi | `0.3263` |  |  |
| English | Classic | Default | `0.2340` |  |  |
| English | Classic | Multi | `0.2237` |  |  |
| English | Boolean | Default | `0.0755` |  |  |
| English | Boolean | Multi | `0.0769` |  |  |
| English | Custom | Default | `0.3323` |  |  |
| English | Custom | Multi | `0.3353` |  |  |
| Custom | BM25 | Default | `0.3260` | `0.3307` | `0.3352` |
| Custom | BM25 | Multi | `0.3263` | `0.3302` | `0.3344` |
| Custom | Classic | Default | `0.2340` | `0.2372` | `0.2374` |
| Custom | Classic | Multi | `0.2237` | `0.2281` | `0.2278` |
| Custom | Boolean | Default | `0.0755` | `0.0953` | `0.1180` |
| Custom | Boolean | Multi | `0.0769` | `0.0967` | `0.1205` |
| Custom | Custom | Default | `0.3323` | `0.3389` | `0.3419` |
| Custom | Custom | Multi | `0.3353` | `0.3400` | `0.3424` |
