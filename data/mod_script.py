import pandas as pd

# Load the CSV file
df_goalscorers = pd.read_csv("goalscorers.csv")
df_shootouts = pd.read_csv('shootouts.csv')
df_countries = pd.read_csv("countries.csv")#, dtype=str)

####################################################################
########## script for goalscorers ##################################

# Convert 'penalty' column: False -> 0, True -> 1
df_goalscorers['penalty'] = df_goalscorers['penalty'].map({False: 0, True: 1})

# Convert 'own_goal' column: False -> 0, True -> 1
df_goalscorers['own_goal'] = df_goalscorers['own_goal'].map({False: 0, True: 1})

# Replace NaN (NULL) values in the 'minute' column with -1 (as an integer)
df_goalscorers['minute'] = df_goalscorers['minute'].fillna(-1).astype(int)

# Save the transformed data back to a new CSV file
df_goalscorers.to_csv("goalscorers.csv", index=False)

print("Succesfully Transformed file goalscorers.csv")

#####################################################################
############### script for shootouts ##############################

# Replace null values in the 'first_shooter' column with -1 as an integer
df_shootouts['first_shooter'] = df_shootouts['first_shooter'].fillna(-1).astype(int)

# Save the transformed data to a new CSV file
df_shootouts.to_csv('shootouts.csv', index=False)

print("Succesfully Transformed file shootouts.csv")

#####################################################################
############### script for countries ################################

# Replace NaN values with "N/A"
df_countries.fillna("N/A", inplace=True)

# Save the updated file
df_countries.to_csv("countries.csv", index=False)

print("Succesfully Transformed file countries.csv")

