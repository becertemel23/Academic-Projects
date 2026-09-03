from datetime import datetime
import json



def add_daily_record(country_obj, date, cases, deaths):

    country_obj['daily_records'].append({'date': date, 'cases': cases, 'deaths': deaths})


def add_weekly_case_data(country_obj, age_group, year_week, new_cases):

    country_obj['weekly_records'][age_group].append({'year_week': year_week, 'new_cases': new_cases})


def create_country_entry(name, population):

    return {
        'name': name,
        'population': population,
        'population_by_agegroup': {'<15yr': -1, '15-24yr': -1, '25-49yr': -1, '50-64yr': -1, '65-79yr': -1,
                                   '80+yr': -1},
        'daily_records': [],
        'weekly_records': {'<15yr': [], '15-24yr': [], '25-49yr': [], '50-64yr': [], '65-79yr': [], '80+yr': []},
    }


def process_daily_data(db):
    
    
    with open("covid_data.json","r") as file:
        data=json.load(file)
        
    records=data["records"]
    
    for record in records:
        country=record["geoId"]
        
        if country not in db:
            db[country]=create_country_entry(record["countriesAndTerritories"], int(record["popData2020"]))
        
        date_string=record["dateRep"]
        date_object=datetime.strptime(date_string,"%d/%m/%Y")
        if datetime(2021,3,7)<date_object<datetime(2021,5,3):
            add_daily_record(db[country], date_string, record["cases"], record["deaths"])
            



def process_weekly_data(db):
    
    with open("age_cases.json","r") as file:
        full_data=json.load(file)
    
    valid_weeks=[]
    for i in range(10,18):
        valid_weeks.append(f"2021-{i:02d}")
        
    
    for data in full_data:
        
        if data["year_week"] in valid_weeks:
            if data["country_code"] not in db:
                db[data["country_code"]]=create_country_entry(data["country_code"], data["population"])
            if db[data["country_code"]]["population_by_agegroup"][data["age_group"]]==-1:
                db[data["country_code"]]["population_by_agegroup"][data["age_group"]]=data["population"]
            
            add_weekly_case_data(db[data["country_code"]], data["age_group"], data["year_week"], data["new_cases"])
                                                    

