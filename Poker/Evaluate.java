  import java.rmi.Naming;
  import java.util.Random;
  import java.util.*;
  import java.rmi.registry.*;
  import java.util.concurrent.*;

  // H        D        C        S
  // 1        14        27        40
  // 2        15        28        41
  // 3        16        29        42
  // 4        17        30        43
  // 5        18        31        44
  // 6        19        32        45
  // 7        20        33        46
  // 8        21        34        47
  // 9        22        35        48
  // 10        23        36        49
  // 11        24        37        50
  // 12        25        38        51
  // 13        26        39        52


public class Evaluate {
    // public static ArrayList < Integer > exRoyalStraightFlush = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exRoyalStraightFlush2 = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exStraightFlush = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exStraightFlush2 = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exPoker = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exFlush = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exFullHouse = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exStraight = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exStraight2 = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exTris = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exTwoPairs = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exPair = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exHighCard = new ArrayList < Integer > ();
    // public static ArrayList < Integer > exNull = new ArrayList < Integer > ();
    // public static ArrayList < ArrayList < Integer >> playersCards = new ArrayList < ArrayList < Integer >> ();
    // public static ArrayList < Integer > commonCards = new ArrayList < Integer > ();
    // public static ArrayList < Integer > p1 = new ArrayList < Integer > ();
    // public static ArrayList < Integer > p2 = new ArrayList < Integer > ();
    // public static ArrayList < Integer > p3 = new ArrayList < Integer > ();
    // public static ArrayList < Integer > p4 = new ArrayList < Integer > ();
    public static ArrayList <ArrayList<String>> playersResults;
    public static String[] PointsEN = {
      "Null",
      "Royal Straight Flush",
      "Straight Flush",
      "Poker",
      "Flush",
      "Full House",
      "Straight",
      "Tris",
      "Two Pairs",
      "Pair",
      "High Card"
    };
    public static String[] PointsIT = {
      "Null",
      "Scala Reale",
      "Scala colore",
      "Poker",
      "Colore",
      "Full",
      "Scala",
      "Tris",
      "Doppia Coppia",
      "Coppia",
      "CartaAlta"
    };
    public static String[] SuitsEN = {
      "Hearts",
      "Diamonds",
      "Clubs",
      "Spades"
    };
    public static String[] SuitsIT = {
      "Cuori",
      "Quadri",
      "Fiori",
      "Picche"
    };
    public static String[] PlayersEN = {
      "South",
      "West",
      "North",
      "East"
    };
    public static String[] PlayersIT = {
      "Sud",
      "Ovest",
      "Nord",
      "Est"
    };
    public static String[] CardsEN = {
      "Null",
      "Aces",
      "Twos",
      "Threes",
      "Fours",
      "Fives",
      "Sixs",
      "Sevens",
      "Eights",
      "Nines",
      "Tens",
      "Jacks",
      "Queens",
      "Kings",
      "Aces"
    };
    public static String[] CardsENSingular = {
      "Null",
      "Ace",
      "Two",
      "Three",
      "Four",
      "Five",
      "Six",
      "Seven",
      "Eight",
      "Nine",
      "Ten",
      "Jack",
      "Queen",
      "King",
      "Ace"
    };
    public static String[] CardsIT = {
      "Null",
      "A",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "J",
      "Q",
      "K",
      "A"
    };
    public static String[] CardsITSingular = {
      "Null",
      "A",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "J",
      "Q",
      "K",
      "A"
    };

