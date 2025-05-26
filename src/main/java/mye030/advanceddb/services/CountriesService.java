package mye030.advanceddb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mye030.advanceddb.dto.CountryHomeAwaySummaryDTO;
import mye030.advanceddb.dto.CountryYearlyResultsDTO;
import mye030.advanceddb.models.BarChart;
import mye030.advanceddb.models.Countries;
import mye030.advanceddb.models.ScatterPlot;
import mye030.advanceddb.repositories.CountriesRepository;

@Service
public class CountriesService {

	@Autowired
	private CountriesRepository countriesRepository;
	
	public List<Countries> findAllCountries(){
		return  countriesRepository.findAll();
	}
	
	public  Countries findCountryById(int iso_code){
		return countriesRepository.findById(iso_code);
	}
	
	 public Optional<CountryHomeAwaySummaryDTO> getSummaryForCountry(int isoCode) {
	        return countriesRepository.findByIsoCode(isoCode);
	 }
	 

	public String getRequestedTitle(String requestedChart) {
		if(requestedChart.equals("Top Countries By Wins")) {
			return "Top Countries By Wins";
		}
		else if(requestedChart.equals("Top Countries By Points")) {
			return "Top Countries By Points";
		}
		else if(requestedChart.equals("Top Countries By Wins Per Years Competed")) {
			return "Top Countries By Wins Per Years Competed";
		}
		else if(requestedChart.equals("Top Countries By Points Per Years Competed")) {
			return "Top Countries By Points Per Years Competed";
		}
		else {
				return "";
		}
	}

	public List<BarChart> getRequestedBarChart(String requestedChart) {
		if(requestedChart.equals("Top Countries By Wins")) {
			return countriesRepository.getTopCountriesByWins();
		}
		else if(requestedChart.equals("Top Countries By Points")) {
			return countriesRepository.getTopCountriesByPoints();
		}
		else if(requestedChart.equals("Top Countries By Wins Per Years Competed")) {
			return countriesRepository.getTopCountriesByWinsPerYear();
		}
		else if(requestedChart.equals("Top Countries By Points Per Years Competed")) {
			return countriesRepository.getTopCountriesByPointsPerYear();
		}
		else {
				return null;
		}
	}

	public String getRequestedDescription(String requestedChart) {
		if(requestedChart.equals("Top Countries By Points") || requestedChart.equals("Top Countries By Points Per Years Competed")) {
			return " The points for each country are calculated by the match results for each specific country. A win counts for 3 points, a draw for 1 point and a loss for zero points.";
		}
		else {
				return "";
		}
	}


	public String getYlabel(String requestedChart) {
		if(requestedChart.equals("Top Countries By Wins")) {
			return " Total Wins";
		}
		else if(requestedChart.equals("Top Countries By Points")) {
			return "Total Points";
		}
		else if(requestedChart.equals("Top Countries By Wins Per Years Competed")) {
			return "Total Wins Per Year";
		}
		else if(requestedChart.equals("Top Countries By Points Per Years Competed")) {
			return "Total Points Per Year";
		}
		else {
				return "";
		}
	}


	public List<ScatterPlot> getRequestedScatterPlot(String scatterPlotSelect) {
		if(scatterPlotSelect.equals("Wins VS Population")) {
			return countriesRepository.getTopCountriesByWinsAndPopulationForScatterplot();
		}
		else if(scatterPlotSelect.equals("Wins VS AreaSqKm")) {
			return countriesRepository.getTopCountriesByWinsAndAreaForScatterplot();
		}
		else if(scatterPlotSelect.equals("Points VS Population")) {
			return countriesRepository.getTopCountriesByPointsAndPopulationForScatterplot();
		}
		else if(scatterPlotSelect.equals("Points VS AreaSqKm")) {
			return countriesRepository.getTopCountriesByPointsAndAreaForScatterplot();
		}
		else if(scatterPlotSelect.equals("Wins Per Years Competed VS Years Active")){
			return countriesRepository.getTopCountriesByWinsPerYearAndYearsActive();
		}
		else if(scatterPlotSelect.equals("Wins vs Goals Scored")) {
			return countriesRepository.getTopCountriesByWinsAndGoalsScored();
		}
		else if(scatterPlotSelect.equals("Wins vs Goals Conceded")) {
			return countriesRepository.getTopCountriesByWinsAndGoalsConceded();
		}
		else {
				return null;
		}
	}
	
