package com.cami.view;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.LigneAppel;
import com.cami.persistence.model.TypeMateriel;
import com.cami.view.resolver.AbstractITextPdfView;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.WHITE;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static java.lang.Long.MAX_VALUE;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PdfView
        extends AbstractITextPdfView
{

    @Override
    protected void buildPdfDocument(final Map<String, Object> model,
            final Document document, final PdfWriter writer,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {
        if (model.get("mesTypeMateriels") != null) {
            buildPdfDocumentForTypeMateriel(model, document, writer, request, response);
        }
        if (model.get("appelOffre") != null) {
            buildPdfDocumentForAppelOffre(model, document, writer, request, response);
        }
        if (model.get("cautionsReport") != null) {
            // This method doesn't work fine because columns are out of range of the pdf sheet
            // A good solution will be to see how to set the sheet's orientation to Landscape
            // Because the default orientation is Portrait
            buildPdfDocumentForCautions(model, document, writer, request, response);
        }
    }

    private void buildPdfDocumentForTypeMateriel(final Map<String, Object> model,
            Document document, final PdfWriter writer,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {
//        document = new Document(PageSize);
        final List<TypeMateriel> materiels = (List<TypeMateriel>) model
                .get("mesTypeMateriels");

        PdfPTable table = new PdfPTable(3);
        table = buildStyle(table);

        table.addCell("ID");
        table.addCell("CODE");
        table.addCell("NOM");

        for (final TypeMateriel typeMateriel2 : materiels) {
            table.addCell(typeMateriel2.getId() + "");
            table.addCell(typeMateriel2.getCode());
            table.addCell(typeMateriel2.getNom());

        }
        document.add(table);
    }

    private void buildPdfDocumentForCautions(final Map<String, Object> model,
            final Document document, final PdfWriter writer,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {
        final List<AppelOffre> appelOffres = (List<AppelOffre>) model.get("cautionsReport");
        PdfPTable table = new PdfPTable(21);
        table = buildStyle(table);

//        table.setHeaderRows(0);
        table.addCell("N° Dossier VN");
        table.addCell("N° Cpte Client");
        table.addCell("N° A.O");
        table.addCell("Date remise A.O");
        table.addCell("Bénéficiaire");
        table.addCell("Ref. Marché");
        table.addCell("Objet A.O");
        table.addCell("Delai Validité A.O");
        table.addCell("Montant Marché");
        table.addCell("Delai reception Provisoire");
        table.addCell("Delai reception Définitif");
        table.addCell("Delai retenue de Gtie");
        table.addCell("Nature Caution");
        table.addCell("Date Emission");
        table.addCell("Date Écheance");
        table.addCell("Montant caution");
        table.addCell("Documents à fournir");
        table.addCell("Banque");
        table.addCell("Ref Engagement Banque");
        table.addCell("Agence Bénéficiaire");
        table.addCell("Apporteur D'affaires");

        for (final AppelOffre appelOffre : appelOffres) {
            List<Caution> cautions = appelOffre.getCautions();
            System.out.println("taille cautions = " + cautions.size());
            for (Caution caution : cautions) {
                table.addCell(caution.getNumero());
                table.addCell("Pas de N° Client");
                table.addCell(appelOffre.getNumero());
                table.addCell(appelOffre.getTrueDate(appelOffre.getDateDepot()));
                table.addCell(appelOffre.getMaitreDouvrage());
                table.addCell(caution.getReferenceMarche());
                table.addCell(appelOffre.getIntitule());
                table.addCell(appelOffre.getDelaiDeValidite());
                table.addCell(caution.getMontantMarche() + "");
                table.addCell("Non fixé");
                table.addCell("Non fixé");
                table.addCell("Non fixé");
                table.addCell(caution.getTypeCaution().getNom());
                table.addCell(caution.getDateDebut().toString());
                table.addCell(caution.getDateFin().toString());
                table.addCell(caution.getMontant() + "");
                table.addCell("Pas de Documents");
                table.addCell(caution.getBanque().getLibelle());
                table.addCell("Pas de ref");
                table.addCell(appelOffre.getFiliale().getNom());
                table.addCell(caution.getCommercial().getUser().getNom());
            }
        }
//        document.left(100f);
//        document.top(150f);
        table.setTotalWidth(PageSize.A4.getWidth());
        document.add(table);
    }

    private void buildPdfDocumentForAppelOffre(final Map<String, Object> model,
            Document document, final PdfWriter writer,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {
        final AppelOffre appelOffre = (AppelOffre) model.get("appelOffre");

        PdfPTable mainTable = new PdfPTable(1);
        mainTable = this.buildStyle(mainTable);

        final PdfPTable entete = new PdfPTable(5);

        entete.addCell("Numéro");
        entete.addCell("Intitulé");
        entete.addCell("Date de dépôt");
        entete.addCell("Filiale");
        entete.addCell("Maitre d'ouvrage");

        entete.addCell(appelOffre.getNumero());
        entete.addCell(appelOffre.getIntitule());
        entete.addCell(appelOffre.getDateDepot() + "");
        entete.addCell(appelOffre.getFiliale().getNom());
        entete.addCell(appelOffre.getMaitreDouvrage());

        mainTable.addCell(entete);
        mainTable.addCell("");
        mainTable.addCell("");
        mainTable.addCell("");

        final PdfPTable tableMateriel = new PdfPTable(4);
        tableMateriel.addCell("Code du Matériel");
        tableMateriel.addCell("Nom du Materiel");
        tableMateriel.addCell("Prix Unitaire");
        tableMateriel.addCell("Quantite");

        for (LigneAppel ligneAppel : appelOffre.getLigneAppels()) {
            tableMateriel.addCell(ligneAppel.getMateriel().getCode());
            tableMateriel.addCell(ligneAppel.getMateriel().getNom());
            tableMateriel.addCell(ligneAppel.getPrixUnitaire() + "");
            tableMateriel.addCell(ligneAppel.getQuantite() + "");
        }

        mainTable.addCell(tableMateriel);
        mainTable.completeRow();
//        mainTable.addCell("");
//        mainTable.addCell("");
//        mainTable.addCell("");

        if (appelOffre.getCautions().size() > 0) {
            // we declare headers
            final PdfPTable tableCaution = new PdfPTable(6);
            tableCaution.setWidthPercentage(MAX_VALUE);
            tableCaution.addCell("Numéro");
            tableCaution.addCell("Type de Caution");
            tableCaution.addCell("Banque");
            tableCaution.addCell("Montant");
            tableCaution.addCell("Date début");
            tableCaution.addCell("Date Fin");

            //Now we fill this table
            for (Caution caution : appelOffre.getCautions()) {
                tableCaution.addCell(caution.getNumero());
                tableCaution.addCell(caution.getTypeCaution().getNom());
                tableCaution.addCell(caution.getBanque().getLibelle());
                tableCaution.addCell(caution.getMontant() + "");
                tableCaution.addCell(caution.getDateDebut().toString());
                tableCaution.addCell(caution.getDateFin().toString());
            }

            mainTable.addCell(tableCaution);
        }
        else {
            mainTable.addCell("Aucune Caution enregistrée pour cet Appel d'Offre");
        }

        document.add(mainTable);

    }

    private PdfPTable buildStyle(final PdfPTable table)
    {
//        table.getDefaultCell().
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(WHITE.brighter());
        table.getDefaultCell().setBorderColor(BLACK.brighter());
        table.getDefaultCell().setBorderWidth(1);
        return table;
    }

    public PdfView()
    {
        this.setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent()
    {
        return true;
    }

}
