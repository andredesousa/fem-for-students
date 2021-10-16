/*
 * Esta classe fornece a matriz de rigidez de um elemento finito unidimensional
 * O método requer o comprimento e o número de nós do elemento finito
 * A matriz de rigidez calculada não considera o parâmetro EA
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixK_1D {

    /**
     * Este método fornece a matriz de rigidez de um elemento finito unidimensional
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixK_1D(double L, int nodes) {
        double[][] matrixK;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixK = new double[4][4];

                matrixK[0][0] = 1 / L;
                matrixK[0][1] = 0;
                matrixK[0][2] = -1 / L;
                matrixK[0][3] = 0;

                matrixK[1][0] = 0;
                matrixK[1][1] = 0;
                matrixK[1][2] = 0;
                matrixK[1][3] = 0;

                matrixK[2][0] = -1 / L;
                matrixK[2][1] = 0;
                matrixK[2][2] = 1 / L;
                matrixK[2][3] = 0;

                matrixK[3][0] = 0;
                matrixK[3][1] = 0;
                matrixK[3][2] = 0;
                matrixK[3][3] = 0;
            } else {
                matrixK = null;
            }
        } else {
            matrixK = null;
        }

        return matrixK;
    }
}
