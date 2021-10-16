/*
 * Esta classe é responsável pela criação das matrizes de rigidez
 * A classe requer os parâmetros relativos à geometria dos elementos finitos
 * A matriz de rigidez é calculada pelo método específico
 */

package app.finiteelement;

import static app.finiteelement.MatrixD.matrixD_3D;
import static app.finiteelement.MatrixD.matrixD_Kirchhoff;
import static app.finiteelement.MatrixD.matrixD_PlaneStrain;
import static app.finiteelement.MatrixD.matrixD_PlaneStress;
import static app.finiteelement.MatrixD.matrixD_ReissnerMindlin;
import static app.finiteelement.MatrixK_1D.matrixK_1D;
import static app.finiteelement.MatrixK_2D.matrixK_2D;
import static app.finiteelement.MatrixK_3D.matrixK_3D;
import static app.finiteelement.MatrixK_Beams.matrixK_EulerBernoulli;
import static app.finiteelement.MatrixK_Beams.matrixKb_Timoshenko;
import static app.finiteelement.MatrixK_Beams.matrixKs_Timoshenko;
import static app.finiteelement.MatrixK_Frames.matrixK_Frames;
import static app.finiteelement.MatrixK_Grids.matrixK_Grids;
import static app.finiteelement.MatrixK_Slabs.matrixK_Kirchhoff;
import static app.finiteelement.MatrixK_Slabs.matrixK_ReissnerMindlin;
import static app.matrices.Multiply.multiply;
import static app.matrices.Sum.sum;

/**
 *
 * @author André de Sousa
 */
public class MatrixK {

    /**
     * Método construtor da classe MatrixK
     *
     * @param elasticity
     * @param shear
     * @param poisson
     * @param inertia
     * @param torsion
     * @param area
     * @param thickness
     * @param type
     */
    public MatrixK(
        double elasticity,
        double shear,
        double poisson,
        double inertia,
        double torsion,
        double area,
        double thickness,
        String type
    ) {
        this.elasticity = elasticity;
        this.shear = shear;
        this.poisson = poisson;
        this.inertia = inertia;
        this.torsion = torsion;
        this.area = area;
        this.thickness = thickness;
        this.type = type;
    }

    //variáveis internas para o funcionamento da classe
    private double elasticity;
    private double shear;
    private double poisson;

    /**
     * Método para editar o material do elemento finito
     *
     * @param elasticity
     * @param shear
     * @param poisson
     */
    public void editMaterial(double elasticity, double shear, double poisson) {
        this.elasticity = elasticity;
        this.shear = shear;
        this.poisson = poisson;
    }

    //variáveis internas para o funcionamento da classe
    private double inertia;
    private double torsion;
    private double area;
    private double thickness;

    /**
     * Método para editar a secção do elemento finito
     *
     * @param inertia
     * @param torsion
     * @param area
     * @param thickness
     */
    public void editSection(double inertia, double torsion, double area, double thickness) {
        this.inertia = inertia;
        this.torsion = torsion;
        this.area = area;
        this.thickness = thickness;
    }

    //Variável para identificar o tipo de elemento finito
    String type;

