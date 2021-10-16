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
public class WeightsCoordinates_2D {

    /**
     * Este método fornece os pesos e as coordenadas para a quadratura de Gauss-Legendre
     *
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param points é o número de pontos de integração
     * @return
     */
    public static double[][] weightsCoordinates_Quadrilaterals(double a, double b, int points) {
        double[][] matrixWC;

        if (a > 0 && b > 0 && points > 0) {
            double weightX, weightY, pointX, pointY;

            weightX = a / 2;
            weightY = b / 2;
            pointX = a / 2;
            pointY = b / 2;

            if (points == 1) {
                matrixWC = new double[1][4];

                matrixWC[0][0] = weightX * 2.0000000000000000;
                matrixWC[0][1] = pointX * 0.0000000000000000;
                matrixWC[0][2] = weightY * 2.0000000000000000;
                matrixWC[0][3] = pointY * 0.0000000000000000;
            } else if (points == 4) {
                matrixWC = new double[4][4];

                matrixWC[0][0] = weightX * 1.0000000000000000;
                matrixWC[0][1] = pointX * -0.5773502691896257;
                matrixWC[0][2] = weightY * 1.0000000000000000;
                matrixWC[0][3] = pointY * -0.5773502691896257;

                matrixWC[1][0] = weightX * 1.0000000000000000;
                matrixWC[1][1] = pointX * 0.5773502691896257;
                matrixWC[1][2] = weightY * 1.0000000000000000;
                matrixWC[1][3] = pointY * -0.5773502691896257;

                matrixWC[2][0] = weightX * 1.0000000000000000;
                matrixWC[2][1] = pointX * 0.5773502691896257;
                matrixWC[2][2] = weightY * 1.0000000000000000;
                matrixWC[2][3] = pointY * 0.5773502691896257;

                matrixWC[3][0] = weightX * 1.0000000000000000;
                matrixWC[3][1] = pointX * -0.5773502691896257;
                matrixWC[3][2] = weightY * 1.0000000000000000;
                matrixWC[3][3] = pointY * 0.5773502691896257;
            } else if (points == 9) {
                matrixWC = new double[9][4];

                matrixWC[0][0] = weightX * 0.5555555555555556;
                matrixWC[0][1] = pointX * -0.7745966692414834;
                matrixWC[0][2] = weightY * 0.5555555555555556;
                matrixWC[0][3] = pointY * -0.7745966692414834;

                matrixWC[1][0] = weightX * 0.8888888888888888;
                matrixWC[1][1] = pointX * 0.0000000000000000;
                matrixWC[1][2] = weightY * 0.5555555555555556;
                matrixWC[1][3] = pointY * -0.7745966692414834;

                matrixWC[2][0] = weightX * 0.5555555555555556;
                matrixWC[2][1] = pointX * 0.7745966692414834;
                matrixWC[2][2] = weightY * 0.5555555555555556;
                matrixWC[2][3] = pointY * -0.7745966692414834;

                matrixWC[3][0] = weightX * 0.5555555555555556;
                matrixWC[3][1] = pointX * 0.7745966692414834;
                matrixWC[3][2] = weightY * 0.8888888888888888;
                matrixWC[3][3] = pointY * 0.0000000000000000;

                matrixWC[4][0] = weightX * 0.5555555555555556;
                matrixWC[4][1] = pointX * 0.7745966692414834;
                matrixWC[4][2] = weightY * 0.5555555555555556;
                matrixWC[4][3] = pointY * 0.7745966692414834;

                matrixWC[5][0] = weightX * 0.8888888888888888;
                matrixWC[5][1] = pointX * 0.0000000000000000;
                matrixWC[5][2] = weightY * 0.5555555555555556;
                matrixWC[5][3] = pointY * 0.7745966692414834;

                matrixWC[6][0] = weightX * 0.5555555555555556;
                matrixWC[6][1] = pointX * -0.7745966692414834;
                matrixWC[6][2] = weightY * 0.5555555555555556;
                matrixWC[6][3] = pointY * 0.7745966692414834;

                matrixWC[7][0] = weightX * 0.5555555555555556;
                matrixWC[7][1] = pointX * -0.7745966692414834;
                matrixWC[7][2] = weightY * 0.8888888888888888;
                matrixWC[7][3] = pointY * 0.0000000000000000;

                matrixWC[8][0] = weightX * 0.8888888888888888;
                matrixWC[8][1] = pointX * 0.0000000000000000;
                matrixWC[8][2] = weightY * 0.8888888888888888;
                matrixWC[8][3] = pointY * 0.0000000000000000;
            } else if (points == 16) {
                matrixWC = new double[16][4];

                matrixWC[0][0] = weightX * 0.3478548451374538;
                matrixWC[0][1] = pointX * -0.8611363115940526;
                matrixWC[0][2] = weightY * 0.3478548451374538;
                matrixWC[0][3] = pointY * -0.8611363115940526;

                matrixWC[1][0] = weightX * 0.6521451548625461;
                matrixWC[1][1] = pointX * -0.3399810435848563;
                matrixWC[1][2] = weightY * 0.3478548451374538;
                matrixWC[1][3] = pointY * -0.8611363115940526;

                matrixWC[2][0] = weightX * 0.6521451548625461;
                matrixWC[2][1] = pointX * 0.3399810435848563;
                matrixWC[2][2] = weightY * 0.3478548451374538;
                matrixWC[2][3] = pointY * -0.8611363115940526;

                matrixWC[3][0] = weightX * 0.3478548451374538;
                matrixWC[3][1] = pointX * 0.8611363115940526;
                matrixWC[3][2] = weightY * 0.3478548451374538;
                matrixWC[3][3] = pointY * -0.8611363115940526;

                matrixWC[4][0] = weightX * 0.3478548451374538;
                matrixWC[4][1] = pointX * 0.8611363115940526;
                matrixWC[4][2] = weightY * 0.6521451548625461;
                matrixWC[4][3] = pointY * -0.3399810435848563;

                matrixWC[5][0] = weightX * 0.3478548451374538;
                matrixWC[5][1] = pointX * 0.8611363115940526;
                matrixWC[5][2] = weightY * 0.6521451548625461;
                matrixWC[5][3] = pointY * 0.3399810435848563;

                matrixWC[6][0] = weightX * 0.3478548451374538;
                matrixWC[6][1] = pointX * 0.8611363115940526;
                matrixWC[6][2] = weightY * 0.3478548451374538;
                matrixWC[6][3] = pointY * 0.8611363115940526;

                matrixWC[7][0] = weightX * 0.6521451548625461;
                matrixWC[7][1] = pointX * 0.3399810435848563;
                matrixWC[7][2] = weightY * 0.3478548451374538;
                matrixWC[7][3] = pointY * 0.8611363115940526;

                matrixWC[8][0] = weightX * 0.6521451548625461;
                matrixWC[8][1] = pointX * -0.3399810435848563;
                matrixWC[8][2] = weightY * 0.3478548451374538;
                matrixWC[8][3] = pointY * 0.8611363115940526;

                matrixWC[9][0] = weightX * 0.3478548451374538;
                matrixWC[9][1] = pointX * -0.8611363115940526;
                matrixWC[9][2] = weightY * 0.3478548451374538;
                matrixWC[9][3] = pointY * 0.8611363115940526;

                matrixWC[10][0] = weightX * 0.3478548451374538;
                matrixWC[10][1] = pointX * -0.8611363115940526;
                matrixWC[10][2] = weightY * 0.6521451548625461;
                matrixWC[10][3] = pointY * 0.3399810435848563;

                matrixWC[11][0] = weightX * 0.3478548451374538;
                matrixWC[11][1] = pointX * -0.8611363115940526;
                matrixWC[11][2] = weightY * 0.6521451548625461;
                matrixWC[11][3] = pointY * -0.3399810435848563;

                matrixWC[12][0] = weightX * 0.6521451548625461;
                matrixWC[12][1] = pointX * -0.3399810435848563;
                matrixWC[12][2] = weightY * 0.6521451548625461;
                matrixWC[12][3] = pointY * -0.3399810435848563;

                matrixWC[13][0] = weightX * 0.6521451548625461;
                matrixWC[13][1] = pointX * 0.3399810435848563;
                matrixWC[13][2] = weightY * 0.6521451548625461;
                matrixWC[13][3] = pointY * -0.3399810435848563;

                matrixWC[14][0] = weightX * 0.6521451548625461;
                matrixWC[14][1] = pointX * 0.3399810435848563;
                matrixWC[14][2] = weightY * 0.6521451548625461;
                matrixWC[14][3] = pointY * 0.3399810435848563;

                matrixWC[15][0] = weightX * 0.6521451548625461;
                matrixWC[15][1] = pointX * -0.3399810435848563;
                matrixWC[15][2] = weightY * 0.6521451548625461;
                matrixWC[15][3] = pointY * 0.3399810435848563;
            } else {
                matrixWC = null;
            }
        } else {
            matrixWC = null;
        }

        return matrixWC;
    }

