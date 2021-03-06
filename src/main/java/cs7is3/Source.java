package cs7is3;

public enum Source {
    FBIS,
    FR,
    FT,
    LAT;

    public String getFileNamePrefix()
    {
        return this.toString().substring(0, 2);
    }

    @Override
    public String toString()
    {
        switch (this)
        {
            case FBIS:
                return "fbis";
            case FR:
                return "fr94";
            case FT:
                return "ft";
            case LAT:
                return "latimes";
            default:
                return "";
        }
    }
}
