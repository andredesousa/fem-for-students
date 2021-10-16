/*
 * Classe para abrir e salvar os objetos criados no projeto
 * A classe permite abrir e devolver a informação de um projeto criado
 * A classe permite salvar os objetos desenhados num arquivo binário
 */

package app.backend;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author André de Sousa
 */
public class OpenSave {

    private String type = new String();
    private int[] geometryCounter = new int[4];
    private int[] loadCounter = new int[7];
    private ArrayList<Geometry.Section> sections = new ArrayList();
    private ArrayList<Geometry.Material> materials = new ArrayList();
    private ArrayList<Geometry.ElasticSupport> elasticSupports = new ArrayList();
    private ArrayList<Geometry.Settlement> settlements = new ArrayList();
    private ArrayList<DrawEllipse> arrayListNodes = new ArrayList();
    private ArrayList<DrawLine> arrayListLines = new ArrayList();
    private ArrayList<DrawPolygon> arrayListPolygons = new ArrayList();
    private ArrayList<DrawSupports> arrayListSupports = new ArrayList();
    private ArrayList<DrawElasticSupports> arrayListElasticSupports = new ArrayList();
    private ArrayList<DrawSettlements> arrayListSettlements = new ArrayList();
    private ArrayList<DrawLoads> arrayListLoads = new ArrayList();
    private ArrayList<DrawDimensionLine> arrayListDimensionLines = new ArrayList();
    private ArrayList<TypesOfLoads.ConcentratedLoad> concentratedLoads = new ArrayList();
    private ArrayList<TypesOfLoads.BendingMoment> bendingMoments = new ArrayList();
    private ArrayList<TypesOfLoads.DistributedLoad> distributedLoads = new ArrayList();
    private ArrayList<TypesOfLoads.AxialLoad> axialLoads = new ArrayList();
    private ArrayList<TypesOfLoads.PlanarLoad> planarLoads = new ArrayList();
    private ArrayList<TypesOfLoads.ThermalLoad> thermalLoads = new ArrayList();
    private ArrayList<TypesOfLoads.SelfWeight> selfWeights = new ArrayList();

    /**
     * Construtor da classe para salvar os objetos do projeto
     *
     * @param type
     * @param geometryCounter
     * @param loadCounter
     * @param sections
     * @param materials
     * @param elasticSupports
     * @param settlements
     * @param arrayListNodes
     * @param arrayListLines
     * @param arrayListPolygons
     * @param arrayListSupports
     * @param arrayListElasticSupports
     * @param arrayListSettlements
     * @param arrayListLoads
     * @param arrayListDimensionLines
     * @param concentratedLoads
     * @param bendingMoments
     * @param distributedLoads
     * @param axialLoads
     * @param planarLoads
     * @param thermalLoads
     * @param selfWeights
     * @param absolutePath
     */
    public OpenSave(
        String type,
        int[] geometryCounter,
        int[] loadCounter,
        ArrayList<Geometry.Section> sections,
        ArrayList<Geometry.Material> materials,
        ArrayList<Geometry.ElasticSupport> elasticSupports,
        ArrayList<Geometry.Settlement> settlements,
        ArrayList<DrawEllipse> arrayListNodes,
        ArrayList<DrawLine> arrayListLines,
        ArrayList<DrawPolygon> arrayListPolygons,
        ArrayList<DrawSupports> arrayListSupports,
        ArrayList<DrawElasticSupports> arrayListElasticSupports,
        ArrayList<DrawSettlements> arrayListSettlements,
        ArrayList<DrawLoads> arrayListLoads,
        ArrayList<DrawDimensionLine> arrayListDimensionLines,
        ArrayList<TypesOfLoads.ConcentratedLoad> concentratedLoads,
        ArrayList<TypesOfLoads.BendingMoment> bendingMoments,
        ArrayList<TypesOfLoads.DistributedLoad> distributedLoads,
        ArrayList<TypesOfLoads.AxialLoad> axialLoads,
        ArrayList<TypesOfLoads.PlanarLoad> planarLoads,
        ArrayList<TypesOfLoads.ThermalLoad> thermalLoads,
        ArrayList<TypesOfLoads.SelfWeight> selfWeights,
        String absolutePath
    ) {
        this.type = type;
        this.geometryCounter = geometryCounter;
        this.loadCounter = loadCounter;
        this.sections = sections;
        this.materials = materials;
        this.elasticSupports = elasticSupports;
        this.settlements = settlements;
        this.arrayListNodes = arrayListNodes;
        this.arrayListLines = arrayListLines;
        this.arrayListPolygons = arrayListPolygons;
        this.arrayListSupports = arrayListSupports;
        this.arrayListElasticSupports = arrayListElasticSupports;
        this.arrayListSettlements = arrayListSettlements;
        this.arrayListLoads = arrayListLoads;
        this.arrayListDimensionLines = arrayListDimensionLines;
        this.concentratedLoads = concentratedLoads;
        this.bendingMoments = bendingMoments;
        this.distributedLoads = distributedLoads;
        this.axialLoads = axialLoads;
        this.planarLoads = planarLoads;
        this.thermalLoads = thermalLoads;
        this.selfWeights = selfWeights;

        //Chamada do método responsável por gravar o projeto
        saveAllObjects(absolutePath);
    }

