/*
 * Esta classe fornece a matriz B de um elemento do tipo viga
 * O método requer a coordenada x, o comprimento e o número de nós
 * A matriz B é avaliada em função da coordena x
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_Beams {

    /**
     * Este método fornece a matriz B de um elemento do tipo viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_EulerBernoulli(double x, double L, int nodes) {
        double[][] matrixB;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixB = new double[1][4];

                matrixB[0][0] = -12 * x / (L * L * L);
                matrixB[0][1] = 1 / L - 6 * x / (L * L);
                matrixB[0][2] = 12 * x / (L * L * L);
                matrixB[0][3] = -1 / L - 6 * x / (L * L);
            } else if (nodes == 3) {
                matrixB = new double[1][6];

                double x3 = x * x * x;
                double L3 = L * L * L;
                double L4 = L3 * L;
                double L5 = L3 * L * L;

                matrixB[0][0] = -(8 / (L * L) - 60 * x / (L3) - 96 * (x * x) / L4 + 480 * (x3) / L5);
                matrixB[0][1] = -(1 / L - 6 * x / (L * L) - 24 * (x * x) / (L3) + 80 * (x3) / L4);
                matrixB[0][2] = -(-16 / (L * L) + 192 * (x * x) / L4);
                matrixB[0][3] = -(-48 * x / (L * L) + 320 * (x3) / L4);
                matrixB[0][4] = -(8 / (L * L) + 60 * x / (L3) - 96 * (x * x) / L4 - 480 * (x3) / L5);
                matrixB[0][5] = -(-1 / L - 6 * x / (L * L) + 24 * (x * x) / (L3) + 80 * (x3) / L4);
            } else if (nodes == 4) {
                matrixB = new double[1][8];

                double x2 = x * x;
                double x3 = x * x * x;
                double L2 = L * L;
                double L3 = L * L * L;
                double L4 = L3 * L;
                double L5 = L3 * L * L;

                matrixB[0][0] =
                    (3 * (81 * L5 - 562 * x * L4 - 9720 * x2 * L3 + 37680 * x3 * L2 + 58320 * x3 * x * L - 199584 * x3 * x2)) /
                    (64 * L5 * L2);
                matrixB[0][1] =
                    (19 * L5 - 114 * x * L4 - 2376 * x2 * L3 + 7920 * x3 * L2 + 19440 * x3 * x * L - 54432 * x3 * x2) / (64 * L5 * L);
                matrixB[0][2] = -(243 * (L + 2 * x) * (L4 + 112 * x * L3 - 344 * x2 * L2 - 1152 * x3 * L + 3024 * x3 * x)) / (64 * L5 * L2);
                matrixB[0][3] =
                    (27 * (11 * L5 - 198 * x * L4 - 456 * x2 * L3 + 4560 * x3 * L2 + 2160 * x3 * x * L - 18144 * x3 * x2)) / (64 * L5 * L);
                matrixB[0][4] = -(243 * (L - 2 * x) * (L4 - 112 * x * L3 - 344 * x2 * L2 + 1152 * x3 * L + 3024 * x3 * x)) / (64 * L5 * L2);
                matrixB[0][5] =
                    -(27 * (11 * L5 + 198 * x * L4 - 456 * x2 * L3 - 4560 * x3 * L2 + 2160 * x3 * x * L + 18144 * x3 * x2)) / (64 * L5 * L);
                matrixB[0][6] =
                    (3 * (81 * L5 + 562 * x * L4 - 9720 * x2 * L3 - 37680 * x3 * L2 + 58320 * x3 * x * L + 199584 * x3 * x2)) /
                    (64 * L5 * L2);
                matrixB[0][7] =
                    -(19 * L5 + 114 * x * L4 - 2376 * x2 * L3 - 7920 * x3 * L2 + 19440 * x3 * x * L + 54432 * x3 * x2) / (64 * L5 * L);
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }

    /**
     * Este método fornece a matriz Bb de um elemento do tipo viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixBb_Timoshenko(double x, double L, int nodes) {
        double[][] matrixBb;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixBb = new double[1][4];

                matrixBb[0][0] = 0;
                matrixBb[0][1] = 1 / L;
                matrixBb[0][2] = 0;
                matrixBb[0][3] = -1 / L;
            } else if (nodes == 3) {
                matrixBb = new double[1][6];

                matrixBb[0][0] = 0;
                matrixBb[0][1] = 1 / L - 4 * x / (L * L);
                matrixBb[0][2] = 0;
                matrixBb[0][3] = 8 * x / (L * L);
                matrixBb[0][4] = 0;
                matrixBb[0][5] = -1 / L - 4 * x / (L * L);
            } else if (nodes == 4) {
                matrixBb = new double[1][8];

                double L3 = L * L * L;

                matrixBb[0][0] = 0;
                matrixBb[0][1] = -1 / (8 * L) - (9 * x) / (2 * L * L) + (27 * x * x) / (2 * L3);
                matrixBb[0][2] = 0;
                matrixBb[0][3] = 27 / (8 * L) + (9 * x) / (2 * L * L) - (81 * x * x) / (2 * L3);
                matrixBb[0][4] = 0;
                matrixBb[0][5] = -27 / (8 * L) + (9 * x) / (2 * L * L) + (81 * x * x) / (2 * L3);
                matrixBb[0][6] = 0;
                matrixBb[0][7] = 1 / (8 * L) - (9 * x) / (2 * L * L) - (27 * x * x) / (2 * L3);
            } else {
                matrixBb = null;
            }
        } else {
            matrixBb = null;
        }

        return matrixBb;
    }

    /**
     * Este método fornece a matriz Bs de um elemento do tipo viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixBs_Timoshenko(double x, double L, int nodes) {
        double[][] matrixBs;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixBs = new double[1][4];

                matrixBs[0][0] = -1.0 / L;
                matrixBs[0][1] = -1.0 / 2.0 + x / L;
                matrixBs[0][2] = 1.0 / L;
                matrixBs[0][3] = -1.0 / 2.0 - x / L;
            } else if (nodes == 3) {
                matrixBs = new double[1][6];

                matrixBs[0][0] = -1 / L + 4 * x / (L * L);
                matrixBs[0][1] = x / L - 2 * (x * x) / (L * L);
                matrixBs[0][2] = -8 * x / (L * L);
                matrixBs[0][3] = -1 + 4 * (x * x) / (L * L);
                matrixBs[0][4] = 1 / L + 4 * x / (L * L);
                matrixBs[0][5] = -x / L - 2 * (x * x) / (L * L);
            } else if (nodes == 4) {
                matrixBs = new double[1][8];

                double x3 = x * x * x;
                double L3 = L * L * L;

                matrixBs[0][0] = 1 / (8 * L) + (9 * x) / (2 * L * L) - (27 * x * x) / (2 * L3);
                matrixBs[0][1] = -x / (8 * L) - (9 * x * x) / (4 * L * L) + (9 * x3) / (2 * L3) + 1.0 / 16.0;
                matrixBs[0][2] = -27 / (8 * L) - (9 * x) / (2 * L * L) + (81 * x * x) / (2 * L3);
                matrixBs[0][3] = (27 * x) / (8 * L) + (9 * x * x) / (4 * L * L) - (27 * x3) / (2 * L3) - 9.0 / 16.0;
                matrixBs[0][4] = 27 / (8 * L) - (9 * x) / (2 * L * L) - (81 * x * x) / (2 * L3);
                matrixBs[0][5] = -(27 * x) / (8 * L) + (9 * x * x) / (4 * L * L) + (27 * x3) / (2 * L3) - 9.0 / 16.0;
                matrixBs[0][6] = -1 / (8 * L) + (9 * x) / (2 * L * L) + (27 * x * x) / (2 * L3);
                matrixBs[0][7] = x / (8 * L) - (9 * x * x) / (4 * L * L) - (9 * x3) / (2 * L3) + 1.0 / 16.0;
            } else {
                matrixBs = null;
            }
        } else {
            matrixBs = null;
        }

        return matrixBs;
    }
}
