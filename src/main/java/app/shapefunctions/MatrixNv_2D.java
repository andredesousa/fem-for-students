/*
 * Esta classe fornece as funções de forma de elementos finitos bidimensionais
 * O método requer as coordenadas x e y, as dimensões do elemento e o número de nós
 * A matriz Nv é avaliada em função das coordenas x e y
 */

package app.shapefunctions;

import static app.matrices.Inverse.inverse;
import static app.matrices.Multiply.multiply;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv_2D {

    /**
     * Este método fornece a matriz Nv de um elemento finito bidimensional
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param a é o comprimento do elemento finito retangular
     * @param b é a largura do elemento finito retangular
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_2D(double x, double y, double a, double b, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 3) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = -y / b - x / a + 1;
                matrixNv[1][0] = x / a;
                matrixNv[2][0] = y / b;
            } else if (nodes == 4) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = 0.25 - x / (2 * a) - y / (2 * b) + x * y / (a * b);
                matrixNv[1][0] = 0.25 + x / (2 * a) - y / (2 * b) - x * y / (a * b);
                matrixNv[2][0] = 0.25 + x / (2 * a) + y / (2 * b) + x * y / (a * b);
                matrixNv[3][0] = 0.25 - x / (2 * a) + y / (2 * b) - x * y / (a * b);
            } else if (nodes == 6) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = (2 * y * y) / (b * b) + (4 * x * y) / (a * b) - (3 * y) / b + (2 * x * x) / (a * a) - (3 * x) / a + 1;
                matrixNv[1][0] = -(4 * x * y) / (a * b) - (4 * x * x) / (a * a) + (4 * x) / a;
                matrixNv[2][0] = (2 * x * x) / (a * a) - x / a;
                matrixNv[3][0] = (4 * x * y) / (a * b);
                matrixNv[4][0] = (2 * y * y) / (b * b) - y / b;
                matrixNv[5][0] = -(4 * y * y) / (b * b) - (4 * x * y) / (a * b) + (4 * y) / b;
            } else if (nodes == 8) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = -((2 * x - a) * (2 * y - b) * (2 * a * y + 2 * b * x + a * b)) / (4 * a * a * b * b);
                matrixNv[1][0] = ((2 * x - a) * (2 * x + a) * (2 * y - b)) / (2 * a * a * b);
                matrixNv[2][0] = ((2 * x + a) * (2 * y - b) * (2 * a * y - 2 * b * x + a * b)) / (4 * a * a * b * b);
                matrixNv[3][0] = -((2 * x + a) * (2 * y - b) * (2 * y + b)) / (2 * a * b * b);
                matrixNv[4][0] = ((2 * x + a) * (2 * y + b) * (2 * a * y + 2 * b * x - a * b)) / (4 * a * a * b * b);
                matrixNv[5][0] = -((2 * x - a) * (2 * x + a) * (2 * y + b)) / (2 * a * a * b);
                matrixNv[6][0] = -((2 * x - a) * (2 * y + b) * (2 * a * y - 2 * b * x - a * b)) / (4 * a * a * b * b);
                matrixNv[7][0] = ((2 * x - a) * (2 * y - b) * (2 * y + b)) / (2 * a * b * b);
            } else if (nodes == 9) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = (x * y / (a * a * b * b)) * (2 * x - a) * (2 * y - b);
                matrixNv[1][0] = (-y / (a * a * b * b)) * (4 * x * x - a * a) * (2 * y - b);
                matrixNv[2][0] = (x * y / (a * a * b * b)) * (2 * x + a) * (2 * y - b);
                matrixNv[3][0] = (-x / (a * a * b * b)) * (2 * x + a) * (4 * y * y - b * b);
                matrixNv[4][0] = (x * y / (a * a * b * b)) * (2 * x + a) * (2 * y + b);
                matrixNv[5][0] = (-y / (a * a * b * b)) * (4 * x * x - a * a) * (2 * y + b);
                matrixNv[6][0] = (x * y / (a * a * b * b)) * (2 * x - a) * (2 * y + b);
                matrixNv[7][0] = (-x / (a * a * b * b)) * (2 * x - a) * (4 * y * y - b * b);
                matrixNv[8][0] = (1 / (a * a * b * b)) * (4 * x * x - a * a) * (4 * y * y - b * b);
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }

    /**
     * Este método fornece a matriz Nv de um elemento finito bidimensional
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param coordinates são as coordenadas do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_2D(double x, double y, double[][] coordinates, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 3) {
                //Cálculo da matriz Q avaliada nos nós do elemento finito
                double[][] Q = new double[nodes][nodes];

                Q[0][0] = 1.0;
                Q[0][1] = 1.0;
                Q[0][2] = 1.0;

                Q[1][0] = coordinates[0][0];
                Q[1][1] = coordinates[1][0];
                Q[1][2] = coordinates[2][0];

                Q[2][0] = coordinates[0][1];
                Q[2][1] = coordinates[1][1];
                Q[2][2] = coordinates[2][1];

                //Cálculo do vetor V avaliado no ponto P = (x, y)
                double[][] V = new double[nodes][1];

                V[0][0] = 1.0;
                V[1][0] = x;
                V[2][0] = y;

                //Cálculo da matriz Nv avaliada no ponto p = (x, y)
                matrixNv = multiply(inverse(Q), V);
            } else if (nodes == 4) {
                //Cálculo da matriz Q avaliada nos nós do elemento finito
                double[][] Q = new double[nodes][nodes];

                Q[0][0] = 1.0;
                Q[0][1] = 1.0;
                Q[0][2] = 1.0;
                Q[0][3] = 1.0;

                Q[1][0] = coordinates[0][0];
                Q[1][1] = coordinates[1][0];
                Q[1][2] = coordinates[2][0];
                Q[1][3] = coordinates[3][0];

                Q[2][0] = coordinates[0][1];
                Q[2][1] = coordinates[1][1];
                Q[2][2] = coordinates[2][1];
                Q[2][3] = coordinates[3][1];

                Q[3][0] = coordinates[0][0] * coordinates[0][1];
                Q[3][1] = coordinates[1][0] * coordinates[1][1];
                Q[3][2] = coordinates[2][0] * coordinates[2][1];
                Q[3][3] = coordinates[3][0] * coordinates[3][1];

                //Cálculo do vetor V avaliado no ponto P = (x, y)
                double[][] V = new double[nodes][1];

                V[0][0] = 1.0;
                V[1][0] = x;
                V[2][0] = y;
                V[3][0] = x * y;

                //Cálculo da matriz Nv avaliada no ponto p = (x, y)
                matrixNv = multiply(inverse(Q), V);
            } else if (nodes == 6) {
                //Cálculo da matriz Q avaliada nos nós do elemento finito
                double[][] Q = new double[nodes][nodes];

                Q[0][0] = 1.0;
                Q[0][1] = 1.0;
                Q[0][2] = 1.0;
                Q[0][3] = 1.0;
                Q[0][4] = 1.0;
                Q[0][5] = 1.0;

                Q[1][0] = coordinates[0][0];
                Q[1][1] = coordinates[1][0];
                Q[1][2] = coordinates[2][0];
                Q[1][3] = coordinates[3][0];
                Q[1][4] = coordinates[4][0];
                Q[1][5] = coordinates[5][0];

                Q[2][0] = coordinates[0][1];
                Q[2][1] = coordinates[1][1];
                Q[2][2] = coordinates[2][1];
                Q[2][3] = coordinates[3][1];
                Q[2][4] = coordinates[4][1];
                Q[2][5] = coordinates[5][1];

                Q[3][0] = coordinates[0][0] * coordinates[0][0];
                Q[3][1] = coordinates[1][0] * coordinates[1][0];
                Q[3][2] = coordinates[2][0] * coordinates[2][0];
                Q[3][3] = coordinates[3][0] * coordinates[3][0];
                Q[3][4] = coordinates[4][0] * coordinates[4][0];
                Q[3][5] = coordinates[5][0] * coordinates[5][0];

                Q[4][0] = coordinates[0][0] * coordinates[0][1];
                Q[4][1] = coordinates[1][0] * coordinates[1][1];
                Q[4][2] = coordinates[2][0] * coordinates[2][1];
                Q[4][3] = coordinates[3][0] * coordinates[3][1];
                Q[4][4] = coordinates[4][0] * coordinates[4][1];
                Q[4][5] = coordinates[5][0] * coordinates[5][1];

                Q[5][0] = coordinates[0][1] * coordinates[0][1];
                Q[5][1] = coordinates[1][1] * coordinates[1][1];
                Q[5][2] = coordinates[2][1] * coordinates[2][1];
                Q[5][3] = coordinates[3][1] * coordinates[3][1];
                Q[5][4] = coordinates[4][1] * coordinates[4][1];
                Q[5][5] = coordinates[5][1] * coordinates[5][1];

                //Cálculo do vetor V avaliado no ponto P = (x, y)
                double[][] V = new double[nodes][1];

                V[0][0] = 1.0;
                V[1][0] = x;
                V[2][0] = y;
                V[3][0] = x * x;
                V[4][0] = x * y;
                V[5][0] = y * y;

                //Cálculo da matriz Nv avaliada no ponto p = (x, y)
                matrixNv = multiply(inverse(Q), V);
            } else if (nodes == 8) {
                //Cálculo da matriz Q avaliada nos nós do elemento finito
                double[][] Q = new double[nodes][nodes];

                Q[0][0] = 1.0;
                Q[0][1] = 1.0;
                Q[0][2] = 1.0;
                Q[0][3] = 1.0;
                Q[0][4] = 1.0;
                Q[0][5] = 1.0;
                Q[0][6] = 1.0;
                Q[0][7] = 1.0;

                Q[1][0] = coordinates[0][0];
                Q[1][1] = coordinates[1][0];
                Q[1][2] = coordinates[2][0];
                Q[1][3] = coordinates[3][0];
                Q[1][4] = coordinates[4][0];
                Q[1][5] = coordinates[5][0];
                Q[1][6] = coordinates[6][0];
                Q[1][7] = coordinates[7][0];

                Q[2][0] = coordinates[0][1];
                Q[2][1] = coordinates[1][1];
                Q[2][2] = coordinates[2][1];
                Q[2][3] = coordinates[3][1];
                Q[2][4] = coordinates[4][1];
                Q[2][5] = coordinates[5][1];
                Q[2][6] = coordinates[6][1];
                Q[2][7] = coordinates[7][1];

                Q[3][0] = coordinates[0][0] * coordinates[0][0];
                Q[3][1] = coordinates[1][0] * coordinates[1][0];
                Q[3][2] = coordinates[2][0] * coordinates[2][0];
                Q[3][3] = coordinates[3][0] * coordinates[3][0];
                Q[3][4] = coordinates[4][0] * coordinates[4][0];
                Q[3][5] = coordinates[5][0] * coordinates[5][0];
                Q[3][6] = coordinates[6][0] * coordinates[6][0];
                Q[3][7] = coordinates[7][0] * coordinates[7][0];

                Q[4][0] = coordinates[0][0] * coordinates[0][1];
                Q[4][1] = coordinates[1][0] * coordinates[1][1];
                Q[4][2] = coordinates[2][0] * coordinates[2][1];
                Q[4][3] = coordinates[3][0] * coordinates[3][1];
                Q[4][4] = coordinates[4][0] * coordinates[4][1];
                Q[4][5] = coordinates[5][0] * coordinates[5][1];
                Q[4][6] = coordinates[6][0] * coordinates[6][1];
                Q[4][7] = coordinates[7][0] * coordinates[7][1];

                Q[5][0] = coordinates[0][1] * coordinates[0][1];
                Q[5][1] = coordinates[1][1] * coordinates[1][1];
                Q[5][2] = coordinates[2][1] * coordinates[2][1];
                Q[5][3] = coordinates[3][1] * coordinates[3][1];
                Q[5][4] = coordinates[4][1] * coordinates[4][1];
                Q[5][5] = coordinates[5][1] * coordinates[5][1];
                Q[5][6] = coordinates[6][1] * coordinates[6][1];
                Q[5][7] = coordinates[7][1] * coordinates[7][1];

                Q[6][0] = coordinates[0][0] * coordinates[0][0] * coordinates[0][1];
                Q[6][1] = coordinates[1][0] * coordinates[1][0] * coordinates[1][1];
                Q[6][2] = coordinates[2][0] * coordinates[2][0] * coordinates[2][1];
                Q[6][3] = coordinates[3][0] * coordinates[3][0] * coordinates[3][1];
                Q[6][4] = coordinates[4][0] * coordinates[4][0] * coordinates[4][1];
                Q[6][5] = coordinates[5][0] * coordinates[5][0] * coordinates[5][1];
                Q[6][6] = coordinates[6][0] * coordinates[6][0] * coordinates[6][1];
                Q[6][7] = coordinates[7][0] * coordinates[7][0] * coordinates[7][1];

                Q[7][0] = coordinates[0][0] * coordinates[0][1] * coordinates[0][1];
                Q[7][1] = coordinates[1][0] * coordinates[1][1] * coordinates[1][1];
                Q[7][2] = coordinates[2][0] * coordinates[2][1] * coordinates[2][1];
                Q[7][3] = coordinates[3][0] * coordinates[3][1] * coordinates[3][1];
                Q[7][4] = coordinates[4][0] * coordinates[4][1] * coordinates[4][1];
                Q[7][5] = coordinates[5][0] * coordinates[5][1] * coordinates[5][1];
                Q[7][6] = coordinates[6][0] * coordinates[6][1] * coordinates[6][1];
                Q[7][7] = coordinates[7][0] * coordinates[7][1] * coordinates[7][1];

                //Cálculo do vetor V avaliado no ponto P = (x, y)
                double[][] V = new double[nodes][1];

                V[0][0] = 1.0;
                V[1][0] = x;
                V[2][0] = y;
                V[3][0] = x * x;
                V[4][0] = x * y;
                V[5][0] = y * y;
                V[6][0] = x * x * y;
                V[7][0] = x * y * y;

                //Cálculo da matriz Nv avaliada no ponto p = (x, y)
                matrixNv = multiply(inverse(Q), V);
            } else if (nodes == 9) {
                //Cálculo da matriz Q avaliada nos nós do elemento finito
                double[][] Q = new double[nodes][nodes];

                Q[0][0] = 1.0;
                Q[0][1] = 1.0;
                Q[0][2] = 1.0;
                Q[0][3] = 1.0;
                Q[0][4] = 1.0;
                Q[0][5] = 1.0;
                Q[0][6] = 1.0;
                Q[0][7] = 1.0;
                Q[0][8] = 1.0;

                Q[1][0] = coordinates[0][0];
                Q[1][1] = coordinates[1][0];
                Q[1][2] = coordinates[2][0];
                Q[1][3] = coordinates[3][0];
                Q[1][4] = coordinates[4][0];
                Q[1][5] = coordinates[5][0];
                Q[1][6] = coordinates[6][0];
                Q[1][7] = coordinates[7][0];
                Q[1][8] = coordinates[8][0];

                Q[2][0] = coordinates[0][1];
                Q[2][1] = coordinates[1][1];
                Q[2][2] = coordinates[2][1];
                Q[2][3] = coordinates[3][1];
                Q[2][4] = coordinates[4][1];
                Q[2][5] = coordinates[5][1];
                Q[2][6] = coordinates[6][1];
                Q[2][7] = coordinates[7][1];
                Q[2][8] = coordinates[8][1];

                Q[3][0] = coordinates[0][0] * coordinates[0][0];
                Q[3][1] = coordinates[1][0] * coordinates[1][0];
                Q[3][2] = coordinates[2][0] * coordinates[2][0];
                Q[3][3] = coordinates[3][0] * coordinates[3][0];
                Q[3][4] = coordinates[4][0] * coordinates[4][0];
                Q[3][5] = coordinates[5][0] * coordinates[5][0];
                Q[3][6] = coordinates[6][0] * coordinates[6][0];
                Q[3][7] = coordinates[7][0] * coordinates[7][0];
                Q[3][8] = coordinates[8][0] * coordinates[8][0];

                Q[4][0] = coordinates[0][0] * coordinates[0][1];
                Q[4][1] = coordinates[1][0] * coordinates[1][1];
                Q[4][2] = coordinates[2][0] * coordinates[2][1];
                Q[4][3] = coordinates[3][0] * coordinates[3][1];
                Q[4][4] = coordinates[4][0] * coordinates[4][1];
                Q[4][5] = coordinates[5][0] * coordinates[5][1];
                Q[4][6] = coordinates[6][0] * coordinates[6][1];
                Q[4][7] = coordinates[7][0] * coordinates[7][1];
                Q[4][8] = coordinates[8][0] * coordinates[8][1];

                Q[5][0] = coordinates[0][1] * coordinates[0][1];
                Q[5][1] = coordinates[1][1] * coordinates[1][1];
                Q[5][2] = coordinates[2][1] * coordinates[2][1];
                Q[5][3] = coordinates[3][1] * coordinates[3][1];
                Q[5][4] = coordinates[4][1] * coordinates[4][1];
                Q[5][5] = coordinates[5][1] * coordinates[5][1];
                Q[5][6] = coordinates[6][1] * coordinates[6][1];
                Q[5][7] = coordinates[7][1] * coordinates[7][1];
                Q[5][8] = coordinates[8][1] * coordinates[8][1];

                Q[6][0] = coordinates[0][0] * coordinates[0][0] * coordinates[0][1];
                Q[6][1] = coordinates[1][0] * coordinates[1][0] * coordinates[1][1];
                Q[6][2] = coordinates[2][0] * coordinates[2][0] * coordinates[2][1];
                Q[6][3] = coordinates[3][0] * coordinates[3][0] * coordinates[3][1];
                Q[6][4] = coordinates[4][0] * coordinates[4][0] * coordinates[4][1];
                Q[6][5] = coordinates[5][0] * coordinates[5][0] * coordinates[5][1];
                Q[6][6] = coordinates[6][0] * coordinates[6][0] * coordinates[6][1];
                Q[6][7] = coordinates[7][0] * coordinates[7][0] * coordinates[7][1];
                Q[6][8] = coordinates[8][0] * coordinates[8][0] * coordinates[8][1];

                Q[7][0] = coordinates[0][0] * coordinates[0][1] * coordinates[0][1];
                Q[7][1] = coordinates[1][0] * coordinates[1][1] * coordinates[1][1];
                Q[7][2] = coordinates[2][0] * coordinates[2][1] * coordinates[2][1];
                Q[7][3] = coordinates[3][0] * coordinates[3][1] * coordinates[3][1];
                Q[7][4] = coordinates[4][0] * coordinates[4][1] * coordinates[4][1];
                Q[7][5] = coordinates[5][0] * coordinates[5][1] * coordinates[5][1];
                Q[7][6] = coordinates[6][0] * coordinates[6][1] * coordinates[6][1];
                Q[7][7] = coordinates[7][0] * coordinates[7][1] * coordinates[7][1];
                Q[7][8] = coordinates[8][0] * coordinates[8][1] * coordinates[8][1];

                Q[8][0] = coordinates[0][0] * coordinates[0][0] * coordinates[0][1] * coordinates[0][1];
                Q[8][1] = coordinates[1][0] * coordinates[1][0] * coordinates[1][1] * coordinates[1][1];
                Q[8][2] = coordinates[2][0] * coordinates[2][0] * coordinates[2][1] * coordinates[2][1];
                Q[8][3] = coordinates[3][0] * coordinates[3][0] * coordinates[3][1] * coordinates[3][1];
                Q[8][4] = coordinates[4][0] * coordinates[4][0] * coordinates[4][1] * coordinates[4][1];
                Q[8][5] = coordinates[5][0] * coordinates[5][0] * coordinates[5][1] * coordinates[5][1];
                Q[8][6] = coordinates[6][0] * coordinates[6][0] * coordinates[6][1] * coordinates[6][1];
                Q[8][7] = coordinates[7][0] * coordinates[7][0] * coordinates[7][1] * coordinates[7][1];
                Q[8][8] = coordinates[8][0] * coordinates[8][0] * coordinates[8][1] * coordinates[8][1];

                //Cálculo do vetor V avaliado no ponto P = (x, y)
                double[][] V = new double[nodes][1];

                V[0][0] = 1.0;
                V[1][0] = x;
                V[2][0] = y;
                V[3][0] = x * x;
                V[4][0] = x * y;
                V[5][0] = y * y;
                V[6][0] = x * x * y;
                V[7][0] = x * y * y;
                V[8][0] = x * x * y * y;

                //Cálculo da matriz Nv avaliada no ponto p = (x, y)
                matrixNv = multiply(inverse(Q), V);
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }
}
