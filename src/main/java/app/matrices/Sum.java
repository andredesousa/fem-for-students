/**
 * This class performs an operation of sum of matrices
 * The sum A + B is the matrix obtained by adding the input B with the inputs A
 * The matrix obtained has the same size that A and B
 */

package app.matrices;

/**
 *
 * @author André de Sousa
 */
public class Sum {

    /**
     * This method performs an operation of sum of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static int[][] sum(int[][] matrixA, int[][] matrixB) {
        int[][] sumMatrix;

        //Evaluation of the dimensions of the matrixs to add up

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and sum of matrixA with matrixB

        if (linesA == linesB && columnsA == columnsB) {
            sumMatrix = new int[linesA][columnsA];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsA; j++) {
                    sumMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
                }
            }
        } else {
            sumMatrix = null;
        }

        return sumMatrix;
    }

    /**
     * This method performs an operation of sum of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static float[][] sum(float[][] matrixA, float[][] matrixB) {
        float[][] sumMatrix;

        //Evaluation of the dimensions of the matrixs to add up

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and sum of matrixA with matrixB

        if (linesA == linesB && columnsA == columnsB) {
            sumMatrix = new float[linesA][columnsA];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsA; j++) {
                    sumMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
                }
            }
        } else {
            sumMatrix = null;
        }

        return sumMatrix;
    }

    /**
     * This method performs an operation of sum of matrices
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static double[][] sum(double[][] matrixA, double[][] matrixB) {
        double[][] sumMatrix;

        //Evaluation of the dimensions of the matrixs to add up

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        int linesB = matrixB.length;
        int columnsB = matrixB[0].length;

        //Validation and sum of matrixA with matrixB

        if (linesA == linesB && columnsA == columnsB) {
            sumMatrix = new double[linesA][columnsA];
            for (int i = 0; i < linesA; i++) {
                for (int j = 0; j < columnsA; j++) {
                    sumMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
                }
            }
        } else {
            sumMatrix = null;
        }

        return sumMatrix;
    }
}
