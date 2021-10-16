/*
 * Esta classe fornece os resultados nodais para cada elemento finito
 * As tensões nodais são calculadas com as matrizes D e B e com o vetor de deslocamentos
 * As forças nodais são calculadas a partir da matriz B e do vetor de deslocamentos
 */

package app.calculations;

import static app.matrices.Inverse.inverse;
import static app.matrices.Multiply.multiply;

import app.finiteelement.MatrixD;
import app.finiteelement.MatrixJ;
import app.gausslegendre.GaussLegendre;
import app.gausslobatto.GaussLobatto;
import app.variablesubstitution.DerivedMatrix;
import app.variablesubstitution.MatrixB;
import java.util.ArrayList;

/**
 *
 * @author André de Sousa
 */
public class NodalResults {

    //Parâmetros caracterizadores do elemento finito
    private final String type;
    private final String theory;
    private final String shape;
    private final int nodes;

    /**
     * Método construtor da classe NodalResults
     *
     * @param type
     * @param theory
     * @param shape
     * @param nodes
     */
    public NodalResults(String type, String theory, String shape, int nodes) {
        this.type = type;
        this.theory = theory;
        this.shape = shape;
        this.nodes = nodes;
    }

    //Secção e material do elemento finito
    private double inertia, torsion, area, thickness;
    private double elasticity, shear, poisson;

    /**
     * Método para definir a secção do elemento finito
     *
     * @param inertia é a inercia da secção transversal
     * @param torsion é a inércia de torção da secção transversal
     * @param area é a área de um elementos finito
     * @param thickness é a espessura de um elemento finito
     */
    public void setSection(double inertia, double torsion, double area, double thickness) {
        this.inertia = inertia;
        this.torsion = torsion;
        this.area = area;
        this.thickness = thickness;
    }

    /**
     * Método para definir o meterial do elemento finito
     *
     * @param elasticity é o módulo de elasticidade do material
     * @param shear é o módulo de distorção do material
     * @param poisson é o coeficiente de Poisson do material
     */
    public void setMaterial(double elasticity, double shear, double poisson) {
        this.elasticity = elasticity;
        this.shear = shear;
        this.poisson = poisson;
    }

    /**
     * Método para calcular os esforços nodais no caso unidimensional
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalForces_1D(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalForces = new ArrayList();

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
            double[][] forces = multiply(multiply(area * elasticity, matrixB), displacementVector);
            nodalForces.add(forces);

            n++;
        }

        return nodalForces;
    }

    /**
     * Método para calcular os esforços nodais no caso bidimensional
     *
     * @param a
     * @param b
     * @param displacementVector
     * @param nodesCoordinates
     * @return
     */
    public ArrayList<double[][]> nodalForces_2D(double a, double b, double[][] displacementVector, double[][] nodesCoordinates) {
        ArrayList<double[][]> nodalForces = new ArrayList();

        //Cálculo da matriz de elasticidade
        double[][] matrixD;
        if ("Plane Stress".equals(theory)) {
            matrixD = MatrixD.matrixD_PlaneStress(elasticity, poisson);
            matrixD = multiply(thickness, matrixD);
        } else {
            matrixD = MatrixD.matrixD_PlaneStrain(elasticity, poisson);
        }

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(0, a, b, 0);
        double[] dimensions = { 0, a, b, 0 };

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            double[] pointsCoordinates = weightsCoordinates[n];
            double[][] matrixB, forces;

            switch (shape) {
                case "Rectangle":
                    double pointX = pointsCoordinates[1];
                    double pointY = pointsCoordinates[3];
                    double pointZ = 0;

                    app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, 0, a, b, 0);
                    matrixB = matrix.getMatrixB(pointX, pointY, pointZ, nodes, theory);

                    forces = multiply(multiply(matrixD, matrixB), displacementVector);
                    nodalForces.add(forces);
                    break;
                default:
                    //Matriz jacobiana no ponto de integração (i, j)
                    MatrixJ jacobianMatrix = new MatrixJ(type, nodesCoordinates, pointsCoordinates);
                    double[][] matrixJ = jacobianMatrix.getJacobianMatrix(nodes, theory);

                    //Inversa da matriz Jacobiana
                    double[][] inverseJ = inverse(matrixJ);

                    //Cálculo da matriz das derivadas das funções de forma
                    double[][] derivedMatrix = DerivedMatrix.derivedMatrix(type, theory, pointsCoordinates, dimensions, nodes);

                    //Cálculo dos termos da matriz B
                    double[][] derivedMatrixB = multiply(derivedMatrix, inverseJ);

                    //Cálculo da matriz B do elemento finito
                    matrixB = MatrixB.matrixB(type, theory, derivedMatrixB, nodes);

                    forces = multiply(multiply(matrixD, matrixB), displacementVector);
                    nodalForces.add(forces);
                    break;
            }

