package data_generator;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ProtectedProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        Sheet excelSheet = getExcelSheet();
        List<AttributeTemplate> attributeTemplates = loadAttributeTemplates(excelSheet);
        List<Classification> classifications = loadClassifications(excelSheet, attributeTemplates);
        ArrayList<weka.core.Attribute> wekaAttributes = getWekaAttributes("Baumgattung", classifications);
        int numOfDataSets = 10;
        Instances generatedData = prepareARFF(excelSheet, wekaAttributes, numOfDataSets);
        fillARFF(classifications, generatedData, numOfDataSets);
        System.out.println(generatedData);
    }

    private static Sheet getExcelSheet() throws IOException {
        InputStream catalogStream = Main.class.getResourceAsStream("Laubbaum.xlsx");
        Workbook workbook = WorkbookFactory.create(catalogStream);
        return workbook.getSheetAt(0);
    }

    public static List<AttributeTemplate> loadAttributeTemplates(Sheet excelSheet) {
        List<AttributeTemplate> result = new ArrayList<>();
        Row rowOne = excelSheet.getRow(0);
        Row rowTwo = excelSheet.getRow(1);
        for (Cell cell : rowOne) {
            if (cell.getCellType() != CellType.BLANK) {
                if (cell.getStringCellValue().contains("[")) {
                    NumericAttributeTemplate numeric = new NumericAttributeTemplate(cell.getStringCellValue(), cell.getColumnIndex());
                    result.add(numeric);
                } else {
                    ChoiceAttributeTemplate choice = new ChoiceAttributeTemplate(cell.getStringCellValue(), cell.getColumnIndex(), rowTwo.getCell(cell.getColumnIndex()).getStringCellValue());
                    result.add(choice);
                    cell = rowOne.getCell(cell.getColumnIndex() + 1);
                    while (cell.getCellType() == CellType.BLANK) {
                        choice.addChoiceName(rowTwo.getCell(cell.getColumnIndex()).getStringCellValue());
                        cell = rowOne.getCell(cell.getColumnIndex() + 1);
                    }
                }
            }
        }
        return result;
    }

    public static List<Classification> loadClassifications(Sheet excelSheet, List<AttributeTemplate> attributeTemplates) {
        List<Classification> result = new ArrayList<>();
        for (Row row : excelSheet) {
            if (row.getRowNum() >= 2) {
                List<Attribute> attributes = new ArrayList<>();
                for (AttributeTemplate temp : attributeTemplates) {
                    if (temp instanceof ChoiceAttributeTemplate) {
                        List<ChoiceValue> values = new ArrayList<>();
                        List<String> choiceNames = ((ChoiceAttributeTemplate) temp).getChoiceNames();
                        int counter = 0;
                        for (String choice : choiceNames) {
                            values.add(new ChoiceValue(choice, row.getCell(temp.getStartColumn() + counter).getNumericCellValue()));
                            counter++;
                        }
                        attributes.add(new ChoiceAttribute(temp.getName(), values));
                    } else {
                        double mean = row.getCell(temp.getStartColumn()).getNumericCellValue();
                        double sd = row.getCell(temp.getStartColumn() + 1).getNumericCellValue();
                        attributes.add(new NumericAttribute(temp.getName(), mean, sd));
                    }
                }
                result.add(new Classification(row.getCell(0).getStringCellValue(), attributes));
            }
        }
        return result;
    }

    public static weka.core.Attribute catalogAttributeToWekaAttribute(Attribute anAttribute) {
        weka.core.Attribute result;
        if (anAttribute instanceof ChoiceAttribute) {
            List<ChoiceValue> choices = ((ChoiceAttribute) anAttribute).getPossibleValues();
            List<String> choicesString = new ArrayList<>();
            for (ChoiceValue choice : choices) {
                choicesString.add(choice.getName());
            }
            result = new weka.core.Attribute(anAttribute.getName(), choicesString);
        } else result = new weka.core.Attribute(anAttribute.getName());
        return result;
    }

    public static ArrayList<weka.core.Attribute> getWekaAttributes(String classificationAttributeName, List<Classification> classifications) {
        ArrayList<weka.core.Attribute> result = new ArrayList<>();
        List<String> cNames = new ArrayList<>();
        for (Classification cl : classifications) {
            cNames.add(cl.getName());
        }
        result.add(new weka.core.Attribute(classificationAttributeName, cNames));
        for (Attribute at : classifications.get(0).getAttributes()) {
            result.add(catalogAttributeToWekaAttribute(at));
        }
        return result;
    }

    public static Instances prepareARFF(Sheet excelSheet, ArrayList<weka.core.Attribute> wekaAttributes, int numOfDataSets) {
        if (numOfDataSets <= 0) {
            return null;
        }
        return new weka.core.Instances(excelSheet.getSheetName(), wekaAttributes, numOfDataSets);
    }

    public static void fillARFF(List<Classification> classifications, Instances instances, int numOfDataSets) {
        int counter = numOfDataSets;
        while (counter > 0) {
            Random r = new Random();
            int low = 1;
            int high = classifications.size();
            int rnd = r.nextInt(high - low) + low;
            Classification resultClassification = classifications.get(rnd - 1);

            int numberAttr = instances.numAttributes();
            Instance in = new DenseInstance(numberAttr);
            instances.add(in);
            instances.instance(numOfDataSets-counter).setValue(0,resultClassification.getName());
            int num = 1;
            for (Attribute attribute : resultClassification.getAttributes()) {
                instances.instance(numOfDataSets-counter).setValue(num,attribute.generateValue());
                num++;
            }
            counter--;
        }
    }
}