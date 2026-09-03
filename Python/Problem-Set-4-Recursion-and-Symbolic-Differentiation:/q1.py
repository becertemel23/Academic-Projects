def solve_gems(values, target):
    
    if target==0 and values==[]:
        return 1
    
    if values==[]:
        return 0
    
    include_first=solve_gems(values[1:],target-values[0])
    exclude_first=solve_gems(values[1:],target)
    
    return include_first+exclude_first
    


