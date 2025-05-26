package mye030.advanceddb.dto;

public class CountryYearlyResultsDTO {
	
	private int year;
	private int homeWins;
	private int homeDraws;
	private int homeLosses;
	private int awayWins;
	private int awayDraws;
	private int awayLosses;
	private int totalWins;
	private int totalDraws;
	private int totalLosses;
	private int homeMatchesPlayed;
	private int awayMatchesPlayed;
	private int totalMatchesPlayed;
	private int penaltyMatches;
	private int penaltyWins;
	private int penaltyLosses;
	private int homeGoalsScored;
	private int homeGoalsConceded;
	private int awayGoalsScored;
	private int awayGoalsConceded;
	private int totalGoalsScored;
	private int totalGoalsConceded;
	
	public CountryYearlyResultsDTO(int year, int homeWins, int homeDraws, int homeLosses, int awayWins, int awayDraws,
			int awayLosses, int totalWins, int totalDraws, int totalLosses, int homeMatchesPlayed,
			int awayMatchesPlayed, int totalMatchesPlayed,int penaltyMatches,int penaltyWins,int penaltyLosses,
			int homeGoalsScored, int homeGoalsConceded, int awayGoalsScored, int awayGoalsConceded,
			int totalGoalsScored, int totalGoalsConceded) {
		this.year = year;
		this.homeWins = homeWins;
		this.homeDraws = homeDraws;
		this.homeLosses = homeLosses;
		this.awayWins = awayWins;
		this.awayDraws = awayDraws;
		this.awayLosses = awayLosses;
		this.totalWins = totalWins;
		this.totalDraws = totalDraws;
		this.totalLosses = totalLosses;
		this.homeMatchesPlayed = homeMatchesPlayed;
		this.awayMatchesPlayed = awayMatchesPlayed;
		this.totalMatchesPlayed = totalMatchesPlayed;
		this.penaltyMatches = penaltyMatches;
		this.penaltyWins = penaltyWins;
		this.penaltyLosses = penaltyLosses;
		this.homeGoalsScored = homeGoalsScored;
		this.homeGoalsConceded = homeGoalsConceded;
		this.awayGoalsScored = awayGoalsScored;
		this.awayGoalsConceded = awayGoalsConceded;
		this.totalGoalsScored = totalGoalsScored;
		this.totalGoalsConceded = totalGoalsConceded;
	}
	
	
	public int getYear() {
		return year;
	}
	
	
	public int getHomeWins() {
		return homeWins;
	}
	public int getHomeDraws() {
		return homeDraws;
	}
	public int getHomeLosses() {
		return homeLosses;
	}
	public int getAwayWins() {
		return awayWins;
	}
	public int getAwayDraws() {
		return awayDraws;
	}
	public int getAwayLosses() {
		return awayLosses;
	}
	public int getTotalWins() {
		return totalWins;
	}
	public int getTotalDraws() {
		return totalDraws;
	}
	public int getTotalLosses() {
		return totalLosses;
	}
	public int getHomeMatchesPlayed() {
		return homeMatchesPlayed;
	}
	public int getAwayMatchesPlayed() {
		return awayMatchesPlayed;
	}
	public int getTotalMatchesPlayed() {
		return totalMatchesPlayed;
	}


	public int getPenaltyMatches() {
		return penaltyMatches;
	}


	public int getPenaltyWins() {
		return penaltyWins;
	}


	public int getPenaltyLosses() {
		return penaltyLosses;
	}


	public int getHomeGoalsScored() {
		return homeGoalsScored;
	}


	public int getHomeGoalsConceded() {
		return homeGoalsConceded;
	}


	public int getAwayGoalsScored() {
		return awayGoalsScored;
	}


	public int getAwayGoalsConceded() {
		return awayGoalsConceded;
	}


	public int getTotalGoalsScored() {
		return totalGoalsScored;
	}


	public int getTotalGoalsConceded() {
		return totalGoalsConceded;
	}

	
}
