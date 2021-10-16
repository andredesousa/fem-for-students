/*
 * Esta classe fornece as derivadas das funções de forma de um elemento finito
 * O método requer a coordenada r, s e o número de nós do elemento finito
 * A matriz deve ser posteriormente multiplicada pelas coordenadas cartesianas
 */

package app.variablesubstitution;

/**
 *
 * @author André de Sousa
 */
public class DerivedMatrix_2D {

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param r é a coordenada local segundo o eiro r
     * @param s é a coordenada local segundo o eiro s
     * @param a é o comprimento do elemento finito retangular
     * @param b é a altura do elemento finito retangular
     * @param nodes é o número de nós do elemento finito
     * @return
     */
    public static double[][] derivedMatrix_2D(double r, double s, double a, double b, int nodes) {
        double[][] matrixJ;

        if (a != 0 && b != 0 && nodes > 1) {
            if (nodes == 3) {
                double ar, as, br, bs, cr, cs;

                ar = -1 / a;
                as = -1 / b;
                br = 1 / a;
                bs = 0;
                cr = 0;
                cs = 1 / b;

                double[][] matrix = { { ar, as }, { br, bs }, { cr, cs } };

                matrixJ = matrix;
            } else if (nodes == 4) {
                double ar, as, br, bs, cr, cs, dr, ds;

                ar = -1 / (2 * a) + s / (a * b);
                as = -1 / (2 * b) + r / (a * b);
                br = 1 / (2 * a) - s / (a * b);
                bs = -1 / (2 * b) - r / (a * b);
                cr = 1 / (2 * a) + s / (a * b);
                cs = 1 / (2 * b) + r / (a * b);
                dr = -1 / (2 * a) - s / (a * b);
                ds = 1 / (2 * b) - r / (a * b);

                double[][] matrix = { { ar, as }, { br, bs }, { cr, cs }, { dr, ds } };

                matrixJ = matrix;
            } else if (nodes == 6) {
                double ar, br, cr, dr, er, fr;
                double as, bs, cs, ds, es, fs;

                ar = (4 * a * s + 4 * b * r - 3 * a * b) / (a * a * b);
                as = (4 * a * s + 4 * b * r - 3 * a * b) / (a * b * b);
                br = -(4 * (a * s + 2 * b * r - a * b)) / (a * a * b);
                bs = -(4 * r) / (a * b);
                cr = (4 * r - a) / (a * a);
                cs = 0;
                dr = (4 * s) / (a * b);
                ds = (4 * r) / (a * b);
                er = 0;
                es = (4 * s - b) / (b * b);
                fr = -(4 * s) / (a * b);
                fs = -(4 * (2 * a * s + b * r - a * b)) / (a * b * b);

                double[][] matrix = { { ar, as }, { br, bs }, { cr, cs }, { dr, ds }, { er, es }, { fr, fs } };

                matrixJ = matrix;
            } else if (nodes == 8) {
                double ar, br, cr, dr, er, fr, gr, hr;
                double as, bs, cs, ds, es, fs, gs, hs;

                ar = -((2 * s - b) * (a * s + 2 * b * r)) / (a * a * b * b);
                as = -((2 * r - a) * (2 * a * s + b * r)) / (a * a * b * b);
                br = (4 * r * (2 * s - b)) / (a * a * b);
                bs = ((2 * r - a) * (2 * r + a)) / (a * a * b);
                cr = ((2 * s - b) * (a * s - 2 * b * r)) / (a * a * b * b);
                cs = ((2 * r + a) * (2 * a * s - b * r)) / (a * a * b * b);
                dr = -((2 * s - b) * (2 * s + b)) / (a * b * b);
                ds = -(4 * (2 * r + a) * s) / (a * b * b);
                er = ((2 * s + b) * (a * s + 2 * b * r)) / (a * a * b * b);
                es = ((2 * r + a) * (2 * a * s + b * r)) / (a * a * b * b);
                fr = -(4 * r * (2 * s + b)) / (a * a * b);
                fs = -((2 * r - a) * (2 * r + a)) / (a * a * b);
                gr = -((2 * s + b) * (a * s - 2 * b * r)) / (a * a * b * b);
                gs = -((2 * r - a) * (2 * a * s - b * r)) / (a * a * b * b);
                hr = ((2 * s - b) * (2 * s + b)) / (a * b * b);
                hs = (4 * (2 * r - a) * s) / (a * b * b);

                double[][] matrix = { { ar, as }, { br, bs }, { cr, cs }, { dr, ds }, { er, es }, { fr, fs }, { gr, gs }, { hr, hs } };

                matrixJ = matrix;
            } else if (nodes == 9) {
                double ar, br, cr, dr, er, fr, gr, hr, ir;
                double as, bs, cs, ds, es, fs, gs, hs, is;

                ar = s / (a * a * b * b) * (4 * r - a) * (2 * s - b);
                as = r / (a * a * b * b) * (2 * r - a) * (4 * s - b);
                br = -(8 * r * s) / (a * a * b * b) * (2 * s - b);
                bs = -1 / (a * a * b * b) * (4 * r * r - a * a) * (4 * s - b);
                cr = s / (a * a * b * b) * (4 * r + a) * (2 * s - b);
                cs = r / (a * a * b * b) * (2 * r + a) * (4 * s - b);
                dr = -1 / (a * a * b * b) * (4 * r + a) * (4 * s * s - b * b);
                ds = -(8 * r * s) / (a * a * b * b) * (2 * r + a);
                er = s / (a * a * b * b) * (4 * r + a) * (2 * s + b);
                es = r / (a * a * b * b) * (2 * r + a) * (4 * s + b);
                fr = -(8 * r * s) / (a * a * b * b) * (2 * s + b);
                fs = -1 / (a * a * b * b) * (4 * r * r - a * a) * (4 * s + b);
                gr = s / (a * a * b * b) * (4 * r - a) * (2 * s + b);
                gs = r / (a * a * b * b) * (2 * r - a) * (4 * s + b);
                hr = -1 / (a * a * b * b) * (4 * r - a) * (4 * s * s - b * b);
                hs = -(8 * r * s) / (a * a * b * b) * (2 * r - a);
                ir = (8 * r) / (a * a * b * b) * (4 * s * s - b * b);
                is = (8 * s) / (a * a * b * b) * (4 * r * r - a * a);

                double[][] matrix = {
                    { ar, as },
                    { br, bs },
                    { cr, cs },
                    { dr, ds },
                    { er, es },
                    { fr, fs },
                    { gr, gs },
                    { hr, hs },
                    { ir, is },
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
