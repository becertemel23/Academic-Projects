def get_codons(mrna: str) -> tuple:
    
    result=()
    codon=""
    count=1
    for cod in mrna:
        if count%3!=0:
            codon+=cod
        else:
            codon+=cod
            result+=(codon,)
            codon=""
        count+=1
    return result
            
        
