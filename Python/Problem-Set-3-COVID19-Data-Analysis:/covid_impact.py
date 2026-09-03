from covid_data_analysis import total_cases_and_deaths_per_1m, calc_exposure_per_age_group
import json


def analyze_covid_impact(db):
 

    top_case_cnt = 0
    top_death_cnt = 0
    top_case_country = ""
    top_death_country = ""
    
    case_list=[]
    death_list=[]
    exposure_list=[]
    for country in db.values():
        case_list.append((country["name"],total_cases_and_deaths_per_1m(country)[0]))
        death_list.append((country["name"],total_cases_and_deaths_per_1m(country)[1]))
        exposure_list.append(calc_exposure_per_age_group(country))
    exposure_dict={}
    for dictionary in exposure_list:
        for age in dictionary:
            if age in exposure_dict:
                exposure_dict[age]+=dictionary[age]
            if age not in exposure_dict:
                exposure_dict[age]=dictionary[age]
    ordered_exposure_list=sorted(list(exposure_dict.items()),key=lambda x: x[1])
    most_exposed_age_group=ordered_exposure_list[-1][0]
    least_exposed_age_group=ordered_exposure_list[0][0]
    
    
    
        
        
    case_list=sorted(case_list, key=lambda x: x[1], reverse=True)
    death_list=sorted(death_list, key=lambda x: x[1], reverse=True)
    top_case_cnt+=case_list[0][1]
    top_death_cnt+=death_list[0][1]
    top_case_country+=case_list[0][0]
    top_death_country+=death_list[0][0]
    
    
        
    

    return (top_death_country, top_death_cnt, top_case_country,
            top_case_cnt, most_exposed_age_group, least_exposed_age_group)


def write_summary_to_json(data):
    
    with open("covid_impact_summary.json","w") as file:
        json.dump({"Highest_Deaths_per_1M":{"Country":data[0],"Deaths_per_1M":data[1]},"Highest_Cases_per_1M":{"Country":data[2],"Cases_per_1M":data[3]},"Most_Exposed_Age_Group":data[4],"Least_Exposed_Age_Group":data[5]},file)
