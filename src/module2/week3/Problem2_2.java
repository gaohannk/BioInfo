package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static common.PrintUtils.printListByLine;
import static module2.week3.Problem2.PeptideEncodingProblem;

public class Problem2_2 {

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/Bacillus_brevis.txt"), Charset.forName("UTF-8"));
		StringBuilder DNA = new StringBuilder();
		String[] splits = text.split("\n");
		for (int i = 0; i < splits.length; i++) {
			DNA.append(splits[i]);
		}
		String peptide = "VKLFPWFNQY";
		printListByLine(PeptideEncodingProblem(DNA.toString(), peptide));
	}
}
