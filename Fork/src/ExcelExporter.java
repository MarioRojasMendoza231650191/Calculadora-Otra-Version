import java.util.*;
import java.io.*;
import jxl.*;
import jxl.write.*;

public class ExcelExporter {
    public static void exportar(ArrayList<VLSMCalculator.Subred> subredes, String archivo) {
        try {
            WritableWorkbook libro = Workbook.createWorkbook(new File(archivo));
            WritableSheet hoja = libro.createSheet("Subredes", 0);

            hoja.addCell(new Label(0, 0, "IP Inicial"));
            hoja.addCell(new Label(1, 0, "Máscara"));
            hoja.addCell(new Label(2, 0, "IP Final"));
            hoja.addCell(new Label(3, 0, "Broadcast"));
            hoja.addCell(new Label(4, 0, "Hosts"));

            for (int i = 0; i < subredes.size(); i++) {
                VLSMCalculator.Subred s = subredes.get(i);
                hoja.addCell(new Label(0, i + 1, s.ipInicial));
                hoja.addCell(new Label(1, i + 1, s.mascara));
                hoja.addCell(new Label(2, i + 1, s.ipFinal));
                hoja.addCell(new Label(3, i + 1, s.broadcast));
                hoja.addCell(new Label(4, i + 1, String.valueOf(s.hosts)));
            }

            libro.write();
            libro.close();
        } catch (Exception e) {
            System.out.println("Error al exportar a Excel: " + e.getMessage());
        }
    }
}



public class ExcelExporter {
    public static void exportar(ArrayList<VLSMCalculator.Subred> subredes, String archivo) {
        try {
            WritableWorkbook libro = Workbook.createWorkbook(new File(archivo));
            WritableSheet hoja = libro.createSheet("Subredes", 0);

            hoja.addCell(new Label(0, 0, "IP Inicial"));
            hoja.addCell(new Label(1, 0, "Máscara"));
            hoja.addCell(new Label(2, 0, "IP Final"));
            hoja.addCell(new Label(3, 0, "Broadcast"));
            hoja.addCell(new Label(4, 0, "Hosts"));

            for (int i = 0; i < subredes.size(); i++) {
                VLSMCalculator.Subred s = subredes.get(i);
                hoja.addCell(new Label(0, i + 1, s.ipInicial));
                hoja.addCell(new Label(1, i + 1, s.mascara));
                hoja.addCell(new Label(2, i + 1, s.ipFinal));
                hoja.addCell(new Label(3, i + 1, s.broadcast));
                hoja.addCell(new Label(4, i + 1, String.valueOf(s.hosts)));
            }

            libro.write();
            libro.close();
        } catch (Exception e) {
            System.out.println("Error al exportar a Excel: " + e.getMessage());
        }
    }
}
