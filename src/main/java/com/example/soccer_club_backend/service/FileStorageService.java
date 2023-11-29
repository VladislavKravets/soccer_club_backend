package com.example.soccer_club_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class FileStorageService {

    //    @Value("${file.upload.directory}")
//    private String uploadDirectory;
    private static final Random random = new Random();

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String projectDirectory = System.getProperty("user.dir");
            String directoryPath = projectDirectory + "/src/main/resources/static/res/";

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
            System.out.println(fileExtension);
            if(fileExtension.equals(".jpg") || fileExtension.equals(".png")){
                String generatedFileName = generateRandomString(15) + fileExtension;
                String filePath = directoryPath + generatedFileName;
                File dest = new File(filePath);
                file.transferTo(dest);

                return "http://localhost:8080/res/" + generatedFileName; // Здесь указывается базовый URL вашего бэкенда и путь к файлу
            }else
                throw new IllegalArgumentException("Файл повинен бути з розширенням .jpg .png ");
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при загрузке файла.";
        }
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    // Метод для удаления файла
    public boolean deleteFile(String fileUrl) {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String directoryPath = projectDirectory + "/src/main/resources/static/res/";

            // Извлекаем имя файла из URL
            String fileName = fileUrl.replace("http://localhost:8080/res/", "");
            String filePath = directoryPath + fileName;

            File fileToDelete = new File(filePath);

            // Проверяем, существует ли файл перед удалением
            if (fileToDelete.exists()) {
                return fileToDelete.delete(); // Возвращает true, если файл успешно удален, иначе false
            } else {
                System.out.println("Файл не существует.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
