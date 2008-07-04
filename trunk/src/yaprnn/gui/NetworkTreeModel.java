package yaprnn.gui;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import yaprnn.dvv.Data;
import yaprnn.mlp.NeuralNetwork;

// The structure is organized according to the following:
// root
// |-Networks
// | |-Network 1
// | |...
// | |-Network n
// |-Datasets
// _ |-Loaded
// _ | |-Data 1
// _ | |...
// _ | |-Data m
// _ |-for Network' 1
// _ | |-Training set
// _ | | |-Data' 1
// _ | | |...
// _ | | |-Data' x_1
// _ | |-Test set
// _ | _ |-Data' 1
// _ | _ |...
// _ | _ |-Data' y_1
// _ |...
// _ |-for Network' n
// _ _ |-Training set
// _ _ | |-Data' 1
// _ _ | |...
// _ _ | |-Data' x_n
// _ _ |-Test set
// _ _ _ |-Data' 1
// _ _ _ |...
// _ _ _ |-Data' y_n
/**
 * NetworkTreeModel is used to store and reflect NeuralNetworks and datasets in
 * a JTree.
 */
public class NetworkTreeModel implements TreeModel {

	final static ImageIcon ICON_MLP = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconMLP.png");
	final static ImageIcon ICON_NEURON = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconNeuron.png");
	final static ImageIcon ICON_LAYER = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconLayer.png");
	final static ImageIcon ICON_AVF = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconAVF.png");
	final static ImageIcon ICON_PROCESSED = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconProcessed.png");
	final static ImageIcon ICON_UNPROCESSED = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconUnProcessed.png");
	final static ImageIcon ICON_TRAININGSET = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconFolderTraining.png");
	final static ImageIcon ICON_TESTSET = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconFolderTest.png");
	final static ImageIcon ICON_DATASETS = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconFolderDataSet.png");
	final static ImageIcon ICON_AUDIO = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconLoadAudio.png");
	final static ImageIcon ICON_IMAGE = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconLoadImage.png");
	final static ImageIcon ICON_OPENED = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconFolderWhite.png");
	final static ImageIcon ICON_CLOSED = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconFolderGrey.png");

	/**
	 * Base node class for all nodes in NetworkTreeModel
	 */
	class ModelNode {

		private Icon icon;
		private String label;

		ModelNode(Icon icon, String label) {
			this.icon = icon;
			this.label = label;
		}

		int getIndexOf(ModelNode child) {
			return -1;
		}

		ModelNode getChild(int index) {
			return null;
		}

		int getChildsCount() {
			return 0;
		}

		/**
		 * Returns false when this node can have childs, else true.
		 */
		boolean isLeaf() {
			return false;
		}

		final Icon getIcon() {
			return icon;
		}

		final void setIcon(Icon icon) {
			this.icon = icon;
		}

		@Override
		public String toString() {
			return label;
		}

	}

	/**
	 * RootNode is the root node containing a NetworkListNode and DatasetsNode.
	 */
	class RootNode extends ModelNode {

		private NetworksNode nln;
		private DatasetsNode dn;

		RootNode(NetworksNode nln, DatasetsNode dn) {
			super(null, "ROOT");
			this.nln = nln;
			this.dn = dn;
		}

		@Override
		ModelNode getChild(int index) {
			if (index == 0)
				return nln;
			return (index == 1) ? dn : null;
		}

		@Override
		int getChildsCount() {
			return 2;
		}

		@Override
		int getIndexOf(ModelNode child) {
			if (child == nln)
				return 0;
			return (child == dn) ? 1 : -1;
		}

	}

	/**
	 * NetworkListNode is a node that displays the list of all loaded networks
	 * in the tree model.
	 */
	class NetworksNode extends ModelNode {

		private List<NeuralNetwork> nets;
		private Dictionary<NeuralNetwork, NetworkNode> netsNodes;

		NetworksNode(List<NeuralNetwork> nets,
				Dictionary<NeuralNetwork, NetworkNode> netsNodes) {
			super(null, "Networks");
			this.nets = nets;
			this.netsNodes = netsNodes;
		}

		@Override
		ModelNode getChild(int index) {
			return netsNodes.get(nets.get(index));
		}

		@Override
		int getChildsCount() {
			return nets.size();
		}

		@Override
		int getIndexOf(ModelNode child) {
			if (child instanceof NetworkNode)
				return nets.indexOf(((NetworkNode) child).getNetwork());
			return -1;
		}

	}

