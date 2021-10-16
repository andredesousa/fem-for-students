/*
 * Esta classe fornece a matriz B de um elemento finito unidimensional
 * O método requer a coordenada x, o comprimento e o número de nós
 * A matriz B é avaliada em função da coordena x
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_1D {

    /**
     * Este método fornece a matriz B de um elemento finito unidimensional
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_1D(double x, double L, int nodes) {
        double[][] matrixB;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixB = new double[1][4];

                matrixB[0][0] = -1 / L;
                matrixB[0][1] = 0;
                matrixB[0][2] = 1 / L;
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
