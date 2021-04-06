package algorithms.search;

public interface ISearchable {
    AState getStartState();
    AState getFinalState();

    /**Return the State's successors States
     * @param currState
     * @return an array of all the States that the current State can move to.
     */
    AState[] getAllSuccessors(AState currState);

}
