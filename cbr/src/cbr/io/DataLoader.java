package cbr.io;

import cbr.attribute.Attribute;
import cbr.attribute.ChoiceAttribute;
import cbr.attribute.NumAttribute;
import cbr.instance.DataInstance;
import cbr.value.ChoiceValue;
import cbr.value.NumValue;
import cbr.value.Value;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
    Map<String, Attribute> attributesMap = new HashMap<>();
    try {
      Row firstRow = excelSheet.getRow(0);
      Row secondRow = excelSheet.getRow(1);
      Iterator<Cell> secondRowCellIt = secondRow.cellIterator();
      Attribute curAttribute = null;
      while (secondRowCellIt.hasNext()) {
        Cell curCell = secondRowCellIt.next();
        String curCellValue = curCell.getStringCellValue();
        if (curCellValue.isEmpty())
          continue;
        String topCellValue = firstRow.getCell(curCell.getColumnIndex()).getStringCellValue();
        if (!topCellValue.isEmpty()) {
          if (topCellValue.endsWith("]"))
            curAttribute = new NumAttribute();
          else {
            curAttribute = new ChoiceAttribute();
            ((ChoiceAttribute) curAttribute).addPossibleValue(curCellValue);
          }
          attributesMap.put(topCellValue, curAttribute);
        } else {
          if (curAttribute != null && curAttribute.getClass().equals(ChoiceAttribute.class)) {
            ((ChoiceAttribute) curAttribute).addPossibleValue(curCellValue);
          }
        }
      }
      ChoiceAttribute baumgattung = new ChoiceAttribute();
      for (int i = 2; i <= excelSheet.getLastRowNum(); i++) {
        String curValue = excelSheet.getRow(i).getCell(0).getStringCellValue();
        if (curValue != null && !curValue.isEmpty())
          baumgattung.addPossibleValue(curValue);
      }
      attributesMap.put("Baumgattung", baumgattung);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return attributesMap;
  }

  private static List<DataInstance> loadDataInstances(Map<String, Attribute> attributes, Instances instances) {
    List<DataInstance> dataInstancesList = new ArrayList<>();
    try {
      for (Instance aData : instances) {
        Map<String, Value> curValues = new HashMap<>();
        Enumeration<weka.core.Attribute> instanceAttributes = aData.enumerateAttributes();
        while (instanceAttributes.hasMoreElements()) {
          weka.core.Attribute curWekaAttribute = instanceAttributes.nextElement();
          String curAttributeName = curWekaAttribute.name();
          Attribute curAttribute = attributes.get(curAttributeName);
          if (curAttribute.getClass().equals(ChoiceAttribute.class))
            curValues.put(curAttributeName, new ChoiceValue(aData.stringValue(curWekaAttribute)));
          else {
            NumAttribute curNumAttribute = (NumAttribute) curAttribute;
            double curValue = aData.value(curWekaAttribute);
            curNumAttribute.setMin(curValue);
            curNumAttribute.setMax(curValue);
            curValues.put(curAttributeName, new NumValue(curValue));
          }
        }
        dataInstancesList.add(new DataInstance(curValues));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dataInstancesList;
  }
}
