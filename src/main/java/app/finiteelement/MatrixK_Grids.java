/*
 * Esta classe fornece a matriz de rigidez de um elemento de grelha
 * O método requer o comprimento e o número de nós do elemento finito
 * A matriz de rigidez calculada já considera os parâmetros EI e GJ
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixK_Grids {

    /**
     * Este método fornece a matriz de rigidez de um elemento de grelha
     *
     * @param L é o comprimento do elemento finito
     * @param EI é a rigidez de flexão do elemento finito
     * @param GJ é a rigidez de torção do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixK_Grids(double L, double EI, double GJ, int nodes) {
        double[][] matrixK;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixK = new double[6][6];

                matrixK[0][0] = 12 * EI / (L * L * L);
                matrixK[0][1] = 0;
                matrixK[0][2] = 6 * EI / (L * L);
                matrixK[0][3] = -12 * EI / (L * L * L);
                matrixK[0][4] = 0;
                matrixK[0][5] = 6 * EI / (L * L);

                matrixK[1][0] = 0;
                matrixK[1][1] = GJ / L;
                matrixK[1][2] = 0;
                matrixK[1][3] = 0;
                matrixK[1][4] = -GJ / L;
                matrixK[1][5] = 0;

                matrixK[2][0] = 6 * EI / (L * L);
                matrixK[2][1] = 0;
                matrixK[2][2] = 4 * EI / L;
                matrixK[2][3] = -6 * EI / (L * L);
                matrixK[2][4] = 0;
                matrixK[2][5] = 2 * EI / L;

                matrixK[3][0] = -12 * EI / (L * L * L);
                matrixK[3][1] = 0;
                matrixK[3][2] = -6 * EI / (L * L);
                matrixK[3][3] = 12 * EI / (L * L * L);
                matrixK[3][4] = 0;
                matrixK[3][5] = -6 * EI / (L * L);

                matrixK[4][0] = 0;
                matrixK[4][1] = -GJ / L;
                matrixK[4][2] = 0;
                matrixK[4][3] = 0;
                matrixK[4][4] = GJ / L;
                matrixK[4][5] = 0;

                matrixK[5][0] = 6 * EI / (L * L);
                matrixK[5][1] = 0;
                matrixK[5][2] = 2 * EI / L;
                matrixK[5][3] = -6 * EI / (L * L);
                matrixK[5][4] = 0;
                matrixK[5][5] = 4 * EI / L;
            } else {
                matrixK = null;
            }
        } else {
            matrixK = null;
        }

        return matrixK;
    }
}
