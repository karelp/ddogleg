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

package org.ddogleg.nn.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains algorithms for generating data for nn-search algorithms.
 */
public class NnDataGenerator {
	private static Random random = new Random(42);

	public static List<double[]> createRandom(int dimensions, int points) {
		ArrayList<double[]> res = new ArrayList<double[]>(points);
		for (int i = 0; i < points; i++) {
			double[] pt = new double[dimensions];
			for (int j = 0; j < dimensions; j++) {
				pt[j] = random.nextDouble();
			}
			res.add(pt);
		}
		return res;
	}

	public static List<double[]> createLine(int dimensions, int points) {
		final double[] dir = new double[dimensions], origin = new double[dimensions];
		for (int i = 0; i < dimensions; i++) {
			dir[i] = random.nextDouble();
			origin[i] = random.nextDouble();
		}

		ArrayList<double[]> res = new ArrayList<double[]>(points);
		for (int i = 0; i < points; i++) {
			double[] pt = new double[dimensions];
			double k = random.nextDouble();
			for (int j = 0; j < dimensions; j++) {
				pt[j] = dir[j] * k + origin[j];
			}
			res.add(pt);
		}
		return res;
	}

	public static List<double[]> createCircle(int points) {
		double[] origin = { 0, 0 };
		double r = 1;
		ArrayList<double[]> res = new ArrayList<double[]>(points);
		for (int i = 0; i < points; i++) {
			double phi = i / (2 * Math.PI * points);
			res.add(new double[] { r * Math.cos(phi) + origin[0], r * Math.sin(phi) + origin[1] });
		}
		Collections.shuffle(res);
		return res;
	}

	public static List<double[]> createPlane(int points) {

		List<double[]> result = new ArrayList<double[]>();
		double[] v1 = new double[] { random.nextDouble(), random.nextDouble(), random.nextDouble() };
		double[] v2 = new double[] { random.nextDouble(), random.nextDouble(), random.nextDouble() };
		for (int i = 0; i < points; i++) {
			double k = random.nextDouble();
			double l = random.nextDouble();
			result.add(new double[] {k * v1[0] + l * v2[0], k * v1[1] + l * v2[0], k * v1[2] + l * v2[2]});
		}

		return result;
	}
}
