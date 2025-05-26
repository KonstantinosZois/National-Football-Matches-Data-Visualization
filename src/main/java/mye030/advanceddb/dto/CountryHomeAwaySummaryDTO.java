package mye030.advanceddb.dto;

public class CountryHomeAwaySummaryDTO {

    private String country;
    private int team;
    private int homeWins;
    private int homeLosses;
    private int homeDraws;
    private int awayWins;
    private int awayLosses;
    private int awayDraws;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;
    private int totalHomeMatchesPlayed;
    private int totalAwayMatchesPlayed;
    private int totalMatchesPlayed;
    private int firstYear;
    private int lastYear;


    public CountryHomeAwaySummaryDTO(String country, int team, int homeWins, int homeLosses, int homeDraws,
                                      int awayWins, int awayLosses, int awayDraws, int totalWins, int totalLosses,
                                      int totalDraws, int totalHomeMatchesPlayed, int totalAwayMatchesPlayed, int totalMatchesPlayed, int firstYear, int lastYear) {
        this.country = country;
        this.team = team;
        this.homeWins = homeWins;
        this.homeLosses = homeLosses;
        this.homeDraws = homeDraws;
        this.awayWins = awayWins;
        this.awayLosses = awayLosses;
        this.awayDraws = awayDraws;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.totalDraws = totalDraws;
        this.totalHomeMatchesPlayed = totalHomeMatchesPlayed;
        this.totalAwayMatchesPlayed = totalAwayMatchesPlayed;
        this.totalMatchesPlayed = totalMatchesPlayed;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
    }


    public String getCountry() {
        return country;
    }


    public int getTeam() {
        return team;
    }



    public int getHomeWins() {
        return homeWins;
    }


    public int getHomeLosses() {
        return homeLosses;
    }



    public int getHomeDraws() {
        return homeDraws;
    }



    public int getAwayWins() {
        return awayWins;
    }


    public int getAwayLosses() {
        return awayLosses;
    }



    public int getAwayDraws() {
        return awayDraws;
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



    public int getTotalHomeMatchesPlayed() {
        return totalHomeMatchesPlayed;
    }



    public int getTotalAwayMatchesPlayed() {
        return totalAwayMatchesPlayed;
    }


    public int getTotalMatchesPlayed() {
        return totalMatchesPlayed;
    }

    public int getFirstYear() {
    	return firstYear;
    }
    
    public int getLastYear() {
    	return lastYear;
    }
  
}

