package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    protected AState cameFrom;
    protected int cost;

    public AState(AState cameFrom){ this.cameFrom = cameFrom; }

    public int getCost(){ return cost; }

    public AState getCameFrom(){
        return cameFrom;
    }

    public abstract String toString();

}
