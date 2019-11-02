import java.util.Scanner;
import java.lang.Math;

/**
 * Class to play a casino game that takes user input for the bet amounts and
 * then randomly generates pulls, like a slot machine. When the user is done
 * playing, the program will displays their winnings.
 * @author Lindsey Reynolds
 * @date 10/05/19
 *
 */
public class Assig2
{
   private static Scanner keyboard = new Scanner(System.in);

   public static void main(String[] args)
   {
      // Initiate bet and ThreeString variables to be used in the loop
      int bet;
      int totalBets = 0;
      ThreeString pullString = new ThreeString();
      

      // While the user inputs valid bets, continue the game 
      // Stop when the user enters 0
      

      do 
      {
         bet = getBet();
         totalBets += bet;
         
         // If the user 
         if(bet != 0)
         {
            // Initiate a new pull and create a new ThreeString object
            pullString = pull();
            
            // Determine how much the user won, if anything
            int winnings = bet * getPayMultiplier(pullString);
            pullString.saveWinnings(winnings);
            
            // Display the user's results along with their winnings
            display(pullString, winnings);
         } 
         else 
         {
            System.out.println(pullString.displayWinnings());
            int finalAmount = pullString.getTotalWinnings() - totalBets;
            System.out.println(
               "You bet: $" + totalBets + 
               ", so you left with: $" + finalAmount);
            keyboard.close();
         }
      } 
      while(bet != 0);
      
   }

   
   /**
    * Method to retrieve the user's bet amount for this pull
    * @return an int containing the user's bet amount
    */
   public static int getBet()
   {
      // boolean to keep track of whether or not the user input a valid bet
      boolean validInput = false;
      
      // Initialize an int, in order to start the while loop
      int userInput = 0;
      
      // While the user enters invalid input, continue prompting them
      while(!validInput)
      {
         System.out.println(
            "How much would you like to bet (1 - 100) or 0 to quit?");
         // Read in the user's input from the keyboard
         userInput = keyboard.nextInt();
         
         // Check if the user's input was valid and update the boolean
         if(userInput >= 0 && userInput <= 100)
         {
            validInput = true;
         } 
      }
      
      return userInput;
   }
   
   /**
    * Method that creates a new ThreeString object, containing three randomly 
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
      currentPull.setString1(randString1);
      currentPull.setString2(randString2);
      currentPull.setString3(randString3);
      
      // Return the newly created ThreeString object
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
      // created below
      String randomString = ""; 
      
      // Create a random double between 0.0 and 1.0
      double decimal = Math.random();
      
      // Turn the random double into an int between 0 and 1000
      int wholeNum = (int) (decimal * 1000);
      
      // 50% of the time, create a "(space)" string
      if(wholeNum <= 500)
      {
         randomString = "(space)";
      } 
      // 25% of the time, create a "cherries" string
      else if(wholeNum > 500 && wholeNum <= 750)
      {
         randomString = "cherries";
      }
      // 12.5% of the time, create a "BAR" string
      else if(wholeNum > 750 && wholeNum <= 875)
      {
         randomString = "BAR";
      }
      // 12.5% of the time, create a "7" string
      else if(wholeNum > 875 && wholeNum <= 1000)
      {
         randomString = "7";
      }
      
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
      // cherries  [not cherries]  [any] pays 5 × bet
      if(string1.equals("cherries") && !string2.equals("cherries"))
      {
         return 5;
      }
      // cherries  cherries  [not cherries] pays 15 × bet
      else if(string1.equals("cherries") && string2.equals("cherries") &&
         !string3.equals("cherries"))
      {
         return 15;
      } 
      // cherries  cherries  cherries pays 30 × bet
      else if(string1.equals("cherries") && string2.equals("cherries") &&
         string3.equals("cherries"))
      {
         return 30;
      } 
      // BAR  BAR  BARpays 50 × bet
      else if (string1.equals("BAR") && string2.equals("BAR") &&
         string3.equals("BAR"))
      {
         return 50;
      }
      // 7  7  7 pays 100 × bet
      else if(string1.equals("7") && string2.equals("7") &&
         string3.equals("7"))
      {
         return 100;
      } 
      // If none of the previous wins are true, return 0
      else 
      {
         return 0;
      }
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
      {
         System.out.println("Sorry, you lost.");
      }
      else 
      {
         System.out.println("Congratulations! You win: $" + winnings);
      }
   }
}

/**
 * Class to store information about a user's current game, including their
 * pull results and winnings.
 * @author Lindsey Reynolds
 */
class ThreeString
{
   // Static variables
   private static final int MAX_LEN = 20;
   private static final int MAX_PULLS = 40;
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls = 0;
   
