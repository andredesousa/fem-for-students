/*
 * Esta classe fornece as derivadas das funções de forma para substituição de variável
 * A classe requer os parâmetros relativos à geometria e aos pontos de cálculo
 * As derivadas são avaliadas consoante o tipo de elemento finito
 */

package app.variablesubstitution;

import static app.variablesubstitution.DerivedMatrix_1D.derivedMatrix_1D;
import static app.variablesubstitution.DerivedMatrix_2D.derivedMatrix_2D;
import static app.variablesubstitution.DerivedMatrix_3D.derivedMatrix_3D;
import static app.variablesubstitution.DerivedMatrix_Beams.derivedMatrix_EulerBernoulli;
import static app.variablesubstitution.DerivedMatrix_Slabs.derivedMatrix_ReissnerMindlin;

/**
 *
 * @author André de Sousa
 */
public class DerivedMatrix {

    /**
     * Método para obter as derivadas das funções de forma
     *
     * @param type
     * @param theory
     * @param pointsCoordinates
     * @param dimensions
     * @param nodes
     * @return
     */
    public static double[][] derivedMatrix(String type, String theory, double[] pointsCoordinates, double[] dimensions, int nodes) {
        double[][] derivedMatrix;

        double L = dimensions[0];
        double a = dimensions[1];
        double b = dimensions[2];
        double c = dimensions[3];

        double pointX, pointY, pointZ;

        switch (type) {
            case "1D":
                pointX = pointsCoordinates[1];
                derivedMatrix = derivedMatrix_1D(pointX, L, nodes);
                break;
            case "2D":
                pointX = pointsCoordinates[1];
                pointY = pointsCoordinates[3];
                derivedMatrix = derivedMatrix_2D(pointX, pointY, a, b, nodes);
                break;
            case "3D":
                pointX = pointsCoordinates[1];
                pointY = pointsCoordinates[3];
                pointZ = pointsCoordinates[5];
                derivedMatrix = derivedMatrix_3D(pointX, pointY, pointZ, a, b, c, nodes);
                break;
            case "Beams":
                pointX = pointsCoordinates[1];
                derivedMatrix = derivedMatrix_EulerBernoulli(pointX, L, nodes);
                break;
            case "Frames":
                derivedMatrix = null;
                break;
            case "Grids":
                derivedMatrix = null;
                break;
            case "Slabs":
                pointX = pointsCoordinates[1];
                pointY = pointsCoordinates[3];
                derivedMatrix = derivedMatrix_ReissnerMindlin(pointX, pointY, a, b, nodes);
                break;
            default:
                derivedMatrix = null;
                break;
        }

        return derivedMatrix;
    }
}
