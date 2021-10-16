/**
 * This class determines the transpose of matrix with dimension m x n
 * The transpose of matrix results from the permutation of the rows with the columns
 * The matrix obtained has the size n x m
 */

package app.matrices;

/**
 *
 * @author André de Sousa
 */
public class Transpose {

    /**
     * This method determines the transpose of matrix
     *
     * @param matrixA
     * @return
     */
    public static int[][] transpose(int[][] matrixA) {
        int[][] transposeMatrix;

        //Review the matrix dimensions

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        //Validate and make transposed the matrixA

        if (linesA > 0 && columnsA > 0) {
            transposeMatrix = new int[columnsA][linesA];
            for (int i = 0; i < columnsA; i++) {
                for (int j = 0; j < linesA; j++) {
                    transposeMatrix[i][j] = matrixA[j][i];
                }
            }
        } else {
            transposeMatrix = null;
        }
        return transposeMatrix;
    }

    /**
     * This method determines the transpose of matrix
     *
     * @param matrixA
     * @return
     */
    public static float[][] transpose(float[][] matrixA) {
        float[][] transposeMatrix;

        //Review the matrix dimensions

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        //Validate and make transposed the matrixA

        if (linesA > 0 && columnsA > 0) {
            transposeMatrix = new float[columnsA][linesA];
            for (int i = 0; i < columnsA; i++) {
                for (int j = 0; j < linesA; j++) {
                    transposeMatrix[i][j] = matrixA[j][i];
                }
            }
        } else {
            transposeMatrix = null;
        }
        return transposeMatrix;
    }

    /**
     * This method determines the transpose of matrix
     *
     * @param matrixA
     * @return
     */
    public static double[][] transpose(double[][] matrixA) {
        double[][] transposeMatrix;

        //Review the matrix dimensions

        int linesA = matrixA.length;
        int columnsA = matrixA[0].length;

        //Validate and make transposed the matrixA

        if (linesA > 0 && columnsA > 0) {
            transposeMatrix = new double[columnsA][linesA];
            for (int i = 0; i < columnsA; i++) {
                for (int j = 0; j < linesA; j++) {
                    transposeMatrix[i][j] = matrixA[j][i];
                }
            }
        } else {
            transposeMatrix = null;
        }
        return transposeMatrix;
    }
}
