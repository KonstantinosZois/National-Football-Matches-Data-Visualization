package mye030.advanceddb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;




@Table(name ="main_info_of_player") 
public class PlayersInfo {

	    @Id
	    @Column("scorer")
	    private String scorer;  
	    
	    @Column("country_name")
	    private String countryName;
	    
	    @Column("total_goals")
	    private int totalGoals;
	    
	    @Column("max_goals_per_game")
	    private int maxGoalsPerGame;
	    
	    @Column("total_own_goals")
	    private int totalOwnGoals;
	    
	    @Column("penalties_scored")
	    private int penaltiesScored;
	    
	    @Column("first_year")
	    private int firstYear;
	    
	    @Column("last_year")
	    private int lastYear;
	    
	    
	    
	    
		public String getScorer() {
			return scorer;
		}
		public String getCountryName() { 
		    	return countryName; 
		}
		public int getTotalGoals() {
			return totalGoals;
		}
		public int getMaxGoalsPerGame() {
			return maxGoalsPerGame;
		}
		public int getTotalOwnGoals() {
			return totalOwnGoals;
		}
		public int getPenaltiesScored() {
			return penaltiesScored;
		}
		public int getFirstYear() {
			return firstYear;
		}
		public int getLastYear() {
			return lastYear;
		}
	
		
	

}
