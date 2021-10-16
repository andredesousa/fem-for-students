/*
 * Esta classe fornece as funções de forma de elementos finitos de viga
 * O método requer a coordenada x, o comprimento e o número de nós
 * A matriz Nv é avaliada em função da coordena x
 */

package app.shapefunctions;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv_Beams {

    /**
     * Este método fornece a matriz Nv de um elemento do tipo viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_EulerBernoulli(double x, double L, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 2) {
                matrixNv = new double[nodes * 2][1];
                
                matrixNv[0][0] = 0.5 - 3 * x / (2 * L) + 2 * x * x * x / (L * L * L);
                matrixNv[1][0] = L / 8 - x / 4 - x * x / (2 * L) + x * x * x / (L * L);
                matrixNv[2][0] = 0.5 + 3 * x / (2 * L) - 2 * x * x * x / (L * L * L);
                matrixNv[3][0] = -L / 8 - x / 4 + x * x / (2 * L) + x * x * x / (L * L);
            } else if (nodes == 3) {
                matrixNv = new double[nodes * 2][1];
                double x2 = x * x;
                double x3 = x * x * x;
                double x4 = x * x * x * x;
                double x5 = x * x * x * x * x;
                double L2 = L * L;
                double L3 = L * L * L;
                double L4 = L * L * L * L;
                double L5 = L * L * L * L * L;

                matrixNv[0][0] = (4 * x2) / L2 - (10 * x3) / L3 - (8 * x4) / L4 + (24 * x5) / L5;
                matrixNv[1][0] = x2 / (2 * L) - x3 / L2 - (2 * x4) / L3 + (4 * x5) / L4;
                matrixNv[2][0] = -(8 * x2) / L2 + (16 * x4) / L4 + 1;
                matrixNv[3][0] = -(8 * x3) / L2 + (16 * x5) / L4 + x;
                matrixNv[4][0] = (4 * x2) / L2 + (10 * x3) / L3 - (8 * x4) / L4 - (24 * x5) / L5;
                matrixNv[5][0] = -x2 / (2 * L) - x3 / L2 + (2 * x4) / L3 + (4 * x5) / L4;
            } else if (nodes == 4) {
                matrixNv = new double[nodes * 2][1];
                double x2 = x * x;
                double x3 = x * x * x;
                double x4 = x * x * x * x;
                double x5 = x * x * x * x * x;
                double x6 = x * x * x * x * x * x;
                double x7 = x * x * x * x * x * x * x;
                double L2 = L * L;
                double L3 = L * L * L;
                double L4 = L * L * L * L;
                double L5 = L * L * L * L * L;
                double L6 = L * L * L * L * L * L;
                double L7 = L * L * L * L * L * L * L;

                matrixNv[0][0] = -(15 * x) / (256 * L) - (243 * x2) / (128 * L2) + (281 * x3) / (64 * L3) + (1215 * x4) / (32 * L4) - (1413 * x5) / (16 * L5) - (729 * x6) / (8 * L6) + (891 * x7) / (4 * L7) + 13.0 / 512.0;
                matrixNv[1][0] = L / 512 - (19 * x2) / (128 * L) + (19 * x3) / (64 * L2) + (99 * x4) / (32 * L3) - (99 * x5) / (16 * L4) - (81 * x6) / (8 * L5) + (81 * x7) / (4 * L6) - x / 256;
                matrixNv[2][0] = -(1215 * x) / (256 * L) + (243 * x2) / (128 * L2) + (4617 * x3) / (64 * L3) - (1215 * x4) / (32 * L4) - (5589 * x5) / (16 * L5) + (729 * x6) / (8 * L6) + (2187 * x7) / (4 * L7) + 243.0 / 512.0;
                matrixNv[3][0] = (27 * L) / 512 - (297 * x2) / (128 * L) + (891 * x3) / (64 * L2) + (513 * x4) / (32 * L3) - (1539 * x5) / (16 * L4) - (243 * x6) / (8 * L5) + (729 * x7) / (4 * L6) - (81 * x) / 256;
                matrixNv[4][0] = (1215 * x) / (256 * L) + (243 * x2) / (128 * L2) - (4617 * x3) / (64 * L3) - (1215 * x4) / (32 * L4) + (5589 * x5) / (16 * L5) + (729 * x6) / (8 * L6) - (2187 * x7) / (4 * L7) + 243.0 / 512.0;
                matrixNv[5][0] = -(27 * L) / 512 + (297 * x2) / (128 * L) + (891 * x3) / (64 * L2) - (513 * x4) / (32 * L3) - (1539 * x5) / (16 * L4) + (243 * x6) / (8 * L5) + (729 * x7) / (4 * L6) - (81 * x) / 256;
                matrixNv[6][0] = (15 * x) / (256 * L) - (243 * x2) / (128 * L2) - (281 * x3) / (64 * L3) + (1215 * x4) / (32 * L4) + (1413 * x5) / (16 * L5) - (729 * x6) / (8 * L6) - (891 * x7) / (4 * L7) + 13.0 / 512.0;
                matrixNv[7][0] = -L / 512 + (19 * x2) / (128 * L) + (19 * x3) / (64 * L2) - (99 * x4) / (32 * L3) - (99 * x5) / (16 * L4) + (81 * x6) / (8 * L5) + (81 * x7) / (4 * L6) - x / 256;
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }

    /**
     * Este método fornece a matriz Nv de um elemento do tipo viga
     *
     * @param x é a coordenada local segundo o eixo x
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_Timoshenko(double x, double L, int nodes) {
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
