package common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ReadUtils {
	public static List<String> readKmerList(String path) throws IOException {
		String file = Files.readString(Path.of(path), Charset.forName("UTF-8"));
		String splits[] = file.split("\n");
		try {
			Integer.parseInt(splits[0]);
		} catch (NumberFormatException e) {
			return readKmerListFrom(splits, 0);
		}
		return readKmerListFrom(splits, 1);
	}

	private static List<String> readKmerListFrom(String[] splits, int start) {
		List<String> patterns = new LinkedList<>();
		for (int i = start; i < splits.length; i++) {
			patterns.add(splits[i]);
		}
		return patterns;
	}
}
