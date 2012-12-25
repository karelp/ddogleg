package org.ddogleg.nn;

import org.ddogleg.nn.alg.KdTreeSearchBbf;
import org.ddogleg.nn.wrap.WrapExhaustiveNeighbor;
import org.ddogleg.nn.wrap.WrapKdTree;

/**
 * Factory for creating implementations of {@link NearestNeighbor}.
 *
 * @author Peter Abeles
 */
public class FactoryNearestNeighbor {

	public static <D> NearestNeighbor<D> kdtree() {
		return new WrapKdTree<D>();
	}

	public static <D> NearestNeighbor<D> kdtree( int numNodes ) {
		return new WrapKdTree<D>(new KdTreeSearchBbf(numNodes));
	}

	public static <D> NearestNeighbor<D> exhaustive() {
		return new WrapExhaustiveNeighbor<D>();
	}
}
