package com.enlil.universe.matter.chemistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A matrix of exact fractions, each a {@code long} numerator over a positive {@code long} denominator reduced by
 * their gcd, enough to balance chemical equations without rounding.
 */
final class RationalMatrix {

    private final long[][] numerators;
    private final long[][] denominators;
    private final int rows;
    private final int columns;

    RationalMatrix(long[][] entries) {
        this.rows = entries.length;
        this.columns = rows == 0 ? 0 : entries[0].length;
        this.numerators = new long[rows][columns];
        this.denominators = new long[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                numerators[row][column] = entries[row][column];
                denominators[row][column] = 1;
            }
        }
    }

    /**
     * A basis of the null space (the vectors x with Ax = 0), from the reduced row echelon form by Gauss–Jordan
     * elimination: one vector per free column, scaled to coprime integers.
     */
    List<long[]> nullSpace() {
        List<Integer> pivotColumns = reduceToRowEchelonForm();
        List<long[]> basis = new ArrayList<>();
        for (int free = 0; free < columns; free++) {
            if (pivotColumns.contains(free)) {
                continue;
            }
            // x_free = 1, the other free variables 0, and each pivot variable = −(its row's entry in the free column)
            long[] vectorNumerators = new long[columns];
            long[] vectorDenominators = new long[columns];
            Arrays.fill(vectorDenominators, 1);
            vectorNumerators[free] = 1;
            for (int row = 0; row < pivotColumns.size(); row++) {
                vectorNumerators[pivotColumns.get(row)] = -numerators[row][free];
                vectorDenominators[pivotColumns.get(row)] = denominators[row][free];
            }
            basis.add(toCoprimeIntegers(vectorNumerators, vectorDenominators));
        }
        return basis;
    }

    /** Reduces this matrix in place and returns the pivot column of each nonzero row, top to bottom. */
    private List<Integer> reduceToRowEchelonForm() {
        List<Integer> pivotColumns = new ArrayList<>();
        int pivotRow = 0;
        for (int column = 0; column < columns && pivotRow < rows; column++) {
            int found = -1;
            for (int row = pivotRow; row < rows; row++) {
                if (numerators[row][column] != 0) {
                    found = row;
                    break;
                }
            }
            if (found < 0) {
                continue;
            }
            swapRows(found, pivotRow);
            // scale the pivot row so the pivot is 1
            long pivotNumerator = numerators[pivotRow][column];
            long pivotDenominator = denominators[pivotRow][column];
            for (int c = 0; c < columns; c++) {
                set(pivotRow, c, numerators[pivotRow][c] * pivotDenominator,
                        denominators[pivotRow][c] * pivotNumerator);
            }
            // clear the column in every other row
            for (int row = 0; row < rows; row++) {
                if (row == pivotRow || numerators[row][column] == 0) {
                    continue;
                }
                long factorNumerator = numerators[row][column];
                long factorDenominator = denominators[row][column];
                for (int c = 0; c < columns; c++) {
                    // entry − factor × pivot row entry
                    long productNumerator = factorNumerator * numerators[pivotRow][c];
                    long productDenominator = factorDenominator * denominators[pivotRow][c];
                    set(row, c, numerators[row][c] * productDenominator - productNumerator * denominators[row][c],
                            denominators[row][c] * productDenominator);
                }
            }
            pivotColumns.add(column);
            pivotRow++;
        }
        return pivotColumns;
    }

    private void swapRows(int a, int b) {
        long[] numeratorRow = numerators[a];
        numerators[a] = numerators[b];
        numerators[b] = numeratorRow;
        long[] denominatorRow = denominators[a];
        denominators[a] = denominators[b];
        denominators[b] = denominatorRow;
    }

    private void set(int row, int column, long numerator, long denominator) {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        long gcd = gcd(Math.abs(numerator), denominator);
        numerators[row][column] = numerator == 0 ? 0 : numerator / gcd;
        denominators[row][column] = numerator == 0 ? 1 : denominator / gcd;
    }

    private static long[] toCoprimeIntegers(long[] numerators, long[] denominators) {
        long lcm = 1;
        for (long denominator : denominators) {
            lcm = lcm / gcd(lcm, denominator) * denominator;
        }
        long[] integers = new long[numerators.length];
        long common = 0;
        for (int i = 0; i < numerators.length; i++) {
            integers[i] = numerators[i] * (lcm / denominators[i]);
            common = gcd(common, Math.abs(integers[i]));
        }
        for (int i = 0; i < integers.length; i++) {
            integers[i] /= common;
        }
        return integers;
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
