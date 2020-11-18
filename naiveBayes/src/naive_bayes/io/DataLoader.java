package naive_bayes.io;

import naive_bayes.DataInstance;
import naive_bayes.attribute.Attribute;
import naive_bayes.attribute.ChoiceAttribute;
import naive_bayes.attribute.NumAttribute;
import naive_bayes.value.ChoiceValue;
import naive_bayes.value.NumValue;
import naive_bayes.value.Value;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;

public class DataLoader {

    private final Map<String, Attribute> attributes;

    private final List<DataInstance> dataInstances;

    public DataLoader(Map<String, Attribute> attributes, List<DataInstance> dataInstances) {
        this.attributes = attributes;
        this.dataInstances = dataInstances;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public List<DataInstance> getDataInstances() {
        return dataInstances;
    }

    public static DataLoader loadData(Sheet excelSheet, Instances instances) {
        Map<String, Attribute> attributes = loadAttributes(excelSheet);
        List<DataInstance> dataInstances = loadDataInstances(attributes, instances);
        return new DataLoader(attributes, dataInstances);
    }

    private static Map<String, Attribute> loadAttributes(Sheet excelSheet) {
        Map<String, Attribute> result = new HashedMap<>();
        Row rowOne = excelSheet.getRow(0);
        Row rowTwo = excelSheet.getRow(1);

        Set<String> trees = new HashSet<>();
        for (int i = 2; i <= excelSheet.getLastRowNum(); i++) {
            trees.add(excelSheet.getRow(i).getCell(0).getStringCellValue());
        }
        result.put("Baumgattung", new ChoiceAttribute(trees));

        for (Cell cell : rowOne) {
            if (cell.getCellType() != CellType.BLANK) {
                if (cell.getStringCellValue().contains("[")) {
                    NumAttribute numeric = new NumAttribute();
                    result.put(cell.getStringCellValue(), numeric);
                } else {
                    String name = cell.getStringCellValue();
                    Set<String> attributes = new HashSet<>();
                    attributes.add(rowTwo.getCell(cell.getColumnIndex()).getStringCellValue());
                    cell = rowOne.getCell(cell.getColumnIndex() + 1);
                    while (cell.getCellType() == CellType.BLANK) {
                        attributes.add(rowTwo.getCell(cell.getColumnIndex()).getStringCellValue());
                        cell = rowOne.getCell(cell.getColumnIndex() + 1);
                    }
                    result.put(name, new ChoiceAttribute(attributes));
                }
            }
        }
        return result;
    }

    private static List<DataInstance> loadDataInstances(Map<String, Attribute> attributes, Instances instances) {
        List<DataInstance> result = new ArrayList<>();

        for (Instance in : instances) {
            Map<String, Value> dataInstances = new HashMap<>();
            //int counter = 0;
            for (int i = 0; i < in.numAttributes(); i++) {
                weka.core.Attribute att = in.attribute(i);
                if (att.isNumeric()) {
                    double value = in.value(i);
                    NumValue num = new NumValue(value);
                    dataInstances.put(att.name(), num);
                    NumAttribute numAttribute = (NumAttribute) attributes.get(att.name());
                    numAttribute.setMax(num.getValue());
                    numAttribute.setMin(num.getValue());
                } else {
                    ChoiceValue choice = new ChoiceValue(in.stringValue(i));
                    dataInstances.put(att.name(), choice);
                }
            }
        result.add(new DataInstance(dataInstances));
    }
        return result;
}
}


