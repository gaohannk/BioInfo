package module3.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static module3.week5.Problem2.CycleToChromosome;

public class Problem4 {
	/**
	 * GraphToGenome(GenomeGraph)
	 *      P ← an empty set of chromosomes
	 *      for each cycle Nodes in GenomeGraph
	 *           Nodes﻿ ← sequence of nodes in this cycle (starting from node 1)
	 *           Chromosome ← CycleToChromosome(Nodes)
	 *           add Chromosome to P
	 *      return P
	 */
	/**
	 * Code Challenge: Implement GraphToGenome.
	 * <p>
	 * Input: The colored edges ColoredEdges of a genome graph.
	 * Output: The genome P corresponding to this genome graph.
	 */
	public static List<List<Integer>> GraphToGenome(List<String> input) {
		List<List<Integer>> genome = new LinkedList<>();
		List<Integer> nodes = new LinkedList<>();
		int head = Integer.MIN_VALUE;
		for (String edge : input) {
			if (nodes.size() == 0) {
				head = Integer.parseInt(edge.split(", ")[0]);
			}
			int node = Integer.parseInt(edge.split(", ")[0]);
			int node2 = Integer.parseInt(edge.split(", ")[1]);
			if (head % 2 == 0 && head - node2 == 1
					|| head % 2 == 1 && head - node2 == -1) {

				nodes.add(node);
				nodes.add(0, node2); // Add to the front of this cycle
				List<Integer> chromosome = CycleToChromosome(nodes);
				genome.add(chromosome);
				nodes = new LinkedList<>();
			} else {
				nodes.add(node);
				nodes.add(node2);
			}
		}
		return genome;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_8222_8.txt"), Charset.forName("UTF-8"));
		List<String> input = Arrays.stream(text.substring(1, text.length() - 2).split("\\), \\(")).collect(Collectors.toList());
		List<List<Integer>> genome = GraphToGenome(input);
		System.out.println(genome.stream().map(
				l -> "(" + l.stream().map(e -> e > 0 ? "+" + e : "" + e).collect(Collectors.joining(" ")) + ")"
		).collect(Collectors.joining()));
	}
}
