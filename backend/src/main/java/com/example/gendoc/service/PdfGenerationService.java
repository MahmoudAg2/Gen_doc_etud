package com.example.gendoc.service;

import com.example.gendoc.entity.Etudiant;
import com.example.gendoc.entity.Note;
import com.example.gendoc.repository.EtudiantRepository;
import com.example.gendoc.repository.NoteRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    private final EtudiantRepository etudiantRepository;
    private final NoteRepository noteRepository;

    public byte[] genererCarteEtudiant(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitre = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font fontText = new Font(Font.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("CARTE D'ÉTUDIANT", fontTitre));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Nom : " + etudiant.getUtilisateur().getNom(), fontText));
            document.add(new Paragraph("Prénom : " + etudiant.getUtilisateur().getPrenom(), fontText));
            document.add(new Paragraph("Matricule : " + etudiant.getMatricule(), fontText));
            if (etudiant.getClasse() != null) {
                document.add(new Paragraph("Classe : " + etudiant.getClasse().getNom(), fontText));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur de génération du PDF: " + e.getMessage());
        }
    }

    public byte[] genererBulletin(Long etudiantId, String semestre) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
        List<Note> notes = noteRepository.findByEtudiantIdAndSemestre(etudiantId, semestre);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitre = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font fontText = new Font(Font.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("BULLETIN DE NOTES - " + semestre, fontTitre));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Étudiant : " + etudiant.getUtilisateur().getNom() + " " + etudiant.getUtilisateur().getPrenom(), fontText));
            document.add(new Paragraph(" "));

            double somme = 0;
            for (Note note : notes) {
                document.add(new Paragraph(note.getMatiere().getNom() + " : " + note.getValeur() + "/20 - " + note.getAppreciation(), fontText));
                somme += note.getValeur();
            }

            if (!notes.isEmpty()) {
                double moyenne = somme / notes.size();
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Moyenne : " + String.format("%.2f", moyenne) + "/20", fontTitre));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur de génération du PDF: " + e.getMessage());
        }
    }
}
