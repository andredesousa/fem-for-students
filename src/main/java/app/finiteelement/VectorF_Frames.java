/*
 * Esta classe fornece o vetor F de um elemento de barra
 * O método requer o comprimento, as cargas e o número de nós do elemento finito
 * O vetor F é avaliado em função do comprimento L
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_Frames {

    /**
     * Este método fornece o vetor F de um elemento de barra
     *
     * @param L é o comprimento do elemento finito
     * @param p é a carga axial distribuída no elemento finito
     * @param q é a carga transversal distribuída no elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_Frames(double L, double p, double q, int nodes) {
        double[][] vectorF;

        if (L >= 0 && nodes > 1) {
            if (nodes == 2) {
                vectorF = new double[6][1];

                vectorF[0][0] = (p * L) / 2;
                vectorF[1][0] = (q * L) / 2;
                vectorF[2][0] = (q * L * L) / 12;
                vectorF[3][0] = (p * L) / 2;
                vectorF[4][0] = (q * L) / 2;
                vectorF[5][0] = -(q * L * L) / 12;
            } else {
                vectorF = null;
            }
        } else {
            vectorF = null;
        }

        return vectorF;
    }

    /**
     * Este método fornece o vetor F de um elemento de barra
     *
     * @param Tmean  é o valor da variação térmica linear
     * @param deltaT é o valor da variação térmica diferencial
     * @param h é a altura da secção do elemento finito
     * @param EA é a rigidez axial do elemento finito
     * @param EI é a rigidez de flexão do elemento finito
     * @param alpha é o coeficiente de dilatação térmica linear
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_Frames(double Tmean, double deltaT, double h, double EA, double EI, double alpha, int nodes) {
        double[][] vectorF;

        if (nodes == 2) {
            vectorF = new double[6][1];

            vectorF[0][0] = -Tmean * EA * alpha;
            vectorF[1][0] = 0;
            vectorF[2][0] = (deltaT * EI * alpha) / h;
            vectorF[3][0] = Tmean * EA * alpha;
            vectorF[4][0] = 0;
            vectorF[5][0] = -(deltaT * EI * alpha) / h;
        } else {
            vectorF = null;
        }

        return vectorF;
    }
}
