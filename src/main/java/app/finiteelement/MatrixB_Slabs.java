/*
 * Esta classe fornece a matriz B de um elemento finito do tipo laje
 * O método requer as coordenadas x e y, as dimensões do elemento e o número de nós
 * A matriz B é avaliada em função das coordenas x e y
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_Slabs {

    /**
     * Este método fornece a matriz B de um elemento finito do tipo laje
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param a é o comprimento do elemento finito retangular
     * @param b é a largura do elemento finito retangular
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_ReissnerMindlin(double x, double y, double a, double b, int nodes) {
        double[][] matrixB;

        if (a >= 0 && b >= 0 && nodes > 1) {
            if (nodes == 4) {
                double N1, N2, N3, N4;
                double N1x, N2x, N3x, N4x;
                double N1y, N2y, N3y, N4y;
                
                //Funções de forma do elemento finito
                N1 = 0.25 - x / (2 * a) - y / (2 * b) + x * y / (a * b);
                N2 = 0.25 / 4 + x / (2 * a) - y / (2 * b) - x * y / (a * b);
                N3 = 0.25 / 4 + x / (2 * a) + y / (2 * b) + x * y / (a * b);
                N4 = 0.25 / 4 - x / (2 * a) + y / (2 * b) - x * y / (a * b);
                
                //Derivadas das funções de forma
                N1x = -1 / (2 * a) + y / (a * b);
                N1y = -1 / (2 * b) + x / (a * b);
                N2x = 1 / (2 * a) - y / (a * b);
                N2y = -1 / (2 * b) - x / (a * b);
                N3x = 1 / (2 * a) + y / (a * b);
                N3y = 1 / (2 * b) + x / (a * b);
                N4x = -1 / (2 * a) - y / (a * b);
                N4y = 1 / (2 * b) - x / (a * b);

                double[][] matrix = {
                    {0, N1x, 0, 0, N2x, 0, 0, N3x, 0, 0, N4x, 0},
                    {0, 0, N1y, 0, 0, N2y, 0, 0, N3y, 0, 0, N4y},
                    {0, N1y, N1x, 0, N2y, N2x, 0, N3y, N3x, 0, N4y, N4x},
                    {N1x, -N1, 0, N2x, -N2, 0, N3x, -N3, 0, N4x, -N4, 0},
                    {N1y, 0, -N1, N2y, 0, -N2, N3y, 0, -N3, N4y, 0, -N4}};

                matrixB = matrix;
            } else if (nodes == 8) {
                double N1, N2, N3, N4, N5, N6, N7, N8;
                double N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x;
                double N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y;
                
                //Funções de forma do elemento finito
                N1 = -((2 * x - a) * (2 * y - b) * (2 * a * y + 2 * b * x + a * b)) / (4 * a * a * b * b);
                N2 = ((2 * x - a) * (2 * x + a) * (2 * y - b)) / (2 * a * a * b);
                N3 = ((2 * x + a) * (2 * y - b) * (2 * a * y - 2 * b * x + a * b)) / (4 * a * a * b * b);
                N4 = -((2 * x + a) * (2 * y - b) * (2 * y + b)) / (2 * a * b * b);
                N5 = ((2 * x + a) * (2 * y + b) * (2 * a * y + 2 * b * x - a * b)) / (4 * a * a * b * b);
                N6 = -((2 * x - a) * (2 * x + a) * (2 * y + b)) / (2 * a * a * b);
                N7 = -((2 * x - a) * (2 * y + b) * (2 * a * y - 2 * b * x - a * b)) / (4 * a * a * b * b);
                N8 = ((2 * x - a) * (2 * y - b) * (2 * y + b)) / (2 * a * b * b);

                //Derivadas das funções de forma
                N1x = -((2 * y - b) * (a * y + 2 * b * x)) / (a * a * b * b);
                N1y = -((2 * x - a) * (2 * a * y + b * x)) / (a * a * b * b);
                N2x = (4 * x * (2 * y - b)) / (a * a * b);
                N2y = ((2 * x - a) * (2 * x + a)) / (a * a * b);
                N3x = ((2 * y - b) * (a * y - 2 * b * x)) / (a * a * b * b);
                N3y = ((2 * x + a) * (2 * a * y - b * x)) / (a * a * b * b);
                N4x = -((2 * y - b) * (2 * y + b)) / (a * b * b);
                N4y = -(4 * (2 * x + a) * y) / (a * b * b);
                N5x = ((2 * y + b) * (a * y + 2 * b * x)) / (a * a * b * b);
                N5y = ((2 * x + a) * (2 * a * y + b * x)) / (a * a * b * b);
                N6x = -(4 * x * (2 * y + b)) / (a * a * b);
                N6y = -((2 * x - a) * (2 * x + a)) / (a * a * b);
                N7x = -((2 * y + b) * (a * y - 2 * b * x)) / (a * a * b * b);
                N7y = -((2 * x - a) * (2 * a * y - b * x)) / (a * a * b * b);
                N8x = ((2 * y - b) * (2 * y + b)) / (a * b * b);
                N8y = (4 * (2 * x - a) * y) / (a * b * b);

                double[][] matrix = {
                    {0, N1x, 0, 0, N2x, 0, 0, N3x, 0, 0, N4x, 0, 0, N5x, 0, 0, N6x, 0, 0, N7x, 0, 0, N8x, 0},
                    {0, 0, N1y, 0, 0, N2y, 0, 0, N3y, 0, 0, N4y, 0, 0, N5y, 0, 0, N6y, 0, 0, N7y, 0, 0, N8y},
                    {0, N1y, N1x, 0, N2y, N2x, 0, N3y, N3x, 0, N4y, N4x, 0, N5y, N5x, 0, N6y, N6x, 0, N7y, N7x, 0, N8y, N8x},
                    {N1x, -N1, 0, N2x, -N2, 0, N3x, -N3, 0, N4x, -N4, 0, N5x, -N5, 0, N6x, -N6, 0, N7x, -N7, 0, N8x, -N8, 0},
                    {N1y, 0, -N1, N2y, 0, -N2, N3y, 0, -N3, N4y, 0, -N4, N5y, 0, -N5, N6y, 0, -N6, N7y, 0, -N7, N8y, 0, -N8}};

                matrixB = matrix;
            } else if (nodes == 9) {
                double N1, N2, N3, N4, N5, N6, N7, N8, N9;
                double N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x;
                double N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y;

                //Funções de forma do elemento finito
                N1 = (x * y / (a * a * b * b)) * (2 * x - a) * (2 * y - b);
                N2 = (-y / (a * a * b * b)) * (4 * x * x - a * a) * (2 * y - b);
                N3 = (x * y / (a * a * b * b)) * (2 * x + a) * (2 * y - b);
                N4 = (-x / (a * a * b * b)) * (2 * x + a) * (4 * y * y - b * b);
                N5 = (x * y / (a * a * b * b)) * (2 * x + a) * (2 * y + b);
                N6 = (-y / (a * a * b * b)) * (4 * x * x - a * a) * (2 * y + b);
                N7 = (x * y / (a * a * b * b)) * (2 * x - a) * (2 * y + b);
                N8 = (-x / (a * a * b * b)) * (2 * x - a) * (4 * y * y - b * b);
                N9 = (1 / (a * a * b * b)) * (4 * x * x - a * a) * (4 * y * y - b * b);

                //Derivadas das funções de forma
                N1x = y / (a * a * b * b) * (4 * x - a) * (2 * y - b);
                N1y = x / (a * a * b * b) * (2 * x - a) * (4 * y - b);
                N2x = -(8 * x * y) / (a * a * b * b) * (2 * y - b);
                N2y = -1 / (a * a * b * b) * (4 * x * x - a * a) * (4 * y - b);
                N3x = y / (a * a * b * b) * (4 * x + a) * (2 * y - b);
                N3y = x / (a * a * b * b) * (2 * x + a) * (4 * y - b);
                N4x = -1 / (a * a * b * b) * (4 * x + a) * (4 * y * y - b * b);
                N4y = -(8 * x * y) / (a * a * b * b) * (2 * x + a);
                N5x = y / (a * a * b * b) * (4 * x + a) * (2 * y + b);
                N5y = x / (a * a * b * b) * (2 * x + a) * (4 * y + b);
                N6x = -(8 * x * y) / (a * a * b * b) * (2 * y + b);
                N6y = -1 / (a * a * b * b) * (4 * x * x - a * a) * (4 * y + b);
                N7x = y / (a * a * b * b) * (4 * x - a) * (2 * y + b);
                N7y = x / (a * a * b * b) * (2 * x - a) * (4 * y + b);
                N8x = -1 / (a * a * b * b) * (4 * x - a) * (4 * y * y - b * b);
                N8y = -(8 * x * y) / (a * a * b * b) * (2 * x - a);
                N9x = (8 * x) / (a * a * b * b) * (4 * y * y - b * b);
                N9y = (8 * y) / (a * a * b * b) * (4 * x * x - a * a);

                double[][] matrix = {
                    {0, N1x, 0, 0, N2x, 0, 0, N3x, 0, 0, N4x, 0, 0, N5x, 0, 0, N6x, 0, 0, N7x, 0, 0, N8x, 0, 0, N9x, 0},
                    {0, 0, N1y, 0, 0, N2y, 0, 0, N3y, 0, 0, N4y, 0, 0, N5y, 0, 0, N6y, 0, 0, N7y, 0, 0, N8y, 0, 0, N9y},
                    {0, N1y, N1x, 0, N2y, N2x, 0, N3y, N3x, 0, N4y, N4x, 0, N5y, N5x, 0, N6y, N6x, 0, N7y, N7x, 0, N8y, N8x, 0, N9y, N9x},
                    {N1x, -N1, 0, N2x, -N2, 0, N3x, -N3, 0, N4x, -N4, 0, N5x, -N5, 0, N6x, -N6, 0, N7x, -N7, 0, N8x, -N8, 0, N9x, -N9, 0},
                    {N1y, 0, -N1, N2y, 0, -N2, N3y, 0, -N3, N4y, 0, -N4, N5y, 0, -N5, N6y, 0, -N6, N7y, 0, -N7, N8y, 0, -N8, N9y, 0, -N9}};

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
     * Este método fornece a matriz B de um elemento finito do tipo laje
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param a é o comprimento do elemento finito retangular
     * @param b é a largura do elemento finito retangular
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_Kirchhoff(double x, double y, double a, double b, int nodes) {
        double[][] matrixB;

        if (a >= 0 && b >= 0 && nodes > 1) {
            double a2, a3, a4, a5, b2, b3, b4, b5, x2, x3, y2, y3;

            a2 = a * a;
            a3 = a * a * a;
            a4 = a * a * a * a;
            a5 = a * a * a * a * a;
            b2 = b * b;
            b3 = b * b * b;
            b4 = b * b * b * b;
            b5 = b * b * b * b * b;
            x2 = x * x;
            x3 = x * x * x;
            y2 = y * y;
            y3 = y * y * y;

            if (nodes == 4) {
                //Segundas derivadas das funções de forma d2N/dx2
                double N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x;
                double N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x;

                N1x = (24 * x * y3 - 18 * b2 * x * y + 6 * b3 * x) / (a3 * b3);
                N2x = ((24 * x - 4 * a) * y3 + (3 * a * b2 - 18 * b2 * x) * y + 6 * b3 * x - a * b3) / (2 * a2 * b3);
                N3x = (24 * x * y3 - 12 * b * x * y2 - 6 * b2 * x * y + 3 * b3 * x) / (2 * a3 * b2);
                N4x = ((48 * x - 8 * a) * y3 + (4 * a * b - 24 * b * x) * y2 + (2 * a * b2 - 12 * b2 * x) * y + 6 * b3 * x - a * b3) / (8 * a2 * b2);
                N5x = -(24 * x * y3 - 18 * b2 * x * y + 6 * b3 * x) / (a3 * b3);
                N6x = ((24 * x + 4 * a) * y3 + (-18 * b2 * x - 3 * a * b2) * y + 6 * b3 * x + a * b3) / (2 * a2 * b3);
                N7x = -(24 * x * y3 - 12 * b * x * y2 - 6 * b2 * x * y + 3 * b3 * x) / (2 * a3 * b2);
                N8x = ((48 * x + 8 * a) * y3 + (-24 * b * x - 4 * a * b) * y2 + (-12 * b2 * x - 2 * a * b2) * y + 6 * b3 * x + a * b3) / (8 * a2 * b2);
                N9x = (24 * x * y3 - 18 * b2 * x * y - 6 * b3 * x) / (a3 * b3);
                N10x = -((24 * x + 4 * a) * y3 + (-18 * b2 * x - 3 * a * b2) * y - 6 * b3 * x - a * b3) / (2 * a2 * b3);
                N11x = -(24 * x * y3 + 12 * b * x * y2 - 6 * b2 * x * y - 3 * b3 * x) / (2 * a3 * b2);
                N12x = ((48 * x + 8 * a) * y3 + (24 * b * x + 4 * a * b) * y2 + (-12 * b2 * x - 2 * a * b2) * y - 6 * b3 * x - a * b3) / (8 * a2 * b2);
                N13x = -(24 * x * y3 - 18 * b2 * x * y - 6 * b3 * x) / (a3 * b3);
                N14x = -((24 * x - 4 * a) * y3 + (3 * a * b2 - 18 * b2 * x) * y - 6 * b3 * x + a * b3) / (2 * a2 * b3);
                N15x = (24 * x * y3 + 12 * b * x * y2 - 6 * b2 * x * y - 3 * b3 * x) / (2 * a3 * b2);
                N16x = ((48 * x - 8 * a) * y3 + (24 * b * x - 4 * a * b) * y2 + (2 * a * b2 - 12 * b2 * x) * y - 6 * b3 * x + a * b3) / (8 * a2 * b2);

                //Segundas derivadas das funções de forma d2N/dy2
                double N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y;
                double N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y;

                N1y = ((24 * x3 - 18 * a2 * x + 6 * a3) * y) / (a3 * b3);
                N2y = ((24 * x3 - 12 * a * x2 - 6 * a2 * x + 3 * a3) * y) / (2 * a2 * b3);
                N3y = ((24 * x3 - 18 * a2 * x + 6 * a3) * y - 4 * b * x3 + 3 * a2 * b * x - a3 * b) / (2 * a3 * b2);
                N4y = ((48 * x3 - 24 * a * x2 - 12 * a2 * x + 6 * a3) * y - 8 * b * x3 + 4 * a * b * x2 + 2 * a2 * b * x - a3 * b) / (8 * a2 * b2);
                N5y = -((24 * x3 - 18 * a2 * x - 6 * a3) * y) / (a3 * b3);
                N6y = ((24 * x3 + 12 * a * x2 - 6 * a2 * x - 3 * a3) * y) / (2 * a2 * b3);
                N7y = -((24 * x3 - 18 * a2 * x - 6 * a3) * y - 4 * b * x3 + 3 * a2 * b * x + a3 * b) / (2 * a3 * b2);
                N8y = ((48 * x3 + 24 * a * x2 - 12 * a2 * x - 6 * a3) * y - 8 * b * x3 - 4 * a * b * x2 + 2 * a2 * b * x + a3 * b) / (8 * a2 * b2);
                N9y = ((24 * x3 - 18 * a2 * x - 6 * a3) * y) / (a3 * b3);
                N10y = -((24 * x3 + 12 * a * x2 - 6 * a2 * x - 3 * a3) * y) / (2 * a2 * b3);
                N11y = -((24 * x3 - 18 * a2 * x - 6 * a3) * y + 4 * b * x3 - 3 * a2 * b * x - a3 * b) / (2 * a3 * b2);
                N12y = ((48 * x3 + 24 * a * x2 - 12 * a2 * x - 6 * a3) * y + 8 * b * x3 + 4 * a * b * x2 - 2 * a2 * b * x - a3 * b) / (8 * a2 * b2);
                N13y = -((24 * x3 - 18 * a2 * x + 6 * a3) * y) / (a3 * b3);
                N14y = -((24 * x3 - 12 * a * x2 - 6 * a2 * x + 3 * a3) * y) / (2 * a2 * b3);
                N15y = ((24 * x3 - 18 * a2 * x + 6 * a3) * y + 4 * b * x3 - 3 * a2 * b * x + a3 * b) / (2 * a3 * b2);
                N16y = ((48 * x3 - 24 * a * x2 - 12 * a2 * x + 6 * a3) * y + 8 * b * x3 - 4 * a * b * x2 - 2 * a2 * b * x + a3 * b) / (8 * a2 * b2);

                //Segundas derivadas das funções de forma d2N/dxdy
                double N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy;
                double N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy;

                N1xy = 2 * (((144 * x2 - 36 * a2) * y2 - 36 * b2 * x2 + 9 * a2 * b2) / (4 * a3 * b3));
                N2xy = 2 * (((144 * x2 - 48 * a * x - 12 * a2) * y2 - 36 * b2 * x2 + 12 * a * b2 * x + 3 * a2 * b2) / (8 * a2 * b3));
                N3xy = 2 * (((144 * x2 - 36 * a2) * y2 + (12 * a2 * b - 48 * b * x2) * y - 12 * b2 * x2 + 3 * a2 * b2) / (8 * a3 * b2));
                N4xy = 2 * (((144 * x2 - 48 * a * x - 12 * a2) * y2 + (-48 * b * x2 + 16 * a * b * x + 4 * a2 * b) * y - 12 * b2 * x2 + 4 * a * b2 * x + a2 * b2) / (16 * a2 * b2));
                N5xy = 2 * (-((144 * x2 - 36 * a2) * y2 - 36 * b2 * x2 + 9 * a2 * b2) / (4 * a3 * b3));
                N6xy = 2 * (((144 * x2 + 48 * a * x - 12 * a2) * y2 - 36 * b2 * x2 - 12 * a * b2 * x + 3 * a2 * b2) / (8 * a2 * b3));
                N7xy = 2 * (-((144 * x2 - 36 * a2) * y2 + (12 * a2 * b - 48 * b * x2) * y - 12 * b2 * x2 + 3 * a2 * b2) / (8 * a3 * b2));
                N8xy = 2 * (((144 * x2 + 48 * a * x - 12 * a2) * y2 + (-48 * b * x2 - 16 * a * b * x + 4 * a2 * b) * y - 12 * b2 * x2 - 4 * a * b2 * x + a2 * b2) / (16 * a2 * b2));
                N9xy = 2 * (((144 * x2 - 36 * a2) * y2 - 36 * b2 * x2 + 9 * a2 * b2) / (4 * a3 * b3));
                N10xy = 2 * (-((144 * x2 + 48 * a * x - 12 * a2) * y2 - 36 * b2 * x2 - 12 * a * b2 * x + 3 * a2 * b2) / (8 * a2 * b3));
                N11xy = 2 * (-((144 * x2 - 36 * a2) * y2 + (48 * b * x2 - 12 * a2 * b) * y - 12 * b2 * x2 + 3 * a2 * b2) / (8 * a3 * b2));
                N12xy = 2 * (((144 * x2 + 48 * a * x - 12 * a2) * y2 + (48 * b * x2 + 16 * a * b * x - 4 * a2 * b) * y - 12 * b2 * x2 - 4 * a * b2 * x + a2 * b2) / (16 * a2 * b2));
                N13xy = 2 * (-((144 * x2 - 36 * a2) * y2 - 36 * b2 * x2 + 9 * a2 * b2) / (4 * a3 * b3));
                N14xy = 2 * (-((144 * x2 - 48 * a * x - 12 * a2) * y2 - 36 * b2 * x2 + 12 * a * b2 * x + 3 * a2 * b2) / (8 * a2 * b3));
                N15xy = 2 * (((144 * x2 - 36 * a2) * y2 + (48 * b * x2 - 12 * a2 * b) * y - 12 * b2 * x2 + 3 * a2 * b2) / (8 * a3 * b2));
                N16xy = 2 * (((144 * x2 - 48 * a * x - 12 * a2) * y2 + (48 * b * x2 - 16 * a * b * x - 4 * a2 * b) * y - 12 * b2 * x2 + 4 * a * b2 * x + a2 * b2) / (16 * a2 * b2));

                //Construção da matriz B a partir de uma matriz auxiliar
                double[][] matrix = {
                    {N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x},
                    {N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y},
                    {N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy, N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy}};

                matrixB = matrix;
            } else if (nodes == 8) {
                //Segundas derivadas das funções de forma d2N/dx2
                double N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x;
                double N17x, N18x, N19x, N20x, N21x, N22x, N23x, N24x, N25x, N26x, N27x, N28x, N29x, N30x, N31x, N32x;

                N1x = (4 * ((2 * y - b) * (2 * y - b)) * (18 * a2 * x * y3 + 12 * a2 * b * x * y2 + 60 * b2 * x3 * y - 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y + a3 * b2 * y + 60 * b3 * x3 - 12 * a * b3 * x2 - 9 * a2 * b3 * x + a3 * b3)) / (a5 * b5);
                N2x = (((2 * y - b) * (2 * y - b)) * (36 * a2 * x * y3 - 6 * a3 * y3 + 24 * a2 * b * x * y2 - 4 * a3 * b * y2 + 40 * b2 * x3 * y - 12 * a * b2 * x2 * y - 6 * a2 * b2 * x * y + a3 * b2 * y + 40 * b3 * x3 - 12 * a * b3 * x2 - 6 * a2 * b3 * x + a3 * b3)) / (a4 * b5);
                N3x = (((2 * y - b) * (2 * y - b)) * (2 * y + b) * (6 * a2 * x * y2 + 60 * b2 * x3 - 12 * a * b2 * x2 - 9 * a2 * b2 * x + a3 * b2)) / (a5 * b4);
                N4x = (((2 * y - b) * (2 * y - b)) * (2 * y + b) * (12 * a2 * x * y2 - 2 * a3 * y2 + 40 * b2 * x3 - 12 * a * b2 * x2 - 6 * a2 * b2 * x + a3 * b2)) / (4 * a4 * b4);
                N5x = (8 * (12 * x2 - a2) * (y + b) * ((2 * y - b) * (2 * y - b))) / (a4 * b3);
                N6x = (8 * x * (20 * x2 - 3 * a2) * (y + b) * ((2 * y - b) * (2 * y - b))) / (a4 * b3);
                N7x = (2 * (12 * x2 - a2) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a4 * b2);
                N8x = (2 * x * (20 * x2 - 3 * a2) * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a4 * b2);
                N9x = -(4 * ((2 * y - b) * (2 * y - b)) * (18 * a2 * x * y3 + 12 * a2 * b * x * y2 + 60 * b2 * x3 * y + 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y - a3 * b2 * y + 60 * b3 * x3 + 12 * a * b3 * x2 - 9 * a2 * b3 * x - a3 * b3)) / (a5 * b5);
                N10x = (((2 * y - b) * (2 * y - b)) * (36 * a2 * x * y3 + 6 * a3 * y3 + 24 * a2 * b * x * y2 + 4 * a3 * b * y2 + 40 * b2 * x3 * y + 12 * a * b2 * x2 * y - 6 * a2 * b2 * x * y - a3 * b2 * y + 40 * b3 * x3 + 12 * a * b3 * x2 - 6 * a2 * b3 * x - a3 * b3)) / (a4 * b5);
                N11x = -(((2 * y - b) * (2 * y - b)) * (2 * y + b) * (6 * a2 * x * y2 + 60 * b2 * x3 + 12 * a * b2 * x2 - 9 * a2 * b2 * x - a3 * b2)) / (a5 * b4);
                N12x = (((2 * y - b) * (2 * y - b)) * (2 * y + b) * (12 * a2 * x * y2 + 2 * a3 * y2 + 40 * b2 * x3 + 12 * a * b2 * x2 - 6 * a2 * b2 * x - a3 * b2)) / (4 * a4 * b4);
                N13x = -(12 * x * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a3 * b4);
                N14x = ((6 * x + a) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a2 * b4);
                N15x = -(12 * x * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a3 * b4);
                N16x = ((6 * x + a) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a2 * b4);
                N17x = (4 * ((2 * y + b) * (2 * y + b)) * (18 * a2 * x * y3 - 12 * a2 * b * x * y2 + 60 * b2 * x3 * y + 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y - a3 * b2 * y - 60 * b3 * x3 - 12 * a * b3 * x2 + 9 * a2 * b3 * x + a3 * b3)) / (a5 * b5);
                N18x = -(((2 * y + b) * (2 * y + b)) * (36 * a2 * x * y3 + 6 * a3 * y3 - 24 * a2 * b * x * y2 - 4 * a3 * b * y2 + 40 * b2 * x3 * y + 12 * a * b2 * x2 * y - 6 * a2 * b2 * x * y - a3 * b2 * y - 40 * b3 * x3 - 12 * a * b3 * x2 + 6 * a2 * b3 * x + a3 * b3)) / (a4 * b5);
                N19x = -((2 * y - b) * ((2 * y + b) * (2 * y + b)) * (6 * a2 * x * y2 + 60 * b2 * x3 + 12 * a * b2 * x2 - 9 * a2 * b2 * x - a3 * b2)) / (a5 * b4);
                N20x = ((2 * y - b) * ((2 * y + b) * (2 * y + b)) * (12 * a2 * x * y2 + 2 * a3 * y2 + 40 * b2 * x3 + 12 * a * b2 * x2 - 6 * a2 * b2 * x - a3 * b2)) / (4 * a4 * b4);
                N21x = -(8 * (12 * x2 - a2) * (y - b) * ((2 * y + b) * (2 * y + b))) / (a4 * b3);
                N22x = -(8 * x * (20 * x2 - 3 * a2) * (y - b) * ((2 * y + b) * (2 * y + b))) / (a4 * b3);
                N23x = (2 * (12 * x2 - a2) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a4 * b2);
                N24x = (2 * x * (20 * x2 - 3 * a2) * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a4 * b2);
                N25x = -(4 * ((2 * y + b) * (2 * y + b)) * (18 * a2 * x * y3 - 12 * a2 * b * x * y2 + 60 * b2 * x3 * y - 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y + a3 * b2 * y - 60 * b3 * x3 + 12 * a * b3 * x2 + 9 * a2 * b3 * x - a3 * b3)) / (a5 * b5);
                N26x = -(((2 * y + b) * (2 * y + b)) * (36 * a2 * x * y3 - 6 * a3 * y3 - 24 * a2 * b * x * y2 + 4 * a3 * b * y2 + 40 * b2 * x3 * y - 12 * a * b2 * x2 * y - 6 * a2 * b2 * x * y + a3 * b2 * y - 40 * b3 * x3 + 12 * a * b3 * x2 + 6 * a2 * b3 * x - a3 * b3)) / (a4 * b5);
                N27x = ((2 * y - b) * ((2 * y + b) * (2 * y + b)) * (6 * a2 * x * y2 + 60 * b2 * x3 - 12 * a * b2 * x2 - 9 * a2 * b2 * x + a3 * b2)) / (a5 * b4);
                N28x = ((2 * y - b) * ((2 * y + b) * (2 * y + b)) * (12 * a2 * x * y2 - 2 * a3 * y2 + 40 * b2 * x3 - 12 * a * b2 * x2 - 6 * a2 * b2 * x + a3 * b2)) / (4 * a4 * b4);
                N29x = (12 * x * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a3 * b4);
                N30x = ((6 * x - a) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a2 * b4);
                N31x = (12 * x * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a3 * b4);
                N32x = ((6 * x - a) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a2 * b4);

                //Segundas derivadas das funções de forma d2N/dy2
                double N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y;
                double N17y, N18y, N19y, N20y, N21y, N22y, N23y, N24y, N25y, N26y, N27y, N28y, N29y, N30y, N31y, N32y;

                N1y = (4 * ((2 * x - a) * (2 * x - a)) * (60 * a2 * x * y3 + 60 * a3 * y3 - 12 * a2 * b * x * y2 - 12 * a3 * b * y2 + 18 * b2 * x3 * y + 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y - 9 * a3 * b2 * y + a2 * b3 * x + a3 * b3)) / (a5 * b5);
                N2y = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * (60 * a2 * y3 - 12 * a2 * b * y2 + 6 * b2 * x2 * y - 9 * a2 * b2 * y + a2 * b3)) / (a4 * b5);
                N3y = (((2 * x - a) * (2 * x - a)) * (40 * a2 * x * y3 + 40 * a3 * y3 - 12 * a2 * b * x * y2 - 12 * a3 * b * y2 + 36 * b2 * x3 * y + 24 * a * b2 * x2 * y - 6 * a2 * b2 * x * y - 6 * a3 * b2 * y - 6 * b3 * x3 - 4 * a * b3 * x2 + a2 * b3 * x + a3 * b3)) / (a5 * b4);
                N4y = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * (40 * a2 * y3 - 12 * a2 * b * y2 + 12 * b2 * x2 * y - 6 * a2 * b2 * y - 2 * b3 * x2 + a2 * b3)) / (4 * a4 * b4);
                N5y = (12 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y) / (a4 * b3);
                N6y = (12 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y) / (a4 * b3);
                N7y = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (6 * y - b)) / (a4 * b2);
                N8y = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (6 * y - b)) / (a4 * b2);
                N9y = -(4 * ((2 * x + a) * (2 * x + a)) * (60 * a2 * x * y3 - 60 * a3 * y3 - 12 * a2 * b * x * y2 + 12 * a3 * b * y2 + 18 * b2 * x3 * y - 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y + 9 * a3 * b2 * y + a2 * b3 * x - a3 * b3)) / (a5 * b5);
                N10y = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (60 * a2 * y3 - 12 * a2 * b * y2 + 6 * b2 * x2 * y - 9 * a2 * b2 * y + a2 * b3)) / (a4 * b5);
                N11y = -(((2 * x + a) * (2 * x + a)) * (40 * a2 * x * y3 - 40 * a3 * y3 - 12 * a2 * b * x * y2 + 12 * a3 * b * y2 + 36 * b2 * x3 * y - 24 * a * b2 * x2 * y - 6 * a2 * b2 * x * y + 6 * a3 * b2 * y - 6 * b3 * x3 + 4 * a * b3 * x2 + a2 * b3 * x - a3 * b3)) / (a5 * b4);
                N12y = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (40 * a2 * y3 - 12 * a2 * b * y2 + 12 * b2 * x2 * y - 6 * a2 * b2 * y - 2 * b3 * x2 + a2 * b3)) / (4 * a4 * b4);
                N13y = -(8 * (x - a) * ((2 * x + a) * (2 * x + a)) * (12 * y2 - b2)) / (a3 * b4);
                N14y = (2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * (12 * y2 - b2)) / (a2 * b4);
                N15y = -(8 * (x - a) * ((2 * x + a) * (2 * x + a)) * y * (20 * y2 - 3 * b2)) / (a3 * b4);
                N16y = (2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y * (20 * y2 - 3 * b2)) / (a2 * b4);
                N17y = (4 * ((2 * x + a) * (2 * x + a)) * (60 * a2 * x * y3 - 60 * a3 * y3 + 12 * a2 * b * x * y2 - 12 * a3 * b * y2 + 18 * b2 * x3 * y - 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y + 9 * a3 * b2 * y - a2 * b3 * x + a3 * b3)) / (a5 * b5);
                N18y = -((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (60 * a2 * y3 + 12 * a2 * b * y2 + 6 * b2 * x2 * y - 9 * a2 * b2 * y - a2 * b3)) / (a4 * b5);
                N19y = -(((2 * x + a) * (2 * x + a)) * (40 * a2 * x * y3 - 40 * a3 * y3 + 12 * a2 * b * x * y2 - 12 * a3 * b * y2 + 36 * b2 * x3 * y - 24 * a * b2 * x2 * y - 6 * a2 * b2 * x * y + 6 * a3 * b2 * y + 6 * b3 * x3 - 4 * a * b3 * x2 - a2 * b3 * x + a3 * b3)) / (a5 * b4);
                N20y = ((2 * x - a) * ((2 * x + a) * (2 * x + a)) * (40 * a2 * y3 + 12 * a2 * b * y2 + 12 * b2 * x2 * y - 6 * a2 * b2 * y + 2 * b3 * x2 - a2 * b3)) / (4 * a4 * b4);
                N21y = -(12 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y) / (a4 * b3);
                N22y = -(12 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y) / (a4 * b3);
                N23y = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (6 * y + b)) / (a4 * b2);
                N24y = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (6 * y + b)) / (a4 * b2);
                N25y = -(4 * ((2 * x - a) * (2 * x - a)) * (60 * a2 * x * y3 + 60 * a3 * y3 + 12 * a2 * b * x * y2 + 12 * a3 * b * y2 + 18 * b2 * x3 * y + 12 * a * b2 * x2 * y - 9 * a2 * b2 * x * y - 9 * a3 * b2 * y - a2 * b3 * x - a3 * b3)) / (a5 * b5);
                N26y = -(((2 * x - a) * (2 * x - a)) * (2 * x + a) * (60 * a2 * y3 + 12 * a2 * b * y2 + 6 * b2 * x2 * y - 9 * a2 * b2 * y - a2 * b3)) / (a4 * b5);
                N27y = (((2 * x - a) * (2 * x - a)) * (40 * a2 * x * y3 + 40 * a3 * y3 + 12 * a2 * b * x * y2 + 12 * a3 * b * y2 + 36 * b2 * x3 * y + 24 * a * b2 * x2 * y - 6 * a2 * b2 * x * y - 6 * a3 * b2 * y + 6 * b3 * x3 + 4 * a * b3 * x2 - a2 * b3 * x - a3 * b3)) / (a5 * b4);
                N28y = (((2 * x - a) * (2 * x - a)) * (2 * x + a) * (40 * a2 * y3 + 12 * a2 * b * y2 + 12 * b2 * x2 * y - 6 * a2 * b2 * y + 2 * b3 * x2 - a2 * b3)) / (4 * a4 * b4);
                N29y = (8 * (x + a) * ((2 * x - a) * (2 * x - a)) * (12 * y2 - b2)) / (a3 * b4);
                N30y = (2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * (12 * y2 - b2)) / (a2 * b4);
                N31y = (8 * (x + a) * ((2 * x - a) * (2 * x - a)) * y * (20 * y2 - 3 * b2)) / (a3 * b4);
                N32y = (2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y * (20 * y2 - 3 * b2)) / (a2 * b4);

                //Segundas derivadas das funções de forma d2N/dxdy
                double N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy, N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy;
                double N17xy, N18xy, N19xy, N20xy, N21xy, N22xy, N23xy, N24xy, N25xy, N26xy, N27xy, N28xy, N29xy, N30xy, N31xy, N32xy;

                N1xy = 2 * ((3 * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (60 * a2 * y2 - 16 * a2 * b * y + 60 * b2 * x2 - 16 * a * b2 * x - 3 * a2 * b2)) / (4 * a5 * b5));
                N2xy = 2 * (((2 * x - a) * (2 * y - b) * (2 * y + b) * (360 * a2 * x * y2 + 60 * a3 * y2 - 96 * a2 * b * x * y - 16 * a3 * b * y + 120 * b2 * x3 + 12 * a * b2 * x2 - 30 * a2 * b2 * x - 3 * a3 * b2)) / (8 * a4 * b5));
                N3xy = 2 * (((2 * x - a) * (2 * x + a) * (2 * y - b) * (120 * a2 * y3 + 12 * a2 * b * y2 + 360 * b2 * x2 * y - 96 * a * b2 * x * y - 30 * a2 * b2 * y + 60 * b3 * x2 - 16 * a * b3 * x - 3 * a2 * b3)) / (8 * a5 * b4));
                N4xy = 2 * (((2 * x - a) * (2 * y - b) * (240 * a2 * x * y3 + 40 * a3 * y3 + 24 * a2 * b * x * y2 + 4 * a3 * b * y2 + 240 * b2 * x3 * y + 24 * a * b2 * x2 * y - 84 * a2 * b2 * x * y - 10 * a3 * b2 * y + 40 * b3 * x3 + 4 * a * b3 * x2 - 10 * a2 * b3 * x - a3 * b3)) / (16 * a4 * b4));
                N5xy = 2 * ((24 * x * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b)) / (a4 * b3));
                N6xy = 2 * ((3 * (2 * x - a) * (2 * x + a) * (20 * x2 - a2) * (2 * y - b) * (2 * y + b)) / (2 * a4 * b3));
                N7xy = 2 * ((4 * x * (2 * x - a) * (2 * x + a) * (2 * y - b) * (6 * y + b)) / (a4 * b2));
                N8xy = 2 * (((2 * x - a) * (2 * x + a) * (20 * x2 - a2) * (2 * y - b) * (6 * y + b)) / (4 * a4 * b2));
                N9xy = 2 * (-(3 * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (60 * a2 * y2 - 16 * a2 * b * y + 60 * b2 * x2 + 16 * a * b2 * x - 3 * a2 * b2)) / (4 * a5 * b5));
                N10xy = 2 * (((2 * x + a) * (2 * y - b) * (2 * y + b) * (360 * a2 * x * y2 - 60 * a3 * y2 - 96 * a2 * b * x * y + 16 * a3 * b * y + 120 * b2 * x3 - 12 * a * b2 * x2 - 30 * a2 * b2 * x + 3 * a3 * b2)) / (8 * a4 * b5));
                N11xy = 2 * (-((2 * x - a) * (2 * x + a) * (2 * y - b) * (120 * a2 * y3 + 12 * a2 * b * y2 + 360 * b2 * x2 * y + 96 * a * b2 * x * y - 30 * a2 * b2 * y + 60 * b3 * x2 + 16 * a * b3 * x - 3 * a2 * b3)) / (8 * a5 * b4));
                N12xy = 2 * (((2 * x + a) * (2 * y - b) * (240 * a2 * x * y3 - 40 * a3 * y3 + 24 * a2 * b * x * y2 - 4 * a3 * b * y2 + 240 * b2 * x3 * y - 24 * a * b2 * x2 * y - 84 * a2 * b2 * x * y + 10 * a3 * b2 * y + 40 * b3 * x3 - 4 * a * b3 * x2 - 10 * a2 * b3 * x + a3 * b3)) / (16 * a4 * b4));
                N13xy = 2 * (-(24 * (2 * x - a) * (2 * x + a) * y * (2 * y - b) * (2 * y + b)) / (a3 * b4));
                N14xy = 2 * ((4 * (2 * x + a) * (6 * x - a) * y * (2 * y - b) * (2 * y + b)) / (a2 * b4));
                N15xy = 2 * (-(3 * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (2 * a3 * b4));
                N16xy = 2 * (((2 * x + a) * (6 * x - a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (4 * a2 * b4));
                N17xy = 2 * ((3 * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (60 * a2 * y2 + 16 * a2 * b * y + 60 * b2 * x2 + 16 * a * b2 * x - 3 * a2 * b2)) / (4 * a5 * b5));
                N18xy = 2 * (-((2 * x + a) * (2 * y - b) * (2 * y + b) * (360 * a2 * x * y2 - 60 * a3 * y2 + 96 * a2 * b * x * y - 16 * a3 * b * y + 120 * b2 * x3 - 12 * a * b2 * x2 - 30 * a2 * b2 * x + 3 * a3 * b2)) / (8 * a4 * b5));
                N19xy = 2 * (-((2 * x - a) * (2 * x + a) * (2 * y + b) * (120 * a2 * y3 - 12 * a2 * b * y2 + 360 * b2 * x2 * y + 96 * a * b2 * x * y - 30 * a2 * b2 * y - 60 * b3 * x2 - 16 * a * b3 * x + 3 * a2 * b3)) / (8 * a5 * b4));
                N20xy = 2 * (((2 * x + a) * (2 * y + b) * (240 * a2 * x * y3 - 40 * a3 * y3 - 24 * a2 * b * x * y2 + 4 * a3 * b * y2 + 240 * b2 * x3 * y - 24 * a * b2 * x2 * y - 84 * a2 * b2 * x * y + 10 * a3 * b2 * y - 40 * b3 * x3 + 4 * a * b3 * x2 + 10 * a2 * b3 * x - a3 * b3)) / (16 * a4 * b4));
                N21xy = 2 * (-(24 * x * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b)) / (a4 * b3));
                N22xy = 2 * (-(3 * (2 * x - a) * (2 * x + a) * (20 * x2 - a2) * (2 * y - b) * (2 * y + b)) / (2 * a4 * b3));
                N23xy = 2 * ((4 * x * (2 * x - a) * (2 * x + a) * (2 * y + b) * (6 * y - b)) / (a4 * b2));
                N24xy = 2 * (((2 * x - a) * (2 * x + a) * (20 * x2 - a2) * (2 * y + b) * (6 * y - b)) / (4 * a4 * b2));
                N25xy = 2 * (-(3 * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (60 * a2 * y2 + 16 * a2 * b * y + 60 * b2 * x2 - 16 * a * b2 * x - 3 * a2 * b2)) / (4 * a5 * b5));
                N26xy = 2 * (-((2 * x - a) * (2 * y - b) * (2 * y + b) * (360 * a2 * x * y2 + 60 * a3 * y2 + 96 * a2 * b * x * y + 16 * a3 * b * y + 120 * b2 * x3 + 12 * a * b2 * x2 - 30 * a2 * b2 * x - 3 * a3 * b2)) / (8 * a4 * b5));
                N27xy = 2 * (((2 * x - a) * (2 * x + a) * (2 * y + b) * (120 * a2 * y3 - 12 * a2 * b * y2 + 360 * b2 * x2 * y - 96 * a * b2 * x * y - 30 * a2 * b2 * y - 60 * b3 * x2 + 16 * a * b3 * x + 3 * a2 * b3)) / (8 * a5 * b4));
                N28xy = 2 * (((2 * x - a) * (2 * y + b) * (240 * a2 * x * y3 + 40 * a3 * y3 - 24 * a2 * b * x * y2 - 4 * a3 * b * y2 + 240 * b2 * x3 * y + 24 * a * b2 * x2 * y - 84 * a2 * b2 * x * y - 10 * a3 * b2 * y - 40 * b3 * x3 - 4 * a * b3 * x2 + 10 * a2 * b3 * x + a3 * b3)) / (16 * a4 * b4));
                N29xy = 2 * ((24 * (2 * x - a) * (2 * x + a) * y * (2 * y - b) * (2 * y + b)) / (a3 * b4));
                N30xy = 2 * ((4 * (2 * x - a) * (6 * x + a) * y * (2 * y - b) * (2 * y + b)) / (a2 * b4));
                N31xy = 2 * ((3 * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (2 * a3 * b4));
                N32xy = 2 * (((2 * x - a) * (6 * x + a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (4 * a2 * b4));

                //Construção da matriz B a partir de uma matriz auxiliar
                double[][] matrix = {
                    {N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x, N17x, N18x, N19x, N20x, N21x, N22x, N23x, N24x, N25x, N26x, N27x, N28x, N29x, N30x, N31x, N32x},
                    {N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y, N17y, N18y, N19y, N20y, N21y, N22y, N23y, N24y, N25y, N26y, N27y, N28y, N29y, N30y, N31y, N32y},
                    {N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy, N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy, N17xy, N18xy, N19xy, N20xy, N21xy, N22xy, N23xy, N24xy, N25xy, N26xy, N27xy, N28xy, N29xy, N30xy, N31xy, N32xy}};

                matrixB = matrix;
            } else if (nodes == 9) {
                //Segundas derivadas das funções de forma d2N/dx2
                double N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x, N17x, N18x;
                double N19x, N20x, N21x, N22x, N23x, N24x, N25x, N26x, N27x, N28x, N29x, N30x, N31x, N32x, N33x, N34x, N35x, N36x;

                N1x = (8 * (120 * x3 - 24 * a * x2 - 15 * a2 * x + 2 * a3) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a5 * b5);
                N2x = (2 * (80 * x3 - 24 * a * x2 - 6 * a2 * x + a3) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                N3x = (2 * (120 * x3 - 24 * a * x2 - 15 * a2 * x + 2 * a3) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a5 * b4);
                N4x = ((80 * x3 - 24 * a * x2 - 6 * a2 * x + a3) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (2 * a4 * b4);
                N5x = (32 * (12 * x2 - a2) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                N6x = (32 * x * (20 * x2 - 3 * a2) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                N7x = (8 * (12 * x2 - a2) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a4 * b4);
                N8x = (8 * x * (20 * x2 - 3 * a2) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a4 * b4);
                N9x = -(8 * (120 * x3 + 24 * a * x2 - 15 * a2 * x - 2 * a3) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a5 * b5);
                N10x = (2 * (80 * x3 + 24 * a * x2 - 6 * a2 * x - a3) * y2 * ((2 * y - b) * (2 * y - b)) * (3 * y + 2 * b)) / (a4 * b5);
                N11x = -(2 * (120 * x3 + 24 * a * x2 - 15 * a2 * x - 2 * a3) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (a5 * b4);
                N12x = ((80 * x3 + 24 * a * x2 - 6 * a2 * x - a3) * y2 * ((2 * y - b) * (2 * y - b)) * (2 * y + b)) / (2 * a4 * b4);
                N13x = -(4 * (120 * x3 + 24 * a * x2 - 15 * a2 * x - 2 * a3) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                N14x = ((80 * x3 + 24 * a * x2 - 6 * a2 * x - a3) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N15x = -(4 * (120 * x3 + 24 * a * x2 - 15 * a2 * x - 2 * a3) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                N16x = ((80 * x3 + 24 * a * x2 - 6 * a2 * x - a3) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N17x = (8 * (120 * x3 + 24 * a * x2 - 15 * a2 * x - 2 * a3) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a5 * b5);
                N18x = -(2 * (80 * x3 + 24 * a * x2 - 6 * a2 * x - a3) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                N19x = -(2 * (120 * x3 + 24 * a * x2 - 15 * a2 * x - 2 * a3) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                N20x = ((80 * x3 + 24 * a * x2 - 6 * a2 * x - a3) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                N21x = -(32 * (12 * x2 - a2) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                N22x = -(32 * x * (20 * x2 - 3 * a2) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                N23x = (8 * (12 * x2 - a2) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N24x = (8 * x * (20 * x2 - 3 * a2) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N25x = -(8 * (120 * x3 - 24 * a * x2 - 15 * a2 * x + 2 * a3) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a5 * b5);
                N26x = -(2 * (80 * x3 - 24 * a * x2 - 6 * a2 * x + a3) * y2 * ((2 * y + b) * (2 * y + b)) * (3 * y - 2 * b)) / (a4 * b5);
                N27x = (2 * (120 * x3 - 24 * a * x2 - 15 * a2 * x + 2 * a3) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                N28x = ((80 * x3 - 24 * a * x2 - 6 * a2 * x + a3) * y2 * (2 * y - b) * ((2 * y + b) * (2 * y + b))) / (2 * a4 * b4);
                N29x = (4 * (120 * x3 - 24 * a * x2 - 15 * a2 * x + 2 * a3) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                N30x = ((80 * x3 - 24 * a * x2 - 6 * a2 * x + a3) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N31x = (4 * (120 * x3 - 24 * a * x2 - 15 * a2 * x + 2 * a3) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a5 * b4);
                N32x = ((80 * x3 - 24 * a * x2 - 6 * a2 * x + a3) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N33x = (16 * (12 * x2 - a2) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N34x = (16 * x * (20 * x2 - 3 * a2) * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N35x = (16 * (12 * x2 - a2) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);
                N36x = (16 * x * (20 * x2 - 3 * a2) * y * ((2 * y - b) * (2 * y - b)) * ((2 * y + b) * (2 * y + b))) / (a4 * b4);

                //Segundas derivadas das funções de forma d2N/dy2
                double N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y, N17y, N18y;
                double N19y, N20y, N21y, N22y, N23y, N24y, N25y, N26y, N27y, N28y, N29y, N30y, N31y, N32y, N33y, N34y, N35y, N36y;

                N1y = (8 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * (120 * y3 - 24 * b * y2 - 15 * b2 * y + 2 * b3)) / (a5 * b5);
                N2y = (2 * x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * (120 * y3 - 24 * b * y2 - 15 * b2 * y + 2 * b3)) / (a4 * b5);
                N3y = (2 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * (80 * y3 - 24 * b * y2 - 6 * b2 * y + b3)) / (a5 * b4);
                N4y = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * (80 * y3 - 24 * b * y2 - 6 * b2 * y + b3)) / (2 * a4 * b4);
                N5y = (4 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (120 * y3 - 24 * b * y2 - 15 * b2 * y + 2 * b3)) / (a4 * b5);
                N6y = (4 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (120 * y3 - 24 * b * y2 - 15 * b2 * y + 2 * b3)) / (a4 * b5);
                N7y = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (80 * y3 - 24 * b * y2 - 6 * b2 * y + b3)) / (a4 * b4);
                N8y = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (80 * y3 - 24 * b * y2 - 6 * b2 * y + b3)) / (a4 * b4);
                N9y = -(8 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * (120 * y3 - 24 * b * y2 - 15 * b2 * y + 2 * b3)) / (a5 * b5);
                N10y = (2 * x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * (120 * y3 - 24 * b * y2 - 15 * b2 * y + 2 * b3)) / (a4 * b5);
                N11y = -(2 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * (80 * y3 - 24 * b * y2 - 6 * b2 * y + b3)) / (a5 * b4);
                N12y = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * (80 * y3 - 24 * b * y2 - 6 * b2 * y + b3)) / (2 * a4 * b4);
                N13y = -(32 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * (12 * y2 - b2)) / (a5 * b4);
                N14y = (8 * x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * (12 * y2 - b2)) / (a4 * b4);
                N15y = -(32 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * y * (20 * y2 - 3 * b2)) / (a5 * b4);
                N16y = (8 * x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * y * (20 * y2 - 3 * b2)) / (a4 * b4);
                N17y = (8 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * (120 * y3 + 24 * b * y2 - 15 * b2 * y - 2 * b3)) / (a5 * b5);
                N18y = -(2 * x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * (120 * y3 + 24 * b * y2 - 15 * b2 * y - 2 * b3)) / (a4 * b5);
                N19y = -(2 * x2 * ((2 * x + a) * (2 * x + a)) * (3 * x - 2 * a) * (80 * y3 + 24 * b * y2 - 6 * b2 * y - b3)) / (a5 * b4);
                N20y = (x2 * (2 * x - a) * ((2 * x + a) * (2 * x + a)) * (80 * y3 + 24 * b * y2 - 6 * b2 * y - b3)) / (2 * a4 * b4);
                N21y = -(4 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (120 * y3 + 24 * b * y2 - 15 * b2 * y - 2 * b3)) / (a4 * b5);
                N22y = -(4 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (120 * y3 + 24 * b * y2 - 15 * b2 * y - 2 * b3)) / (a4 * b5);
                N23y = (((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (80 * y3 + 24 * b * y2 - 6 * b2 * y - b3)) / (a4 * b4);
                N24y = (x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (80 * y3 + 24 * b * y2 - 6 * b2 * y - b3)) / (a4 * b4);
                N25y = -(8 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * (120 * y3 + 24 * b * y2 - 15 * b2 * y - 2 * b3)) / (a5 * b5);
                N26y = -(2 * x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * (120 * y3 + 24 * b * y2 - 15 * b2 * y - 2 * b3)) / (a4 * b5);
                N27y = (2 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * (80 * y3 + 24 * b * y2 - 6 * b2 * y - b3)) / (a5 * b4);
                N28y = (x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * (80 * y3 + 24 * b * y2 - 6 * b2 * y - b3)) / (2 * a4 * b4);
                N29y = (32 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * (12 * y2 - b2)) / (a5 * b4);
                N30y = (8 * x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * (12 * y2 - b2)) / (a4 * b4);
                N31y = (32 * x2 * ((2 * x - a) * (2 * x - a)) * (3 * x + 2 * a) * y * (20 * y2 - 3 * b2)) / (a5 * b4);
                N32y = (8 * x2 * ((2 * x - a) * (2 * x - a)) * (2 * x + a) * y * (20 * y2 - 3 * b2)) / (a4 * b4);
                N33y = (16 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (12 * y2 - b2)) / (a4 * b4);
                N34y = (16 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * (12 * y2 - b2)) / (a4 * b4);
                N35y = (16 * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y * (20 * y2 - 3 * b2)) / (a4 * b4);
                N36y = (16 * x * ((2 * x - a) * (2 * x - a)) * ((2 * x + a) * (2 * x + a)) * y * (20 * y2 - 3 * b2)) / (a4 * b4);

                //Segundas derivadas das funções de forma d2N/dxdy
                double N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy, N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy, N17xy, N18xy;
                double N19xy, N20xy, N21xy, N22xy, N23xy, N24xy, N25xy, N26xy, N27xy, N28xy, N29xy, N30xy, N31xy, N32xy, N33xy, N34xy, N35xy, N36xy;

                N1xy = 2 * ((4 * x * (2 * x - a) * (2 * x + a) * (15 * x - 4 * a) * y * (2 * y - b) * (2 * y + b) * (15 * y - 4 * b)) / (a5 * b5));
                N2xy = 2 * ((2 * x * (2 * x - a) * (10 * x2 + a * x - a2) * y * (2 * y - b) * (2 * y + b) * (15 * y - 4 * b)) / (a4 * b5));
                N3xy = 2 * ((2 * x * (2 * x - a) * (2 * x + a) * (15 * x - 4 * a) * y * (2 * y - b) * (10 * y2 + b * y - b2)) / (a5 * b4));
                N4xy = 2 * ((x * (2 * x - a) * (10 * x2 + a * x - a2) * y * (2 * y - b) * (10 * y2 + b * y - b2)) / (a4 * b4));
                N5xy = 2 * ((32 * x * (2 * x - a) * (2 * x + a) * y * (2 * y - b) * (2 * y + b) * (15 * y - 4 * b)) / (a4 * b5));
                N6xy = 2 * ((2 * (2 * x - a) * (2 * x + a) * (20 * x2 - a2) * y * (2 * y - b) * (2 * y + b) * (15 * y - 4 * b)) / (a4 * b5));
                N7xy = 2 * ((16 * x * (2 * x - a) * (2 * x + a) * y * (2 * y - b) * (10 * y2 + b * y - b2)) / (a4 * b4));
                N8xy = 2 * (((2 * x - a) * (2 * x + a) * (20 * x2 - a2) * y * (2 * y - b) * (10 * y2 + b * y - b2)) / (a4 * b4));
                N9xy = 2 * (-(4 * x * (2 * x - a) * (2 * x + a) * (15 * x + 4 * a) * y * (2 * y - b) * (2 * y + b) * (15 * y - 4 * b)) / (a5 * b5));
                N10xy = 2 * ((2 * x * (2 * x + a) * (10 * x2 - a * x - a2) * y * (2 * y - b) * (2 * y + b) * (15 * y - 4 * b)) / (a4 * b5));
                N11xy = 2 * (-(2 * x * (2 * x - a) * (2 * x + a) * (15 * x + 4 * a) * y * (2 * y - b) * (10 * y2 + b * y - b2)) / (a5 * b4));
                N12xy = 2 * ((x * (2 * x + a) * (10 * x2 - a * x - a2) * y * (2 * y - b) * (10 * y2 + b * y - b2)) / (a4 * b4));
                N13xy = 2 * (-(32 * x * (2 * x - a) * (2 * x + a) * (15 * x + 4 * a) * y * (2 * y - b) * (2 * y + b)) / (a5 * b4));
                N14xy = 2 * ((16 * x * (2 * x + a) * (10 * x2 - a * x - a2) * y * (2 * y - b) * (2 * y + b)) / (a4 * b4));
                N15xy = 2 * (-(2 * x * (2 * x - a) * (2 * x + a) * (15 * x + 4 * a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (a5 * b4));
                N16xy = 2 * ((x * (2 * x + a) * (10 * x2 - a * x - a2) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (a4 * b4));
                N17xy = 2 * ((4 * x * (2 * x - a) * (2 * x + a) * (15 * x + 4 * a) * y * (2 * y - b) * (2 * y + b) * (15 * y + 4 * b)) / (a5 * b5));
                N18xy = 2 * (-(2 * x * (2 * x + a) * (10 * x2 - a * x - a2) * y * (2 * y - b) * (2 * y + b) * (15 * y + 4 * b)) / (a4 * b5));
                N19xy = 2 * (-(2 * x * (2 * x - a) * (2 * x + a) * (15 * x + 4 * a) * y * (2 * y + b) * (10 * y2 - b * y - b2)) / (a5 * b4));
                N20xy = 2 * ((x * (2 * x + a) * (10 * x2 - a * x - a2) * y * (2 * y + b) * (10 * y2 - b * y - b2)) / (a4 * b4));
                N21xy = 2 * (-(32 * x * (2 * x - a) * (2 * x + a) * y * (2 * y - b) * (2 * y + b) * (15 * y + 4 * b)) / (a4 * b5));
                N22xy = 2 * (-(2 * (2 * x - a) * (2 * x + a) * (20 * x2 - a2) * y * (2 * y - b) * (2 * y + b) * (15 * y + 4 * b)) / (a4 * b5));
                N23xy = 2 * ((16 * x * (2 * x - a) * (2 * x + a) * y * (2 * y + b) * (10 * y2 - b * y - b2)) / (a4 * b4));
                N24xy = 2 * (((2 * x - a) * (2 * x + a) * (20 * x2 - a2) * y * (2 * y + b) * (10 * y2 - b * y - b2)) / (a4 * b4));
                N25xy = 2 * (-(4 * x * (2 * x - a) * (2 * x + a) * (15 * x - 4 * a) * y * (2 * y - b) * (2 * y + b) * (15 * y + 4 * b)) / (a5 * b5));
                N26xy = 2 * (-(2 * x * (2 * x - a) * (10 * x2 + a * x - a2) * y * (2 * y - b) * (2 * y + b) * (15 * y + 4 * b)) / (a4 * b5));
                N27xy = 2 * ((2 * x * (2 * x - a) * (2 * x + a) * (15 * x - 4 * a) * y * (2 * y + b) * (10 * y2 - b * y - b2)) / (a5 * b4));
                N28xy = 2 * ((x * (2 * x - a) * (10 * x2 + a * x - a2) * y * (2 * y + b) * (10 * y2 - b * y - b2)) / (a4 * b4));
                N29xy = 2 * ((32 * x * (2 * x - a) * (2 * x + a) * (15 * x - 4 * a) * y * (2 * y - b) * (2 * y + b)) / (a5 * b4));
                N30xy = 2 * ((16 * x * (2 * x - a) * (10 * x2 + a * x - a2) * y * (2 * y - b) * (2 * y + b)) / (a4 * b4));
                N31xy = 2 * ((2 * x * (2 * x - a) * (2 * x + a) * (15 * x - 4 * a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (a5 * b4));
                N32xy = 2 * ((x * (2 * x - a) * (10 * x2 + a * x - a2) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (a4 * b4));
                N33xy = 2 * ((256 * x * (2 * x - a) * (2 * x + a) * y * (2 * y - b) * (2 * y + b)) / (a4 * b4));
                N34xy = 2 * ((16 * (2 * x - a) * (2 * x + a) * (20 * x2 - a2) * y * (2 * y - b) * (2 * y + b)) / (a4 * b4));
                N35xy = 2 * ((16 * x * (2 * x - a) * (2 * x + a) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (a4 * b4));
                N36xy = 2 * (((2 * x - a) * (2 * x + a) * (20 * x2 - a2) * (2 * y - b) * (2 * y + b) * (20 * y2 - b2)) / (a4 * b4));

                //Construção da matriz B a partir de uma matriz auxiliar
                double[][] matrix = {
                    {N1x, N2x, N3x, N4x, N5x, N6x, N7x, N8x, N9x, N10x, N11x, N12x, N13x, N14x, N15x, N16x, N17x, N18x, N19x, N20x, N21x, N22x, N23x, N24x, N25x, N26x, N27x, N28x, N29x, N30x, N31x, N32x, N33x, N34x, N35x, N36x},
                    {N1y, N2y, N3y, N4y, N5y, N6y, N7y, N8y, N9y, N10y, N11y, N12y, N13y, N14y, N15y, N16y, N17y, N18y, N19y, N20y, N21y, N22y, N23y, N24y, N25y, N26y, N27y, N28y, N29y, N30y, N31y, N32y, N33y, N34y, N35y, N36y},
                    {N1xy, N2xy, N3xy, N4xy, N5xy, N6xy, N7xy, N8xy, N9xy, N10xy, N11xy, N12xy, N13xy, N14xy, N15xy, N16xy, N17xy, N18xy, N19xy, N20xy, N21xy, N22xy, N23xy, N24xy, N25xy, N26xy, N27xy, N28xy, N29xy, N30xy, N31xy, N32xy, N33xy, N34xy, N35xy, N36xy}};

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
