def build_peptides_from_amino_sequence(amino_acids: str) -> tuple:
    
    checker=False
    peptide=""
    result=()
    
    
    for i in range(len(amino_acids)):
        if amino_acids[i]=="M":
            checker=True
        if amino_acids[i]=="*":
            checker=False
        if checker:
            peptide+=amino_acids[i]
        if amino_acids[i]=="*" and ("M" in peptide):
            result+=(peptide,)
            peptide=""
    return result


