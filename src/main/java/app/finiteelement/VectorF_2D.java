/*
 * Esta classe fornece o integral da matriz N de um elemento bidimensional
 * O método requer o comprimento, a altura e o número de nós do elemento finito
 * Esta matriz é fornecida para se calcular o vetor F
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_2D {

    /**
     * Este método fornece o integral da matriz N de um elemento bidimensional
     *
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_2D(double a, double b, int nodes) {
        double[][] matrixN;

        if (a >= 0 && b >= 0 && nodes > 1) {
            if (nodes == 3) {
                double N1, N2, N3;

                N1 = (a * b) / 6;
                N2 = (a * b) / 6;
                N3 = (a * b) / 6;

                double[][] matrix = { { N1, 0 }, { 0, N1 }, { N2, 0 }, { 0, N2 }, { N3, 0 }, { 0, N3 } };

                matrixN = matrix;
            } else if (nodes == 4) {
                double N1, N2, N3, N4;

                N1 = (a * b) / 4;
                N2 = (a * b) / 4;
                N3 = (a * b) / 4;
                N4 = (a * b) / 4;

                double[][] matrix = { { N1, 0 }, { 0, N1 }, { N2, 0 }, { 0, N2 }, { N3, 0 }, { 0, N3 }, { N4, 0 }, { 0, N4 } };

                matrixN = matrix;
            } else if (nodes == 6) {
                double N1, N2, N3, N4, N5, N6;

                N1 = 0;
                N2 = (a * b) / 6;
                N3 = 0;
                N4 = (a * b) / 6;
                N5 = 0;
                N6 = (a * b) / 6;

                double[][] matrix = {
                    { N1, 0 },
                    { 0, N1 },
                    { N2, 0 },
                    { 0, N2 },
                    { N3, 0 },
                    { 0, N3 },
                    { N4, 0 },
                    { 0, N4 },
                    { N5, 0 },
                    { 0, N5 },
                    { N6, 0 },
                    { 0, N6 },
                };

                matrixN = matrix;
            } else if (nodes == 8) {
                double N1, N2, N3, N4, N5, N6, N7, N8;

                N1 = -(a * b) / 12;
                N2 = (a * b) / 3;
                N3 = -(a * b) / 12;
                N4 = (a * b) / 3;
                N5 = -(a * b) / 12;
                N6 = (a * b) / 3;
                N7 = -(a * b) / 12;
                N8 = (a * b) / 3;

                double[][] matrix = {
                    { N1, 0 },
                    { 0, N1 },
                    { N2, 0 },
                    { 0, N2 },
                    { N3, 0 },
                    { 0, N3 },
                    { N4, 0 },
                    { 0, N4 },
                    { N5, 0 },
                    { 0, N5 },
                    { N6, 0 },
                    { 0, N6 },
                    { N7, 0 },
                    { 0, N7 },
                    { N8, 0 },
                    { 0, N8 },
                };

                matrixN = matrix;
            } else if (nodes == 9) {
                double N1, N2, N3, N4, N5, N6, N7, N8, N9;

                N1 = (a * b) / 36;
                N2 = (a * b) / 9;
                N3 = (a * b) / 36;
                N4 = (a * b) / 9;
                N5 = (a * b) / 36;
                N6 = (a * b) / 9;
                N7 = (a * b) / 36;
                N8 = (a * b) / 9;
                N9 = (4 * a * b) / 9;

                double[][] matrix = {
                    { N1, 0 },
                    { 0, N1 },
                    { N2, 0 },
                    { 0, N2 },
                    { N3, 0 },
                    { 0, N3 },
                    { N4, 0 },
                    { 0, N4 },
                    { N5, 0 },
                    { 0, N5 },
                    { N6, 0 },
                    { 0, N6 },
                    { N7, 0 },
                    { 0, N7 },
                    { N8, 0 },
                    { 0, N8 },
                    { N9, 0 },
                    { 0, N9 },
                };

                matrixN = matrix;
            } else {
                matrixN = null;
            }
        } else {
            matrixN = null;
        }

        return matrixN;
    }

    /**
     * Este método fornece o integral da matriz N de um elemento bidimensional
     *
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_2D(double L, int nodes) {
        double[][] matrixN;

        if (L >= 0 && nodes > 1) {
            if (nodes == 3) {
                double N1, N2, N3;

                N1 = L / 2;
                N2 = L / 2;
                N3 = L / 2;

                double[][] matrix = { { N1, 0 }, { 0, N1 }, { N2, 0 }, { 0, N2 }, { N3, 0 }, { 0, N3 } };

                matrixN = matrix;
            } else if (nodes == 4) {
                double N1, N2, N3, N4;

                N1 = L / 2;
                N2 = L / 2;
                N3 = L / 2;
                N4 = L / 2;

                double[][] matrix = { { N1, 0 }, { 0, N1 }, { N2, 0 }, { 0, N2 }, { N3, 0 }, { 0, N3 }, { N4, 0 }, { 0, N4 } };

                matrixN = matrix;
            } else if (nodes == 6) {
                double N1, N2, N3, N4, N5, N6;

                N1 = L / 6;
                N2 = 2 * L / 3;
                N3 = L / 6;
                N4 = 2 * L / 3;
                N5 = L / 6;
                N6 = 2 * L / 3;

                double[][] matrix = {
                    { N1, 0 },
                    { 0, N1 },
                    { N2, 0 },
                    { 0, N2 },
                    { N3, 0 },
                    { 0, N3 },
                    { N4, 0 },
                    { 0, N4 },
                    { N5, 0 },
                    { 0, N5 },
                    { N6, 0 },
                    { 0, N6 },
                };

                matrixN = matrix;
            } else if (nodes == 8) {
                double N1, N2, N3, N4, N5, N6, N7, N8;

                N1 = L / 6;
                N2 = 2 * L / 3;
                N3 = L / 6;
                N4 = 2 * L / 3;
                N5 = L / 6;
                N6 = 2 * L / 3;
                N7 = L / 6;
                N8 = 2 * L / 3;

                double[][] matrix = {
                    { N1, 0 },
                    { 0, N1 },
                    { N2, 0 },
                    { 0, N2 },
                    { N3, 0 },
                    { 0, N3 },
                    { N4, 0 },
                    { 0, N4 },
                    { N5, 0 },
                    { 0, N5 },
                    { N6, 0 },
                    { 0, N6 },
                    { N7, 0 },
                    { 0, N7 },
                    { N8, 0 },
                    { 0, N8 },
                };

                matrixN = matrix;
            } else if (nodes == 9) {
                double N1, N2, N3, N4, N5, N6, N7, N8, N9;

                N1 = L / 6;
                N2 = 2 * L / 3;
                N3 = L / 6;
                N4 = 2 * L / 3;
                N5 = L / 6;
                N6 = 2 * L / 3;
                N7 = L / 6;
                N8 = 2 * L / 3;
                N9 = 0;

                double[][] matrix = {
                    { N1, 0 },
                    { 0, N1 },
                    { N2, 0 },
                    { 0, N2 },
                    { N3, 0 },
                    { 0, N3 },
                    { N4, 0 },
                    { 0, N4 },
                    { N5, 0 },
                    { 0, N5 },
                    { N6, 0 },
                    { 0, N6 },
                    { N7, 0 },
                    { 0, N7 },
                    { N8, 0 },
                    { 0, N8 },
                    { N9, 0 },
                    { 0, N9 },
                };

                matrixN = matrix;
            } else {
                matrixN = null;
            }
        } else {
            matrixN = null;
        }

        return matrixN;
    }
}
