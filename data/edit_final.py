import pandas as pd

doc_file = open("unknown_countries.txt", "w", encoding="utf-8", errors="ignore")


# Load CSVs
results_df = pd.read_csv("results.csv", dtype={"minute": "Int64"},encoding="utf-8")
shootouts_df = pd.read_csv("shootouts.csv", dtype={"minute": "Int64"},encoding="utf-8")
goalscorers_df = pd.read_csv("goalscorers.csv", dtype={"minute": "Int64"},encoding="utf-8")
countries_df = pd.read_csv("countries.csv", encoding="utf-8")

# Create the mapping from Display_Name to ISO_Code
country_map = dict(zip(countries_df["Display_Name"], countries_df["ISO_Code"]))

country_replacements = {
    "Dahomey":204, #Benin
    "Soviet Union": 643,  # Russia
    "Ceylon": 144, #Sri Lanka
    "Western Samoa": 16, #Samoa
    "Western Australia" : 36, #Oscar Piastri
    "Upper Volta":854, #Burkina Faso
    "Netherlands Antilles": 530, #Curaçao
    "Bohemia" : 203, # Chechia
    "Bohemia and Moravia" : 203, # Chehia
    "Czech Republic" : 203, #Chechia
    "Belgian Congo" :178, #DR Congo
    "Congo-Léopoldville" :178, #DR Congo
    "Congo-Kinshasa" :178, #DR Congo
    "Zaïre" :178, #DR Congo
    "DR Congo":178, #DR Congo
    "French Somaliland":262, #Djibouti
    "Swaziland":748, #Eswatini
    "Gold Coast":288, #Ghana
    "Portuguese Guinea":624 ,#Guinea-Bissau 
    "British Guiana":328 , #Guyana
    "Mandatory Palestine":376, #Israel
    "Nyasaland":454, #Malawi
    "Malaya":458, #Malaysia
    "Burma":104, #Myanmar
    "Republic of Ireland":372 , #Dublin Ireland
    "Irish Free State":372 , #Dublin Ireland
    "Éire":372 , #Dublin Ireland
    "Northern Ireland":372 , #Ireland
    "CIS":643 , #Russia
    "Western Samoa":882 , #Samoa
    "Dutch Guyana":740 , #Suriname
    "Tanganyika":834, #Tanzania
    "New Hebrides":548, #Vanuatu
    "Northern Rhodesia":894, #Zambia
    "Southern Rhodesia":716 ,#Zimbabwe 
    "Vatican City":336 ,#Vatican
    "United States Virgin Islands": 850 ,#Virgin Islands
    "China PR":156 ,#China
    "Vietnam Republic":704 ,#Vietnam
    "Parishes of Jersey":832, #Jersey
    "Yemen AR":887, #Yemen
    "Eswatini":748, #Swaziland
    "North Macedonia":807, #FYROM
    "Curaçao":531, #Curacao
    "São Tomé and Príncipe":678 ,#Sao Tome and Principe
    "Congo":180 ,#Republic of Congo
    "Timor-Leste":626, #Timor Leste
    "Wallis Islands and Futuna":876, #Wallis islands
    "Yemen DPR":984, #South Yemmen
    "Bonaire":535, #Boaire
    "Åland Islands":248, #Aland Islands
    "Åland":248, #Aland Islands
    "Ellan Vannin":833, #Isle of man
    "Saint Barthélemy":652, #Saint Barthelemy
    "Macau":446 ,#Macao
    "Luhansk PR":913 ,#Luhansk
    "German DR":909, #East Germany
    "Chagos Islands":86,#British Indian Ocean Territory
}

# Add entries from replacements
country_map.update(country_replacements)

# First replace any known historic names with the correct ISO_Code directly
results_df["home_team"] = results_df["home_team"].replace(country_replacements)
results_df["away_team"] = results_df["away_team"].replace(country_replacements)
results_df["country"] = results_df["country"].replace(country_replacements)

shootouts_df["home_team"] = shootouts_df["home_team"].replace(country_replacements)
shootouts_df["away_team"] = shootouts_df["away_team"].replace(country_replacements)
shootouts_df["first_shooter"] = shootouts_df["first_shooter"].replace(country_replacements)
shootouts_df["winner"] = shootouts_df["winner"].replace(country_replacements)

