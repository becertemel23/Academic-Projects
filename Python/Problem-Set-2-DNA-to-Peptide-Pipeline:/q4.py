from q1 import transcribe_dna_to_mrna
from q2b import get_codons
from q2c import translate_codons_to_amino_acids
from q3a import build_peptides_from_amino_sequence
from q3b import maybe_remove_initial_m


def dna_to_peptides(dna: str) -> tuple:
    
    m_rnas=transcribe_dna_to_mrna(dna)
    codons=get_codons(m_rnas)
    amino_acids=translate_codons_to_amino_acids(codons)
    peptids=build_peptides_from_amino_sequence(amino_acids)
    
    result=()
    for peptid in peptids:
        peptid=maybe_remove_initial_m(peptid)
        result+=(peptid,)
        
    return result
