# PART A: Initial Struggle
def wc_count_no_bacterial_growth(initial_bacteria, initial_white_cells, white_cell_growth_rate, bacteria_death_rate):
    
    count_days=0
    bacteria=initial_bacteria
    white_cell=initial_white_cells
    while bacteria>white_cell and count_days<=85:
        white_cell+=int(white_cell*white_cell_growth_rate)
        bacteria-=int(white_cell*bacteria_death_rate)
        count_days+=1
        
    if count_days<=85:
        return count_days
    else:    
        return None