/*
 * Esta classe fornece a matriz B de um elemento finito unidimensional
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_1D {

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB é a matriz das derivadas das funções de forma
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_1D(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
            if (nodes == 2) {
                matrixB = new double[1][2];

                matrixB[0][0] = 0;
                matrixB[0][1] = 0;
            } else if (nodes == 3) {
                matrixB = new double[1][3];

                matrixB[0][0] = 0;
                matrixB[0][1] = 0;
                matrixB[0][2] = 0;
            } else if (nodes == 4) {
                matrixB = new double[1][4];

                matrixB[0][0] = 0;
                matrixB[0][1] = 0;
                matrixB[0][2] = 0;
                matrixB[0][3] = 0;
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }
}
