package org.ddogleg.nn.alg;

import java.util.ArrayList;
import java.util.List;

/**
 * Memory management for recycling KdTree data structures.
 *
 * @author Peter Abeles
 */
public class KdTreeMemory {

	// unused structures that are available for use
	protected List<KdTree.Node> unusedNodes = new ArrayList<KdTree.Node>();
	protected List<KdTree> unusedTrees = new ArrayList<KdTree>();

	// list of open nodes when recycling a tree
	protected List<KdTree.Node> open = new ArrayList<KdTree.Node>();

	/**
	 * Returns a new node.  All object references can be assumed to be null.
	 * @return
	 */
	public KdTree.Node requestNode() {
		if( unusedNodes.isEmpty() )
			return new KdTree.Node();
		return unusedNodes.remove( unusedNodes.size()-1);
	}

	/**
	 * Request a leaf node be returned.  All data parameters will be automatically assigned appropriate
	 * values for a leaf.
	 */
	public KdTree.Node requestNode(  double[] point , Object data ) {
		KdTree.Node n = requestNode();
		n.point = point;
		n.data = data;
		n.split = -1;
		return n;
	}

	public KdTree requestTree() {
		if( unusedTrees.isEmpty() )
			return new KdTree();
		return unusedTrees.remove( unusedTrees.size()-1);
	}

	public void recycle( KdTree.Node node ) {
		// null to avoid potential memory leaks
		node.point = null;
		node.left = null;
		node.right = null;
		unusedNodes.add(node);
	}

	public void recycleGraph( KdTree tree ) {
		// step through the graph and recycle each node
		open.add( tree.root );

		while( !open.isEmpty() ) {
			KdTree.Node n = open.remove( open.size()-1 );
			if( n.left != null )
				open.add( n.left );
			if( n.right != null )
				open.add( n.right );

			recycle(n);
		}

		tree.root = null;
		unusedTrees.add(tree);
	}


}
