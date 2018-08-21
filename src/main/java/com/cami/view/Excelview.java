package com.cami.view;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.LigneAppel;
import com.cami.persistence.model.TypeMateriel;
import static java.lang.Boolean.TRUE;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class Excelview
        extends AbstractExcelView {

    public HSSFCellStyle getRedStyle(final HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        style.setFont(font);
        style.setFillBackgroundColor(HSSFColor.RED.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        style.setShrinkToFit(true);
        return style;
    }

    @Override
    protected void buildExcelDocument(final Map<String, Object> model,
            final HSSFWorkbook workbook, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        if (model.get("mesTypeMateriels") != null) {
            buildExcelDocumentForTypeMateriel(model, workbook, request, response);
        }
        if (model.get("appelOffre") != null) {
            buildExcelDocumentForAppelOfffre(model, workbook, request, response);
        }
        if (model.get("cautionsReport") != null) {
            System.out.println("building the excel document");
            buildExcelDocumentForCautions(model, workbook, request, response);
        }
    }

    public CellStyle getCellStyle(final HSSFWorkbook workbook) {
        final CellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setShrinkToFit(TRUE);
        style.setWrapText(true);
        return style;
    }

    public HSSFCellStyle getHSSFCellStyle(final HSSFWorkbook workbook, final HSSFSheet hSSFSheet) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        style.setShrinkToFit(true);
        return style;
    }

    protected void buildExcelDocumentForAppelOfffre(final Map<String, Object> model,
            final HSSFWorkbook workbook, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        final HSSFSheet sheet = workbook.createSheet("sheet 1");

        final AppelOffre appelOffre = (AppelOffre) model.get("appelOffre");
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;

        // Create header cells
        row = sheet.createRow(rowCount++);
        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Numéro");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Intitulé");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Date de Dépôt");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Date Limite");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Filiale");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Maitre d'ouvrage");

        // First Line Header & Datas
        row = sheet.createRow(rowCount++);
        colCount = 0;
        row.createCell(colCount).setCellValue(appelOffre.getNumero());
        colCount++;
        row.createCell(colCount).setCellValue(appelOffre.getIntitule());
        colCount++;
        row.createCell(colCount).setCellValue(appelOffre.getTrueDate(appelOffre.getDateDepot()));
        colCount++;
        row.createCell(colCount).setCellValue(appelOffre.getDelaiDeValidite());
        colCount++;
        row.createCell(colCount).setCellValue(appelOffre.getFiliale().getNom());
        colCount++;
        row.createCell(colCount).setCellValue(appelOffre.getMaitreDouvrage());
        colCount++;

        row = sheet.createRow(rowCount + 3);
        row.createCell(colCount).setCellValue("Liste du Materiel");
        row = sheet.createRow(rowCount++);
        colCount = 0;

        // Headers for Materiel within this Appel d'offre
        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Code du Matériel");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Nom du Matériel");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Prix Unitaire");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Quantité");

        for (LigneAppel ligneAppel : appelOffre.getLigneAppels()) {
            row = sheet.createRow(rowCount++);
            colCount = 0;
            row.createCell(colCount).setCellValue(ligneAppel.getMateriel().getCode());
            colCount++;
            row.createCell(colCount).setCellValue(ligneAppel.getMateriel().getNom());
            colCount++;
            row.createCell(colCount).setCellValue(ligneAppel.getPrixUnitaire());
            colCount++;
            row.createCell(colCount).setCellValue(ligneAppel.getQuantite());
            colCount++;
        }

        // *************************************//
        row = sheet.createRow(rowCount + 3);
        row.createCell(colCount).setCellValue("Liste des Cautions");
        row = sheet.createRow(rowCount++);

        if (appelOffre.getCautions().size() > 0) {
            colCount = 0;
            // Headers for Cautions within this Appel d'offre
            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Reference Marché");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Montant Marché");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Commercial");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Numéro");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Type Caution");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Banque");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Montant");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Date début");

            cell = row.createCell(colCount++);
            cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
            cell.setCellValue("Date Fin");

            for (Caution caution : appelOffre.getCautions()) {
                row = sheet.createRow(rowCount++);
                colCount = 0;
                row.createCell(colCount).setCellValue(caution.getReferenceMarche());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getMontantMarche());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getCommercial().getUser().getNom());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getNumero());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getTypeCaution().getNom());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getBanque().getLibelle());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getMontant());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getDateDebut().toString());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getDateFin().toString());
            }

        } else {
            colCount = 0;
            row.createCell(colCount).setCellValue("Aucune caution pour cet appel d'offre");
        }
        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i, true);
        }

    }

    protected void buildExcelDocumentForCautions(final Map<String, Object> model,
            final HSSFWorkbook workbook, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {

        System.out.println("building the excel document method .. ");
        final HSSFSheet sheet = workbook.createSheet("sheet 1");
        sheet.setVerticallyCenter(true);

        final List<AppelOffre> appelOffres = (List<AppelOffre>) model.get("cautionsReport");
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;

        // Create header cells
        row = sheet.createRow(rowCount++);
        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("N° Dossier VN"); // Numéro caution

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("N° cpte Client");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("N° A.O"); // Numero appel d'offre

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Date remise A.O"); //date depot appel d'offre

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Bénéficiaire"); // Maitre d'ouvrage

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Reférence du Marché"); // intitulé caution

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Objet de l'A.O"); // Intitulé appel offre

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Delai validité des AO");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Montant Marché");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Delai reception Provisoire");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Delai reception Définitif");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Delai retenue de Gtie");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Nature Caution"); // Type de caution

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Date Emission"); // Date debut caution

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Date Échéance"); // Date fin caution

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Montant Caution"); // Montant Caution

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Document à obtenir pour apurer la caution");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Banque"); // Banque

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Réf Engagement Banque");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Agence Bénéficiaire"); // Filiale

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getHSSFCellStyle(workbook, sheet));
        cell.setCellValue("Apporteur d'affaires"); // Commercial

        // Fill Datas in excel cells
        for (AppelOffre appelOffre : appelOffres) {
            List<Caution> cautions = appelOffre.getCautions();
            for (Caution caution : cautions) {
                row = sheet.createRow(rowCount++);
                if (caution.getDateFin().before(new Date()) | caution.getDateFin().equals(new Date())) {
                    row.setRowStyle(this.getRedStyle(workbook));
                    row.createCell(colCount).setCellStyle(this.getRedStyle(workbook));
                }

                colCount = 0;

                row.createCell(colCount).setCellValue(caution.getNumero());
                colCount++;
                row.createCell(colCount).setCellValue("Pas de N° Client");
                colCount++;
                row.createCell(colCount).setCellValue(appelOffre.getNumero());
                colCount++;
                row.createCell(colCount).setCellValue(appelOffre.getTrueDate(appelOffre.getDateDepot()));
                colCount++;
                row.createCell(colCount).setCellValue(appelOffre.getMaitreDouvrage());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getReferenceMarche());
                colCount++;
                row.createCell(colCount).setCellValue(appelOffre.getIntitule());
                colCount++;
                row.createCell(colCount).setCellValue(appelOffre.getDelaiDeValidite());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getMontantMarche());
                colCount++;
                row.createCell(colCount).setCellValue("Non fixé");
                colCount++;
                row.createCell(colCount).setCellValue("Non fixé");
                colCount++;
                row.createCell(colCount).setCellValue("Non fixé");
                colCount++;
                row.createCell(colCount).setCellValue(caution.getTypeCaution().getNom());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getTrueDate(caution.getDateDebut()));
                colCount++;
                row.createCell(colCount).setCellValue(caution.getTrueDate(caution.getDateFin()));
                colCount++;
                row.createCell(colCount).setCellValue(caution.getMontant());
                colCount++;
                row.createCell(colCount).setCellValue("Pas de Documents");
                colCount++;
                row.createCell(colCount).setCellValue(caution.getBanque().getLibelle());
                colCount++;
                row.createCell(colCount).setCellValue("Pas de ref");
                colCount++;
                row.createCell(colCount).setCellValue(appelOffre.getFiliale().getNom());
                colCount++;
                row.createCell(colCount).setCellValue(caution.getCommercial().getUser().getNom());
            }

        }

        for (int i = 0; i < 27; i++) {
            sheet.autoSizeColumn(i, true);
        }

        sheet.setFitToPage(true);
    }

    protected void buildExcelDocumentForTypeMateriel(final Map<String, Object> model,
            final HSSFWorkbook workbook, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {

        final List<TypeMateriel> materiels = (List<TypeMateriel>) model
                .get("mesTypeMateriels");
        final Sheet sheet = workbook.createSheet("sheet 1");
        final CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setShrinkToFit(TRUE);
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;

        // Create header cells
        row = sheet.createRow(rowCount++);
        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getCellStyle(workbook));
        cell.setCellValue("id");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getCellStyle(workbook));
        cell.setCellValue("code");

        cell = row.createCell(colCount++);
        cell.setCellStyle(this.getCellStyle(workbook));
        cell.setCellValue("nom");

        // Create data cells
        System.out.println("taille liste to send to excel ="
                + materiels.size());
        for (final TypeMateriel typeMateriel : materiels) {
            row = sheet.createRow(rowCount++);
            colCount = 0;
            row.createCell(colCount).setCellValue(typeMateriel.getId() + "");
            colCount++;
            row.createCell(colCount).setCellValue(typeMateriel.getCode());
            colCount++;
            row.createCell(colCount).setCellValue(typeMateriel.getNom());
        }
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }

}
