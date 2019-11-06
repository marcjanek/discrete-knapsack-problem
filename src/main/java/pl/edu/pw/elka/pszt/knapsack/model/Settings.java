package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter @Setter
public class Settings {
    private double probability = 100;
    private double dominatorPercentage = 0.9;
    private double iterations = 100;

    public void initDataFromFile(String inputPath){
        String dataFromFile;
        try {
            dataFromFile = getDataFromFile(inputPath);
        } catch (FileNotFoundException ignored) {
            return;
        }
        for (String s : dataFromFile.split("\n")) {
            String[] split = s.split("=");
            if(split.length == 2){
                setValue(split[0].trim(),split[1].trim());
            }
        }
    }
    private void setValue(String key, String value){
        if(!NumberUtils.isParsable(value))
            return;
        double val = Double.parseDouble(value);
        if(val < 0)
            return;
        switch (key){
            case "probability":
                probability = val;
                break;
            case "dominatorPercentage":
                dominatorPercentage = val;
                break;
            case "iterations":
                iterations= val;
                break;
        }
    }

    private String getDataFromFile(String inputPath) throws FileNotFoundException {
        Scanner scanner = new Scanner(getFile(inputPath));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine())
            stringBuilder.append(scanner.nextLine()).append("\n");
        scanner.close();
        return stringBuilder.toString();
    }
    private File getFile(String inputPath){
        return new File(inputPath);
    }
}
