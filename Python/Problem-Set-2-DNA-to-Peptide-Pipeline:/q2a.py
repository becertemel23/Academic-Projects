def codon_to_amino(codon: str) -> str:
    if codon in ("UUU","UUC"):
        return "F"
    elif codon in ("UUA","UUG","CUU","CUC","CUA","CUG"):
        return "L"  
    elif codon in ("AUU","AUC","AUA"):
        return "I"
    elif codon in ("AUG"):
        return "M"
    elif codon in ("GUU","GUC","GUA","GUG"):
        return "V"
    elif codon in ("UCU","UCC","UCA","UCG","AGU","AGC"):
        return "S"
    elif codon in ("CCU","CCC","CCA","CCG"):
        return "P"
    elif codon in ("ACU","ACC","ACA","ACG"):
        return "T"
    elif codon in ("GCU","GCC","GCA","GCG"):
        return "A"
    elif codon in ("UAU","UAC"):
        return "Y"
    elif codon in ("CAU","CAC"):
        return "H"
    elif codon in ("CAA","CAG"):
        return "Q"
    elif codon in ("AAU","AAC"):
        return "N"
    elif codon in ("AAA","AAG"):
        return "K"
    elif codon in ("GAU","GAC"):
        return "D"
    elif codon in ("GAA","GAG"):
        return "E"
    elif codon in ("UGU","UGC"):
        return "C"
    elif codon in ("UGG"):
        return "W"
    elif codon in ("CGU","CGC","CGA","CGG","AGA","AGG"):
        return "R"
    elif codon in ("GGU","GGC","GGA","GGG"):
        return "G"
    elif codon in ("UAA","UAG","UGA"):
        return "*"