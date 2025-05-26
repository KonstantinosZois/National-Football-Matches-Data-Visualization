package mye030.advanceddb.dto;

public class PlayerYearlyResultsDTO {

	
	private int year;
	private int totalGoalsByPlayer;
	private int maxGoalsInMatch;
	private int totalGoalsByCountry;
	
	public PlayerYearlyResultsDTO(int year, int totalGoalsByPlayer, int maxGoalsInMatch, int totalGoalsByCountry) {
		super();
		this.year = year;
		this.totalGoalsByPlayer = totalGoalsByPlayer;
		this.maxGoalsInMatch = maxGoalsInMatch;
		this.totalGoalsByCountry = totalGoalsByCountry;
	}
	
	public int getYear() {
		return year;
	}

	public int getTotalGoalsByPlayer() {
		return totalGoalsByPlayer;
	}
	
	public int getMaxGoalsInMatch() {
		return maxGoalsInMatch;
	}
	
	public int getTotalGoalsByCountry() {
		return totalGoalsByCountry;
	}
	
	
}