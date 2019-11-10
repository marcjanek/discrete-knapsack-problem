package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Settings.
 */
@Getter @Setter
public class Settings extends FileGetter{
    private final List<Setting> settingList;
    public Settings(final long initialPopulation){
        this.settingList = new ArrayList<>();
        settingList.add(new Setting("initialPopulation",initialPopulation));
        settingList.add(new Setting("chromosomePerMille", 100D));//‰ * 1000
        settingList.add(new Setting("genChance", calculateGenChance(100D)));//0 - 1
        settingList.add(new Setting("dominatorPercentage", 10D));//%
        settingList.add(new Setting("iterations", 100L));
        settingList.add(new Setting("generateChart",true));
    }

    public long getInitialPopulation() {
        return (long) getSettingValue("initialPopulation");
    }

    public double getGenChance() {
        return (double) getSettingValue("genChance");
    }

    public double getDominatorPercentage() {
        return (double) getSettingValue("dominatorPercentage");
    }

    public long getIterations() {
        return (long) getSettingValue("iterations");
    }

    public boolean getGenerateChart() {
        return (boolean) getSettingValue("generateChart");
    }
    public void setSetting(String key,Object value){
        List<Setting> collect = this.settingList.stream().filter(e -> e.getName().equals(key)).collect(Collectors.toList());
        if(collect.size() == 1){
            collect.get(0).setValue(value);
        }
    }

    /**
     * Init data from file.
     *
     * @param inputPath the input path
     */
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

    private double calculateGenProbability(double chromosomePerMille) {
        double x = getInitialPopulation();
        return 1 - Math.pow(1 - chromosomePerMille / 1000, 1 / x);
    }
    private void setValue(String key, String value){
        switch (key){
            case "genProbability"://double //TODO można wywalić możliwość wprowadzenia(jak zbyt skomplikowane to zostawić i nie mówić)
            case "dominatorPercentage": {
                if (!NumberUtils.isParsable(value))
                    return;
                double val = Double.parseDouble(value);
                if (val < 0)
                    return;
                setSetting(key, val);
            }
            break;
            case "chromosomePerMille":{
                if(!NumberUtils.isParsable(value))
                    return;
                double val = Double.parseDouble(value);
                if(val < 0)
                    return;
                setSetting(key, val);
                setSetting("chromosomePerMille", calculateGenProbability(val));
            }
            break;
            case "iterations"://long
            case "initialPopulation": {
                if(isLongParsable(value)){
                    long l = Long.parseLong(value);
                    if(l>=0){
                        setSetting(key, l);
                    }
                }
            }
            break;
            case "generateChart"://boolean
            {
                if(isBooleanParsable(value)){
                    setSetting(key,Boolean.parseBoolean(value));
                }
            }
            break;
        }
    }
    private boolean isBooleanParsable(String value){
        try{
            Boolean.parseBoolean(value);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private boolean isLongParsable(String value){
        try{
            Long.parseLong(value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private Object getSettingValue(String key){
        List<Setting> collect = this.settingList.stream().filter(e -> e.getName().equals(key)).collect(Collectors.toList());
        if(collect.size() == 1){
            return collect.get(0).getValue();
        }
        return null;
    }
}