	public String getRequestedScatterPlotTitle(String scatterPlotSelect) {
		if(scatterPlotSelect.equals("Wins VS Population")) {
			return "Wins VS Population";
		}
		else if(scatterPlotSelect.equals("Wins VS AreaSqKm")) {
			return "Wins VS AreaSqKm";
		}
		else if(scatterPlotSelect.equals("Points VS Population")) {
			return "Points VS Population";
		}
		else if(scatterPlotSelect.equals("Points VS AreaSqKm")) {
			return "Points VS AreaSqKm";
		}
		else if(scatterPlotSelect.equals("Wins Per Years Competed VS Years Active")) {
			return "Wins/Years Competed VS Years Active";
		}
		else if(scatterPlotSelect.equals("Wins vs Goals Scored")) {
			return "Wins vs Goals Scored";
		}
		else if(scatterPlotSelect.equals("Wins vs Goals Conceded")) {
			return "Wins vs Goals Conceded";
		}
		else {
				return "";
		}
	}
	
	public String[] getXYScatterPlotlabel(String scatterPlotSelect) {
		String[] result = new String[2];
		if(scatterPlotSelect.equals("Wins VS Population")) {
			result[0]= "Wins";
			result[1]= "Population";
			
			return result;
		}
		else if(scatterPlotSelect.equals("Wins VS AreaSqKm")) {
			result[0]= "Wins";
			result[1]= "AreaSqKm";
			return result;
		}
		else if(scatterPlotSelect.equals("Points VS Population")) {
			result[0]= "Points";
			result[1]= "Population";
			return result;
		}
		else if(scatterPlotSelect.equals("Points VS AreaSqKm")) {
			result[0]= "Points";
			result[1]= "AreaSqKm";
			return result;
		}
		else if(scatterPlotSelect.equals("Wins Per Years Competed VS Years Active")) {
			result[0] = "Wins/Years Competed";
			result[1] = "Years Active";
			return result;
		}
		else if(scatterPlotSelect.equals("Wins vs Goals Scored")) {
			result[0] = "Goals Scored";
			result[1] = "Total Wins";
			return result;
		}
		else if(scatterPlotSelect.equals("Wins vs Goals Conceded")) {
			result[0] = "Goals Conceded";
			result[1] = "Total Wins";
			return result;
		}
		else {
				return null;
		}
	}
	
	public String getRequestedScatterPlotDescription(String scatterPlotSelect) {
		if(scatterPlotSelect.equals("Points VS Population") || scatterPlotSelect.equals("Points VS AreaSqKm")) {
			return "The points for each country are calculated by the match results for each specific country."
					+ "       A win counts for 3 points, a draw for 1 point and a loss for zero points.";
		}
		else {
				return " ";
		}
	}

	public List<CountryYearlyResultsDTO> getYearlyResultsForSpecificCountry(int iso_Code) {
		return countriesRepository.getYearlyResults(iso_Code);
	}

	public List<BarChart> getRequestedLeaderboard(String method) {
		if(method.equals("Countries By Wins")) {
			return countriesRepository.getCountriesByWins();
		}
		else if(method.equals("Countries By Points")) {
			return countriesRepository.getCountriesByPoints();
		}
		else if(method.equals("Countries By Wins Per Years Competed")) {
			return countriesRepository.getCountriesByWinsPerYear();
		}
		else if(method.equals("Countries By Points Per Years Competed")) {
			return countriesRepository.getCountriesByPointsPerYear();
		}
		else {
				return null;
		}
	}
}
