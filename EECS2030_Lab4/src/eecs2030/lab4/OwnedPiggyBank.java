package eecs2030.lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.List;

/**
 * A class representing a piggy bank that has an owner. A piggy bank owns a
 * collection (or possibly collections) of coins, but does not own the coins
 * themselves. In other words, the piggy bank and its collection of coins form a
 * composition.
 * 
 * <p>
 * Only the owner of the piggy bank is able to remove coins from the piggy bank.
 * The piggy bank does own its owner. In other words, the piggy bank and its
 * owner form an aggregation.
 */
public class OwnedPiggyBank
{
	/*
	 * YOU NEED A FIELD HERE TO HOLD THE COINS OF THIS PIGGY BANK
	 */
	private ArrayList<Coin> contents;

	// Prereq checker for removeCoins
	private long bankValue;

	/**
	 * The owner of this pigyg bank.
	 */
	private Owner owner;

	/**
	 * Initializes this piggy bank so that it has the specified owner and its
	 * collection of coins is empty.
	 * 
	 * @param owner
	 *            the owner of this piggy bank
	 */
	public OwnedPiggyBank(Owner owner)
	{
		this.owner = owner;

		this.contents = new ArrayList<Coin>();

		this.bankValue = 0;

	}

	/**
	 * Initializes this piggy bank by copying another piggy bank. This piggy bank
	 * will have the same owner and the same number and type of coins as the other
	 * piggy bank.
	 * 
	 * @param other
	 *            the piggy bank to copy
	 */
	public OwnedPiggyBank(OwnedPiggyBank other)
	{
		this.owner = other.owner;

		this.contents = new ArrayList<Coin>(other.contents);
		this.bankValue = other.bankValue;
	}

	/**
	 * Returns the owner of this piggy bank.
	 * 
	 * <p>
	 * This method is present only for testing purposes. Returning the owner of this
	 * piggy bank allows any user to remove coins from the piggy bank (because any
	 * user can get the owner of this piggy bank)!
	 * 
	 * @return the owner of this piggy bank
	 */
	public Owner getOwner()
	{
		// ALREADY IMPLEMENTED; DO NOT MODIFY
		return this.owner;
	}

	/**
	 * Allows the current owner of this piggy bank to give this piggy bank to a new
	 * owner.
	 * 
	 * @param currentOwner
	 *            the current owner of this piggy bank
	 * @param newOwner
	 *            the new owner of this piggy bank
	 * @throws IllegalArgumentException
	 *             if currentOwner is not the current owner of this piggy bank
	 */
	public void changeOwner(Owner currentOwner, Owner newOwner)
	{
		if (this.owner.equals(currentOwner))
		{
			this.owner = newOwner;
		}
		else
		{
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Adds the specified coins to this piggy bank.
	 * 
	 * @param coins
	 *            a list of coins to add to this piggy bank
	 */
	public void add(List<Coin> coins)
	{
		for (Coin c : coins)
		{
			contents.add(new Coin(c));
			bankValue += c.getValue();
		}

	}

	/**
	 * Returns true if this piggy bank contains the specified coin, and false
	 * otherwise.
	 * 
	 * @param coin
	 *            a coin
	 * @return true if this piggy bank contains the specified coin, and false
	 *         otherwise
	 */
	public boolean contains(Coin coin)
	{
		return contents.contains(coin);

	}

	/**
	 * Allows the owner of this piggy bank to remove a coin equal to the value of
	 * the specified coin from this piggy bank.
	 * 
	 * <p>
	 * If the specified user is not equal to the owner of this piggy bank, then the
	 * coin is not removed from this piggy bank, and null is returned.
	 * 
	 * @param user
	 *            the person trying to remove the coin
	 * @param coin
	 *            a coin
	 * @return a coin equal to the value of the specified coin from this piggy bank,
	 *         or null if user is not the owner of this piggy bank @pre. the piggy
	 *         bank contains a coin equal to the specified coin
	 */
	public Coin remove(Owner user, Coin coin)
	{
		if (this.owner.equals(user))
		{
			if (contents.contains(coin))
			{
				contents.remove(coin);
				bankValue -= coin.getValue();
				return new Coin(coin);
			}
		}
		return null;
	}

	/**
	 * Allows the owner of this piggy bank to remove the smallest number of coins
	 * whose total value in cents is equal to the specified value in cents from this
	 * piggy bank.
	 * 
	 * <p>
	 * Returns the empty list if the specified user is not equal to the owner of
	 * this piggy bank.
	 * 
	 * @param user
	 *            the person trying to remove coins from this piggy bank
	 * @param value
	 *            a value in cents
	 * @return the smallest number of coins whose total value in cents is equal to
	 *         the specified value in cents from this piggy bank @pre. the piggy
	 *         bank contains a group of coins whose total value is equal to
	 *         specified value
	 */
	public List<Coin> removeCoins(Owner user, int value)
	{
		ArrayList<Coin> coinList = new ArrayList<Coin>();
		Collections.sort(this.contents);
		int counter = contents.size() - 1;
		int lastAddedIndex = 0;

		// Store required amount in balance then keep subtracting from it until 0
		int balance = value;
		if (bankValue >= balance && this.owner.equals(user))
		{
			while (balance > 0)
			{
				balance -= contents.get(counter).getValue();
				// If subtracting the coin amount results in a negative value add the value back
				// but do nothing with the coin
				// Otherwise take the coin and add it to the coin list
				if (balance < 0)
				{
					balance += contents.get(counter).getValue();
				}
				else
				{
					bankValue -= contents.get(counter).getValue();
					coinList.add(new Coin(contents.get(counter)));
					contents.remove(counter);
					lastAddedIndex = counter;
				}
				counter--;

				// If the counter hits -1 (iterated through entire bank balance) and still has
				// not
				// arrived at the required balance remove the last coin added
				// Then iterate through the coin after that
				if (counter < 0 && balance != 0)
				{
					balance += coinList.get(coinList.size() - 1).getValue();
					contents.add(coinList.get(coinList.size() - 1));
					coinList.remove(coinList.size() - 1);
					Collections.sort(this.contents);
					counter = lastAddedIndex - 1;
				}
			}
		}
		return coinList;

	}

	/**
	 * Returns a deep copy of the coins in this piggy bank. The returned list has
	 * its coins in sorted order (from smallest value to largest value; i.e.,
	 * pennies first, followed by nickels, dimes, quarters, loonies, and toonies).
	 * 
	 * @return a deep copy of the coins in this piggy bank
	 */
	public List<Coin> deepCopy()
	{
		List<Coin> l = new ArrayList<Coin>();
		for (Coin c : contents)
		{
			l.add(new Coin(c));
		}
		return l;

	}
}
