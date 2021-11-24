echo "=== Standard Analyzer ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a STANDARD
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s BM25 -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s BM25 -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s CLASSIC -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s CLASSIC -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s BOOLEAN -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s BOOLEAN -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s CUSTOM -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s CUSTOM -p MULTI

echo "=== English Analyzer ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a ENGLISH
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s BM25 -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s BM25 -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s CLASSIC -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s CLASSIC -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s BOOLEAN -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s BOOLEAN -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s CUSTOM -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s CUSTOM -p MULTI

echo "=== Custom Analyzer (sw=tiny) ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a CUSTOM --stopwords TINY
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s BM25 -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s BM25 -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s CLASSIC -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s CLASSIC -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s BOOLEAN -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s BOOLEAN -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s CUSTOM -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s CUSTOM -p MULTI

echo "=== Custom Analyzer (sw=short) ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a CUSTOM --stopwords SHORT
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s BM25 -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s BM25 -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s CLASSIC -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s CLASSIC -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s BOOLEAN -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s BOOLEAN -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s CUSTOM -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s CUSTOM -p MULTI

echo "=== Custom Analyzer (sw=long) ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a CUSTOM --stopwords LONG
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s BM25 -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s BM25 -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s CLASSIC -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s CLASSIC -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s BOOLEAN -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s BOOLEAN -p MULTI
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s CUSTOM -p DEFAULT
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s CUSTOM -p MULTI
