package exel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Exel util
 *
 * @see <a href="https://poi.apache.org/">Apache Poi</a>
 *
 * @author P.Pridorozhny
 */
public class Exel {

    private String filename;
    private Workbook wb;
    private int[] sizes;
    private int columns;
    private Row row;
    private Cell cell;


    /**
     *
     * util constructor
     *
     * @param name
     * name of the Exel file without file extension
     * @param pathParamMethods
     * set of all filler methods
     * @param sizes
     * sizes of the arrays
     */
    public Exel(String name, Set<Method> pathParamMethods, int[] sizes) {
        filename = name + ".xls";

        wb = new HSSFWorkbook();


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

        Sheet sheet = wb.getSheet(methodName);

        if (sheet == null) {
            System.out.println("Can not write data to Exel file" +
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
    private void writeData(Sheet sheet, String sortName, long time) {

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

    /**
     *writes data to the Exel file
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

//    private void createChart(Sheet sheet) {
//
///* At the end of this step, we have a worksheet with test data, that we want to write into a chart */
//                        /* Create a drawing canvas on the worksheet */
//        XSSFDrawing xlsx_drawing = my_worksheet.createDrawingPatriarch();
//                        /* Define anchor points in the worksheet to position the chart */
//        XSSFClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
//                        /* Create the chart object based on the anchor point */
//        XSSFChart my_line_chart = xlsx_drawing.createChart(anchor);
//                        /* Define legends for the line chart and set the position of the legend */
//        XSSFChartLegend legend = my_line_chart.getOrCreateLegend();
//        legend.setPosition(LegendPosition.BOTTOM);
//                        /* Create data for the chart */
//        LineChartData data = my_line_chart.getChartDataFactory().createLineChartData();
//                        /* Define chart AXIS */
//        ChartAxis bottomAxis = my_line_chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
//        ValueAxis leftAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
//        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
//                        /* Define Data sources for the chart */
//                        /* Set the right cell range that contain values for the chart */
//                        /* Pass the worksheet and cell range address as inputs */
//                        /* Cell Range Address is defined as First row, last row, first column, last column */
//        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 0, 4));
//        ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, 4));
//        ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 0, 4));
//        ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(3, 3, 0, 4));
//                        /* Add chart data sources as data to the chart */
//        data.addSerie(xs, ys1);
//        data.addSerie(xs, ys2);
//        data.addSerie(xs, ys3);
//                        /* Plot the chart with the inputs from data and chart axis */
//        my_line_chart.plot(data, new ChartAxis[] { bottomAxis, leftAxis });
//
//    }


}
