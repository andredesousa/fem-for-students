/*
 * Esta classe fornece a matriz B de um elemento finito tridimensional
 * O método requer as coordenadas x, y e z, as dimensões do elemento e o número de nós
 * A matriz B é avaliada em função das coordenas x, y e z
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_3D {

    /**
     * Este método fornece a matriz B de um elemento finito tridimensional
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
    public static double[][] matrixB_3D(double x, double y, double z, double a, double b, double c, int nodes) {
        double[][] matrixB;

        if (a != 0 && b != 0 && c != 0 && nodes > 1) {
            if (nodes == 4) {
                double ax, bx, cx, dx;
                double ay, by, cy, dy;
                double az, bz, cz, dz;

                ax = -1 / a;
                ay = -1 / b;
                az = -1 / c;
                bx = 1 / a;
                by = 0;
                bz = 0;
                cx = 0;
                cy = 1 / b;
                cz = 0;
                dx = 0;
                dy = 0;
                dz = 1 / c;

                double[][] matrix = {
                    { ax, 0, 0, bx, 0, 0, cx, 0, 0, dx, 0, 0 },
                    { 0, ay, 0, 0, by, 0, 0, cy, 0, 0, dy, 0 },
                    { 0, 0, az, 0, 0, bz, 0, 0, cz, 0, 0, dz },
                    { 0, az, ay, 0, bz, by, 0, cz, cy, 0, dz, dy },
                    { az, 0, ax, bz, 0, bx, cz, 0, cx, dz, 0, dx },
                    { ay, ax, 0, by, bx, 0, cy, cx, 0, dy, dx, 0 },
                };

                matrixB = matrix;
            } else if (nodes == 8) {
                double ax, bx, cx, dx, ex, fx, gx, hx;
                double ay, by, cy, dy, ey, fy, gy, hy;
                double az, bz, cz, dz, ez, fz, gz, hz;

                ax = -1 / (a * b * c) * (0.5 * b - y) * (0.5 * c - z);
                ay = -1 / (a * b * c) * (0.5 * a - x) * (0.5 * c - z);
                az = -1 / (a * b * c) * (0.5 * a - x) * (0.5 * b - y);
                bx = 1 / (a * b * c) * (0.5 * b - y) * (0.5 * c - z);
                by = -1 / (a * b * c) * (0.5 * a + x) * (0.5 * c - z);
                bz = -1 / (a * b * c) * (0.5 * a + x) * (0.5 * b - y);
                cx = 1 / (a * b * c) * (0.5 * b + y) * (0.5 * c - z);
                cy = 1 / (a * b * c) * (0.5 * a + x) * (0.5 * c - z);
                cz = -1 / (a * b * c) * (0.5 * a + x) * (0.5 * b + y);
                dx = -1 / (a * b * c) * (0.5 * b + y) * (0.5 * c - z);
                dy = 1 / (a * b * c) * (0.5 * a - x) * (0.5 * c - z);
                dz = -1 / (a * b * c) * (0.5 * a - x) * (0.5 * b + y);
                ex = -1 / (a * b * c) * (0.5 * b - y) * (0.5 * c + z);
                ey = -1 / (a * b * c) * (0.5 * a - x) * (0.5 * c + z);
                ez = 1 / (a * b * c) * (0.5 * a - x) * (0.5 * b - y);
                fx = 1 / (a * b * c) * (0.5 * b - y) * (0.5 * c + z);
                fy = -1 / (a * b * c) * (0.5 * a + x) * (0.5 * c + z);
                fz = 1 / (a * b * c) * (0.5 * a + x) * (0.5 * b - y);
                gx = 1 / (a * b * c) * (0.5 * b + y) * (0.5 * c + z);
                gy = 1 / (a * b * c) * (0.5 * a + x) * (0.5 * c + z);
                gz = 1 / (a * b * c) * (0.5 * a + x) * (0.5 * b + y);
                hx = -1 / (a * b * c) * (0.5 * b + y) * (0.5 * c + z);
                hy = 1 / (a * b * c) * (0.5 * a - x) * (0.5 * c + z);
                hz = 1 / (a * b * c) * (0.5 * a - x) * (0.5 * b + y);

                double[][] matrix = {
                    { ax, 0, 0, bx, 0, 0, cx, 0, 0, dx, 0, 0, ex, 0, 0, fx, 0, 0, gx, 0, 0, hx, 0, 0 },
                    { 0, ay, 0, 0, by, 0, 0, cy, 0, 0, dy, 0, 0, ey, 0, 0, fy, 0, 0, gy, 0, 0, hy, 0 },
                    { 0, 0, az, 0, 0, bz, 0, 0, cz, 0, 0, dz, 0, 0, ez, 0, 0, fz, 0, 0, gz, 0, 0, hz },
                    { 0, az, ay, 0, bz, by, 0, cz, cy, 0, dz, dy, 0, ez, ey, 0, fz, fy, 0, gz, gy, 0, hz, hy },
                    { az, 0, ax, bz, 0, bx, cz, 0, cx, dz, 0, dx, ez, 0, ex, fz, 0, fx, gz, 0, gx, hz, 0, hx },
                    { ay, ax, 0, by, bx, 0, cy, cx, 0, dy, dx, 0, ey, ex, 0, fy, fx, 0, gy, gx, 0, hy, hx, 0 },
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
