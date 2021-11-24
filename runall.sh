echo "=== Standard Analyzer ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a STANDARD
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s BM25
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s CLASSIC
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s BOOLEAN
java -jar target/searchengine-1.0.jar -m SEARCH -a STANDARD -s CUSTOM

echo "=== English Analyzer ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a ENGLISH
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s BM25
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s CLASSIC
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s BOOLEAN
java -jar target/searchengine-1.0.jar -m SEARCH -a ENGLISH -s CUSTOM

echo "=== Custom Analyzer (sw=tiny) ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a CUSTOM --stopwords TINY
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s BM25
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s CLASSIC
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s BOOLEAN
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords TINY -s CUSTOM

echo "=== Custom Analyzer (sw=short) ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a CUSTOM --stopwords SHORT
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s BM25
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s CLASSIC
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s BOOLEAN
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords SHORT -s CUSTOM

echo "=== Custom Analyzer (sw=long) ==="
echo "# Indexing"
java -jar target/searchengine-1.0.jar -m INDEX -a CUSTOM --stopwords LONG
echo "# Searching"
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s BM25
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s CLASSIC
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s BOOLEAN
java -jar target/searchengine-1.0.jar -m SEARCH -a CUSTOM --stopwords LONG -s CUSTOM
