/*
 * Esta classe fornece os pesos e as coordenadas para a quadratura de Gauss-Lobatto
 * A classe requer os parâmetros relativos à geometria do elemento finito
 * As coordenadas são definidas em função da geometria do elemento finito
 */

package app.gausslobatto;

import static app.gausslobatto.WeightsCoordinates_1D.weightsCoordinates_1D;
import static app.gausslobatto.WeightsCoordinates_2D.weightsCoordinates_Quadrilaterals;
import static app.gausslobatto.WeightsCoordinates_2D.weightsCoordinates_Triangles;
import static app.gausslobatto.WeightsCoordinates_3D.weightsCoordinates_3D;
import static app.gausslobatto.WeightsCoordinates_Beams.weightsCoordinates_EulerBernoulli;
import static app.gausslobatto.WeightsCoordinates_Beams.weightsCoordinates_Timoshenko;
import static app.gausslobatto.WeightsCoordinates_Frames.weightsCoordinates_Frames;
import static app.gausslobatto.WeightsCoordinates_Grids.weightsCoordinates_Grids;
import static app.gausslobatto.WeightsCoordinates_Slabs.weightsCoordinates_Kirchhoff;
import static app.gausslobatto.WeightsCoordinates_Slabs.weightsCoordinates_ReissnerMindlin;

/**
 *
 * @author André de Sousa
 */
public class GaussLobatto {

    private final String type;
    private final String theory;
    private final String shape;
    private final int points;
    private final double[] dimensions;

    /**
     * Método construtor da classe GaussLobatto
     *
     * @param type
     * @param theory
     * @param points
     * @param dimensions
     */
    public GaussLobatto(String type, String theory, int points, double[] dimensions) {
        this.type = type;
        this.theory = theory;
        this.shape = "";
        this.points = points;
        this.dimensions = dimensions;
    }

    /**
     * Método construtor da classe GaussLobatto
     *
     * @param type
     * @param theory
     * @param shape
     * @param points
     * @param dimensions
     */
    public GaussLobatto(String type, String theory, String shape, int points, double[] dimensions) {
        this.type = type;
        this.theory = theory;
        this.shape = shape;
        this.points = points;
        this.dimensions = dimensions;
    }

    /**
     * Método para obter as coordenadas e pesos da quadratura de Gauss-Lobatto
     *
     * @return
     */
    public double[][] getWeightsCoordinates() {
        return weightsCoordinates();
    }

    /**
     * Método que retorna as coordenadas e pesos da quadratura de Gauss-Lobatto
     *
     * @return
     */
    private double[][] weightsCoordinates() {
        double[][] weightsCoordinates;

        double L = dimensions[0];
        double a = dimensions[1];
        double b = dimensions[2];
        double c = dimensions[3];

        switch (type) {
            case "1D":
                weightsCoordinates = weightsCoordinates_1D(L, points);
                break;
            case "2D":
                if ("Triangle".equals(shape)) {
                    weightsCoordinates = weightsCoordinates_Triangles(a, b, points);
                } else {
                    weightsCoordinates = weightsCoordinates_Quadrilaterals(a, b, points);
                }
                break;
            case "3D":
                weightsCoordinates = weightsCoordinates_3D(a, b, c, points);
                break;
            case "Beams":
                switch (theory) {
                    case "Euler-Bernoulli":
                        weightsCoordinates = weightsCoordinates_EulerBernoulli(L, points);
                        break;
                    case "Timoshenko":
                        weightsCoordinates = weightsCoordinates_Timoshenko(L, points);
                        break;
                    default:
                        weightsCoordinates = null;
                        break;
                }
                break;
            case "Frames":
                weightsCoordinates = weightsCoordinates_Frames(L, points);
                break;
            case "Grids":
                weightsCoordinates = weightsCoordinates_Grids(L, points);
                break;
            case "Slabs":
                switch (theory) {
                    case "Reissner-Mindlin":
                        weightsCoordinates = weightsCoordinates_ReissnerMindlin(a, b, points);
                        break;
                    case "Kirchhoff":
                        weightsCoordinates = weightsCoordinates_Kirchhoff(a, b, points);
                        break;
                    default:
                        weightsCoordinates = null;
                        break;
                }
                break;
            default:
                weightsCoordinates = null;
                break;
        }

        return weightsCoordinates;
    }
}
