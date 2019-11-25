package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Problem3 {

	private static Object CyclopeptideSequencingProblem(int n) {
		return n * (n - 1);
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module2/dataset_98_3.txt"), Charset.forName("UTF-8"));
		System.out.println(CyclopeptideSequencingProblem(Integer.parseInt(text.replace("\n", ""))));
	}
}
