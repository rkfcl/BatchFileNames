package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 사용자로부터 디렉토리 경로 입력받기
        Scanner scanner = new Scanner(System.in);
        System.out.print("디렉토리 경로를 입력하세요: ");
        String directoryPath = scanner.nextLine();

        // 사용자로부터 변경하고자 하는 파일명 입력받기
        System.out.print("변경하고자 하는 파일명을 입력하세요: ");
        String baseFileName = scanner.nextLine();

        // 사용자로부터 제외하고 싶은 확장자들 입력받기 (콤마(,)로 구분)
        System.out.print("제외하고 싶은 확장자들을 입력하세요 (예: txt,pdf): ");
        String excludedExtensionsInput = scanner.nextLine();
        scanner.close(); // 스캐너 닫기

        // 디렉토리 객체 생성
        File directory = new File(directoryPath);

        // 디렉토리가 존재하는지 확인
        if (!directory.exists()) {
            System.out.println("디렉토리가 존재하지 않습니다.");
            return;
        }

        // 입력받은 확장자들을 콤마(,)로 분리
        String[] excludedExtensions = excludedExtensionsInput.split(",");

        // 디렉토리 안의 파일 목록 가져오기
        File[] files = directory.listFiles();

        // 파일 이름 변경
        int count = 1;
        for (File file : files) {
            if (file.isFile()) { // 파일인 경우에만 처리
                String fileName = file.getName();
                String extension = getFileExtension(fileName);

                // 제외하고 싶은 확장자가 아닌 경우에만 처리
                if (!isExcludedExtension(extension, excludedExtensions)) {
                    String newName = baseFileName + "_" + count + extension;
                    File newFile = new File(directory, newName);
                    if (file.renameTo(newFile)) {
                        System.out.println("파일 이름 변경 성공: " + fileName + " -> " + newName);
                    } else {
                        System.out.println("파일 이름 변경 실패: " + fileName);
                    }
                    count++;
                }
            }
        }
    }

    // 파일의 확장자를 가져오는 메서드
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    // 확장자가 제외할 확장자 목록에 포함되어 있는지 확인하는 메서드
    private static boolean isExcludedExtension(String extension, String[] excludedExtensions) {
        for (String excludedExtension : excludedExtensions) {
            if (extension.equalsIgnoreCase("." + excludedExtension.trim())) {
                return true;
            }
        }
        return false;
    }
}

