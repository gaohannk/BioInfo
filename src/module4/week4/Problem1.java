package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constant.nucle;
import static module2.common.Constant.MASSTABLE;
import static module2.common.Constant.MASSTABLE_REV;

public class Problem1 {
	/**
	 * CODE CHALLENGE: Construct the graph of a spectrum.
	 * Given: A space-delimited list of integers Spectrum.
	 * Return: Graph(Spectrum).
	 * <p>
	 * Note: Throughout this chapter, all dataset problems implicitly use the standard integer-valued mass table for the regular twenty amino acids. Examples sometimes use the toy amino acid alphabet {X, Z} whose masses are 4 and 5, respectively.
	 * <p>
	 * Extra Dataset
	 */

	public static Map<Integer, List<Integer>> graphOfSpectrum(List<Integer> spectrum) {
		Map<Integer, List<Integer>> map = new TreeMap<>();
		List<Integer> iter= new LinkedList<>(spectrum);
		iter.add(0);
		for (int mass1 : iter) {
			for (int mass2 : iter) {
				if (mass1 < mass2 && MASSTABLE_REV.containsKey(mass2 - mass1)) {
					if (map.containsKey(mass1)) {
						map.get(mass1).add(mass2);
					} else {
						List<Integer> values = new LinkedList<>();
						values.add(mass2);
						map.put(mass1, values);
					}
				}
			}
		}
		return map;
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_11813_2.txt"), Charset.forName("UTF-8"));
		String line = text.replace("\n", "");
		List<Integer> spectrum = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
		Map<Integer, List<Integer>> map = graphOfSpectrum(spectrum);
		printMap(map);

	}

	private static void printMap(Map<Integer, List<Integer>> map) {
		for (int mass1 : map.keySet()) {
			for (int mass2 : map.get(mass1)) {
				System.out.println(mass1 + "->" + mass2 + ":" + MASSTABLE_REV.get(mass2 - mass1));
			}
		}
	}
}
