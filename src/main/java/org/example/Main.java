package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("File Renamer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();

            // 사용자 입력을 받을 커스텀 대화 상자 생성
            CustomDialog dialog = new CustomDialog(frame);
            dialog.setVisible(true);

            String newFileName = dialog.getNewFileName();

            if (newFileName != null && !newFileName.isEmpty()) {
                // 선택한 파일들에 대해 이름 변경 로직을 수행
                for (int i = 0; i < selectedFiles.length; i++) {
                    File file = selectedFiles[i];
                    String extension = getFileExtension(file.getName());
                    String newName = newFileName + "_" + (i + 1) + extension;
                    File newFile = new File(file.getParentFile(), newName);

                    if (file.renameTo(newFile)) {
                        System.out.println("파일 이름 변경 성공: " + file.getName() + " -> " + newName);
                    } else {
                        System.out.println("파일 이름 변경 실패: " + file.getName());
                    }
                }

                JOptionPane.showMessageDialog(frame, "파일 이름 변경이 완료되었습니다.");
                createAndShowGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "새로운 파일명을 입력하세요.");
            }
        }

        frame.dispose();
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}

class CustomDialog extends JDialog {
    private JTextField textField;
    private String newFileName;

    public CustomDialog(JFrame parent) {
        super(parent, "새 파일명 입력", true);
        setLayout(new BorderLayout());

        textField = new JTextField(20);
        JButton okButton = new JButton("확인");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFileName = textField.getText();
                dispose();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("새 파일명: "));
        inputPanel.add(textField);
        inputPanel.add(okButton);

        add(inputPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
    }

    public String getNewFileName() {
        return newFileName;
    }
}
