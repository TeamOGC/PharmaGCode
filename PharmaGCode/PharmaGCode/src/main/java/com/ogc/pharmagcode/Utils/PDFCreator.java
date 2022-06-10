package com.ogc.pharmagcode.Utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.Main;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFCreator {
    private static final Font fontTitolo = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLACK);
    private static final Font fontSottotitolo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    private static final Font fontCorpo = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);


    public static void creaPDF(Collo collo) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        String foo = String.valueOf(collo.getId_collo());
        PdfWriter.getInstance(document, new FileOutputStream("PharmaGCode/PharmaGCode/pdf/Ricevuta di consegna n." + foo + ".pdf"));
        document.open();
        aggiungiMetadati(document);
        aggiungiTitolo(document, collo);
        creaTabella(document, collo);
        document.close();
        File file = new File("PharmaGCode/PharmaGCode/pdf/Ricevuta di consegna n." + foo + ".pdf");
        openFile(file);

    }

    private static void aggiungiMetadati(Document document) {
        document.addTitle("Ricevuta di Consegna");
    }

    private static void aggiungiTitolo(Document d, Collo c) throws DocumentException, IOException {
        Paragraph titolo = new Paragraph();
        Image img = Image.getInstance("PharmaGCode/PharmaGCode/images/LOGO_UNIPA.png");
        img.setAlignment(Element.ALIGN_RIGHT);
        titolo.add(img);
        Paragraph p1 = (new Paragraph("Ricevuta di consegna ordine", fontTitolo));
        p1.setAlignment(Element.ALIGN_LEFT);
        titolo.add(p1);
        d.add(Chunk.NEWLINE);
        Paragraph p2 = new Paragraph("Questa ricevuta è stata generata in data " + Main.orologio.chiediOrario().toLocalDate() + "\ned è relativa al collo n. " + String.valueOf(c.getId_collo()), fontSottotitolo);
        p2.setAlignment(Element.ALIGN_LEFT);
        titolo.add(p2);
        d.add(titolo);
        int i = 0;
        while (i < 3) {
            d.add(Chunk.NEWLINE);
            i++;
        }
    }

    private static void creaTabella(Document d, Collo c) throws DocumentException {
        Paragraph p = new Paragraph();
        PdfPTable tabella = new PdfPTable(4);
        insertCell(tabella, "Nome Farmaco", Element.ALIGN_CENTER, 1, fontCorpo);
        insertCell(tabella, "Quantità", Element.ALIGN_CENTER, 1, fontCorpo);
        insertCell(tabella, "Farmacia di Destinazione", Element.ALIGN_CENTER, 1, fontCorpo);
        insertCell(tabella, "Firma", Element.ALIGN_CENTER, 1, fontCorpo);
        insertCell(tabella, "", Element.ALIGN_LEFT, 4, fontCorpo);
        for (int i = 0; i < c.getOrdini().size(); i++) {
            insertCell(tabella, c.getOrdini().get(i).getNome_farmaco(), Element.ALIGN_CENTER, 1, fontCorpo);
            insertCell(tabella, String.valueOf(c.getOrdini().get(i).getQuantita()), Element.ALIGN_CENTER, 1, fontCorpo);
            insertCell(tabella, c.getNome_farmacia() + " " + c.getIndirizzo_farmacia(), Element.ALIGN_CENTER, 1, fontCorpo);
            insertCell(tabella, c.getFirma(), Element.ALIGN_CENTER, 1, fontCorpo);
        }
        p.add(tabella);
        d.add(p);

    }

    private static void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

    private static void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
