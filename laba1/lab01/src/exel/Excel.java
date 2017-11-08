package exel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Excel util
 *
 * @see <a href="https://poi.apache.org/">Apache Poi</a>
 *
 * @author P.Pridorozhny
 */
public class Excel {

    private String filename;
    private XSSFWorkbook wb;
    private int[] sizes;
    private int columns;
    private Row row;
    private Cell cell;


    /**
     *
     * util constructor
     *
     * @param name
     * name of the Excel file without file extension
     * @param pathParamMethods
     * set of all filler methods
     * @param sizes
     * sizes of the arrays
     */
    public Excel(String name, Set<Method> pathParamMethods, int[] sizes) {
        filename = name + ".xlsx";

        wb = new XSSFWorkbook();


        for (Method method : pathParamMethods) {

            wb.createSheet(method.getName());

        }

        this.sizes = sizes;
        columns = sizes.length;

        createTitles();

    }

    /**
     *
     * create header of sizes
     *
     * {@link #write(String sortName, String methodName, long time)}
     *
     */
    private void createTitles() {

        for (Sheet sheet : wb) {
            row = sheet.createRow(0);
            for (int i = 1; i <= columns; i++) {
                cell = row.createCell(i);
                cell.setCellValue(sizes[i - 1]);
            }
        }
    }

    /**
     *
     * checks if sheet exists and writes data to it
     *
     * {@link #writeToFile()}
     *
     * @param sortName
     * name of sort
     * @param methodName
     * name of filler method
     * @param time
     * algorithm running time
     * @return
     * true - if writing was successful;
     * false -  if sheet does not exist
     *
     */
    public boolean write(String sortName, String methodName, long time) {

        XSSFSheet sheet = wb.getSheet(methodName);

        if (sheet == null) {
            System.out.println("Can not write data to Excel file" +
                    "Check your input data");
            return false;
        }

//        System.out.println(sortName + " " + methodName + " " + time);
        writeData(sheet, sortName, time);

        return true;

    }

    /**
     *
     * writes data into cells
     *
     * {@link #write(String sortName, String methodName, long time)}
     *
     * @param sheet
     * sheet in which writes
     * @param sortName
     * name of the sort
     * @param time
     * algorithm running time
     *
     */
    private void writeData(XSSFSheet sheet, String sortName, long time) {

        int rowNum = sheet.getLastRowNum();
        int column = sheet.getRow(rowNum).getLastCellNum();

        if (column > columns) {
            column = 0;
            rowNum += 1;
            row = sheet.createRow(rowNum);
        }

        cell = row.createCell(column);

        if (column == 0) {
            cell.setCellValue(sortName.substring(sortName.lastIndexOf('.') + 1));
            writeData(sheet, sortName, time);
        } else
            cell.setCellValue(time);

        //System.out.println(sortName + " " + time);

    }

    /**
     *
     * fit columns to the text size
     *
     */
    public void autoSize() {

        for (Sheet sheet : wb) {
            for (int i = 0; i <= columns; i++) {
                sheet.autoSizeColumn(i);
            }
        }

    }

    public void createCharts() {

        for (Sheet sheet : wb)
            createChart(sheet);

    }

    /**
     * create linear chart in exel
     * @param sheet
     * sheet to paste the chart
     */
    private void createChart(Sheet sheet) {

        assert sheet != null : "null pointer exception";

        ArrayList<ChartDataSource<Number>> ar = new ArrayList<>(7);

        XSSFDrawing xlsx_drawing = (XSSFDrawing) sheet.createDrawingPatriarch();

        XSSFClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);

        XSSFChart my_line_chart = xlsx_drawing.createChart(anchor);
        my_line_chart.setTitleText(sheet.getSheetName());

        XSSFChartLegend legend = my_line_chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        //create data for the chart
        LineChartData data = my_line_chart.getChartDataFactory().createLineChartData();
        //Define chart AXIS
        ChartAxis bottomAxis = my_line_chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        //bottomAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        //leftAxis.setLogBase(10);
        //System.out.println(leftAxis.isSetLogBase());

        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            ar.add(DataSources.fromNumericCellRange(sheet, new CellRangeAddress(i, i, 0, columns)));
        }
//        ar.add(DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 0, columns)));
//        ar.add(DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, sheet.getLastRowNum(), 0, columns)));
        //Add chart data sources as data to the chart

        for (int i = 1; i < ar.size(); i++)
            data.addSeries(ar.get(0), ar.get(i));

        //Plot the chart with the inputs from data and chart axis
        my_line_chart.plot(data, bottomAxis, leftAxis);

    }

    /**
     *writes data to the Excel file
     *
     * {@link #write(String sortName, String methodName, long time)}
     */
    public void writeToFile() {
        try {
            File file = new File(filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
