package cs7is3;

public class Result
{
    private int topicId;
    private int documentId;
    private int rank;
    private float score;

    public Result(int topicId, int documentId, int rank, float score)
    {
        this.topicId = topicId;
        this.documentId = documentId;
        this.rank = rank;
        this.score = score;
    }

    @Override
    public String toString()
    {
        return String.format(
            "%d 0 %d %d %f STANDARD",
            this.topicId, this.documentId, this.rank, this.score
        );
    }
}
