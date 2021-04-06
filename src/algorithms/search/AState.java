package algorithms.search;

public abstract class AState {
    private AState cameFrom;
    private int cost;

    public int getCost(){return cost;}
    public AState getCameFrom(){return cameFrom;}
}
