/*
 * Copyright (c) 2012-2014, Peter Abeles. All Rights Reserved.
 *
 * This file is part of DDogleg (http://ddogleg.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ddogleg.nn.performance;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import org.ddogleg.nn.FactoryNearestNeighbor;
import org.ddogleg.nn.NearestNeighbor;
import org.ddogleg.nn.NnData;
import org.ddogleg.nn.data.NnDataGenerator;
import org.ddogleg.struct.FastQueue;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Tests VpTree performance using JUnitBenchmark
 */
public class TestVpTreePerformance extends AbstractBenchmark {
	public static final int MAX_POINTS = 200000;
	public static final int SEARCH_COUNT = 10000;
	public static final int SAMPLE_SIZE = 150000;
	public static final int K = 10;

	private static Random random = new Random();
	private static Map<Integer, List<double[]>> randomPoints = new HashMap<Integer, List<double[]>>() {{
		put(2, NnDataGenerator.createRandom(2, MAX_POINTS));
		put(3, NnDataGenerator.createRandom(3, MAX_POINTS));
		put(10, NnDataGenerator.createRandom(10, MAX_POINTS));
	}};

	private static List<double[]> circlePoints = NnDataGenerator.createCircle(MAX_POINTS);
	private static Map<Integer, List<double[]>> linePoints = new HashMap<Integer, List<double[]>>() {{
		put(2, NnDataGenerator.createLine(2, MAX_POINTS));
		put(3, NnDataGenerator.createLine(3, MAX_POINTS));
		put(10, NnDataGenerator.createRandom(10, MAX_POINTS));
	}};
	private static List<double[]> planePoints = NnDataGenerator.createPlane(MAX_POINTS);

	@Test
	public void testVpTreeRandom2d() {
		testPoints(randomPoints.get(2).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeRandom2d() {
		testPoints(randomPoints.get(2).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreeRandom3d() {
		testPoints(randomPoints.get(3).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeRandom3d() {
		testPoints(randomPoints.get(3).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreeRandom10d() {
		testPoints(randomPoints.get(10).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeRandom10d() {
		testPoints(randomPoints.get(10).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreeCircle() {
		testPoints(circlePoints.subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeCircle() {
		testPoints(circlePoints.subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreePlane() {
		testPoints(circlePoints.subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreePlane() {
		testPoints(circlePoints.subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreeLine2d() {
		testPoints(linePoints.get(2).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeLine2d() {
		testPoints(linePoints.get(2).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreeLine3d() {
		testPoints(linePoints.get(3).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeLine3d() {
		testPoints(linePoints.get(3).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}

	@Test
	public void testVpTreeLine10d() {
		testPoints(linePoints.get(10).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.vptree(), SEARCH_COUNT, K);
	}

	@Test
	public void testKdTreeLine10d() {
		testPoints(linePoints.get(10).subList(0, SAMPLE_SIZE), FactoryNearestNeighbor.kdtree(), SEARCH_COUNT, K);
	}
        
    public static void main(String... args) {
		new TestVpTreePerformance().testVpTreeCircle();
	}

	private void testPoints(List<double[]> points, NearestNeighbor nnsearch, int searches, int k) {
		int dimensions = points.get(0).length;
		nnsearch.init(dimensions);
		nnsearch.setPoints(points, null);
		for (int i = 0; i < searches; i++) {
			double[] pt = new double[dimensions];
			for (int j = 0; j < dimensions; j++) {
				pt[j] = random.nextDouble();
			}

			if (k == 1) {
				NnData<Object> nearest = new NnData<Object>();
				nnsearch.findNearest(pt, 0.5, nearest);
			} else {
				FastQueue<NnData> nearest = new FastQueue<NnData>(k, NnData.class, true);
				nnsearch.findNearest(pt, 0.5, k, nearest);
			}
		}
	}
}