goalscorers_df["home_team"] = goalscorers_df["home_team"].replace(country_replacements)
goalscorers_df["away_team"] = goalscorers_df["away_team"].replace(country_replacements)
goalscorers_df["team"] = goalscorers_df["team"].replace(country_replacements)

# Function to map country name to ISO_Code
def get_iso_code(name):
    return country_map.get(name, name)

# Now identify unknown countries not in the country_map
unknown_countries = set()
for col in ["home_team", "away_team", "country"]:
    for team in results_df[col].unique():
        if team not in country_map and not isinstance(team, int):
            unknown_countries.add(team)

# Assign temporary negative ISO codes to unknown countries
temp_iso_code = -1
print("################ start countries.csv ################",file=doc_file)
for country in unknown_countries:
    print(f"Unknown Country: {country} → Temp ISO_Code: {temp_iso_code}",file=doc_file)
    country_map[country] = temp_iso_code
    temp_iso_code -= 1
print("################ end countries.csv ################",file=doc_file)



# Apply mapping
results_df["home_team"] = results_df["home_team"].apply(get_iso_code)
results_df["away_team"] = results_df["away_team"].apply(get_iso_code)
results_df["country"] = results_df["country"].apply(get_iso_code)

####################################################################################
########################## shootouts.csv ###########################################

# Now identify unknown countries not in the country_map
unknown_countries = set()
for col in ["home_team", "away_team", "winner"]:
    for team in shootouts_df[col].unique():
        if team not in country_map and not isinstance(team, int):
            unknown_countries.add(team)

# Assign temporary negative ISO codes to unknown countries
temp_iso_code = -1
print("################ start shootouts.csv ################",file=doc_file)
for country in unknown_countries:
    print(f"Unknown Country: {country} → Temp ISO_Code: {temp_iso_code}",file=doc_file)
    country_map[country] = temp_iso_code
    temp_iso_code -= 1
print("################ end shootouts.csv ################",file=doc_file)

# Apply mapping
shootouts_df["home_team"] = shootouts_df["home_team"].apply(get_iso_code)
shootouts_df["away_team"] = shootouts_df["away_team"].apply(get_iso_code)
shootouts_df["first_shooter"] = shootouts_df["first_shooter"].apply(get_iso_code)
shootouts_df["winner"] = shootouts_df["winner"].apply(get_iso_code)

# After mapping everything
shootouts_df["first_shooter"] = shootouts_df["first_shooter"].astype("Int64")

####################################################################################
########################## goalscorers.csv ###########################################

# Now identify unknown countries not in the country_map
unknown_countries = set()
for col in ["home_team", "away_team", "team"]:
    for team in goalscorers_df[col].unique():
        if team not in country_map and not isinstance(team, int):
            unknown_countries.add(team)

# Assign temporary negative ISO codes to unknown countries
temp_iso_code = -1
print("################ start goalscorers.csv ################",file=doc_file)
for country in unknown_countries:
    print(f"Unknown Country: {country} → Temp ISO_Code: {temp_iso_code}",file=doc_file)
    country_map[country] = temp_iso_code
    temp_iso_code -= 1
print("################ end goalscorers.csv ################",file=doc_file)

# Apply mapping
goalscorers_df["home_team"] = goalscorers_df["home_team"].apply(get_iso_code)
goalscorers_df["away_team"] = goalscorers_df["away_team"].apply(get_iso_code)
goalscorers_df["team"] = goalscorers_df["team"].apply(get_iso_code)

# Save cleaned results
goalscorers_df.to_csv("goalscorers.csv", index=False)

# Save cleaned results
results_df.to_csv("results.csv", index=False)

# Save cleaned results
shootouts_df.to_csv("shootouts.csv", index=False)

doc_file.close()

print(" Completed Saving unknown_countries.txt!")
print(" Completed Saving results.csv!")
print(" Completed Saving goalscorers.csv!")
print(" Completed Saving shootouts.csv!")

