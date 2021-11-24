echo "=== Standard Analyzer ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardBm25Default
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardBm25Multi
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardClassicDefault
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardClassicMulti
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardBooleanDefault
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardBooleanMulti
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardCustomDefault
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardCustomMulti

echo "=== English Analyzer ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishBm25Default
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishBm25Multi
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishClassicDefault
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishClassicMulti
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishBooleanDefault
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishBooleanMulti
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishCustomDefault
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishCustomMulti

echo "=== Custom Analyzer (sw=tiny) ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25DefaultTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25MultiTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicDefaultTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicMultiTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanDefaultTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanMultiTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomDefaultTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomMultiTinyNone

echo "=== Custom Analyzer (sw=short) ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25DefaultShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25MultiShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicDefaultShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicMultiShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanDefaultShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanMultiShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomDefaultShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomMultiShortNone

echo "=== Custom Analyzer (sw=long) ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25DefaultLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25MultiLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicDefaultLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicMultiLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanDefaultLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanMultiLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomDefaultLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomMultiLongNone