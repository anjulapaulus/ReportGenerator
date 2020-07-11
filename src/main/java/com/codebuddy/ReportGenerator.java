package com.codebuddy;

import com.codebuddy.util.NumberToWordsConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReportGenerator {
    static final String inFile = "./src/main/java/com/codebuddy/reports/Manifest.jrxml";

    public void GenerateManifestReport(String reference, String vessel_voyage, String eta, String port_of_loading,
                                       String port_of_discharge,String serial_num,String master_shipper,String notify_party,
                                       String consignee,String file_num, String agent_name, String agent_address,String agent_telephone,
                                       String hbl_num,String shipper_address,String notify_party_address,String consigneeAddress,
                                       String marks_num, String description, String gross_weight, String net_weight,String total_cbm,String total_packages,String mbl_num){
        ContainerBeanList DataBeanList = new ContainerBeanList();
        ArrayList<ContainerBean> dataList = DataBeanList.getDataBeanList(reference);
        String outFile = reference.replaceAll("/","_")+"_manifest"+".pdf";

        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);
        HashMap param = new HashMap();

        param.put("referenceNum", reference);
        param.put("vessel_voyage", vessel_voyage);
        param.put("eta", eta);
        param.put("port_of_loading", port_of_loading);
        param.put("port_of_discharge", port_of_discharge);
        param.put("serial_number", serial_num);
        param.put("master_shipper", master_shipper);
        param.put("file_num", file_num);
        param.put("agent", agent_name+"\n"+ agent_address +"\n"+agent_telephone);
        param.put("master_bl_num", mbl_num);
        param.put("hbl_num", hbl_num);
        param.put("shipper", master_shipper+"\n"+shipper_address);
        param.put("consignee",consignee+"\n"+consigneeAddress);
        param.put("notify_party", notify_party+"\n"+notify_party_address);
        param.put("marks_num", marks_num);
        param.put("gross_weight", gross_weight);
        param.put("description_cargo", description);
        param.put("net_weight", net_weight);
        param.put("total_cbm", total_cbm);
        param.put("total_packages", total_packages);

        JasperReport jasperDesign = null;
        try {
            jasperDesign = JasperCompileManager.compileReport(inFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, param,
                    beanColDataSource);

            File file = new File(outFile);
            OutputStream outputSteam = new FileOutputStream(file);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);
            JRViewer jrViewer= new JRViewer(jasperPrint);
            JFrame jf = new JFrame();
            jf.getContentPane().add(jrViewer);
            jf.validate();
            jf.setVisible(true);
//            JasperPrintManager.printReport(jasperPrint,true);

            System.out.println("Report Generated!");
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void GenerateDO(String reference, String vessel,String voayge, String eta, String port_of_loading,
                           String port_of_discharge,String serial_num,String master_shipper,String notify_party,
                           String consignee,String file_num, String agent_name, String agent_address,String agent_telephone,
                           String hbl_num,String shipper_address,String notify_party_address,String consigneeAddress,
                           String marks_num, String description, String gross_weight, String net_weight,String total_cbm,String total_packages,String mbl_num,String do_expiry,String package_type
    ){
        ContainerBeanList DataBeanList = new ContainerBeanList();
        ArrayList<ContainerBean> dataList = DataBeanList.getDataBeanList(reference);
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        int twenty = 0;
        int fourty = 0;
        int over_forty = 0;
        HashMap param1 = new HashMap();
        param1.put("do_num", reference);
        param1.put("serial_num", serial_num);
        param1.put("bl_num", mbl_num);
        param1.put("agent_telephone", agent_telephone);
        param1.put("agent", agent_name+"\n"+agent_address);

        DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
        // parse the date string into Date object
        Date date = null;
        try {
            date = srcDf.parse(do_expiry);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat destDf = new SimpleDateFormat("yy-MM-dd");

        // format the date into another format
        String dateStr = destDf.format(date);
        param1.put("do_expiry", dateStr.replaceAll("-",""));
        param1.put("consignee", consignee+"\n"+consigneeAddress);
        param1.put("voyage_num", voayge);
        param1.put("date", eta);
        param1.put("vessel", vessel);
        param1.put("port_of_loading", port_of_loading);
        param1.put("marks_num", marks_num);
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            text.append(dataList.get(i).getContainerNum()).append("\n");
        }
        param1.put("containerNum",text.toString());
        param1.put("package_type", package_type);
        param1.put("description", description);
        param1.put("gross_weight", gross_weight);
        param1.put("cbm", total_cbm);
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getSize().equals("20") && dataList.get(i).getStatus().equals("FCL/FCL")){
                twenty++;
            }else if(dataList.get(i).getSize().equals("40") && dataList.get(i).getStatus().equals("FCL/FCL") ){
                fourty++;
            }else if(dataList.get(i).getSize().equals("OVER 40") && dataList.get(i).getStatus().equals("FCL/FCL")){
                over_forty++;
            }
        }
        param1.put("20ft", Integer.toString(twenty)); //Have to fix
        param1.put("40ft", Integer.toString(fourty)); //Have to fix
        param1.put("over_40ft", Integer.toString(over_forty)); //Have to fix
        param1.put("package_figures", total_packages);
        param1.put("packages_in_words", NumberToWordsConverter.convert(Integer.parseInt(total_packages))+" ONLY"); //Have to fix

        final String inFilea = "./src/main/java/com/codebuddy/reports/Delivery_Order.jrxml";
        String outFilea = reference.replaceAll("/","_")+"_do"+".pdf";
        JasperReport jasperDesign = null;
        try {
            jasperDesign = JasperCompileManager.compileReport(inFilea);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign,param1,new JREmptyDataSource());

            File file = new File(outFilea);
            OutputStream outputSteam = new FileOutputStream(file);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);


            System.out.println("Report Generated!");
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
