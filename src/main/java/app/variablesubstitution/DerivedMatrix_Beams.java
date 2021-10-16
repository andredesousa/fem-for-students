/*
 * Esta classe fornece as derivadas das funções de forma de um elemento finito
 * Os métodos requerem a coordenada r, e o número de nós do elemento finito
 * A matriz deve ser posteriormente multiplicada pelas coordenadas cartesianas
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class DerivedMatrix_Beams {

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eixo r
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrix_EulerBernoulli(double r, double L, int nodes) {
        double[][] matrixJ;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixJ = new double[4][1];

                matrixJ[0][0] = -12 * r / (L * L * L);
                matrixJ[1][0] = 1 / L - 6 * r / (L * L);
                matrixJ[2][0] = 12 * r / (L * L * L);
                matrixJ[3][0] = -1 / L - 6 * r / (L * L);
            } else if (nodes == 3) {
                matrixJ = new double[6][1];

                matrixJ[0][0] =
                    -(8 / (L * L) - 60 * r / (L * L * L) - 96 * (r * r) / (L * L * L * L) + 480 * (r * r * r) / (L * L * L * L * L));
                matrixJ[1][0] = -(1 / L - 6 * r / (L * L) - 24 * (r * r) / (L * L * L) + 80 * (r * r * r) / (L * L * L * L));
                matrixJ[2][0] = -(-16 / (L * L) + 192 * (r * r) / (L * L * L * L));
                matrixJ[3][0] = -(-48 * r / (L * L) + 320 * (r * r * r) / (L * L * L * L));
                matrixJ[4][0] =
                    -(8 / (L * L) + 60 * r / (L * L * L) - 96 * (r * r) / (L * L * L * L) - 480 * (r * r * r) / (L * L * L * L * L));
                matrixJ[5][0] = -(-1 / L - 6 * r / (L * L) + 24 * (r * r) / (L * L * L) + 80 * (r * r * r) / (L * L * L * L));
            } else if (nodes == 4) {
                matrixJ = new double[8][1];

                double r2 = r * r;
                double r3 = r * r * r;
                double L2 = L * L;
                double L3 = L * L * L;
                double L4 = L3 * L;
                double L5 = L3 * L * L;

                matrixJ[0][0] =
                    (3 * (81 * L5 - 562 * r * L4 - 9720 * r2 * L3 + 37680 * r3 * L2 + 58320 * r3 * r * L - 199584 * r3 * r2)) /
                    (64 * L5 * L2);
                matrixJ[1][0] =
                    (19 * L5 - 114 * r * L4 - 2376 * r2 * L3 + 7920 * r3 * L2 + 19440 * r3 * r * L - 54432 * r3 * r2) / (64 * L5 * L);
                matrixJ[2][0] = -(243 * (L + 2 * r) * (L4 + 112 * r * L3 - 344 * r2 * L2 - 1152 * r3 * L + 3024 * r3 * r)) / (64 * L5 * L2);
                matrixJ[3][0] =
                    (27 * (11 * L5 - 198 * r * L4 - 456 * r2 * L3 + 4560 * r3 * L2 + 2160 * r3 * r * L - 18144 * r3 * r2)) / (64 * L5 * L);
                matrixJ[4][0] = -(243 * (L - 2 * r) * (L4 - 112 * r * L3 - 344 * r2 * L2 + 1152 * r3 * L + 3024 * r3 * r)) / (64 * L5 * L2);
                matrixJ[5][0] =
                    -(27 * (11 * L5 + 198 * r * L4 - 456 * r2 * L3 - 4560 * r3 * L2 + 2160 * r3 * r * L + 18144 * r3 * r2)) / (64 * L5 * L);
                matrixJ[6][0] =
                    (3 * (81 * L5 + 562 * r * L4 - 9720 * r2 * L3 - 37680 * r3 * L2 + 58320 * r3 * r * L + 199584 * r3 * r2)) /
                    (64 * L5 * L2);
                matrixJ[7][0] =
                    -(19 * L5 + 114 * r * L4 - 2376 * r2 * L3 - 7920 * r3 * L2 + 19440 * r3 * r * L + 54432 * r3 * r2) / (64 * L5 * L);
            } else {
                matrixJ = null;
            }
        } else {
            matrixJ = null;
        }

        return matrixJ;
    }

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eixo r
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrixBb_Timoshenko(double r, double L, int nodes) {
        double[][] matrixJb;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixJb = new double[4][1];

                matrixJb[0][0] = 0;
                matrixJb[1][0] = 1 / L;
                matrixJb[2][0] = 0;
                matrixJb[3][0] = -1 / L;
            } else if (nodes == 3) {
                matrixJb = new double[6][1];

                matrixJb[0][0] = 0;
                matrixJb[1][0] = 1 / L - 4 * r / (L * L);
                matrixJb[2][0] = 0;
                matrixJb[3][0] = 8 * r / (L * L);
                matrixJb[4][0] = 0;
                matrixJb[5][0] = -1 / L - 4 * r / (L * L);
            } else if (nodes == 4) {
                matrixJb = new double[8][1];

                double L3 = L * L * L;

                matrixJb[0][0] = 0;
                matrixJb[1][0] = -1 / (8 * L) - (9 * r) / (2 * L * L) + (27 * r * r) / (2 * L3);
                matrixJb[2][0] = 0;
                matrixJb[3][0] = 27 / (8 * L) + (9 * r) / (2 * L * L) - (81 * r * r) / (2 * L3);
                matrixJb[4][0] = 0;
                matrixJb[5][0] = -27 / (8 * L) + (9 * r) / (2 * L * L) + (81 * r * r) / (2 * L3);
                matrixJb[6][0] = 0;
                matrixJb[7][0] = 1 / (8 * L) - (9 * r) / (2 * L * L) - (27 * r * r) / (2 * L3);
            } else {
                matrixJb = null;
            }
        } else {
            matrixJb = null;
        }

        return matrixJb;
    }

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eixo r
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrixBs_Timoshenko(double r, double L, int nodes) {
        double[][] matrixJs;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixJs = new double[4][1];

                matrixJs[0][0] = -1 / L;
                matrixJs[1][0] = -1 / 2 + r / L;
                matrixJs[2][0] = 1 / L;
                matrixJs[3][0] = -1 / 2 - r / L;
            } else if (nodes == 3) {
                matrixJs = new double[6][1];

                matrixJs[0][0] = -1 / L + 4 * r / (L * L);
                matrixJs[1][0] = r / L - 2 * (r * r) / (L * L);
                matrixJs[2][0] = -8 * r / (L * L);
                matrixJs[3][0] = -1 + 4 * (r * r) / (L * L);
                matrixJs[4][0] = 1 / L + 4 * r / (L * L);
                matrixJs[5][0] = -r / L - 2 * (r * r) / (L * L);
            } else if (nodes == 4) {
                matrixJs = new double[8][1];

                double r3 = r * r * r;
                double L3 = L * L * L;

                matrixJs[0][0] = 1 / (8 * L) + (9 * r) / (2 * L * L) - (27 * r * r) / (2 * L3);
                matrixJs[1][0] = -r / (8 * L) - (9 * r * r) / (4 * L * L) + (9 * r3) / (2 * L3) + 1.0 / 16.0;
                matrixJs[2][0] = -27 / (8 * L) - (9 * r) / (2 * L * L) + (81 * r * r) / (2 * L3);
                matrixJs[3][0] = (27 * r) / (8 * L) + (9 * r * r) / (4 * L * L) - (27 * r3) / (2 * L3) - 9.0 / 16.0;
                matrixJs[4][0] = 27 / (8 * L) - (9 * r) / (2 * L * L) - (81 * r * r) / (2 * L3);
                matrixJs[5][0] = -(27 * r) / (8 * L) + (9 * r * r) / (4 * L * L) + (27 * r3) / (2 * L3) - 9.0 / 16.0;
                matrixJs[6][0] = -1 / (8 * L) + (9 * r) / (2 * L * L) + (27 * r * r) / (2 * L3);
                matrixJs[7][0] = r / (8 * L) - (9 * r * r) / (4 * L * L) - (9 * r3) / (2 * L3) + 1.0 / 16.0;
            } else {
                matrixJs = null;
            }
        } else {
            matrixJs = null;
        }

        return matrixJs;
    }
}
