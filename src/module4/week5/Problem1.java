package module4.week5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Problem1 {
	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module4/dataset_11813_2.txt"), Charset.forName("UTF-8"));
		String line = text.replace("\n", "");
		List<Integer> spectrum = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
	}
}
