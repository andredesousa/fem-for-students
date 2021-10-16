/**
 * This class performs the operation of multiplication of matrices
 * The multiplication A * B requires that the number of columns of A is equal to the number of rows of B
 * The Multiplication a * B returns a multiple matriz of B
 */

package app.matrices;

/**
 *
 * @author André de Sousa
 */
public class Multiply {

    /**
     * This method performs the operation of multiplication of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static int[][] multiply(int[][] matrixA, int[][] matrixB) {
        int[][] multiplyMatrix;

        //Evaluation of the dimensions of the matrixs to multiply

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and multiplication of matrixA with matrixB

        if (columnsA == linesB) {
            multiplyMatrix = new int[linesA][columnsB];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsB; j++) {
                    multiplyMatrix[i][j] = 0;
                    for (int k = 0; k < columnsA; k++) {
                        multiplyMatrix[i][j] = multiplyMatrix[i][j] + matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        } else {
            multiplyMatrix = null;
        }

        return multiplyMatrix;
    }

    /**
     * This method performs the operation of multiplication of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static float[][] multiply(float[][] matrixA, float[][] matrixB) {
        float[][] multiplyMatrix;

        //Evaluation of the dimensions of the matrixs to multiply

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and multiplication of matrixA with matrixB

        if (columnsA == linesB) {
            multiplyMatrix = new float[linesA][columnsB];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsB; j++) {
                    multiplyMatrix[i][j] = 0;
                    for (int k = 0; k < columnsA; k++) {
                        multiplyMatrix[i][j] = multiplyMatrix[i][j] + matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        } else {
            multiplyMatrix = null;
        }

        return multiplyMatrix;
    }

    /**
     * This method performs the operation of multiplication of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
        double[][] multiplyMatrix;

        //Evaluation of the dimensions of the matrixs to multiply

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and multiplication of matrixA with matrixB

        if (columnsA == linesB) {
            multiplyMatrix = new double[linesA][columnsB];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsB; j++) {
                    multiplyMatrix[i][j] = 0;
                    for (int k = 0; k < columnsA; k++) {
                        multiplyMatrix[i][j] = multiplyMatrix[i][j] + matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        } else {
            multiplyMatrix = null;
        }

        return multiplyMatrix;
    }

    /**
     * This method performs the multiplication operation of a scalar with a matrix
     *
     * @param scalarA
     * @param matrixB
     * @return
     */
    public static int[][] multiply(int scalarA, int[][] matrixB) {
        int[][] multiplyMatrix;

        //Review the matrix dimensions

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and multiplication of scalarA with matrixB

        if (linesB > 0 && columnsB > 0) {
            multiplyMatrix = new int[linesB][columnsB];
            for (int i = 0; i < linesB; i++) {
                for (int j = 0; j < columnsB; j++) {
                    multiplyMatrix[i][j] = scalarA * matrixB[i][j];
                }
            }
        } else {
            multiplyMatrix = null;
        }

        return multiplyMatrix;
    }

    /**
     * This method performs the multiplication operation of a scalar with a matrix
     *
     * @param scalarA
     * @param matrixB
     * @return
     */
    public static float[][] multiply(float scalarA, float[][] matrixB) {
        float[][] multiplyMatrix;

        //Review the matrix dimensions

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and multiplication of scalarA with matrixB

        if (linesB > 0 && columnsB > 0) {
            multiplyMatrix = new float[linesB][columnsB];
            for (int i = 0; i < linesB; i++) {
                for (int j = 0; j < columnsB; j++) {
                    multiplyMatrix[i][j] = scalarA * matrixB[i][j];
                }
            }
        } else {
            multiplyMatrix = null;
        }

        return multiplyMatrix;
    }

    /**
     * This method performs the multiplication operation of a scalar with a matrix
     *
     * @param scalarA
     * @param matrixB
     * @return
     */
    public static double[][] multiply(double scalarA, double[][] matrixB) {
        double[][] multiplyMatrix;

        //Review the matrix dimensions

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and multiplication of scalarA with matrixB

        if (linesB > 0 && columnsB > 0) {
            multiplyMatrix = new double[linesB][columnsB];
            for (int i = 0; i < linesB; i++) {
                for (int j = 0; j < columnsB; j++) {
                    multiplyMatrix[i][j] = scalarA * matrixB[i][j];
                }
            }
        } else {
            multiplyMatrix = null;
        }

        return multiplyMatrix;
    }
}
