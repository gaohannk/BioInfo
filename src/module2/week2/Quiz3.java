package module2.week2;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static common.PrintUtils.printListInOneline;
import static module2.common.PrintUtils.printPairReads;
import static module2.week2.Problem7.*;

public class Quiz3 {

	public static void main(String[] args) throws Exception {
		String file = Files.readString(Path.of("./resource/module2/quiz3.txt"), Charset.forName("UTF-8"));
		String splits[] = file.split("\n");
		List<String> kmer = Arrays.stream(splits)
				.map(e-> e.substring(1, e.length()-1))
				.collect(Collectors.toList());
		printListInOneline(kmer);
		List<Pair<String>> pairReads = constructPairReads(kmer);
		printPairReads(pairReads);
		System.out.println(StringReconstructionFromReadPairsProblem(3, 1, pairReads));
	}
}

