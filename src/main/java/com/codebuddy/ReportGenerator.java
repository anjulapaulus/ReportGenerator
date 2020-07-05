package com.codebuddy;

import net.sf.jasperreports.engine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class ReportGenerator {
    static final String fileName = "reports/Manifest.jrxml";
    static final String outFile = "Manifest.pdf";

    public void GenerateManifestReport(){
        HashMap<String, Object> param = new HashMap<>();

        param.put("title", "Manifest");
//        param.put("studentDataSource", studentCollectionDataSource);
//        param.put("teacherDataSource", teacherCollectionDataSource);

        JasperReport jasperDesign = null;
        try {
            jasperDesign = JasperCompileManager.compileReport(fileName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, param,
                    new JREmptyDataSource());

            File file = new File(outFile);
            OutputStream outputSteam = new FileOutputStream(file);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);

            System.out.println("Report Generated!");
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
