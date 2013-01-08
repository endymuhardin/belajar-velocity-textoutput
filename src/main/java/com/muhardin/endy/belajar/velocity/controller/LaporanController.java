/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muhardin.endy.belajar.velocity.controller;

import com.muhardin.endy.belajar.velocity.domain.Produk;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author endy
 */
@Controller
public class LaporanController {
    
    private List<Produk> dataProduk = new ArrayList<Produk>();
    private VelocityEngine velocityEngine = new VelocityEngine();
    private Template templateDaftarProduk;

    public LaporanController() {
        velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,CommonsLogLogChute.class.getName());
        templateDaftarProduk = velocityEngine.getTemplate("/template-daftar-produk.txt");
        try {
            Produk p1 = new Produk();
            Produk p2 = new Produk();
            Produk p3 = new Produk();
            Produk p4 = new Produk();
            Produk p5 = new Produk();
            
            p1.setHarga(new BigDecimal(5000.00));
            p2.setHarga(new BigDecimal(125000.00));
            p3.setHarga(new BigDecimal(100000000.00));
            p4.setHarga(new BigDecimal(100.00));
            p5.setHarga(new BigDecimal(100000.00));
            
            p1.setKode("P-001");
            p2.setKode("P-002");
            p3.setKode("P-003");
            p4.setKode("P-004");
            p5.setKode("P-005");
            
            p1.setNama("Produk Satu");
            p2.setNama("Produk Dua");
            p3.setNama("Produk Tiga");
            p4.setNama("Produk Empat");
            p5.setNama("Produk Lima");
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            p1.setTanggalKadaluarsa(formatter.parse("2012-01-01"));
            p2.setTanggalKadaluarsa(formatter.parse("2012-02-01"));
            p3.setTanggalKadaluarsa(formatter.parse("2012-03-01"));
            p4.setTanggalKadaluarsa(formatter.parse("2012-04-01"));
            p5.setTanggalKadaluarsa(formatter.parse("2012-05-01"));
            
            dataProduk.add(p1);
            dataProduk.add(p2);
            dataProduk.add(p3);
            dataProduk.add(p4);
            dataProduk.add(p5);
        } catch (ParseException ex) {
            Logger.getLogger(LaporanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @RequestMapping("/produk")
    @ResponseBody
    public String daftarProduk(){
        Locale indonesia = new Locale("id", "ID");
        SimpleDateFormat formatterTanggal = new SimpleDateFormat("EEE, dd MMM yyyy", indonesia);
        SimpleDateFormat formatterWaktu = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", indonesia);
        DecimalFormat formatterAngka = (DecimalFormat) NumberFormat.getInstance(indonesia);
        formatterAngka.applyPattern("#,##0.00");
        
        VelocityContext ctx = new VelocityContext();
        ctx.put("formatterWaktu", formatterWaktu);
        ctx.put("formatterTanggal", formatterTanggal);
        ctx.put("formatterAngka", formatterAngka);
        ctx.put("daftarProduk", dataProduk);
        ctx.put("tanggal", new Date());
        ctx.put("stringUtils", StringUtils.class);
        
        StringWriter output = new StringWriter();
        templateDaftarProduk.merge(ctx, output);
        
        return output.toString();
    }
    
}
