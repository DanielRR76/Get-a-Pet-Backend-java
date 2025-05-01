package com.danfy.get_a_pet.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.danfy.get_a_pet.domain.services.IMediaService;
import com.danfy.get_a_pet.dtos.FileDTO;
import com.danfy.get_a_pet.exceptions.media.ErrorDeleteFileException;
import com.danfy.get_a_pet.exceptions.media.ErrorUploadFileException;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class CloudinaryService implements IMediaService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(FileDTO file) {
        String url;
        try {
            var uploadResult = cloudinary.uploader().upload(
                    file.file().getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "folder", file.folder(),
                            "public_id", UUID.randomUUID().toString()
                    )
            );
            url = (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new ErrorUploadFileException(e.getMessage());
        }
        return url;
    }
    @Override
    public List<String> uploadFiles(List<FileDTO> files) {
        List<String> urls = new ArrayList<>();
        for (FileDTO file : files) {
            try {
                var uploadResult = cloudinary.uploader().upload(
                        file.file().getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", "image",
                                "folder", file.folder(),
                                "public_id", UUID.randomUUID().toString()
                        )
                );
                urls.add((String) uploadResult.get("secure_url"));
            } catch (IOException e) {
                throw new ErrorUploadFileException(e.getMessage());
            }

        }
        return urls;
    }

    @Override
    public void deleteFile(String url) {
        String[] parts = url.split("/");
        String public_id = parts[parts.length - 2] + "/" + parts[parts.length - 1].substring(0,parts[parts.length - 1].lastIndexOf("."));
        try {
            cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ErrorDeleteFileException(e.getMessage());
        }
    }



    @Override
    public void deleteFiles(List<String> urls) {
        for (String url : urls) {
            String[] parts = url.split("/");
            String public_id = parts[parts.length - 2] + "/" + parts[parts.length - 1].substring(0,parts[parts.length - 1].lastIndexOf("."));
            try {
                cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
            } catch (IOException e) {
                throw new ErrorDeleteFileException(e.getMessage());
            }
        }
    }

    @Override
    public String getFile(FileDTO fileToUpload, String file) {
        String fileUrl = file;
        if(fileUrl != null) {
            this.deleteFile(fileUrl);
        }
        return this.uploadFile(fileToUpload);
    }
    @Override
    public String getFile(FileDTO fileToUpload) {
        return this.uploadFile(fileToUpload);
    }
    @Override
    public List<String> getFiles(List<FileDTO> filesToUpload, List<String> files) {
        List<String> fileUrls = (files != null) ? files : new ArrayList<>();
        if(fileUrls.size() > 0) {
            this.deleteFiles(fileUrls);
        }
        return this.uploadFiles(filesToUpload);
    }


    @Override
    public List<String> getFiles(List<FileDTO> filesToUpload) {
        return this.uploadFiles(filesToUpload);
    }
}
