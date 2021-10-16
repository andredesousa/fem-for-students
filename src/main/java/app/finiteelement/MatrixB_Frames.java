/*
 * Esta classe fornece a matriz B de um elemento do tipo barra
 * O método requer a coordenada x, o comprimento e o número de nós
 * A matriz B é avaliada em função da coordena x
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_Frames {

    /**
     * Este método fornece a matriz B de um elemento do tipo barra
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_Frames(double x, double L, int nodes) {
        double[][] matrixB;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixB = new double[2][6];

                matrixB[0][0] = -1 / L;
                matrixB[0][1] = 0;
                matrixB[0][2] = 0;
                matrixB[0][3] = 1 / L;
                matrixB[0][4] = 0;
                matrixB[0][5] = 0;

                matrixB[1][0] = 0;
                matrixB[1][1] = -12 * x / (L * L * L);
                matrixB[1][2] = 1 / L - 6 * x / (L * L);
                matrixB[1][3] = 0;
                matrixB[1][4] = 12 * x / (L * L * L);
                matrixB[1][5] = -1 / L - 6 * x / (L * L);
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }
}
