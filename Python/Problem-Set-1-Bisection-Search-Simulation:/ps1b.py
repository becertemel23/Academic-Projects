# PART B: Mounting Infection
def wc_count_with_bacterial_growth(initial_bacteria, initial_white_cells, white_cell_growth_rate, bacteria_growth_rate, bacteria_death_rate, white_cell_death_rate):
    
    count=0
    bacteria=initial_bacteria
    white_cells=initial_white_cells
    
    while bacteria>white_cells and count<=85:
        bacteria+=int(bacteria*bacteria_growth_rate)
        white_cells+=int(white_cells*white_cell_growth_rate)
        bacteria-=int(white_cells*bacteria_death_rate)
        white_cells-=int(white_cells*white_cell_death_rate)
        count+=1
    
    if count<=85:
        return count
    else:
        return None