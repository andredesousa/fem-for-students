/*
 * Esta classe fornece a matriz B de um elemento finito tridimensional
 * A classe requer as derivadas das funções de forma do elemento finito
 * A matriz B é avaliada consoante o tipo de elemento finito
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class MatrixB_3D {

    /**
     * Método para obter a matriz B do elemento finito
     *
     * @param derivedMatrixB é a matriz das derivadas das funções de forma
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] matrixB_3D(double[][] derivedMatrixB, int nodes) {
        double[][] matrixB;

        if (derivedMatrixB != null && nodes > 1) {
            if (nodes == 4) {
                double ax, bx, cx, dx;
                double ay, by, cy, dy;
                double az, bz, cz, dz;

                ax = 0;
                ay = 0;
                az = 0;
                bx = 0;
                by = 0;
                bz = 0;
                cx = 0;
                cy = 0;
                cz = 0;
                dx = 0;
                dy = 0;
                dz = 0;

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

                ax = 0;
                ay = 0;
                az = 0;
                bx = 0;
                by = 0;
                bz = 0;
                cx = 0;
                cy = 0;
                cz = 0;
                dx = 0;
                dy = 0;
                dz = 0;
                ex = 0;
                ey = 0;
                ez = 0;
                fx = 0;
                fy = 0;
                fz = 0;
                gx = 0;
                gy = 0;
                gz = 0;
                hx = 0;
                hy = 0;
                hz = 0;

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
