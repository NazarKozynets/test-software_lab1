package ua.edu.lab2;

/** Незмінна прямокутна матриця з операцією поелементного додавання. */
public final class Matrix {
    private final double[][] values;
    private final int rows;
    private final int columns;

    /** Створює незалежну копію непорожнього прямокутного масиву. */
    public Matrix(double[][] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Матриця має містити хоча б один рядок");
        }
        if (values[0] == null || values[0].length == 0) {
            throw new IllegalArgumentException("Рядок матриці має містити хоча б один елемент");
        }

        rows = values.length;
        columns = values[0].length;
        this.values = new double[rows][];
        for (int row = 0; row < rows; row++) {
            if (values[row] == null || values[row].length != columns) {
                throw new IllegalArgumentException("Усі рядки матриці мають бути однакової довжини");
            }
            this.values[row] = values[row].clone();
        }
    }

    /** Повертає суму матриць однакового розміру, не змінюючи операнди. */
    public Matrix add(Matrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Друга матриця не може бути null");
        }
        if (rows != other.rows || columns != other.columns) {
            throw new IllegalArgumentException("Розміри матриць мають збігатися");
        }

        double[][] result = new double[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                result[row][column] = values[row][column] + other.values[row][column];
            }
        }
        return new Matrix(result);
    }

    public double get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Індекс елемента виходить за межі матриці");
        }
        return values[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
