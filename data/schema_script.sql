create database if not exists footballdb;
use footballdb;

show variables like 'local_infile';
set global local_infile = 1;

create table countries(
	ISO varchar(3),
    ISO3 varchar(3),
    ISO_Code int,
    FIPS varchar(255),
    Display_Name varchar(255),
    Official_Name varchar(255),
    Capital varchar(255),
    Continent varchar(255),
    Currency_Code varchar(30),
    Currency_name varchar(255),
    Phone varchar(255),
    Region_Code varchar(255),
    Region_Name varchar(255),
    Subregion_Code varchar(255),
    Subregion_Name varchar(255),
    Intermediate_Region_Code varchar(255),
    Intermediate_Region_Name varchar(255),
    Status_of_Country varchar(255),
    Developed_or_not varchar(255),
    SIDS varchar(255),
    LLDC varchar(255),
    LDC varchar(255),
    Area_SqKm int,
    Population int,
    Primary key (ISO_Code)
)ENGINE=InnoDB;

create table results(
	match_date date,
    home int,
    away int,
    home_score int,
    away_score int,
    tournament varchar(255),
    city varchar(255),
    country int,
    neutral boolean,
    primary key (match_date,home,away),
    foreign key(home) references countries(ISO_Code) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key(away) references countries(ISO_Code) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key(country) references countries(ISO_Code) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

create table shootouts(
	match_date date,
    home int,
    away int,
    winner int,
    first_shooter int,
    primary key (match_date,home,away),
    foreign key(match_date,home,away) references results(match_date,home,away) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

CREATE TABLE goalscorers (
	goal int primary key auto_increment,
    match_date date,
    home int,
    away int,
    team int,
    scorer VARCHAR(255),
    minute_of_goal int,
    own_goal boolean,
    penalty boolean,
    foreign key(match_date,home,away) references results(match_date,home,away) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

create table former_names(
	current_name int,
    former_name varchar(255),
    start_date date,
    end_date date,
    primary key (former_name),
    foreign key(current_name) references countries(ISO_Code) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

create table shootoutsExtra(
    match_date date,
    home int,
    away int,
    winner int,
    first_shooter int,
    primary key (match_date,home,away),
    foreign key(home) references countries(ISO_Code) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key(away) references countries(ISO_Code) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

drop table former_names;

LOAD DATA LOCAL INFILE 'D:/mysql/former_names.csv'
INTO TABLE former_names
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

DROP TABLE countries;

LOAD DATA LOCAL INFILE 'D:/mysql/countries_new.csv'
INTO TABLE countries
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;


DROP TABLE results;

LOAD DATA LOCAL INFILE 'D:/mysql/results_new.csv'
INTO TABLE results
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;


DROP TABLE shootouts;

LOAD DATA LOCAL INFILE 'D:/mysql/shootouts_new.csv'
INTO TABLE shootouts
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

DROP TABLE goalscorers;

LOAD DATA LOCAL INFILE 'D:/mysql/goalscorers_new.csv'
INTO TABLE goalscorers
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(match_date, home, away, team, scorer, minute_of_goal, own_goal, penalty);

drop table shootoutsExtra;

LOAD DATA LOCAL INFILE 'D:/mysql/shootoutsExtra.csv'
INTO TABLE shootoutsExtra
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

