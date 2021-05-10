package algorithms.search;

import java.util.Comparator;

public class StateCostComparator implements Comparator<AState> {
    /** Compare between two states
     * @param s1 the first state
     * @param s2 the second state
     * @return num, where: num < 0 if s1 < s2, num == 0 if s1 == s2, and num > 0 if s1 > s2
     */
    @Override
    public int compare(AState s1, AState s2) { return s1.getCost() - s2.getCost(); }

    @Override
    public String toString() {
        return "StateCostComparator";
    }

}