    /**
     * Método para obter a matriz de rigidez de um elemento finito
     *
     * @param L é o comprimento do elemento finito
     * @param a é o comprimento do elemento finito
     * @param b é a largura do elemento finito
     * @param c é a altura do elemento finito
     * @param nodes é o número de nós do elemento finito
     * @param theory é a teoria de formulação do elemento finito
     * @return
     */
    public double[][] getStiffnessMatrix(double L, double a, double b, double c, int nodes, String theory) {
        double[][] matrixK;

        switch (this.type) {
            case "1D":
                matrixK = stiffnessMatrix_1D(L, nodes);
                break;
            case "2D":
                matrixK = stiffnessMatrix_2D(a, b, nodes, theory);
                break;
            case "3D":
                matrixK = stiffnessMatrix_3D(a, b, c, nodes);
                break;
            case "Beams":
                matrixK = stiffnessMatrix_Beams(L, nodes, theory);
                break;
            case "Frames":
                matrixK = stiffnessMatrix_Frames(L, nodes);
                break;
            case "Grids":
                matrixK = stiffnessMatrix_Grids(L, nodes);
                break;
            case "Slabs":
                matrixK = stiffnessMatrix_Slabs(a, b, nodes, theory);
                break;
            default:
                matrixK = null;
                break;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito unidimensional
     *
     * @param L
     * @param nodes
     * @return
     */
    private double[][] stiffnessMatrix_1D(double L, int nodes) {
        double[][] matrixK;

        try {
            matrixK = matrixK_1D(L, nodes);
            matrixK = multiply(elasticity * area, matrixK);
        } catch (Exception e) {
            matrixK = null;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito bidimensional
     *
     * @param a
     * @param b
     * @param nodes
     * @param theory
     * @return
     */
    private double[][] stiffnessMatrix_2D(double a, double b, int nodes, String theory) {
        double[][] matrixK;

        switch (theory) {
            case "Plane Stress":
                try {
                    double[][] matrixD = matrixD_PlaneStress(elasticity, poisson);
                    matrixK = matrixK_2D(a, b, nodes, matrixD);
                    matrixK = multiply(thickness, matrixK);
                } catch (Exception e) {
                    matrixK = null;
                }
                break;
            case "Plane Strain":
                try {
                    double[][] matrixD = matrixD_PlaneStrain(elasticity, poisson);
                    matrixK = matrixK_2D(a, b, nodes, matrixD);
                } catch (Exception e) {
                    matrixK = null;
                }
                break;
            default:
                matrixK = null;
                break;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito tridimensional
     *
     * @param a
     * @param b
     * @param c
     * @param nodes
     * @return
     */
    private double[][] stiffnessMatrix_3D(double a, double b, double c, int nodes) {
        double[][] matrixK;

        try {
            double[][] matrixD = matrixD_3D(elasticity, poisson);
            matrixK = matrixK_3D(a, b, c, nodes, matrixD);
        } catch (Exception e) {
            matrixK = null;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito de viga
     *
     * @param L
     * @param nodes
     * @param theory
     * @return
     */
    private double[][] stiffnessMatrix_Beams(double L, int nodes, String theory) {
        double[][] matrixK;

        switch (theory) {
            case "Euler-Bernoulli":
                try {
                    matrixK = matrixK_EulerBernoulli(L, nodes);
                    matrixK = multiply(elasticity * inertia, matrixK);
                } catch (Exception e) {
                    matrixK = null;
                }
                break;
            case "Timoshenko":
                try {
                    double[][] matrixKb = matrixKb_Timoshenko(L, nodes);
                    double[][] matrixKs = matrixKs_Timoshenko(L, nodes);

                    matrixKb = multiply(elasticity * inertia, matrixKb);
                    matrixKs = multiply(shear * (area * (5.0 / 6.0)), matrixKs);

                    matrixK = sum(matrixKb, matrixKs);
                } catch (Exception e) {
                    matrixK = null;
                }
                break;
            default:
                matrixK = null;
                break;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito de barra
     *
     * @param L
     * @param nodes
     * @return
     */
    private double[][] stiffnessMatrix_Frames(double L, int nodes) {
        double[][] matrixK;

        try {
            matrixK = matrixK_Frames(L, elasticity * area, elasticity * inertia, nodes);
        } catch (Exception e) {
            matrixK = null;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito de grelha
     *
     * @param L
     * @param nodes
     * @return
     */
    private double[][] stiffnessMatrix_Grids(double L, int nodes) {
        double[][] matrixK;

        try {
            matrixK = matrixK_Grids(L, elasticity * inertia, shear * torsion, nodes);
        } catch (Exception e) {
            matrixK = null;
        }

        return matrixK;
    }

    /**
     * Este método cria a matriz de rigidez de um elemento finito de laje
     *
     * @param a
     * @param b
     * @param nodes
     * @param theory
     * @return
     */
    private double[][] stiffnessMatrix_Slabs(double a, double b, int nodes, String theory) {
        double[][] matrixK;

        switch (theory) {
            case "Reissner-Mindlin":
                try {
                    double[][] matrixD = matrixD_ReissnerMindlin(elasticity, shear, poisson);
                    matrixD[0][0] = matrixD[0][0] * ((thickness * thickness * thickness) / 12.0);
                    matrixD[0][1] = matrixD[0][1] * ((thickness * thickness * thickness) / 12.0);
                    matrixD[1][0] = matrixD[1][0] * ((thickness * thickness * thickness) / 12.0);
                    matrixD[1][1] = matrixD[1][1] * ((thickness * thickness * thickness) / 12.0);
                    matrixD[2][2] = matrixD[2][2] * ((thickness * thickness * thickness) / 12.0);
                    matrixD[3][3] = matrixD[3][3] * thickness;
                    matrixD[4][4] = matrixD[4][4] * thickness;

                    matrixK = matrixK_ReissnerMindlin(a, b, nodes, matrixD);
                } catch (Exception e) {
                    matrixK = null;
                }
                break;
            case "Kirchhoff":
                try {
                    double[][] matrixD = matrixD_Kirchhoff(elasticity, shear, poisson);
                    matrixK = matrixK_Kirchhoff(a, b, nodes, matrixD);
                    matrixK = multiply((thickness * thickness * thickness) / 12.0, matrixK);
                } catch (Exception e) {
                    matrixK = null;
                }
                break;
            default:
                matrixK = null;
                break;
        }

        return matrixK;
    }
}
