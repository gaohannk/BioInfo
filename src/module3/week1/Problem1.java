package module3.week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.WriteUtils.writeToFile;

public class Problem1 {
	/**
	 * Code Challenge: Solve the Change Problem. The DPChange pseudocode is reproduced below for your convenience.
	 * <p>
	 * Input: An integer money and an array Coins = (coin1, ..., coind).
	 * Output: The minimum number of coins with denominations Coins that changes money.
	 * <p>
	 *     DPChange(money, Coins)
	 *       MinNumCoins(0) ← 0
	 *       for m ← 1 to money
	 *          MinNumCoins(m) ← ∞
	 *          for i ← 0 to |Coins| - 1
	 *             if m ≥ coini
	 *                if MinNumCoins(m - coini) + 1 < MinNumCoins(m)
	 *                   MinNumCoins(m) ← MinNumCoins(m - coini) + 1
	 *       output MinNumCoins(money)
	 */
	public static int DPChange(int money, List<Integer> coins) {
		int minNumCoins[] = new int[money + 1];
		for (int m = 1; m <= money; m++) {
			minNumCoins[m] = Integer.MAX_VALUE;
			for (int j = 0; j < coins.size(); j++) {
				if (m >= coins.get(j)) {
					if (minNumCoins[m - coins.get(j)] + 1 < minNumCoins[m]) {
						minNumCoins[m] = minNumCoins[m - coins.get(j)] + 1;
					}
				}
			}
		}
		return minNumCoins[money];
	}

	public static void main(String[] args) throws IOException {
		String text = Files.readString(Path.of("./resource/module3/dataset_243_10.txt"), Charset.forName("UTF-8"));
		int money = Integer.parseInt(text.split("\n")[0]);
		List<Integer> coins = Arrays.stream(text.split("\n")[1].split(","))
				.map(e -> Integer.parseInt(e)).collect(Collectors.toList());
		System.out.println(DPChange(money, coins));
	}
}
