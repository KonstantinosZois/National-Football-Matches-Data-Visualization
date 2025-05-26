CREATE VIEW order_by_points AS
SELECT Display_Name,team,SUM(total_wins) AS total_wins,SUM(total_draws) AS total_draws,SUM(total_losses) AS total_losses,
	   SUM(total_wins * 3 + total_draws) AS total_points
FROM team_yearly_results
GROUP BY Display_Name,team;


CREATE VIEW order_by_points_per_year AS
WITH country_year_range_named AS (
    SELECT 
        c.Display_Name, 
        team AS ISO_Code, 
        MIN(YEAR(match_date)) AS first_year, 
        MAX(YEAR(match_date)) AS last_year
    FROM (
        SELECT home AS team, match_date FROM results
        UNION ALL
        SELECT away AS team, match_date FROM results
    ) AS all_matches
    JOIN countries c ON c.ISO_Code = all_matches.team
    GROUP BY c.Display_Name, team
)
SELECT tyr.Display_Name,team,CAST(SUM(total_wins)/(last_year-first_year) AS DECIMAL(10,3)) AS total_wins_per_year,CAST(SUM(total_draws)/(last_year-first_year) AS DECIMAL(10,3)) AS total_draws_per_year,CAST(SUM(total_losses)/(last_year-first_year) AS DECIMAL(10,3)) AS total_losses_per_year,
	   CAST(SUM(total_wins * 3 + total_draws)/(last_year-first_year) AS DECIMAL(10,3)) AS total_points_per_year, last_year-first_year AS year_active
FROM team_yearly_results tyr JOIN country_year_range_named cyrn ON tyr.team = cyrn.ISO_CODE
GROUP BY Display_Name,team,last_year-first_year;


CREATE VIEW scatterplot_country_stats AS
SELECT o.Display_Name, o.team AS code, o.total_wins, o.total_points, c.Population, c.Area_SqKm AS area
FROM order_by_points AS o JOIN countries AS c
ON o.team = c.ISO_Code;