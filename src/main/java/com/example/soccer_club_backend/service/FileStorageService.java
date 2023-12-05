package com.example.soccer_club_backend.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            String directoryPath = projectDirectory + "/res/";
//            String directoryPath = projectDirectory + "/src/main/resources/static/res/";

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
//                return generatedFileName; // Здесь указывается базовый URL вашего бэкенда и путь к файлу
            }else
                throw new IllegalArgumentException("Файл повинен бути з розширенням .jpg .png ");
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при загрузке файла.";
        }
    }

    public ResponseEntity<byte[]> readFile(String fileName) {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String directoryPath = projectDirectory + "/res/";

            Path filePath = Paths.get(directoryPath, fileName);

            FileSystemResource fileResource = new FileSystemResource(filePath.toFile());

            // Проверяем, существует ли файл
            if (fileResource.exists()) {
                byte[] fileContent = Files.readAllBytes(filePath);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .contentType(MediaType.IMAGE_JPEG)  // Замените на соответствующий MediaType вашему типу файла
                        .body(fileContent);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // Обработка ошибок загрузки файла
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private String generateRandomString(int length) {
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
            String directoryPath = projectDirectory + "/res/";
//            String directoryPath = projectDirectory + "/src/main/resources/static/res/";

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
