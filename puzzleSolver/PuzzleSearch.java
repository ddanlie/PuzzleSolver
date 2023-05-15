import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PuzzleSearch
{
    private SearchTree goalState;
    private SearchTree startState;
    private PriorityQueue<SearchTree> nodeList = new PriorityQueue<SearchTree>(new Comparator<SearchTree>()
    {
        @Override
        public int compare(SearchTree o1, SearchTree o2)
        {
            if(o1.getWeight() > o2.getWeight())
                return 1;
            else if(o1.getWeight() < o2.getWeight())
                return -1;
            else
                return 0;
        }
    });
    PuzzleSearch(int h, int w, XPuzzle start)
    {
        XPuzzle tmp = new XPuzzle(w,h);
        goalState = new SearchTree(tmp, 0);
        startState = new SearchTree(start,
                heuristic(start.getMap(), goalState.getState().getMap(), h, w));

        sortIn(startState);
    }

    XPuzzle[] execute()
    {
        final int maxExplored = 1_000_000;
        int explored = 0;
        XPuzzle[] solution = new XPuzzle[maxExplored];
        while(true)
        {
            if(nodeList.isEmpty() || explored >= maxExplored) return null;
            SearchTree currentState = nodeList.remove();
            explored++;
            if(SearchTree.compareStates(currentState, goalState))
            {
//                System.out.println("Current state:\n");
//                currentState.getState().drawMap();

                int solIdx = maxExplored-1;
                solution[solIdx--] = new XPuzzle(currentState.getState());
                while(currentState.getPredecessor() != null)
                {
                    solution[solIdx--] = new XPuzzle(currentState.getState());
                    currentState = currentState.getPredecessor();
                }
                System.out.println("Explored: "+explored);

                return solution;
            }

            currentState.generateNextStates();
            int nextStatesCount = 0;
            int maxNextStatesCount = currentState.getMaxNextStatesCount();
            while(nextStatesCount < maxNextStatesCount)
            {
                SearchTree nextState = currentState.getNextState(nextStatesCount++);
                if(nextState == null)
                    break;
                sortIn(nextState);
            }
        }
    }


    //based on mapInit() of XPuzzle
    private int heuristic(int[][] state, int[][] goalState, int h, int w)
    {
        int f = 0;
        for(int i = 0; i < h; i++)
        {
            for(int j = 0; j < w; j++)
            {
                if(state[i][j] != goalState[i][j])
                    f++;
            }
        }
        return f;
    }

    private void sortIn(SearchTree st)
    {
        if(st != null)
            nodeList.add(st);
    }

    public static void copyState(int[][] copyto, int[][] copyfrom, int h, int w)
    {
        for(int i = 0; i < h; i++)
            System.arraycopy(copyfrom[i], 0, copyto[i], 0, w);
    }

    void showStartState()
    {
        startState.getState().drawMap();
    }

    void showGoalState()
    {
        goalState.getState().drawMap();
    }




}
