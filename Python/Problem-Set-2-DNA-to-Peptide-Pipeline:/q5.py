def count_occurences(pep, peptides):
    
    count=0
    
    for i in range(len(peptides)):
        if peptides[i]==pep:
            count+=1
    return count



def peptides_freq_count(peptides):
    
    result=()
    checker=()
    
    for pep in peptides:
        if pep not in checker:
            checker+=(pep,)
            result+=((pep,count_occurences(pep, peptides)),)
    return result
            
    
    
