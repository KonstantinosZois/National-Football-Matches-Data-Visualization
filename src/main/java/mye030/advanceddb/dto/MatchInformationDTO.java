package mye030.advanceddb.dto;

import java.time.LocalDate;

public class MatchInformationDTO {
	
	

	private LocalDate matchDate;
	private String homeTeamName;
    private String homeRegion;
    private String homeDevelopmentStatus;
    
    private String awayTeamName;
    private String awayRegion;
    private String awayDevelopmentStatus;
    
    private int homeScore;
    private int awayScore;
    private String tournament;
    private String city;
    
    private String countryName;
    private String matchCountryRegion;
    private String matchCountryDevelopmentStatus;
    
    private boolean neutral;
    
    public MatchInformationDTO(LocalDate matchDate, String homeTeamName, String homeRegion,
			String homeDevelopmentStatus, String awayTeamName, String awayRegion, String awayDevelopmentStatus,
			int homeScore, int awayScore, String tournament, String city, String countryName, String matchCountryRegion,
			String matchCountryDevelopmentStatus, boolean neutral) {
		this.matchDate = matchDate;
		this.homeTeamName = homeTeamName;
		this.homeRegion = homeRegion;
		this.homeDevelopmentStatus = homeDevelopmentStatus;
		this.awayTeamName = awayTeamName;
		this.awayRegion = awayRegion;
		this.awayDevelopmentStatus = awayDevelopmentStatus;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.tournament = tournament;
		this.city = city;
		this.countryName = countryName;
		this.matchCountryRegion = matchCountryRegion;
		this.matchCountryDevelopmentStatus = matchCountryDevelopmentStatus;
		this.neutral = neutral;
	}

	public LocalDate getMatchDate() {
		return matchDate;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public String getHomeRegion() {
		return homeRegion;
	}

	public String getHomeDevelopmentStatus() {
		return homeDevelopmentStatus;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public String getAwayRegion() {
		return awayRegion;
	}

	public String getAwayDevelopmentStatus() {
		return awayDevelopmentStatus;
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

	public String getCity() {
		return city;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getMatchCountryRegion() {
		return matchCountryRegion;
	}

	public String getMatchCountryDevelopmentStatus() {
		return matchCountryDevelopmentStatus;
	}

	public boolean isNeutral() {
		return neutral;
	}



}
