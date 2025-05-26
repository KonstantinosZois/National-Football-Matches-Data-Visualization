package mye030.advanceddb.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import mye030.advanceddb.dto.CountryHomeAwaySummaryDTO;
import mye030.advanceddb.dto.CountryYearlyResultsDTO;
import mye030.advanceddb.dto.MatchInformationDTO;
import mye030.advanceddb.models.BarChart;
import mye030.advanceddb.models.Countries;
import mye030.advanceddb.models.ScatterPlot;

@Repository
public interface CountriesRepository  extends CrudRepository<Countries, Integer>{
	Countries findById(int isoCode);
	List<Countries> findAll();
	
	@Query("SELECT " +
            "r.country, r.team, r.home_wins, r.home_losses, r.home_draws, " +
            "r.away_wins, r.away_losses, r.away_draws, r.total_wins, r.total_losses, " +
            "r.total_draws, r.total_home_matches_played, r.total_away_matches_played, r.total_matches_played, r.first_year ,r.last_year " +
            "FROM country_home_away_summary r " +
            "WHERE r.team = :isoCode")
    Optional<CountryHomeAwaySummaryDTO> findByIsoCode(@Param("isoCode")int isoCode);
	

	@Query("SELECT t.year,t.home_wins,t.home_draws, t.home_losses,"
			+ "t.away_wins, t.away_draws, t.away_losses,"
			+ " t.total_wins, t.total_draws, t.total_losses, "
			+ "t.home_matches_played, t.away_matches_played, t.total_matches_played, t.penalty_matches,t.penalty_wins,t.penalty_losses,"
			+ "t.home_goals_scored,t.home_goals_conceded,t.away_goals_scored,t.away_goals_conceded,t.total_goals_scored,t.total_goals_conceded " 
			+ " FROM team_yearly_results t WHERE t.team = :isoCode ORDER BY t.year")
	List<CountryYearlyResultsDTO> getYearlyResults(@Param("isoCode") int isoCode);
	
	
	@Query("SELECT Display_Name AS x, total_wins AS y FROM order_by_points ORDER BY total_wins DESC LIMIT 10 ")
	List<BarChart> getTopCountriesByWins();
	
	@Query("SELECT Display_Name AS x, total_points AS y FROM order_by_points "
			+ "ORDER BY total_points DESC LIMIT 10 ")
	List<BarChart> getTopCountriesByPoints();
	
	@Query("SELECT Display_Name AS x, total_wins_per_year AS y "
			+ "FROM order_by_points_per_year ORDER BY total_wins_per_year DESC LIMIT 10")
	List<BarChart> getTopCountriesByWinsPerYear();
	
	@Query("SELECT Display_Name AS x, total_points_per_year AS y FROM order_by_points_per_year ORDER BY total_points_per_year DESC LIMIT 10")
	List<BarChart> getTopCountriesByPointsPerYear();
	
