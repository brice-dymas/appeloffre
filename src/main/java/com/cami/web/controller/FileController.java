/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.service.IAppelOffreService;
import com.cami.web.form.FileMeta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author gervais
 */
@Controller
@RequestMapping("/file")
public class FileController {

    LinkedList<FileMeta> files;
    FileMeta fileMeta = null;
    final static String SAVE_DIRECTORY = "documents";

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private IAppelOffreService appelOffreService;

    /**
     * *************************************************
     * URL: /appel-offre/file/upload upload(): receives files
     *
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @param idAppelOffre
     * @return LinkedList<FileMeta> as json format
     * **************************************************
     */
    @RequestMapping(value = "/{idAppelOffre}/upload", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response, @PathVariable String idAppelOffre) {

        //1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        AppelOffre appelOffre = appelOffreService.findOne(Long.valueOf(idAppelOffre));
        int i = 0;
        //2. get each file
        while (itr.hasNext()) {
            System.out.println("i = " + i);
            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());

            System.out.println(mpf.getOriginalFilename() + " uploaded! ");

            //2.2 if files > 10 remove the first from the list
//             if(files.size() >= 10)
//                 files.pop();
            //2.3 create new fileMeta
//             fileMeta = new FileMeta();
//             fileMeta.setFileName(saveName);
//             fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
//             fileMeta.setFileType(mpf.getContentType());
            try {
                //fileMeta.setBytes(mpf.getBytes());

                // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
                String saveName = getFileName(mpf, appelOffre);
                processFileData(mpf, SAVE_DIRECTORY, saveName);
                //FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("/home/gervais/" + saveName));
                appelOffre.addFile(saveName);
                appelOffre = appelOffreService.updateFiles(appelOffre);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //2.4 add to files
            // files.add(fileMeta);
        }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        files = new LinkedList<>();
        for (String file : appelOffre.getFiles()) {
            fileMeta = new FileMeta();
            fileMeta.setFileName(file);
            files.add(fileMeta);
        }
        return files;
    }

    /**
     * *************************************************
     * URL: /appel-offre/file/get/{value} get(): get file as an attachment
     *
     * @param response : passed by the server
     * @param value : value from the URL
     * @return void **************************************************
     */
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        System.out.println(value);
        String savedFileName = getSavedFileName(SAVE_DIRECTORY, value);
        File file = new File(savedFileName);
        Path source = Paths.get(savedFileName);
        FileMeta getFile = new FileMeta();

        try {

            getFile.setFileName(file.getName());
            getFile.setFileSize(Files.size(source) / 1024 + " Kb");
            getFile.setFileType(Files.probeContentType(source));

            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(Files.readAllBytes(source), response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
     @RequestMapping(value = "/remove/{value}", method = RequestMethod.GET)
    public @ResponseBody
    LinkedList<FileMeta> remove(HttpServletResponse response, @PathVariable String value) {
        Long idAppelOffre = Long.valueOf(value.split("_")[0]);
         AppelOffre appelOffre = appelOffreService.deleteFiles(idAppelOffre, value);
        
        files = new LinkedList<>();
        for (String file : appelOffre.getFiles()) {
            fileMeta = new FileMeta();
            fileMeta.setFileName(file);
            files.add(fileMeta);
        }
        return files;
        
    }

    private String getSavedFileName(String uploadDir, String nameOfFile) {
        String webappRoot = servletContext.getRealPath("/");
        String relativeFolder = File.separator + "resources" + File.separator + "bootstrap" + File.separator
                + uploadDir + File.separator;
        String filename = webappRoot + relativeFolder + nameOfFile;

        System.out.println(filename);
        return filename;
    }

    private void processFileData(MultipartFile file, String uploadDir, String nameOfFile)
            throws IllegalStateException,
            IOException {

        String filename = getSavedFileName(uploadDir, nameOfFile);
        file.transferTo(new File(filename));

    }

    private String getFileName(MultipartFile file, AppelOffre appelOffre) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String saveName = appelOffre.getId() + "_" + new Date().getTime() + "." + ext;
        return saveName;

    }

}
