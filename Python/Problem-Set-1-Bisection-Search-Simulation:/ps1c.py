# PART C: Critical Decision
def simulate_growth(initial_bacteria, initial_white_cells, white_cell_daily_increase, bacteria_growth_rate,
                    bacteria_death_rate, white_cell_death_rate, days=28):
    count=0
    while count<days:
        initial_bacteria+=int(initial_bacteria*bacteria_growth_rate)
        initial_white_cells+=int(initial_white_cells*white_cell_daily_increase)
        initial_bacteria-=int(initial_white_cells*bacteria_death_rate)
        initial_white_cells-=int(initial_white_cells*white_cell_death_rate)
        count+= 1
        
    return initial_bacteria,initial_white_cells

def wc_count_is_acceptable(final_bacteria, final_white_cells):
    
    if abs(final_bacteria-final_white_cells)<=800:
        return True
    else:
        return False

def find_minimum_wc_growth_rate(initial_bacteria, initial_white_cells, bacteria_growth_rate,
                                bacteria_death_rate, white_cell_death_rate):
    final_bacteria,final_white_cells=simulate_growth(initial_bacteria , initial_white_cells,0,bacteria_growth_rate, bacteria_death_rate, white_cell_death_rate)
    
    if wc_count_is_acceptable(final_bacteria, final_white_cells) or final_white_cells>final_bacteria:
        return (final_bacteria,final_white_cells,0)
    
    low=0
    high=0.95
    mid=(low+high)/2
    count=0
    final_bacteria,final_white_cells=simulate_growth(initial_bacteria, initial_white_cells, mid, bacteria_growth_rate, bacteria_death_rate, white_cell_death_rate)
    while (not wc_count_is_acceptable(final_bacteria,final_white_cells)) and count<400:
        if final_bacteria>final_white_cells:
            low=mid
        else:
            high=mid
        count+=1
        mid=(high+low)/2
        final_bacteria,final_white_cells=simulate_growth(initial_bacteria, initial_white_cells, mid, bacteria_growth_rate, bacteria_death_rate, white_cell_death_rate)
    
    if wc_count_is_acceptable(final_bacteria, final_white_cells):
        return (final_bacteria,final_white_cells,mid)
    else:
        return None
        
