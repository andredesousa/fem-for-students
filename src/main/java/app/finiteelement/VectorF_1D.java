/*
 * Esta classe fornece o vetor F de um elemento unidimensional
 * O método requer o comprimento e o número de nós do elemento finito
 * O vetor F é avaliado em função do comprimento L
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_1D {

    /**
     * Este método fornece o vetor F de um elemento unidimensional
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_1D(double L, int nodes) {
        double[][] vectorF;

        if (L >= 0 && nodes > 1) {
            if (nodes == 2) {
                vectorF = new double[4][1];
                vectorF[0][0] = L / 2;
                vectorF[1][0] = 0;
                vectorF[2][0] = L / 2;
                vectorF[3][0] = 0;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método fornece o vetor F de um elemento unidimensional
     *
     * @param Tmean é o valor da variação térmica no elemento finito
     * @param EA é a rigidez axial do elemento finito
     * @param alpha é o coeficiente de dilatação térmica linear
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_1D(double Tmean, double EA, double alpha, int nodes) {
        double[][] vectorF;

        if (nodes == 2) {
            vectorF = new double[4][1];
            vectorF[0][0] = -Tmean * EA * alpha;
            vectorF[1][0] = 0;
            vectorF[2][0] = Tmean * EA * alpha;
            vectorF[3][0] = 0;
        } else {
            vectorF = null;
        }

        return vectorF;
    }
}
