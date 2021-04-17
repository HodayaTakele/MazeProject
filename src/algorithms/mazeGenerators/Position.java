package algorithms.mazeGenerators;

import java.util.Objects;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) throws IllegalArgumentException{
        if ( row < 0  || column < 0 ) throw new IllegalArgumentException("row and column must be positive integers.");
        this.row = row;
        this.column = column;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }

    @Override
    public String toString() {
        return "{" + row + ',' + column + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
