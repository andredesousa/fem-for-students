/*
 * Esta classe fornece os pesos e as coordenadas para a quadratura de Gauss-Legendre
 * As colunas pares recebem os pesos associados a cada ponto de Gauss
 * As colunas ímpares recebem as coordenadas associadas a cada ponto de Gauss
 */

package app.gausslegendre;

/**
 *
 * @author André de Sousa
 */
public class WeightsCoordinates_3D {

    /**
     * Este método fornece os pesos e as coordenadas para a quadratura de Gauss-Legendre
     *
     * @param a é a largura do elemento finito
     * @param b é o comprimento do elemento finito
     * @param c é a altura do elemento finito
     * @param points é o número de pontos de integração
     * @return
     */
    public static double[][] weightsCoordinates_3D(double a, double b, double c, int points) {
        double[][] matrixWC;

        if (a > 0 && b > 0 && c > 0 && points > 0) {
            double weightX, weightY, weightZ, pointX, pointY, pointZ;

            weightX = a / 2;
            weightY = b / 2;
            weightZ = c / 2;
            pointX = a / 2;
            pointY = b / 2;
            pointZ = c / 2;

            if (points == 1) {
                matrixWC = new double[1][6];

                matrixWC[0][0] = weightX * 2.0000000000000000;
                matrixWC[0][1] = pointX * 0.0000000000000000;
                matrixWC[0][2] = weightY * 2.0000000000000000;
                matrixWC[0][3] = pointY * 0.0000000000000000;
                matrixWC[0][4] = weightZ * 2.0000000000000000;
                matrixWC[0][5] = pointZ * 0.0000000000000000;
            } else if (points == 8) {
                matrixWC = new double[8][6];

                matrixWC[0][0] = weightX * 1.0000000000000000;
                matrixWC[0][1] = pointX * -0.5773502691896257;
                matrixWC[0][2] = weightY * 1.0000000000000000;
                matrixWC[0][3] = pointY * -0.5773502691896257;
                matrixWC[0][4] = weightZ * 1.0000000000000000;
                matrixWC[0][5] = pointZ * -0.5773502691896257;

                matrixWC[1][0] = weightX * 1.0000000000000000;
                matrixWC[1][1] = pointX * 0.5773502691896257;
                matrixWC[1][2] = weightY * 1.0000000000000000;
                matrixWC[1][3] = pointY * -0.5773502691896257;
                matrixWC[1][4] = weightZ * 1.0000000000000000;
                matrixWC[1][5] = pointZ * -0.5773502691896257;

                matrixWC[2][0] = weightX * 1.0000000000000000;
                matrixWC[2][1] = pointX * 0.5773502691896257;
                matrixWC[2][2] = weightY * 1.0000000000000000;
                matrixWC[2][3] = pointY * 0.5773502691896257;
                matrixWC[2][4] = weightZ * 1.0000000000000000;
                matrixWC[2][5] = pointZ * -0.5773502691896257;

                matrixWC[3][0] = weightX * 1.0000000000000000;
                matrixWC[3][1] = pointX * -0.5773502691896257;
                matrixWC[3][2] = weightY * 1.0000000000000000;
                matrixWC[3][3] = pointY * 0.5773502691896257;
                matrixWC[3][4] = weightZ * 1.0000000000000000;
                matrixWC[3][5] = pointZ * -0.5773502691896257;

                matrixWC[4][0] = weightX * 1.0000000000000000;
                matrixWC[4][1] = pointX * -0.5773502691896257;
                matrixWC[4][2] = weightY * 1.0000000000000000;
                matrixWC[4][3] = pointY * -0.5773502691896257;
                matrixWC[4][4] = weightZ * 1.0000000000000000;
                matrixWC[4][5] = pointZ * 0.5773502691896257;

                matrixWC[5][0] = weightX * 1.0000000000000000;
                matrixWC[5][1] = pointX * 0.5773502691896257;
                matrixWC[5][2] = weightY * 1.0000000000000000;
                matrixWC[5][3] = pointY * -0.5773502691896257;
                matrixWC[5][4] = weightZ * 1.0000000000000000;
                matrixWC[5][5] = pointZ * 0.5773502691896257;

                matrixWC[6][0] = weightX * 1.0000000000000000;
                matrixWC[6][1] = pointX * 0.5773502691896257;
                matrixWC[6][2] = weightY * 1.0000000000000000;
                matrixWC[6][3] = pointY * 0.5773502691896257;
                matrixWC[6][4] = weightZ * 1.0000000000000000;
                matrixWC[6][5] = pointZ * 0.5773502691896257;

                matrixWC[7][0] = weightX * 1.0000000000000000;
                matrixWC[7][1] = pointX * -0.5773502691896257;
                matrixWC[7][2] = weightY * 1.0000000000000000;
                matrixWC[7][3] = pointY * 0.5773502691896257;
                matrixWC[7][4] = weightZ * 1.0000000000000000;
                matrixWC[7][5] = pointZ * 0.5773502691896257;
            } else {
                matrixWC = null;
            }
        } else {
            matrixWC = null;
        }

        return matrixWC;
    }
}
