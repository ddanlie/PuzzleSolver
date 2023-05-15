import java.util.Arrays;

public class SearchTree
{
    private SearchTree[] nextStates;
    private SearchTree predecessor;
    private int maxNextStatesCount = XPuzzle.MoveSide.values().length;
    private XPuzzle state;
    private static int id = 0;
    private boolean nextGenerated = false;
    private int weight;

    SearchTree(XPuzzle state, int weight)
    {
        id++;
        this.predecessor = null;
        this.weight = weight;
        this.state = new XPuzzle(state);
        nextStates = new SearchTree[this.maxNextStatesCount];
    }

    private SearchTree(XPuzzle state, int weight, SearchTree predecessor)
    {
        this(state, weight);
        this.predecessor = predecessor;
    }

    void generateNextStates()
    {
        if(nextGenerated)
            return;
        nextGenerated = true;
        int count = 0;
        for(var i : XPuzzle.MoveSide.values())
        {
            if(state.canMove(i))
            {
                SearchTree newState = new SearchTree(this.state, this.weight+1, this);
                newState.state.move(i);
                //Search cycles
                SearchTree tmp = newState;
                boolean cycleFlag = false;
                while(tmp.predecessor != null)
                {
                    if(XPuzzle.isEqualPuzzle(tmp.state, tmp.predecessor.state))
                    {
                        cycleFlag = true;
                        break;
                    }
                    tmp = tmp.predecessor;
                }
                if(cycleFlag)
                    continue;
                //Add next state
                nextStates[count++] = newState;
            }
        }
        while(count < this.maxNextStatesCount)
            nextStates[count++] = null;
    }

    SearchTree getNextState(int idx)
    {
        return nextStates[idx];
    }

    static boolean compareStates(SearchTree st1, SearchTree st2)
    {
        return XPuzzle.isEqualPuzzle(st1.state, st2.state);
    }

    int getWeight()
    {
        return weight;
    }

    int getMaxNextStatesCount()
    {
        return maxNextStatesCount;
    }

    XPuzzle getState()
    {
        return state;
    }

    SearchTree getPredecessor() {return predecessor; }

    int getId() {return id;}
}
