import java.util.Scanner;
import java.lang.Math;

/**
 * Class to play a casino game that takes user input for the bet amounts and
 * then randomly generates pulls, like a slot machine. 
 * @author Lindsey Reynolds
 * @date 11/05/19
 */
public class Assig2
{
   // Create a scanner object to read in user input
   private static Scanner keyboard = new Scanner(System.in);

   public static void main(String[] args)
   {
      // Initiate variables to be used in the do-while loop
      int bet;
      ThreeString pullString = new ThreeString();
      boolean playAgain = true;

      // While the user bets and does not reach 40 plays, continue the game 
      do 
      {
         bet = getBet();

         // Exit the loop if the user quits by entering 0
         if(bet == 0) break;

         // Initiate a new pull and create a ThreeString object
         pullString = pull();

         // Determine how much the user won
         int winnings = bet * getPayMultiplier(pullString);

         // Display the user's results along with their winnings
         display(pullString, winnings);

         // Save their winnings and determine if they can play again
         playAgain = pullString.saveWinnings(winnings);
      } 
      while(playAgain);

      // Print out the user's results and end the program
      System.out.println("\nThank you for playing at the Casino!");  
      System.out.println(pullString.displayWinnings());
      keyboard.close();
      System.exit(0);
   }

   /**
    * Method to retrieve the user's bet amount for this pull
    * @return an int containing the user's bet amount
    */
   public static int getBet()
   {
      // boolean to track if the users input is valid
      boolean validInput = false;

      // Initiate userInput variable to be used in the do-while loop
      int userInput;

      // Prompt the user to enter a bet until they enter valid input
      do
      {
         System.out.println(
            "How much would you like to bet (1 - 100) or 0 to quit?");
         // Read in the user's input from the keyboard
         userInput = keyboard.nextInt();

         // Check if the user's input is valid (0-100) and update the boolean 
         if(userInput >= 0 && userInput <= 100) 
            validInput = true;

      }
      while(!validInput);

      return userInput;
   }

   /**
    * Method to create a new ThreeString object, containing three randomly 
    * generated strings
    * @return a ThreeString object
    */
   public static ThreeString pull()
   {
      ThreeString currentPull = new ThreeString();

      // Use the helper method, randString(), to generate three random strings
      String randString1 = randString();
      String randString2 = randString();
      String randString3 = randString();

      // Set the ThreeString object's string variables with the random strings
      if(!currentPull.setString1(randString1))
         currentPull.setString1("(space)");
      
      if(!currentPull.setString2(randString2))
         currentPull.setString2("cherries");
      
      if(!currentPull.setString3(randString3))
         currentPull.setString3("BAR");

      return currentPull;
   }

   /**
    * Method to randomly generate a String based on specific odds. 50% chance
    * of getting "(space)", 25% chance of getting "cherries", 12.5% chance of 
    * getting "BAR" and 12.5% chance of getting "7".
    * @return a new random String object
    */
   private static String randString() 
   {
      // This String will be given a value depending on the random number 
      String randomString = ""; 

      double decimal = Math.random(); // Random double between 0.0 and 1.0

      // Turn the double into an int between 0 and 1000
      int wholeNum = (int) (decimal * 1000);

      // 50% of the time, create a "(space)" string
      if(wholeNum < 500)
         randomString = "(space)";

      // 25% of the time, create a "cherries" string
      else if(wholeNum >= 500 && wholeNum < 750)
         randomString = "cherries";

      // 12.5% of the time, create a "BAR" string
      else if(wholeNum >= 750 && wholeNum < 875)
         randomString = "BAR";

      // 12.5% of the time, create a "7" string
      else if(wholeNum >= 875 && wholeNum < 1000)
         randomString = "7";

      return randomString;
   }

