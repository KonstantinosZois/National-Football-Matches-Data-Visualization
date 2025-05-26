CREATE VIEW country_home_away_summary AS
WITH all_shootouts AS (
    SELECT match_date, home, away, winner
    FROM shootouts
    UNION ALL
    SELECT match_date, home, away, winner
    FROM shootoutsExtra
),
home_results AS (
    SELECT
        m.match_date,
        m.home AS team,
        CASE
            WHEN m.home_score > m.away_score THEN 'win'
            WHEN m.home_score < m.away_score THEN 'loss'
            ELSE
                CASE
                    WHEN s.winner IS NULL THEN 'draw'
                    WHEN s.winner = m.home THEN 'win'
                    ELSE 'loss'
                END
        END AS result
    FROM results m
    LEFT JOIN all_shootouts s
      ON m.match_date = s.match_date AND m.home = s.home AND m.away = s.away
),
away_results AS (
    SELECT
        m.match_date,
        m.away AS team,
        CASE
            WHEN m.away_score > m.home_score THEN 'win'
            WHEN m.away_score < m.home_score THEN 'loss'
            ELSE
                CASE
                    WHEN s.winner IS NULL THEN 'draw'
                    WHEN s.winner = m.away THEN 'win'
                    ELSE 'loss'
                END
        END AS result
    FROM results m
    LEFT JOIN all_shootouts s
      ON m.match_date = s.match_date AND m.home = s.home AND m.away = s.away
),
home_agg AS (
    SELECT
        team,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) AS home_wins,
        SUM(CASE WHEN result = 'loss' THEN 1 ELSE 0 END) AS home_losses,
        SUM(CASE WHEN result = 'draw' THEN 1 ELSE 0 END) AS home_draws
    FROM home_results
    GROUP BY team
),
away_agg AS (
    SELECT
        team,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) AS away_wins,
        SUM(CASE WHEN result = 'loss' THEN 1 ELSE 0 END) AS away_losses,
        SUM(CASE WHEN result = 'draw' THEN 1 ELSE 0 END) AS away_draws
    FROM away_results
    GROUP BY team
),
home_away_summary AS (
    SELECT
  COALESCE(h.team, a.team) AS team,
  COALESCE(h.home_wins, 0) AS home_wins,
  COALESCE(h.home_losses, 0) AS home_losses,
  COALESCE(h.home_draws, 0) AS home_draws,
  COALESCE(a.away_wins, 0) AS away_wins,
  COALESCE(a.away_losses, 0) AS away_losses,
  COALESCE(a.away_draws, 0) AS away_draws
FROM home_agg h
LEFT JOIN away_agg a ON h.team = a.team
UNION
SELECT
  COALESCE(h.team, a.team) AS team,
  COALESCE(h.home_wins, 0) AS home_wins,
  COALESCE(h.home_losses, 0) AS home_losses,
  COALESCE(h.home_draws, 0) AS home_draws,
  COALESCE(a.away_wins, 0) AS away_wins,
  COALESCE(a.away_losses, 0) AS away_losses,
  COALESCE(a.away_draws, 0) AS away_draws
FROM away_agg a
LEFT JOIN home_agg h ON h.team = a.team
),
country_year_range_named AS (
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
SELECT 
    c.Display_Name AS country,
    r.team,
    r.home_wins,
    r.home_losses,
    r.home_draws,
    r.away_wins,
    r.away_losses,
    r.away_draws,
    (r.home_wins + r.away_wins) AS total_wins,
    (r.home_losses + r.away_losses) AS total_losses,
    (r.home_draws + r.away_draws) AS total_draws,
    (r.home_wins + r.home_losses + r.home_draws) AS total_home_matches_played,
    (r.away_wins + r.away_losses + r.away_draws) AS total_away_matches_played,
    (r.home_wins + r.home_losses + r.home_draws + r.away_wins + r.away_losses + r.away_draws) AS total_matches_played,
    cy.first_year,
    cy.last_year
FROM home_away_summary r
JOIN countries c ON c.ISO_Code = r.team
JOIN country_year_range_named cy ON cy.ISO_Code = c.ISO_Code;

-- final(will be used)
CREATE VIEW team_yearly_results AS
WITH all_matches AS (
    SELECT m.match_date, YEAR(m.match_date) AS year, m.home, m.away, m.home_score, m.away_score, s.winner AS shootout_winner
    FROM results m
    LEFT JOIN shootouts s ON s.match_date = m.match_date AND s.home = m.home AND s.away = m.away
    UNION ALL
    SELECT e.match_date, YEAR(e.match_date) AS year, e.home, e.away,
        NULL AS home_score,
        NULL AS away_score,
        e.winner AS shootout_winner
    FROM shootoutsextra e
),
home_results AS (
    SELECT 
        year,
        home AS team, home_score AS goals_scored, away_score AS goals_conceded,
        CASE 
            WHEN home_score > away_score THEN 'win'
            WHEN home_score < away_score THEN 'loss'
            WHEN home_score = away_score AND shootout_winner = home THEN 'win'
            WHEN home_score = away_score AND shootout_winner = away THEN 'loss'
            ELSE 'draw'
        END AS result_type,
        CASE 
            WHEN home_score = away_score AND shootout_winner IS NOT NULL THEN 1
            ELSE 0
        END AS went_to_penalties,
        CASE 
            WHEN home_score = away_score AND shootout_winner = home THEN 1 ELSE 0
        END AS penalty_win,
        CASE 
            WHEN home_score = away_score AND shootout_winner = away THEN 1 ELSE 0
        END AS penalty_loss
    FROM all_matches
    WHERE home IS NOT NULL
),
away_results AS (
    SELECT 
        year,
        away AS team, away_score AS goals_scored, home_score AS goals_conceded,
        CASE 
            WHEN away_score > home_score THEN 'win'
            WHEN away_score < home_score THEN 'loss'
            WHEN home_score = away_score AND shootout_winner = away THEN 'win'
            WHEN home_score = away_score AND shootout_winner = home THEN 'loss'
            ELSE 'draw'
        END AS result_type,
        CASE 
            WHEN home_score = away_score AND shootout_winner IS NOT NULL THEN 1
            ELSE 0
        END AS went_to_penalties,
        CASE 
            WHEN home_score = away_score AND shootout_winner = away THEN 1 ELSE 0
        END AS penalty_win,
        CASE 
            WHEN home_score = away_score AND shootout_winner = home THEN 1 ELSE 0
        END AS penalty_loss
    FROM all_matches
    WHERE away IS NOT NULL
),
home_agg AS (
    SELECT year, team,
        SUM(CASE WHEN result_type = 'win' THEN 1 ELSE 0 END) AS home_wins,
        SUM(CASE WHEN result_type = 'draw' THEN 1 ELSE 0 END) AS home_draws,
        SUM(CASE WHEN result_type = 'loss' THEN 1 ELSE 0 END) AS home_losses,
        SUM(goals_scored) AS home_goals_scored,
        SUM(goals_conceded) AS home_goals_conceded
    FROM home_results
    GROUP BY year, team
),
away_agg AS (
    SELECT year, team,
        SUM(CASE WHEN result_type = 'win' THEN 1 ELSE 0 END) AS away_wins,
        SUM(CASE WHEN result_type = 'draw' THEN 1 ELSE 0 END) AS away_draws,
        SUM(CASE WHEN result_type = 'loss' THEN 1 ELSE 0 END) AS away_losses,
		SUM(goals_scored) AS away_goals_scored,
        SUM(goals_conceded) AS away_goals_conceded
    FROM away_results
    GROUP BY year, team
),
home_penalty_stats AS (
    SELECT year, team,
        SUM(went_to_penalties) AS home_penalty_matches,
        SUM(penalty_win) AS home_penalty_wins,
        SUM(penalty_loss) AS home_penalty_losses
    FROM home_results
    GROUP BY year, team
),
away_penalty_stats AS (
    SELECT year, team,
        SUM(went_to_penalties) AS away_penalty_matches,
        SUM(penalty_win) AS away_penalty_wins,
        SUM(penalty_loss) AS away_penalty_losses
    FROM away_results
    GROUP BY year, team
),
combined AS (
    SELECT
        COALESCE(h.year, a.year) AS year,
        COALESCE(h.team, a.team) AS team,
        COALESCE(h.home_wins, 0) AS home_wins,
        COALESCE(h.home_draws, 0) AS home_draws,
        COALESCE(h.home_losses, 0) AS home_losses,
        COALESCE(h.home_goals_scored, 0) AS home_goals_scored,
        COALESCE(h.home_goals_conceded, 0) AS home_goals_conceded,
        COALESCE(a.away_wins, 0) AS away_wins,
        COALESCE(a.away_draws, 0) AS away_draws,
        COALESCE(a.away_losses, 0) AS away_losses,
        COALESCE(a.away_goals_scored, 0) AS away_goals_scored,
        COALESCE(a.away_goals_conceded, 0) AS away_goals_conceded
    FROM home_agg h
    LEFT JOIN away_agg a ON h.team = a.team AND h.year = a.year
    UNION 
    SELECT
        COALESCE(h.year, a.year) AS year,
        COALESCE(h.team, a.team) AS team,
        COALESCE(h.home_wins, 0) AS home_wins,
        COALESCE(h.home_draws, 0) AS home_draws,
        COALESCE(h.home_losses, 0) AS home_losses,
		COALESCE(h.home_goals_scored, 0) AS home_goals_scored,
        COALESCE(h.home_goals_conceded, 0) AS home_goals_conceded,
        COALESCE(a.away_wins, 0) AS away_wins,
        COALESCE(a.away_draws, 0) AS away_draws,
        COALESCE(a.away_losses, 0) AS away_losses,
		COALESCE(a.away_goals_scored, 0) AS away_goals_scored,
		COALESCE(a.away_goals_conceded, 0) AS away_goals_conceded
    FROM away_agg a
    LEFT JOIN home_agg h ON h.team = a.team AND h.year = a.year
),
combined_with_penalties AS (
    SELECT
        c.*,
        COALESCE(hps.home_penalty_matches, 0) + COALESCE(aps.away_penalty_matches, 0) AS penalty_matches,
        COALESCE(hps.home_penalty_wins, 0) + COALESCE(aps.away_penalty_wins, 0) AS penalty_wins,
        COALESCE(hps.home_penalty_losses, 0) + COALESCE(aps.away_penalty_losses, 0) AS penalty_losses
    FROM combined c
    LEFT JOIN home_penalty_stats hps ON c.team = hps.team AND c.year = hps.year
    LEFT JOIN away_penalty_stats aps ON c.team = aps.team AND c.year = aps.year
)
SELECT
    countries.Display_Name,
    year,
    team,
    home_wins,
    home_draws,
    home_losses,
    away_wins,
    away_draws,
    away_losses,
    home_wins + away_wins AS total_wins,
    home_draws + away_draws AS total_draws,
    home_losses + away_losses AS total_losses,
    home_wins + home_draws + home_losses AS home_matches_played,
    away_wins + away_draws + away_losses AS away_matches_played,
    home_wins + home_draws + home_losses + away_wins + away_draws + away_losses AS total_matches_played,
    penalty_matches,
    penalty_wins,
    penalty_losses,
    home_goals_scored,
    home_goals_conceded,
    away_goals_scored,
    away_goals_conceded,
    home_goals_scored + away_goals_scored AS total_goals_scored,
    home_goals_conceded + away_goals_conceded AS total_goals_conceded
FROM combined_with_penalties c
JOIN countries ON c.team = countries.ISO_CODE;


CREATE VIEW match_information AS
SELECT 
    r.match_date,

    -- Home team info
    home_country.Display_Name AS home_team_name,
    home_country.ISO_Code AS home_code,
    home_country.Region_Name AS home_region,
    home_country.Status_of_Country AS home_status,
    home_country.Developed_or_not AS home_development_status,
    home_country.Population AS home_population,
    home_country.Area_SqKm AS home_sqKm,
    home_country.SIDS AS home_SIDS,
    home_country.LLDC AS home_LLDC,
    home_country.LDC AS home_LDC,

    -- Away team info
    away_country.Display_Name AS away_team_name,
    away_country.ISO_Code AS away_code,
    away_country.Region_Name AS away_region,
    away_country.Status_of_Country AS away_status,
    away_country.Developed_or_not AS away_development_status,
    away_country.Population AS away_population,
    away_country.Area_SqKm AS away_sqKm,
    away_country.SIDS AS away_SIDS,
    away_country.LLDC AS away_LLDC,
    away_country.LDC AS away_LDC,

    -- Match info
    r.home_score,
    r.away_score,
    r.tournament,
    r.city,

    -- Match location info
    country_name.Display_Name AS country_name,
    country_name.Region_Name AS match_country_region,
    country_name.Status_of_Country AS match_country_status,
    country_name.Developed_or_not AS match_country_development_status,
    country_name.SIDS AS match_country_SIDS,
    country_name.LLDC AS match_country_LLDC,
    country_name.LDC AS match_country_LDC,

    r.neutral,

    -- Shootout info
	COALESCE(s.winner, -1) AS shootout_winner,
     COALESCE(s.first_shooter, -1) AS shootout_first_shooter,
    CASE 
        WHEN s.winner IS NOT NULL THEN TRUE 
        ELSE FALSE 
    END AS went_to_shootout

FROM results r
JOIN countries home_country ON r.home = home_country.ISO_Code
JOIN countries away_country ON r.away = away_country.ISO_Code
JOIN countries country_name ON r.country = country_name.ISO_Code

-- Shootout data (either in shootouts or shootoutsExtra)
LEFT JOIN shootouts s 
    ON r.match_date = s.match_date AND r.home = s.home AND r.away = s.away;
    

CREATE VIEW main_info_of_player AS
WITH scorer_year_range AS (
SELECT scorer,team,countries.Display_Name AS country_name, MIN(year(match_date)) AS first_year, MAX(year(match_date)) AS last_year
FROM goalscorers JOIN countries ON goalscorers.team = countries.ISO_Code
GROUP BY scorer,team,countries.Display_Name
),
scorer_goal_stats AS (
SELECT
    scorer,Display_Name As country_name,
    SUM(game_goals) AS total_goals,
    MAX(game_goals) AS max_goals_per_game,
    SUM(own_goal_count) AS total_own_goals,
    SUM(penalty_made) AS penalties_scored
FROM (
    SELECT
        scorer,Display_Name,match_date,home,away,
        COUNT(*) AS game_goals,
        SUM(CASE WHEN own_goal = 1 THEN 1 ELSE 0 END) AS own_goal_count,
        SUM( CASE WHEN penalty = 1 THEN 1 ELSE 0 END) AS penalty_made
    FROM goalscorers JOIN countries ON goalscorers.team = countries.ISO_Code
    GROUP BY scorer,Display_Name, match_date, home, away
) AS sub_table
GROUP BY scorer,Display_Name
)
SELECT distinct sg.scorer,sg.country_name,sg.total_goals,sg.max_goals_per_game,sg.total_own_goals,sg.penalties_scored,
sy.first_year,sy.last_year
FROM scorer_goal_stats sg JOIN scorer_year_range sy ON sg.scorer = sy.scorer and sg.country_name = sy.country_name;