	/**
	 * NetworkNode is a node that displays the structure of NeuralNetwork in the
	 * tree model.
	 */
	class NetworkNode extends ModelNode {

		private NeuralNetwork network;
		private List<LayerNode> layerNodes = new Vector<LayerNode>();

		NetworkNode(NeuralNetwork network) {
			super(ICON_MLP, network.getName());
			this.network = network;
			for (int i = 0; i < network.getNumLayers(); i++)
				layerNodes.add(new LayerNode(network, i));
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		/**
		 * Updates the sub nodes due to a change to the NeuralNetwork.
		 */
		void update() {
			// TODO : Update sub nodes
		}

		@Override
		ModelNode getChild(int index) {
			return layerNodes.get(index);
		}

		@Override
		int getChildsCount() {
			return layerNodes.size();
		}

		@Override
		int getIndexOf(ModelNode child) {
			return layerNodes.indexOf(child);
		}

	}

	/**
	 * LayerNode is a node that displays one layer of a NeuralNetwork.
	 */
	class LayerNode extends ModelNode {

		private NeuralNetwork network;
		private int layerIndex;
		private NeuronsNode neuronsNode;
		private AVFNode avfNode;
		private BiasNode biasNode;

		LayerNode(NeuralNetwork network, int layerIndex) {
			super(ICON_LAYER, (layerIndex == 0 || layerIndex + 1 == network
					.getNumLayers()) ? (layerIndex == 0 ? "Input layer"
					: "Output layer") : "Layer " + layerIndex);
			this.network = network;
			this.layerIndex = layerIndex;
			neuronsNode = new NeuronsNode(network, layerIndex);
			avfNode = new AVFNode(network, layerIndex);
			biasNode = new BiasNode(network, layerIndex);
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		int getLayerIndex() {
			return layerIndex;
		}

		@Override
		ModelNode getChild(int index) {
			if (index == 0)
				return neuronsNode;
			if (index == 1)
				return avfNode;
			return (index == 2) ? biasNode : null;
		}

		@Override
		int getChildsCount() {
			return 3;
		}

		@Override
		int getIndexOf(ModelNode child) {
			if (child == neuronsNode)
				return 0;
			if (child == avfNode)
				return 1;
			return (child == biasNode) ? 2 : -1;
		}

	}

	/**
	 * NeuronsNode is a node that represents the count of neurons in one layer
	 * of a NeuralNetwork.
	 */
	class NeuronsNode extends ModelNode {

		private NeuralNetwork network;
		private int layerIndex;

		NeuronsNode(NeuralNetwork network, int layerIndex) {
			super(ICON_NEURON, "Neuron count: "
					+ network.getLayerSize(layerIndex));
			this.network = network;
			this.layerIndex = layerIndex;
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		int getLayerIndex() {
			return layerIndex;
		}

		@Override
		boolean isLeaf() {
			return true;
		}

	}

	/**
	 * AVFNode is a node that represents the activation function of one layer of
	 * a NeuralNetwork.
	 */
	class AVFNode extends ModelNode {

		private NeuralNetwork network;
		private int layerIndex;

		AVFNode(NeuralNetwork network, int layerIndex) {
			super(ICON_AVF, "AVF: " + network.getActivationFunction(layerIndex));
			this.network = network;
			this.layerIndex = layerIndex;
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		int getLayerIndex() {
			return layerIndex;
		}

		@Override
		boolean isLeaf() {
			return true;
		}

	}

	/**
	 * BiasNode is a node that represents the bias of one layer of a
	 * NeuralNetwork.
	 */
	class BiasNode extends ModelNode {

		private NeuralNetwork network;
		private int layerIndex;

		BiasNode(NeuralNetwork network, int layerIndex) {
			super(ICON_AVF, "AVF: " + network.getBias(layerIndex));
			this.network = network;
			this.layerIndex = layerIndex;
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		int getLayerIndex() {
			return layerIndex;
		}

		@Override
		boolean isLeaf() {
			return true;
		}

	}

	/**
	 * DatasetsNode is a node that displays a DatasetsLoadedNode and a list of
	 * NetTrainingTestSetsNodes linked with all loaded networks in the tree
	 * model.
	 */
	class DatasetsNode extends ModelNode {

		private DataSetNode ldn;

		private List<NeuralNetwork> nets;
		private Dictionary<NeuralNetwork, NetworkSetsNode> setsNodes;

