/*
 * Esta classe fornece a matriz B de um elemento finito bidimensional
 * O método requer as coordenadas x e y, as dimensões do elemento e o número de nós
 * A matriz B é avaliada em função das coordenas x e y
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_2D {

    /**
     * Este método fornece a matriz B de um elemento finito bidimensional
     *
     * @param x é a coordenada local segundo o eixo x
     * @param y é a coordenada local segundo o eixo y
     * @param a é o comprimento do elemento finito
     * @param b é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_2D(double x, double y, double a, double b, int nodes) {
        double[][] matrixB;

        if (a != 0 && b != 0 && nodes > 1) {
            if (nodes == 3) {
                double ax, ay, bx, by, cx, cy;

                ax = -1 / a;
                ay = -1 / b;
                bx = 1 / a;
                by = 0;
                cx = 0;
                cy = 1 / b;

                double[][] matrix = { { ax, 0, bx, 0, cx, 0 }, { 0, ay, 0, by, 0, cy }, { ay, ax, by, bx, cy, cx } };

                matrixB = matrix;
            } else if (nodes == 4) {
                double ax, ay, bx, by, cx, cy, dx, dy;

                ax = -1 / (2 * a) + y / (a * b);
                ay = -1 / (2 * b) + x / (a * b);
                bx = 1 / (2 * a) - y / (a * b);
                by = -1 / (2 * b) - x / (a * b);
                cx = 1 / (2 * a) + y / (a * b);
                cy = 1 / (2 * b) + x / (a * b);
                dx = -1 / (2 * a) - y / (a * b);
                dy = 1 / (2 * b) - x / (a * b);

                double[][] matrix = { { ax, 0, bx, 0, cx, 0, dx, 0 }, { 0, ay, 0, by, 0, cy, 0, dy }, { ay, ax, by, bx, cy, cx, dy, dx } };

                matrixB = matrix;
            } else if (nodes == 6) {
                double ax, bx, cx, dx, ex, fx;
                double ay, by, cy, dy, ey, fy;

                ax = (4 * a * y + 4 * b * x - 3 * a * b) / (a * a * b);
                ay = (4 * a * y + 4 * b * x - 3 * a * b) / (a * b * b);
                bx = -(4 * (a * y + 2 * b * x - a * b)) / (a * a * b);
                by = -(4 * x) / (a * b);
                cx = (4 * x - a) / (a * a);
                cy = 0;
                dx = (4 * y) / (a * b);
                dy = (4 * x) / (a * b);
                ex = 0;
                ey = (4 * y - b) / (b * b);
                fx = -(4 * y) / (a * b);
                fy = -(4 * (2 * a * y + b * x - a * b)) / (a * b * b);

                double[][] matrix = {
                    { ax, 0, bx, 0, cx, 0, dx, 0, ex, 0, fx, 0 },
                    { 0, ay, 0, by, 0, cy, 0, dy, 0, ey, 0, fy },
                    { ay, ax, by, bx, cy, cx, dy, dx, ey, ex, fy, fx },
                };

                matrixB = matrix;
            } else if (nodes == 8) {
                double ax, bx, cx, dx, ex, fx, gx, hx;
                double ay, by, cy, dy, ey, fy, gy, hy;

                ax = -((2 * y - b) * (a * y + 2 * b * x)) / (a * a * b * b);
                ay = -((2 * x - a) * (2 * a * y + b * x)) / (a * a * b * b);
                bx = (4 * x * (2 * y - b)) / (a * a * b);
                by = ((2 * x - a) * (2 * x + a)) / (a * a * b);
                cx = ((2 * y - b) * (a * y - 2 * b * x)) / (a * a * b * b);
                cy = ((2 * x + a) * (2 * a * y - b * x)) / (a * a * b * b);
                dx = -((2 * y - b) * (2 * y + b)) / (a * b * b);
                dy = -(4 * (2 * x + a) * y) / (a * b * b);
                ex = ((2 * y + b) * (a * y + 2 * b * x)) / (a * a * b * b);
                ey = ((2 * x + a) * (2 * a * y + b * x)) / (a * a * b * b);
                fx = -(4 * x * (2 * y + b)) / (a * a * b);
                fy = -((2 * x - a) * (2 * x + a)) / (a * a * b);
                gx = -((2 * y + b) * (a * y - 2 * b * x)) / (a * a * b * b);
                gy = -((2 * x - a) * (2 * a * y - b * x)) / (a * a * b * b);
                hx = ((2 * y - b) * (2 * y + b)) / (a * b * b);
                hy = (4 * (2 * x - a) * y) / (a * b * b);

                double[][] matrix = {
                    { ax, 0, bx, 0, cx, 0, dx, 0, ex, 0, fx, 0, gx, 0, hx, 0 },
                    { 0, ay, 0, by, 0, cy, 0, dy, 0, ey, 0, fy, 0, gy, 0, hy },
                    { ay, ax, by, bx, cy, cx, dy, dx, ey, ex, fy, fx, gy, gx, hy, hx },
                };

                matrixB = matrix;
            } else if (nodes == 9) {
                double ax, bx, cx, dx, ex, fx, gx, hx, ix;
                double ay, by, cy, dy, ey, fy, gy, hy, iy;

                ax = y / (a * a * b * b) * (4 * x - a) * (2 * y - b);
                ay = x / (a * a * b * b) * (2 * x - a) * (4 * y - b);
                bx = -(8 * x * y) / (a * a * b * b) * (2 * y - b);
                by = -1 / (a * a * b * b) * (4 * x * x - a * a) * (4 * y - b);
                cx = y / (a * a * b * b) * (4 * x + a) * (2 * y - b);
                cy = x / (a * a * b * b) * (2 * x + a) * (4 * y - b);
                dx = -1 / (a * a * b * b) * (4 * x + a) * (4 * y * y - b * b);
                dy = -(8 * x * y) / (a * a * b * b) * (2 * x + a);
                ex = y / (a * a * b * b) * (4 * x + a) * (2 * y + b);
                ey = x / (a * a * b * b) * (2 * x + a) * (4 * y + b);
                fx = -(8 * x * y) / (a * a * b * b) * (2 * y + b);
                fy = -1 / (a * a * b * b) * (4 * x * x - a * a) * (4 * y + b);
                gx = y / (a * a * b * b) * (4 * x - a) * (2 * y + b);
                gy = x / (a * a * b * b) * (2 * x - a) * (4 * y + b);
                hx = -1 / (a * a * b * b) * (4 * x - a) * (4 * y * y - b * b);
                hy = -(8 * x * y) / (a * a * b * b) * (2 * x - a);
                ix = (8 * x) / (a * a * b * b) * (4 * y * y - b * b);
                iy = (8 * y) / (a * a * b * b) * (4 * x * x - a * a);

                double[][] matrix = {
                    { ax, 0, bx, 0, cx, 0, dx, 0, ex, 0, fx, 0, gx, 0, hx, 0, ix, 0 },
                    { 0, ay, 0, by, 0, cy, 0, dy, 0, ey, 0, fy, 0, gy, 0, hy, 0, iy },
                    { ay, ax, by, bx, cy, cx, dy, dx, ey, ex, fy, fx, gy, gx, hy, hx, iy, ix },
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