    // public static void initialize_for_testing(){
    //   exRoyalStraightFlush.add(52);
    //   exRoyalStraightFlush.add(51);
    //   exRoyalStraightFlush.add(1);
    //   exRoyalStraightFlush.add(13);
    //   exRoyalStraightFlush.add(12);
    //   exRoyalStraightFlush.add(11);
    //   exRoyalStraightFlush.add(10);
    //
    //   exRoyalStraightFlush2.add(14);
    //   exRoyalStraightFlush2.add(23);
    //   exRoyalStraightFlush2.add(1);
    //   exRoyalStraightFlush2.add(24);
    //   exRoyalStraightFlush2.add(26);
    //   exRoyalStraightFlush2.add(11);
    //   exRoyalStraightFlush2.add(25);
    //
    //   exStraightFlush.add(52);
    //   exStraightFlush.add(51);
    //   exStraightFlush.add(1);
    //   exStraightFlush.add(2);
    //   exStraightFlush.add(3);
    //   exStraightFlush.add(4);
    //   exStraightFlush.add(5);
    //
    //   exStraightFlush2.add(22);
    //   exStraightFlush2.add(23);
    //   exStraightFlush2.add(1);
    //   exStraightFlush2.add(24);
    //   exStraightFlush2.add(21);
    //   exStraightFlush2.add(11);
    //   exStraightFlush2.add(25);
    //
    //   exPoker.add(52);
    //   exPoker.add(51);
    //   exPoker.add(1);
    //   exPoker.add(14);
    //   exPoker.add(27);
    //   exPoker.add(40);
    //   exPoker.add(16);
    //
    //   exFlush.add(52);
    //   exFlush.add(51);
    //   exFlush.add(1);
    //   exFlush.add(3);
    //   exFlush.add(5);
    //   exFlush.add(7);
    //   exFlush.add(9);
    //
    //   exFullHouse.add(52);
    //   exFullHouse.add(51);
    //   exFullHouse.add(1);
    //   exFullHouse.add(14);
    //   exFullHouse.add(27);
    //   exFullHouse.add(37);
    //   exFullHouse.add(24);
    //
    //   exStraight.add(52);//K
    //   exStraight.add(51);//Q
    //   exStraight.add(8);//8
    //   exStraight.add(22);//9
    //   exStraight.add(36);//10
    //   exStraight.add(50);//J
    //   exStraight.add(13);//K
    //
    //   exStraight2.add(22);
    //   exStraight2.add(23);
    //   exStraight2.add(1);
    //   exStraight2.add(24);
    //   exStraight2.add(39);
    //   exStraight2.add(11);
    //   exStraight2.add(38);
    //
    //   exTris.add(52);
    //   exTris.add(51);
    //   exTris.add(10);
    //   exTris.add(23);
    //   exTris.add(36);
    //   exTris.add(37);
    //   exTris.add(2);
    //   exTwoPairs.add(52);
    //   exTwoPairs.add(51);
    //   exTwoPairs.add(39);
    //   exTwoPairs.add(38);
    //   exTwoPairs.add(8);
    //   exTwoPairs.add(9);
    //   exTwoPairs.add(15);
    //   exPair.add(52);
    //   exPair.add(51);
    //   exPair.add(3);
    //   exPair.add(4);
    //   exPair.add(5);
    //   exPair.add(9);
    //   exPair.add(22);
    //   exHighCard.add(52);
    //   exHighCard.add(51);
    //   exHighCard.add(1);
    //   exHighCard.add(2);
    //   exHighCard.add(3);
    //   exHighCard.add(33);
    //   exHighCard.add(49);
    //   exNull.add(0);
    //   exNull.add(0);
    //   exNull.add(0);
    //   exNull.add(0);
    //   exNull.add(0);
    //   exNull.add(0);
    //   exNull.add(0);
    //
    //   p1.add(2);
    //   p1.add(15);
    //   p2.add(15);
    //   p2.add(27);
    //   p3.add(52);
    //   p3.add(39);
    //   p4.add(26);
    //   p4.add(25);
    //   commonCards.add(2);
    //   commonCards.add(3);
    //   commonCards.add(18);
    //   commonCards.add(50);
    //   commonCards.add(43);
    //   playersCards.add(p1);
    //   playersCards.add(p2);
    //   playersCards.add(p3);
    //   playersCards.add(p4);
    // }


