package algorithms.search;

public interface ISearchable {
    AState getStartState();
    AState getGoalState();

    /**Return the State's successors States
     * @param currState the state that we wish to find it's successors
     * @return an array of all the States that the current State can move to.
     */
    AState[] getAllSuccessors(AState currState);

}
