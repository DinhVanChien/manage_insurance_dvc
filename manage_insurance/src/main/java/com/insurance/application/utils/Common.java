package com.insurance.application.utils;

import com.insurance.application.dto.CompanyDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Common {
    public static List<CompanyDto> compareName(List<CompanyDto> list) {
        Collections.sort(list, new Comparator<CompanyDto>() {
            @Override
            public int compare(CompanyDto o1, CompanyDto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }

    public static int convertInt(String input) {
        return Integer.parseInt(input);
    }

    public static String sqlLike(String input) {
        return "%" + input + "%";
    }

    public static boolean isNumber(String number) {
        return number.matches("\\d+") ? true : false;
    }

    public static boolean isEmail(String email) {
        String EMAIL_PATTERN =
                "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        return email.matches(EMAIL_PATTERN) ? true : false;
    }

    public static boolean isPhone(String phone) {
        String PHONE_PATTERN = "0[0-9\\s.-]{9,13}";
        return phone.matches(PHONE_PATTERN) ? true : false;
    }

    public static Date convertFromDate(String stringDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date convertFromDateDMY(String stringDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.sql.Date sd = new java.sql.Date(sdf.parse(stringDate).getTime());
            return sd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertFromDateDMY(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert date format dd-MM-yyyy to yyyy-MM-dd
     *
     * @param input
     * @return
     */
    public static String convertFromDateYMD(String input) {
        if (!StringUtils.isBlank(input)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(input.substring(6, 10)).append("-");
            stringBuilder.append(input.substring(3, 5)).append("-");
            stringBuilder.append(input.substring(0, 2));
            return stringBuilder.toString();
        }
        return input;
    }

    // Create CellStyle for header
    public static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    public static void createCellHeader(Sheet sheet, CellStyle cellStyle, String[] value) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < value.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(value[i]);
        }
    }

    // Write data
    public static void writeInsurance(String[] value, Row row) {
        for (int i = 0; i < value.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(value[i]);
        }
    }

    public static void writeXlsx(Workbook workbook, Sheet sheet, HttpServletResponse response, int sizeColumn, String fileName) {
        if (fileName.endsWith("xlsx")) {
            response.setContentType("application/xlsx");
        } else if (fileName.endsWith("xls")) {
            response.setContentType("application/xls");
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);
        try {
            for (int k = 0; k < sizeColumn; k++) {
                sheet.autoSizeColumn(k);
            }
            workbook.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException ex) {

        }
    }

    public static int getOffset(int currentPage, int limit) {
        return (currentPage - 1) * limit;
    }

    public static Boolean checkRole(Set<String> roles) {
        if (roles != null) {
            for (String role : roles) {
                if ("ROLE_ADMIN".equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isNewCompany(String isNew) {
        if(!isNew.isEmpty() && "yes".equals(isNew)) {
            return true;
        }
        return false;
    }
    /*
    Chuyển tiếng Việt có dấu sang không dấu
     */
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
