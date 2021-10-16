/*
 * Esta classe fornece os pesos e as coordenadas para a quadratura de Gauss-Legendre
 * A primeira coluna recebe os pesos associados a cada ponto de Gauss
 * A segunda coluna recebe a coordenada associada a cada ponto de Gauss
 */

package app.gausslegendre;

/**
 *
 * @author André de Sousa
 */
public class WeightsCoordinates_Frames {

    /**
     * Este método fornece os pesos e as coordenadas para a quadratura de Gauss-Legendre
     *
     * @param L é o comprimento do elemento finito
     * @param points é o número de pontos de integração
     * @return
     */
    public static double[][] weightsCoordinates_Frames(double L, int points) {
        double[][] matrixWC;

        if (L > 0 && points > 0) {
            double weight = L / 2;
            double abscissa = L / 2;

            if (points == 1) {
                matrixWC = new double[1][2];

                matrixWC[0][0] = weight * 2.0000000000000000;
                matrixWC[0][1] = abscissa * 0.0000000000000000;
            } else if (points == 2) {
                matrixWC = new double[2][2];

                matrixWC[0][0] = weight * 1.0000000000000000;
                matrixWC[0][1] = abscissa * -0.5773502691896257;

                matrixWC[1][0] = weight * 1.0000000000000000;
                matrixWC[1][1] = abscissa * 0.5773502691896257;
            } else if (points == 3) {
                matrixWC = new double[3][2];

                matrixWC[0][0] = weight * 0.5555555555555556;
                matrixWC[0][1] = abscissa * -0.7745966692414834;

                matrixWC[1][0] = weight * 0.8888888888888888;
                matrixWC[1][1] = abscissa * 0.0000000000000000;

                matrixWC[2][0] = weight * 0.5555555555555556;
                matrixWC[2][1] = abscissa * 0.7745966692414834;
            } else if (points == 4) {
                matrixWC = new double[4][2];

                matrixWC[0][0] = weight * 0.3478548451374538;
                matrixWC[0][1] = abscissa * -0.8611363115940526;

                matrixWC[1][0] = weight * 0.6521451548625461;
                matrixWC[1][1] = abscissa * -0.3399810435848563;

                matrixWC[2][0] = weight * 0.6521451548625461;
                matrixWC[2][1] = abscissa * 0.3399810435848563;

                matrixWC[3][0] = weight * 0.3478548451374538;
                matrixWC[3][1] = abscissa * 0.8611363115940526;
            } else {
                matrixWC = null;
            }
        } else {
            matrixWC = null;
        }

        return matrixWC;
    }
}
