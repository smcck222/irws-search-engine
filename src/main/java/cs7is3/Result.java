package cs7is3;

public class Result
{
    private int topicId;
    private String documentId;
    private int rank;
    private float score;
    private String runName;

    public Result(int topicId, String documentId, int rank, float score, String runName)
    {
        this.topicId = topicId;
        this.documentId = documentId;
        this.rank = rank;
        this.score = score;
        this.runName = runName;
    }

    @Override
    public String toString()
    {
        return String.format(
            "%d 0 %s %d %f %s",
            this.topicId, this.documentId, this.rank, this.score, this.runName
        );
    }
}