            n++;
        }

        return nodalForces;
    }

    /**
     * Método para calcular os esforços nodais em vigas
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalForces_Beams(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalForces = new ArrayList();

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[][] matrixB, forces, M, V;
            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            switch (theory) {
                case "Timoshenko":
                    double[][] matrixBb = matrix.getMatrixBb(point, nodes, theory);
                    double[][] matrixBs = matrix.getMatrixBs(point, nodes, theory);
                    M = multiply(multiply(elasticity * inertia, matrixBb), displacementVector);
                    V = multiply(multiply(shear * (area * (5.0 / 6.0)), matrixBs), displacementVector);

                    forces = new double[2][1];
                    forces[0][0] = M[0][0];
                    forces[1][0] = V[0][0];
                    nodalForces.add(forces);
                    break;
                case "Euler-Bernoulli":
                    matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
                    M = multiply(multiply(elasticity * inertia, matrixB), displacementVector);

                    forces = new double[2][1];
                    forces[0][0] = M[0][0];
                    nodalForces.add(forces);
                    break;
            }

            n++;
        }

        return nodalForces;
    }

    /**
     * Método para calcular os esforços nodais em barras
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalForces_Frames(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalForces = new ArrayList();

        double[][] matrixD = MatrixD.matrixD_Frames(elasticity);
        matrixD[0][0] = matrixD[0][0] * area;
        matrixD[1][1] = matrixD[1][1] * inertia;

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
            double[][] forces = multiply(multiply(matrixD, matrixB), displacementVector);
            nodalForces.add(forces);

            n++;
        }

        return nodalForces;
    }

    /**
     * Método para calcular os esforços nodais em grelhas
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalForces_Grids(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalForces = new ArrayList();

        double[][] matrixD = MatrixD.matrixD_Grids(elasticity, shear);
        matrixD[0][0] = matrixD[0][0] * torsion;
        matrixD[1][1] = matrixD[1][1] * inertia;

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
            double[][] forces = multiply(multiply(matrixD, matrixB), displacementVector);
            nodalForces.add(forces);

            n++;
        }

        return nodalForces;
    }

    /**
     * Método para calcular os esforços nodais em lajes
     *
     * @param a
     * @param b
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalForces_Slabs(double a, double b, double[][] displacementVector) {
        ArrayList<double[][]> nodalForces = new ArrayList();

        //Cálculo da matriz de elasticidade
        double h3 = thickness * thickness * thickness;
        double[][] matrixD;

        if ("Reissner-Mindlin".equals(theory)) {
            matrixD = MatrixD.matrixD_ReissnerMindlin(elasticity, shear, poisson);

            matrixD[0][0] = matrixD[0][0] * (-h3 / 12.0);
            matrixD[0][1] = matrixD[0][1] * (-h3 / 12.0);
            matrixD[1][0] = matrixD[1][0] * (-h3 / 12.0);
            matrixD[1][1] = matrixD[1][1] * (-h3 / 12.0);
            matrixD[2][2] = matrixD[2][2] * (-h3 / 12.0);
            matrixD[3][3] = matrixD[3][3] * (thickness);
            matrixD[4][4] = matrixD[4][4] * (thickness);
        } else {
            matrixD = MatrixD.matrixD_Kirchhoff(elasticity, shear, poisson);
        }

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(0, a, b, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            double[] pointsCoordinates = weightsCoordinates[n];

            double pointX = pointsCoordinates[1];
            double pointY = pointsCoordinates[3];
            double pointZ = 0;

            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, 0, a, b, 0);
            double[][] matrixB = matrix.getMatrixB(pointX, pointY, pointZ, nodes, theory);

            if ("Reissner-Mindlin".equals(theory)) {
                double[][] forces = multiply(multiply(matrixD, matrixB), displacementVector);
                nodalForces.add(forces);
            }

            if ("Kirchhoff".equals(theory)) {
                double[][] vector = multiply(-(h3 / 12.0), multiply(multiply(matrixD, matrixB), displacementVector));
                double[][] forces = new double[5][1];

                forces[0][0] = vector[0][0];
                forces[1][0] = vector[1][0];
                forces[2][0] = vector[2][0];
                forces[3][0] = 0;
                forces[4][0] = 0;

                nodalForces.add(forces);
            }

            n++;
        }

        return nodalForces;
    }

    /**
     * Método para calcular as tensões nodais no caso unidimensional
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalStresses_1D(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
            double[][] stresses = multiply(multiply(elasticity, matrixB), displacementVector);
            nodalStresses.add(stresses);
            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para calcular as tensões nodais no caso bidimensional
     *
     * @param a
     * @param b
     * @param displacementVector
     * @param nodesCoordinates
     * @return
     */
    public ArrayList<double[][]> nodalStresses_2D(double a, double b, double[][] displacementVector, double[][] nodesCoordinates) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        //Cálculo da matriz de elasticidade
        double[][] matrixD;
        if ("Plane Stress".equals(theory)) {
            matrixD = MatrixD.matrixD_PlaneStress(elasticity, poisson);
        } else {
            matrixD = MatrixD.matrixD_PlaneStrain(elasticity, poisson);
        }

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(0, a, b, 0);
        double[] dimensions = { 0, a, b, 0 };

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            double[] pointsCoordinates = weightsCoordinates[n];
            double[][] matrixB, stresses;

            switch (shape) {
                case "Rectangle":
                    double pointX = pointsCoordinates[1];
                    double pointY = pointsCoordinates[3];
                    double pointZ = 0;

                    app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, 0, a, b, 0);
                    matrixB = matrix.getMatrixB(pointX, pointY, pointZ, nodes, theory);

                    stresses = multiply(multiply(matrixD, matrixB), displacementVector);
                    nodalStresses.add(stresses);
                    break;
                default:
                    //Matriz jacobiana no ponto de integração (i, j)
                    MatrixJ jacobianMatrix = new MatrixJ(type, nodesCoordinates, pointsCoordinates);
                    double[][] matrixJ = jacobianMatrix.getJacobianMatrix(nodes, theory);

                    //Inversa da matriz Jacobiana
                    double[][] inverseJ = inverse(matrixJ);

                    //Cálculo da matriz das derivadas das funções de forma
                    double[][] derivedMatrix = DerivedMatrix.derivedMatrix(type, theory, pointsCoordinates, dimensions, nodes);

                    //Cálculo dos termos da matriz B
                    double[][] derivedMatrixB = multiply(derivedMatrix, inverseJ);

                    //Cálculo da matriz B do elemento finito
                    matrixB = MatrixB.matrixB(type, theory, derivedMatrixB, nodes);

                    stresses = multiply(multiply(matrixD, matrixB), displacementVector);
                    nodalStresses.add(stresses);
                    break;
            }

            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para calcular as tensões nodais no caso tridimensional
     *
     * @param a
     * @param b
     * @param c
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalStresses_3D(double a, double b, double c, double[][] displacementVector) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        //Cálculo da matriz de elasticidade
        double[][] matrixD = MatrixD.matrixD_3D(elasticity, poisson);

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(0, a, b, c);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            double[] pointsCoordinates = weightsCoordinates[n];

            double pointX = pointsCoordinates[1];
            double pointY = pointsCoordinates[3];
            double pointZ = pointsCoordinates[5];

            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, 0, a, b, c);
            double[][] matrixB = matrix.getMatrixB(pointX, pointY, pointZ, nodes, theory);

            double[][] stresses = multiply(multiply(matrixD, matrixB), displacementVector);
            nodalStresses.add(stresses);

            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para calcular as tensões nodais em vigas
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalStresses_Beams(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[][] stresses, σtop, σbottom, τ;
            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            switch (theory) {
                case "Timoshenko":
                    double[][] matrixBb = matrix.getMatrixBb(point, nodes, theory);
                    double[][] matrixBs = matrix.getMatrixBs(point, nodes, theory);

                    σtop = multiply(multiply((-thickness / 2) * elasticity, matrixBb), displacementVector);
                    σbottom = multiply(multiply((thickness / 2) * elasticity, matrixBb), displacementVector);
                    τ = multiply(multiply(shear, matrixBs), displacementVector);

                    stresses = new double[1][3];
                    stresses[0][0] = σtop[0][0];
                    stresses[0][1] = σbottom[0][0];
                    stresses[0][2] = τ[0][0];
                    nodalStresses.add(stresses);
                    break;
                case "Euler-Bernoulli":
                    double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);

                    σtop = multiply(multiply((-thickness / 2) * elasticity, matrixB), displacementVector);
                    σbottom = multiply(multiply((thickness / 2) * elasticity, matrixB), displacementVector);

                    stresses = new double[1][2];
                    stresses[0][0] = σtop[0][0];
                    stresses[0][1] = σbottom[0][0];
                    nodalStresses.add(stresses);
                    break;
            }

            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para calcular as tensões nodais em barras
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalStresses_Frames(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        double[][] matrixD = MatrixD.matrixD_Frames(elasticity);

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
            double[][] σ = multiply(multiply(matrixD, matrixB), displacementVector);

            double[][] stresses = new double[1][3];
            stresses[0][0] = σ[0][0];
            stresses[0][1] = σ[1][0] * (-thickness / 2);
            stresses[0][2] = σ[1][0] * (thickness / 2);
            nodalStresses.add(stresses);

            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para calcular as tensões nodais em grelhas
     *
     * @param L
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalStresses_Grids(double L, double[][] displacementVector) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        double[][] matrixD = MatrixD.matrixD_Grids(elasticity, shear);

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(L, 0, 0, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, L, 0, 0, 0);

            double[] pointsCoordinates = weightsCoordinates[n];
            double point = pointsCoordinates[1];

            double[][] matrixB = matrix.getMatrixB(point, 0, 0, nodes, theory);
            double[][] σ = multiply(multiply(matrixD, matrixB), displacementVector);

            double[][] stresses = new double[1][3];
            stresses[0][0] = σ[0][0];
            stresses[0][1] = σ[1][0] * (-thickness / 2);
            stresses[0][2] = σ[1][0] * (thickness / 2);
            nodalStresses.add(stresses);

            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para calcular as tensões nodais em lajes
     *
     * @param a
     * @param b
     * @param displacementVector
     * @return
     */
    public ArrayList<double[][]> nodalStresses_Slabs(double a, double b, double[][] displacementVector) {
        ArrayList<double[][]> nodalStresses = new ArrayList();

        //Cálculo da matriz de elasticidade
        double[][] matrixD, matrixDTop, matrixDBottom;

        if ("Reissner-Mindlin".equals(theory)) {
            matrixD = MatrixD.matrixD_ReissnerMindlin(elasticity, shear, poisson);

            matrixDTop = new double[5][5];
            matrixDTop[0][0] = matrixD[0][0] * (thickness / 2);
            matrixDTop[0][1] = matrixD[0][1] * (thickness / 2);
            matrixDTop[1][0] = matrixD[1][0] * (thickness / 2);
            matrixDTop[1][1] = matrixD[1][1] * (thickness / 2);
            matrixDTop[2][2] = matrixD[2][2] * (thickness / 2);
            matrixDTop[3][3] = matrixD[3][3];
            matrixDTop[4][4] = matrixD[4][4];

            matrixDBottom = new double[5][5];
            matrixDBottom[0][0] = matrixD[0][0] * (-thickness / 2);
            matrixDBottom[0][1] = matrixD[0][1] * (-thickness / 2);
            matrixDBottom[1][0] = matrixD[1][0] * (-thickness / 2);
            matrixDBottom[1][1] = matrixD[1][1] * (-thickness / 2);
            matrixDBottom[2][2] = matrixD[2][2] * (-thickness / 2);
            matrixDBottom[3][3] = matrixD[3][3];
            matrixDBottom[4][4] = matrixD[4][4];
        } else {
            matrixD = MatrixD.matrixD_Kirchhoff(elasticity, shear, poisson);
            matrixDTop = null;
            matrixDBottom = null;
        }

        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates = getGaussLegendreCoordinates(0, a, b, 0);

        //Ciclo para todos os nós do elemento finito
        int n = 0;
        while (n < weightsCoordinates.length) {
            double[] pointsCoordinates = weightsCoordinates[n];

            double pointX = pointsCoordinates[1];
            double pointY = pointsCoordinates[3];
            double pointZ = 0;

            app.finiteelement.MatrixB matrix = new app.finiteelement.MatrixB(type, 0, a, b, 0);
            double[][] matrixB = matrix.getMatrixB(pointX, pointY, pointZ, nodes, theory);

            if ("Reissner-Mindlin".equals(theory)) {
                double[][] stressesTop = multiply(multiply(matrixDTop, matrixB), displacementVector);
                double[][] stressesBottom = multiply(multiply(matrixDBottom, matrixB), displacementVector);

                double[][] stresses = new double[8][1];
                stresses[0][0] = stressesTop[0][0];
                stresses[1][0] = stressesTop[1][0];
                stresses[2][0] = stressesTop[2][0];
                stresses[3][0] = stressesBottom[0][0];
                stresses[4][0] = stressesBottom[1][0];
                stresses[5][0] = stressesBottom[2][0];
                stresses[6][0] = (stressesTop[3][0] + stressesBottom[3][0]) / 2;
                stresses[7][0] = (stressesTop[4][0] + stressesBottom[4][0]) / 2;
                nodalStresses.add(stresses);
            }

            if ("Kirchhoff".equals(theory)) {
                double[][] stressesTop = multiply(thickness / 2, multiply(multiply(matrixD, matrixB), displacementVector));
                double[][] stressesBottom = multiply(-thickness / 2, multiply(multiply(matrixD, matrixB), displacementVector));

                double[][] stresses = new double[8][1];
                stresses[0][0] = stressesTop[0][0];
                stresses[1][0] = stressesTop[1][0];
                stresses[2][0] = stressesTop[2][0];
                stresses[3][0] = stressesBottom[0][0];
                stresses[4][0] = stressesBottom[1][0];
                stresses[5][0] = stressesBottom[2][0];
                stresses[6][0] = 0;
                stresses[7][0] = 0;
                nodalStresses.add(stresses);
            }

            n++;
        }

        return nodalStresses;
    }

    /**
     * Método para obter as coordenadas dos pontos para cálculo dos resultados
     *
     * @param L
     * @param a
     * @param b
     * @param c
     * @return
     */
    public double[][] getGaussLegendreCoordinates(double L, double a, double b, double c) {
        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates;
        double[] dimensions = { L, a, b, c };

        GaussLegendre gaussLegendre = new GaussLegendre(type, theory, shape, nodes, dimensions);
        if (nodes == 6) {
            gaussLegendre = new GaussLegendre(type, theory, shape, 3, dimensions);
        }
        if (nodes == 8) {
            gaussLegendre = new GaussLegendre(type, theory, shape, 9, dimensions);
        }
        weightsCoordinates = gaussLegendre.getWeightsCoordinates();

        switch (shape) {
            case "Line":
                if (nodes == 3) {
                    double[][] coordinates = new double[2][2];

                    coordinates[0][0] = weightsCoordinates[0][0];
                    coordinates[0][1] = weightsCoordinates[0][1];
                    coordinates[1][0] = weightsCoordinates[2][0];
                    coordinates[1][1] = weightsCoordinates[2][1];

                    weightsCoordinates = coordinates;
                }
                if (nodes == 4) {
                    double[][] coordinates = new double[2][2];

                    coordinates[0][0] = weightsCoordinates[0][0];
                    coordinates[0][1] = weightsCoordinates[0][1];
                    coordinates[1][0] = weightsCoordinates[3][0];
                    coordinates[1][1] = weightsCoordinates[3][1];
                    weightsCoordinates = coordinates;
                }

                return weightsCoordinates;
            case "Triangle":
                return weightsCoordinates;
            case "Rectangle":
            case "Quadrilateral":
                if (nodes == 8 || nodes == 9) {
                    double[][] coordinates = new double[4][4];

                    int i = 0;
                    int j = 0;
                    for (double[] wCoordinates : weightsCoordinates) {
                        if ((j % 2) == 0 && j < 8) {
                            coordinates[i][0] = wCoordinates[0];
                            coordinates[i][1] = wCoordinates[1];
                            coordinates[i][2] = wCoordinates[2];
                            coordinates[i][3] = wCoordinates[3];
                            i++;
                        }
                        j++;
                    }

                    weightsCoordinates = coordinates;
                }

                return weightsCoordinates;
            default:
                return weightsCoordinates;
        }
    }

    /**
     * Método para obter as coordenadas dos pontos para cálculo dos resultados
     *
     * @param L
     * @param a
     * @param b
     * @param c
     * @return
     */
    public double[][] getGaussLobattoCoordinates(double L, double a, double b, double c) {
        //Matriz de coordenadas e pesos associados a cada ponto
        double[][] weightsCoordinates;
        double[] dimensions = { L, a, b, c };

        GaussLobatto gaussLobatto = new GaussLobatto(type, theory, shape, nodes, dimensions);
        if (nodes == 6) {
            gaussLobatto = new GaussLobatto(type, theory, shape, 3, dimensions);
        }
        if (nodes == 8) {
            gaussLobatto = new GaussLobatto(type, theory, shape, 9, dimensions);
        }
        weightsCoordinates = gaussLobatto.getWeightsCoordinates();

        switch (shape) {
            case "Line":
                if (nodes == 3) {
                    double[][] coordinates = new double[2][2];

                    coordinates[0][0] = weightsCoordinates[0][0];
                    coordinates[0][1] = weightsCoordinates[0][1];
                    coordinates[1][0] = weightsCoordinates[2][0];
                    coordinates[1][1] = weightsCoordinates[2][1];

                    weightsCoordinates = coordinates;
                }
                if (nodes == 4) {
                    double[][] coordinates = new double[2][2];

                    coordinates[0][0] = weightsCoordinates[0][0];
                    coordinates[0][1] = weightsCoordinates[0][1];
                    coordinates[1][0] = weightsCoordinates[3][0];
                    coordinates[1][1] = weightsCoordinates[3][1];
                    weightsCoordinates = coordinates;
                }

                return weightsCoordinates;
            case "Triangle":
                return weightsCoordinates;
            case "Rectangle":
            case "Quadrilateral":
                if (nodes == 8 || nodes == 9) {
                    double[][] coordinates = new double[4][4];

                    int i = 0;
                    int j = 0;
                    for (double[] wCoordinates : weightsCoordinates) {
                        if ((j % 2) == 0 && j < 8) {
                            coordinates[i][0] = wCoordinates[0];
                            coordinates[i][1] = wCoordinates[1];
                            coordinates[i][2] = wCoordinates[2];
                            coordinates[i][3] = wCoordinates[3];
                            i++;
                        }
                        j++;
                    }

                    weightsCoordinates = coordinates;
                }

                return weightsCoordinates;
            default:
                return weightsCoordinates;
        }
    }
}
