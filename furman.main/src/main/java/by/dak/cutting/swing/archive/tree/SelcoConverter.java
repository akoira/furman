package by.dak.cutting.swing.archive.tree;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.facade.OrderFacade;
import by.dak.cutting.facade.OrderFurnitureFacade;
import by.dak.cutting.swing.order.controls.OrderDetailsDTOConverter;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.convert.TextureBoardDefPair2StringConverter;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderFurniture;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SelcoConverter {
    public static final String DIR = "d:\\_nodelete\\selco";
    private OrderFacade orderFacade;
    private OrderFurnitureFacade orderFurnitureFacade;
    private Long id;
    private Order order;
    private List<OrderFurniture> details;
    private List<TextureBoardDefPair> pairs;
    private Map<TextureBoardDefPair, List<OrderDetailsDTO>> map;
    private XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet spreadsheet;
    private AtomicInteger rowIndex = new AtomicInteger(0);
    private String fileName;


    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("ru", "RU", "utf8"));
        SpringConfiguration springConfiguration = new SpringConfiguration(false);

        SelcoConverter converter = new SelcoConverter()
//                .id(123249661L)
//                .id(123239703L)
                .id(123244656L)
                .orderFacade(springConfiguration.getMainFacade().getOrderFacade())
                .orderFurnitureFacade(springConfiguration.getMainFacade().getOrderFurnitureFacade())
                .prepare()
                .xlsx();
    }


    public SelcoConverter id(Long id) {
        this.id = id;
        return this;
    }

    public SelcoConverter orderFacade(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
        return this;
    }

    public SelcoConverter orderFurnitureFacade(OrderFurnitureFacade orderFurnitureFacade) {
        this.orderFurnitureFacade = orderFurnitureFacade;
        return this;
    }

    public SelcoConverter prepare() {
        this.order = orderFacade.findBy(id);
        this.details = orderFurnitureFacade.loadAllBy(order);
        this.pairs = orderFurnitureFacade.findUniquePairDefText(order);
        this.map = new HashMap<>();

        this.details.forEach(d -> {
            List<OrderDetailsDTO> list = map.get(d.getPair());
            if (list == null)
                list = new ArrayList<>();
            OrderDetailsDTO dto = new OrderDetailsDTOConverter(d).getDTO();
            list.add(dto);
            map.put(d.getPair(), list);
        });
        return this;
    }

    public SelcoConverter xlsx() throws IOException {
        this.fileName = order.getNumber().getStringValue() + ".xlsx";
        this.spreadsheet = workbook.createSheet(order.getNumber().getStringValue());
        columns();
        map.keySet().forEach(k -> {
            pair(k);
            List<OrderDetailsDTO> dtos = map.get(k);
            IntStream.range(0, dtos.size()).forEach(i -> {
                XSSFRow row = spreadsheet.createRow(rowIndex.getAndIncrement());
                XSSFCell cell = row.createCell(0);
                OrderDetailsDTO dto = dtos.get(i);

                cell.setCellValue(dto.getOrderFurnitureEntity().getLength());

                cell = row.createCell(1);
                cell.setCellValue(dto.getOrderFurnitureEntity().getWidth());

                cell = row.createCell(2);
                cell.setCellValue(dto.getCount());

                cell = row.createCell(3);
                cell.setCellValue(!dto.getTexture().isRotatable() ? "1" : "0");

                if (dto.getGlueing() != null) {
                    cell = row.createCell(4);
                    cell.setCellValue(dto.getGlueing().getUpBorderDef() != null ? String.valueOf(dto.getGlueing().getUpBorderDef().getThickness().intValue()) : "");

                    cell = row.createCell(5);
                    cell.setCellValue(dto.getGlueing().getDownBorderDef() != null ? String.valueOf(dto.getGlueing().getDownBorderDef().getThickness().intValue()) : "");

                    cell = row.createCell(6);
                    cell.setCellValue(dto.getGlueing().getLeftBorderDef() != null ? String.valueOf(dto.getGlueing().getLeftBorderDef().getThickness().intValue()) : "");

                    cell = row.createCell(7);
                    cell.setCellValue(dto.getGlueing().getRightBorderDef() != null ? String.valueOf(dto.getGlueing().getRightBorderDef().getThickness().intValue()) : "");
                }

                cell = row.createCell(8);
                cell.setCellValue(order.getNumber().getStringValue());

                cell = row.createCell(9);
                List<String> value = new ArrayList<>();
                if (dto.getMilling() != null)
                    value.add("R");
                if (dto.getGroove() != null)
                    value.add("Paz");
                if (dto.getA45() != null)
                    value.add("UG");
                if (dto.getCutoff() != null)
                    value.add("SR");
                if (dto.getOrderFurnitureEntity().isComplex())
                    value.add("SKL");
                if (dto.getDrilling() != null && StringUtils.isNotBlank(dto.getDrilling().getNotes()))
                    value.add(StringUtils.trim(dto.getDrilling().getNotes()));
                if (value.size() > 0)
                    cell.setCellValue(String.join("/", value));
            });
        });

        FileOutputStream out = new FileOutputStream(new File(DIR, fileName));
        workbook.write(out);
        out.close();
        System.out.println(new File(DIR, fileName));
        return this;
    }

    public String fileName() {
        return fileName;
    }

    private void pair(TextureBoardDefPair k) {
        XSSFRow row = spreadsheet.createRow(rowIndex.getAndIncrement());
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(new TextureBoardDefPair2StringConverter().convert(k));
    }

    private void columns() {
        XSSFRow row = spreadsheet.createRow(rowIndex.getAndIncrement());
        List<String> columns =
                Arrays.asList("Длина", "Ширина", "Кол-во", "поворот", "кромка длина1", "кромка длина2", "кромка ширина1", "кромка ширина2", "номер заказа", "примечание");

        IntStream.range(0, columns.size()).forEach(i -> {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(columns.get(i));
        });
    }

}
