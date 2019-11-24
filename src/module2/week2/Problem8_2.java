package module2.week2;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.*;
import static common.ReadUtils.readKmerList;
import static module2.week1.Problem5.DeBruijnGraphFromkmersProblem;
import static module2.week2.Problem8.MaximalNonBranchingPaths;

public class Problem8_2 {
	/**
	 * Contig Generation Problem: Generate the contigs from a collection of reads (with imperfect coverage).
	 * Input: A collection of k-mers Patterns.
	 * Output: All contigs in DeBruijn(Patterns).
	 *
	 * @throws IOException
	 */
	public static List<String> ContigGenerationProblem(List<String> kmerlist) {
		Map<String, List<String>> graph = DeBruijnGraphFromkmersProblem(kmerlist);
		List<List<String>> paths = MaximalNonBranchingPaths(graph);
		return fromPathToContigs(paths);
	}

    public static List<String> fromPathToContigs(List<List<String>> paths) {
        return paths.stream().map(path -> {
            String prefix = path.get(0).substring(0, path.get(0).length() - 1);
            String follow = path.stream().map(a -> a.substring(a.length() - 1)).reduce((a, b) -> (a + b)).get();
            return prefix + follow;
        }).collect(Collectors.toList());
    }


	public static void main(String[] args) throws IOException {
		List<String> kmerlist = readKmerList("./resource/module2/dataset_205_5.txt");
		List<String> contigs = ContigGenerationProblem(kmerlist);
        printListByLine(contigs);
	}
}
