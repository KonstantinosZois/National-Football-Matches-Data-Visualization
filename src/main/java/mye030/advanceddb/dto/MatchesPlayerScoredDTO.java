package mye030.advanceddb.dto;

import java.time.LocalDate;

public class MatchesPlayerScoredDTO {
	
	
	
	private LocalDate matchDate;
	private String homeTeamName;
	private String awayTeamName;
	private int homeScore;
	private int awayScore;
	private String tournament;
	private int minuteOfGoal;
	private boolean ownGoal;
	private boolean penalty;
	
	
	public MatchesPlayerScoredDTO(LocalDate matchDate, String homeTeamName, String awayTeamName, int homeScore,
			int awayScore, String tournament, int minuteOfGoal, boolean ownGoal, boolean penalty) {
		super();
		this.matchDate = matchDate;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.tournament = tournament;
		this.minuteOfGoal = minuteOfGoal;
		this.ownGoal = ownGoal;
		this.penalty = penalty;
	}
	
	public LocalDate getMatchDate() {
		return matchDate;
	}
	
	public String getHomeTeamName() {
		return homeTeamName;
	}
	
	public String getAwayTeamName() {
		return awayTeamName;
	}
	
	public int getHomeScore() {
		return homeScore;
	}
	
	public int getAwayScore() {
		return awayScore;
	}
	
	public String getTournament() {
		return tournament;
	}
	
	public int getMinuteOfGoal() {
		return minuteOfGoal;
	}
	
	public boolean getOwnGoal() {
		return ownGoal;
	}
	
	public boolean getPenalty() {
		return penalty;
	}
	
	
	
	
}
