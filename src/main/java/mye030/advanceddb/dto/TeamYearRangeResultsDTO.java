package mye030.advanceddb.dto;

public class TeamYearRangeResultsDTO {

	
	private int totalWins;
	private int totalLosses;
	private int totalDraws;
	private int totalMatchesPlayed;
	private int totalGoalsScored;
	private int totalGoalsConceded;
	
	
	
	
	public TeamYearRangeResultsDTO(int totalWins, int totalLosses, int totalDraws,
			int totalMatchesPlayed, int totalGoalsScored, int totalGoalsConceded) {
		super();
		this.totalWins = totalWins;
		this.totalLosses = totalLosses;
		this.totalDraws = totalDraws;
		this.totalMatchesPlayed = totalMatchesPlayed;
		this.totalGoalsScored = totalGoalsScored;
		this.totalGoalsConceded = totalGoalsConceded;
	}
	

	
	public int getTotalWins() {
		return totalWins;
	}
	
	public int getTotalLosses() {
		return totalLosses;
	}
	
	public int getTotalDraws() {
		return totalDraws;
	}
	
	public int getTotalMatchesPlayed() {
		return totalMatchesPlayed;
	}
	
	public int getTotalGoalsScored() {
		return totalGoalsScored;
	}
	
	public int getTotalGoalsConceded() {
		return totalGoalsConceded;
	}
	
	
	
}
