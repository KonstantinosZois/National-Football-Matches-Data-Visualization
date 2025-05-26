package mye030.advanceddb.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mye030.advanceddb.dto.CountryYearlyResultsDTO;
import mye030.advanceddb.dto.MatchInformationDTO;
import mye030.advanceddb.repositories.CountriesRepository;

@Controller
public class CountryYearlyController {
	
	@Autowired
	private CountriesRepository countriesRepository;
	
	@RequestMapping("/yearlyStats/choose_year")
	public String showListOfCountries(Model model) {
		List<Integer> availableYears = countriesRepository.getAvailableYears();
		model.addAttribute("availableYears", availableYears);
		return "/yearlyStats/choose_year";
	}
	
	@RequestMapping("/yearlyStats/specific_year_stats")
	public String showDetailsOfYear(@RequestParam int chosenYear, Model model) {
		 if(chosenYear == 0) {
			 String errorNoYear = "Please specify a year";
			 model.addAttribute("errorNoYear",errorNoYear);
			 return "forward:/yearlyStats/specific_year_stats";
		 }
		 CountryYearlyResultsDTO specificYear = countriesRepository.getSpecificYearResults(chosenYear);
		 model.addAttribute("specificYear",specificYear);

		 return "/yearlyStats/specific_year_stats";
	}
	
	 @RequestMapping("/yearlyStats/all_matches")
	 public String getFilteredMatchesInASpecificYear(@RequestParam(required = false) String devStatus,@RequestParam(required= false) String status,
	     @RequestParam(required = false) String tournament, @RequestParam(required = false) String location,@RequestParam(required = false) Integer year,
	     @RequestParam(required = false)  String devStatusHome ,@RequestParam(required = false) String statusHome,@RequestParam(required = false) String regionHome,
	     @RequestParam(required = false) String region, @RequestParam(required = false) String devStatusAway,@RequestParam(required = false) String statusAway,@RequestParam(required = false) String regionAway,Model model) {			

	     List<MatchInformationDTO> matches = countriesRepository.findMatchesWithFiltersInSpecificYear(
	     year,devStatusHome,statusHome,devStatus,regionHome,status,region, tournament, location,devStatusAway,statusAway,regionAway);
	     model.addAttribute("matches", matches);
	     
	     model.addAttribute("year",year);
	     
	     List<String> tournamentsParticipated = countriesRepository.findTournamentsThatTookPlaceInASpecificYear(year);
	     model.addAttribute("tournaments",tournamentsParticipated);
	     
	     List<String> locationsPlayed = countriesRepository.findCountriesWhereItTookPlaceInASpecificYear(year);
	     model.addAttribute("locationsPlayed",locationsPlayed);
	     
	     List<String> statusOfParticipant = countriesRepository.findStatusOfCountriesParticipatingInASpecificYear(year);
	     model.addAttribute("statusParticipants",statusOfParticipant);
	     
	     List<String> statusOfCountry = countriesRepository.findStatusOfCountriesItTookPlaceInASpecificYear(year);
	     model.addAttribute("status",statusOfCountry);
	     
	     List<String> regionsCountries = countriesRepository.findRegionsInASpecificYear(year);
	     model.addAttribute("regions",regionsCountries);
	
	     return "/yearlyStats/all_matches";
	 }
}