    public Evaluate() {
      //initialize_for_testing();
      playersResults= new ArrayList<ArrayList<String>>();
    }
    public static Integer upperFlush(Integer suit, ArrayList < ArrayList < Integer >> all) {
      Integer tmp = 0;
      for (ArrayList < Integer > arr: all) {
        if (arr.get(1) == suit) {
          if (arr.get(0) == 1) {
            tmp = 14;
            return tmp;
          }
          else if (arr.get(0) > 0) {
            tmp = arr.get(0);
          }
        }
      }
      return tmp;
    }
    public static ArrayList < Integer > straightflush(ArrayList < ArrayList < Integer >> all) {
      int[] cardsH = new int[14];
      int[] cardsD = new int[14];
      int[] cardsC = new int[14];
      int[] cardsS = new int[14];

      int straight = 0;
      int counterH = 1;
      int counterD = 1;
      int counterC = 1;
      int counterS = 1;

      ArrayList < Integer > result = new ArrayList < Integer > ();

      for (int card: cardsH) {
        card = 0;
      }
      for (int card: cardsD) {
        card = 0;
      }
      for (int card: cardsC) {
        card = 0;
      }
      for (int card: cardsS) {
        card = 0;
      }
      for (ArrayList < Integer > cardPlayer: all) {
        Integer oldVal;
        switch (cardPlayer.get(1)) {
          case 1:
          //System.out.println("hearts");
          oldVal = cardsH[cardPlayer.get(0) - 1];
          cardsH[cardPlayer.get(0) - 1] = oldVal + 1;
          break;
          case 2:
          //System.out.println("diamonds");
          oldVal = cardsD[cardPlayer.get(0) - 1];
          cardsD[cardPlayer.get(0) - 1] = oldVal + 1;
          break;
          case 3:
          //System.out.println("clubs");
          oldVal = cardsC[cardPlayer.get(0) - 1];
          cardsC[cardPlayer.get(0) - 1] = oldVal + 1;
          break;
          case 4:
          //System.out.println("spades");
          oldVal = cardsS[cardPlayer.get(0) - 1];
          cardsS[cardPlayer.get(0) - 1] = oldVal + 1;
          break;

        }

      }

      // for(Integer value: values){
      //         int oldVal=cards[value-1];
      //         cards[value-1]=oldVal + 1;
      // }
      cardsH[13] = cardsH[0];
      cardsD[13] = cardsD[0];
      cardsC[13] = cardsC[0];
      cardsS[13] = cardsS[0];

      for (int i = 13; i > 0; i--) {
        if (cardsH[i] > 0 && cardsH[i - 1] > 0) {
          counterH++;
        }
        else {
          //System.out.println("azzerat");
          counterH = 1;
        }
        if (counterH == 5) {
          //System.out.println(counterH);
          straight = i + 4; //i-1 because in the for loop we look the next value in array too
          //System.out.println("straight of "+CardsIT[straight]);
          result.add(straight);
          result.add(straight - 1);
          result.add(straight - 2);
          result.add(straight - 3);
          result.add(straight - 4);
          return result;
        }
        if (cardsD[i] > 0 && cardsD[i - 1] > 0) {
          counterD++;
        }
        else {
          //System.out.println("azzerat");
          counterD = 1;
        }
        if (counterD == 5) {
          straight = i + 4; //i-1 because in the for loop we look the next value in array too
          //System.out.println("straight of "+CardsIT[straight]);
          result.add(straight);
          result.add(straight - 1);
          result.add(straight - 2);
          result.add(straight - 3);
          result.add(straight - 4);
          return result;
        }
        if (cardsC[i] > 0 && cardsC[i - 1] > 0) {
          counterC++;
        }
        else {
          //System.out.println("azzerat");
          counterC = 1;
        }
        if (counterC == 5) {
          straight = i + 4; //i-1 because in the for loop we look the next value in array too
          //System.out.println("straight of "+CardsIT[straight]);
          result.add(straight);
          result.add(straight - 1);
          result.add(straight - 2);
          result.add(straight - 3);
          result.add(straight - 4);
          return result;
        }
        if (cardsS[i] > 0 && cardsS[i - 1] > 0) {
          counterS++;
        }
        else {
          //System.out.println("azzerat");
          counterS = 1;
        }
        if (counterS == 5) {
          straight = i + 4; //i-1 because in the for loop we look the next value in array too
          //System.out.println("straight of "+CardsIT[straight]);
          result.add(straight);
          result.add(straight - 1);
          result.add(straight - 2);
          result.add(straight - 3);
          result.add(straight - 4);
          return result;
        }

      }
      //System.out.println(Arrays.toString(cardsH));
      //System.out.println(counterH);
      //System.out.println(Arrays.toString(cardsD));
      //System.out.println(counterD);
      //System.out.println(Arrays.toString(cardsC));
      //System.out.println(counterC);
      //System.out.println(Arrays.toString(cardsS));
      //System.out.println(counterS);
      return null;
    }
    public static ArrayList < Integer > poker(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      int poker = 0;
      int fifth = 0;
      ArrayList < Integer > result = new ArrayList < Integer > ();
      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];

