/*
 * Esta classe fornece o integral da matriz N de um elemento tridimensional
 * O método requer a largura, o comprimento, a altura e o número de nós do elemento finito
 * Esta matriz é fornecida para se calcular o vetor F
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class VectorF_3D {

    /**
     * Este método fornece o integral da matriz N de um elemento tridimensional
     *
     * @param a é a largura do elemento finito
     * @param b é o comprimento do elemento finito
     * @param c é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] vectorF_3D(double a, double b, double c, int nodes) {
        double[][] matrixN;

        if (a >= 0 && b >= 0 && c >= 0 && nodes > 1) {
            if (nodes == 4) {
                double N1, N2, N3, N4;

                N1 = 0;
                N2 = 0;
                N3 = 0;
                N4 = 0;

                double[][] matrix = {
                    { N1, 0, 0 },
                    { 0, N1, 0 },
                    { 0, 0, N1 },
                    { N2, 0, 0 },
                    { 0, N2, 0 },
                    { 0, 0, N2 },
                    { N3, 0, 0 },
                    { 0, N3, 0 },
                    { 0, 0, N3 },
                    { N4, 0, 0 },
                    { 0, N4, 0 },
                    { 0, 0, N4 },
                };

                matrixN = matrix;
            } else if (nodes == 8) {
                double N1, N2, N3, N4, N5, N6, N7, N8;

                N1 = 0.125 * a * b * c;
                N2 = 0.125 * a * b * c;
                N3 = 0.125 * a * b * c;
                N4 = 0.125 * a * b * c;
                N5 = 0.125 * a * b * c;
                N6 = 0.125 * a * b * c;
                N7 = 0.125 * a * b * c;
                N8 = 0.125 * a * b * c;

                double[][] matrix = {
                    { N1, 0, 0 },
                    { 0, N1, 0 },
                    { 0, 0, N1 },
                    { N2, 0, 0 },
                    { 0, N2, 0 },
                    { 0, 0, N2 },
                    { N3, 0, 0 },
                    { 0, N3, 0 },
                    { 0, 0, N3 },
                    { N4, 0, 0 },
                    { 0, N4, 0 },
                    { 0, 0, N4 },
                    { N5, 0, 0 },
                    { 0, N5, 0 },
                    { 0, 0, N5 },
                    { N6, 0, 0 },
                    { 0, N6, 0 },
                    { 0, 0, N6 },
                    { N7, 0, 0 },
                    { 0, N7, 0 },
                    { 0, 0, N7 },
                    { N8, 0, 0 },
                    { 0, N8, 0 },
                    { 0, 0, N8 },
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