		public DatasetsNode(DataSetNode ldn, List<NeuralNetwork> nets,
				Dictionary<NeuralNetwork, NetworkSetsNode> setsNodes) {
			super(null, "Datasets");
			this.ldn = ldn;
			this.nets = nets;
			this.setsNodes = setsNodes;
		}

		@Override
		ModelNode getChild(int index) {
			return (index == 0) ? ldn : setsNodes.get(nets.get(index - 1));
		}

		@Override
		int getChildsCount() {
			return 1 + nets.size();
		}

		@Override
		int getIndexOf(ModelNode child) {
			if (child == ldn)
				return 0;
			if (child instanceof NetworkSetsNode) {
				int i = nets.indexOf(((NetworkSetsNode) child).getNetwork());
				return (i == -1) ? -1 : i + 1;
			}
			return -1;
		}

	}

	/**
	 * DataSetNode is a node that displays a set og data objects in the tree
	 * model.
	 */
	class DataSetNode extends ModelNode {

		private List<Data> dataset;
		private Dictionary<Data, DataNode> dataNodes = new Hashtable<Data, DataNode>();

		public DataSetNode(String label, List<Data> dataset) {
			super(ICON_DATASETS, label);
			this.dataset = dataset;
		}

		void add(Data d) {
			dataNodes.put(d, new DataNode(d));
		}

		void remove(Data d) {
			dataNodes.remove(d);
		}

		@Override
		ModelNode getChild(int index) {
			return dataNodes.get(dataset.get(index));
		}

		@Override
		int getChildsCount() {
			return dataset.size();
		}

		@Override
		int getIndexOf(ModelNode child) {
			if (child instanceof DataNode)
				return dataset.indexOf(((DataNode) child).getData());
			return -1;
		}

	}

	/**
	 * DataNode is a node that displays a data object in the tree model.
	 */
	class DataNode extends ModelNode {

		private Data data;

		public DataNode(Data data) {
			super(data.isAudio() ? ICON_AUDIO : ICON_IMAGE, data.getName());
			this.data = data;
		}

		Data getData() {
			return data;
		}

		@Override
		boolean isLeaf() {
			return true;
		}

	}

	/**
	 * NetworkSetsNode is a node that displays training and test sets of
	 * NeuralNetwork.
	 */
	class NetworkSetsNode extends ModelNode {

		private NeuralNetwork network;
		private DataSetNode trainingSetNode;
		private DataSetNode testSetNode;

		NetworkSetsNode(NeuralNetwork network, List<Data> trainingSet,
				List<Data> testSet) {
			super(ICON_MLP, "for " + network.getName());
			this.network = network;
			trainingSetNode = new DataSetNode("Training set", trainingSet);
			trainingSetNode.setIcon(ICON_TRAININGSET);
			testSetNode = new DataSetNode("Test set", testSet);
			testSetNode.setIcon(ICON_TESTSET);
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		DataSetNode getTrainingSetNode() {
			return trainingSetNode;
		}

		DataSetNode getTestSetNode() {
			return testSetNode;
		}

		@Override
		ModelNode getChild(int index) {
			if (index == 0)
				return trainingSetNode;
			return (index == 1) ? testSetNode : null;
		}

		@Override
		int getChildsCount() {
			return 2;
		}

		@Override
		int getIndexOf(ModelNode child) {
			if (child == trainingSetNode)
				return 0;
			return (child == testSetNode) ? 1 : -1;
		}

	}

	// Listeners
	private Collection<TreeModelListener> listeners = new Vector<TreeModelListener>();

	// Speicher für die eigentlichen Netzwerke und Daten
	private List<NeuralNetwork> nets = new Vector<NeuralNetwork>();
	private List<Data> loadedData = new Vector<Data>();
	private Dictionary<NeuralNetwork, List<Data>> trainingSets = new Hashtable<NeuralNetwork, List<Data>>();
	private Dictionary<NeuralNetwork, List<Data>> testSets = new Hashtable<NeuralNetwork, List<Data>>();

	// Dynamische Knoten
	private Dictionary<NeuralNetwork, NetworkNode> netsNodes = new Hashtable<NeuralNetwork, NetworkNode>();
	private Dictionary<NeuralNetwork, NetworkSetsNode> setsNodes = new Hashtable<NeuralNetwork, NetworkSetsNode>();

	// Statische Knoten
	private DataSetNode loadedNode = new DataSetNode("Loaded", loadedData);
	private DatasetsNode datasetsNode = new DatasetsNode(loadedNode, nets,
			setsNodes);
	private NetworksNode netsNode = new NetworksNode(nets, netsNodes);
	private RootNode rootNode = new RootNode(netsNode, datasetsNode);

