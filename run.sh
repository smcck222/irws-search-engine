if [[ $# -eq 2 ]]
    then
        java -jar target/searchengine-1.0.jar --mode=$1 --results=$2
    else
        java -jar target/searchengine-1.0.jar --mode=$1
fi
