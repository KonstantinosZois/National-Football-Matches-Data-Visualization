package mye030.advanceddb.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mye030.advanceddb.dto.CountryHomeAwaySummaryDTO;
import mye030.advanceddb.dto.CountryYearlyResultsDTO;
import mye030.advanceddb.dto.MatchInformationDTO;
import mye030.advanceddb.models.BarChart;
import mye030.advanceddb.models.Countries;
import mye030.advanceddb.models.ScatterPlot;
import mye030.advanceddb.repositories.CountriesRepository;
import mye030.advanceddb.services.CountriesService;

@Controller
public class CountriesController {

	@Autowired
	private CountriesRepository countriesRepository;
	
	@Autowired
	private CountriesService countriesService;
	
	@RequestMapping("/countries/show_options")
	public String showListOfCountries(Model model) {
		
	        List<Countries> countries = countriesService.findAllCountries();
	        model.addAttribute("countries", countries);
	        return "/countries/show_options";
	}
	
	 @RequestMapping("/countries/countries_info")
	 public String searchCountry(@RequestParam("iso_Code") int iso_Code, Model model) {
		 Countries requestedCountry = countriesService.findCountryById(iso_Code);
		 
		 Optional<CountryHomeAwaySummaryDTO> summary = countriesService.getSummaryForCountry(iso_Code);
		 if(summary.isPresent()) {
			 List<CountryYearlyResultsDTO> yearlyStats = countriesService.getYearlyResultsForSpecificCountry(iso_Code);
			 model.addAttribute("years", yearlyStats);
			 model.addAttribute("summary", summary.get());
		 }else {
			 return "redirect:/countries/show_options";
		 }
		 model.addAttribute("country", requestedCountry);
 
		 return "countries/countries_info";
	 }
	 
	 @RequestMapping("/countries/{isoCode}/yearly_stats_chart")
	 public String yearlyStats(@PathVariable int isoCode, Model model) {
		 
		 List<CountryYearlyResultsDTO> yearlyStats = countriesService.getYearlyResultsForSpecificCountry(isoCode);

		 model.addAttribute("yearlyStats",yearlyStats);
		 return "countries/yearly_stats_chart";
	 }
	 
	 @RequestMapping("/countries/barchart")
	 public String viewRequestedBarChart(Model model,@RequestParam("requestedChart") String requestedChart){
		 List<BarChart> barChartInfo = countriesService.getRequestedBarChart(requestedChart);
		 model.addAttribute("barChartInfo",barChartInfo);
		 
		 String title = countriesService.getRequestedTitle(requestedChart);
		 model.addAttribute("title",title);
		 
		 String description = countriesService.getRequestedDescription(requestedChart);
		 model.addAttribute("description",description);
		 
		 String ylabel = countriesService.getYlabel(requestedChart);
		 model.addAttribute("yAxisLabel", ylabel);
		 return "/countries/barchart";
	 }
	 
	 @RequestMapping("/countries/scatter_plot")
	 public String viewRequestedScatter(@RequestParam("scatterPlotSelect") String scatterPlotSelect,Model model) {
		 List<ScatterPlot> scatterPlotInfo = countriesService.getRequestedScatterPlot(scatterPlotSelect);
		 model.addAttribute("scatterPlotInfo",scatterPlotInfo);
		 
		 String title = countriesService.getRequestedScatterPlotTitle(scatterPlotSelect);
		 model.addAttribute("title",title);
		 
		 String description = countriesService.getRequestedScatterPlotDescription(scatterPlotSelect);
		 model.addAttribute("description",description);
		 
		 String[] xylabel = countriesService.getXYScatterPlotlabel(scatterPlotSelect);
		 model.addAttribute("xyLabel", xylabel);
		 return "/countries/scatter_plot";
	 }
	 
	 @RequestMapping("/countries/matches")
	 public String getFilteredMatches(@RequestParam("iso_Code") int iso_Code,@RequestParam(required = false) String opponentDevStatus,
	     @RequestParam(required = false) String tournament, @RequestParam(required = false) String location,@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String onlyHomeMatchesOrAwayMatches,@RequestParam(required = false) LocalDate startDate,
	     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,@RequestParam(required = false) String result, @RequestParam(required = false) Integer year,
	     @RequestParam(required = false) String regionOpp,@RequestParam(required = false) String statusOpp,
	     @RequestParam(required = false) String statusCountry, @RequestParam(required = false) String countryDevStatus,@RequestParam(required = false) String region,Model model) {

	     List<MatchInformationDTO> matches = countriesRepository.findMatchesWithFilters(
	         iso_Code, opponentDevStatus, tournament, location,onlyHomeMatchesOrAwayMatches, startDate,endDate,
	         result, year,regionOpp,statusCountry,statusOpp,countryDevStatus,region);
	     model.addAttribute("matches", matches);
	     
	     List<String> tournamentsParticipated = countriesRepository.findTournamentsThatParticipated(iso_Code);
	     model.addAttribute("tournaments",tournamentsParticipated);
	     
	     List<String> locationsPlayed = countriesRepository.findCountriesWhereItTookPlace(iso_Code);
	     model.addAttribute("locationsPlayed",locationsPlayed);
	     
	     
	     List<Integer> yearsPlayed = countriesRepository.getYearsPlayed(iso_Code);
	     model.addAttribute("yearsPlayed",yearsPlayed);
	     
	     List<String> regions = countriesRepository.findRegions(iso_Code);
	     model.addAttribute("regions",regions);
	     
	     List<String> statusOfCountryList = countriesRepository.findStatusOfCountriesItTookPlace(iso_Code);
	     model.addAttribute("statusOfCountry",statusOfCountryList);
	     
	     List<String> statusOfOpponents = countriesRepository.findStatusOfCountriesParticipating(iso_Code);
	     model.addAttribute("statusOfOpponents",statusOfOpponents);
	     
	     model.addAttribute("selectedCountryId", iso_Code);
	     return "countries/country_matches";
	 }
	 
	 @RequestMapping("countries/specific_year_stats")
	 public String showSpecificYearPage(@RequestParam int iso_Code, @RequestParam int specificyear, Model model) {
		 if(specificyear == 0) {
			 String errorNoYear = "Please specify a year";
			 model.addAttribute("errorNoYear",errorNoYear);
			 return "forward:/countries/countries_info";
		 }
		 CountryYearlyResultsDTO specificYear = countriesRepository.getSpecificCountryYearResults(iso_Code,specificyear);
		 model.addAttribute("specificYear",specificYear);

		 return "countries/specific_year_stats";
	 }
	 
	 @RequestMapping("/countries/leaderboard")
	 public String viewRequestedLeaderboard(Model model,@RequestParam("method") String method){
		 List<BarChart> leaderboard = countriesService.getRequestedLeaderboard(method);
		 model.addAttribute("leaderboard",leaderboard);
		 
		 
		 return "/countries/leaderboard";
	 }
}
