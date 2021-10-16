/*
 * Esta classe fornece a matriz D de um elemento finito
 * Os métodos requerem os parâmetros E, G e v relativos ao tipo de material
 * A matriz D é avaliada consoante o tipo de elemento finito
 */

package app.finiteelement;

/**
 *
 * @author André de Sousa
 */
public class MatrixD {

    /**
     * Método para obter a matriz D para um estado plano de tensão
     *
     * @param E é o módulo de elasticidade do material
     * @param v é o coeficiente de Poisson do material
     * @return
     */
    public static double[][] matrixD_PlaneStress(double E, double v) {
        double a, b, c;

        a = E / (1 - (v * v));
        b = E * v / (1 - (v * v));
        c = E / (2 * (1 + v));

        double[][] matrix = { { a, b, 0 }, { b, a, 0 }, { 0, 0, c } };

        return matrix;
    }

    /**
     * Método para obter a matriz D para um estado plano de deformação
     *
     * @param E é o módulo de elasticidade do material
     * @param v é o coeficiente de Poisson do material
     * @return
     */
    public static double[][] matrixD_PlaneStrain(double E, double v) {
        double a, b, c;

        a = (E * (v - 1)) / ((v + 1) * (2 * v - 1));
        b = (-E * v) / ((v + 1) * (2 * v - 1));
        c = E / (2 * (v + 1));

        double[][] matrix = { { a, b, 0 }, { b, a, 0 }, { 0, 0, c } };

        return matrix;
    }

    /**
     * Método para obter a matriz D para o caso tridimensional
     *
     * @param E é o módulo de elasticidade do material
     * @param v é o coeficiente de Poisson do material
     * @return
     */
    public static double[][] matrixD_3D(double E, double v) {
        double a, b, c;

        a = (E * (1 - v)) / ((1 + v) * (1 - 2 * v));
        b = (E * v) / ((1 + v) * (1 - 2 * v));
        c = E / (2 * (1 + v));

        double[][] matrix = {
            { a, b, b, 0, 0, 0 },
            { b, a, b, 0, 0, 0 },
            { b, b, a, 0, 0, 0 },
            { 0, 0, 0, c, 0, 0 },
            { 0, 0, 0, 0, c, 0 },
            { 0, 0, 0, 0, 0, c },
        };

        return matrix;
    }

    /**
     * Método para obter a matriz D para uma barra reticulada
     *
     * @param E é o módulo de elasticidade do material
     * @return
     */
    public static double[][] matrixD_Frames(double E) {
        double[][] matrix = { { E, 0 }, { 0, E } };

        return matrix;
    }

    /**
     * Método para obter a matriz D para uma grelha
     *
     * @param E é o módulo de elasticidade do material
     * @param G é o módulo de distorção do material
     * @return
     */
    public static double[][] matrixD_Grids(double E, double G) {
        double[][] matrix = { { G, 0 }, { 0, E } };

        return matrix;
    }

    /**
     * Método para obter a matriz D para lajes pela teoria de Reissner-Mindlin
     *
     * @param E é o módulo de elasticidade do material
     * @param G é o módulo de distorção do material
     * @param v é o coeficiente de Poisson do material
     * @return
     */
    public static double[][] matrixD_ReissnerMindlin(double E, double G, double v) {
        double a, b, c, d;

        a = E / (1 - (v * v));
        b = E * v / (1 - (v * v));
        c = E / (2 * (1 + v));
        d = (5.0 / 6.0) * G;

        double[][] matrix = { { a, b, 0, 0, 0 }, { b, a, 0, 0, 0 }, { 0, 0, c, 0, 0 }, { 0, 0, 0, d, 0 }, { 0, 0, 0, 0, d } };

        return matrix;
    }

    /**
     * Método para obter a matriz D para lajes pela teoria de Kirchhoff
     *
     * @param E é o módulo de elasticidade do material
     * @param G é o módulo de distorção do material
     * @param v é o coeficiente de Poisson do material
     * @return
     */
    public static double[][] matrixD_Kirchhoff(double E, double G, double v) {
        double a, b, c;

        a = E / (1 - (v * v));
        b = E * v / (1 - (v * v));
        c = G;

        double[][] matrix = { { a, b, 0 }, { b, a, 0 }, { 0, 0, c } };

        return matrix;
    }
}
