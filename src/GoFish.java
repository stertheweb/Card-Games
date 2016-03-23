import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class GoFish {

	public static int STARTING_HAND_SIZE = 7;

	public static void main(String[] args)
	{
		// TODO: Create deck of cards
		ArrayList<Integer> pool = createDeck();
		Scanner input = new Scanner(System.in);

		// Shuffle cards
		//TODO: Shuffle Cards
		Collections.shuffle(pool);
		playOneGame(pool, input);
	}

	public static void playOneGame(ArrayList<Integer> pool, Scanner input)
	{
		ArrayList<Integer> computer = new ArrayList<Integer>();
		ArrayList<Integer> person = new ArrayList<Integer>();
		ArrayList<Integer> computerPile = new ArrayList<Integer> ();
		ArrayList<Integer> personPile = new ArrayList<Integer>();

		// TODO: Deal cards
		dealHands(person,pool,computer);

		// TODO: Show the person their starting hand
		showGameState(person, computerPile, personPile);
		Random random = new Random();

		// Play the game
		while (computerPile.size() + personPile.size() < 52 || !pool.isEmpty())
		{
			// Let the person play first
			// show the person their cards
			if (!person.isEmpty())
			{
				System.out.println("What card do you want?");
				int card = input.nextInt();

				//TODO: Play one turn with the person doing the choosing
				playOneTurn(card, person, computer, personPile, computerPile, pool);
			}
			else
			{
				//TODO: Let the player draw from the deck
				int cardFromPile = random.nextInt(pool.size());
				person.add(pool.get(cardFromPile));
				pool.remove(cardFromPile);
			}

			showGameState(person, computerPile, personPile);

			// Now it is the computer's turn
			// Randomly choose a card
			if (!computer.isEmpty())
			{
				int card = computer.get((int)(Math.random()*computer.size()));	
				System.out.println("Do you have any "  + card + "'s ?");

				//TODO: Play one turn with the computer doing the choosing
				playOneTurn(card, person, computer, personPile, computerPile, pool);
			}
			else if (!pool.isEmpty())
			{
				//TODO: Let the computer draw from the deck
				int cardFromPile = random.nextInt(pool.size());
				person.add(pool.get(cardFromPile));
				pool.remove(cardFromPile);
			}

			showGameState(person, computerPile, personPile);
		}

		// TODO: Determine the winner and tell the user--remember ties are possible
		if ( personPile.size() > computerPile.size())
		{
			System.out.println("You are the winner with " + personPile.size() + " books");
		}
		if ( personPile.size() < computerPile.size())
		{
			System.out.println("You lost with the computer have " + computerPile.size() + " books.");
		}
		if ( personPile.size() == computerPile.size())
		{
			System.out.println("The game is a tie with " + computerPile.size() + " books.");
		}          
	}

	public static void showGameState(ArrayList<Integer> person, ArrayList<Integer> computerPile,
			ArrayList<Integer> personPile)
	{
		System.out.println("Here are your cards");
		showCards(person);
		System.out.println("Here is your pile");
		showCards(personPile);
		System.out.println("Here is my pile");
		showCards(computerPile);
	}

	public static void playOneTurn(int card, ArrayList<Integer> chooser, ArrayList<Integer> chosen,
			ArrayList<Integer> chooserPile, ArrayList<Integer> chosenPile, ArrayList<Integer> pool)
	{
		if (chosen.contains(card))
		{
			//TODO: Chosen gives cards to Chooser
			transferCards(card, chooser, chosen);
			//TODO: If there is a set of four matching cards, put them up on the table
			int x = 0;
			int deck = 0;
			for (x = 0; x < chooser.size(); x++)
			{
				if (card == chooser.get(x))
				{
					deck = deck + 1;
				}
				if(deck == 4)
				{
					chooserPile.add(card);
					int y = 0;
					for(y = 0; y < chooser.size() + 1; y++)
					{
						if (chooser.get(y) == card)
						{
							chooser.remove(y);
						}
					}
				} 
			}
		}
		else
		{
			System.out.println("Go fish!");

			//TODO: Draw a card by removing it from the pool and putting it in the chooser's hand
			Random random = new Random();
			if(pool.size() > 0)
			{
				int cardFromPile = random.nextInt(pool.size());
				chooser.add(pool.get(cardFromPile));
				pool.remove(cardFromPile);
			}

			//TODO: If there is a set of four matching cards, put them on the table
			int x = 0;
			for (x = 0; x < chooser.size(); x++)
			{
				int frequency = Collections.frequency(chooser, x);
				if (frequency == 4)
				{
					for (int i = 0; i < 4; i++)
					{
						chooserPile.add(x);
						chooser.remove(new Integer(x));
					}
				}
				if(pool.isEmpty() && chooser.get(0) == chooser.get(3))
				{
					int y = chooser.get(0);
					for (int i = 0; i < 4; i++)
					{
					chooserPile.add(new Integer(y));
					chooser.remove(new Integer(y));
					}
				}
			}
		}
	}

	public static void transferCards(int card, ArrayList<Integer> destination, ArrayList<Integer> source)
	{
		while (source.contains(card))
		{
			destination.add(card);
			source.remove(new Integer(card));
		}
	}

	public static void dealHands(ArrayList<Integer> deck, ArrayList<Integer> hand1, ArrayList<Integer> hand2)
	{
		//TODO: Deal the cards
		Random randomNumber = new Random();
		if (deck.size() > 0)
		{
			int count = 0;
			while (count <= STARTING_HAND_SIZE)
			{
				int randomIndex = randomNumber.nextInt(deck.size());
				hand1.add(deck.get(randomIndex));
				deck.remove(deck.get(randomIndex));
				count++;
			}
			count = 0;
			while (count <= STARTING_HAND_SIZE)
			{
				int randomIndex = randomNumber.nextInt(deck.size());
				hand2.add(deck.get(randomIndex));
				deck.remove(deck.get(randomIndex));
				count++;
			}
		}
		else 
		{
			return;	
		}
	}


	public static ArrayList<Integer> createDeck()
	{
		//TODO: Create a deck of cards
		ArrayList<Integer> createDeck = new ArrayList<Integer>();
		int i = 0;
		while (i < 52)
		{
			int addRankings = (i % 13) + 1;
			createDeck.add(addRankings);
			i++;
		}
		return createDeck;
	}

	public static void showCards(ArrayList<Integer> cards)
	{
		// TODO: Sort the cards to make it easier for the user to know what they have
		Collections.sort(cards);
		for (Integer i: cards)
		{
			System.out.print(i + " ");
		}
		System.out.println();
	}

}