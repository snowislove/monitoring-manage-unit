package com.secpro.platform.monitoring.manage.topology.service;

import javax.servlet.http.HttpServletRequest;

import y.layout.BufferedLayouter;
import y.layout.CanonicMultiStageLayouter;
import y.layout.Layouter;
import y.layout.MinNodeSizeStage;
import y.layout.circular.CircularLayouter;
import y.layout.hierarchic.IncrementalHierarchicLayouter;
import y.layout.hierarchic.incremental.NodeLayoutDescriptor;
import y.layout.hierarchic.incremental.SimplexNodePlacer;
import y.layout.labeling.GreedyMISLabeling;
import y.layout.organic.OrganicLayouter;
import y.layout.orthogonal.OrthogonalGroupLayouter;
import y.layout.random.RandomLayouter;
import y.layout.router.OrthogonalEdgeRouter;
import y.layout.tree.ARTreeLayouter;
import y.layout.tree.TreeReductionStage;

import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;

/**
 * <p>
 * Description: 拓扑图布局渲染器
 * </p>
 * 
 * @author baiyanwei
 * @version
 * 
 *          <p>
 *          History:
 * 
 *          Date Author Version Description
 *          --------------------------------------
 *          ------------------------------------------- 2012-12-28 下午6:12:41
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class TopologyLayoutRender {
	private static TopologyLayoutRender topologyLayoutRender = null;
	public static final String HIERARCHIC = "hierarchic";
	public static final String ORTHOGONAL = "orthogonal";
	public static final String ORGANIC = "organic";
	public static final String CIRCULAR = "circular";
	public static final String RANDOM = "random";
	public static final String TREE = "tree";

	// private static final String[] LAYOUTS = { HIERARCHIC, ORTHOGONAL,
	// ORGANIC, CIRCULAR, RANDOM };

	public static TopologyLayoutRender getInstance() {
		if (topologyLayoutRender == null) {
			topologyLayoutRender = new TopologyLayoutRender();
		}
		return topologyLayoutRender;
	}

	private TopologyLayoutRender() {

	}

	/**
	 * 处理布局
	 * 
	 * @param graph
	 * @param referentMap
	 */
	public void renderLayout(StyledLayoutGraph graph, HttpServletRequest request) {
		if (request == null) {
			return;
		}
		String layoutStyle = request.getParameter("layoutStyle");
		renderLayout(graph, layoutStyle);
	}

	public void renderLayout(StyledLayoutGraph graph, String layoutStyle) {

		if (graph == null || layoutStyle == null) {
			return;
		}

		Layouter layouter = createLayouter(layoutStyle);
		if (layouter == null) {
			return;
		}
		new BufferedLayouter(layouter).doLayout(graph);
	}

	/**
	 * 根据要求的样式创建一个布局
	 * 
	 * @param style
	 * @return
	 */
	private Layouter createLayouter(String style) {
		Layouter layouter = null;
		if (HIERARCHIC.equals(style)) {
			IncrementalHierarchicLayouter ihl = new IncrementalHierarchicLayouter();
			ihl.setIntegratedEdgeLabelingEnabled(false);
			ihl.setLabelLayouterEnabled(true);
			ihl.setAutomaticEdgeGroupingEnabled(true);
			ihl.setGroupAlignmentPolicy(IncrementalHierarchicLayouter.POLICY_ALIGN_GROUPS_BOTTOM);
			ihl.getNodeLayoutDescriptor().setNodeLabelMode(NodeLayoutDescriptor.NODE_LABEL_MODE_CONSIDER_FOR_DRAWING);
			ihl.setRecursiveGroupLayeringEnabled(true);
			ihl.setConsiderNodeLabelsEnabled(true);
			ihl.setNodeToEdgeDistance(60);
			ihl.setNodeToNodeDistance(36);
			ihl.setMinimumLayerDistance(50);
			((SimplexNodePlacer) ihl.getNodePlacer()).setGroupCompactionStrategy(SimplexNodePlacer.GROUP_COMPACTION_NONE);
			layouter = ihl;
		} else if (ORTHOGONAL.equals(style)) {
			OrthogonalGroupLayouter orthogonalGroupLayouter = new OrthogonalGroupLayouter();
			// orthogonalGroupLayouter.setIntegratedEdgeLabelingEnabled(labelLayout);
			layouter = orthogonalGroupLayouter;
		} else if (ORGANIC.equals(style)) {
			layouter = new OrganicLayouter();
		} else if (CIRCULAR.equals(style)) {
			layouter = new CircularLayouter();
		} else if (RANDOM.equals(style)) {
			RandomLayouter rl = new RandomLayouter();
			rl.setLabelLayouterEnabled(false);
			layouter = rl;
		} else if (TREE.equals(style)) {
			ARTreeLayouter grl = new ARTreeLayouter();
			grl.setAspectRatio(1);
			grl.setHorizontalSpace(40);
			grl.setVerticalSpace(35);
			TreeReductionStage trs = new TreeReductionStage();
			OrthogonalEdgeRouter orthogonal = new OrthogonalEdgeRouter();
			orthogonal.setCrossingCost(1.0);
			orthogonal.setReroutingEnabled(true);
			orthogonal.setSphereOfAction(OrthogonalEdgeRouter.ROUTE_SELECTED_EDGES);
			// trs.setNonTreeEdgeSelectionKey(orthogonal.getSelectedEdgesDpKey());
			// trs.setNonTreeEdgeRouter(orthogonal);
			grl.appendStage(trs);
			// new BufferedLayouter(grl).doLayout(graph);
			// grl.removeStage(trs);
			layouter = grl;
		}
		if (layouter instanceof CanonicMultiStageLayouter) {
			GreedyMISLabeling labelLayouter = new GreedyMISLabeling();
			labelLayouter.setPlaceEdgeLabels(true);
			labelLayouter.setPlaceNodeLabels(false);
			((CanonicMultiStageLayouter) layouter).setLabelLayouter(labelLayouter);
			// ((CanonicMultiStageLayouter)
			// layouter).setLabelLayouterEnabled(labelLayout);
		}
		return new BufferedLayouter(new MinNodeSizeStage(layouter));
	}
}
