package com.example.gendoc.controller;

import com.example.gendoc.service.PdfGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final PdfGenerationService pdfService;

    @GetMapping("/carte/{etudiantId}")
    public ResponseEntity<byte[]> getCarteEtudiant(@PathVariable Long etudiantId) {
        byte[] pdf = pdfService.genererCarteEtudiant(etudiantId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"carte_etudiant_" + etudiantId + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/bulletin/{etudiantId}/{semestre}")
    public ResponseEntity<byte[]> getBulletin(@PathVariable Long etudiantId, @PathVariable String semestre) {
        byte[] pdf = pdfService.genererBulletin(etudiantId, semestre);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bulletin_" + semestre + "_" + etudiantId + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
