from q2a import codon_to_amino


def translate_codons_to_amino_acids(codons: tuple) -> str:
    
    result=""
    for codon in codons:
        result+=codon_to_amino(codon)
    return result
        
