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
public class DerivedMatrix_Frames {

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eixo r
     * @param L é o comprimento do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrix_Frames(double r, double L, int nodes) {
        double[][] matrixB;

        if (L != 0 && nodes > 1) {
            if (nodes == 2) {
                matrixB = null;
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }
}
