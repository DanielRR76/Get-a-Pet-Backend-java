package com.danfy.get_a_pet.domain.services;


import com.danfy.get_a_pet.dtos.FileDTO;

import java.util.List;

public interface IMediaService {
    public String uploadFile(FileDTO file);
    public List<String> uploadFiles(List<FileDTO> files);
    public void deleteFile(String url);
    public void deleteFiles(List<String> urls);
    public String getFile (FileDTO fileToUpload, String file);
    public String getFile (FileDTO fileToUpload);
    public List<String> getFiles(List<FileDTO> filesToUpload, List<String> files);
    public List<String> getFiles(List<FileDTO> filesToUpload);
}
