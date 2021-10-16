/*
 * Esta classe fornece a matriz de rigidez de um elemento finito bidimensional
 * O método requer o comprimento, a altura e o número de nós do elemento finito
 * A matriz de rigidez calculada não considera a espessura do elemento finito
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixK_2D {

    /**
     * Este método fornece a matriz de rigidez de um elemento finito bidimensional
     *
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @param matrixD é a matriz de elasticidade do elemento finito
     * @return
     */
    public static double[][] matrixK_2D(double a, double b, int nodes, double[][] matrixD) {
        double[][] matrixK;

        if (a != 0 && b != 0 && nodes > 1) {
            double D1, D2, D3;

            D1 = matrixD[0][0];
            D2 = matrixD[1][0];
            D3 = matrixD[2][2];

            if (nodes == 3) {
                matrixK = new double[6][6];

                matrixK[0][0] = (a * b * (D3 / (b * b) + D1 / (a * a))) / 2;
                matrixK[0][1] = (a * b * (D3 / (a * b) + D2 / (a * b))) / 2;
                matrixK[0][2] = -(b * D1) / (2 * a);
                matrixK[0][3] = -D3 / 2;
                matrixK[0][4] = -(a * D3) / (2 * b);
                matrixK[0][5] = -D2 / 2;

                matrixK[1][0] = (a * b * (D3 / (a * b) + D2 / (a * b))) / 2;
                matrixK[1][1] = (a * b * (D3 / (a * a) + D1 / (b * b))) / 2;
                matrixK[1][2] = -D2 / 2;
                matrixK[1][3] = -(b * D3) / (2 * a);
                matrixK[1][4] = -D3 / 2;
                matrixK[1][5] = -(a * D1) / (2 * b);

                matrixK[2][0] = -(b * D1) / (2 * a);
                matrixK[2][1] = -D2 / 2;
                matrixK[2][2] = (b * D1) / (2 * a);
                matrixK[2][3] = 0;
                matrixK[2][4] = 0;
                matrixK[2][5] = D2 / 2;

                matrixK[3][0] = -D3 / 2;
                matrixK[3][1] = -(b * D3) / (2 * a);
                matrixK[3][2] = 0;
                matrixK[3][3] = (b * D3) / (2 * a);
                matrixK[3][4] = D3 / 2;
                matrixK[3][5] = 0;

                matrixK[4][0] = -(a * D3) / (2 * b);
                matrixK[4][1] = -D3 / 2;
                matrixK[4][2] = 0;
                matrixK[4][3] = D3 / 2;
                matrixK[4][4] = (a * D3) / (2 * b);
                matrixK[4][5] = 0;

                matrixK[5][0] = -D2 / 2;
                matrixK[5][1] = -(a * D1) / (2 * b);
                matrixK[5][2] = D2 / 2;
                matrixK[5][3] = 0;
                matrixK[5][4] = 0;
                matrixK[5][5] = (a * D1) / (2 * b);
            } else if (nodes == 4) {
                matrixK = new double[8][8];

                matrixK[0][0] = (a * a * D3 + b * b * D1) / (3 * a * b);
                matrixK[0][1] = (D3 + D2) / 4;
                matrixK[0][2] = (a * a * D3 - 2 * b * b * D1) / (6 * a * b);
                matrixK[0][3] = -(D3 - D2) / 4;
                matrixK[0][4] = -(a * a * D3 + b * b * D1) / (6 * a * b);
                matrixK[0][5] = -(D3 + D2) / 4;
                matrixK[0][6] = -(2 * a * a * D3 - b * b * D1) / (6 * a * b);
                matrixK[0][7] = (D3 - D2) / 4;

                matrixK[1][0] = (D3 + D2) / 4;
                matrixK[1][1] = (b * b * D3 + a * a * D1) / (3 * a * b);
                matrixK[1][2] = (D3 - D2) / 4;
                matrixK[1][3] = -(2 * b * b * D3 - a * a * D1) / (6 * a * b);
                matrixK[1][4] = -(D3 + D2) / 4;
                matrixK[1][5] = -(b * b * D3 + a * a * D1) / (6 * a * b);
                matrixK[1][6] = -(D3 - D2) / 4;
                matrixK[1][7] = (b * b * D3 - 2 * a * a * D1) / (6 * a * b);

                matrixK[2][0] = (a * a * D3 - 2 * b * b * D1) / (6 * a * b);
                matrixK[2][1] = (D3 - D2) / 4;
                matrixK[2][2] = (a * a * D3 + b * b * D1) / (3 * a * b);
                matrixK[2][3] = -(D3 + D2) / 4;
                matrixK[2][4] = -(2 * a * a * D3 - b * b * D1) / (6 * a * b);
                matrixK[2][5] = -(D3 - D2) / 4;
                matrixK[2][6] = -(a * a * D3 + b * b * D1) / (6 * a * b);
                matrixK[2][7] = (D3 + D2) / 4;

                matrixK[3][0] = -(D3 - D2) / 4;
                matrixK[3][1] = -(2 * b * b * D3 - a * a * D1) / (6 * a * b);
                matrixK[3][2] = -(D3 + D2) / 4;
                matrixK[3][3] = (b * b * D3 + a * a * D1) / (3 * a * b);
                matrixK[3][4] = (D3 - D2) / 4;
                matrixK[3][5] = (b * b * D3 - 2 * a * a * D1) / (6 * a * b);
                matrixK[3][6] = (D3 + D2) / 4;
                matrixK[3][7] = -(b * b * D3 + a * a * D1) / (6 * a * b);

                matrixK[4][0] = -(a * a * D3 + b * b * D1) / (6 * a * b);
                matrixK[4][1] = -(D3 + D2) / 4;
                matrixK[4][2] = -(2 * a * a * D3 - b * b * D1) / (6 * a * b);
                matrixK[4][3] = (D3 - D2) / 4;
                matrixK[4][4] = (a * a * D3 + b * b * D1) / (3 * a * b);
                matrixK[4][5] = (D3 + D2) / 4;
                matrixK[4][6] = (a * a * D3 - 2 * b * b * D1) / (6 * a * b);
                matrixK[4][7] = -(D3 - D2) / 4;

                matrixK[5][0] = -(D3 + D2) / 4;
                matrixK[5][1] = -(b * b * D3 + a * a * D1) / (6 * a * b);
                matrixK[5][2] = -(D3 - D2) / 4;
                matrixK[5][3] = (b * b * D3 - 2 * a * a * D1) / (6 * a * b);
                matrixK[5][4] = (D3 + D2) / 4;
                matrixK[5][5] = (b * b * D3 + a * a * D1) / (3 * a * b);
                matrixK[5][6] = (D3 - D2) / 4;
                matrixK[5][7] = -(2 * b * b * D3 - a * a * D1) / (6 * a * b);

                matrixK[6][0] = -(2 * a * a * D3 - b * b * D1) / (6 * a * b);
                matrixK[6][1] = -(D3 - D2) / 4;
                matrixK[6][2] = -(a * a * D3 + b * b * D1) / (6 * a * b);
                matrixK[6][3] = (D3 + D2) / 4;
                matrixK[6][4] = (a * a * D3 - 2 * b * b * D1) / (6 * a * b);
                matrixK[6][5] = (D3 - D2) / 4;
                matrixK[6][6] = (a * a * D3 + b * b * D1) / (3 * a * b);
                matrixK[6][7] = -(D3 + D2) / 4;

                matrixK[7][0] = (D3 - D2) / 4;
                matrixK[7][1] = (b * b * D3 - 2 * a * a * D1) / (6 * a * b);
                matrixK[7][2] = (D3 + D2) / 4;
                matrixK[7][3] = -(b * b * D3 + a * a * D1) / (6 * a * b);
                matrixK[7][4] = -(D3 - D2) / 4;
                matrixK[7][5] = -(2 * b * b * D3 - a * a * D1) / (6 * a * b);
                matrixK[7][6] = -(D3 + D2) / 4;
                matrixK[7][7] = (b * b * D3 + a * a * D1) / (3 * a * b);
            } else if (nodes == 6) {
                matrixK = new double[12][12];

                matrixK[0][0] = (a * a * D3 + b * b * D1) / (2 * a * b);
                matrixK[0][1] = (D3 + D2) / 2;
                matrixK[0][2] = -(2 * b * D1) / (3 * a);
                matrixK[0][3] = -(2 * D3) / 3;
                matrixK[0][4] = (b * D1) / (6 * a);
                matrixK[0][5] = D3 / 6;
                matrixK[0][6] = 0;
                matrixK[0][7] = 0;
                matrixK[0][8] = (a * D3) / (6 * b);
                matrixK[0][9] = D2 / 6;
                matrixK[0][10] = -(2 * a * D3) / (3 * b);
                matrixK[0][11] = -(2 * D2) / 3;

                matrixK[1][0] = (D3 + D2) / 2;
                matrixK[1][1] = (b * b * D3 + a * a * D1) / (2 * a * b);
                matrixK[1][2] = -(2 * D2) / 3;
                matrixK[1][3] = -(2 * b * D3) / (3 * a);
                matrixK[1][4] = D2 / 6;
                matrixK[1][5] = (b * D3) / (6 * a);
                matrixK[1][6] = 0;
                matrixK[1][7] = 0;
                matrixK[1][8] = D3 / 6;
                matrixK[1][9] = (a * D1) / (6 * b);
                matrixK[1][10] = -(2 * D3) / 3;
                matrixK[1][11] = -(2 * a * D1) / (3 * b);

                matrixK[2][0] = -(2 * b * D1) / (3 * a);
                matrixK[2][1] = -(2 * D2) / 3;
                matrixK[2][2] = (4 * a * a * D3 + 4 * b * b * D1) / (3 * a * b);
                matrixK[2][3] = (2 * D3 + 2 * D2) / 3;
                matrixK[2][4] = -(2 * b * D1) / (3 * a);
                matrixK[2][5] = -(2 * D3) / 3;
                matrixK[2][6] = -(4 * a * D3) / (3 * b);
                matrixK[2][7] = -(2 * D3 + 2 * D2) / 3;
                matrixK[2][8] = 0;
                matrixK[2][9] = 0;
                matrixK[2][10] = 0;
                matrixK[2][11] = (2 * D3 + 2 * D2) / 3;

                matrixK[3][0] = -(2 * D3) / 3;
                matrixK[3][1] = -(2 * b * D3) / (3 * a);
                matrixK[3][2] = (2 * D3 + 2 * D2) / 3;
                matrixK[3][3] = (4 * b * b * D3 + 4 * a * a * D1) / (3 * a * b);
                matrixK[3][4] = -(2 * D2) / 3;
                matrixK[3][5] = -(2 * b * D3) / (3 * a);
                matrixK[3][6] = -(2 * D3 + 2 * D2) / 3;
                matrixK[3][7] = -(4 * a * D1) / (3 * b);
                matrixK[3][8] = 0;
                matrixK[3][9] = 0;
                matrixK[3][10] = (2 * D3 + 2 * D2) / 3;
                matrixK[3][11] = 0;

                matrixK[4][0] = (b * D1) / (6 * a);
                matrixK[4][1] = D2 / 6;
                matrixK[4][2] = -(2 * b * D1) / (3 * a);
                matrixK[4][3] = -(2 * D2) / 3;
                matrixK[4][4] = (b * D1) / (2 * a);
                matrixK[4][5] = 0;
                matrixK[4][6] = 0;
                matrixK[4][7] = (2 * D2) / 3;
                matrixK[4][8] = 0;
                matrixK[4][9] = -D2 / 6;
                matrixK[4][10] = 0;
                matrixK[4][11] = 0;

                matrixK[5][0] = D3 / 6;
                matrixK[5][1] = (b * D3) / (6 * a);
                matrixK[5][2] = -(2 * D3) / 3;
                matrixK[5][3] = -(2 * b * D3) / (3 * a);
                matrixK[5][4] = 0;
                matrixK[5][5] = (b * D3) / (2 * a);
                matrixK[5][6] = (2 * D3) / 3;
                matrixK[5][7] = 0;
                matrixK[5][8] = -D3 / 6;
                matrixK[5][9] = 0;
                matrixK[5][10] = 0;
                matrixK[5][11] = 0;

                matrixK[6][0] = 0;
                matrixK[6][1] = 0;
                matrixK[6][2] = -(4 * a * D3) / (3 * b);
                matrixK[6][3] = -(2 * D3 + 2 * D2) / 3;
                matrixK[6][4] = 0;
                matrixK[6][5] = (2 * D3) / 3;
                matrixK[6][6] = (4 * a * a * D3 + 4 * b * b * D1) / (3 * a * b);
                matrixK[6][7] = (2 * D3 + 2 * D2) / 3;
                matrixK[6][8] = 0;
                matrixK[6][9] = (2 * D2) / 3;
                matrixK[6][10] = -(4 * b * D1) / (3 * a);
                matrixK[6][11] = -(2 * D3 + 2 * D2) / 3;

                matrixK[7][0] = 0;
                matrixK[7][1] = 0;
                matrixK[7][2] = -(2 * D3 + 2 * D2) / 3;
                matrixK[7][3] = -(4 * a * D1) / (3 * b);
                matrixK[7][4] = (2 * D2) / 3;
                matrixK[7][5] = 0;
                matrixK[7][6] = (2 * D3 + 2 * D2) / 3;
                matrixK[7][7] = (4 * b * b * D3 + 4 * a * a * D1) / (3 * a * b);
                matrixK[7][8] = (2 * D3) / 3;
                matrixK[7][9] = 0;
                matrixK[7][10] = -(2 * D3 + 2 * D2) / 3;
                matrixK[7][11] = -(4 * b * D3) / (3 * a);

                matrixK[8][0] = (a * D3) / (6 * b);
                matrixK[8][1] = D3 / 6;
                matrixK[8][2] = 0;
                matrixK[8][3] = 0;
                matrixK[8][4] = 0;
                matrixK[8][5] = -D3 / 6;
                matrixK[8][6] = 0;
                matrixK[8][7] = (2 * D3) / 3;
                matrixK[8][8] = (a * D3) / (2 * b);
                matrixK[8][9] = 0;
                matrixK[8][10] = -(2 * a * D3) / (3 * b);
                matrixK[8][11] = -(2 * D3) / 3;

                matrixK[9][0] = D2 / 6;
                matrixK[9][1] = (a * D1) / (6 * b);
                matrixK[9][2] = 0;
                matrixK[9][3] = 0;
                matrixK[9][4] = -D2 / 6;
                matrixK[9][5] = 0;
                matrixK[9][6] = (2 * D2) / 3;
                matrixK[9][7] = 0;
                matrixK[9][8] = 0;
                matrixK[9][9] = (a * D1) / (2 * b);
                matrixK[9][10] = -(2 * D2) / 3;
                matrixK[9][11] = -(2 * a * D1) / (3 * b);

                matrixK[10][0] = -(2 * a * D3) / (3 * b);
                matrixK[10][1] = -(2 * D3) / 3;
                matrixK[10][2] = 0;
                matrixK[10][3] = (2 * D3 + 2 * D2) / 3;
                matrixK[10][4] = 0;
                matrixK[10][5] = 0;
                matrixK[10][6] = -(4 * b * D1) / (3 * a);
                matrixK[10][7] = -(2 * D3 + 2 * D2) / 3;
                matrixK[10][8] = -(2 * a * D3) / (3 * b);
                matrixK[10][9] = -(2 * D2) / 3;
                matrixK[10][10] = (4 * a * a * D3 + 4 * b * b * D1) / (3 * a * b);
                matrixK[10][11] = (2 * D3 + 2 * D2) / 3;

                matrixK[11][0] = -(2 * D2) / 3;
                matrixK[11][1] = -(2 * a * D1) / (3 * b);
                matrixK[11][2] = (2 * D3 + 2 * D2) / 3;
                matrixK[11][3] = 0;
                matrixK[11][4] = 0;
                matrixK[11][5] = 0;
                matrixK[11][6] = -(2 * D3 + 2 * D2) / 3;
                matrixK[11][7] = -(4 * b * D3) / (3 * a);
                matrixK[11][8] = -(2 * D3) / 3;
                matrixK[11][9] = -(2 * a * D1) / (3 * b);
                matrixK[11][10] = (2 * D3 + 2 * D2) / 3;
                matrixK[11][11] = (4 * b * b * D3 + 4 * a * a * D1) / (3 * a * b);
            } else if (nodes == 8) {
                matrixK = new double[16][16];

                matrixK[0][0] = (26 * a * a * D3 + 26 * b * b * D1) / (45 * a * b);
                matrixK[0][1] = (17 * D3 + 17 * D2) / 36;
                matrixK[0][2] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[0][3] = -(5 * D3 - D2) / 9;
                matrixK[0][4] = (17 * a * a * D3 + 28 * b * b * D1) / (90 * a * b);
                matrixK[0][5] = (D3 - D2) / 12;
                matrixK[0][6] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[0][7] = -(D3 + D2) / 9;
                matrixK[0][8] = (23 * a * a * D3 + 23 * b * b * D1) / (90 * a * b);
                matrixK[0][9] = (7 * D3 + 7 * D2) / 36;
                matrixK[0][10] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[0][11] = -(D3 + D2) / 9;
                matrixK[0][12] = (28 * a * a * D3 + 17 * b * b * D1) / (90 * a * b);
                matrixK[0][13] = -(D3 - D2) / 12;
                matrixK[0][14] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[0][15] = (D3 - 5 * D2) / 9;

                matrixK[1][0] = (17 * D3 + 17 * D2) / 36;
                matrixK[1][1] = (26 * b * b * D3 + 26 * a * a * D1) / (45 * a * b);
                matrixK[1][2] = (D3 - 5 * D2) / 9;
                matrixK[1][3] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[1][4] = -(D3 - D2) / 12;
                matrixK[1][5] = (28 * b * b * D3 + 17 * a * a * D1) / (90 * a * b);
                matrixK[1][6] = -(D3 + D2) / 9;
                matrixK[1][7] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);
                matrixK[1][8] = (7 * D3 + 7 * D2) / 36;
                matrixK[1][9] = (23 * b * b * D3 + 23 * a * a * D1) / (90 * a * b);
                matrixK[1][10] = -(D3 + D2) / 9;
                matrixK[1][11] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[1][12] = (D3 - D2) / 12;
                matrixK[1][13] = (17 * b * b * D3 + 28 * a * a * D1) / (90 * a * b);
                matrixK[1][14] = -(5 * D3 - D2) / 9;
                matrixK[1][15] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);

                matrixK[2][0] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[2][1] = (D3 - 5 * D2) / 9;
                matrixK[2][2] = (24 * a * a * D3 + 80 * b * b * D1) / (45 * a * b);
                matrixK[2][3] = 0;
                matrixK[2][4] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[2][5] = -(D3 - 5 * D2) / 9;
                matrixK[2][6] = 0;
                matrixK[2][7] = -(4 * D3 + 4 * D2) / 9;
                matrixK[2][8] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[2][9] = -(D3 + D2) / 9;
                matrixK[2][10] = -(24 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[2][11] = 0;
                matrixK[2][12] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[2][13] = (D3 + D2) / 9;
                matrixK[2][14] = 0;
                matrixK[2][15] = (4 * D3 + 4 * D2) / 9;

                matrixK[3][0] = -(5 * D3 - D2) / 9;
                matrixK[3][1] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[3][2] = 0;
                matrixK[3][3] = (80 * b * b * D3 + 24 * a * a * D1) / (45 * a * b);
                matrixK[3][4] = (5 * D3 - D2) / 9;
                matrixK[3][5] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[3][6] = -(4 * D3 + 4 * D2) / 9;
                matrixK[3][7] = 0;
                matrixK[3][8] = -(D3 + D2) / 9;
                matrixK[3][9] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[3][10] = 0;
                matrixK[3][11] = (40 * b * b * D3 - 24 * a * a * D1) / (45 * a * b);
                matrixK[3][12] = (D3 + D2) / 9;
                matrixK[3][13] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[3][14] = (4 * D3 + 4 * D2) / 9;
                matrixK[3][15] = 0;

                matrixK[4][0] = (17 * a * a * D3 + 28 * b * b * D1) / (90 * a * b);
                matrixK[4][1] = -(D3 - D2) / 12;
                matrixK[4][2] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[4][3] = (5 * D3 - D2) / 9;
                matrixK[4][4] = (26 * a * a * D3 + 26 * b * b * D1) / (45 * a * b);
                matrixK[4][5] = -(17 * D3 + 17 * D2) / 36;
                matrixK[4][6] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[4][7] = -(D3 - 5 * D2) / 9;
                matrixK[4][8] = (28 * a * a * D3 + 17 * b * b * D1) / (90 * a * b);
                matrixK[4][9] = (D3 - D2) / 12;
                matrixK[4][10] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[4][11] = (D3 + D2) / 9;
                matrixK[4][12] = (23 * a * a * D3 + 23 * b * b * D1) / (90 * a * b);
                matrixK[4][13] = -(7 * D3 + 7 * D2) / 36;
                matrixK[4][14] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[4][15] = (D3 + D2) / 9;

                matrixK[5][0] = (D3 - D2) / 12;
                matrixK[5][1] = (28 * b * b * D3 + 17 * a * a * D1) / (90 * a * b);
                matrixK[5][2] = -(D3 - 5 * D2) / 9;
                matrixK[5][3] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[5][4] = -(17 * D3 + 17 * D2) / 36;
                matrixK[5][5] = (26 * b * b * D3 + 26 * a * a * D1) / (45 * a * b);
                matrixK[5][6] = (5 * D3 - D2) / 9;
                matrixK[5][7] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[5][8] = -(D3 - D2) / 12;
                matrixK[5][9] = (17 * b * b * D3 + 28 * a * a * D1) / (90 * a * b);
                matrixK[5][10] = (D3 + D2) / 9;
                matrixK[5][11] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[5][12] = -(7 * D3 + 7 * D2) / 36;
                matrixK[5][13] = (23 * b * b * D3 + 23 * a * a * D1) / (90 * a * b);
                matrixK[5][14] = (D3 + D2) / 9;
                matrixK[5][15] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);

                matrixK[6][0] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[6][1] = -(D3 + D2) / 9;
                matrixK[6][2] = 0;
                matrixK[6][3] = -(4 * D3 + 4 * D2) / 9;
                matrixK[6][4] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[6][5] = (5 * D3 - D2) / 9;
                matrixK[6][6] = (80 * a * a * D3 + 24 * b * b * D1) / (45 * a * b);
                matrixK[6][7] = 0;
                matrixK[6][8] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[6][9] = -(5 * D3 - D2) / 9;
                matrixK[6][10] = 0;
                matrixK[6][11] = (4 * D3 + 4 * D2) / 9;
                matrixK[6][12] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[6][13] = (D3 + D2) / 9;
                matrixK[6][14] = (40 * a * a * D3 - 24 * b * b * D1) / (45 * a * b);
                matrixK[6][15] = 0;

                matrixK[7][0] = -(D3 + D2) / 9;
                matrixK[7][1] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);
                matrixK[7][2] = -(4 * D3 + 4 * D2) / 9;
                matrixK[7][3] = 0;
                matrixK[7][4] = -(D3 - 5 * D2) / 9;
                matrixK[7][5] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[7][6] = 0;
                matrixK[7][7] = (24 * b * b * D3 + 80 * a * a * D1) / (45 * a * b);
                matrixK[7][8] = (D3 - 5 * D2) / 9;
                matrixK[7][9] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[7][10] = (4 * D3 + 4 * D2) / 9;
                matrixK[7][11] = 0;
                matrixK[7][12] = (D3 + D2) / 9;
                matrixK[7][13] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);
                matrixK[7][14] = 0;
                matrixK[7][15] = -(24 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);

                matrixK[8][0] = (23 * a * a * D3 + 23 * b * b * D1) / (90 * a * b);
                matrixK[8][1] = (7 * D3 + 7 * D2) / 36;
                matrixK[8][2] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[8][3] = -(D3 + D2) / 9;
                matrixK[8][4] = (28 * a * a * D3 + 17 * b * b * D1) / (90 * a * b);
                matrixK[8][5] = -(D3 - D2) / 12;
                matrixK[8][6] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[8][7] = (D3 - 5 * D2) / 9;
                matrixK[8][8] = (26 * a * a * D3 + 26 * b * b * D1) / (45 * a * b);
                matrixK[8][9] = (17 * D3 + 17 * D2) / 36;
                matrixK[8][10] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[8][11] = -(5 * D3 - D2) / 9;
                matrixK[8][12] = (17 * a * a * D3 + 28 * b * b * D1) / (90 * a * b);
                matrixK[8][13] = (D3 - D2) / 12;
                matrixK[8][14] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[8][15] = -(D3 + D2) / 9;

                matrixK[9][0] = (7 * D3 + 7 * D2) / 36;
                matrixK[9][1] = (23 * b * b * D3 + 23 * a * a * D1) / (90 * a * b);
                matrixK[9][2] = -(D3 + D2) / 9;
                matrixK[9][3] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[9][4] = (D3 - D2) / 12;
                matrixK[9][5] = (17 * b * b * D3 + 28 * a * a * D1) / (90 * a * b);
                matrixK[9][6] = -(5 * D3 - D2) / 9;
                matrixK[9][7] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[9][8] = (17 * D3 + 17 * D2) / 36;
                matrixK[9][9] = (26 * b * b * D3 + 26 * a * a * D1) / (45 * a * b);
                matrixK[9][10] = (D3 - 5 * D2) / 9;
                matrixK[9][11] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[9][12] = -(D3 - D2) / 12;
                matrixK[9][13] = (28 * b * b * D3 + 17 * a * a * D1) / (90 * a * b);
                matrixK[9][14] = -(D3 + D2) / 9;
                matrixK[9][15] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);

                matrixK[10][0] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[10][1] = -(D3 + D2) / 9;
                matrixK[10][2] = -(24 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[10][3] = 0;
                matrixK[10][4] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[10][5] = (D3 + D2) / 9;
                matrixK[10][6] = 0;
                matrixK[10][7] = (4 * D3 + 4 * D2) / 9;
                matrixK[10][8] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[10][9] = (D3 - 5 * D2) / 9;
                matrixK[10][10] = (24 * a * a * D3 + 80 * b * b * D1) / (45 * a * b);
                matrixK[10][11] = 0;
                matrixK[10][12] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[10][13] = -(D3 - 5 * D2) / 9;
                matrixK[10][14] = 0;
                matrixK[10][15] = -(4 * D3 + 4 * D2) / 9;

                matrixK[11][0] = -(D3 + D2) / 9;
                matrixK[11][1] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[11][2] = 0;
                matrixK[11][3] = (40 * b * b * D3 - 24 * a * a * D1) / (45 * a * b);
                matrixK[11][4] = (D3 + D2) / 9;
                matrixK[11][5] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[11][6] = (4 * D3 + 4 * D2) / 9;
                matrixK[11][7] = 0;
                matrixK[11][8] = -(5 * D3 - D2) / 9;
                matrixK[11][9] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[11][10] = 0;
                matrixK[11][11] = (80 * b * b * D3 + 24 * a * a * D1) / (45 * a * b);
                matrixK[11][12] = (5 * D3 - D2) / 9;
                matrixK[11][13] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[11][14] = -(4 * D3 + 4 * D2) / 9;
                matrixK[11][15] = 0;

                matrixK[12][0] = (28 * a * a * D3 + 17 * b * b * D1) / (90 * a * b);
                matrixK[12][1] = (D3 - D2) / 12;
                matrixK[12][2] = -(3 * a * a * D3 + 20 * b * b * D1) / (45 * a * b);
                matrixK[12][3] = (D3 + D2) / 9;
                matrixK[12][4] = (23 * a * a * D3 + 23 * b * b * D1) / (90 * a * b);
                matrixK[12][5] = -(7 * D3 + 7 * D2) / 36;
                matrixK[12][6] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[12][7] = (D3 + D2) / 9;
                matrixK[12][8] = (17 * a * a * D3 + 28 * b * b * D1) / (90 * a * b);
                matrixK[12][9] = -(D3 - D2) / 12;
                matrixK[12][10] = (3 * a * a * D3 - 40 * b * b * D1) / (45 * a * b);
                matrixK[12][11] = (5 * D3 - D2) / 9;
                matrixK[12][12] = (26 * a * a * D3 + 26 * b * b * D1) / (45 * a * b);
                matrixK[12][13] = -(17 * D3 + 17 * D2) / 36;
                matrixK[12][14] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[12][15] = -(D3 - 5 * D2) / 9;

                matrixK[13][0] = -(D3 - D2) / 12;
                matrixK[13][1] = (17 * b * b * D3 + 28 * a * a * D1) / (90 * a * b);
                matrixK[13][2] = (D3 + D2) / 9;
                matrixK[13][3] = -(20 * b * b * D3 + 3 * a * a * D1) / (45 * a * b);
                matrixK[13][4] = -(7 * D3 + 7 * D2) / 36;
                matrixK[13][5] = (23 * b * b * D3 + 23 * a * a * D1) / (90 * a * b);
                matrixK[13][6] = (D3 + D2) / 9;
                matrixK[13][7] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);
                matrixK[13][8] = (D3 - D2) / 12;
                matrixK[13][9] = (28 * b * b * D3 + 17 * a * a * D1) / (90 * a * b);
                matrixK[13][10] = -(D3 - 5 * D2) / 9;
                matrixK[13][11] = -(40 * b * b * D3 - 3 * a * a * D1) / (45 * a * b);
                matrixK[13][12] = -(17 * D3 + 17 * D2) / 36;
                matrixK[13][13] = (26 * b * b * D3 + 26 * a * a * D1) / (45 * a * b);
                matrixK[13][14] = (5 * D3 - D2) / 9;
                matrixK[13][15] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);

                matrixK[14][0] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[14][1] = -(5 * D3 - D2) / 9;
                matrixK[14][2] = 0;
                matrixK[14][3] = (4 * D3 + 4 * D2) / 9;
                matrixK[14][4] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[14][5] = (D3 + D2) / 9;
                matrixK[14][6] = (40 * a * a * D3 - 24 * b * b * D1) / (45 * a * b);
                matrixK[14][7] = 0;
                matrixK[14][8] = -(20 * a * a * D3 + 3 * b * b * D1) / (45 * a * b);
                matrixK[14][9] = -(D3 + D2) / 9;
                matrixK[14][10] = 0;
                matrixK[14][11] = -(4 * D3 + 4 * D2) / 9;
                matrixK[14][12] = -(40 * a * a * D3 - 3 * b * b * D1) / (45 * a * b);
                matrixK[14][13] = (5 * D3 - D2) / 9;
                matrixK[14][14] = (80 * a * a * D3 + 24 * b * b * D1) / (45 * a * b);
                matrixK[14][15] = 0;

                matrixK[15][0] = (D3 - 5 * D2) / 9;
                matrixK[15][1] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[15][2] = (4 * D3 + 4 * D2) / 9;
                matrixK[15][3] = 0;
                matrixK[15][4] = (D3 + D2) / 9;
                matrixK[15][5] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);
                matrixK[15][6] = 0;
                matrixK[15][7] = -(24 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[15][8] = -(D3 + D2) / 9;
                matrixK[15][9] = -(3 * b * b * D3 + 20 * a * a * D1) / (45 * a * b);
                matrixK[15][10] = -(4 * D3 + 4 * D2) / 9;
                matrixK[15][11] = 0;
                matrixK[15][12] = -(D3 - 5 * D2) / 9;
                matrixK[15][13] = (3 * b * b * D3 - 40 * a * a * D1) / (45 * a * b);
                matrixK[15][14] = 0;
                matrixK[15][15] = (24 * b * b * D3 + 80 * a * a * D1) / (45 * a * b);
            } else if (nodes == 9) {
                matrixK = new double[18][18];

                matrixK[0][0] = (14 * a * a * D3 + 14 * b * b * D1) / (45 * a * b);
                matrixK[0][1] = (D3 + D2) / 4;
                matrixK[0][2] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[0][3] = -(D3 - D2) / 3;
                matrixK[0][4] = -(7 * a * a * D3 - 4 * b * b * D1) / (90 * a * b);
                matrixK[0][5] = (D3 - D2) / 12;
                matrixK[0][6] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[0][7] = (D3 + D2) / 9;
                matrixK[0][8] = -(a * a * D3 + b * b * D1) / (90 * a * b);
                matrixK[0][9] = -(D3 + D2) / 36;
                matrixK[0][10] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[0][11] = (D3 + D2) / 9;
                matrixK[0][12] = (4 * a * a * D3 - 7 * b * b * D1) / (90 * a * b);
                matrixK[0][13] = -(D3 - D2) / 12;
                matrixK[0][14] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[0][15] = (D3 - D2) / 3;
                matrixK[0][16] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[0][17] = -(4 * D3 + 4 * D2) / 9;

                matrixK[1][0] = (D3 + D2) / 4;
                matrixK[1][1] = (14 * b * b * D3 + 14 * a * a * D1) / (45 * a * b);
                matrixK[1][2] = (D3 - D2) / 3;
                matrixK[1][3] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[1][4] = -(D3 - D2) / 12;
                matrixK[1][5] = (4 * b * b * D3 - 7 * a * a * D1) / (90 * a * b);
                matrixK[1][6] = (D3 + D2) / 9;
                matrixK[1][7] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[1][8] = -(D3 + D2) / 36;
                matrixK[1][9] = -(b * b * D3 + a * a * D1) / (90 * a * b);
                matrixK[1][10] = (D3 + D2) / 9;
                matrixK[1][11] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[1][12] = (D3 - D2) / 12;
                matrixK[1][13] = -(7 * b * b * D3 - 4 * a * a * D1) / (90 * a * b);
                matrixK[1][14] = -(D3 - D2) / 3;
                matrixK[1][15] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[1][16] = -(4 * D3 + 4 * D2) / 9;
                matrixK[1][17] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);

                matrixK[2][0] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[2][1] = (D3 - D2) / 3;
                matrixK[2][2] = (56 * a * a * D3 + 32 * b * b * D1) / (45 * a * b);
                matrixK[2][3] = 0;
                matrixK[2][4] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[2][5] = -(D3 - D2) / 3;
                matrixK[2][6] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[2][7] = -(4 * D3 + 4 * D2) / 9;
                matrixK[2][8] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[2][9] = (D3 + D2) / 9;
                matrixK[2][10] = (8 * a * a * D3 - 8 * b * b * D1) / (45 * a * b);
                matrixK[2][11] = 0;
                matrixK[2][12] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[2][13] = -(D3 + D2) / 9;
                matrixK[2][14] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[2][15] = (4 * D3 + 4 * D2) / 9;
                matrixK[2][16] = -(64 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[2][17] = 0;

                matrixK[3][0] = -(D3 - D2) / 3;
                matrixK[3][1] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[3][2] = 0;
                matrixK[3][3] = (32 * b * b * D3 + 56 * a * a * D1) / (45 * a * b);
                matrixK[3][4] = (D3 - D2) / 3;
                matrixK[3][5] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[3][6] = -(4 * D3 + 4 * D2) / 9;
                matrixK[3][7] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[3][8] = (D3 + D2) / 9;
                matrixK[3][9] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[3][10] = 0;
                matrixK[3][11] = -(8 * b * b * D3 - 8 * a * a * D1) / (45 * a * b);
                matrixK[3][12] = -(D3 + D2) / 9;
                matrixK[3][13] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[3][14] = (4 * D3 + 4 * D2) / 9;
                matrixK[3][15] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[3][16] = 0;
                matrixK[3][17] = (16 * b * b * D3 - 64 * a * a * D1) / (45 * a * b);

                matrixK[4][0] = -(7 * a * a * D3 - 4 * b * b * D1) / (90 * a * b);
                matrixK[4][1] = -(D3 - D2) / 12;
                matrixK[4][2] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[4][3] = (D3 - D2) / 3;
                matrixK[4][4] = (14 * a * a * D3 + 14 * b * b * D1) / (45 * a * b);
                matrixK[4][5] = -(D3 + D2) / 4;
                matrixK[4][6] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[4][7] = -(D3 - D2) / 3;
                matrixK[4][8] = (4 * a * a * D3 - 7 * b * b * D1) / (90 * a * b);
                matrixK[4][9] = (D3 - D2) / 12;
                matrixK[4][10] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[4][11] = -(D3 + D2) / 9;
                matrixK[4][12] = -(a * a * D3 + b * b * D1) / (90 * a * b);
                matrixK[4][13] = (D3 + D2) / 36;
                matrixK[4][14] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[4][15] = -(D3 + D2) / 9;
                matrixK[4][16] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[4][17] = (4 * D3 + 4 * D2) / 9;

                matrixK[5][0] = (D3 - D2) / 12;
                matrixK[5][1] = (4 * b * b * D3 - 7 * a * a * D1) / (90 * a * b);
                matrixK[5][2] = -(D3 - D2) / 3;
                matrixK[5][3] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[5][4] = -(D3 + D2) / 4;
                matrixK[5][5] = (14 * b * b * D3 + 14 * a * a * D1) / (45 * a * b);
                matrixK[5][6] = (D3 - D2) / 3;
                matrixK[5][7] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[5][8] = -(D3 - D2) / 12;
                matrixK[5][9] = -(7 * b * b * D3 - 4 * a * a * D1) / (90 * a * b);
                matrixK[5][10] = -(D3 + D2) / 9;
                matrixK[5][11] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[5][12] = (D3 + D2) / 36;
                matrixK[5][13] = -(b * b * D3 + a * a * D1) / (90 * a * b);
                matrixK[5][14] = -(D3 + D2) / 9;
                matrixK[5][15] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[5][16] = (4 * D3 + 4 * D2) / 9;
                matrixK[5][17] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);

                matrixK[6][0] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[6][1] = (D3 + D2) / 9;
                matrixK[6][2] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[6][3] = -(4 * D3 + 4 * D2) / 9;
                matrixK[6][4] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[6][5] = (D3 - D2) / 3;
                matrixK[6][6] = (32 * a * a * D3 + 56 * b * b * D1) / (45 * a * b);
                matrixK[6][7] = 0;
                matrixK[6][8] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[6][9] = -(D3 - D2) / 3;
                matrixK[6][10] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[6][11] = (4 * D3 + 4 * D2) / 9;
                matrixK[6][12] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[6][13] = -(D3 + D2) / 9;
                matrixK[6][14] = -(8 * a * a * D3 - 8 * b * b * D1) / (45 * a * b);
                matrixK[6][15] = 0;
                matrixK[6][16] = (16 * a * a * D3 - 64 * b * b * D1) / (45 * a * b);
                matrixK[6][17] = 0;

                matrixK[7][0] = (D3 + D2) / 9;
                matrixK[7][1] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[7][2] = -(4 * D3 + 4 * D2) / 9;
                matrixK[7][3] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[7][4] = -(D3 - D2) / 3;
                matrixK[7][5] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[7][6] = 0;
                matrixK[7][7] = (56 * b * b * D3 + 32 * a * a * D1) / (45 * a * b);
                matrixK[7][8] = (D3 - D2) / 3;
                matrixK[7][9] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[7][10] = (4 * D3 + 4 * D2) / 9;
                matrixK[7][11] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[7][12] = -(D3 + D2) / 9;
                matrixK[7][13] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[7][14] = 0;
                matrixK[7][15] = (8 * b * b * D3 - 8 * a * a * D1) / (45 * a * b);
                matrixK[7][16] = 0;
                matrixK[7][17] = -(64 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);

                matrixK[8][0] = -(a * a * D3 + b * b * D1) / (90 * a * b);
                matrixK[8][1] = -(D3 + D2) / 36;
                matrixK[8][2] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[8][3] = (D3 + D2) / 9;
                matrixK[8][4] = (4 * a * a * D3 - 7 * b * b * D1) / (90 * a * b);
                matrixK[8][5] = -(D3 - D2) / 12;
                matrixK[8][6] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[8][7] = (D3 - D2) / 3;
                matrixK[8][8] = (14 * a * a * D3 + 14 * b * b * D1) / (45 * a * b);
                matrixK[8][9] = (D3 + D2) / 4;
                matrixK[8][10] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[8][11] = -(D3 - D2) / 3;
                matrixK[8][12] = -(7 * a * a * D3 - 4 * b * b * D1) / (90 * a * b);
                matrixK[8][13] = (D3 - D2) / 12;
                matrixK[8][14] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[8][15] = (D3 + D2) / 9;
                matrixK[8][16] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[8][17] = -(4 * D3 + 4 * D2) / 9;

                matrixK[9][0] = -(D3 + D2) / 36;
                matrixK[9][1] = -(b * b * D3 + a * a * D1) / (90 * a * b);
                matrixK[9][2] = (D3 + D2) / 9;
                matrixK[9][3] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[9][4] = (D3 - D2) / 12;
                matrixK[9][5] = -(7 * b * b * D3 - 4 * a * a * D1) / (90 * a * b);
                matrixK[9][6] = -(D3 - D2) / 3;
                matrixK[9][7] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[9][8] = (D3 + D2) / 4;
                matrixK[9][9] = (14 * b * b * D3 + 14 * a * a * D1) / (45 * a * b);
                matrixK[9][10] = (D3 - D2) / 3;
                matrixK[9][11] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[9][12] = -(D3 - D2) / 12;
                matrixK[9][13] = (4 * b * b * D3 - 7 * a * a * D1) / (90 * a * b);
                matrixK[9][14] = (D3 + D2) / 9;
                matrixK[9][15] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[9][16] = -(4 * D3 + 4 * D2) / 9;
                matrixK[9][17] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);

                matrixK[10][0] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[10][1] = (D3 + D2) / 9;
                matrixK[10][2] = (8 * a * a * D3 - 8 * b * b * D1) / (45 * a * b);
                matrixK[10][3] = 0;
                matrixK[10][4] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[10][5] = -(D3 + D2) / 9;
                matrixK[10][6] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[10][7] = (4 * D3 + 4 * D2) / 9;
                matrixK[10][8] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[10][9] = (D3 - D2) / 3;
                matrixK[10][10] = (56 * a * a * D3 + 32 * b * b * D1) / (45 * a * b);
                matrixK[10][11] = 0;
                matrixK[10][12] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[10][13] = -(D3 - D2) / 3;
                matrixK[10][14] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[10][15] = -(4 * D3 + 4 * D2) / 9;
                matrixK[10][16] = -(64 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[10][17] = 0;

                matrixK[11][0] = (D3 + D2) / 9;
                matrixK[11][1] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[11][2] = 0;
                matrixK[11][3] = -(8 * b * b * D3 - 8 * a * a * D1) / (45 * a * b);
                matrixK[11][4] = -(D3 + D2) / 9;
                matrixK[11][5] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[11][6] = (4 * D3 + 4 * D2) / 9;
                matrixK[11][7] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[11][8] = -(D3 - D2) / 3;
                matrixK[11][9] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[11][10] = 0;
                matrixK[11][11] = (32 * b * b * D3 + 56 * a * a * D1) / (45 * a * b);
                matrixK[11][12] = (D3 - D2) / 3;
                matrixK[11][13] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[11][14] = -(4 * D3 + 4 * D2) / 9;
                matrixK[11][15] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[11][16] = 0;
                matrixK[11][17] = (16 * b * b * D3 - 64 * a * a * D1) / (45 * a * b);

                matrixK[12][0] = (4 * a * a * D3 - 7 * b * b * D1) / (90 * a * b);
                matrixK[12][1] = (D3 - D2) / 12;
                matrixK[12][2] = (a * a * D3 + 4 * b * b * D1) / (45 * a * b);
                matrixK[12][3] = -(D3 + D2) / 9;
                matrixK[12][4] = -(a * a * D3 + b * b * D1) / (90 * a * b);
                matrixK[12][5] = (D3 + D2) / 36;
                matrixK[12][6] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[12][7] = -(D3 + D2) / 9;
                matrixK[12][8] = -(7 * a * a * D3 - 4 * b * b * D1) / (90 * a * b);
                matrixK[12][9] = -(D3 - D2) / 12;
                matrixK[12][10] = (7 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[12][11] = (D3 - D2) / 3;
                matrixK[12][12] = (14 * a * a * D3 + 14 * b * b * D1) / (45 * a * b);
                matrixK[12][13] = -(D3 + D2) / 4;
                matrixK[12][14] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[12][15] = -(D3 - D2) / 3;
                matrixK[12][16] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[12][17] = (4 * D3 + 4 * D2) / 9;

                matrixK[13][0] = -(D3 - D2) / 12;
                matrixK[13][1] = -(7 * b * b * D3 - 4 * a * a * D1) / (90 * a * b);
                matrixK[13][2] = -(D3 + D2) / 9;
                matrixK[13][3] = (4 * b * b * D3 + a * a * D1) / (45 * a * b);
                matrixK[13][4] = (D3 + D2) / 36;
                matrixK[13][5] = -(b * b * D3 + a * a * D1) / (90 * a * b);
                matrixK[13][6] = -(D3 + D2) / 9;
                matrixK[13][7] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[13][8] = (D3 - D2) / 12;
                matrixK[13][9] = (4 * b * b * D3 - 7 * a * a * D1) / (90 * a * b);
                matrixK[13][10] = -(D3 - D2) / 3;
                matrixK[13][11] = -(16 * b * b * D3 - 7 * a * a * D1) / (45 * a * b);
                matrixK[13][12] = -(D3 + D2) / 4;
                matrixK[13][13] = (14 * b * b * D3 + 14 * a * a * D1) / (45 * a * b);
                matrixK[13][14] = (D3 - D2) / 3;
                matrixK[13][15] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[13][16] = (4 * D3 + 4 * D2) / 9;
                matrixK[13][17] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);

                matrixK[14][0] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[14][1] = -(D3 - D2) / 3;
                matrixK[14][2] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[14][3] = (4 * D3 + 4 * D2) / 9;
                matrixK[14][4] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[14][5] = -(D3 + D2) / 9;
                matrixK[14][6] = -(8 * a * a * D3 - 8 * b * b * D1) / (45 * a * b);
                matrixK[14][7] = 0;
                matrixK[14][8] = (4 * a * a * D3 + b * b * D1) / (45 * a * b);
                matrixK[14][9] = (D3 + D2) / 9;
                matrixK[14][10] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[14][11] = -(4 * D3 + 4 * D2) / 9;
                matrixK[14][12] = -(16 * a * a * D3 - 7 * b * b * D1) / (45 * a * b);
                matrixK[14][13] = (D3 - D2) / 3;
                matrixK[14][14] = (32 * a * a * D3 + 56 * b * b * D1) / (45 * a * b);
                matrixK[14][15] = 0;
                matrixK[14][16] = (16 * a * a * D3 - 64 * b * b * D1) / (45 * a * b);
                matrixK[14][17] = 0;

                matrixK[15][0] = (D3 - D2) / 3;
                matrixK[15][1] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[15][2] = (4 * D3 + 4 * D2) / 9;
                matrixK[15][3] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[15][4] = -(D3 + D2) / 9;
                matrixK[15][5] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[15][6] = 0;
                matrixK[15][7] = (8 * b * b * D3 - 8 * a * a * D1) / (45 * a * b);
                matrixK[15][8] = (D3 + D2) / 9;
                matrixK[15][9] = (b * b * D3 + 4 * a * a * D1) / (45 * a * b);
                matrixK[15][10] = -(4 * D3 + 4 * D2) / 9;
                matrixK[15][11] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[15][12] = -(D3 - D2) / 3;
                matrixK[15][13] = (7 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[15][14] = 0;
                matrixK[15][15] = (56 * b * b * D3 + 32 * a * a * D1) / (45 * a * b);
                matrixK[15][16] = 0;
                matrixK[15][17] = -(64 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);

                matrixK[16][0] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[16][1] = -(4 * D3 + 4 * D2) / 9;
                matrixK[16][2] = -(64 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[16][3] = 0;
                matrixK[16][4] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[16][5] = (4 * D3 + 4 * D2) / 9;
                matrixK[16][6] = (16 * a * a * D3 - 64 * b * b * D1) / (45 * a * b);
                matrixK[16][7] = 0;
                matrixK[16][8] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[16][9] = -(4 * D3 + 4 * D2) / 9;
                matrixK[16][10] = -(64 * a * a * D3 - 16 * b * b * D1) / (45 * a * b);
                matrixK[16][11] = 0;
                matrixK[16][12] = -(8 * a * a * D3 + 8 * b * b * D1) / (45 * a * b);
                matrixK[16][13] = (4 * D3 + 4 * D2) / 9;
                matrixK[16][14] = (16 * a * a * D3 - 64 * b * b * D1) / (45 * a * b);
                matrixK[16][15] = 0;
                matrixK[16][16] = (128 * a * a * D3 + 128 * b * b * D1) / (45 * a * b);
                matrixK[16][17] = 0;

                matrixK[17][0] = -(4 * D3 + 4 * D2) / 9;
                matrixK[17][1] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[17][2] = 0;
                matrixK[17][3] = (16 * b * b * D3 - 64 * a * a * D1) / (45 * a * b);
                matrixK[17][4] = (4 * D3 + 4 * D2) / 9;
                matrixK[17][5] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[17][6] = 0;
                matrixK[17][7] = -(64 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[17][8] = -(4 * D3 + 4 * D2) / 9;
                matrixK[17][9] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[17][10] = 0;
                matrixK[17][11] = (16 * b * b * D3 - 64 * a * a * D1) / (45 * a * b);
                matrixK[17][12] = (4 * D3 + 4 * D2) / 9;
                matrixK[17][13] = -(8 * b * b * D3 + 8 * a * a * D1) / (45 * a * b);
                matrixK[17][14] = 0;
                matrixK[17][15] = -(64 * b * b * D3 - 16 * a * a * D1) / (45 * a * b);
                matrixK[17][16] = 0;
                matrixK[17][17] = (128 * b * b * D3 + 128 * a * a * D1) / (45 * a * b);
            } else {
                matrixK = null;
            }
        } else {
            matrixK = null;
        }

        return matrixK;
    }
}
