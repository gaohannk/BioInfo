package module4.week4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module2.common.Constant.MASSTABLE;
import static module2.common.Constant.MASSTABLE_REV;
import static module4.week4.Problem1.graphOfSpectrum;

public class Problem2 {

    /**
     CODE CHALLENGE: Solve the Decoding an Ideal Spectrum Problem.
     Given: A space-delimited list of integers Spectrum.
     Return: An amino acid string that explains Spectrum.
     */
    /**
     * DecodingIdealSpectrum(Spectrum)
     * construct Graph(Spectrum)
     * for each path Path from source to sink in Graph(Spectrum)
     * Peptide ← the amino acid string spelled by the edge labels of Path
     * if IdealSpectrum(Peptide) = Spectrum
     * return Peptide
     *
     * @param spectrum
     */
    public static String DecodingIdealSpectrum(List<Integer> spectrum) {
        Map<Integer, List<Integer>> graph = graphOfSpectrum(spectrum);
        List<String> paths = new LinkedList<>();
        getPeptide(graph, 0, "", paths);
        for (String peptide : paths) {
            if (compareSpectrum(idealSpecturm(peptide), spectrum)) {
                return peptide;
            }
        }
        return "";
    }

    private static boolean compareSpectrum(List<Integer> idealSpectrum, List<Integer> spectrum) {
        if (idealSpectrum.size() == 0 || idealSpectrum.size() != spectrum.size()) {
            return false;
        }
        for (int i = 0; i < idealSpectrum.size(); i++) {
            if (!spectrum.get(i).equals(idealSpectrum.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> idealSpecturm(String peptide) {
        List<Integer> idealSpectrum = new LinkedList<>();
        int sum = 0;
        //dealSpectrum.add(sum);
        for (int i = 0; i < peptide.length(); i++) {
            sum += MASSTABLE.get(peptide.charAt(i));
            idealSpectrum.add(sum);
        }
        sum = 0;
        for (int i = peptide.length() - 1; i > 0; i--) {
            sum += MASSTABLE.get(peptide.charAt(i));
            idealSpectrum.add(sum);
        }
        Collections.sort(idealSpectrum);
        return idealSpectrum;

    }

    private static void getPeptide(Map<Integer, List<Integer>> graph, int src, String cur, List<String> paths) {
        if (!graph.containsKey(src)) {
            // reach to sink
            paths.add(cur);
            return;
        }
        for (int next : graph.get(src)) {
            getPeptide(graph, next, cur + MASSTABLE_REV.get(next - src), paths);
        }
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11813_4.txt"), Charset.forName("UTF-8"));
        List<Integer> spectrum = Arrays.stream(text.split("\n")[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(DecodingIdealSpectrum(spectrum));
    }
}
