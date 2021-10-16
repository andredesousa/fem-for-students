/*
 * Esta classe fornece as funções de forma de elementos finitos de grelha
 * O método requer a coordenada x, o comprimento e o número de nós
 * A matriz Nv é avaliada em função da coordena x
 */

package app.shapefunctions;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv_Grids {

    /**
     * Este método fornece a matriz Nv de um elemento do tipo grelha
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_Torsion(double x, double L, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 2) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = 1.0 / 2.0 - x / L;
                matrixNv[1][0] = 1.0 / 2.0 + x / L;
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }

    /**
     * Este método fornece a matriz Nv de um elemento do tipo grelha
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_Bending(double x, double L, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 2) {
                matrixNv = new double[nodes * 2][1];

                matrixNv[0][0] = 0.5 - 3 * x / (2 * L) + 2 * x * x * x / (L * L * L);
                matrixNv[1][0] = L / 8 - x / 4 - x * x / (2 * L) + x * x * x / (L * L);
                matrixNv[2][0] = 0.5 + 3 * x / (2 * L) - 2 * x * x * x / (L * L * L);
                matrixNv[3][0] = -L / 8 - x / 4 + x * x / (2 * L) + x * x * x / (L * L);
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }
}
