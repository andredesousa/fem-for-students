/*
 * Esta classe fornece o vetor F de um elemento de grelha
 * O método requer o comprimento e o número de nós do elemento finito
 * O vetor F é avaliado em função do comprimento L
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_Grids {

    /**
     * Este método fornece o vetor F de um elemento de grelha
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_Grids(double L, int nodes) {
        double[][] vectorF;

        if (L >= 0 && nodes > 1) {
            if (nodes == 2) {
                vectorF = new double[6][1];

                vectorF[0][0] = L / 2;
                vectorF[1][0] = 0;
                vectorF[2][0] = L * L / 12;
                vectorF[3][0] = L / 2;
                vectorF[4][0] = 0;
                vectorF[5][0] = -L * L / 12;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }
}
