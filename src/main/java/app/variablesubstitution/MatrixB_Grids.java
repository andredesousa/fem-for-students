/*
 * Esta classe fornece a matriz B de um elemento finito de grelha
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_Grids {

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB é a matriz das derivadas das funções de forma
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_Grids(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
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
