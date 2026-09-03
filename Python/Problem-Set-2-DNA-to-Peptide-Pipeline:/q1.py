def transcribe_dna_to_mrna(dna: str) -> str:
    dna=dna.upper()
    result=""
    if "ATG" in dna:
        for i in range(len(dna)):
            if dna[i]=="A":
                result+="A"
            elif dna[i]=="T":
                result+="U"
            elif dna[i]=="C":
                result+="C"
            elif dna[i]=="G":
                result+="G"
    else:
        for i in range(len(dna)):
            if dna[i]=="A":
                result+="U"
            elif dna[i]=="T":
                result+="A"
            elif dna[i]=="C":
                result+="G"
            elif dna[i]=="G":
                result+="C"
    return result
        
        
    

