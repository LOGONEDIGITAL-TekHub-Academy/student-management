package com.logonedigital.student_management.common;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.logonedigital.student_management.student.Student;
import com.logonedigital.student_management.student.grade.Grade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {
    @Value("${application.file.reports-dir}")
    private String uploadDir;

    public String generateReportPdf(Student student, List<Grade> grades) throws IOException, DocumentException {

        String fileName = "Bulletin_" + student.getMatricule() + ".pdf";
        // Création du répertoire s'il n'existe pas
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + File.separator + fileName;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();

        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);
        Paragraph title = new Paragraph("Bulletin de Notes", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\nNom : " + student.getFirstname()));
        document.add(new Paragraph("Prénom : " + student.getLastname()));
        document.add(new Paragraph("Matricule : " + student.getMatricule()));
        document.add(new Paragraph("\n"));
        //Création d'un tableau
        PdfPTable table = new PdfPTable(3); // 3 colonnes : Cours, Note, Appréciation
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // En-tête du tableau
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPCell cell1 = new PdfPCell(new Phrase("Cours", headerFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Note", headerFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Appréciation", headerFont));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        // Remplissage du tableau
        for (Grade note : grades) {
            table.addCell(note.getCourse().getTitle());
            table.addCell(String.valueOf(note.getValue()));
            table.addCell(note.getAppreciation());
        }

        document.add(table);

        document.close();
        return filePath;
    }

    public String generateBulletin(Student student, List<Grade> grades) throws IOException, DocumentException {
        String fileName = "Bulletin_" + student.getMatricule() + ".pdf";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + File.separator + fileName;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();

        // Titre centré en gros
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
        Paragraph title = new Paragraph("Bulletin de Notes", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Informations étudiant
        Font infoFont = new Font(Font.FontFamily.HELVETICA, 12);
        document.add(new Paragraph("Nom: " + student.getLastname(), infoFont));
        document.add(new Paragraph("Prénom: " + student.getFirstname(), infoFont));
        document.add(new Paragraph("Email: " + student.getEmail(), infoFont));
        document.add(new Paragraph("MATRICULE: " + student.getMatricule(), infoFont));
        document.add(new Paragraph("\n"));

        // Tableau des notes
        PdfPTable table = new PdfPTable(4); // 4 colonnes comme dans l'image
        table.setWidthPercentage(100);
        table.setSpacingBefore(15f);
        table.setSpacingAfter(30f);

        // En-tête du tableau avec style
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell1 = new PdfPCell(new Phrase("Matière", headerFont));
        cell1.setBackgroundColor(new BaseColor(70, 130, 180)); // Bleu acier
        PdfPCell cell2 = new PdfPCell(new Phrase("Note", headerFont));
        cell2.setBackgroundColor(new BaseColor(70, 130, 180));
        PdfPCell cell3 = new PdfPCell(new Phrase("Appreciation", headerFont));
        cell3.setBackgroundColor(new BaseColor(70, 130, 180));
        PdfPCell cell4 = new PdfPCell(new Phrase("Prof.", headerFont));
        cell4.setBackgroundColor(new BaseColor(70, 130, 180));

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        // Remplissage du tableau
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 10);
        for (Grade note : grades) {
            table.addCell(new Phrase(note.getCourse().getTitle(), contentFont));
            table.addCell(new Phrase(String.valueOf(note.getValue()), contentFont));
            table.addCell(new Phrase(note.getAppreciation(), contentFont));
            table.addCell(new Phrase(note.getCourse().getTeacherName(), contentFont)); // Colonne Pref. vide
        }

        document.add(table);

        // Signature du directeur alignée à droite
        Paragraph signature = new Paragraph();
        signature.setAlignment(Element.ALIGN_RIGHT);
        signature.add(new Chunk("Directeur", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        signature.add(Chunk.NEWLINE);
        signature.add(new Chunk("Th", new Font(Font.FontFamily.TIMES_ROMAN, 12)));
        document.add(signature);

        document.close();
        return filePath;
    }
}
