/*
 * Esta classe fornece a matriz de rigidez de um elemento de viga
 * O método requer o comprimento e o número de nós do elemento finito
 * A matriz de rigidez calculada não considera os parâmetros EI e GA*
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixK_Beams {

    /**
     * Este método fornece a matriz de rigidez de um elemento de viga
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixK_EulerBernoulli(double L, int nodes) {
        double[][] matrixK;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixK = new double[4][4];

                matrixK[0][0] = 12.0 / (L * L * L);
                matrixK[0][1] = 6.0 / (L * L);
                matrixK[0][2] = -12.0 / (L * L * L);
                matrixK[0][3] = 6.0 / (L * L);

                matrixK[1][0] = 6.0 / (L * L);
                matrixK[1][1] = 4.0 / L;
                matrixK[1][2] = -6.0 / (L * L);
                matrixK[1][3] = 2.0 / L;

                matrixK[2][0] = -12.0 / (L * L * L);
                matrixK[2][1] = -6.0 / (L * L);
                matrixK[2][2] = 12.0 / (L * L * L);
                matrixK[2][3] = -6.0 / (L * L);

                matrixK[3][0] = 6.0 / (L * L);
                matrixK[3][1] = 2.0 / L;
                matrixK[3][2] = -6.0 / (L * L);
                matrixK[3][3] = 4.0 / L;
            } else if (nodes == 3) {
                matrixK = new double[6][6];

                matrixK[0][0] = 5092.0 / (35.0 * (L * L * L));
                matrixK[0][1] = 1138.0 / (35.0 * (L * L));
                matrixK[0][2] = -512.0 / (5.0 * (L * L * L));
                matrixK[0][3] = 384.0 / (7.0 * (L * L));
                matrixK[0][4] = -1508.0 / (35.0 * (L * L * L));
                matrixK[0][5] = 242.0 / (35.0 * (L * L));

                matrixK[1][0] = 1138.0 / (35.0 * (L * L));
                matrixK[1][1] = 332.0 / (35.0 * L);
                matrixK[1][2] = -128.0 / (5.0 * (L * L));
                matrixK[1][3] = 64.0 / (7.0 * L);
                matrixK[1][4] = -242.0 / (35.0 * (L * L));
                matrixK[1][5] = 38.0 / (35.0 * L);

                matrixK[2][0] = -512.0 / (5.0 * (L * L * L));
                matrixK[2][1] = -128.0 / (5.0 * (L * L));
                matrixK[2][2] = 1024.0 / (5.0 * (L * L * L));
                matrixK[2][3] = 0.0;
                matrixK[2][4] = -512.0 / (5.0 * (L * L * L));
                matrixK[2][5] = 128.0 / (5.0 * (L * L));

                matrixK[3][0] = 384.0 / (7.0 * (L * L));
                matrixK[3][1] = 64.0 / (7.0 * L);
                matrixK[3][2] = 0.0;
                matrixK[3][3] = 256.0 / (7.0 * L);
                matrixK[3][4] = -384.0 / (7.0 * (L * L));
                matrixK[3][5] = 64.0 / (7.0 * L);

                matrixK[4][0] = -1508.0 / (35.0 * (L * L * L));
                matrixK[4][1] = -242.0 / (35.0 * (L * L));
                matrixK[4][2] = -512.0 / (5.0 * (L * L * L));
                matrixK[4][3] = -384.0 / (7.0 * (L * L));
                matrixK[4][4] = 5092.0 / (35.0 * (L * L * L));
                matrixK[4][5] = -1138.0 / (35.0 * (L * L));

                matrixK[5][0] = 242.0 / (35.0 * (L * L));
                matrixK[5][1] = 38.0 / (35.0 * L);
                matrixK[5][2] = 128.0 / (5.0 * (L * L));
                matrixK[5][3] = 64.0 / (7.0 * L);
                matrixK[5][4] = -1138.0 / (35.0 * (L * L));
                matrixK[5][5] = 332.0 / (35.0 * L);
            } else if (nodes == 4) {
                matrixK = new double[8][8];

                matrixK[0][0] = 4539 / (7 * L * L * L);
                matrixK[0][1] = 2517 / (28 * L * L);
                matrixK[0][2] = -2187 / (16 * L * L * L);
                matrixK[0][3] = 12393 / (56 * L * L);
                matrixK[0][4] = -10935 / (28 * L * L * L);
                matrixK[0][5] = 729 / (7 * L * L);
                matrixK[0][6] = -13575 / (112 * L * L * L);
                matrixK[0][7] = 165 / (14 * L * L);

                matrixK[1][0] = 2517 / (28 * L * L);
                matrixK[1][1] = 6157 / (385 * L);
                matrixK[1][2] = -10935 / (308 * L * L);
                matrixK[1][3] = 148959 / (6160 * L);
                matrixK[1][4] = -6561 / (154 * L * L);
                matrixK[1][5] = 4131 / (385 * L);
                matrixK[1][6] = -165 / (14 * L * L);
                matrixK[1][7] = 6893 / (6160 * L);

                matrixK[2][0] = -2187 / (16 * L * L * L);
                matrixK[2][1] = -10935 / (308 * L * L);
                matrixK[2][2] = 177147 / (154 * L * L * L);
                matrixK[2][3] = 6561 / (44 * L * L);
                matrixK[2][4] = -767637 / (1232 * L * L * L);
                matrixK[2][5] = 164025 / (616 * L * L);
                matrixK[2][6] = -10935 / (28 * L * L * L);
                matrixK[2][7] = 6561 / (154 * L * L);

                matrixK[3][0] = 12393 / (56 * L * L);
                matrixK[3][1] = 148959 / (6160 * L);
                matrixK[3][2] = 6561 / (44 * L * L);
                matrixK[3][3] = 45198 / (385 * L);
                matrixK[3][4] = -164025 / (616 * L * L);
                matrixK[3][5] = 490617 / (6160 * L);
                matrixK[3][6] = -729 / (7 * L * L);
                matrixK[3][7] = 4131 / (385 * L);

                matrixK[4][0] = -10935 / (28 * L * L * L);
                matrixK[4][1] = -6561 / (154 * L * L);
                matrixK[4][2] = -767637 / (1232 * L * L * L);
                matrixK[4][3] = -164025 / (616 * L * L);
                matrixK[4][4] = 177147 / (154 * L * L * L);
                matrixK[4][5] = -6561 / (44 * L * L);
                matrixK[4][6] = -2187 / (16 * L * L * L);
                matrixK[4][7] = 10935 / (308 * L * L);

                matrixK[5][0] = 729 / (7 * L * L);
                matrixK[5][1] = 4131 / (385 * L);
                matrixK[5][2] = 164025 / (616 * L * L);
                matrixK[5][3] = 490617 / (6160 * L);
                matrixK[5][4] = -6561 / (44 * L * L);
                matrixK[5][5] = 45198 / (385 * L);
                matrixK[5][6] = -12393 / (56 * L * L);
                matrixK[5][7] = 148959 / (6160 * L);

                matrixK[6][0] = -13575 / (112 * L * L * L);
                matrixK[6][1] = -165 / (14 * L * L);
                matrixK[6][2] = -10935 / (28 * L * L * L);
                matrixK[6][3] = -729 / (7 * L * L);
                matrixK[6][4] = -2187 / (16 * L * L * L);
                matrixK[6][5] = -12393 / (56 * L * L);
                matrixK[6][6] = 4539 / (7 * L * L * L);
                matrixK[6][7] = -2517 / (28 * L * L);

                matrixK[7][0] = 165 / (14 * L * L);
                matrixK[7][1] = 6893 / (6160 * L);
                matrixK[7][2] = 6561 / (154 * L * L);
                matrixK[7][3] = 4131 / (385 * L);
                matrixK[7][4] = 10935 / (308 * L * L);
                matrixK[7][5] = 148959 / (6160 * L);
                matrixK[7][6] = -2517 / (28 * L * L);
                matrixK[7][7] = 6157 / (385 * L);
            } else {
                matrixK = null;
            }
        } else {
            matrixK = null;
        }

        return matrixK;
    }

    /**
     * Este método fornece a matriz de rigidez Kb de um elemento de viga
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixKb_Timoshenko(double L, int nodes) {
        double[][] matrixKb;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixKb = new double[4][4];

                matrixKb[0][0] = 0.0;
                matrixKb[0][1] = 0.0;
                matrixKb[0][2] = 0.0;
                matrixKb[0][3] = 0.0;

                matrixKb[1][0] = 0.0;
                matrixKb[1][1] = 1.0 / L;
                matrixKb[1][2] = 0.0;
                matrixKb[1][3] = -1.0 / L;

                matrixKb[2][0] = 0.0;
                matrixKb[2][1] = 0.0;
                matrixKb[2][2] = 0.0;
                matrixKb[2][3] = 0.0;

                matrixKb[3][0] = 0.0;
                matrixKb[3][1] = -1.0 / L;
                matrixKb[3][2] = 0.0;
                matrixKb[3][3] = 1.0 / L;
            } else if (nodes == 3) {
                matrixKb = new double[6][6];

                matrixKb[0][0] = 0.0;
                matrixKb[0][1] = 0.0;
                matrixKb[0][2] = 0.0;
                matrixKb[0][3] = 0.0;
                matrixKb[0][4] = 0.0;
                matrixKb[0][5] = 0.0;

                matrixKb[1][0] = 0.0;
                matrixKb[1][1] = 7.0 / (3.0 * L);
                matrixKb[1][2] = 0.0;
                matrixKb[1][3] = -8.0 / (3.0 * L);
                matrixKb[1][4] = 0.0;
                matrixKb[1][5] = 1.0 / (3.0 * L);

                matrixKb[2][0] = 0.0;
                matrixKb[2][1] = 0.0;
                matrixKb[2][2] = 0.0;
                matrixKb[2][3] = 0.0;
                matrixKb[2][4] = 0.0;
                matrixKb[2][5] = 0.0;

                matrixKb[3][0] = 0.0;
                matrixKb[3][1] = -8.0 / (3.0 * L);
                matrixKb[3][2] = 0.0;
                matrixKb[3][3] = 16.0 / (3.0 * L);
                matrixKb[3][4] = 0.0;
                matrixKb[3][5] = -8.0 / (3.0 * L);

                matrixKb[4][0] = 0.0;
                matrixKb[4][1] = 0.0;
                matrixKb[4][2] = 0.0;
                matrixKb[4][3] = 0.0;
                matrixKb[4][4] = 0.0;
                matrixKb[4][5] = 0.0;

                matrixKb[5][0] = 0.0;
                matrixKb[5][1] = 1.0 / (3.0 * L);
                matrixKb[5][2] = 0.0;
                matrixKb[5][3] = -8.0 / (3.0 * L);
                matrixKb[5][4] = 0.0;
                matrixKb[5][5] = 7.0 / (3.0 * L);
            } else if (nodes == 4) {
                matrixKb = new double[8][8];

                matrixKb[0][0] = 0.0;
                matrixKb[0][1] = 0.0;
                matrixKb[0][2] = 0.0;
                matrixKb[0][3] = 0.0;
                matrixKb[0][4] = 0.0;
                matrixKb[0][5] = 0.0;
                matrixKb[0][6] = 0.0;
                matrixKb[0][7] = 0.0;

                matrixKb[1][0] = 0.0;
                matrixKb[1][1] = 37.0 / (10.0 * L);
                matrixKb[1][2] = 0.0;
                matrixKb[1][3] = -189.0 / (40.0 * L);
                matrixKb[1][4] = 0.0;
                matrixKb[1][5] = 27.0 / (20.0 * L);
                matrixKb[1][6] = 0.0;
                matrixKb[1][7] = -13.0 / (40.0 * L);

                matrixKb[2][0] = 0.0;
                matrixKb[2][1] = 0.0;
                matrixKb[2][2] = 0.0;
                matrixKb[2][3] = 0.0;
                matrixKb[2][4] = 0.0;
                matrixKb[2][5] = 0.0;
                matrixKb[2][6] = 0.0;
                matrixKb[2][7] = 0.0;

                matrixKb[3][0] = 0.0;
                matrixKb[3][1] = -189.0 / (40.0 * L);
                matrixKb[3][2] = 0.0;
                matrixKb[3][3] = 54.0 / (5.0 * L);
                matrixKb[3][4] = 0.0;
                matrixKb[3][5] = -297.0 / (40.0 * L);
                matrixKb[3][6] = 0.0;
                matrixKb[3][7] = 27.0 / (20.0 * L);

                matrixKb[4][0] = 0.0;
                matrixKb[4][1] = 0.0;
                matrixKb[4][2] = 0.0;
                matrixKb[4][3] = 0.0;
                matrixKb[4][4] = 0.0;
                matrixKb[4][5] = 0.0;
                matrixKb[4][6] = 0.0;
                matrixKb[4][7] = 0.0;

                matrixKb[5][0] = 0.0;
                matrixKb[5][1] = 27.0 / (20.0 * L);
                matrixKb[5][2] = 0.0;
                matrixKb[5][3] = -297.0 / (40.0 * L);
                matrixKb[5][4] = 0.0;
                matrixKb[5][5] = 54.0 / (5.0 * L);
                matrixKb[5][6] = 0.0;
                matrixKb[5][7] = -189.0 / (40.0 * L);

                matrixKb[6][0] = 0.0;
                matrixKb[6][1] = 0.0;
                matrixKb[6][2] = 0.0;
                matrixKb[6][3] = 0.0;
                matrixKb[6][4] = 0.0;
                matrixKb[6][5] = 0.0;
                matrixKb[6][6] = 0.0;
                matrixKb[6][7] = 0.0;

                matrixKb[7][0] = 0.0;
                matrixKb[7][1] = -13.0 / (40.0 * L);
                matrixKb[7][2] = 0.0;
                matrixKb[7][3] = 27.0 / (20.0 * L);
                matrixKb[7][4] = 0.0;
                matrixKb[7][5] = -189.0 / (40.0 * L);
                matrixKb[7][6] = 0.0;
                matrixKb[7][7] = 37.0 / (10.0 * L);
            } else {
                matrixKb = null;
            }
        } else {
            matrixKb = null;
        }

        return matrixKb;
    }

    /**
     * Este método fornece a matriz de rigidez Ks de um elemento de viga
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixKs_Timoshenko(double L, int nodes) {
        double[][] matrixKs;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixKs = new double[4][4];

                matrixKs[0][0] = 1.0 / L;
                matrixKs[0][1] = 1.0 / 2.0;
                matrixKs[0][2] = -1.0 / L;
                matrixKs[0][3] = 1.0 / 2.0;

                matrixKs[1][0] = 1.0 / 2.0;
                matrixKs[1][1] = L / 3.0;
                matrixKs[1][2] = -1.0 / 2.0;
                matrixKs[1][3] = L / 6.0;

                matrixKs[2][0] = -1.0 / L;
                matrixKs[2][1] = -1.0 / 2.0;
                matrixKs[2][2] = 1.0 / L;
                matrixKs[2][3] = -1.0 / 2.0;

                matrixKs[3][0] = 1.0 / 2.0;
                matrixKs[3][1] = L / 6.0;
                matrixKs[3][2] = -1.0 / 2.0;
                matrixKs[3][3] = L / 3.0;
            } else if (nodes == 3) {
                matrixKs = new double[6][6];

                matrixKs[0][0] = 7.0 / (3.0 * L);
                matrixKs[0][1] = 1.0 / 2.0;
                matrixKs[0][2] = -8.0 / (3.0 * L);
                matrixKs[0][3] = 2.0 / 3.0;
                matrixKs[0][4] = 1.0 / (3.0 * L);
                matrixKs[0][5] = -1.0 / 6.0;

                matrixKs[1][0] = 1.0 / 2.0;
                matrixKs[1][1] = (2.0 * L) / 15.0;
                matrixKs[1][2] = -2.0 / 3.0;
                matrixKs[1][3] = L / 15.0;
                matrixKs[1][4] = 1.0 / 6.0;
                matrixKs[1][5] = -L / 30.0;

                matrixKs[2][0] = -8.0 / (3.0 * L);
                matrixKs[2][1] = -2.0 / 3.0;
                matrixKs[2][2] = 16.0 / (3.0 * L);
                matrixKs[2][3] = 0.0;
                matrixKs[2][4] = -8.0 / (3.0 * L);
                matrixKs[2][5] = 2.0 / 3.0;

                matrixKs[3][0] = 2.0 / 3.0;
                matrixKs[3][1] = L / 15.0;
                matrixKs[3][2] = 0.0;
                matrixKs[3][3] = (8.0 * L) / 15.0;
                matrixKs[3][4] = -2.0 / 3.0;
                matrixKs[3][5] = L / 15.0;

                matrixKs[4][0] = 1.0 / (3.0 * L);
                matrixKs[4][1] = 1.0 / 6.0;
                matrixKs[4][2] = -8.0 / (3.0 * L);
                matrixKs[4][3] = -2.0 / 3.0;
                matrixKs[4][4] = 7.0 / (3.0 * L);
                matrixKs[4][5] = -1.0 / 2.0;

                matrixKs[5][0] = -1.0 / 6.0;
                matrixKs[5][1] = -L / 30.0;
                matrixKs[5][2] = 2.0 / 3.0;
                matrixKs[5][3] = L / 15.0;
                matrixKs[5][4] = -1.0 / 2.0;
                matrixKs[5][5] = (2.0 * L) / 15.0;
            } else if (nodes == 4) {
                matrixKs = new double[8][8];

                matrixKs[0][0] = 37.0 / (10.0 * L);
                matrixKs[0][1] = 1.0 / 2.0;
                matrixKs[0][2] = -189.0 / (40.0 * L);
                matrixKs[0][3] = 57.0 / 80.0;
                matrixKs[0][4] = 27.0 / (20.0 * L);
                matrixKs[0][5] = -3.0 / 10.0;
                matrixKs[0][6] = -13.0 / (40.0 * L);
                matrixKs[0][7] = 7.0 / 80.0;

                matrixKs[1][0] = 1.0 / 2.0;
                matrixKs[1][1] = (8.0 * L) / 105.0;
                matrixKs[1][2] = -57.0 / 80.0;
                matrixKs[1][3] = (33.0 * L) / 560.0;
                matrixKs[1][4] = 3.0 / 10.0;
                matrixKs[1][5] = -(3.0 * L) / 140.0;
                matrixKs[1][6] = -7.0 / 80.0;
                matrixKs[1][7] = (19.0 * L) / 1680.0;

                matrixKs[2][0] = -189.0 / (40.0 * L);
                matrixKs[2][1] = -57.0 / 80.0;
                matrixKs[2][2] = 54.0 / (5.0 * L);
                matrixKs[2][3] = 0.0;
                matrixKs[2][4] = -297.0 / (40.0 * L);
                matrixKs[2][5] = 81.0 / 80.0;
                matrixKs[2][6] = 27.0 / (20.0 * L);
                matrixKs[2][7] = -3.0 / 10.0;

                matrixKs[3][0] = 57.0 / 80.0;
                matrixKs[3][1] = (33.0 * L) / 560.0;
                matrixKs[3][2] = 0.0;
                matrixKs[3][3] = (27.0 * L) / 70.0;
                matrixKs[3][4] = -81.0 / 80.0;
                matrixKs[3][5] = -(27.0 * L) / 560.0;
                matrixKs[3][6] = 3.0 / 10.0;
                matrixKs[3][7] = -(3.0 * L) / 140.0;

                matrixKs[4][0] = 27.0 / (20.0 * L);
                matrixKs[4][1] = 3.0 / 10.0;
                matrixKs[4][2] = -297.0 / (40.0 * L);
                matrixKs[4][3] = -81.0 / 80.0;
                matrixKs[4][4] = 54.0 / (5.0 * L);
                matrixKs[4][5] = 0.0;
                matrixKs[4][6] = -189.0 / (40.0 * L);
                matrixKs[4][7] = 57.0 / 80.0;

                matrixKs[5][0] = -3.0 / 10.0;
                matrixKs[5][1] = -(3.0 * L) / 140.0;
                matrixKs[5][2] = 81.0 / 80.0;
                matrixKs[5][3] = -(27.0 * L) / 560.0;
                matrixKs[5][4] = 0.0;
                matrixKs[5][5] = (27.0 * L) / 70.0;
                matrixKs[5][6] = -57.0 / 80.0;
                matrixKs[5][7] = (33.0 * L) / 560.0;

                matrixKs[6][0] = -13.0 / (40.0 * L);
                matrixKs[6][1] = -7.0 / 80.0;
                matrixKs[6][2] = 27.0 / (20.0 * L);
                matrixKs[6][3] = 3.0 / 10.0;
                matrixKs[6][4] = -189.0 / (40.0 * L);
                matrixKs[6][5] = -57.0 / 80.0;
                matrixKs[6][6] = 37.0 / (10.0 * L);
                matrixKs[6][7] = -1.0 / 2.0;

                matrixKs[7][0] = 7.0 / 80.0;
                matrixKs[7][1] = (19.0 * L) / 1680.0;
                matrixKs[7][2] = -3.0 / 10.0;
                matrixKs[7][3] = -(3.0 * L) / 140.0;
                matrixKs[7][4] = 57.0 / 80.0;
                matrixKs[7][5] = (33.0 * L) / 560.0;
                matrixKs[7][6] = -1.0 / 2.0;
                matrixKs[7][7] = (8.0 * L) / 105.0;
            } else {
                matrixKs = null;
            }
        } else {
            matrixKs = null;
        }

        return matrixKs;
    }
}
