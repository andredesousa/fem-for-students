/*
 * Esta classe fornece as derivadas das funções de forma de um elemento finito
 * O método requer a coordenada r, s, t e o número de nós do elemento finito
 * A matriz deve ser posteriormente multiplicada pelas coordenadas cartesianas
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class DerivedMatrix_3D {

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eixo x
     * @param s é a coordenada local segundo o eixo y
     * @param t é a coordenada local segundo o eixo z
     * @param a é a largura do elemento finito
     * @param b é o comprimento do elemento finito
     * @param c é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrix_3D(double r, double s, double t, double a, double b, double c, int nodes) {
        double[][] matrixJ;

        if (a != 0 && b != 0 && c != 0 && nodes > 1) {
            if (nodes == 4) {
                double ar, br, cr, dr;
                double as, bs, cs, ds;
                double at, bt, ct, dt;

                ar = -1 / a;
                as = -1 / b;
                at = -1 / c;
                br = 1 / a;
                bs = 0;
                bt = 0;
                cr = 0;
                cs = 1 / b;
                ct = 0;
                dr = 0;
                ds = 0;
                dt = 1 / c;

                double[][] matrix = { { ar, as, at }, { br, bs, bt }, { cr, cs, ct }, { dr, ds, dt } };

                matrixJ = matrix;
            } else if (nodes == 8) {
                double ar, br, cr, dr, er, fr, gr, hr;
                double as, bs, cs, ds, es, fs, gs, hs;
                double at, bt, ct, dt, et, ft, gt, ht;

                ar = -1 / (a * b * c) * (0.5 * b - s) * (0.5 * c - t);
                as = -1 / (a * b * c) * (0.5 * a - r) * (0.5 * c - t);
                at = -1 / (a * b * c) * (0.5 * a - r) * (0.5 * b - s);
                br = 1 / (a * b * c) * (0.5 * b - s) * (0.5 * c - t);
                bs = -1 / (a * b * c) * (0.5 * a + r) * (0.5 * c - t);
                bt = -1 / (a * b * c) * (0.5 * a + r) * (0.5 * b - s);
                cr = 1 / (a * b * c) * (0.5 * b + s) * (0.5 * c - t);
                cs = 1 / (a * b * c) * (0.5 * a + r) * (0.5 * c - t);
                ct = -1 / (a * b * c) * (0.5 * a + r) * (0.5 * b + s);
                dr = -1 / (a * b * c) * (0.5 * b + s) * (0.5 * c - t);
                ds = 1 / (a * b * c) * (0.5 * a - r) * (0.5 * c - t);
                dt = -1 / (a * b * c) * (0.5 * a - r) * (0.5 * b + s);
                er = -1 / (a * b * c) * (0.5 * b - s) * (0.5 * c + t);
                es = -1 / (a * b * c) * (0.5 * a - r) * (0.5 * c + t);
                et = 1 / (a * b * c) * (0.5 * a - r) * (0.5 * b - s);
                fr = 1 / (a * b * c) * (0.5 * b - s) * (0.5 * c + t);
                fs = -1 / (a * b * c) * (0.5 * a + r) * (0.5 * c + t);
                ft = 1 / (a * b * c) * (0.5 * a + r) * (0.5 * b - s);
                gr = 1 / (a * b * c) * (0.5 * b + s) * (0.5 * c + t);
                gs = 1 / (a * b * c) * (0.5 * a + r) * (0.5 * c + t);
                gt = 1 / (a * b * c) * (0.5 * a + r) * (0.5 * b + s);
                hr = -1 / (a * b * c) * (0.5 * b + s) * (0.5 * c + t);
                hs = 1 / (a * b * c) * (0.5 * a - r) * (0.5 * c + t);
                ht = 1 / (a * b * c) * (0.5 * a - r) * (0.5 * b + s);

                double[][] matrix = {
                    { ar, as, at },
                    { br, bs, bt },
                    { cr, cs, ct },
                    { dr, ds, dt },
                    { er, es, et },
                    { fr, fs, ft },
                    { gr, gs, gt },
                    { hr, hs, ht },
                };

                matrixJ = matrix;
            } else {
                matrixJ = null;
            }
        } else {
            matrixJ = null;
        }

        return matrixJ;
    }
}
