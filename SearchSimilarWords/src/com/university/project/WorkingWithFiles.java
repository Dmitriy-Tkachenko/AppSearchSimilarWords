package com.university.project;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class WorkingWithFiles {
    public void importFile(JFrame frame, JTextArea jTextPane, JLabel nameFile) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            String file = fileChooser.getSelectedFile().getName();
            String[] splitData = file.split("\\.");
            String text;

            if (splitData.length > 0) {
                if (splitData[1].equalsIgnoreCase("doc")) {
                    text = readDocFile(file);
                } else if (splitData[1].equalsIgnoreCase("docx")) {
                    text = readDocXFile(file);
                } else if (splitData[1].equalsIgnoreCase("txt")) {
                    text = readTxtFile(file);
                } else {
                    JOptionPane.showMessageDialog(frame, "Файл не поддерживается!");
                    return;
                }
                jTextPane.setText(text);
                nameFile.setText(file);
            }
        }
    }

    private String readDocFile(String file) {
        String text = null;
        try (HWPFDocument document = new HWPFDocument(new FileInputStream(file));
             WordExtractor extractor = new WordExtractor(document)) {
            text = extractor.getText();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    private String readDocXFile(String file) {
        StringBuilder text = new StringBuilder();
        XWPFDocument document;
        try {
            document = new XWPFDocument(new FileInputStream(file));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                text.append(para.getText()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(text);
    }

    private String readTxtFile(String file) {
        StringBuilder text;
        text = new StringBuilder();
        int c;
        try (FileReader reader = new FileReader(file)) {
            while((c = reader.read()) != -1){
                text.append((char) c);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return String.valueOf(text);
    }
}
