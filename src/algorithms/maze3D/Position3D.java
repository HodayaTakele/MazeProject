package algorithms.maze3D;

import java.util.Objects;

public class Position3D {
    private int depth;
    private int row;
    private int column;

    public Position3D(int depth, int row, int column) throws IllegalArgumentException {
        if (depth < 0 || row < 0 || column < 0)
            throw new IllegalArgumentException("depth, row and column must be positive integers.");
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    public int getDepthIndex() {
        return depth;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }

    @Override
    public String toString() {
        return "{" + depth + ',' + row + ',' + column + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position3D)) return false;
        Position3D Position3D = (Position3D) o;
        return depth == Position3D.depth && row == Position3D.row && column == Position3D.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, row, column);
    }
}
