import java.util.*;

public class Yahtzee {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Random rand = new Random();

    Die[] dice;
    Board board = new Board();

    while(board.anyRemaining()) {
      dice = new Die[5];

      for (int i = 0; i < 5; i++) {
        dice[i] = new Die();
      }

      roll(dice);

      System.out.println("Here are your current dice:");
      System.out.println(dice[0].getFace() + " " + dice[1].getFace() +
        " " + dice[2].getFace() + " " + dice[3].getFace() + " " + dice[4].getFace());

      // how many dice to you want to reroll?
      System.out.println("Enter the number of dice you want to reroll:");
      int numToReroll = input.nextInt();

      // making sure you are entering valid input
      while (numToReroll < 0 || numToReroll > 6) {
        System.out.println("Please take this seriously.\nEnter a valid number of dice:");
        numToReroll = input.nextInt();
      }

      // if you want to reroll anything, then do this:
      if (numToReroll > 0) {
        System.out.println("Enter the numbers of the dice you want to reroll:");
        for (int i = 0; i < numToReroll; i++) {
          int dieNum = input.nextInt();

          while(dieNum <= 0 || dieNum > 6) {
            System.out.println("Please take this seriously.\nPlease enter a valid die.");
            dieNum = input.nextInt();
          }

          dice[dieNum-1].roll();
        }
      }

      System.out.println("Here are your current dice:");
      System.out.println(dice[0].getFace() + " " + dice[1].getFace() +
        " " + dice[2].getFace() + " " + dice[3].getFace() + " " + dice[4].getFace());

      board.printOptions();
      int choice = input.nextInt();

      while (board.getBasedOnChoice(choice) != -1) {
        System.out.println("Please take this seriously.");
        board.printOptions();
        choice = input.nextInt();
      }

      switch (choice) {
        case 1:
          board.calculateOnes(dice);
          System.out.println("That got you " + board.ones + " points.\n");
          break;
        case 2:
          board.calculateTwos(dice);
          System.out.println("That got you " + board.twos + " points.\n");
          break;
        case 3:
          board.calculateThrees(dice);
          System.out.println("That got you " + board.threes + " points.\n");
          break;
        case 4:
          board.calculateFours(dice);
          System.out.println("That got you " + board.fours + " points.\n");
          break;
        case 5:
          board.calculateFives(dice);
          System.out.println("That got you " + board.fives + " points.\n");
          break;
        case 6:
          board.calculateSixes(dice);
          System.out.println("That got you " + board.sixes + " points.\n");
          break;
        case 7:
          board.calculateThreeOfAKind(dice);
          System.out.println("That got you " + board.threeOfAKind + " points.\n");
          break;
        case 8:
          board.calculateFourOfAKind(dice);
          System.out.println("That got you " + board.fourOfAKind + " points.\n");
          break;
        case 9:
          board.calculatesSmallStraight(dice);
          System.out.println("That got you " + board.smallStraight + " points.\n");
          break;
        case 10:
          board.calculateLongStraight(dice);
          System.out.println("That got you " + board.longStraight + " points.\n");
          break;
        case 11:
          board.calculateFullHouse(dice);
          System.out.println("That got you " + board.fullHouse + " points.\n");
          break;
        case 12:
          board.calculateYahtzee(dice);
          System.out.println("That got you " + board.yahtzee + " points.");
          break;
        default:
          System.out.println("You should not be seeing this.  Something fucked up.");
      }
    }
    System.out.println("Your total score is: " + board.calculateTotalScore());
  }

  static void roll(Die[] dice) {
    for (Die d : dice) {
      d.roll();
    }
  }
}

// represents the board of all possible scores, and what has been scored
// also keeps track of all scores
// also calcualtes the scores with an array of dice
class Board {
  int ones, twos, threes, fours, fives, sixes, threeOfAKind, fourOfAKind, smallStraight, longStraight,
      fullHouse, yahtzee;

  // if it is a -1, then it has not yet been used
  Board() {
    this.ones = -1;         // 1
    this.twos = -1;         // 2
    this.threes = -1;       // 3
    this.fours = -1;        // 4
    this.fives = -1;        // 5
    this.sixes = -1;        // 6
    this.threeOfAKind = -1; // 7
    this.fourOfAKind = -1;  // 8
    this.smallStraight = -1;// 9
    this.longStraight = -1; // 10
    this.fullHouse = -1;    // 11
    this.yahtzee = -1;      // 12
  }