    /**
     * Este método fornece os pesos e as coordenadas para a quadratura de Gauss-Legendre
     *
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param points é o número de pontos de integração
     * @return
     */
    public static double[][] weightsCoordinates_Triangles(double a, double b, int points) {
        double[][] matrixWC;

        if (a > 0 && b > 0 && points > 0) {
            double weightX, weightY, pointX, pointY;

            weightX = a / 2;
            weightY = b / 2;
            pointX = a / 2;
            pointY = b / 2;

            if (points == 1) {
                matrixWC = new double[1][4];

                matrixWC[0][0] = weightX * 1.4142135623730951;
                matrixWC[0][1] = pointX * 0.6666666666666666;
                matrixWC[0][2] = weightY * 1.4142135623730951;
                matrixWC[0][3] = pointY * 0.6666666666666666;
            } else if (points == 3) {
                matrixWC = new double[3][4];

                matrixWC[0][0] = weightX * 0.8164965809277259;
                matrixWC[0][1] = pointX * 0.3333333333333333;
                matrixWC[0][2] = weightY * 0.8164965809277259;
                matrixWC[0][3] = pointY * 0.3333333333333333;

                matrixWC[1][0] = weightX * 0.8164965809277259;
                matrixWC[1][1] = pointX * 1.3333333333333333;
                matrixWC[1][2] = weightY * 0.8164965809277259;
                matrixWC[1][3] = pointY * 0.3333333333333333;

                matrixWC[2][0] = weightX * 0.8164965809277259;
                matrixWC[2][1] = pointX * 0.3333333333333333;
                matrixWC[2][2] = weightY * 0.8164965809277259;
                matrixWC[2][3] = pointY * 1.3333333333333333;
            } else {
                matrixWC = null;
            }
        } else {
            matrixWC = null;
        }

        return matrixWC;
    }
}
