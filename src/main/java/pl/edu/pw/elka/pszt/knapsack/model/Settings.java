package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileNotFoundException;
import java.util.Objects;

@Getter @Setter
public class Settings extends FileGetter{
    private double initialPopulation = 0;
    private double probability = 30;
    private double dominatorPercentage = 90;
    private double iterations = 100;
    private double generateChart = 1;

    public void initDataFromFile(String inputPath){
        if (Objects.isNull(inputPath))
            return;
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

    public void setInitialPopulationIfZero(int initialPopulation) {
        if (initialPopulation == 0) {
            this.initialPopulation = initialPopulation;
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
            case "initialPopulation":
                initialPopulation = val;
                break;
            case "generateChart":
                generateChart = val;
                break;
        }
    }
}
