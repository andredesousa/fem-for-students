/*
 * Esta classe fornece o vetor F de um elemento de viga
 * O método requer o comprimento e o número de nós do elemento finito
 * O vetor F é avaliado em função do comprimento L
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_Beams {

    /**
     * Este método fornece o vetor F de um elemento de viga
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_EulerBernoulli(double L, int nodes) {
        double[][] vectorF;

        if (L >= 0 && nodes > 1) {
            if (nodes == 2) {
                vectorF = new double[4][1];

                vectorF[0][0] = L / 2;
                vectorF[1][0] = L * L / 12;
                vectorF[2][0] = L / 2;
                vectorF[3][0] = -L * L / 12;
            } else if (nodes == 3) {
                vectorF = new double[6][1];

                vectorF[0][0] = 7 * L / 30;
                vectorF[1][0] = L * L / 60;
                vectorF[2][0] = 8 * L / 15;
                vectorF[3][0] = 0;
                vectorF[4][0] = 7 * L / 30;
                vectorF[5][0] = -L * L / 60;
            } else if (nodes == 4) {
                vectorF = new double[8][1];

                vectorF[0][0] = (31 * L) / 224;
                vectorF[1][0] = (19 * L * L) / 3360;
                vectorF[2][0] = (81 * L) / 224;
                vectorF[3][0] = -(9 * L * L) / 1120;
                vectorF[4][0] = (81 * L) / 224;
                vectorF[5][0] = (9 * L * L) / 1120;
                vectorF[6][0] = (31 * L) / 224;
                vectorF[7][0] = -(19 * L * L) / 3360;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método fornece o vetor F de um elemento de viga
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_Timoshenko(double L, int nodes) {
        double[][] vectorF;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                vectorF = new double[4][1];

                vectorF[0][0] = L / 2;
                vectorF[1][0] = 0;
                vectorF[2][0] = L / 2;
                vectorF[3][0] = 0;
            } else if (nodes == 3) {
                vectorF = new double[6][1];

                vectorF[0][0] = L / 6;
                vectorF[1][0] = 0;
                vectorF[2][0] = 2 * L / 3;
                vectorF[3][0] = 0;
                vectorF[4][0] = L / 6;
                vectorF[5][0] = 0;
            } else if (nodes == 4) {
                vectorF = new double[8][1];

                vectorF[0][0] = L / 8;
                vectorF[1][0] = 0;
                vectorF[2][0] = 3 * L / 8;
                vectorF[3][0] = 0;
                vectorF[4][0] = 3 * L / 8;
                vectorF[5][0] = 0;
                vectorF[6][0] = L / 8;
                vectorF[7][0] = 0;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }
}
