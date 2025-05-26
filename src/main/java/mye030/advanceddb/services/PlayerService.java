package mye030.advanceddb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mye030.advanceddb.models.BarChart;
import mye030.advanceddb.repositories.CountriesRepository;
import mye030.advanceddb.repositories.PlayerInfoRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerInfoRepository playersRepository;

	public String getRequestedTitle(String requestedChart) {
		if(requestedChart.equals("Top Players By Goals")) {
			return "Top Players By Goals";
		}
		else if(requestedChart.equals("Top Players By Penalty Goals")) {
			return "Top Players By Penalty Goals";
		}
		else {
				return "";
		}
	}

	public List<BarChart> getRequestedBarChart(String requestedChart) {
		if(requestedChart.equals("Top Players By Goals")) {
			return playersRepository.getTopPlayersByGoals();
		}
		else if(requestedChart.equals("Top Players By Penalty Goals")) {
			return playersRepository.getTopPlayersByPenaltyGoals();
		}
		else {
				return null;
		}
	}



	public String getYlabel(String requestedChart) {
		if(requestedChart.equals("Top Players By Goals")) {
			return " Total Goals";
		}
		else if(requestedChart.equals("Top Players By Penalty Goals")) {
			return "Total Penalty Goals";
		}
		else {
				return "";
		}
	}
}
