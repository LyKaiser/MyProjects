package sudoku;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
  
  private Map<String, Set<Integer>> board = new HashMap<>();

  private static final Set<Integer> newVariable = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

  public Board() {
    for (int y = 1; y <= 9; y++) {
      for (int x = 1; x <= 9; x++) {
        board.put(name(y, x), newVariable);
      }
    }
  }

  public Set<Integer> get(String name) {
    return board.get(name);
  }

  public Board set(String name, int value) {
    final Map<String, Set<Integer>> newBoard = new HashMap<>(board);
    newBoard.put(name, Collections.singleton(value));
    return new Board(Collections.unmodifiableMap(newBoard));
  }

  public Board eliminate(String name, int value) {
    final Map<String, Set<Integer>> newBoard = new HashMap<>(board);
    final Set<Integer> values = new HashSet<>(board.get(name));
    values.remove(value);
    newBoard.put(name, values);
    return new Board(Collections.unmodifiableMap(newBoard));
  }

  public Set<String> variables() {
    return board.keySet();
  }

  private Board(Map<String, Set<Integer>> board) {
    this.board = board;
  }

  @Override
  public String toString() {
    final StringBuilder b = new StringBuilder();

    for (int y = 1; y <= 9; y++) {
      for (int x = 1; x <= 9; x++) {
        b.append(variableToString(board.get(name(y, x))));
        b.append(getSpace(x, " ", "    "));
      }
      b.append(getSpace(y, "\n", "\n\n\n"));
    }

    return b.toString();
  }

  private static String getSpace(int i, String normalSpace, String bigSpace) {
    if (i == 9) {
      return "";
    } else if (i % 3 == 0) {
      return bigSpace;
    } else {
      return normalSpace;
    }
  }

  public static String name(int y, int x) {
    return "" + y + x;
  }

  public static String variableToString(Set<Integer> possibleValues) {
    return "(" +
            IntStream.range(1, 10)
                    .mapToObj(i -> possibleValues.contains(i) ? "" + i : " ")
                    .collect(Collectors.joining("")) +
            ")";
  }

  @Override
  public boolean equals(Object other) {
    return (other instanceof Board) && ((Board) other).board.equals(board);
  }

  @Override
  public int hashCode() {
    return board.hashCode();
  }

}
