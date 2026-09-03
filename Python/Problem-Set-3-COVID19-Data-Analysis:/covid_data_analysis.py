def daily_records_to_string(country_obj, idxs):
    output_str = ""
    for idx in idxs:
        record = country_obj['daily_records'][idx]
        output_str += f"Date: {record['date']}, Cases: {record['cases']}, Deaths: {record['deaths']} \n"

    return output_str


def get_total_cases(country_obj):
    
    daily_cases=country_obj["daily_records"]
    
    total_cases=0
    
    for cases in daily_cases:
        total_cases+=cases["cases"]
        


    return total_cases


def get_total_deaths(country_obj):
    
    daily_deaths=country_obj["daily_records"]
    
    total_deaths=0
    
    for deaths in daily_deaths:
        total_deaths+=deaths["deaths"]

    

    return total_deaths


def total_cases_and_deaths_per_1m(country_obj):
    
    
    cases_per_1m=(get_total_cases(country_obj)/country_obj["population"])*1000000
    
    deaths_per_1m=(get_total_deaths(country_obj)/country_obj["population"])*1000000


    return int(cases_per_1m), int(deaths_per_1m)


def sort_records_by(country_obj, param):
    

    nums = [(i, record[param]) for i, record in enumerate(country_obj['daily_records'])]
    
    sorted_nums=sorted(nums,key=lambda x: x[1])
    
    indexes=[]

    for element in sorted_nums:
        indexes.append(element[0])


    return indexes


def find_top_k_cases(country_obj, k):
    idxs = sort_records_by(country_obj, "cases")[-k:]
    out = daily_records_to_string(country_obj, idxs)

    return out


def find_top_k_deaths(country_obj, k):
    idxs = sort_records_by(country_obj, "deaths")[-k:]
    out = daily_records_to_string(country_obj, idxs)

    return out


def calc_exposure_per_age_group(country_obj):
    
    exposure_dict={}
    
    
    
    for key,value in country_obj["weekly_records"].items():
        total=0
        for cases in value:
            total+=cases["new_cases"]
        exposure_dict[key]=total/country_obj["population_by_agegroup"][key]
        
            

    return exposure_dict


def find_most_exposed_age_group(country_obj):
    
    
    exposure_dict=calc_exposure_per_age_group(country_obj)
    
    exposure_list=list(exposure_dict.items())
    
    ordered_list=sorted(exposure_list, key=lambda x: x[1], reverse=True)
    
    the_group=ordered_list[0][0]
    

    return the_group


def find_least_exposed_age_group(country_obj):
    
    
    exposure_dict=calc_exposure_per_age_group(country_obj)
    
    exposure_list=list(exposure_dict.items())
    
    ordered_list=sorted(exposure_list, key=lambda x: x[1])
    
    the_group=ordered_list[0][0]
    

    return the_group


def calc_prob_of_cases_per_age_group(country_obj):
    
    prob_dict={}
    
    
    total_cases=0
    for key,value in country_obj["weekly_records"].items():
        for cases in value:
            total_cases+=cases["new_cases"]
    for key,value in country_obj["weekly_records"].items():
        total=0
        for cases in value:
            total+=cases["new_cases"]
            
        
        
        prob_dict[key]=total/total_cases
    
    


    return prob_dict


def distribute_deaths_to_age_groups(country_obj):

    case_probs = calc_prob_of_cases_per_age_group(country_obj)
    age_groups = list(case_probs.keys())
    death_probs = {
        '<15yr': 0.05,
        '15-24yr': 0.05,
        '25-49yr': 0.1,
        '50-64yr': 0.2,
        '65-79yr': 0.25,
        '80+yr': 0.35
    }
    death_distribution={}
    weight_dict={}
    norm_weight_dict={}
    
    for age in age_groups:
        weight_dict[age]=case_probs[age]*death_probs[age]
    
    helper_total=0
    for key in weight_dict.keys():
        helper_total+=weight_dict[key]
    
    for key in weight_dict.keys():
        norm_weight_dict[key]=weight_dict[key]/helper_total
        
    total_deaths=get_total_deaths(country_obj)
    
    for key in weight_dict.keys():
        death_distribution[key]=total_deaths*norm_weight_dict[key]
        
        


    return total_deaths, death_distribution