  // will be used to make sure the player gave a valid choice
  public int getBasedOnChoice(int choice) {
    switch (choice) {
      case 1:
        return this.ones;
      case 2:
        return this.twos;
      case 3:
        return this.threes;
      case 4:
        return this.fours;
      case 5:
        return this.fives;
      case 6:
        return this.sixes;
      case 7:
        return this.threeOfAKind;
      case 8:
        return this.fourOfAKind;
      case 9:
        return this.smallStraight;
      case 10:
        return this.longStraight;
      case 11:
        return this.fullHouse;
      case 12:
        return this.yahtzee;
    }
    return 0;
  }

  public void printOptions() {
    System.out.println("Here are your options:");
    if (this.ones == -1)          System.out.println("Ones:             1");
    if (this.twos == -1)          System.out.println("Twos:             2");
    if (this.threes == -1)        System.out.println("Threes:           3");
    if (this.fours == -1)         System.out.println("Fours:            4");
    if (this.fives == -1)         System.out.println("Fives:            5");
    if (this.sixes == -1)         System.out.println("Sixes:            6");
    if (this.threeOfAKind == -1)  System.out.println("Three of A Kind:  7");
    if (this.fourOfAKind == -1)   System.out.println("Four of A Kind:   8");
    if (this.smallStraight == -1) System.out.println("Small Straight:   9");
    if (this.longStraight == -1)  System.out.println("Long Straight:    10");
    if (this.fullHouse == -1)     System.out.println("Full House:       11");
    if (this.yahtzee == -1)       System.out.println("Yahtzee:          12");
  }

  public int calculateTotalScore() {
    return this.ones + this.twos + this.threes + this.fours + this.fives + this.sixes +
            this.threeOfAKind + this.fourOfAKind + this.smallStraight + this.longStraight +
            this.fullHouse + this.yahtzee;
  }

  public Boolean anyRemaining() {
    return this.ones == -1        ||
        this.twos == -1           ||
        this.threes == -1         ||
        this.fours == -1          ||
        this.fives == -1          ||
        this.sixes == -1          ||
        this.threeOfAKind == -1   ||
        this.fourOfAKind == -1    ||
        this.smallStraight == -1  ||
        this.longStraight == -1   ||
        this.fullHouse == -1      ||
        this.yahtzee == -1;
  }


  public void calculateOnes(Die[] dice) {
    int totalScore = 0;

    if (this.ones != -1) throw new RuntimeException("CHEATER ALLERT!!!!!\n");

    for (int i = 0; i < 5; i++) {
      if (dice[i].getFace() == 1) {
        totalScore += 1;
      }
    }
    this.ones = totalScore;
  }

  public void calculateTwos(Die[] dice) {
    int totalScore = 0;

    if (this.twos != -1) throw new RuntimeException("CHEATER ALLERT!!!!!\n");

    for (int i = 0; i < 5; i++) {
      if (dice[i].getFace() == 2) {
        totalScore += 2;
      }
    }
    this.twos = totalScore;
  }

  public void calculateThrees(Die[] dice) {
    int totalScore = 0;

    if (this.threes != -1) throw new RuntimeException("CHEATER ALLERT!!!!!\n");

    for (int i = 0; i < 5; i++) {
      if (dice[i].getFace() == 3) {
        totalScore += 3;
      }
    }
    this.threes = totalScore;
  }

  public void calculateFours(Die[] dice) {
    int totalScore = 0;

    if (this.fours != -1) throw new RuntimeException("CHEATER ALLERT!!!!!\n");

    for (int i = 0; i < 5; i++) {
      if (dice[i].getFace() == 4) {
        totalScore += 4;
      }
    }
    this.fours = totalScore;
  }

  public void calculateFives(Die[] dice) {
    int totalScore = 0;

    if (this.fives != -1) throw new RuntimeException("CHEATER ALLERT!!!!!\n");

    for (int i = 0; i < 5; i++) {
      if (dice[i].getFace() == 5) {
        totalScore += 5;
      }
    }
    this.fives = totalScore;
  }

  public void calculateSixes(Die[] dice) {
    int totalScore = 0;

    if (this.sixes != -1) throw new RuntimeException("CHEATER ALLERT!!!!!\n");

    for (int i = 0; i < 5; i++) {
      if (dice[i].getFace() == 6) {
        totalScore += 6;
      }
    }
    this.sixes = totalScore;
  }

