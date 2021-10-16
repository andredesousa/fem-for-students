/*
 * Esta classe fornece as funções de forma de elementos finitos unidimensionais
 * O método requer a coordenada x, o comprimento e o número de nós
 * A matriz Nv é avaliada em função da coordena x
 */

package app.shapefunctions;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv_1D {

    /**
     * Este método fornece a matriz Nv de um elemento finito unidimensional
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_1D(double x, double L, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 2) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = 1.0 / 2.0 - x / L;
                matrixNv[1][0] = 1.0 / 2.0 + x / L;
            } else if (nodes == 3) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = -x / L + 2.0 * x * x / (L * L);
                matrixNv[1][0] = 1 - 4.0 * x * x / (L * L);
                matrixNv[2][0] = x / L + 2.0 * x * x / (L * L);
            } else if (nodes == 4) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = x / (8 * L) + (9 * x * x) / (4 * L * L) - (9 * x * x * x) / (2 * L * L * L) - 1.0 / 16.0;
                matrixNv[1][0] = 9.0 / 16.0 - 27.0 * x / (8.0 * L) - 9.0 * x * x / (4.0 * L * L) + 27.0 * x * x * x / (2.0 * L * L * L);
                matrixNv[2][0] = 9.0 / 16.0 + 27.0 * x / (8.0 * L) - 9.0 * x * x / (4.0 * L * L) - 27.0 * x * x * x / (2.0 * L * L * L);
                matrixNv[3][0] = -x / (8 * L) + (9 * x * x) / (4 * L * L) + (9 * x * x * x) / (2 * L * L * L) - 1.0 / 16.0;
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }
}
