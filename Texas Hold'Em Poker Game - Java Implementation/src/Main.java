import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/*
	INFO: This program deals a total of 7 cards (5 on table and 2 of player) and gives the result for that user.
	Author: Aakshay Subramaniam
*/

public class Main {
	public static void main(String[] args) {
		HashSet<Card> cardList = dealSeven();
		System.out.println("Cards Dealt");
		System.out.println("-----------");
		for (Card cd : cardList)
			System.out.println(cd.getPrint());
		System.out.println("-----------");
		System.out.println("Result:");
		if (isRoyalFlush(cardList))
			System.out.println("Royal Flush!");
		else if (isStraightFlush(cardList))
			System.out.println("Straight Flush");
		else if (isFourOfAKind(cardList))
			System.out.println("Four Of A Kind");
		else if (isFullHouse(cardList))
			System.out.println("Full House");
		else if (isFlush(cardList))
			System.out.println("Flush");
		else if (isStraight(cardList))
			System.out.println("Straight");
		else if (isThreeOfAKind(cardList) != null)
			System.out.println("Three Of A Kind");
		else if (isTwoPair(cardList))
			System.out.println("Two Pair");
		else if (isPair(cardList))
			System.out.println("Pair");
		else
			System.out.println("High Card");
	}

	public static HashSet<Card> dealSeven() {
		HashSet<Card> output = new HashSet<>();
		String[] suites = { "Hearts", "Spades", "Clubs", "Diamonds" };
		int[] types = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
		while (output.size() < 7) {
			Random rand = new Random();
			int suiteInd = rand.nextInt((4 - 1) + 1) + 1;
			int typeInd = rand.nextInt((13 - 1) + 1) + 1;
			Card cd = new Card(suites[suiteInd - 1], types[typeInd - 1]);
			output.add(cd);
		}
		return output;
	}

	public static boolean isRoyalFlush(HashSet<Card> cardList) {
		String suite = getFlushSuite(cardList);
		if (suite.equals("None"))
			return false;
		List<Card> sortedList = new ArrayList<>(cardList);
		Collections.sort(sortedList, (Card ac, Card bc) -> bc.getType() - ac.getType());
		if (sortedList.get(0).getType() == 14) {
			int prev = 14;
			for (int i = 1; i < 5; i++) {
				if (prev - sortedList.get(i).getType() != 1)
					return false;
				prev = sortedList.get(i).getType();
			}
		}
		return true;
	}

	public static boolean isStraightFlush(HashSet<Card> cardList) {
		String suite = getFlushSuite(cardList);
		if (suite.equals("None"))
			return false;
		List<Card> sortedList = new ArrayList<>(cardList);
		int max = 0;
		int cur = 0;
		Collections.sort(sortedList, (Card ac, Card bc) -> ac.getType() - bc.getType());
		Card prev = sortedList.get(0);
		for (int i = 1; i < sortedList.size(); i++) {
			if (sortedList.get(i).getType() - prev.getType() == 1
					&& sortedList.get(i).getSuite().equals(prev.getSuite()))
				cur++;
			else {
				max = Math.max(max, cur);
				cur = 0;
			}
			prev = sortedList.get(i);
		}
		if (max >= 5)
			return true;
		else
			return false;
	}

	public static boolean isFourOfAKind(HashSet<Card> cardList) {
		int[] counter = new int[13];
		for (Card cd : cardList) {
			counter[cd.getType() - 2]++;
			if (counter[cd.getType() - 2] == 4)
				return true;
		}
		return false;
	}

	public static boolean isFullHouse(HashSet<Card> cardList) {
		Card card = isThreeOfAKind(cardList);
		if (card != null) {
			int[] counter = new int[13];
			for (Card cd : cardList) {
				counter[cd.getType() - 2]++;
				if (counter[cd.getType() - 2] == 2 && cd.getType() != card.getType())
					return true;
			}
		}
		return false;
	}

	public static boolean isFlush(HashSet<Card> cardList) {
		if (getFlushSuite(cardList).equals("None"))
			return false;
		else
			return true;
	}

	public static boolean isStraight(HashSet<Card> cardList) {
		List<Card> sortedList = new ArrayList<>(cardList);
		int max = 0;
		int cur = 0;
		Collections.sort(sortedList, (Card ac, Card bc) -> ac.getType() - bc.getType());
		int prev = sortedList.get(0).getType();
		for (int i = 1; i < sortedList.size(); i++) {
			if (sortedList.get(i).getType() - prev == 1)
				cur++;
			else {
				max = Math.max(max, cur);
				cur = 0;
			}
			prev = sortedList.get(i).getType();
		}
		if (max >= 5)
			return true;
		else
			return false;
	}

	public static Card isThreeOfAKind(HashSet<Card> cardList) {
		int[] counter = new int[13];
		for (Card cd : cardList) {
			counter[cd.getType() - 2]++;
			if (counter[cd.getType() - 2] == 3)
				return cd;
		}
		return null;
	}

	public static boolean isTwoPair(HashSet<Card> cardList) {
		boolean oneDone = false;
		int[] counter = new int[13];
		for (Card cd : cardList) {
			counter[cd.getType() - 2]++;
			if (counter[cd.getType() - 2] == 2) {
				if (oneDone)
					return true;
				else
					oneDone = true;
			}
		}
		return false;
	}

	public static boolean isPair(HashSet<Card> cardList) {
		int[] counter = new int[13];
		for (Card cd : cardList) {
			counter[cd.getType() - 2]++;
			if (counter[cd.getType() - 2] == 2)
				return true;
		}
		return false;
	}

	public static String getFlushSuite(HashSet<Card> cardList) {
		int spades = 0, Diamondss = 0, hearts = 0, Clubs = 0;
		for (Card cd : cardList) {
			if (cd.getSuite().equals("Hearts"))
				hearts++;
			else if (cd.getSuite().equals("Spades"))
				spades++;
			else if (cd.getSuite().equals("Diamonds"))
				Diamondss++;
			else
				Clubs++;
		}
		if (hearts >= 5)
			return "Hearts";
		else if (spades >= 5)
			return "Spades";
		else if (Diamondss >= 5)
			return "Diamonds";
		else if (Clubs >= 5)
			return "Clubs";
		else
			return "None";
	}
}