  // sums all the dice in the array
  public void calculateThreeOfAKind(Die[] dice) {
    for (int i = 0; i < 6; i++) {
      if (this.numOfOccurances(dice, i) >= 3) {
        this.threeOfAKind = this.diceSum(dice);
        //System.out.println("you have gotten a three of a kind");
        return;
      }
    }
    this.threeOfAKind = 0;
    return;
  }

  public void calculateFourOfAKind(Die[] dice) {
    for (int i = 0; i < 6; i++) {
      if (this.numOfOccurances(dice, i) >= 4) {
        this.fourOfAKind = this.diceSum(dice);
        return;
      }
    }
    this.fourOfAKind = 0;
    return;
  }

  public void calculatesSmallStraight(Die[] dice) {
    Die[] newDice = sortDice(dice);
    int consecutive = 0;
    for (int i = 1; i < 5; i++) {
      // if this is one more than the last,
      if (newDice[i].getFace() == newDice[i-1].getFace() + 1) {
        ++consecutive;
        if (consecutive == 4) {
          this.smallStraight = 30;
          return;
        }
      }
      // otherwise, return to 0
      else {
        consecutive = 0;
      }
    }
    this.smallStraight = 0;
    return;
  }

  // if all dice are in a
  public void calculateLongStraight(Die[] dice) {
    Die[] newDice = sortDice(dice);
    for (int i = 1; i < 5; i++) {
      if (newDice[i].getFace() != newDice[i-1].getFace() + 1) {
        this.longStraight = 0;
        return;
      }
    }
    this.longStraight = 40;
    return;
  }

  public void calculateFullHouse(Die[] dice) {
    // finds the three of a kind
    int occurrances = 0;
    for (int i = 1; i <= 6; i++) {
      for (Die d : dice) {
        if (d.getFace() == i) {
          occurrances++;
        }
      }
      if (occurrances == 3) {
        int newOccurrances = 0;
        for (int j = 1; j <= 6; j++) {
          for (Die d : dice) {
            if (d.getFace() == j) {
              newOccurrances++;
            }
          }
          if (newOccurrances == 2) {
            this.fullHouse = 25;
            return;
          }
          else newOccurrances = 0;
        }
      }
      else occurrances = 0;
    }
    this.fullHouse = 0;
    return;
  }

  // YAHTZEE!
  public void calculateYahtzee(Die[] dice) {
    for (int i = 1; i <= 6; i++) {
      int occurrances = 0;
      for (Die d : dice) {
        if (d.getFace() == i) occurrances++;
        if (occurrances == 5) {
          this.yahtzee = 50;
          return;
        }
      }
    }
    this.yahtzee = 0;
    return;
  }

  // returns the sum of all the faces on the dice
  // useful as a helper function
  public int diceSum(Die[] dice) {
    int sum = 0;
    for (Die d : dice) {
      sum += d.getFace();
    }
    return sum;
  }

  // returns the number of occurrances of the given number in the array
  public int numOfOccurances(Die[] dice, int num) {
    int occ = 0;
    for (Die d : dice) {
      if (d.getFace() == num) ++occ;
    }
    return occ;
  }

  // returns an array of dice sorted by their face values
  // just use insertion sort.  Its so small that efficiency won't matter
  public Die[] sortDice(Die[] dice) {
    ArrayList<Die> newDice = new ArrayList<>(5);
    for (Die d : dice) {
      this.insertDie(newDice, d);
    }

    Die[] result = new Die[5];
    for (int i = 0; i < 5; i++) {
      result[i] = newDice.get(i);
    }

    return result;
  }

  // uses insertion sort to insert the given die into the array
  public void insertDie(ArrayList<Die> dice, Die d) {
    for (int i = 0; i < dice.size(); i++) {
      if (d.getFace() <= dice.get(i).getFace()) {
        dice.add(i, d);
        return;
      }
    }
    dice.add(d);
    return;
  }
}

class Die {
  int face;

  Die() {
    this.face = 0;
  }

  Die(int face) {
    this.face = face;
  }

  public void setFace(int face) {
    this.face = face;
  }

  public int getFace() {
    return this.face;
  }

  public void roll() {
    this.face = new Random().nextInt(6) + 1;
  }
}
