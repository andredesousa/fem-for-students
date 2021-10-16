/*
 * This class computes the inverse of a matrix
 * The matrix A ^ -1 is invertible if it is square and don't have lines of zeros
 * The matrix obtained is the same size that A
 */

package app.matrices;

/**
 *
 * @author André de Sousa
 */
public class Inverse {

    /**
     * This method computes the inverse of a matrix
     *
     * @param matrixA
     * @return
     */
    public static double[][] inverse(double[][] matrixA) {
        double[][] inverseMatrix;
        double[][] identityMatrix;

        //Review the matrix dimensions

        int lines = matrixA.length;
        int columns = matrixA[0].length;

        //Validate and inverse the matrixA

        if (lines == columns) {
            inverseMatrix = new double[lines][columns];
            identityMatrix = new double[lines][columns];

            for (int i = 0; i < lines; i++) {
                identityMatrix[i][i] = 1;
                System.arraycopy(matrixA[i], 0, inverseMatrix[i], 0, columns);
            }

            double multiple;
            boolean invertible = true;

            //PART 1: Cancel the terms below diagonal

            for (int i = 0; i < lines; i++) {
                //Operation for when the pivot is equal to 0
                if (inverseMatrix[i][i] == 0 && invertible == true) {
                    for (int ii = i + 1; ii < lines; ii++) {
                        if (inverseMatrix[ii][i] != 0) {
                            for (int jj = 0; jj < columns; jj++) {
                                inverseMatrix[i][jj] = inverseMatrix[i][jj] + inverseMatrix[ii][jj];
                                identityMatrix[i][jj] = identityMatrix[i][jj] + identityMatrix[ii][jj];
                            }
                        }
                    }

                    if (inverseMatrix[i][i] == 0) {
                        inverseMatrix = null;
                        invertible = false;
                        break;
                    }
                }

                //Operation for when the pivot is different from 0
                if (inverseMatrix[i][i] != 0) {
                    //Operation to turn the pivot on 1
                    if (inverseMatrix[i][i] != 1) {
                        multiple = inverseMatrix[i][i];
                        for (int jj = 0; jj < columns; jj++) {
                            if (inverseMatrix[i][jj] != 0) {
                                inverseMatrix[i][jj] = inverseMatrix[i][jj] / multiple;
                            }
                            if (identityMatrix[i][jj] != 0) {
                                identityMatrix[i][jj] = identityMatrix[i][jj] / multiple;
                            }
                        }
                    }

                    //Operation for when the pivot is 1
                    for (int ii = i + 1; ii < lines; ii++) {
                        if (inverseMatrix[ii][i] != 0) {
                            multiple = inverseMatrix[ii][i];
                            for (int jj = 0; jj < columns; jj++) {
                                inverseMatrix[ii][jj] = inverseMatrix[ii][jj] - multiple * inverseMatrix[i][jj];
                                identityMatrix[ii][jj] = identityMatrix[ii][jj] - multiple * identityMatrix[i][jj];
                            }
                        }
                    }
                }
            }

            //PART 2: Cancel the terms above diagonal

            if (invertible) {
                for (int i = (lines - 1); i >= 0; i--) {
                    if (inverseMatrix[i][i] == 1) {
                        for (int ii = i - 1; ii >= 0; ii--) {
                            if (inverseMatrix[ii][i] != 0) {
                                multiple = inverseMatrix[ii][i];
                                for (int jj = columns - 1; jj >= 0; jj--) {
                                    inverseMatrix[ii][jj] = inverseMatrix[ii][jj] - multiple * inverseMatrix[i][jj];
                                    identityMatrix[ii][jj] = identityMatrix[ii][jj] - multiple * identityMatrix[i][jj];
                                }
                            }
                        }
                    }
                }

                //InverseMatrix assign the value of the inverse matrix
                inverseMatrix = identityMatrix;
            }
        } else {
            inverseMatrix = null;
        }

        return inverseMatrix;
    }
}
