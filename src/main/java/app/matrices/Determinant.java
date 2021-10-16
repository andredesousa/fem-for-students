/*
 * This class calculates the determinant of a matrix
 * The determinant is calculated only if the matrix A is square
 * If the determinant is zero the matrix A is not invertible
 */

package app.matrices;

/**
 *
 * @author André de Sousa
 */
public class Determinant {

    /**
     * This method calculates the determinant of a matrix
     *
     * @param matrixA
     * @return
     */
    public static double determinant(double[][] matrixA) {
        double determinant;

        //Review the matrix dimensions

        int lines = matrixA.length;
        int columns = matrixA[0].length;

        //Validate and inverse the matrixA

        if (lines == columns) {
            determinant = 0;

            //Working copy of the matrixA
            double[][] copyMatrix = new double[lines][columns];

            for (int i = 0; i < lines; i++) {
                System.arraycopy(matrixA[i], 0, copyMatrix[i], 0, columns);
            }

            double multiple, scalar;
            scalar = 1;

            //PART 1: Cancel the terms below diagonal

            for (int i = 0; i < lines; i++) {
                //Operation for when the pivot is equal to 0
                if (copyMatrix[i][i] == 0) {
                    for (int ii = i + 1; ii < lines; ii++) {
                        if (copyMatrix[ii][i] != 0) {
                            for (int jj = 0; jj < columns; jj++) {
                                copyMatrix[i][jj] = copyMatrix[i][jj] + copyMatrix[ii][jj];
                            }
                        }
                    }

                    if (copyMatrix[i][i] == 0) {
                        scalar = 0;
                        break;
                    }
                }

                //Operation for when the pivot is different from 0
                if (copyMatrix[i][i] != 0) {
                    //Operation to turn the pivot on 1
                    if (copyMatrix[i][i] != 1) {
                        multiple = copyMatrix[i][i];
                        scalar = scalar * copyMatrix[i][i];
                        for (int jj = 0; jj < columns; jj++) {
                            if (copyMatrix[i][jj] != 0) {
                                copyMatrix[i][jj] = copyMatrix[i][jj] / multiple;
                            }
                        }
                    }

                    //Operation for when the pivot is 1
                    for (int ii = i + 1; ii < lines; ii++) {
                        if (copyMatrix[ii][i] != 0) {
                            multiple = copyMatrix[ii][i];
                            for (int jj = 0; jj < columns; jj++) {
                                copyMatrix[ii][jj] = copyMatrix[ii][jj] - multiple * copyMatrix[i][jj];
                            }
                        }
                    }
                }
            }

            //PART 2: Calculating the value of the determinant

            if (scalar != 0) {
                determinant = 1;
                for (int i = 0; i < lines; i++) {
                    determinant = determinant * copyMatrix[i][i];
                }
                determinant = determinant * scalar;
            }
        } else {
            determinant = 0;
        }

        return determinant;
    }
}
