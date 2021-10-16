/*
 * Esta classe cria as subclasses para os objetos do painel Geometry
 * A classe cria as cubclasses Nodes, Section, Material, Support e Settlement
 * Os atributos das subclasses são recebidos por parâmetro
 */

package app.backend;

import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class Geometry implements Serializable {

    /**
     * Classe pública para definir o número de nós
     */
    public class Nodes implements Serializable {

        public int barsNumberNodes;
        public int trianglesNumberNodes;
        public int quadrilateralsNumberNodes;

        /**
         * Método construtor da classe Nodes
         *
         * @param bars é o número de nós de um elemento do tipo barra
         * @param triangles é o número de nós de um elemento triangular
         * @param quadrilaterals é o número de nós de um elemento quadrilátero
         */
        public Nodes(int bars, int triangles, int quadrilaterals) {
            this.barsNumberNodes = bars;
            this.trianglesNumberNodes = triangles;
            this.quadrilateralsNumberNodes = quadrilaterals;
        }

        /**
         * Método para alterar os atributos da classe
         *
         * @param bars é o número de nós de um elemento do tipo barra
         * @param triangles é o número de nós de um elemento triangular
         * @param quadrilaterals é o número de nós de um elemento quadrilátero
         */
        public void editNodes(int bars, int triangles, int quadrilaterals) {
            this.barsNumberNodes = bars;
            this.trianglesNumberNodes = triangles;
            this.quadrilateralsNumberNodes = quadrilaterals;
        }
    }

    /**
     * Classe pública para criar uma secção
     */
    public class Section implements Serializable {

        public String name;
        public double inertia;
        public double torsion;
        public double area;
        public double thickness;

        /**
         * Método construtor da classe Section
         *
         * @param name é o nome da secção do elemento finito
         * @param inertia é a inercia da secção transversal
         * @param torsion é a inércia polar da secção transversal
         * @param area é a área de um elementos finito
         * @param thickness é a espessura de um elemento finito
         */
        public Section(String name, double inertia, double torsion, double area, double thickness) {
            this.name = name;
            this.inertia = inertia;
            this.torsion = torsion;
            this.area = area;
            this.thickness = thickness;
        }

        /**
         * Método para alterar os atributos da classe
         *
         * @param inertia é a inercia da secção transversal
         * @param torsion é a inércia polar da secção transversal
         * @param area é a área de um elementos finito
         * @param thickness é a espessura de um elemento finito
         */
        public void editSection(double inertia, double torsion, double area, double thickness) {
            this.inertia = inertia;
            this.torsion = torsion;
            this.area = area;
            this.thickness = thickness;
        }
    }

    /**
     * Classe pública para criar um material
     */
    public class Material implements Serializable {

        public String material;
        public double elasticity;
        public double poisson;
        public double thermal;

        /**
         * Método construtor da classe Material
         *
         * @param material é o nome do material a ser criado
         * @param elasticity é o módulo de elasticidade do material
         * @param poisson é o coeficiente de Poisson do material
         * @param thermal é o coeficiente de dilatação térmica linear
         */
        public Material(String material, double elasticity, double poisson, double thermal) {
            this.material = material;
            this.elasticity = elasticity;
            this.poisson = poisson;
            this.thermal = thermal;
        }

        /**
         * Método para alterar os atributos da classe
         *
         * @param elasticity é o módulo de elasticidade do material
         * @param poisson é o coeficiente de Poisson do material
         * @param thermal é o coeficiente de dilatação térmica linear
         */
        public void editMaterial(double elasticity, double poisson, double thermal) {
            this.elasticity = elasticity;
            this.poisson = poisson;
            this.thermal = thermal;
        }
    }

    /**
     * Classe pública para criar um apoio elástico
     */
    public class ElasticSupport implements Serializable {

        public String name;
        public double stiffnessKx;
        public double stiffnessKy;
        public double stiffnessKz;

        /**
         * Método construtor da classe Support
         *
         * @param name é o nome do apoio a ser criado
         * @param stiffnessKx é a rigidez da mola segundo a direcção x
         * @param stiffnessKy é a rigidez da mola segundo a direcção y
         * @param stiffnessKz é a rigidez da mola segundo a direcção z
         */
        public ElasticSupport(String name, double stiffnessKx, double stiffnessKy, double stiffnessKz) {
            this.name = name;
            this.stiffnessKx = stiffnessKx;
            this.stiffnessKy = stiffnessKy;
            this.stiffnessKz = stiffnessKz;
        }

        /**
         * Método para alterar os atributos da classe
         *
         * @param stiffnessKx é a rigidez da mola segundo a direcção x
         * @param stiffnessKy é a rigidez da mola segundo a direcção y
         * @param stiffnessKz é a rigidez da mola segundo a direcção z
         */
        public void editSupport(double stiffnessKx, double stiffnessKy, double stiffnessKz) {
            this.stiffnessKx = stiffnessKx;
            this.stiffnessKy = stiffnessKy;
            this.stiffnessKz = stiffnessKz;
        }
    }

    /**
     * Classe pública para criar um assentamento de apoio
     */
    public class Settlement implements Serializable {

        public String name;
        public double displacementDx;
        public double displacementDy;
        public double rotationRz;

        /**
         * Método construtor da classe Material
         *
         * @param name é o nome do assentamento a ser criado
         * @param displacementDx é o valor do deslocamento segundo a direcção x
         * @param displacementDy é o valor do deslocamento segundo a direcção y
         * @param rotationRz é o valor da rotação segundo a direcção z
         */
        public Settlement(String name, double displacementDx, double displacementDy, double rotationRz) {
            this.name = name;
            this.displacementDx = displacementDx;
            this.displacementDy = displacementDy;
            this.rotationRz = rotationRz;
        }

        /**
         * Método para alterar os atributos da classe
         *
         * @param displacementDx é o valor do deslocamento segundo a direcção x
         * @param displacementDy é o valor do deslocamento segundo a direcção y
         * @param rotationRz é o valor da rotação segundo a direcção z
         */
        public void editSettlement(double displacementDx, double displacementDy, double rotationRz) {
            this.displacementDx = displacementDx;
            this.displacementDy = displacementDy;
            this.rotationRz = rotationRz;
        }
    }
}
