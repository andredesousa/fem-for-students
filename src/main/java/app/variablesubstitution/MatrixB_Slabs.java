/*
 * Esta classe fornece a matriz B de um elemento finito do tipo laje
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_Slabs {

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB é a matriz das derivadas das funções de forma
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_ReissnerMindlin(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
            if (nodes == 4) {
                //Funções de forma do elemento finito
                double N1 = 0;
                double N2 = 0;
                double N3 = 0;
                double N4 = 0;

                //Derivadas das funções de forma
                double N1x = 0;
                double N1y = 0;
                double N2x = 0;
                double N2y = 0;
                double N3x = 0;
                double N3y = 0;
                double N4x = 0;
                double N4y = 0;

                double[][] matrix = {
                    { 0, N1x, 0, 0, N2x, 0, 0, N3x, 0, 0, N4x, 0 },
                    { 0, 0, N1y, 0, 0, N2y, 0, 0, N3y, 0, 0, N4y },
                    { 0, N1y, N1x, 0, N2y, N2x, 0, N3y, N3x, 0, N4y, N4x },
                    { -N1x, N1, 0, -N2x, N2, 0, -N3x, N3, 0, -N4x, N4, 0 },
                    { -N1y, 0, N1, -N2y, 0, N2, -N3y, 0, N3, -N4y, 0, N4 },
                };

                matrixB = matrix;
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB é a matriz das derivadas das funções de forma
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_Kirchhoff(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
            if (nodes == 4) {
                //Segundas derivadas das funções de forma d2N/dx2
                double N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x;
                double N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x;

                N1x = 0;
                N2x = 0;
                N3x = 0;
                N4x = 0;
                N5x = 0;
                N6x = 0;
                N7x = 0;
                N8x = 0;
                N9x = 0;
                N10x = 0;
                N11x = 0;
                N12x = 0;
                N13x = 0;
                N14x = 0;
                N15x = 0;
                N16x = 0;

                //Segundas derivadas das funções de forma d2N/dy2
                double N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y;
                double N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y;

                N1y = 0;
                N2y = 0;
                N3y = 0;
                N4y = 0;
                N5y = 0;
                N6y = 0;
                N7y = 0;
                N8y = 0;
                N9y = 0;
                N10y = 0;
                N11y = 0;
                N12y = 0;
                N13y = 0;
                N14y = 0;
                N15y = 0;
                N16y = 0;

                //Segundas derivadas das funções de forma d2N/dxdy
                double N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy;
                double N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy;

                N1xy = 0;
                N2xy = 0;
                N3xy = 0;
                N4xy = 0;
                N5xy = 0;
                N6xy = 0;
                N7xy = 0;
                N8xy = 0;
                N9xy = 0;
                N10xy = 0;
                N11xy = 0;
                N12xy = 0;
                N13xy = 0;
                N14xy = 0;
                N15xy = 0;
                N16xy = 0;

                //Construção da matriz B a partir de uma matriz auxiliar
                double[][] matrix = {
                    { N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x },
                    { N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y },
                    { N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy, N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy },
                };

                matrixB = matrix;
            } else {
                matrixB = null;
            }
        } else {
            matrixB = null;
        }

        return matrixB;
    }
}
