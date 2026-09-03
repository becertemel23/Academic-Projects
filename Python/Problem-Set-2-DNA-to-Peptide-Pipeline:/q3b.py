def maybe_remove_initial_m(peptide: str) -> str:
    
    
    if peptide[0]=="M" and peptide[1] in "ACGPSTV":
        return peptide[1:]
    else:
        return peptide
