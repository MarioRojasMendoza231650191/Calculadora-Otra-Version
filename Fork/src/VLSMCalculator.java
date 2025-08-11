import java.util.*;
import java.io.*;

public class VLSMCalculator {
    static class Subred {
        String ipInicial;
        String mascara;
        String ipFinal;
        String broadcast;
        int hosts;

        Subred(String ipInicial, String mascara, String ipFinal, String broadcast, int hosts) {
            this.ipInicial = ipInicial;
            this.mascara = mascara;
            this.ipFinal = ipFinal;
            this.broadcast = broadcast;
            this.hosts = hosts;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese la IP base (ej: 192.168.0.0):");
        String ipBase = sc.nextLine();

        if (!validarIP(ipBase)) {
            System.out.println("IP no válida");
            return;
        }

        System.out.println("¿Cuántos requerimientos de hosts?");
        int n = sc.nextInt();
        int[] requerimientos = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Hosts requeridos para subred " + (i + 1) + ": ");
            requerimientos[i] = sc.nextInt();
        }

        Arrays.sort(requerimientos);
        int[] reqOrdenados = new int[n];
        for (int i = 0; i < n; i++) reqOrdenados[i] = requerimientos[n - 1 - i];

        ArrayList<Subred> subredes = calcularVLSM(ipBase, reqOrdenados);

        // Exportar a Excel
        ExcelExporter.exportar(subredes, "resultado.xls");

        System.out.println("Cálculo terminado. Archivo 'resultado.xls' generado.");
    }

    static boolean validarIP(String ip) {
        String[] partes = ip.split("\\.");
        if (partes.length != 4) return false;
        for (String p : partes) {
            try {
                int num = Integer.parseInt(p);
                if (num < 0 || num > 255) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    static ArrayList<Subred> calcularVLSM(String ipBase, int[] requerimientos) {
        ArrayList<Subred> lista = new ArrayList<>();
        String[] partes = ipBase.split("\\.");
        int base1 = Integer.parseInt(partes[0]);
        int base2 = Integer.parseInt(partes[1]);
        int base3 = Integer.parseInt(partes[2]);
        int base4 = Integer.parseInt(partes[3]);

        int ipActual = base4;
        int oct3 = base3;

        for (int hosts : requerimientos) {
            int bits = 0;
            int totalHosts = 0;
            while (totalHosts < hosts + 2) {
                bits++;
                totalHosts = (int) Math.pow(2, bits);
            }
            int mascaraBits = 32 - bits;
            int bloque = totalHosts;

            int inicio = ipActual;
            int fin = ipActual + bloque - 1;

            if (fin > 255) {
                oct3++;
                ipActual = 0;
                fin = bloque - 1;
            }

            String ipInicial = base1 + "." + base2 + "." + oct3 + "." + inicio;
            String ipFinal = base1 + "." + base2 + "." + oct3 + "." + (fin - 1);
            String broadcast = base1 + "." + base2 + "." + oct3 + "." + (fin);

            String mascara = mascaraDesdeBits(mascaraBits);

            lista.add(new Subred(ipInicial, mascara, ipFinal, broadcast, totalHosts - 2));

            ipActual += bloque;
        }

        return lista;
    }

    static String mascaraDesdeBits(int bits) {
        int[] octetos = new int[4];
        for (int i = 0; i < bits; i++) {
            int octetoIndex = i / 8;
            int bitPos = 7 - (i % 8);
            octetos[octetoIndex] += Math.pow(2, bitPos);
        }
        return octetos[0] + "." + octetos[1] + "." + octetos[2] + "." + octetos[3];
    }
}
