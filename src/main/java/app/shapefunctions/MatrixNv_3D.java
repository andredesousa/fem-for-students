/*
 * Esta classe fornece as funções de forma de elementos finitos tridimensionais
 * O método requer as coordenadas x, y e z, as dimensões do elemento e o número de nós
 * A matriz Nv é avaliada em função das coordenas x, y e z
 */

package app.shapefunctions;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv_3D {

    /**
     * Este método fornece a matriz Nv de um elemento finito tridimensional
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param z é a coordenada local segundo o eixo z
     * @param a é a largura do elemento finito
     * @param b é o comprimento do elemento finito
     * @param c é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_3D(double x, double y, double z, double a, double b, double c, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 4) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = -z / c - y / b - x / a + 1;
                matrixNv[1][0] = x / a;
                matrixNv[2][0] = y / b;
                matrixNv[3][0] = z / c;
            } else if (nodes == 8) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = 1 / (a * b * c) * (0.5 - x) * (0.5 - y) * (0.5 - z);
                matrixNv[1][0] = 1 / (a * b * c) * (0.5 + x) * (0.5 - y) * (0.5 - z);
                matrixNv[2][0] = 1 / (a * b * c) * (0.5 + x) * (0.5 + y) * (0.5 - z);
                matrixNv[3][0] = 1 / (a * b * c) * (0.5 - x) * (0.5 + y) * (0.5 - z);
                matrixNv[4][0] = 1 / (a * b * c) * (0.5 - x) * (0.5 - y) * (0.5 + z);
                matrixNv[5][0] = 1 / (a * b * c) * (0.5 + x) * (0.5 - y) * (0.5 + z);
                matrixNv[6][0] = 1 / (a * b * c) * (0.5 + x) * (0.5 + y) * (0.5 + z);
                matrixNv[7][0] = 1 / (a * b * c) * (0.5 - x) * (0.5 + y) * (0.5 + z);
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }
}
