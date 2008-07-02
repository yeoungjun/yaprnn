package yaprnn.gui;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
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
class NetworkTreeModel implements TreeModel {

	final static ImageIcon ICON_MLP = loadIcon("/yaprnn/gui/view/iconMLP.png");
	final static ImageIcon ICON_NEURON = loadIcon("/yaprnn/gui/view/iconNeuron.png");
	final static ImageIcon ICON_LAYER = loadIcon("/yaprnn/gui/view/iconLayer.png");
	final static ImageIcon ICON_AVF = loadIcon("/yaprnn/gui/view/iconAVF.png");
	final static ImageIcon ICON_PROCESSED = loadIcon("/yaprnn/gui/view/iconProcessed.png");
	final static ImageIcon ICON_UNPROCESSED = loadIcon("/yaprnn/gui/view/iconUnProcessed.png");
	final static ImageIcon ICON_TRAININGSET = loadIcon("/yaprnn/gui/view/iconFolderTraining.png");
	final static ImageIcon ICON_TESTSET = loadIcon("/yaprnn/gui/view/iconFolderTest.png");
	final static ImageIcon ICON_DATASETS = loadIcon("/yaprnn/gui/view/iconFolderDataSet.png");
	final static ImageIcon ICON_OPENED = loadIcon("/yaprnn/gui/view/iconFolderWhite.png");
	final static ImageIcon ICON_CLOSED = loadIcon("/yaprnn/gui/view/iconFolderGrey.png");

	/**
	 * RootNode is the root node containing a NetworkListNode and DatasetsNode.
	 */
	private class RootNode extends ModelNode {

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
	private class NetworksNode extends ModelNode {

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
	private class NetworkNode extends ModelNode {

		private NeuralNetwork network;

		NetworkNode(NeuralNetwork network) {
			super(ICON_MLP, network.getName());
			this.network = network;
		}

		NeuralNetwork getNetwork() {
			return network;
		}

		// TODO : methods

	}

	/**
	 * DatasetsNode is a node that displays a DatasetsLoadedNode and a list of
	 * NetTrainingTestSetsNodes linked with all loaded networks in the tree
	 * model.
	 */
	private class DatasetsNode extends ModelNode {

		private DataSetNode ldn;

		private List<NeuralNetwork> nets;
		private Dictionary<NeuralNetwork, NetworkSetsNode> setsNodes;

		public DatasetsNode(DataSetNode ldn, List<NeuralNetwork> nets,
				Dictionary<NeuralNetwork, NetworkSetsNode> setsNodes) {
			super(null, "Datasets");
			this.ldn = ldn;
			this.nets = nets;
		}

		@Override
		ModelNode getChild(int index) {
			return (index == 0) ? ldn : setsNodes.get(nets.get(index));
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
	private class DataSetNode extends ModelNode {

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
	private class DataNode extends ModelNode {

		private Data data;

		public DataNode(Data data) {
			super(null, data.getName());
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
	private class NetworkSetsNode extends ModelNode {

		private NeuralNetwork network;
		private DataSetNode trainingSetNode;
		private DataSetNode testSetNode;

		NetworkSetsNode(NeuralNetwork network, List<Data> trainingSet,
				List<Data> testSet) {
			super(ICON_MLP, "for " + network.getName());
			this.network = network;
			trainingSetNode = new DataSetNode("Training set", trainingSet);
			testSetNode = new DataSetNode("Test set", testSet);
			trainingSetNode.setIcon(ICON_TRAININGSET);
			trainingSetNode.setIcon(ICON_TESTSET);
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
	private void updateSubTree(Object[] nodes) {
		TreeModelEvent e = new TreeModelEvent(this, nodes);
		for (TreeModelListener tml : listeners)
			tml.treeStructureChanged(e);
	}

	/**
	 * Adds a NeuralNetwork to the tree model.
	 * 
	 * @param n
	 *            the network to add
	 */
	public void add(NeuralNetwork n) {
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

			updateSubTree(new Object[] { netsNode, datasetsNode });
		}
	}

	/**
	 * Removes a NeuralNetwork from the tree model.
	 * 
	 * @param n
	 *            the network to remove
	 */
	public void remove(NeuralNetwork n) {
		if (nets.contains(n)) {
			nets.remove(n);

			// Training- und Testset entferne
			trainingSets.remove(n);
			testSets.remove(n);

			// Dynamische Knoten entfernen
			netsNodes.remove(n);
			setsNodes.remove(n);

			updateSubTree(new Object[] { netsNode, datasetsNode });
		}
	}

	/**
	 * Adds a Data-Object to the tree model.
	 * 
	 * @param d
	 *            the data to add
	 */
	public void add(Data d) {
		if (!loadedData.contains(d)) {
			loadedData.add(d);

			// Dynamische Knoten erstellen
			loadedNode.add(d);

			updateSubTree(new Object[] { loadedNode });
		}
	}

	/**
	 * Removes a Data-Object from the tree model.
	 * 
	 * @param d
	 *            the data to remove
	 */
	public void remove(Data d) {
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

			updateSubTree(new Object[] { datasetsNode });
		}
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

	/**
	 * Loads an icon from a location and resizes it to a default size to fit the
	 * tree optic.
	 * 
	 * @param location
	 *            location to load from
	 * @return the icon
	 */
	private static ImageIcon loadIcon(String location) {
		return new ImageIcon(GUI.resizeImage(new ImageIcon(Class.class
				.getResource(location)).getImage(), 22, 22));
	}

}
