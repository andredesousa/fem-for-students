/**
 * This class performs an operation of subtraction of matrices
 * The subtraction A - B is the matrix obtained by subtracting the input B to the inputs A
 * The matrix obtained has the same size that A and B
 */

package app.matrices;

/**
 *
 * @author André de Sousa
 */
public class Subtract {

    /**
     * This method performs an operation of subtraction of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static int[][] subtract(int[][] matrixA, int[][] matrixB) {
        int[][] subtractMatrix;

        //Evaluation of the dimensions of the matrices to be subtracted

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and subtraction to the matrixA the matrixB

        if (linesA == linesB && columnsA == columnsB) {
            subtractMatrix = new int[linesA][columnsA];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsA; j++) {
                    subtractMatrix[i][j] = matrixA[i][j] - matrixB[i][j];
                }
            }
        } else {
            subtractMatrix = null;
        }

        return subtractMatrix;
    }

    /**
     * This method performs an operation of subtraction of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static float[][] subtract(float[][] matrixA, float[][] matrixB) {
        float[][] subtractMatrix;

        //Evaluation of the dimensions of the matrices to be subtracted

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and subtraction to the matrixA the matrixB

        if (linesA == linesB && columnsA == columnsB) {
            subtractMatrix = new float[linesA][columnsA];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsA; j++) {
                    subtractMatrix[i][j] = matrixA[i][j] - matrixB[i][j];
                }
            }
        } else {
            subtractMatrix = null;
        }

        return subtractMatrix;
    }

    /**
     * This method performs an operation of subtraction of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static double[][] subtract(double[][] matrixA, double[][] matrixB) {
        double[][] subtractMatrix;

        //Evaluation of the dimensions of the matrices to be subtracted

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and subtraction to the matrixA the matrixB

        if (linesA == linesB && columnsA == columnsB) {
            subtractMatrix = new double[linesA][columnsA];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsA; j++) {
                    subtractMatrix[i][j] = matrixA[i][j] - matrixB[i][j];
                }
            }
        } else {
            subtractMatrix = null;
        }

        return subtractMatrix;
    }
}
