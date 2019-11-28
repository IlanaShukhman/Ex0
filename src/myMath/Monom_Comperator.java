package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Monom_Comperator implements Comparator<Monom> {
	public int compare(Monom o1, Monom o2) {
		int dp = o1.get_power() - o2.get_power();
		return dp;
	}

}
