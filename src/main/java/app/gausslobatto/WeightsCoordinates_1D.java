/*
 * Esta classe fornece os pesos e as coordenadas para a quadratura de Gauss-Lobatto
 * A primeira coluna recebe os pesos associados a cada ponto de Gauss
 * A segunda coluna recebe a coordenada associada a cada ponto de Gauss
 */

package app.gausslobatto;

/**
 *
 * @author André de Sousa
 */
public class WeightsCoordinates_1D {

    /**
     * Este método fornece os pesos e as coordenadas para a quadratura de Gauss-Lobatto
     *
     * @param L é o comprimento do elemento finito
     * @param points é o número de pontos de integração
     * @return
     */
    public static double[][] weightsCoordinates_1D(double L, int points) {
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
                matrixWC[0][1] = abscissa * -1.0000000000000000;

                matrixWC[1][0] = weight * 1.0000000000000000;
                matrixWC[1][1] = abscissa * 1.0000000000000000;
            } else if (points == 3) {
                matrixWC = new double[3][2];

                matrixWC[0][0] = weight * 0.3333333333333333;
                matrixWC[0][1] = abscissa * -1.0000000000000000;

                matrixWC[1][0] = weight * 1.3333333333333333;
                matrixWC[1][1] = abscissa * 0.0000000000000000;

                matrixWC[2][0] = weight * 0.3333333333333333;
                matrixWC[2][1] = abscissa * 1.0000000000000000;
            } else if (points == 4) {
                matrixWC = new double[4][2];

                matrixWC[0][0] = weight * 0.1666666666666667;
                matrixWC[0][1] = abscissa * -1.0000000000000000;

                matrixWC[1][0] = weight * 0.8333333333333333;
                matrixWC[1][1] = abscissa * -0.4472135954999579;

                matrixWC[2][0] = weight * 0.8333333333333333;
                matrixWC[2][1] = abscissa * 0.4472135954999579;

                matrixWC[3][0] = weight * 0.1666666666666667;
                matrixWC[3][1] = abscissa * 1.0000000000000000;
            } else {
                matrixWC = null;
            }
        } else {
            matrixWC = null;
        }

        return matrixWC;
    }
}
