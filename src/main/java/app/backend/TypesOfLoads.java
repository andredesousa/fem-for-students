/*
 * Esta classe cria as subclasses para os objetos do painel Loads
 * A classe cria as cubclasses ConcentratedLoad, BendingMoment, DistributedLoad, AxialLoad,
 *PlanarLoad, ThermalLoad e SelfWeight
 * Os atributos das subclasses são recebidos por parâmetro
 */

package app.backend;

import java.io.Serializable;

/**
 *
 * @author André de Sousa
 */
public class TypesOfLoads implements Serializable {

    /**
     * Classe pública para criar uma carga concentrada
     */
    public class ConcentratedLoad implements Serializable {

        public String name;
        public double concentratedLoad_Fx;
        public double concentratedLoad_Fy;

        /**
         * Método construtor da classe ConcentratedLoad
         *
         * @param name é o nome da carga estrutural
         * @param concentratedLoad_Fx é o valor da carga segundo o eixo x
         * @param concentratedLoad_Fy é o valor da carga segundo o eixo y
         */
        public ConcentratedLoad(String name, double concentratedLoad_Fx, double concentratedLoad_Fy) {
            this.name = name;
            this.concentratedLoad_Fx = concentratedLoad_Fx;
            this.concentratedLoad_Fy = concentratedLoad_Fy;
        }

        /**
         * Método para alterar os valores dos atributos da classe
         *
         * @param concentratedLoad_Fx é o valor da carga segundo o eixo x
         * @param concentratedLoad_Fy é o valor da carga segundo o eixo y
         */
        public void editLoad(double concentratedLoad_Fx, double concentratedLoad_Fy) {
            this.concentratedLoad_Fx = concentratedLoad_Fx;
            this.concentratedLoad_Fy = concentratedLoad_Fy;
        }
    }

    /**
     * Classe pública para criar uma carga do tipo momento fletor
     */
    public class BendingMoment implements Serializable {

        public String name;
        public double bendingMoment_M;

        /**
         * Método construtor da classe BendingMoment
         *
         * @param name é o nome da carga estrutural
         * @param bendingMoment_M é o valor do momento flector
         */
        public BendingMoment(String name, double bendingMoment_M) {
            this.name = name;
            this.bendingMoment_M = bendingMoment_M;
        }

        /**
         * Método para alterar o valor do atributo da classe
         *
         * @param bendingMoment_M é o valor do momento flector
         */
        public void editLoad(double bendingMoment_M) {
            this.bendingMoment_M = bendingMoment_M;
        }
    }

    /**
     * Classe pública para criar uma carga uniformemente distribuída
     */
    public class DistributedLoad implements Serializable {

        public String name;
        public double distributedLoad_Qx;
        public double distributedLoad_Qy;

        /**
         * Método construtor da classe DistributedLoad
         *
         * @param name é o nome da carga estrutural
         * @param distributedLoad_Qx é o valor da carga segundo o eixo x
         * @param distributedLoad_Qy é o valor da carga segundo o eixo y
         */
        public DistributedLoad(String name, double distributedLoad_Qx, double distributedLoad_Qy) {
            this.name = name;
            this.distributedLoad_Qx = distributedLoad_Qx;
            this.distributedLoad_Qy = distributedLoad_Qy;
        }

        /**
         * Método para alterar os valores dos atributos da classe
         *
         * @param distributedLoad_Qx é o valor da carga segundo o eixo x
         * @param distributedLoad_Qy é o valor da carga segundo o eixo y
         */
        public void editLoad(double distributedLoad_Qx, double distributedLoad_Qy) {
            this.distributedLoad_Qx = distributedLoad_Qx;
            this.distributedLoad_Qy = distributedLoad_Qy;
        }
    }

    /**
     * Classe pública para criar uma carga axial distribuída
     */
    public class AxialLoad implements Serializable {

        public String name;
        public double axialLoad_N;

        /**
         * Método construtor da classe AxialLoad
         *
         * @param name é o nome da carga estrutural
         * @param axialLoad_N é o valor da carga axial
         */
        public AxialLoad(String name, double axialLoad_N) {
            this.name = name;
            this.axialLoad_N = axialLoad_N;
        }

        /**
         * Método para alterar o valor do atributo da classe
         *
         * @param axialLoad_N é o valor da carga axial
         */
        public void editLoad(double axialLoad_N) {
            this.axialLoad_N = axialLoad_N;
        }
    }

    /**
     * Classe pública para criar uma carga de superfície
     */
    public class PlanarLoad implements Serializable {

        public String name;
        public double planarLoad_Qz;

        /**
         * Método construtor da classe PlanarLoad
         *
         * @param name é o nome da carga estrutural
         * @param planarLoad_Qz é o valor da carga segundo o eixo z
         */
        public PlanarLoad(String name, double planarLoad_Qz) {
            this.name = name;
            this.planarLoad_Qz = planarLoad_Qz;
        }

        /**
         * Método para alterar o valor do atributo da classe
         *
         * @param planarLoad_Qz é o valor da carga segundo o eixo z
         */
        public void editLoad(double planarLoad_Qz) {
            this.planarLoad_Qz = planarLoad_Qz;
        }
    }

    /**
     * Classe pública para criar uma variação de temperatura
     */
    public class ThermalLoad implements Serializable {

        public String name;
        public double thermalLoad_Tzero;
        public double thermalLoad_Ttop;
        public double thermalLoad_Tbot;

        /**
         * Método construtor da classe ThermalLoad
         *
         * @param name é o nome da variação de temperatura
         * @param distributedLoad_Tzero é o valor da variação uniforme de temperatura
         * @param distributedLoad_Ttop é o valor superior da variação de temperatura
         * @param distributedLoad_Tbot é o valor inferior da variação de temperatura
         */
        public ThermalLoad(String name, double distributedLoad_Tzero, double distributedLoad_Ttop, double distributedLoad_Tbot) {
            this.name = name;
            this.thermalLoad_Tzero = distributedLoad_Tzero;
            this.thermalLoad_Ttop = distributedLoad_Ttop;
            this.thermalLoad_Tbot = distributedLoad_Tbot;
        }

        /**
         * Método para alterar os valores dos atributos da classe
         *
         * @param distributedLoad_Tzero é o valor da variação uniforme de temperatura
         * @param distributedLoad_Ttop é o valor superior da variação de temperatura
         * @param distributedLoad_Tbot é o valor inferior da variação de temperatura
         */
        public void editLoad(double distributedLoad_Tzero, double distributedLoad_Ttop, double distributedLoad_Tbot) {
            this.thermalLoad_Tzero = distributedLoad_Tzero;
            this.thermalLoad_Ttop = distributedLoad_Ttop;
            this.thermalLoad_Tbot = distributedLoad_Tbot;
        }
    }

    /**
     * Classe pública para criar uma carga do tipo peso próprio
     */
    public class SelfWeight implements Serializable {

        public String name;
        public double selfWeight_S;

        /**
         * Método construtor da classe SelfWeight
         *
         * @param name é o nome da carga estrutural
         * @param selfWeight_S é o valor do peso próprio
         */
        public SelfWeight(String name, double selfWeight_S) {
            this.name = name;
            this.selfWeight_S = selfWeight_S;
        }

        /**
         * Método para alterar o valor do atributo da classe
         *
         * @param selfWeight_S é o valor do peso próprio
         */
        public void editLoad(double selfWeight_S) {
            this.selfWeight_S = selfWeight_S;
        }
    }
}
