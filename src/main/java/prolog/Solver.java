package prolog;

import org.jpl7.*;
import prolog.model.FieldState;
import prolog.model.Input;
import prolog.model.Solution;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Solver {

    public static final String HELPER_FILE = "helpers.pl";
    public static final String PATTERN_BASIC_FILE = "patterns/basic_patterns.pl";
    public static final String PATTERN_11_FILE = "patterns/pattern1_1.pl";
    public static final String PATTERN_12_FILE = "patterns/pattern1_2.pl";
    private Input solverInput;

    public static void main(String[] args) {
        new Solver();
    }

    public Solver(Integer javaColumnSize, Integer javaRowSize) {
        solverInput = new Input(javaColumnSize, javaRowSize);
        initProlog();
    }

    private Solver(){initProlog();};

    private void initProlog() {
        loadFileToProlog(HELPER_FILE);
        loadFileToProlog(PATTERN_BASIC_FILE);
        loadFileToProlog(PATTERN_11_FILE);
        loadFileToProlog(PATTERN_12_FILE);
    }

    private void loadFileToProlog(String fileName) {
        Query q1 =
                new Query(
                        "consult",
                        new Term[] {new Atom(fileName)}
                );

        System.out.println( String.format("consult %s ", fileName) + (q1.hasSolution() ? "succeeded" : "failed"));
    }

    public List<Solution> setInputFromArray(int[] array, int x, int y) {
//        System.out.println(String.format("X: %d, Y: %d", x, y));
        solverInput.setInputFromArray(array, x, y);
        return callProlog();
    }

    private List<Solution> callProlog() {
        if(solverInput.containsNumberField()) {
            List<Solution> solutions = new ArrayList<>();
            if(isMine(solverInput.toString())) {
                solutions.add(new Solution(3, 3, FieldState.MINE));
            }
            solutions.addAll(call11Pattern(solverInput.toString()));
            return solutions;
        }

        return null;
    }

    private boolean isMine(String B55) {
        if(callBasicPattern(B55)) {
//            System.out.println(solverInput);
//            System.out.println("-----------------------------------------------------------------------");
//            System.out.println("-----------------------------------------------------------------------");
            return true;
        }
        if(call12Pattern(B55)) {
            return true;
        }
        return false;
    }

    private boolean isSafe(String B55) {
        call11Pattern(B55);
        return false;
    }

    private boolean callBasicPattern(String B55) {
        Query q =
                new Query(
                        String.format("is_virgin_mine_basic_pattern(%s)", B55)
                );
        return q.hasSolution();
    }

    private boolean call12Pattern(String B55) {
        Query q =
                new Query(
                        String.format("is_12_pattern(%s)", B55)
                );
        return q.hasSolution();
    }

    private List<Solution> call11Pattern(String B55) {
        Query q =
                new Query(
                        String.format("is_11_pattern(%s, _, _, X, Y)", B55)
                );
        List<Solution> toRet = new ArrayList<>();
        if(q.hasSolution()){
            Map<String, Term> solution;
            for(Iterator<Map<String, Term>> solutionIterator = q.iterator(); solutionIterator.hasNext();) {
                solution = solutionIterator.next();
                for(Iterator<Map.Entry<String, Term>> iter = solution.entrySet().iterator(); iter.hasNext();) {
                    toRet.add(new Solution(iter.next().getValue().intValue(),iter.next().getValue().intValue(), FieldState.SAFE));
                }
            }
        }
        return toRet;
    }
}
