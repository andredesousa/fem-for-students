/*
 * Esta classe fornece a matriz de tranformação de coordenadas
 * Os método correspodem a cada tipo de elemento finito
 * Para calcular a transformação de coordenadas é exigido o ângulo
 */

package app.finiteelement;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author André de Sousa
 */
public class MatrixT {

    /**
     * Matriz de transformação de coordenadas de uma barra articulada
     *
     * @param angle
     * @return
     */
    public static double[][] matrixT_1D(double angle) {
        double[][] matrixT = {
            { cos(angle), sin(angle), 0, 0 },
            { -sin(angle), cos(angle), 0, 0 },
            { 0, 0, cos(angle), sin(angle) },
            { 0, 0, -sin(angle), cos(angle) },
        };

        return matrixT;
    }

    /**
     * Matriz de transformação de coordenadas de uma barra reticulada
     *
     * @param angle
     * @return
     */
    public static double[][] matrixT_Frames(double angle) {
        double[][] matrixT = {
            { cos(angle), sin(angle), 0, 0, 0, 0 },
            { -sin(angle), cos(angle), 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, cos(angle), sin(angle), 0 },
            { 0, 0, 0, -sin(angle), cos(angle), 0 },
            { 0, 0, 0, 0, 0, 1 },
        };

        return matrixT;
    }

    /**
     * Matriz de transformação de coordenadas de uma grelha
     *
     * @param angle
     * @return
     */
    public static double[][] matrixT_Grids(double angle) {
        double[][] matrixT = {
            { 1, 0, 0, 0, 0, 0 },
            { 0, cos(angle), sin(angle), 0, 0, 0 },
            { 0, -sin(angle), cos(angle), 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0 },
            { 0, 0, 0, 0, cos(angle), sin(angle) },
            { 0, 0, 0, 0, -sin(angle), cos(angle) },
        };

        return matrixT;
    }
}
