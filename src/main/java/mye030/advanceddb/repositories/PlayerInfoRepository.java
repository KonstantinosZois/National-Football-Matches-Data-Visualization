package mye030.advanceddb.repositories;

import mye030.advanceddb.dto.MatchesPlayerScoredDTO;
import mye030.advanceddb.dto.PlayerYearlyResultsDTO;
import mye030.advanceddb.dto.TeamYearRangeResultsDTO;
import mye030.advanceddb.models.BarChart;
import mye030.advanceddb.models.PlayersInfo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PlayerInfoRepository extends CrudRepository<PlayersInfo, String> {
	
    List<PlayersInfo> findByScorer(String scorer);
    List<PlayersInfo> findAll(); // for populating the dropdown
    Optional<PlayersInfo> findByScorerAndCountryName(String scorer,String countryName);
    
    @Query("SELECT " +
            "SUM(t.total_wins) as total_wins, SUM(t.total_losses) as total_losses, SUM(t.total_draws) as total_draws, " +
            "SUM(t.total_matches_played) as total_matches_played, SUM(t.total_goals_scored) as total_goals_scored, SUM(t.total_goals_conceded) as total_goals_conceded " +
            "FROM team_yearly_results t " +
            "WHERE t.Display_Name = :countryName AND t.year BETWEEN :startYear AND :endYear")
    TeamYearRangeResultsDTO findTeamStatsInYearRange(@Param("countryName") String countryName, @Param("startYear")int startYear, @Param("endYear") int endYear);
    
    @Query("SELECT m.match_date, m.home_team_name, m.away_team_name, m.home_score, m.away_score, m.tournament, "
    		+ "g.minute_of_goal, g.own_goal, g.penalty "
    		+ "FROM match_information m JOIN goalscorers g ON g.match_date = m.match_date "
    		+ "AND (g.team = m.home_code OR g.team = m.away_code) "
    		+ "WHERE g.scorer = :scorer "
    		+ "AND ((g.team = m.home_code AND m.home_team_name = :countryName) "
    		+ "OR (g.team = m.away_code AND m.away_team_name = :countryName)) "
    		+ "AND (:ownGoal IS NULL OR :ownGoal = '' OR (g.own_goal = :ownGoal)) "
    		+ "AND (:penalty IS NULL OR :penalty = '' OR (g.penalty = :penalty)) "
    		+ "AND (:tournament IS NULL OR :tournament = '' OR m.tournament = :tournament)")
    List<MatchesPlayerScoredDTO> findMatches(@Param("scorer") String scorer, @Param("countryName") String countryName, @Param("ownGoal")
    	Boolean ownGoal, @Param("penalty") Boolean penalty, @Param("tournament") String tournament); 
    
    @Query("SELECT DISTINCT m.tournament "
    		+ "FROM match_information m JOIN goalscorers g ON g.match_date = m.match_date "
    		+ "AND (g.team = m.home_code OR g.team = m.away_code) "
    		+ "WHERE g.scorer = :scorer "
    		+ "AND ((g.team = m.home_code AND m.home_team_name = :countryName) "
    		+ "OR (g.team = m.away_code AND m.away_team_name = :countryName)) "
    		)
    List<String> findTournaments(@Param("scorer") String scorer, @Param("countryName") String countryName);
    
    
    @Query("SELECT YEAR(g.match_date) AS year, " +
    	       "       COUNT(*) AS total_goals_by_player, " +
    	       "       MAX(player_goals_per_match.goals_in_match) AS max_goals_in_match, " +
    	       "       t.total_goals_scored AS total_goals_by_country " +
    	       "FROM goalscorers g " +
    	       "JOIN (SELECT match_date, scorer, team, COUNT(*) AS goals_in_match " +
    	       "      FROM goalscorers " +
    	       "      GROUP BY match_date, scorer, team) AS player_goals_per_match " +
    	       "ON g.match_date = player_goals_per_match.match_date AND g.scorer = player_goals_per_match.scorer AND g.team = player_goals_per_match.team " +
    	       "JOIN team_yearly_results t ON t.team = g.team AND t.year = YEAR(g.match_date) " +
    	       "WHERE g.scorer = :scorer AND t.Display_Name = :countryName " +
    	       "GROUP BY YEAR(g.match_date), t.total_goals_scored " +
    	       "ORDER BY year")
    List<PlayerYearlyResultsDTO> getYearlyResults(@Param("scorer") String scorer, @Param("countryName") String countryName);
    
    @Query("SELECT scorer as x, COUNT(*) as y " +
    		"FROM goalscorers JOIN countries ON team = ISO_CODE " +
    		"GROUP BY scorer " +
    		"ORDER BY y DESC LIMIT 10")
    List<BarChart> getTopPlayersByGoals();
    
    @Query("SELECT scorer as x, COUNT(*) as y " +
    		"FROM goalscorers JOIN countries ON team = ISO_CODE "
    		+ "WHERE penalty = 1 " +
    		"GROUP BY scorer " +
    		"ORDER BY y DESC LIMIT 10")
    List<BarChart> getTopPlayersByPenaltyGoals();
}
