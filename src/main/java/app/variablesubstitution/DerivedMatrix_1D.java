/*
 * Esta classe fornece as derivadas das funções de forma de um elemento finito
 * O método requer a coordenada r, e o número de nós do elemento finito
 * A matriz deve ser posteriormente multiplicada pelas coordenadas cartesianas
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class DerivedMatrix_1D {

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eixo r
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrix_1D(double r, double L, int nodes) {
        double[][] matrixJ;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixJ = new double[2][1];

                matrixJ[0][0] = -1 / L;
                matrixJ[1][0] = 1 / L;
            } else if (nodes == 3) {
                matrixJ = new double[3][1];

                matrixJ[0][0] = -1 / L + 4 * r / (L * L);
                matrixJ[1][0] = -8 * r / (L * L);
                matrixJ[2][0] = 1 / L + 4 * r / (L * L);
            } else if (nodes == 4) {
                matrixJ = new double[4][1];

                matrixJ[0][0] = 1 / (8 * L) + 18 * r / (4 * (L * L)) - 27 * (r * r) / (4 * (L * L * L));
                matrixJ[1][0] = -27 / (8 * L) - 18 * r / (4 * (L * L)) + 81 * (r * r) / (2 * (L * L * L));
                matrixJ[2][0] = 27 / (8 * L) - 18 * r / (4 * (L * L)) - 81 * (r * r) / (2 * (L * L * L));
                matrixJ[3][0] = -1 / (8 * L) + 18 * r / (4 * (L * L)) + 27 * (r * r) / (4 * (L * L * L));
            } else {
                matrixJ = null;
            }
        } else {
            matrixJ = null;
        }

        return matrixJ;
    }
}