   // Instance variables
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
    * @param str
    * @return boolean
    */
   private boolean validString(String str)
   {
      // Check that the string is not null and less than or equal to MAX_LEN
      if(str != null && str.length() <= MAX_LEN)
      {
         return true;
      }
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
    * @return a String object containing all three strings
    */
   public String toString()
   {
      String returnString = string1 + "   " + string2 + "   " + string3;
      return returnString;
   }
   
   /**
    * Method to save the users winnings from the last pull and add them to 
    * the pullWinnings array.
    * @param winnings
    * @return boolean
    */
   public boolean saveWinnings(int winnings) 
   {  
      // Add the winnings to the pullWinnings array
      pullWinnings[numPulls] = winnings;
      numPulls++; // Increment the number of pulls 
      return true;
   }
   
   /**
    * Method to return all the users plays and their total winnings
    * @return a String containing all the winning information
    */
   public String displayWinnings()
   {
      // Create the string object to be returned
      String outputWinnings = "";
      int totalWinnings = 0;
      
      // Loop through the pullWinnings array to get all the users winnings
      for(int i = 0; i < numPulls; i++)
      {
         // Add each winning to the end of the string
         outputWinnings = outputWinnings + pullWinnings[i] + " ";
         totalWinnings += pullWinnings[i]; // update the total winnings
      }
      
      // Add the users total winnings to the final string
      outputWinnings = outputWinnings + "\nYour total winnings were: $"
         + totalWinnings;
      
      return outputWinnings;
   }
   
   public int getTotalWinnings()
   {
      int totalWinnings = 0;
      
      // Loop through the pullWinnings array to get all the users winnings
      for(int i = 0; i < numPulls; i++)
      {
         totalWinnings += pullWinnings[i]; // update the total winnings
      }
      return totalWinnings;
   }

}

/******************************** OUTPUT ************************************
 * 
How much would you like to bet (1 - 100) or 0 to quit?
20
whirrrrrr .... and your pull is ...
(space)   (space)   7
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
10
whirrrrrr .... and your pull is ...
(space)   7   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
30
whirrrrrr .... and your pull is ...
(space)   (space)   BAR
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
100
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
111
How much would you like to bet (1 - 100) or 0 to quit?
101
How much would you like to bet (1 - 100) or 0 to quit?
20
whirrrrrr .... and your pull is ...
cherries   (space)   7
Congratulations! You win: $100
How much would you like to bet (1 - 100) or 0 to quit?
43
whirrrrrr .... and your pull is ...
cherries   cherries   (space)
Congratulations! You win: $645
How much would you like to bet (1 - 100) or 0 to quit?
32
whirrrrrr .... and your pull is ...
(space)   (space)   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
5
whirrrrrr .... and your pull is ...
7   (space)   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
9
whirrrrrr .... and your pull is ...
BAR   (space)   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
4
whirrrrrr .... and your pull is ...
(space)   BAR   7
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
9
whirrrrrr .... and your pull is ...
cherries   (space)   BAR
Congratulations! You win: $45
How much would you like to bet (1 - 100) or 0 to quit?
23
whirrrrrr .... and your pull is ...
BAR   (space)   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
-1
How much would you like to bet (1 - 100) or 0 to quit?
40
whirrrrrr .... and your pull is ...
cherries   cherries   (space)
Congratulations! You win: $600
How much would you like to bet (1 - 100) or 0 to quit?
93
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
20
whirrrrrr .... and your pull is ...
(space)   (space)   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
42
whirrrrrr .... and your pull is ...
cherries   (space)   cherries
Congratulations! You win: $210
How much would you like to bet (1 - 100) or 0 to quit?
32
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
12
whirrrrrr .... and your pull is ...
(space)   (space)   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
94
whirrrrrr .... and your pull is ...
(space)   BAR   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
38
whirrrrrr .... and your pull is ...
(space)   (space)   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
94
whirrrrrr .... and your pull is ...
7   (space)   BAR
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
38
whirrrrrr .... and your pull is ...
cherries   cherries   (space)
Congratulations! You win: $570
How much would you like to bet (1 - 100) or 0 to quit?
38
whirrrrrr .... and your pull is ...
(space)   cherries   7
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
95
whirrrrrr .... and your pull is ...
7   cherries   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
03
whirrrrrr .... and your pull is ...
(space)   BAR   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
38
whirrrrrr .... and your pull is ...
(space)   cherries   (space)
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
2
whirrrrrr .... and your pull is ...
(space)   BAR   cherries
Sorry, you lost.
How much would you like to bet (1 - 100) or 0 to quit?
3
whirrrrrr .... and your pull is ...
cherries   (space)   (space)
Congratulations! You win: $15
How much would you like to bet (1 - 100) or 0 to quit?
0
0 0 0 0 100 645 0 0 0 0 45 0 600 0 0 210 0 0 0 0 0 570 0 0 0 0 0 15 
Your total winnings were: $2185
 * 
 * 
 */

