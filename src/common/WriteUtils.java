package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class WriteUtils {
	public static <T extends Collection<E>, E> void writeToFile(T coll, String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		for (E elem: coll) {
			writer.write(elem.toString() + "\n");
			writer.flush();
		}
		writer.close();
	}
}
