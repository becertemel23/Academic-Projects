def generate_permutations(letter_sequence):
    
    if letter_sequence=="" or len(letter_sequence)==1:
        return [letter_sequence]
    
    
    
    result=[]
    
    for i in range(len(letter_sequence)):
        if i!=len(letter_sequence)-1:
            exclude_letter=generate_permutations(letter_sequence[0:i]+letter_sequence[i+1:])
        else:
            exclude_letter=generate_permutations(letter_sequence[0:i])
        
        for char in exclude_letter:
            result.append(char+letter_sequence[i])
      

        
        
    return result
