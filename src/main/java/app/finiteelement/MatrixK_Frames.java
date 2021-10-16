/*
 * Esta classe fornece a matriz de rigidez de um elemento finito de barra
 * O método requer o comprimento e o número de nós do elemento finito
 * A matriz de rigidez calculada já considera os parâmetros EA e EI
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixK_Frames {

    /**
     * Este método fornece a matriz de rigidez de um elemento finito de barra
     *
     * @param L é o comprimento do elemento finito
     * @param EA é a rigidez axial do elemento finito
     * @param EI é a rigidez de flexão do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixK_Frames(double L, double EA, double EI, int nodes) {
        double[][] matrixK;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixK = new double[6][6];

                matrixK[0][0] = EA / L;
                matrixK[0][1] = 0;
                matrixK[0][2] = 0;
                matrixK[0][3] = -EA / L;
                matrixK[0][4] = 0;
                matrixK[0][5] = 0;

                matrixK[1][0] = 0;
                matrixK[1][1] = 12 * EI / (L * L * L);
                matrixK[1][2] = 6 * EI / (L * L);
                matrixK[1][3] = 0;
                matrixK[1][4] = -12 * EI / (L * L * L);
                matrixK[1][5] = 6 * EI / (L * L);

                matrixK[2][0] = 0;
                matrixK[2][1] = 6 * EI / (L * L);
                matrixK[2][2] = 4 * EI / L;
                matrixK[2][3] = 0;
                matrixK[2][4] = -6 * EI / (L * L);
                matrixK[2][5] = 2 * EI / L;

                matrixK[3][0] = -EA / L;
                matrixK[3][1] = 0;
                matrixK[3][2] = 0;
                matrixK[3][3] = EA / L;
                matrixK[3][4] = 0;
                matrixK[3][5] = 0;

                matrixK[4][0] = 0;
                matrixK[4][1] = -12 * EI / (L * L * L);
                matrixK[4][2] = -6 * EI / (L * L);
                matrixK[4][3] = 0;
                matrixK[4][4] = 12 * EI / (L * L * L);
                matrixK[4][5] = -6 * EI / (L * L);

                matrixK[5][0] = 0;
                matrixK[5][1] = 6 * EI / (L * L);
                matrixK[5][2] = 2 * EI / L;
                matrixK[5][3] = 0;
                matrixK[5][4] = -6 * EI / (L * L);
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