      for (int i = 13; i >= 0; i--) {

        if (cards[i] >= 4) {
          poker = i;
          break;
          //System.out.println("poker of "+CardsIT[poker]);
          //return poker;
        }
      }
      if (poker > 0) {
        for (int i = 13; i >= 0; i--) {

          if (cards[i] >= 1 && i != poker) {
            fifth = i;
            break;
            //System.out.println("poker of "+CardsIT[poker]);
            //return poker;
          }
        }
        poker++;
        fifth++;
        result.add(poker);
        result.add(poker);
        result.add(poker);
        result.add(poker);
        result.add(fifth);
        return result;
      }
      return null;
    }
    public static ArrayList < Integer > flush(ArrayList < Integer > suits, ArrayList < ArrayList < Integer >> all) {
      int hearts = 0;
      int diamonds = 0;
      int clubs = 0;
      int spades = 0;
      int suit = 0;
      int upper = 0;
      ArrayList < Integer > ret = new ArrayList < Integer > ();
      //System.out.println(suits);
      for (Integer seed: suits) {
        switch (seed) {
          case 1:
          //System.out.println("hearts++");
          hearts++;
          //System.out.println(hearts);
          break;
          case 2:
          //System.out.println("diamonds++");
          diamonds++;
          //System.out.println(diamonds);
          break;
          case 3:
          //System.out.println("clubs++");
          clubs++;
          //System.out.println(clubs);
          break;
          case 4:
          //System.out.println("spades++");
          spades++;
          //System.out.println(spades);
          break;
        }
      }


      if (hearts >= 5) {
        suit = 1;
        //System.out.println("flush of "+SuitsIT[suit-1]);
      }
      else if (diamonds >= 5) {
        suit = 2;
        //System.out.println("flush of "+SuitsIT[suit-1]);
      }
      else if (clubs >= 5) {
        suit = 3;
        //System.out.println("flush of "+SuitsIT[suit-1]);
      }
      else if (spades >= 5) {
        suit = 4;
        //System.out.println("flush of "+SuitsIT[suit-1]);
      }
      if (suit > 0) {
        for (ArrayList < Integer > arr: all) {
          if (arr.get(1) == suit) {
            if (arr.get(0) == 1) {
              arr.set(0, 14);
            }
          }
        }
        all.sort(new Comparator < ArrayList < Integer >> () {
          @Override
          public int compare(ArrayList < Integer > a, ArrayList < Integer > b) {
            return b.get(0).compareTo(a.get(0));
          }
        });
        for (ArrayList < Integer > arr: all) {
          if (arr.get(1) == suit) {
            if (arr.get(0) > 0) {
              ret.add(arr.get(0));
            }
          }
        }
        //System.out.println("suit "+suit);
        ret.add(suit - 1);
        return new ArrayList < Integer > (ret.subList(0, 6));
      }
      return null;
    }
    public static ArrayList < Integer > fullhouse(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      int tris = 0;
      int pair = 0;
      Integer[] full = new Integer[2];
      ArrayList < Integer > res = new ArrayList < Integer > ();
      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];
      for (int i = 13; i > 0; i--) {
        if (cards[i] >= 3) {
          if (tris == 0) {
            tris = i + 1;
            //System.out.println("tris of "+CardsIT[tris]);
          }
          else {
            pair = i + 1;
            //System.out.println("coppia of "+CardsIT[pair]);
            res.add(tris);
            res.add(tris);
            res.add(tris);
            res.add(pair);
            res.add(pair);
            return res;
          }
        }
        else if (cards[i] >= 2 && i != tris - 1) {
          pair = i + 1;
          //System.out.println("coppia of "+CardsIT[pair]);
        }
      }
      if (tris > 0 && pair > 0) {
        //System.out.println("fullhouse of "+CardsIT[tris]+" and "+CardsIT[pair]);
        res.add(tris);
        res.add(tris);
        res.add(tris);
        res.add(pair);
        res.add(pair);
        return res;

      }
      return null;
    }
    public static ArrayList < Integer > straight(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      int straight = 0;
      int counter = 1;
      ArrayList < Integer > result = new ArrayList < Integer > ();

      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];
      System.out.println(Arrays.toString(cards));
      for (int i = 13; i > 0; i--) {
        if (cards[i] > 0 && cards[i - 1] > 0) {

          counter++;

        }
        else {
          counter = 1;
        }
        if (counter == 5) {
          straight = i + 4; //i-1 because in the for loop we look the next value in array too
          //System.out.println("straight of "+CardsIT[straight]);
          result.add(straight);
          result.add(straight - 1);
          result.add(straight - 2);
          result.add(straight - 3);
          result.add(straight - 4);
          return result;
        }
        //System.out.println(counter);

      }
      return null;
    }
    public static ArrayList < Integer > tris(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      int tris = 0;
      ArrayList < Integer > res = new ArrayList < Integer > ();

      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];

      for (int i = 13; i > 0; i--) {
        if (cards[i] >= 3) {
          tris = i + 1;
          //System.out.println("tris of "+CardsIT[tris]);
          res.add(tris);
          res.add(tris);
          res.add(tris);
          break;
        }
      }
      int counter = 2;
      if (tris > 0) {

        for (int i = 13; i > 0; i--) {
          if (counter == 0) {
            break;
          }
          else if (cards[i] >= 1 && i != tris - 1) {

            //System.out.println("tris of "+CardsIT[tris]);
            res.add(i + 1);
            counter--;
          }
        }
        return res;
      }
      return null;
    }
    public static ArrayList < Integer > doublepair(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      boolean first = false;
      int pair1 = 0;
      int pair2 = 0;
      Integer[] doublep = new Integer[2];
      ArrayList < Integer > res = new ArrayList < Integer > ();

      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];

      for (int i = 13; i > 0; i--) {
        if (cards[i] >= 2) {
          if (!first) {
            pair1 = i + 1;
            //System.out.println("coppia of "+CardsIT[pair1));
            first = true;
            res.add(pair1);
            res.add(pair1);
          }
          else {
            pair2 = i + 1;
            //System.out.println("double pair of "+CardsIT[pair1] + " e " + CardsIT[pair2]);
            res.add(pair2);
            res.add(pair2);
          }
        }
      }

      if (pair1 > 0 && pair2 > 0) {

        for (int i = 13; i > 0; i--) {
          if (cards[i] >= 1 && i != pair1 - 1 && i != pair2 - 1) {
            //System.out.println("tris of "+CardsIT[tris]);
            res.add(i + 1);
            break;
          }
        }
        return res;
      }
      return null;
    }
    public static ArrayList < Integer > pair(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      int pair = 0;
      ArrayList < Integer > res = new ArrayList < Integer > ();
      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];

      for (int i = 13; i > 0; i--) {
        if (cards[i] >= 2) {

          pair = i + 1;
          //System.out.println("coppia of "+CardsIT[pair1));
          res.add(pair);
          res.add(pair);

        }
      }

      int counter = 3;
      if (pair > 0) {

        for (int i = 13; i > 0; i--) {
          if (counter == 0) {
            break;
          }
          else if (cards[i] >= 1 && i != pair - 1) {

            //System.out.println("pair of "+CardsIT[pair]);
            res.add(i + 1);
            counter--;
          }
        }
        return res;
      }
      return null;
    }
    public static ArrayList < Integer > highcard(ArrayList < Integer > values, ArrayList < ArrayList < Integer >> all) {
      int[] cards = new int[14];
      int highcard = 0;
      ArrayList < Integer > res = new ArrayList < Integer > ();
      for (int card: cards) {
        card = 0;
      }
      for (Integer value: values) {
        int oldVal = cards[value - 1];
        cards[value - 1] = oldVal + 1;
      }
      cards[13] = cards[0];

      int counter = 5;
      for (int i = 13; i > 0; i--) {
        if (counter == 0) {
          return res;
        }
        else if (cards[i] >= 1) {
          //System.out.println("highcard of "+CardsITSingular[pair]);
          res.add(i + 1);
          counter--;
        }
      }

      return null;
    }
    public static ArrayList < Integer > valuate(Integer idPlayer, List < Integer > carte) {
      //List<int[]> myList = new ArrayList<int[]>();
      ArrayList<String> playerVal= new ArrayList<String>();
      playerVal.add(idPlayer.toString());
      ArrayList < ArrayList < Integer >> myList = new ArrayList < ArrayList < Integer >> ();
      for (Integer carta: carte) {
        ArrayList < Integer > message = new ArrayList < Integer > ();
        if (carta > 39) {
          message.add(carta - 39);
          message.add(4);
        }
        else if (carta > 26) {
          message.add(carta - 26);
          message.add(3);
        }
        else if (carta > 13) {
          message.add(carta - 13);
          message.add(2);
        }
        else {
          message.add(carta);
          message.add(1);
        }
        //System.out.println(message);
        myList.add(message);
      }
      //System.out.println(myList);
      ArrayList < Integer > valori = new ArrayList < Integer > ();
      ArrayList < Integer > suits = new ArrayList < Integer > ();
      for (ArrayList < Integer > list: myList) {
        //System.out.println(list);
        valori.add(list.get(0));
        suits.add(list.get(1));

      }
      //System.out.println(valori);
      //System.out.println(suits);
      Collections.sort(valori);
      Collections.sort(suits);
      //System.out.println(valori);
      //System.out.println(suits);
      int punteggio = 0;
      int res;
      int res2;
      Integer[] resA = new Integer[2];
      ArrayList < Integer > sflush = new ArrayList < Integer > ();
      ArrayList < Integer > hand = new ArrayList < Integer > ();
      ArrayList < Integer > result = new ArrayList < Integer > (); //
      //Royal Flush
      //Valori in scala, suits uguali
      //
      hand = straightflush(myList);
      if (hand != null) {
        if(hand.get(0)==14){
            System.out.println(PointsIT[1]+": "+ CardsIT[hand.get(0)]);
            playerVal.add(PointsIT[1]+": "+ CardsIT[hand.get(0)]);
            playersResults.add(playerVal);
            result.add(1);
            for (Integer x: hand) {
              result.add(x);
            }
            //System.out.println("hand: "+hand);
            return result;
        }
        else{
            System.out.println(PointsIT[2]+": "+ CardsIT[hand.get(0)]);
            playerVal.add(PointsIT[2]+": "+ CardsIT[hand.get(0)]);
            playersResults.add(playerVal);
            result.add(2);
            for (Integer x: hand) {
              result.add(x);
            }
            //System.out.println("hand: "+hand);
            return result;

        }
      }


      //Poker
      //Quattro carte uguali
      hand = poker(valori, myList);
      if (hand != null) {
        System.out.println(PointsIT[3] + ": " + CardsIT[hand.get(0)]);
        playerVal.add(PointsIT[3]+": "+ CardsIT[hand.get(0)]);
        playersResults.add(playerVal);
        result.add(3);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
      }
      //Colore
      //suits uguali
      hand = flush(suits, myList);
      if (hand != null) {
        //System.out.println(hand);
        System.out.println(PointsIT[4] + ": " + SuitsIT[hand.get(5)]);
        playerVal.add(PointsIT[4]+": "+ SuitsIT[hand.get(5)]);
        playersResults.add(playerVal);
        result.add(4);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;

      }

      //FullHouse
      //Tris e coppia
      hand = fullhouse(valori, myList);
      if (hand != null) {
        System.out.println(PointsIT[5] + ": " + CardsIT[hand.get(0)] + " & " + CardsIT[hand.get(3)]);
        playerVal.add(PointsIT[5] + ": " + CardsIT[hand.get(0)] + " & " + CardsIT[hand.get(3)]);
        playersResults.add(playerVal);
        result.add(5);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
      }


      //Scala
      //Valori in scala
      hand = straight(valori, myList);
      if (hand != null) {
        System.out.println(hand);
        System.out.println(PointsIT[6] + ": " + CardsIT[hand.get(0)]);
        playerVal.add(PointsIT[6] + ": " + CardsIT[hand.get(0)]);
        playersResults.add(playerVal);
        result.add(6);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
      }


      //Tris
      //Tre carte uguali
      hand = tris(valori, myList);
      if (hand != null) {
        System.out.println(PointsIT[7] + ": " + CardsIT[hand.get(0)]);
        playerVal.add(PointsIT[7] + ": " + CardsIT[hand.get(0)]);
        playersResults.add(playerVal);
        result.add(7);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
      }

      //Doppia
      //Due coppie
      hand = doublepair(valori, myList);
      if (hand != null) {
        System.out.println(PointsIT[8] + ": " + CardsIT[hand.get(0)] + " & " + CardsIT[hand.get(2)]);
        playerVal.add(PointsIT[8] + ": " + CardsIT[hand.get(0)] + " & " + CardsIT[hand.get(2)]);
        playersResults.add(playerVal);
        result.add(8);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
        //result.add(resA);
      }

      //Coppia
      //Due carte uguali
      hand = pair(valori, myList);
      if (hand != null) {
        System.out.println(PointsIT[9] + ": " + CardsIT[hand.get(0)]);
        playerVal.add(PointsIT[9] + ": " + CardsIT[hand.get(0)]);
        playersResults.add(playerVal);
        result.add(9);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
      }

      //Carta alta
      //La carta migliore
      hand = highcard(valori, myList);
      if (hand != null) {
        System.out.println(PointsIT[10] + ": " + CardsITSingular[hand.get(0)]);
        playerVal.add(PointsIT[10] + ": " + CardsITSingular[hand.get(0)]);
        playersResults.add(playerVal);
        result.add(10);
        for (Integer x: hand) {
          result.add(x);
        }
        return result;
      }
      /**/
      return result;


    }
    public static ArrayList < Integer > transform(Integer card) {
      ArrayList < Integer > result = new ArrayList < Integer > ();
      Integer value = ((card - 1) % 13) + 1;
      Integer suit = 0;
      if (card > 39) {
        suit = 4;
      }
      else if (card > 26) {
        suit = 3;
      }
      else if (card > 13) {
        suit = 2;
      }
      else {
        suit = 1;
      }
      result.add(value);
      result.add(suit);
      return result;
    }
    public static ArrayList < Integer > showdown(ArrayList < ArrayList < Integer >> players, ArrayList < Integer > common) {
      int i;

      ArrayList < Integer > winners = new ArrayList < Integer > ();
      int winner = 0;
      int point = 999;
      ArrayList < Integer > winnerHand = new ArrayList < Integer > ();
      ArrayList < Integer > b = new ArrayList < Integer > ();

      for (i = 0; i < players.size(); i++) {
        b.clear();
        b.addAll(common);
        b.add(players.get(i).get(1));
        b.add(players.get(i).get(2));
        //System.out.println(b);
        ArrayList < Integer > hand = valuate(players.get(i).get(0),b);
        //System.out.println(hand);
        if (hand.get(0) < point && hand.get(0) != 0) {
          winner = players.get(i).get(0);
          point = hand.get(0);
          winnerHand = hand;
          winners.clear();
          winners.add(winner);
        }
        else if (hand.get(0) == point) {
          winner = players.get(i).get(0);
          int index = 0;
          int equals = 5;
          for (index = 1; index < 6; index++) {
            //System.out.println(hand.get(index));
            if (winnerHand.get(index) > hand.get(index)) {
              //System.out.println("1");
              break;
            }
            else if (winnerHand.get(index) < hand.get(index)) {
              winner = players.get(i).get(0);
              winners.clear();
              winners.add(winner);
              winnerHand = hand;
              //System.out.println("2");
              break;
            }
            else {
              equals--;
              if (equals == 0) {
                winners.add(winner);
                //System.out.println("3");
                break;
              }
            }
          }
        }
      }

      System.out.println("winners " + winners);

      return winners;



    }
    // public static ArrayList < ArrayList < Integer > > test(int input,int id) {
    //   ArrayList < Integer > playerC = new ArrayList < Integer > ();
    //   ArrayList < Integer > commonC = new ArrayList < Integer > ();
    //   if(id==1){
    //     switch (input) {
    //       case 1:
    //         playerC.add(exRoyalStraightFlush.get(0));
    //         playerC.add(exRoyalStraightFlush.get(1));
    //         commonC.add(exRoyalStraightFlush.get(2));
    //         commonC.add(exRoyalStraightFlush.get(3));
    //         commonC.add(exRoyalStraightFlush.get(4));
    //         commonC.add(exRoyalStraightFlush.get(5));
    //         commonC.add(exRoyalStraightFlush.get(6));
    //         break;
    //       case 2:
    //         playerC.add(exStraightFlush.get(0));
    //         playerC.add(exStraightFlush.get(1));
    //         commonC.add(exStraightFlush.get(2));
    //         commonC.add(exStraightFlush.get(3));
    //         commonC.add(exStraightFlush.get(4));
    //         commonC.add(exStraightFlush.get(5));
    //         commonC.add(exStraightFlush.get(6));
    //         break;
    //       case 3:
    //         playerC.add(exPoker.get(0));
    //         playerC.add(exPoker.get(1));
    //         commonC.add(exPoker.get(2));
    //         commonC.add(exPoker.get(3));
    //         commonC.add(exPoker.get(4));
    //         commonC.add(exPoker.get(5));
    //         commonC.add(exPoker.get(6));
    //         break;
    //       case 4:
    //         playerC.add(exFlush.get(0));
    //         playerC.add(exFlush.get(1));
    //         commonC.add(exFlush.get(2));
    //         commonC.add(exFlush.get(3));
    //         commonC.add(exFlush.get(4));
    //         commonC.add(exFlush.get(5));
    //         commonC.add(exFlush.get(6));
    //         break;
    //       case 5:
    //         playerC.add(exFullHouse.get(0));
    //         playerC.add(exFullHouse.get(1));
    //         commonC.add(exFullHouse.get(2));
    //         commonC.add(exFullHouse.get(3));
    //         commonC.add(exFullHouse.get(4));
    //         commonC.add(exFullHouse.get(5));
    //         commonC.add(exFullHouse.get(6));
    //         break;
    //       case 6:
    //         playerC.add(exStraight.get(0));
    //         playerC.add(exStraight.get(1));
    //         commonC.add(exStraight.get(2));
    //         commonC.add(exStraight.get(3));
    //         commonC.add(exStraight.get(4));
    //         commonC.add(exStraight.get(5));
    //         commonC.add(exStraight.get(6));
    //         break;
    //       case 7:
    //         playerC.add(exTris.get(0));
    //         playerC.add(exTris.get(1));
    //         commonC.add(exTris.get(2));
    //         commonC.add(exTris.get(3));
    //         commonC.add(exTris.get(4));
    //         commonC.add(exTris.get(5));
    //         commonC.add(exTris.get(6));
    //         break;
    //       case 8:
    //         playerC.add(exTwoPairs.get(0));
    //         playerC.add(exTwoPairs.get(1));
    //         commonC.add(exTwoPairs.get(2));
    //         commonC.add(exTwoPairs.get(3));
    //         commonC.add(exTwoPairs.get(4));
    //         commonC.add(exTwoPairs.get(5));
    //         commonC.add(exTwoPairs.get(6));
    //         break;
    //       case 9:
    //         playerC.add(exPair.get(0));
    //         playerC.add(exPair.get(1));
    //         commonC.add(exPair.get(2));
    //         commonC.add(exPair.get(3));
    //         commonC.add(exPair.get(4));
    //         commonC.add(exPair.get(5));
    //         commonC.add(exPair.get(6));
    //         break;
    //       case 10:
    //         playerC.add(exHighCard.get(0));
    //         playerC.add(exHighCard.get(1));
    //         commonC.add(exHighCard.get(2));
    //         commonC.add(exHighCard.get(3));
    //         commonC.add(exHighCard.get(4));
    //         commonC.add(exHighCard.get(5));
    //         commonC.add(exHighCard.get(6));
    //         break;
    //     }
    //   }
    //   else if(id==2){
    //     switch (input) {
    //       case 1:
    //         playerC.add(exRoyalStraightFlush2.get(0));
    //         playerC.add(exRoyalStraightFlush2.get(1));
    //         commonC.add(exRoyalStraightFlush2.get(2));
    //         commonC.add(exRoyalStraightFlush2.get(3));
    //         commonC.add(exRoyalStraightFlush2.get(4));
    //         commonC.add(exRoyalStraightFlush2.get(5));
    //         commonC.add(exRoyalStraightFlush2.get(6));
    //         break;
    //       case 2:
    //         playerC.add(exStraightFlush2.get(0));
    //         playerC.add(exStraightFlush2.get(1));
    //         commonC.add(exStraightFlush2.get(2));
    //         commonC.add(exStraightFlush2.get(3));
    //         commonC.add(exStraightFlush2.get(4));
    //         commonC.add(exStraightFlush2.get(5));
    //         commonC.add(exStraightFlush2.get(6));
    //         break;
    //       // case 3:
    //       //   playerC.add(exPoker.get(0));
    //       //   playerC.add(exPoker.get(1));
    //       //   commonC.add(exPoker.get(2));
    //       //   commonC.add(exPoker.get(3));
    //       //   commonC.add(exPoker.get(4));
    //       //   commonC.add(exPoker.get(5));
    //       //   commonC.add(exPoker.get(6));
    //       //   break;
    //       // case 4:
    //       //   playerC.add(exFlush.get(0));
    //       //   playerC.add(exFlush.get(1));
    //       //   commonC.add(exFlush.get(2));
    //       //   commonC.add(exFlush.get(3));
    //       //   commonC.add(exFlush.get(4));
    //       //   commonC.add(exFlush.get(5));
    //       //   commonC.add(exFlush.get(6));
    //       //   break;
    //       // case 5:
    //       //   playerC.add(exFullHouse.get(0));
    //       //   playerC.add(exFullHouse.get(1));
    //       //   commonC.add(exFullHouse.get(2));
    //       //   commonC.add(exFullHouse.get(3));
    //       //   commonC.add(exFullHouse.get(4));
    //       //   commonC.add(exFullHouse.get(5));
    //       //   commonC.add(exFullHouse.get(6));
    //       //   break;
    //       case 3:
    //         playerC.add(exStraight2.get(0));
    //         playerC.add(exStraight2.get(1));
    //         commonC.add(exStraight2.get(2));
    //         commonC.add(exStraight2.get(3));
    //         commonC.add(exStraight2.get(4));
    //         commonC.add(exStraight2.get(5));
    //         commonC.add(exStraight2.get(6));
    //         break;
    //       // case 7:
    //       //   playerC.add(exTris.get(0));
    //       //   playerC.add(exTris.get(1));
    //       //   commonC.add(exTris.get(2));
    //       //   commonC.add(exTris.get(3));
    //       //   commonC.add(exTris.get(4));
    //       //   commonC.add(exTris.get(5));
    //       //   commonC.add(exTris.get(6));
    //       //   break;
    //       // case 8:
    //       //   playerC.add(exTwoPairs.get(0));
    //       //   playerC.add(exTwoPairs.get(1));
    //       //   commonC.add(exTwoPairs.get(2));
    //       //   commonC.add(exTwoPairs.get(3));
    //       //   commonC.add(exTwoPairs.get(4));
    //       //   commonC.add(exTwoPairs.get(5));
    //       //   commonC.add(exTwoPairs.get(6));
    //       //   break;
    //       // case 9:
    //       //   playerC.add(exPair.get(0));
    //       //   playerC.add(exPair.get(1));
    //       //   commonC.add(exPair.get(2));
    //       //   commonC.add(exPair.get(3));
    //       //   commonC.add(exPair.get(4));
    //       //   commonC.add(exPair.get(5));
    //       //   commonC.add(exPair.get(6));
    //       //   break;
    //       // case 10:
    //       //   playerC.add(exHighCard.get(0));
    //       //   playerC.add(exHighCard.get(1));
    //       //   commonC.add(exHighCard.get(2));
    //       //   commonC.add(exHighCard.get(3));
    //       //   commonC.add(exHighCard.get(4));
    //       //   commonC.add(exHighCard.get(5));
    //       //   commonC.add(exHighCard.get(6));
    //       //   break;
    //     }
    //   }
    //   ArrayList < ArrayList < Integer > > result = new ArrayList < ArrayList < Integer > > ();
    //   result.add(playerC);
    //   result.add(commonC);
    //   return result;
    // }
    public static void main(String[] args) throws Exception {
      Evaluate first = new Evaluate();
      //
      //
      // //System.out.println(commonCards);
      // ArrayList < ArrayList < Integer > > players = new ArrayList < ArrayList < Integer > > ();
      // ArrayList < Integer > player1 = new ArrayList < Integer > ();
      // ArrayList < Integer > common = new ArrayList < Integer > ();
      // ArrayList < ArrayList < Integer > > test = new ArrayList < ArrayList < Integer > > ();
      //
      // for(int i = 1; i < 11; i++){
      //   System.out.println("test "+i);
      //   test.clear();
      //   common.clear();
      //   player1.clear();
      //   players.clear();
      //   test = test(i,1);
      //   player1.add(1);
      //   player1.addAll(test.get(0));
      //   players.add(player1);
      //   common = test.get(1);
      //   showdown(players,common);
      //   System.out.println("\n\n");
      // }
      // for(int i = 1; i < 4; i++){
      //   System.out.println("test "+i);
      //   test.clear();
      //   common.clear();
      //   player1.clear();
      //   players.clear();
      //   test = test(i,2);
      //   player1.add(1);
      //   player1.addAll(test.get(0));
      //   players.add(player1);
      //   common = test.get(1);
      //   showdown(players,common);
      //   System.out.println("\n\n");
      // }
      // //showdown(playersCards, commonCards);
      // System.out.println(players);
      // System.out.println(playersResults);
    }
  } //fine classe
