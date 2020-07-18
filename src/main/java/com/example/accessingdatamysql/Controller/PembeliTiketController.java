package com.example.accessingdatamysql.Controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.example.accessingdatamysql.Model.PembeliTiket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/spring-java")
public class PembeliTiketController {
    @Autowired
    private PembeliTiketRepository pembeliTiketRepository;
    @Autowired
    private TiketController tiketController;

    @GetMapping(path = "/add")
    public String showForm(Model model) {
        model.addAttribute("pembeli", new PembeliTiket());
        return "index";
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewPembeli(@ModelAttribute PembeliTiket pembeli) throws IOException {
        // PembeliTiket pembeli = new PembeliTiket();
        // pembeli.setNamaPembeli(name);
        // pembeli.setIdKonser(idKonser);
        // pembeli.setIdKelas(idKelas);
        if (tiketController.checkJumlahTiket(pembeli.getIdKonser(), pembeli.getIdKelas()) > 0) {
            pembeliTiketRepository.save(pembeli);
            return "Saved";
        } else {
            // boolean finish = false;
            // String ports[] = {"9001","9002"};
            // int i = 0;
            // while (!finish) {
            //     String urlParameters = "idKonser=" + pembeli.getIdKonser() + "&idKelas=" + pembeli.getIdKelas()
            //         + "&namaPembeli=" + pembeli.getNamaPembeli();
            //     byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            //     int postDataLength = postData.length;
            //     String request = "http://localhost:"+ports[i]+"/spring-java/api/add";
            //     URL url;
            //     try {
            //         url = new URL(request);
            //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //         conn.setDoOutput(true);
            //         conn.setInstanceFollowRedirects(false);
            //         conn.setRequestMethod("POST");
            //         conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //         conn.setRequestProperty("charset", "utf-8");
            //         conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            //         conn.setUseCaches(false);
            //         try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            //             wr.write(postData);
            //         }
            //         if (conn.getResponseMessage().equals("Saved")) {
            //             finish = true;
            //             return conn.getResponseMessage() + "from port : " + ports[i];
            //         } else if ((conn.getResponseCode() != HttpURLConnection.HTTP_OK || conn.getResponseMessage().equals("Jumlah tiket kurang"))
            //             && i < 1) {
            //             i += 1;
            //         } else {
            //             finish = true;
            //             return conn.getResponseMessage();
            //         }
            //     } catch (MalformedURLException e) {
            //         // TODO Auto-generated catch block
            //         e.printStackTrace();
            //     } catch (ProtocolException e) {
            //         // TODO Auto-generated catch block
            //         e.printStackTrace();
            //     }
            // }
            return "Tiket kurang";
        }
        // return null;
    }

    @PostMapping(path="api/add")
    public @ResponseBody String addNewPembeli (@RequestParam int idKonser, @RequestParam int idKelas,
        @RequestParam String namaPembeli) {

        PembeliTiket pembeli = new PembeliTiket();
        pembeli.setNamaPembeli(namaPembeli);
        pembeli.setIdKonser(idKonser);
        pembeli.setIdKelas(idKelas);
        
        if (tiketController.checkJumlahTiket(pembeli.getIdKonser(), pembeli.getIdKelas()) > 0) {
            pembeliTiketRepository.save(pembeli);
            return "Saved";
        } else {
            return "Jumlah tiket kurang";
        }
    }

}