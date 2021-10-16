/*
 * Esta classe fornece a numeração das jLabels do menu
 * Todos os atributos da classe são públicos
 * Os valores do atributos são do tipo inteiro
 */

package app.backend;

/**
 *
 * @author André de Sousa
 */
public class LabelsNumeration {

    //Numeração das labels associadas ao painel JPanelDraw
    public int jPDraw_Point = 1;
    public int jPDraw_Line = 2;
    public int jPDraw_Triangle = 3;
    public int jPDraw_Rectangle = 4;
    public int jPDraw_Quadrilateral = 5;
    public int jPDraw_Undo = 6;
    public int jPDraw_Redo = 7;
    public int jPDraw_Move = 8;
    public int jPDraw_Select = 9;
    public int jPDraw_Cut = 10;
    public int jPDraw_Copy = 11;
    public int jPDraw_Paste = 12;

    //Numeração das jLabels associadas ao painel JPanelView
    public int jPanelView_Pan = 1;
    public int jPanelView_ZoomIn = 2;
    public int jPanelView_ZoomOut = 3;
    public int jPanelView_ButtonGrid = 4;
    public int jPanelView_ButtonSnap = 5;
    public int jPanelView_ButtonLegends = 6;

    //Numeração das jLabels associadas ao painel JPanelGeometry
    public int jPGeometry_Nodes = 1;
    public int jPGeometry_NumberOfNodes = 2;
    public int jPGeometry_Quadrilateral = 5;
    public int jPGeometry_Sections = 6;
    public int jPGeometry_Materials = 7;
    public int jPGeometry_Mesh = 8;
    public int jPGeometry_DimensionLine = 9;
    public int jPGeometry_VSimpleSupport = 10;
    public int jPGeometry_HSimpleSupport = 11;
    public int jPGeometry_PinnedSupport = 12;
    public int jPGeometry_FixedSupport = 13;
    public int jPGeometry_HSliderSupport = 14;
    public int jPGeometry_VSliderSupport = 15;
    public int jPGeometry_ElasticSupport = 16;
    public int jPGeometry_Settlements = 17;
    public int jPGeometry_CutSupports = 18;

    //Numeração das jLabels associadas ao painel JPanelLoads
    public int jPLoads_ConcentratedLoad = 1;
    public int jPLoads_BendingMoment = 2;
    public int jPLoads_UnifDistLoad = 3;
    public int jPLoads_DistAxialLoad = 4;
    public int jPLoads_UnifPlanarLoad = 5;
    public int jPLoads_ThermalLoad = 6;
    public int jPLoads_SelfWeight = 7;
    public int jPLoads_Cut = 8;
    public int jPLoads_LoadTable = 9;

    //Numeração das jLabels associadas ao painel JPanelAnalysis
    public int jPAnalysis_Theory = 1;
    public int jPAnalysis_Analytical = 2;
    public int jPAnalysis_Numerical = 3;
    public int jPAnalysis_Calculate = 4;

    //Numeração das jLabels associadas ao painel JPanelResults
    public int jPResults_VectorF = 1;
    public int jPResults_MatrixK = 2;
    public int jPResults_VectorDisplacements = 3;
    public int jPResults_Properties = 4;
    public int jPResults_SupportReactions = 5;
    public int jPResults_NodalForces = 6;
    public int jPResults_NodalStresses = 7;
    public int jPResults_Displacements = 8;
    public int jPResults_Diagrams = 9;
    public int jPResults_Isovalues = 10;
    public int jPResults_Maps = 11;
    public int jPResults_PrincipalDirections = 12;
}