   /**
    * Method to determine the correct pay multiplier, depending on the three
    * strings that were generated by thePull.
    * @param thePull
    * @return an int
    */
   public static int getPayMultiplier(ThreeString thePull)
   {  
      // Retrieve the strings from the ThreeString object 
      String string1 = thePull.getString1();
      String string2 = thePull.getString2();
      String string3 = thePull.getString3();

      // Check to see if the user won, with 5 possible outcomes

      // cherries  [not cherries]  [any] 
      if(string1.equals("cherries") && 
         !string2.equals("cherries"))
         return 5;

      // cherries  cherries  [not cherries] 
      else if(string1.equals("cherries") && 
         string2.equals("cherries") &&
         !string3.equals("cherries"))
         return 15;

      // cherries  cherries  cherries 
      else if(string1.equals("cherries") && 
         string2.equals("cherries") &&
         string3.equals("cherries"))
         return 30;

      // BAR  BAR  BAR
      else if (string1.equals("BAR") && 
         string2.equals("BAR") &&
         string3.equals("BAR"))
         return 50;

      // 7  7  7 
      else if(string1.equals("7") && 
         string2.equals("7") &&
         string3.equals("7"))
         return 100;

      // If the user did not win, return 0
      else 
         return 0;
   }

   /**
    * Method to display the user's pull results and their winnings
    * @param thePull
    * @param winnings
    */
   public static void display(ThreeString thePull, int winnings)
   {
      System.out.println("whirrrrrr .... and your pull is ...");

      // Display the pull results to the user
      System.out.println(thePull.toString());

      // Display the user's winnings or let them know they lost
      if (winnings == 0)
         System.out.println("Sorry, you lost. \n");
      else 
         System.out.println("Congratulations! You win: $" + winnings + "\n");
   }
}

/**
 * Class to store information about the user's current game, including their
 * pull results and winnings.
 * @author Lindsey Reynolds
 * @date 11/05/19
 */
class ThreeString
{
   // Declare static variables
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls = 0;

   // Declare instance variables
   private String string1, string2, string3;

   /**
    * ThreeString constructor. Takes no parameters and sets all strings to "".
    */
   public ThreeString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }

   /**
    * Helper method to test whether or not the input string is valid. A string 
    * is valid when it is not null and its length is less than or equal to 20.
    * @param String str
    * @return boolean
    */
   private boolean validString(String str)
   {
      // Check that the string is not null and less than or equal to MAX_LEN
      if(str != null && str.length() <= MAX_LEN)
         return true;

      return false;
   }

   /**
    * Mutator method to change the value of string1
    * @param str
    * @return boolean stating whether or not the string was updated
    */
   public boolean setString1(String str)
   {
      if(validString(str)) // Check that this is a valid string first
      {
         string1 = str;
         return true;
      }
      return false;
   }

   /**
    * Mutator method to change the value of string2
    * @param str
    * @return boolean stating whether or not the string was updated
    */
   public boolean setString2(String str)
   {
      if(validString(str)) // Check that this is a valid string first
      {
         string2 = str;
         return true;
      }
      return false;
   }

   /**
    * Mutator method to change the value of string3
    * @param str
    * @return boolean stating whether or not the string was updated
    */
   public boolean setString3(String str)
   {
      if(validString(str)) // Check that this is a valid string first
      {
         string3 = str;
         return true;
      }
      return false;
   }

   /**
    * Getter method to retrieve string1
    * @return the string1 private variable
    */
   public String getString1()
   {
      return string1;
   }

   /**
    * Getter method to retrieve string2
    * @return the string2 private variable
    */
   public String getString2()
   {
      return string2;
   }

   /**
    * Getter method to retrieve string3
    * @return the string3 private variable
    */
   public String getString3()
   {
      return string3;
   }

   /**
    * Method to display all three strings in a row
    * @return String 
    */
   public String toString()
   {
      return string1 + "   " + string2 + "   " + string3;
   }

   /**
    * Method to save the users winnings from the last pull and add them to 
    * the pullWinnings array.
    * @param winnings
    * @return boolean
    */
   public boolean saveWinnings(int winnings) 
   {  
      // If numPulls exceeds 40, return false in order to end the game
      if(numPulls == 40) 
         return false;

      // Add the winnings to the pullWinnings array
      pullWinnings[numPulls] = winnings;
      numPulls++; // Increment the number of pulls 

      return true;
   }

   /**
    * Method to return all the users plays and their total winnings
    * @return String
    */
   public String displayWinnings()
   {
      // Create the string object to be returned
      String outputWinnings = "Here are your individual winnings:\n"; 

      // Initialize a variable to keep track of all the user's winnings
      int totalWinnings = 0; 

      // Loop through the pullWinnings array to get all the users winnings
      for(int i = 0; i < numPulls; i++)
      {
         // Add each winning to the end of the string
         outputWinnings += pullWinnings[i] + " ";
         totalWinnings += pullWinnings[i]; // update the total winnings
      }

      // Add the users total winnings to the final string
      outputWinnings += "\nYour total winnings are: $" + totalWinnings;

      return outputWinnings;
   }
}

