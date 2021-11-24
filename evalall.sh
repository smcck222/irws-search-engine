echo "=== Standard Analyzer ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardBm25
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardClassic
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardBoolean
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/StandardCustom

echo "=== English Analyzer ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishBm25
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishClassic
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishBoolean
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/EnglishCustom

echo "=== Custom Analyzer (sw=tiny) ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25TinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanTinyNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomTinyNone

echo "=== Custom Analyzer (sw=short) ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25ShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanShortNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomShortNone

echo "=== Custom Analyzer (sw=long) ==="
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBm25LongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomClassicLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomBooleanLongNone
./trec_eval-9.0.7/trec_eval -m map qrels.assignment2.part1 ./data/results/CustomCustomLongNone
