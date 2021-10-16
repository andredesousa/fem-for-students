/*
 * Esta classe fornece a matriz J de um elemento finito
 * A classe requer os parâmetros relativos à geometria e os pontos de cálculo
 * A matriz J é avaliada consoante o tipo de elemento finito
 */

package app.finiteelement;

import static app.matrices.Multiply.multiply;
import static app.matrices.Transpose.transpose;
import static app.variablesubstitution.DerivedMatrix_1D.derivedMatrix_1D;
import static app.variablesubstitution.DerivedMatrix_2D.derivedMatrix_2D;
import static app.variablesubstitution.DerivedMatrix_3D.derivedMatrix_3D;
import static app.variablesubstitution.DerivedMatrix_Beams.derivedMatrix_EulerBernoulli;

import app.variablesubstitution.DerivedMatrix_Beams;

/**
 *
 * @author André de Sousa
 */
public class MatrixJ {

    private final String type;
    private final double[][] nodesCoordinates;
    private final double[] pointsCoordinates;

    /**
     * Método construtor da classe MatrixJ
     *
     * @param type
     * @param nodesCoordinates
     * @param pointsCoordinates
     */
    public MatrixJ(String type, double[][] nodesCoordinates, double[] pointsCoordinates) {
        this.type = type;
        this.nodesCoordinates = nodesCoordinates;
        this.pointsCoordinates = pointsCoordinates;
    }

    /**
     * Método para obter o determinate jacobiano de um elemento finito unidimensional
     *
     * @param nodes
     * @param theory
     * @return
     */
    public double getJacobian(int nodes, String theory) {
        double jacobian;

        switch (this.type) {
            case "1D":
                jacobian = jacobianMatrix_1D(nodes);
                break;
            case "Beams":
                jacobian = jacobianMatrix_Beams(nodes, theory);
                break;
            default:
                jacobian = 0;
                break;
        }

        return jacobian;
    }

    /**
     * Método para obter a matriz jacobiana de elementos bi e tridimensionais
     *
     * @param nodes
     * @param theory
     * @return
     */
    public double[][] getJacobianMatrix(int nodes, String theory) {
        double[][] matrixJ;

        switch (this.type) {
            case "2D":
                matrixJ = jacobianMatrix_2D(nodes);
                break;
            case "3D":
                matrixJ = jacobianMatrix_3D(nodes);
                break;
            case "Slabs":
                matrixJ = jacobianMatrix_Slabs(nodes, theory);
                break;
            default:
                matrixJ = null;
                break;
        }

        return matrixJ;
    }

    /**
     * Este método cria o Jacobiano de um elemento finito unidimensional
     *
     * @param nodes
     * @return
     */
    private double jacobianMatrix_1D(int nodes) {
        double scalarJ;

        try {
            double[][] matrixJ;
            double pointX = pointsCoordinates[1];
            matrixJ = derivedMatrix_1D(pointX, 2, nodes);
            matrixJ = multiply(transpose(nodesCoordinates), matrixJ);

            scalarJ = matrixJ[0][0];
        } catch (Exception e) {
            scalarJ = 0.0;
        }

        return scalarJ;
    }

    /**
     * Este método cria a matriz Jacobiana de um elemento finito bidimensional
     *
     * @param nodes
     * @return
     */
    private double[][] jacobianMatrix_2D(int nodes) {
        double[][] matrixJ;

        try {
            double pointX = pointsCoordinates[1];
            double pointY = pointsCoordinates[3];
            matrixJ = derivedMatrix_2D(pointX, pointY, 2, 2, nodes);
            matrixJ = multiply(transpose(nodesCoordinates), matrixJ);
        } catch (Exception e) {
            matrixJ = null;
        }

        return matrixJ;
    }

    /**
     * Este método cria a matriz Jacobiana de um elemento finito tridimensional
     *
     * @param nodes
     * @return
     */
    private double[][] jacobianMatrix_3D(int nodes) {
        double[][] matrixJ;

        try {
            double pointX = pointsCoordinates[1];
            double pointY = pointsCoordinates[3];
            double pointZ = pointsCoordinates[5];
            matrixJ = derivedMatrix_3D(pointX, pointY, pointZ, 2, 2, 2, nodes);
            matrixJ = multiply(transpose(nodesCoordinates), matrixJ);
        } catch (Exception e) {
            matrixJ = null;
        }

        return matrixJ;
    }

    /**
     * Este método cria a matriz Jacobiana de um elemento finito de viga
     *
     * @param nodes
     * @param theory
     * @return
     */
    private double jacobianMatrix_Beams(int nodes, String theory) {
        double scalarJ;

        switch (theory) {
            case "Euler-Bernoulli":
                try {
                    double[][] matrixJ;
                    double pointX = pointsCoordinates[1];
                    matrixJ = derivedMatrix_EulerBernoulli(pointX, 2, nodes);

                    matrixJ = multiply(transpose(nodesCoordinates), matrixJ);
                    scalarJ = matrixJ[0][0];
                } catch (Exception e) {
                    scalarJ = 0.0;
                }
                break;
            case "Timoshenko":
                try {
                    double[][] matrixJb, matrixJs;
                    double pointX = pointsCoordinates[1];
                    matrixJb = DerivedMatrix_Beams.derivedMatrixBb_Timoshenko(pointX, 2, nodes);
                    matrixJs = DerivedMatrix_Beams.derivedMatrixBs_Timoshenko(pointX, 2, nodes);

                    matrixJb = multiply(transpose(nodesCoordinates), matrixJb);
                    matrixJs = multiply(transpose(nodesCoordinates), matrixJs);
                    scalarJ = (matrixJb[0][0] + matrixJs[0][0]) / 2;
                } catch (Exception e) {
                    scalarJ = 0.0;
                }
                break;
            default:
                scalarJ = 0.0;
                break;
        }

        return scalarJ;
    }

    /**
     * Este método cria a matriz Jacobiana de um elemento finito de barra
     *
     * @param nodes
     * @return
     */
    private double[][] jacobianMatrix_Frames(int nodes) {
        return null;
    }

    /**
     * Este método cria a matriz Jacobiana de um elemento finito de grelha
     *
     * @param nodes
     * @return
     */
    private double[][] jacobianMatrix_Grids(int nodes) {
        return null;
    }

    /**
     * Este método cria a matriz Jacobiana de um elemento finito de laje
     *
     * @param nodes
     * @param theory
     * @return
     */
    private double[][] jacobianMatrix_Slabs(int nodes, String theory) {
        return null;
    }
}
