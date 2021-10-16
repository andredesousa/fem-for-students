/*
 * Esta classe fornece a matriz B de um elemento finito bidimensional
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_2D {

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB é a matriz das derivadas das funções de forma
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_2D(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
            if (nodes == 3) {
                double ax, ay, bx, by, cx, cy;

                ax = derivedMatrixB[0][0];
                ay = derivedMatrixB[0][1];
                bx = derivedMatrixB[1][0];
                by = derivedMatrixB[1][1];
                cx = derivedMatrixB[2][0];
                cy = derivedMatrixB[2][1];

                double[][] matrix = { { ax, 0, bx, 0, cx, 0 }, { 0, ay, 0, by, 0, cy }, { ay, ax, by, bx, cy, cx } };

                matrixB = matrix;
            } else if (nodes == 4) {
                double ax, ay, bx, by, cx, cy, dx, dy;

                ax = derivedMatrixB[0][0];
                ay = derivedMatrixB[0][1];
                bx = derivedMatrixB[1][0];
                by = derivedMatrixB[1][1];
                cx = derivedMatrixB[2][0];
                cy = derivedMatrixB[2][1];
                dx = derivedMatrixB[3][0];
                dy = derivedMatrixB[3][1];

                double[][] matrix = { { ax, 0, bx, 0, cx, 0, dx, 0 }, { 0, ay, 0, by, 0, cy, 0, dy }, { ay, ax, by, bx, cy, cx, dy, dx } };

                matrixB = matrix;
            } else if (nodes == 6) {
                double ax, bx, cx, dx, ex, fx;
                double ay, by, cy, dy, ey, fy;

                ax = derivedMatrixB[0][0];
                ay = derivedMatrixB[0][1];
                bx = derivedMatrixB[1][0];
                by = derivedMatrixB[1][1];
                cx = derivedMatrixB[2][0];
                cy = derivedMatrixB[2][1];
                dx = derivedMatrixB[3][0];
                dy = derivedMatrixB[3][1];
                ex = derivedMatrixB[4][0];
                ey = derivedMatrixB[4][1];
                fx = derivedMatrixB[5][0];
                fy = derivedMatrixB[5][1];

                double[][] matrix = {
                    { ax, 0, bx, 0, cx, 0, dx, 0, ex, 0, fx, 0 },
                    { 0, ay, 0, by, 0, cy, 0, dy, 0, ey, 0, fy },
                    { ay, ax, by, bx, cy, cx, dy, dx, ey, ex, fy, fx },
                };

                matrixB = matrix;
            } else if (nodes == 8) {
                double ax, bx, cx, dx, ex, fx, gx, hx;
                double ay, by, cy, dy, ey, fy, gy, hy;

                ax = derivedMatrixB[0][0];
                ay = derivedMatrixB[0][1];
                bx = derivedMatrixB[1][0];
                by = derivedMatrixB[1][1];
                cx = derivedMatrixB[2][0];
                cy = derivedMatrixB[2][1];
                dx = derivedMatrixB[3][0];
                dy = derivedMatrixB[3][1];
                ex = derivedMatrixB[4][0];
                ey = derivedMatrixB[4][1];
                fx = derivedMatrixB[5][0];
                fy = derivedMatrixB[5][1];
                gx = derivedMatrixB[6][0];
                gy = derivedMatrixB[6][1];
                hx = derivedMatrixB[7][0];
                hy = derivedMatrixB[7][1];

                double[][] matrix = {
                    { ax, 0, bx, 0, cx, 0, dx, 0, ex, 0, fx, 0, gx, 0, hx, 0 },
                    { 0, ay, 0, by, 0, cy, 0, dy, 0, ey, 0, fy, 0, gy, 0, hy },
                    { ay, ax, by, bx, cy, cx, dy, dx, ey, ex, fy, fx, gy, gx, hy, hx },
                };

                matrixB = matrix;
            } else if (nodes == 9) {
                double ax, bx, cx, dx, ex, fx, gx, hx, ix;
                double ay, by, cy, dy, ey, fy, gy, hy, iy;

                ax = derivedMatrixB[0][0];
                ay = derivedMatrixB[0][1];
                bx = derivedMatrixB[1][0];
                by = derivedMatrixB[1][1];
                cx = derivedMatrixB[2][0];
                cy = derivedMatrixB[2][1];
                dx = derivedMatrixB[3][0];
                dy = derivedMatrixB[3][1];
                ex = derivedMatrixB[4][0];
                ey = derivedMatrixB[4][1];
                fx = derivedMatrixB[5][0];
                fy = derivedMatrixB[5][1];
                gx = derivedMatrixB[6][0];
                gy = derivedMatrixB[6][1];
                hx = derivedMatrixB[7][0];
                hy = derivedMatrixB[7][1];
                ix = derivedMatrixB[8][0];
                iy = derivedMatrixB[8][1];

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
