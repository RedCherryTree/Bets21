package domain;

import java.util.Vector;

public class MultipleQuoteBet extends Transaction{
	private Vector<Quote> betQuotes= new Vector<Quote>();
	
	
	public MultipleQuoteBet() {
		super();
	}
	
	public MultipleQuoteBet(double money, RegisteredUser user, Vector<Quote> betQuotes) {
		super(money, user);
		this.betQuotes=betQuotes;
	}
	
	
	public Vector<Quote> getBetQuotes() {
		return betQuotes;
	}

	public void setBetQuotes(Vector<Quote> betQuotes) {
		this.betQuotes = betQuotes;
	}

	public boolean hasBeenDecided() {
		for(Quote q: betQuotes) {
			if(!q.getQuestion().isHasFinished()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean hasWon() {
		for(Quote q: betQuotes) {
			if(!q.isWinner()) {
				return false;
			}
		}
		return true;
	}
	
	public double calculateFinalMultiplier() {
		double multiplier= 1;;
		for(Quote q: betQuotes) {
			multiplier=multiplier*q.getQuoteMultiplier();
		}
		return multiplier;
	}
	
	public double calculatePrize() {
		return this.calculateFinalMultiplier()*super.getMoney();
	}
	
	public double calculateMinimumBet() {
		double minimumBet= 0;
		for(Quote q: betQuotes) {
			minimumBet=minimumBet+q.getQuestion().getBetMinimum();
		}
		return minimumBet;
	}
	@Override
	public String toString() {
		return super.toString()+" Has been decided= "+this.hasBeenDecided()+"]";
	}
}
