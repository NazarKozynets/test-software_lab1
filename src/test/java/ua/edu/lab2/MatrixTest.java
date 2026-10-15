package ua.edu.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    private static final double DELTA = 1e-9;
    private Matrix matrix;

    @BeforeEach
    void setUp() {
        matrix = new Matrix(new double[][]{{1, 2}, {3, 4}});
    }

    @Test
    @DisplayName("Додавання квадратних матриць повертає правильні елементи та розміри")
    void addsSquareMatrices() {
        Matrix other = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Matrix sum = matrix.add(other);

        assertAll(
                () -> assertEquals(2, sum.getRows()),
                () -> assertEquals(2, sum.getColumns()),
                () -> assertEquals(6, sum.get(0, 0), DELTA),
                () -> assertEquals(8, sum.get(0, 1), DELTA),
                () -> assertEquals(10, sum.get(1, 0), DELTA),
                () -> assertEquals(12, sum.get(1, 1), DELTA));
    }

    @Test
    @DisplayName("Додавання прямокутних матриць розміру 2 на 3")
    void addsRectangularMatrices() {
        Matrix left = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        Matrix right = new Matrix(new double[][]{{6, 5, 4}, {3, 2, 1}});

        Matrix sum = left.add(right);

        assertMatrixEquals(new double[][]{{7, 7, 7}, {7, 7, 7}}, sum);
    }

    @Test
    @DisplayName("Мінімальні матриці розміру 1 на 1 можна додавати")
    void addsSingleElementMatrices() {
        Matrix left = new Matrix(new double[][]{{2}});
        Matrix right = new Matrix(new double[][]{{3}});

        assertMatrixEquals(new double[][]{{5}}, left.add(right));
    }

    @Test
    @DisplayName("Додавання нульової матриці зберігає значення")
    void addingZeroMatrixPreservesValues() {
        Matrix zero = new Matrix(new double[][]{{0, 0}, {0, 0}});

        assertMatrixEquals(new double[][]{{1, 2}, {3, 4}}, matrix.add(zero));
    }

    @Test
    @DisplayName("Від’ємні та дробові числа додаються з допустимою похибкою")
    void addsNegativeAndFractionalValues() {
        Matrix left = new Matrix(new double[][]{{-1.5, 0.1}, {2.25, -4.5}});
        Matrix right = new Matrix(new double[][]{{0.5, 0.2}, {-3.0, 1.25}});

        assertMatrixEquals(new double[][]{{-1.0, 0.3}, {-0.75, -3.25}}, left.add(right));
    }

    @Test
    @DisplayName("Матриці з різною кількістю рядків або стовпців не додаються")
    void rejectsMismatchedDimensions() {
        Matrix differentRows = new Matrix(new double[][]{{1, 2}});
        Matrix differentColumns = new Matrix(new double[][]{{1}, {2}});

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> matrix.add(differentRows)),
                () -> assertThrows(IllegalArgumentException.class, () -> matrix.add(differentColumns)));
    }

    @Test
    @DisplayName("Додавання null замість другої матриці кидає виняток")
    void rejectsNullOperand() {
        assertThrows(IllegalArgumentException.class, () -> matrix.add(null));
    }

    @Test
    @DisplayName("Конструктор відхиляє null, порожню матрицю та порожні рядки")
    void rejectsNullAndEmptyArrays() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Matrix(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[0][])),
                () -> assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[][]{{}})));
    }

    @Test
    @DisplayName("Конструктор відхиляє нерівні рядки та рядки null")
    void rejectsRaggedAndNullRows() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Matrix(new double[][]{{1, 2}, {3}})),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Matrix(new double[][]{{1, 2}, {}})),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Matrix(new double[][]{null, {1, 2}})),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Matrix(new double[][]{{1, 2}, null})));
    }

    @Test
    @DisplayName("Зміна початкового масиву та його рядків не змінює матрицю")
    void copiesInputArrayDeeply() {
        double[][] source = {{1, 2}, {3, 4}};
        Matrix copy = new Matrix(source);

        source[0][0] = 99;
        source[1] = new double[]{88, 77};

        assertMatrixEquals(new double[][]{{1, 2}, {3, 4}}, copy);
    }

    @Test
    @DisplayName("Сума є новою матрицею, а обидва операнди залишаються незмінними")
    void additionDoesNotChangeOperands() {
        Matrix other = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Matrix sum = matrix.add(other);

        assertAll(
                () -> assertNotSame(matrix, sum),
                () -> assertNotSame(other, sum),
                () -> assertMatrixEquals(new double[][]{{1, 2}, {3, 4}}, matrix),
                () -> assertMatrixEquals(new double[][]{{5, 6}, {7, 8}}, other),
                () -> assertMatrixEquals(new double[][]{{6, 8}, {10, 12}}, sum));
    }

    @Test
    @DisplayName("Від’ємні індекси та індекси за верхньою межею відхиляються")
    void rejectsOutOfBoundsIndices() {
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(-1, 0)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(0, -1)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(2, 0)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(0, 2)));
    }

    private static void assertMatrixEquals(double[][] expected, Matrix actual) {
        assertEquals(expected.length, actual.getRows());
        assertEquals(expected[0].length, actual.getColumns());
        for (int row = 0; row < expected.length; row++) {
            for (int column = 0; column < expected[row].length; column++) {
                assertEquals(expected[row][column], actual.get(row, column), DELTA,
                        "Елемент [" + row + "][" + column + "]");
            }
        }
    }
}
