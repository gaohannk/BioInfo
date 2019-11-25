package module2.week3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
	public static Map<String, Character> CONDONTABLE = new HashMap<>();
	public static Map<Character, Integer> MASSTABLE = new HashMap<>();
	public static List<Character> Alphabet = List.of('G', 'A', 'S', 'P', 'V', 'T', 'C', 'I', 'L', 'N', 'D', 'K', 'Q', 'E', 'M', 'H', 'F', 'R', 'Y', 'W');
	public static List<Character> NoDupMassAlphabet = List.of('G', 'A', 'S', 'P', 'V', 'T', 'C', 'I', 'N', 'D', 'K', 'E', 'M', 'H', 'F', 'R', 'Y', 'W');

	static
	{
		String text = null;
		try {
			text = Files.readString(Path.of("./resource/common/RNA_codon_table_1.txt"), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : text.split("\n")) {
			if (line.split(" ").length == 2) {
				CONDONTABLE.put(line.split(" ")[0], line.split(" ")[1].charAt(0));
			}
		}

		try {
			text = Files.readString(Path.of("./resource/common/integer_mass_table.txt"), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : text.split("\n")) {
			if (line.split(" ").length == 2) {
				MASSTABLE.put(line.split(" ")[0].charAt(0), Integer.parseInt(line.split(" ")[1]));
			}
		}
	}
}
