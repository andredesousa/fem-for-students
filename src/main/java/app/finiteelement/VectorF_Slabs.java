/*
 * Esta classe fornece o vetor F de um elemento de laje
 * O método requer o comprimento, a largura e o número de nós do elemento finito
 * O vetor deve depois ser multiplicado pelo valor da carga
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_Slabs {

    /**
     * Este método fornece o vetor F de um elemento de laje
     *
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_ReissnerMindlin(double a, double b, int nodes) {
        double[][] vectorF;

        if (a >= 0 && b >= 0 && nodes > 1) {
            if (nodes == 4) {
                vectorF = new double[12][1];

                vectorF[0][0] = (a * b) / 4;
                vectorF[1][0] = 0;
                vectorF[2][0] = 0;
                vectorF[3][0] = (a * b) / 4;
                vectorF[4][0] = 0;
                vectorF[5][0] = 0;
                vectorF[6][0] = (a * b) / 4;
                vectorF[7][0] = 0;
                vectorF[8][0] = 0;
                vectorF[9][0] = (a * b) / 4;
                vectorF[10][0] = 0;
                vectorF[11][0] = 0;
            } else if (nodes == 8) {
                vectorF = new double[24][1];

                vectorF[0][0] = -(a * b) / 12;
                vectorF[1][0] = 0;
                vectorF[2][0] = 0;
                vectorF[3][0] = (a * b) / 3;
                vectorF[4][0] = 0;
                vectorF[5][0] = 0;
                vectorF[6][0] = -(a * b) / 12;
                vectorF[7][0] = 0;
                vectorF[8][0] = 0;
                vectorF[9][0] = (a * b) / 3;
                vectorF[10][0] = 0;
                vectorF[11][0] = 0;
                vectorF[12][0] = -(a * b) / 12;
                vectorF[13][0] = 0;
                vectorF[14][0] = 0;
                vectorF[15][0] = (a * b) / 3;
                vectorF[16][0] = 0;
                vectorF[17][0] = 0;
                vectorF[18][0] = -(a * b) / 12;
                vectorF[19][0] = 0;
                vectorF[20][0] = 0;
                vectorF[21][0] = (a * b) / 3;
                vectorF[22][0] = 0;
                vectorF[23][0] = 0;
            } else if (nodes == 9) {
                vectorF = new double[27][1];

                vectorF[0][0] = (a * b) / 36;
                vectorF[1][0] = 0;
                vectorF[2][0] = 0;
                vectorF[3][0] = (a * b) / 9;
                vectorF[4][0] = 0;
                vectorF[5][0] = 0;
                vectorF[6][0] = (a * b) / 36;
                vectorF[7][0] = 0;
                vectorF[8][0] = 0;
                vectorF[9][0] = (a * b) / 9;
                vectorF[10][0] = 0;
                vectorF[11][0] = 0;
                vectorF[12][0] = (a * b) / 36;
                vectorF[13][0] = 0;
                vectorF[14][0] = 0;
                vectorF[15][0] = (a * b) / 9;
                vectorF[16][0] = 0;
                vectorF[17][0] = 0;
                vectorF[18][0] = (a * b) / 36;
                vectorF[19][0] = 0;
                vectorF[20][0] = 0;
                vectorF[21][0] = (a * b) / 9;
                vectorF[22][0] = 0;
                vectorF[23][0] = 0;
                vectorF[24][0] = (4 * a * b) / 9;
                vectorF[25][0] = 0;
                vectorF[26][0] = 0;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método fornece o vetor F de um elemento de laje
     *
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_Kirchhoff(double a, double b, int nodes) {
        double[][] vectorF;

        if (a != 0 && b != 0 && nodes > 1) {
            if (nodes == 4) {
                vectorF = new double[16][1];

                vectorF[0][0] = (a * b) / 4;
                vectorF[1][0] = (a * a * b) / 24;
                vectorF[2][0] = (a * b * b) / 24;
                vectorF[3][0] = (a * a * b * b) / 144;
                vectorF[4][0] = (a * b) / 4;
                vectorF[5][0] = -(a * a * b) / 24;
                vectorF[6][0] = (a * b * b) / 24;
                vectorF[7][0] = -(a * a * b * b) / 144;
                vectorF[8][0] = (a * b) / 4;
                vectorF[9][0] = -(a * a * b) / 24;
                vectorF[10][0] = -(a * b * b) / 24;
                vectorF[11][0] = (a * a * b * b) / 144;
                vectorF[12][0] = (a * b) / 4;
                vectorF[13][0] = (a * a * b) / 24;
                vectorF[14][0] = -(a * b * b) / 24;
                vectorF[15][0] = -(a * a * b * b) / 144;
            } else if (nodes == 8) {
                vectorF = new double[32][1];

                vectorF[0][0] = -(a * b) / 60;
                vectorF[1][0] = -(a * a * b) / 72;
                vectorF[2][0] = -(a * b * b) / 72;
                vectorF[3][0] = -(a * a * b * b) / 240;
                vectorF[4][0] = (4 * a * b) / 15;
                vectorF[5][0] = 0;
                vectorF[6][0] = (2 * a * b * b) / 45;
                vectorF[7][0] = 0;
                vectorF[8][0] = -(a * b) / 60;
                vectorF[9][0] = (a * a * b) / 72;
                vectorF[10][0] = -(a * b * b) / 72;
                vectorF[11][0] = (a * a * b * b) / 240;
                vectorF[12][0] = (4 * a * b) / 15;
                vectorF[13][0] = -(2 * a * a * b) / 45;
                vectorF[14][0] = 0;
                vectorF[15][0] = 0;
                vectorF[16][0] = -(a * b) / 60;
                vectorF[17][0] = (a * a * b) / 72;
                vectorF[18][0] = (a * b * b) / 72;
                vectorF[19][0] = -(a * a * b * b) / 240;
                vectorF[20][0] = (4 * a * b) / 15;
                vectorF[21][0] = 0;
                vectorF[22][0] = -(2 * a * b * b) / 45;
                vectorF[23][0] = 0;
                vectorF[24][0] = -(a * b) / 60;
                vectorF[25][0] = -(a * a * b) / 72;
                vectorF[26][0] = (a * b * b) / 72;
                vectorF[27][0] = (a * a * b * b) / 240;
                vectorF[28][0] = (4 * a * b) / 15;
                vectorF[29][0] = (2 * a * a * b) / 45;
                vectorF[30][0] = 0;
                vectorF[31][0] = 0;
            } else if (nodes == 9) {
                vectorF = new double[36][1];

                vectorF[0][0] = (49 * a * b) / 900;
                vectorF[1][0] = (7 * a * a * b) / 1800;
                vectorF[2][0] = (7 * a * b * b) / 1800;
                vectorF[3][0] = (a * a * b * b) / 3600;
                vectorF[4][0] = (28 * a * b) / 225;
                vectorF[5][0] = 0;
                vectorF[6][0] = (2 * a * b * b) / 225;
                vectorF[7][0] = 0;
                vectorF[8][0] = (49 * a * b) / 900;
                vectorF[9][0] = -(7 * a * a * b) / 1800;
                vectorF[10][0] = (7 * a * b * b) / 1800;
                vectorF[11][0] = -(a * a * b * b) / 3600;
                vectorF[12][0] = (28 * a * b) / 225;
                vectorF[13][0] = -(2 * a * a * b) / 225;
                vectorF[14][0] = 0;
                vectorF[15][0] = 0;
                vectorF[16][0] = (49 * a * b) / 900;
                vectorF[17][0] = -(7 * a * a * b) / 1800;
                vectorF[18][0] = -(7 * a * b * b) / 1800;
                vectorF[19][0] = (a * a * b * b) / 3600;
                vectorF[20][0] = (28 * a * b) / 225;
                vectorF[21][0] = 0;
                vectorF[22][0] = -(2 * a * b * b) / 225;
                vectorF[23][0] = 0;
                vectorF[24][0] = (49 * a * b) / 900;
                vectorF[25][0] = (7 * a * a * b) / 1800;
                vectorF[26][0] = -(7 * a * b * b) / 1800;
                vectorF[27][0] = -(a * a * b * b) / 3600;
                vectorF[28][0] = (28 * a * b) / 225;
                vectorF[29][0] = (2 * a * a * b) / 225;
                vectorF[30][0] = 0;
                vectorF[31][0] = 0;
                vectorF[32][0] = (64 * a * b) / 225;
                vectorF[33][0] = 0;
                vectorF[34][0] = 0;
                vectorF[35][0] = 0;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }
}