/**********************************OUTPUT**************************************

How much would you like to bet (1 - 100) or 0 to quit?
-10
How much would you like to bet (1 - 100) or 0 to quit?
293
How much would you like to bet (1 - 100) or 0 to quit?
12
whirrrrrr .... and your pull is ...
(space)   cherries   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
42
whirrrrrr .... and your pull is ...
BAR   7   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
52
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
34
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
23
whirrrrrr .... and your pull is ...
cherries   BAR   (space)
Congratulations! You win: $115

How much would you like to bet (1 - 100) or 0 to quit?
52
whirrrrrr .... and your pull is ...
BAR   7   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
63
whirrrrrr .... and your pull is ...
(space)   cherries   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
42
whirrrrrr .... and your pull is ...
(space)   BAR   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
35
whirrrrrr .... and your pull is ...
(space)   (space)   cherries
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
63
whirrrrrr .... and your pull is ...
(space)   cherries   cherries
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
24
whirrrrrr .... and your pull is ...
(space)   (space)   BAR
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
73
whirrrrrr .... and your pull is ...
cherries   (space)   BAR
Congratulations! You win: $365

How much would you like to bet (1 - 100) or 0 to quit?
42
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
36
whirrrrrr .... and your pull is ...
cherries   cherries   (space)
Congratulations! You win: $540

How much would you like to bet (1 - 100) or 0 to quit?
43
whirrrrrr .... and your pull is ...
BAR   (space)   7
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
25
whirrrrrr .... and your pull is ...
cherries   BAR   7
Congratulations! You win: $125

How much would you like to bet (1 - 100) or 0 to quit?
62
whirrrrrr .... and your pull is ...
(space)   7   BAR
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
34
whirrrrrr .... and your pull is ...
(space)   BAR   BAR
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
52
whirrrrrr .... and your pull is ...
cherries   cherries   (space)
Congratulations! You win: $780

How much would you like to bet (1 - 100) or 0 to quit?
74
whirrrrrr .... and your pull is ...
(space)   cherries   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
4
whirrrrrr .... and your pull is ...
cherries   BAR   (space)
Congratulations! You win: $20

How much would you like to bet (1 - 100) or 0 to quit?
43
whirrrrrr .... and your pull is ...
cherries   (space)   BAR
Congratulations! You win: $215

How much would you like to bet (1 - 100) or 0 to quit?
64
whirrrrrr .... and your pull is ...
(space)   BAR   7
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
42
whirrrrrr .... and your pull is ...
BAR   BAR   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
7
whirrrrrr .... and your pull is ...
7   BAR   (space)
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
5
whirrrrrr .... and your pull is ...
cherries   (space)   (space)
Congratulations! You win: $25

How much would you like to bet (1 - 100) or 0 to quit?
1
whirrrrrr .... and your pull is ...
cherries   BAR   (space)
Congratulations! You win: $5

How much would you like to bet (1 - 100) or 0 to quit?
4
whirrrrrr .... and your pull is ...
(space)   cherries   BAR
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
8
whirrrrrr .... and your pull is ...
cherries   cherries   (space)
Congratulations! You win: $120

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
cherries   BAR   BAR
Congratulations! You win: $325

How much would you like to bet (1 - 100) or 0 to quit?
85
whirrrrrr .... and your pull is ...
(space)   (space)   7
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
BAR   cherries   cherries
Sorry, you lost. 

How much would you like to bet (1 - 100) or 0 to quit?
0

Thank you for playing at the Casino!
Here are your individual winnings:
0 0 0 0 115 0 0 0 0 0 0 365 0 540 0 125 0 0 780 0 20 215 0 0 0 25 5 0 120 325 0 0 
Your total winnings were: $2635

 ******************************************************************************/
