package mye030.advanceddb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import mye030.advanceddb.dto.MatchesPlayerScoredDTO;
import mye030.advanceddb.dto.PlayerYearlyResultsDTO;
import mye030.advanceddb.dto.TeamYearRangeResultsDTO;
import mye030.advanceddb.models.BarChart;
import mye030.advanceddb.models.PlayersInfo;
import mye030.advanceddb.repositories.PlayerInfoRepository;
import mye030.advanceddb.services.PlayerService;

@Controller
public class PlayersController {

	@Autowired
    private PlayerInfoRepository playersRepository;
	
	@Autowired
	private PlayerService playersService;
	
	@RequestMapping("/players/show_list_of_players")
	 public String showListOfPlayers(Model model) {
		
	        List<PlayersInfo> players = playersRepository.findAll();
	        model.addAttribute("players", players);
	        return "/players/show_list_of_players";
	 }
	
	@RequestMapping("/players/player_info")
    public String searchPlayer(@RequestParam("scorer") String scorer, Model model) {

		List<PlayersInfo> listOfPlayers = playersRepository.findByScorer(scorer); 
        if (listOfPlayers.size() == 1) {
        	PlayersInfo player = listOfPlayers.get(0);
        	TeamYearRangeResultsDTO teamStats = playersRepository.findTeamStatsInYearRange(player.getCountryName(), player.getFirstYear(), player.getLastYear());
        	model.addAttribute("teamStats", teamStats);
            model.addAttribute("selectedPlayer", player); 
        } else {
        	model.addAttribute("players", listOfPlayers);
            return "/players/extendedSearch"; 
        }

        return "/players/player_info";
    }
	 
	 @RequestMapping("/players/search")
	 public String searchPlayerWithMatchingName(@RequestParam("scorer") String scorer,@RequestParam("countryName") String countryName,Model model) {
		 Optional<PlayersInfo> requestedPlayer = playersRepository.findByScorerAndCountryName(scorer, countryName);
		 if(requestedPlayer.isPresent()) {
			 PlayersInfo player = requestedPlayer.get();
			 TeamYearRangeResultsDTO teamStats = playersRepository.findTeamStatsInYearRange(player.getCountryName(), player.getFirstYear(), player.getLastYear());
			 model.addAttribute("selectedPlayer", player);
			 model.addAttribute("teamStats", teamStats);
			
		 }else {
			 model.addAttribute("error","no Player found");
		 }
		 return "/players/player_info";
	 }
	 
	 @RequestMapping("/players/matches")
	 public String viewMatchesPlayerScored(@RequestParam("scorer") String scorer, @RequestParam("countryName") String countryName,
			 @RequestParam(required = false) Boolean ownGoal, @RequestParam(required = false) Boolean penalty, @RequestParam(required = false) String tournament, Model model) {
		 
		 List<MatchesPlayerScoredDTO> matches = playersRepository.findMatches(scorer, countryName, ownGoal, penalty, tournament);
		 List<String> tournamentsParticipated = playersRepository.findTournaments(scorer, countryName);
		 model.addAttribute("matches", matches);
		 model.addAttribute("tournaments", tournamentsParticipated);
		 model.addAttribute("scorer", scorer);
		 model.addAttribute("countryName", countryName);
		 
		 return "players/matches";
	 }
	 
	 @RequestMapping("/players/yearly_stats")
	 public String viewYearlyStatsForPlayer(@RequestParam("scorer") String scorer, @RequestParam("countryName") String countryName, Model model) {
		 
		 List<PlayerYearlyResultsDTO> yearlyStats = playersRepository.getYearlyResults(scorer, countryName);
		 model.addAttribute("yearlyStats", yearlyStats);
		 
		 return "players/yearly_stats";
	 }
	 
	 @RequestMapping("/players/barchart")
	 public String viewRequestedBarChart(Model model,@RequestParam("requestedChart") String requestedChart){
		 List<BarChart> barChartInfo = playersService.getRequestedBarChart(requestedChart);
		 model.addAttribute("barChartInfo",barChartInfo);
		 
		 String title = playersService.getRequestedTitle(requestedChart);
		 model.addAttribute("title",title);
		 
		 String ylabel = playersService.getYlabel(requestedChart);
		 model.addAttribute("yAxisLabel", ylabel);
		 return "/players/barchart";
	 }
	
}
