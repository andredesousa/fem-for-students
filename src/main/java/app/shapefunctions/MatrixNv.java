/*
 * Esta classe fornece as funções de forma dos elementos finitos
 * A classe requer os parâmetros relativos à geometria e aos pontos de cálculo
 * A matriz Nv é avaliada consoante o tipo de elemento finito
 */

package app.shapefunctions;

import static app.shapefunctions.MatrixNv_1D.matrixNv_1D;
import static app.shapefunctions.MatrixNv_2D.matrixNv_2D;
import static app.shapefunctions.MatrixNv_3D.matrixNv_3D;
import static app.shapefunctions.MatrixNv_Beams.matrixNv_EulerBernoulli;
import static app.shapefunctions.MatrixNv_Beams.matrixNv_Timoshenko;
import static app.shapefunctions.MatrixNv_Slabs.matrixNv_Kirchhoff;
import static app.shapefunctions.MatrixNv_Slabs.matrixNv_ReissnerMindlin;

import java.awt.geom.Point2D;

/**
 *
 * @author André de Sousa
 */
public class MatrixNv {

    private final String type;
    private final String theory;
    private final int nodes;
    private final double[] dimensions;
    private final double[] coordinates;

    /**
     * Método construtor da classe MatrixNv
     *
     * @param type
     * @param theory
     * @param nodes
     * @param dimensions
     * @param coordinates
     */
    public MatrixNv(String type, String theory, int nodes, double[] dimensions, double[] coordinates) {
        this.type = type;
        this.theory = theory;
        this.nodes = nodes;
        this.dimensions = dimensions;
        this.coordinates = coordinates;
    }

    /**
     * Método construtor da classe MatrixNv
     *
     * @param type
     * @param nodes
     */
    public MatrixNv(String type, int nodes) {
        this.type = type;
        this.theory = "";
        this.nodes = nodes;
        this.dimensions = null;
        this.coordinates = null;
    }

    /**
     * Método para obter as funções de forma do elemento finito
     *
     * @return
     */
    public double[][] getShapeFunctions() {
        return shapeFunctions();
    }

    /**
     * Método para obter as funções de forma do elemento finito
     *
     * @param point
     * @param nodesCoordinates
     * @return
     */
    public double[][] getShapeFunctions(Point2D.Double point, double[][] nodesCoordinates) {
        double[][] matrixNv;

        switch (type) {
            case "2D":
                matrixNv = matrixNv_2D(point.x, point.y, nodesCoordinates, nodes);
                break;
            default:
                matrixNv = null;
                break;
        }

        return matrixNv;
    }

    /**
     * Método que retorna as funções de forma do elemento finito
     *
     * @return
     */
    private double[][] shapeFunctions() {
        double[][] shapeFunctions;

        double L = dimensions[0];
        double a = dimensions[1];
        double b = dimensions[2];
        double c = dimensions[3];

        double x = coordinates[0];
        double y = coordinates[1];
        double z = coordinates[2];

        switch (type) {
            case "1D":
                shapeFunctions = matrixNv_1D(x, L, nodes);
                break;
            case "2D":
                shapeFunctions = matrixNv_2D(x, y, a, b, nodes);
                break;
            case "3D":
                shapeFunctions = matrixNv_3D(x, y, z, a, b, c, nodes);
                break;
            case "Beams":
                switch (theory) {
                    case "Euler-Bernoulli":
                        shapeFunctions = matrixNv_EulerBernoulli(x, L, nodes);
                        break;
                    case "Timoshenko":
                        shapeFunctions = matrixNv_Timoshenko(x, L, nodes);
                        break;
                    default:
                        shapeFunctions = null;
                        break;
                }
                break;
            case "Frames":
                shapeFunctions = null;
                break;
            case "Grids":
                shapeFunctions = null;
                break;
            case "Slabs":
                switch (theory) {
                    case "Reissner-Mindlin":
                        shapeFunctions = matrixNv_ReissnerMindlin(x, y, a, b, nodes);
                        break;
                    case "Kirchhoff":
                        shapeFunctions = matrixNv_Kirchhoff(x, y, a, b, nodes);
                        break;
                    default:
                        shapeFunctions = null;
                        break;
                }
                break;
            default:
                shapeFunctions = null;
                break;
        }

        return shapeFunctions;
    }
}