    /**
     * Construtor da classe para carregar os objetos do projeto
     *
     * @param absolutePath
     */
    public OpenSave(String absolutePath) {
        openAllObjects(absolutePath);
    }

    //Variável para armazenar o estado de execução das tarefas
    private boolean state = false;

    /**
     * Método para informar se o conteúdo dos objetos foi guardado
     *
     * @return
     */
    public boolean allObjectsWereSaved() {
        return state;
    }

    /**
     * Método para informar se o conteúdo dos objetos foi recuperado
     *
     * @return
     */
    public boolean allObjectsWereOpened() {
        return state;
    }

    /**
     * Método para gravar a lista de objetos do projeto
     *
     * @param absolutePath
     */
    private void saveAllObjects(String absolutePath) {
        try (FileOutputStream stream = new FileOutputStream(absolutePath)) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(stream)) {
                outputStream.writeObject(type);
                outputStream.writeObject(geometryCounter);
                outputStream.writeObject(loadCounter);
                outputStream.writeObject(sections);
                outputStream.writeObject(materials);
                outputStream.writeObject(elasticSupports);
                outputStream.writeObject(settlements);
                outputStream.writeObject(arrayListNodes);
                outputStream.writeObject(arrayListLines);
                outputStream.writeObject(arrayListPolygons);
                outputStream.writeObject(arrayListSupports);
                outputStream.writeObject(arrayListElasticSupports);
                outputStream.writeObject(arrayListSettlements);
                outputStream.writeObject(arrayListLoads);
                outputStream.writeObject(arrayListDimensionLines);
                outputStream.writeObject(concentratedLoads);
                outputStream.writeObject(bendingMoments);
                outputStream.writeObject(distributedLoads);
                outputStream.writeObject(axialLoads);
                outputStream.writeObject(planarLoads);
                outputStream.writeObject(thermalLoads);
                outputStream.writeObject(selfWeights);
                state = true;
            } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! Unable to save the project.");
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error! Unable to save the project.");
        }
    }

    /**
     * Método para carregar a lista de objetos do projeto
     *
     * @param absolutePath
     */
    private void openAllObjects(String absolutePath) {
        try (FileInputStream stream = new FileInputStream(absolutePath);) {
            try (ObjectInputStream inputStream = new ObjectInputStream(stream);) {
                type = (String) inputStream.readObject();
                geometryCounter = (int[]) inputStream.readObject();
                loadCounter = (int[]) inputStream.readObject();
                sections = (ArrayList<Geometry.Section>) inputStream.readObject();
                materials = (ArrayList<Geometry.Material>) inputStream.readObject();
                elasticSupports = (ArrayList<Geometry.ElasticSupport>) inputStream.readObject();
                settlements = (ArrayList<Geometry.Settlement>) inputStream.readObject();
                arrayListNodes = (ArrayList<DrawEllipse>) inputStream.readObject();
                arrayListLines = (ArrayList<DrawLine>) inputStream.readObject();
                arrayListPolygons = (ArrayList<DrawPolygon>) inputStream.readObject();
                arrayListSupports = (ArrayList<DrawSupports>) inputStream.readObject();
                arrayListElasticSupports = (ArrayList<DrawElasticSupports>) inputStream.readObject();
                arrayListSettlements = (ArrayList<DrawSettlements>) inputStream.readObject();
                arrayListLoads = (ArrayList<DrawLoads>) inputStream.readObject();
                arrayListDimensionLines = (ArrayList<DrawDimensionLine>) inputStream.readObject();
                concentratedLoads = (ArrayList<TypesOfLoads.ConcentratedLoad>) inputStream.readObject();
                bendingMoments = (ArrayList<TypesOfLoads.BendingMoment>) inputStream.readObject();
                distributedLoads = (ArrayList<TypesOfLoads.DistributedLoad>) inputStream.readObject();
                axialLoads = (ArrayList<TypesOfLoads.AxialLoad>) inputStream.readObject();
                planarLoads = (ArrayList<TypesOfLoads.PlanarLoad>) inputStream.readObject();
                thermalLoads = (ArrayList<TypesOfLoads.ThermalLoad>) inputStream.readObject();
                selfWeights = (ArrayList<TypesOfLoads.SelfWeight>) inputStream.readObject();
                state = true;
            } catch (Exception e) {
                type = new String();
                geometryCounter = new int[4];
                loadCounter = new int[7];
                sections = new ArrayList();
                materials = new ArrayList();
                elasticSupports = new ArrayList();
                settlements = new ArrayList();
                arrayListNodes = new ArrayList();
                arrayListLines = new ArrayList();
                arrayListPolygons = new ArrayList();
                arrayListSupports = new ArrayList();
                arrayListElasticSupports = new ArrayList();
                arrayListSettlements = new ArrayList();
                arrayListLoads = new ArrayList();
                arrayListDimensionLines = new ArrayList();
                concentratedLoads = new ArrayList();
                bendingMoments = new ArrayList();
                distributedLoads = new ArrayList();
                axialLoads = new ArrayList();
                planarLoads = new ArrayList();
                thermalLoads = new ArrayList();
                selfWeights = new ArrayList();

                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Error! Unable to open the project.");
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error! Unable to open the project.");
        }
    }

    /**
     * Método para obter o tipo de projeto
     *
     * @return
     */
    public String getTypeOfProject() {
        return type;
    }

    /**
     * Método para obter a numeração das cargas do projeto
     *
     * @return
     */
    public int[] getSectionCounter() {
        return geometryCounter;
    }

    /**
     * Método para obter a numeração das cargas do projeto
     *
     * @return
     */
    public int[] getLoadCounter() {
        return loadCounter;
    }

    /**
     * Método para obter a secção dos elementos finitos
     *
     * @return
     */
    public ArrayList<Geometry.Section> getSections() {
        return sections;
    }

    /**
     * Método para obter a lista de materiais do projeto
     *
     * @return
     */
    public ArrayList<Geometry.Material> getMaterials() {
        return materials;
    }

    /**
     * Método para obter a lista de apoios elásticos
     *
     * @return
     */
    public ArrayList<Geometry.ElasticSupport> getElasticSupports() {
        return elasticSupports;
    }

    /**
     * Método para obter a lista de assentamentos de apoio
     *
     * @return
     */
    public ArrayList<Geometry.Settlement> getSettlements() {
        return settlements;
    }

    /**
     * Método para obter a lista de nós desenhados do projeto
     *
     * @return
     */
    public ArrayList<DrawEllipse> getArrayListNodes() {
        return arrayListNodes;
    }

    /**
     * Método para obter a lista de linhas desenhadas do projeto
     *
     * @return
     */
    public ArrayList<DrawLine> getArrayListLines() {
        return arrayListLines;
    }

    /**
     * Método para obter a lista de polígonos desenhados do projeto
     *
     * @return
     */
    public ArrayList<DrawPolygon> getArrayListPolygons() {
        return arrayListPolygons;
    }

    /**
     * Método para obter a lista de apoios estruturais do projeto
     *
     * @return
     */
    public ArrayList<DrawSupports> getArrayListSupports() {
        return arrayListSupports;
    }

    /**
     * Método para obter a lista de apoios elásticos do projeto
     *
     * @return
     */
    public ArrayList<DrawElasticSupports> getArrayListElasticSupports() {
        return arrayListElasticSupports;
    }

    /**
     * Método para obter a lista de assentamentos de apoio do projeto
     *
     * @return
     */
    public ArrayList<DrawSettlements> getArrayListSettlements() {
        return arrayListSettlements;
    }

    /**
     * Método para obter a lista de cargas estruturais desenhadas
     *
     * @return
     */
    public ArrayList<DrawLoads> getArrayListLoads() {
        return arrayListLoads;
    }

    /**
     * Método para obter a lista de linhas de cotagem
     *
     * @return
     */
    public ArrayList<DrawDimensionLine> getArrayListDimensionLines() {
        return arrayListDimensionLines;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.ConcentratedLoad> getConcentratedLoads() {
        return concentratedLoads;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.BendingMoment> getBendingMoments() {
        return bendingMoments;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.DistributedLoad> getDistributedLoads() {
        return distributedLoads;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.AxialLoad> getAxialLoads() {
        return axialLoads;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.PlanarLoad> getPlanarLoads() {
        return planarLoads;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.ThermalLoad> getThermalLoads() {
        return thermalLoads;
    }

    /**
     * Método para obter a lista de cargas estruturais do projeto
     *
     * @return
     */
    public ArrayList<TypesOfLoads.SelfWeight> getSelfWeight() {
        return selfWeights;
    }
}
