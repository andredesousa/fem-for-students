/*
 * Esta classe fornece as funções de forma de elementos finitos de laje
 * O método requer as coordenadas x e y, as dimensões do elemento e o número de nós
 * A matriz Nv é avaliada em função das coordenas x e y
 */

package app.shapefunctions;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv_Slabs {

    /**
     * Este método fornece a matriz Nv de um elemento finito do tipo laje
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param a é o comprimento do elemento finito retangular
     * @param b é a largura do elemento finito retangular
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_ReissnerMindlin(double x, double y, double a, double b, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 4) {
                matrixNv = new double[nodes][1];

                matrixNv[0][0] = 1 / 4 - x / (2 * a) - y / (2 * b) + x * y / (a * b);
                matrixNv[1][0] = 1 / 4 + x / (2 * a) - y / (2 * b) - x * y / (a * b);
                matrixNv[2][0] = 1 / 4 + x / (2 * a) + y / (2 * b) + x * y / (a * b);
                matrixNv[3][0] = 1 / 4 - x / (2 * a) + y / (2 * b) - x * y / (a * b);
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
     * Este método fornece a matriz Nv de um elemento finito do tipo laje
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param a é o comprimento do elemento finito retangular
     * @param b é a largura do elemento finito retangular
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixNv_Kirchhoff(double x, double y, double a, double b, int nodes) {
        double[][] matrixNv;

        if (nodes > 1) {
            if (nodes == 4) {
                matrixNv = new double[nodes * 4][1];
                double a2 = a * a;
                double b2 = b * b;
                double a3 = a * a * a;
                double b3 = b * b * b;

                matrixNv[0][0] = ((x + a) * ((2 * x - a) * (2 * x - a)) * (y + b) * ((2 * y - b) * (2 * y - b))) / (4 * a3 * b3);
                matrixNv[1][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * (y + b) * ((2 * y - b) * (2 * y - b))) / (16 * a2 * b3);
                matrixNv[2][0] = ((x + a) * ((2 * x - a) * (2 * x - a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (16 * a3 * b2);
                matrixNv[3][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (64 * a2 * b2);
                matrixNv[4][0] = -((x - a) * ((2 * x + a) * (2 * x + a)) * (y + b) * ((2 * y - b) * (2 * y - b))) / (4 * a3 * b3);
                matrixNv[5][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (y + b) * ((2 * y - b) * (2 * y - b))) / (16 * a2 * b3);
                matrixNv[6][0] = -((x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (16 * a3 * b2);
                matrixNv[7][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (64 * a2 * b2);
                matrixNv[8][0] = ((x - a) * ((2 * x + a) * (2 * x + a)) * (y - b) * ((2 * y + b) * (2 * y + b))) / (4 * a3 * b3);
                matrixNv[9][0] = -((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (y - b) * ((2 * y + b) * (2 * y + b))) / (16 * a2 * b3);
                matrixNv[10][0] = -((x - a) * ((2 * x + a) * (2 * x + a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (16 * a3 * b2);
                matrixNv[11][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (64 * a2 * b2);
                matrixNv[12][0] = -((x + a) * ((2 * x - a) * (2 * x - a)) * (y - b) * ((2 * y + b) * (2 * y + b))) / (4 * a3 * b3);
                matrixNv[13][0] = -(((2 * x - a) * (2 * x - a)) * (2 * x + a) * (y - b) * ((2 * y + b) * (2 * y + b))) / (16 * a2 * b3);
                matrixNv[14][0] = ((x + a) * ((2 * x - a) * (2 * x - a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (16 * a3 * b2);
                matrixNv[15][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (64 * a2 * b2);
            } else if (nodes == 8) {
                matrixNv = new double[nodes * 4][1];
                double x2 = x * x;
                double y2 = y * y;
                double x3 = x * x * x;
                double y3 = y * y * y;
                double a2 = a * a;
                double b2 = b * b;
                double a3 = a * a * a;
                double b3 = b * b * b;
                double a4 = a * a * a * a;
                double b4 = b * b * b * b;
                double a5 = a * a * a * a * a;
                double b5 = b * b * b * b * b;

                matrixNv[0][0] = (((2 * x - a) * (2 * x - a)) * ((2 * y - b) * (2 * y - b)) * (12 * a2 * x * y3 + 12 * a3 * y3 + 8 * a2 * b * x * y2 + 8 * a3 * b * y2 + 12 * b2 * x3 * y + 8 * a * b2 * x2 * y - a2 * b2 * x * y - a3 * b2 * y + 12 * b3 * x3 + 8 * a * b3 * x2 - a2 * b3 * x - a3 * b3)) / (4 * a5 * b5);
                matrixNv[1][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * ((2 * y - b) * (2 * y - b)) * (12 * a2 * y3 + 8 * a2 * b * y2 + 4 * b2 * x2 * y - a2 * b2 * y + 4 * b3 * x2 - a2 * b3)) / (16 * a4 * b5);
                matrixNv[2][0] = (((2 * x - a) * (2 * x - a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b) * (4 * a2 * x * y2 + 4 * a3 * y2 + 12 * b2 * x3 + 8 * a * b2 * x2 - a2 * b2 * x - a3 * b2)) / (16 * a5 * b4);
                matrixNv[3][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * ((2 * y - b) * (2 * y - b)) * (2 * y + b) * (4 * a2 * y2 + 4 * b2 * x2 - a2 * b2)) / (64 * a4 * b4);
                matrixNv[4][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (y + b) * ((2 * y - b) * (2 * y - b))) / (2 * a4 * b3);
                matrixNv[5][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (y + b) * ((2 * y - b) * (2 * y - b))) / (2 * a4 * b3);
                matrixNv[6][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (8 * a4 * b2);
                matrixNv[7][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (8 * a4 * b2);
                matrixNv[8][0] = -(((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (12 * a2 * x * y3 - 12 * a3 * y3 + 8 * a2 * b * x * y2 - 8 * a3 * b * y2 + 12 * b2 * x3 * y - 8 * a * b2 * x2 * y - a2 * b2 * x * y + a3 * b2 * y + 12 * b3 * x3 - 8 * a * b3 * x2 - a2 * b3 * x + a3 * b3)) / (4 * a5 * b5);
                matrixNv[9][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (12 * a2 * y3 + 8 * a2 * b * y2 + 4 * b2 * x2 * y - a2 * b2 * y + 4 * b3 * x2 - a2 * b3)) / (16 * a4 * b5);
                matrixNv[10][0] = -(((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b) * (4 * a2 * x * y2 - 4 * a3 * y2 + 12 * b2 * x3 - 8 * a * b2 * x2 - a2 * b2 * x + a3 * b2)) / (16 * a5 * b4);
                matrixNv[11][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * (2 * y + b) * (4 * a2 * y2 + 4 * b2 * x2 - a2 * b2)) / (64 * a4 * b4);
                matrixNv[12][0] = -((x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a3 * b4);
                matrixNv[13][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (8 * a2 * b4);
                matrixNv[14][0] = -((x - a) * ((2 * x + a) * (2 * x + a)) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a3 * b4);
                matrixNv[15][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (8 * a2 * b4);
                matrixNv[16][0] = (((2 * x + a) * (2 * x + a)) * ((2 * y + b) * (2 * y + b)) * (12 * a2 * x * y3 - 12 * a3 * y3 - 8 * a2 * b * x * y2 + 8 * a3 * b * y2 + 12 * b2 * x3 * y - 8 * a * b2 * x2 * y - a2 * b2 * x * y + a3 * b2 * y - 12 * b3 * x3 + 8 * a * b3 * x2 + a2 * b3 * x - a3 * b3)) / (4 * a5 * b5);
                matrixNv[17][0] = -((2 * x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y + b) * (2 * y + b)) * (12 * a2 * y3 - 8 * a2 * b * y2 + 4 * b2 * x2 * y - a2 * b2 * y - 4 * b3 * x2 + a2 * b3)) / (16 * a4 * b5);
                matrixNv[18][0] = -(((2 * x + a) * (2 * x + a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b)) * (4 * a2 * x * y2 - 4 * a3 * y2 + 12 * b2 * x3 - 8 * a * b2 * x2 - a2 * b2 * x + a3 * b2)) / (16 * a5 * b4);
                matrixNv[19][0] = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b)) * (4 * a2 * y2 + 4 * b2 * x2 - a2 * b2)) / (64 * a4 * b4);
                matrixNv[20][0] = -(((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (y - b) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b3);
                matrixNv[21][0] = -(x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (y - b) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b3);
                matrixNv[22][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (8 * a4 * b2);
                matrixNv[23][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (8 * a4 * b2);
                matrixNv[24][0] = -(((2 * x - a) * (2 * x - a)) * ((2 * y + b) * (2 * y + b)) * (12 * a2 * x * y3 + 12 * a3 * y3 - 8 * a2 * b * x * y2 - 8 * a3 * b * y2 + 12 * b2 * x3 * y + 8 * a * b2 * x2 * y - a2 * b2 * x * y - a3 * b2 * y - 12 * b3 * x3 - 8 * a * b3 * x2 + a2 * b3 * x + a3 * b3)) / (4 * a5 * b5);
                matrixNv[25][0] = -(((2 * x - a) * (2 * x - a)) * (2 * x + a) * ((2 * y + b) * (2 * y + b)) * (12 * a2 * y3 - 8 * a2 * b * y2 + 4 * b2 * x2 * y - a2 * b2 * y - 4 * b3 * x2 + a2 * b3)) / (16 * a4 * b5);
                matrixNv[26][0] = (((2 * x - a) * (2 * x - a)) * (2 * y - b) * ((2 * y + b) * (2 * y + b)) * (4 * a2 * x * y2 + 4 * a3 * y2 + 12 * b2 * x3 + 8 * a * b2 * x2 - a2 * b2 * x - a3 * b2)) / (16 * a5 * b4);
                matrixNv[27][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * (2 * y - b) * ((2 * y + b) * (2 * y + b)) * (4 * a2 * y2 + 4 * b2 * x2 - a2 * b2)) / (64 * a4 * b4);
                matrixNv[28][0] = ((x + a) * ((2 * x - a) * (2 * x - a)) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a3 * b4);
                matrixNv[29][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (8 * a2 * b4);
                matrixNv[30][0] = ((x + a) * ((2 * x - a) * (2 * x - a)) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a3 * b4);
                matrixNv[31][0] = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (8 * a2 * b4);
            } else if (nodes == 9) {
                matrixNv = new double[nodes * 4][1];
                double x2 = x * x;
                double y2 = y * y;
                double a4 = a * a * a * a;
                double b4 = b * b * b * b;
                double a5 = a * a * a * a * a;
                double b5 = b * b * b * b * b;

                matrixNv[0][0] = (4 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a5 * b5);
                matrixNv[1][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                matrixNv[2][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a5 * b4);
                matrixNv[3][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (4 * a4 * b4);
                matrixNv[4][0] = (2 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                matrixNv[5][0] = (2 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                matrixNv[6][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (2 * a4 * b4);
                matrixNv[7][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (2 * a4 * b4);
                matrixNv[8][0] = -(4 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a5 * b5);
                matrixNv[9][0] = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                matrixNv[10][0] = -(x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a5 * b4);
                matrixNv[11][0] = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (4 * a4 * b4);
                matrixNv[12][0] = -(2 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                matrixNv[13][0] = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                matrixNv[14][0] = -(2 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                matrixNv[15][0] = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                matrixNv[16][0] = (4 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a5 * b5);
                matrixNv[17][0] = -(x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                matrixNv[18][0] = -(x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                matrixNv[19][0] = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (4 * a4 * b4);
                matrixNv[20][0] = -(2 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                matrixNv[21][0] = -(2 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                matrixNv[22][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                matrixNv[23][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                matrixNv[24][0] = -(4 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a5 * b5);
                matrixNv[25][0] = -(x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                matrixNv[26][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                matrixNv[27][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (4 * a4 * b4);
                matrixNv[28][0] = (2 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                matrixNv[29][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                matrixNv[30][0] = (2 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                matrixNv[31][0] = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                matrixNv[32][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                matrixNv[33][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                matrixNv[34][0] = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                matrixNv[35][0] = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
            } else {
                matrixNv = null;
            }
        } else {
            matrixNv = null;
        }

        return matrixNv;
    }
}
