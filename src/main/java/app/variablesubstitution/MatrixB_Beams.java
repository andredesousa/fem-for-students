/*
 * Esta classe fornece a matriz B de um elemento do tipo viga
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_Beams {

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_EulerBernoulli(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
            if (nodes == 2) {
                matrixB = new double[1][4];

                matrixB[0][0] = derivedMatrixB[0][0];
                matrixB[0][1] = derivedMatrixB[1][0];
                matrixB[0][2] = derivedMatrixB[2][0];
                matrixB[0][3] = derivedMatrixB[3][0];
            } else if (nodes == 3) {
                matrixB = new double[1][6];

                matrixB[0][0] = derivedMatrixB[0][0];
                matrixB[0][1] = derivedMatrixB[1][0];
                matrixB[0][2] = derivedMatrixB[2][0];
                matrixB[0][3] = derivedMatrixB[3][0];
                matrixB[0][4] = derivedMatrixB[4][0];
                matrixB[0][5] = derivedMatrixB[5][0];
            } else if (nodes == 4) {
                matrixB = new double[1][8];

                matrixB[0][0] = derivedMatrixB[0][0];
                matrixB[0][1] = derivedMatrixB[1][0];
                matrixB[0][2] = derivedMatrixB[2][0];
                matrixB[0][3] = derivedMatrixB[3][0];
                matrixB[0][4] = derivedMatrixB[4][0];
                matrixB[0][5] = derivedMatrixB[5][0];
                matrixB[0][6] = derivedMatrixB[6][0];
                matrixB[0][7] = derivedMatrixB[7][0];
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }

    /**
     * Método para obter a matriz Bb do elemento finito
     *
     * @param derivedMatrixBb
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixBb_Timoshenko(double[][] derivedMatrixBb, int nodes) {
        double[][] matrixBb;

        if (derivedMatrixBb != null && nodes > 1) {
            if (nodes == 2) {
                matrixBb = new double[1][4];
                matrixBb[0][0] = 0;
                matrixBb[0][1] = 0;
                matrixBb[0][2] = 0;
                matrixBb[0][3] = 0;
            } else if (nodes == 3) {
                matrixBb = new double[1][6];
                matrixBb[0][0] = 0;
                matrixBb[0][1] = 0;
                matrixBb[0][2] = 0;
                matrixBb[0][3] = 0;
                matrixBb[0][4] = 0;
                matrixBb[0][5] = 0;
            } else if (nodes == 4) {
                matrixBb = new double[1][8];

                matrixBb[0][0] = 0;
                matrixBb[0][1] = 0;
                matrixBb[0][2] = 0;
                matrixBb[0][3] = 0;
                matrixBb[0][4] = 0;
                matrixBb[0][5] = 0;
                matrixBb[0][6] = 0;
                matrixBb[0][7] = 0;
            } else {
                matrixBb = null;
            }
        } else {
            matrixBb = null;
        }

        return matrixBb;
    }

    /**
     * Método para obter a matriz Bs do elemento finito
     *
     * @param derivedMatrixBs
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixBs_Timoshenko(double[][] derivedMatrixBs, int nodes) {
        double[][] matrixBs;

        if (derivedMatrixBs != null && nodes > 1) {
            if (nodes == 2) {
                matrixBs = new double[1][4];
                matrixBs[0][0] = 0;
                matrixBs[0][1] = 0;
                matrixBs[0][2] = 0;
                matrixBs[0][3] = 0;
            } else if (nodes == 3) {
                matrixBs = new double[1][6];
                matrixBs[0][0] = 0;
                matrixBs[0][1] = 0;
                matrixBs[0][2] = 0;
                matrixBs[0][3] = 0;
                matrixBs[0][4] = 0;
                matrixBs[0][5] = 0;
            } else if (nodes == 4) {
                matrixBs = new double[1][8];

                matrixBs[0][0] = 0;
                matrixBs[0][1] = 0;
                matrixBs[0][2] = 0;
                matrixBs[0][3] = 0;
                matrixBs[0][4] = 0;
                matrixBs[0][5] = 0;
                matrixBs[0][6] = 0;
                matrixBs[0][7] = 0;
            } else {
                matrixBs = null;
            }
        } else {
            matrixBs = null;
        }

        return matrixBs;
    }
}