	/**
	 * Fires event to all listeners that the tree structure has changed.
	 */
	private void fireStructureChanged(Object node) {
		TreeModelEvent e = new TreeModelEvent(this, new Object[] { node });
		for (TreeModelListener tml : listeners)
			tml.treeStructureChanged(e);
	}

	/**
	 * Adds a NeuralNetwork to the tree model.
	 * 
	 * @param n
	 *            the network to add
	 */
	void add(NeuralNetwork n) {
		if (!nets.contains(n)) {
			nets.add(n);

			// Training- und Testset anlegen
			List<Data> trainingSet = new Vector<Data>();
			List<Data> testSet = new Vector<Data>();
			trainingSets.put(n, trainingSet);
			testSets.put(n, testSet);

			// Dynamische Knoten erstellen
			netsNodes.put(n, new NetworkNode(n));
			setsNodes.put(n, new NetworkSetsNode(n, trainingSet, testSet));

			fireStructureChanged(netsNode);
			fireStructureChanged(datasetsNode);
		}
	}

	/**
	 * Removes a NeuralNetwork from the tree model.
	 * 
	 * @param n
	 *            the network to remove
	 */
	void remove(NeuralNetwork n) {
		if (nets.contains(n)) {
			nets.remove(n);

			// Training- und Testset entferne
			trainingSets.remove(n);
			testSets.remove(n);

			// Dynamische Knoten entfernen
			netsNodes.remove(n);
			setsNodes.remove(n);

			fireStructureChanged(netsNode);
			fireStructureChanged(datasetsNode);
		}
	}

	/**
	 * Adds a Data-Object to the tree model.
	 * 
	 * @param d
	 *            the data to add
	 */
	void add(Data d) {
		if (!loadedData.contains(d)) {
			loadedData.add(d);

			// Dynamische Knoten erstellen
			loadedNode.add(d);

			fireStructureChanged(loadedNode);
		}
	}

	/**
	 * Removes a Data-Object from the tree model.
	 * 
	 * @param d
	 *            the data to remove
	 */
	void remove(Data d) {
		if (loadedData.contains(d)) {
			loadedData.remove(d);

			// Dynamische Knoten entfernen
			loadedNode.remove(d);

			// Eventuell für ein Training oder Test eingesetzte Data-Objekte
			// aus den Listen entfernen
			for (NeuralNetwork n : nets) {
				NetworkSetsNode nsn = setsNodes.get(n);
				nsn.getTrainingSetNode().remove(d);
				nsn.getTestSetNode().remove(d);
			}

			fireStructureChanged(datasetsNode);
		}
	}

	/**
	 * Returns a copy of the list of networks loaded
	 * 
	 * @return copy of the list nets
	 */
	List<NeuralNetwork> getNetworks() {
		return new Vector<NeuralNetwork>(nets);
	}

	/**
	 * Returns a copy of the list of data loaded
	 * 
	 * @return copy of the list
	 */
	List<Data> getDatasets() {
		return new Vector<Data>(loadedData);
	}

	/**
	 * Returns a copy of the training set of a network
	 * 
	 * @return copy of a training set
	 */
	List<Data> getTrainingset(NeuralNetwork ofNetwork) {
		return new Vector<Data>(trainingSets.get(ofNetwork));
	}

	/**
	 * Returns a copy of the test set of a network
	 * 
	 * @return copy of a test set
	 */
	List<Data> getTestset(NeuralNetwork ofNetwork) {
		return new Vector<Data>(testSets.get(ofNetwork));
	}

	DataSetNode getLoadedNode() {
		return loadedNode;
	}

	DatasetsNode getDatasetsNode() {
		return datasetsNode;
	}

	NetworksNode getNetsNode() {
		return netsNode;
	}

	RootNode getRootNode() {
		return rootNode;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof ModelNode)
			return ((ModelNode) parent).getChildsCount();
		return 0;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof ModelNode)
			return ((ModelNode) parent).getChild(index);
		return null;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof ModelNode && child instanceof ModelNode)
			return ((ModelNode) parent).getIndexOf((ModelNode) child);
		return -1;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node instanceof ModelNode)
			return ((ModelNode) node).isLeaf();
		return true;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getRoot() {
		return rootNode;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		if (listeners.contains(l))
			listeners.remove(l);
	}

}
