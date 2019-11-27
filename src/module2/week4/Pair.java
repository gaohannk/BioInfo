package module2.week4;

public class Pair implements Comparable<Pair>{
	String peptide;
	int score;
	public Pair(String peptide, int score){
		this.peptide = peptide;
		this.score = score;
	}

	@Override
	public int compareTo(Pair p) {
		return this.score - p.score;
	}
}