	@Query("SELECT Display_Name AS name, total_wins as x, Population AS y FROM scatterplot_country_stats ORDER BY total_wins DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByWinsAndPopulationForScatterplot();
	
	@Query("SELECT Display_Name AS name, total_wins as x, area AS y FROM scatterplot_country_stats ORDER BY total_wins DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByWinsAndAreaForScatterplot();
	
	@Query("SELECT  Display_Name AS name, total_points AS x, Population AS y FROM scatterplot_country_stats ORDER BY total_points DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByPointsAndPopulationForScatterplot();
	
	@Query("SELECT  Display_Name AS name, total_points AS x, area AS y FROM scatterplot_country_stats ORDER BY total_points DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByPointsAndAreaForScatterplot();
	
	@Query("SELECT Display_Name AS name, total_wins_per_year AS x, year_active as y FROM order_by_points_per_year ORDER BY total_wins_per_year DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByWinsPerYearAndYearsActive();
	
	
	
	
	@Query("""
	        SELECT * FROM match_information 
	        WHERE 
	            (home_code = :isoCode OR away_code = :isoCode)
	            AND ( :opponentDevStatus IS NULL OR :opponentDevStatus = ''
			         OR (home_code = :isoCode AND away_development_status = :opponentDevStatus)
	                 OR (away_code = :isoCode AND home_development_status = :opponentDevStatus))
	            AND (:tournament IS NULL OR :tournament = '' OR tournament = :tournament)
	            AND (:onlyHomeMatchesOrAwayMatches IS NULL OR  :onlyHomeMatchesOrAwayMatches = '' OR ( :onlyHomeMatchesOrAwayMatches = 'Home Matches' AND home_code = :isoCode AND neutral IS FALSE)
	            OR (:onlyHomeMatchesOrAwayMatches = 'Away Matches' AND away_code = :isoCode AND neutral IS FALSE) 
	            OR (:onlyHomeMatchesOrAwayMatches = 'Neutral' AND ((home_code = :isoCode OR away_code = :isoCode) AND neutral IS TRUE)))
	            AND (( :startDate IS NULL OR match_date >= :startDate) AND (:endDate IS NULL OR match_date <= :endDate))
	            AND (:location IS NULL OR :location = '' OR country_name = :location)
	            AND (:result IS NULL OR :result = '' OR (:result = 'Wins' AND ((home_score > away_score AND home_code = :isoCode) OR (away_score > home_score AND away_code = :isoCode)))
	            OR (:result = 'Wins By Exactly 1 Goal' AND (((home_score = away_score + 1) AND home_code = :isoCode) OR ((away_score = home_score + 1) AND away_code = :isoCode))) 
	            OR (:result = 'Wins By Exactly 2 Goals' AND (((home_score = away_score+2) AND home_code = :isoCode) OR ((away_score = home_score+2) AND away_code = :isoCode)))
	            OR (:result = 'Wins By 3+ Goals' AND (((home_score >= away_score+3) AND home_code = :isoCode) OR ((away_score >= home_score+3) AND away_code = :isoCode)))
	            OR (:result = 'Draws' AND (home_score = away_score))
	            OR (:result = 'Losses' AND ((home_score > away_score AND away_code = :isoCode) OR (away_score > home_score AND home_code = :isoCode))) 
	            OR (:result = 'Losses By Exactly 1 Goal' AND (((home_score = away_score+1) AND away_code = :isoCode) OR ((away_score = home_score+1) AND home_code = :isoCode)))
	            OR (:result = 'Losses By Exactly 2 Goals' AND (((home_score = away_score+2) AND away_code = :isoCode) OR ((away_score = home_score+2) AND home_code = :isoCode)))
	            OR (:result = 'Losses By 3+ Goals' AND (((home_score >= away_score+3) AND away_code = :isoCode) OR ((away_score >= home_score+3) AND home_code = :isoCode)))
	            OR (:result = 'Went To Shootouts' AND ( went_to_shootout IS TRUE))
	            OR (:result = 'Won On Shootouts' AND (went_to_shootout IS TRUE AND shootout_winner = :isoCode))
	            OR (:result = 'Lost On Shootouts' AND (went_to_shootout IS TRUE AND shootout_winner != :isoCode)))
	            AND (:year IS NULL OR :year = '' OR :year = YEAR(match_date))
	            AND ( :regionOpp IS NULL OR :regionOpp = '' OR (home_region = :regionOpp AND away_code = :isoCode) OR (away_region = :regionOpp AND home_code = :isoCode))
	            AND (:statusCountry IS NULL OR :statusCountry = '' OR match_country_status = :statusCountry)
	            AND (:status IS NULL OR :status = '' OR (home_code = :isoCode AND away_status = :status) OR (away_code = :isoCode AND home_status = :status))
	            AND (:countryDevStatus IS NULL OR :countryDevStatus = '' OR (match_country_development_status = :countryDevStatus))
	            AND (:region IS NULL OR :region = '' OR (match_country_region = :region))
	    """)
	    List<MatchInformationDTO> findMatchesWithFilters(@Param("isoCode") int isoCode,@Param("opponentDevStatus") String opponentDevStatus,
	        @Param("tournament") String tournament,@Param("location") String location,@Param("onlyHomeMatchesOrAwayMatches") String onlyHomeMatchesOrAwayMatches,
	        @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("result") String result,@Param("year") Integer year,
	        @Param("regionOpp") String regionOpp, @Param("statusCountry") String statusCountry, @Param("status") String status,  @Param("countryDevStatus") String countryDevStatus, @Param("region") String region);
	
	
	@Query("SELECT DISTINCT s.tournament FROM match_information s WHERE s.home_code = :isoCode OR s.away_code = :isoCode")
	List<String> findTournamentsThatParticipated(@Param("isoCode") int isoCode);
	
	@Query("SELECT DISTINCT s.country_name FROM match_information s WHERE s.home_code = :isoCode OR s.away_code = :isoCode")
	List<String> findCountriesWhereItTookPlace(@Param("isoCode") int isoCode);
	
	@Query("SELECT DISTINCT YEAR(match_date) FROM match_information s WHERE s.home_code = :isoCode OR s.away_code = :isoCode")
	List<Integer> getYearsPlayed(@Param("isoCode") int isoCode);
	
	
	@Query("SELECT t.year,t.home_wins,t.home_draws, t.home_losses,"
			+ "t.away_wins, t.away_draws, t.away_losses,"
			+ " t.total_wins, t.total_draws, t.total_losses, "
			+ "t.home_matches_played, t.away_matches_played, t.total_matches_played, t.penalty_matches,t.penalty_wins,t.penalty_losses,"
			+ "t.home_goals_scored,t.home_goals_conceded,t.away_goals_scored,t.away_goals_conceded,t.total_goals_scored,t.total_goals_conceded " 
			+ " FROM team_yearly_results t WHERE t.team = :isoCode and t.year = :year")
	CountryYearlyResultsDTO getSpecificCountryYearResults(@Param("isoCode") int isoCode,@Param("year") int year);
	
	
	// FOR COUNTRYYEARLYCONTROLLER
	
	
	@Query("SELECT Distinct year FROM team_yearly_results")
	List<Integer> getAvailableYears();
	
	@Query("SELECT t.year,SUM(t.home_wins) AS home_wins,SUM(t.home_draws) AS home_draws, SUM(t.home_losses) AS home_losses,"
			+ "SUM(t.away_wins) AS away_wins, SUM(t.away_draws) AS away_draws, SUM(t.away_losses) AS away_losses,"
			+ " SUM(t.total_wins) AS total_wins, SUM(t.total_draws) AS total_draws, SUM(t.total_losses) AS total_losses, "
			+ "SUM(t.home_matches_played) AS home_matches_played, SUM(t.away_matches_played) AS away_matches_played, SUM(t.total_matches_played) AS total_matches_played, SUM(t.penalty_matches) AS penalty_matches,SUM(t.penalty_wins) AS penalty_wins,SUM(t.penalty_losses) AS penalty_losses,"
			+ " SUM(t.home_goals_scored) AS home_goals_scored, SUM(t.home_goals_conceded) AS home_goals_conceded, SUM(t.away_goals_scored) AS away_goals_scored, SUM(t.away_goals_conceded) AS away_goals_conceded, SUM(t.total_goals_scored) AS total_goals_scored, SUM(t.total_goals_conceded) AS total_goals_conceded " 
			+ " FROM team_yearly_results t WHERE  t.year = :year  ")
	CountryYearlyResultsDTO getSpecificYearResults(@Param("year") int year);
	
	
	@Query("""
	        SELECT * FROM match_information 
	        WHERE 
	            ( :devStatusHome IS NULL OR :devStatusHome = '' OR (home_development_status = :devStatusHome))
	            AND (:statusHome IS NULL OR :statusHome = '' OR (home_status = :statusHome))
	            AND (:regionHome IS NULL OR :regionHome = '' OR (home_region = :regionHome))
	            AND  ( :devStatus IS NULL OR :devStatus = '' OR (match_country_development_status = :devStatus))
	            AND (:status IS NULL OR :status = '' OR (match_country_status = :status))
	            AND (:region IS NULL OR :region = '' OR (match_country_region = :region))
	            AND ( :devStatusAway IS NULL OR :devStatusAway = '' OR (away_development_status = :devStatusAway))
	            AND (:statusAway IS NULL OR :statusAway = '' OR (away_status = :statusAway))
	            AND (:regionAway IS NULL OR :regionAway = '' OR (away_region = :regionAway))
	            AND (:tournament IS NULL OR :tournament = '' OR tournament = :tournament)
	            AND (:location IS NULL OR :location = '' OR country_name = :location)
			    AND ( :year = YEAR(match_date))
	    """)
	List<MatchInformationDTO> findMatchesWithFiltersInSpecificYear(@Param("year") Integer year,@Param("devStatusHome") String devStatusHome,@Param("statusHome") String statusHome,@Param("devStatus") String devStatus,
	    	@Param("regionHome") String regionHome	,@Param("status") String status, @Param("region") String region,
	        @Param("tournament") String tournament,@Param("location") String location,@Param("devStatusAway") String devStatusAway,@Param("statusAway") String statusAway,@Param("regionAway") String regionAway);
	
	@Query("SELECT DISTINCT s.tournament FROM match_information s WHERE :year = YEAR(match_date)")
	List<String> findTournamentsThatTookPlaceInASpecificYear(@Param("year") int year);
	
	@Query("SELECT DISTINCT s.country_name FROM match_information s WHERE YEAR(match_date) = :year")
	List<String> findCountriesWhereItTookPlaceInASpecificYear(@Param("year") int year);

	@Query("SELECT DISTINCT home_status FROM match_information WHERE home_status NOT LIKE 'N/A' AND :year = YEAR(match_date) "
			+ "UNION SELECT DISTINCT away_status FROM match_information WHERE away_status NOT LIKE 'N/A' AND :year = YEAR(match_date)")
	List<String> findStatusOfCountriesParticipatingInASpecificYear(@Param("year") int year);
	
	@Query("SELECT DISTINCT match_country_status FROM match_information WHERE match_country_status NOT LIKE 'N/A' AND :year = YEAR(match_date) ")
	List<String> findStatusOfCountriesItTookPlaceInASpecificYear(@Param("year") int year);
	
	@Query("SELECT DISTINCT home_region FROM match_information WHERE home_region NOT LIKE 'N/A' AND :year = YEAR(match_date) "
			+ "UNION SELECT DISTINCT away_region FROM match_information WHERE away_region NOT LIKE 'N/A' AND :year = YEAR(match_date) "
			+ "UNION SELECT DISTINCT match_country_region FROM match_information WHERE match_country_region NOT LIKE 'N/A' AND :year = YEAR(match_date)")
	List<String> findRegionsInASpecificYear(@Param("year") int year);
	
	
	@Query("SELECT DISTINCT home_status FROM match_information WHERE home_status NOT LIKE 'N/A' AND away_code = :iso_code "
			+ "UNION SELECT DISTINCT away_status FROM match_information WHERE away_status NOT LIKE 'N/A' AND home_code = :iso_code")
	List<String> findStatusOfCountriesParticipating(@Param("iso_code") int iso_code);
	
	@Query("SELECT DISTINCT match_country_status FROM match_information WHERE match_country_status NOT LIKE 'N/A' AND (home_code = :iso_code OR away_code = :iso_code) ")
	List<String> findStatusOfCountriesItTookPlace(@Param("iso_code") int iso_code);
	
	@Query("SELECT DISTINCT home_region FROM match_information WHERE home_region NOT LIKE 'N/A' AND away_code = :iso_code "
			+ "UNION SELECT DISTINCT away_region FROM match_information WHERE away_region NOT LIKE 'N/A' AND home_code = :iso_code "
			+ "UNION SELECT DISTINCT match_country_region FROM match_information WHERE match_country_region NOT LIKE 'N/A' AND (away_code = :iso_code OR home_code = :iso_code)")
	List<String> findRegions(@Param("iso_code") int iso_code);
	
	
	@Query("SELECT Display_Name AS name, SUM(total_wins) as y, SUM(total_goals_scored) AS x FROM team_yearly_results GROUP BY Display_Name ORDER BY SUM(total_wins) DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByWinsAndGoalsScored();
	
	@Query("SELECT Display_Name AS name, SUM(total_wins) as y, SUM(total_goals_conceded) AS x FROM team_yearly_results GROUP BY Display_Name ORDER BY SUM(total_wins) DESC LIMIT 10")
	List<ScatterPlot> getTopCountriesByWinsAndGoalsConceded();
	
	
	@Query("SELECT Display_Name AS x, total_wins AS y FROM order_by_points ORDER BY total_wins DESC  ")
	List<BarChart> getCountriesByWins();
	
	@Query("SELECT Display_Name AS x, total_points AS y FROM order_by_points "
			+ "ORDER BY total_points DESC  ")
	List<BarChart> getCountriesByPoints();
	
	@Query("SELECT Display_Name AS x, total_wins_per_year AS y "
			+ "FROM order_by_points_per_year ORDER BY total_wins_per_year DESC ")
	List<BarChart> getCountriesByWinsPerYear();
	
	@Query("SELECT Display_Name AS x, total_points_per_year AS y FROM order_by_points_per_year ORDER BY total_points_per_year DESC")
	List<BarChart> getCountriesByPointsPerYear();
	
}
