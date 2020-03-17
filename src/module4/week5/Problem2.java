package module4.week5;

import module4.common.Amino;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.PrintUtils.printListByLine;
import static module4.week5.Problem1.PeptideSIdentificationProblem;

public class Problem2 {
    /**
     * PSM Search Problem: Identify all Peptide-Spectrum Matches scoring above a threshold for a set of spectra and a proteome.
     *      Input: A set of spectral vectors SpectralVectors, an amino acid string Proteome, and an integer threshold.
     *      Output: The set PSMthreshold(Proteome, SpectralVectors).
     */
    /**
     * PSMSearch(SpectralVectors, Proteome, threshold).
     *     PSMSet ← an empty set
     *     for each vector Spectrum' in SpectralVectors
     *           Peptide ← PeptideIdentification(Spectrum', Proteome)
     *           if Score(Peptide, Spectrum) ≥ threshold
     *               add the PSM (Peptide, Spectrum') to PSMSet
     *     return PSMSet
     */
    public static List<String> PSMSearch(List<List<Integer>> spectrumVectors, String proteome, int threshold) {
        List<String> psm = new LinkedList<>();
        for(List<Integer> spectrum: spectrumVectors){
            Amino amino = PeptideSIdentificationProblem(spectrum, proteome);
            if(amino.score >= threshold){
                psm.add(amino.peptide);
            }
        }
        return psm;
    }

    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("./resource/module4/dataset_11866_5.txt"), Charset.forName("UTF-8"));
        String[] splits = text.split("\n");
        List<List<Integer>> spectrumVectors = new LinkedList<>();
        for(int i=0;i< splits.length-2;i++){
            List<Integer> spectrum = Arrays.stream(splits[i].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            spectrumVectors.add(spectrum);
        }
        String proteome = splits[splits.length-2];
        int threshold = Integer.parseInt(splits[splits.length-1]);
        printListByLine(PSMSearch(spectrumVectors, proteome, threshold));
    }
}
