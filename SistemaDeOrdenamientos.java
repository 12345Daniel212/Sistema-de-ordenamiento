
//23400537	Fletes Mendez Jorge Antonio
//24400629	Martinez Banda Elias Oswaldo
//24400621	Rodriguez Altamirano Daniel
//23400554	Gonzalez Zepeda Alondra Yajaira
//23400599	Puerta Martinez Alexis Humberto
//23400579	Mendez Villareal Donnovan Yahir

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;

public class SistemaDeOrdenamientos extends JFrame {

    int[] datos;

    JButton btnGenerar = new JButton("Generar 10,000 numeros de forma aleatoria");
    JButton btnBurbuja = new JButton("Burbuja");
    JButton btnQuick = new JButton("QuickSort");
    JButton btnShell = new JButton("ShellSort");
    JButton btnRadix = new JButton("Radix");
    JTextArea areaNo = new JTextArea();
    JTextArea areaSi = new JTextArea();
    JLabel lblInfo = new JLabel("Primero genera los datos.");

    public SistemaDeOrdenamientos() {
        super("SistemaDeOrdenamientos");

        // Áreas configuradas para mostrar en renglones
        areaNo.setEditable(false);
        areaSi.setEditable(false);
        areaNo.setLineWrap(true);
        areaSi.setLineWrap(true);
        areaNo.setWrapStyleWord(true);
        areaSi.setWrapStyleWord(true);

        JScrollPane spNo = new JScrollPane(areaNo);
        JScrollPane spSi = new JScrollPane(areaSi);
        spNo.setBorder(BorderFactory.createTitledBorder("NO ORDENADO"));
        spSi.setBorder(BorderFactory.createTitledBorder("YA ORDENADO"));

        JPanel arriba = new JPanel();
        arriba.add(btnGenerar);
        arriba.add(lblInfo);

        JPanel abajo = new JPanel();
        abajo.add(btnBurbuja);
        abajo.add(btnQuick);
        abajo.add(btnShell);
        abajo.add(btnRadix);

        setOrdenButtons(false);

        btnGenerar.addActionListener(e -> generar());
        btnBurbuja.addActionListener(e -> ordenar("Burbuja"));
        btnQuick.addActionListener(e -> ordenar("Quick"));
        btnShell.addActionListener(e -> ordenar("Shell"));
        btnRadix.addActionListener(e -> ordenar("Radix"));

        setLayout(new BorderLayout());
        add(arriba, BorderLayout.NORTH);
        add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spNo, spSi), BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);

        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void setOrdenButtons(boolean on) {
        btnBurbuja.setEnabled(on);
        btnQuick.setEnabled(on);
        btnShell.setEnabled(on);
        btnRadix.setEnabled(on);
    }

    void generar() {
        Random r = new Random();
        datos = new int[10000];
        for (int i = 0; i < datos.length; i++)
            datos[i] = r.nextInt(100000);
        areaNo.setText(arrayToString(datos));
        areaSi.setText("");
        lblInfo.setText("Datos listos. Ahora ordena.");
        setOrdenButtons(true);
    }

    void ordenar(String metodo) {
        int[] copia = Arrays.copyOf(datos, datos.length);
        long t0 = System.currentTimeMillis();

        if (metodo.equals("Burbuja"))
            burbuja(copia);
        if (metodo.equals("Quick"))
            quick(copia, 0, copia.length - 1);
        if (metodo.equals("Shell"))
            shell(copia);
        if (metodo.equals("Radix"))
            radix(copia);

        long t1 = System.currentTimeMillis();
        areaSi.setText(arrayToString(copia));
        lblInfo.setText(metodo + " tardó " + (t1 - t0) + " ms en ordenar los 10,000 numeros.");
    }

    // Convertir arreglo en texto vertical (uno por renglón)
    private static String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]).append("\n");
        }
        return sb.toString();
    }

    // ---------- Métodos de ordenamiento ----------
    void burbuja(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

    void quick(int[] a, int l, int r) {
        if (l >= r)
            return;
        int i = l, j = r, p = a[(l + r) / 2];
        while (i <= j) {
            while (a[i] < p)
                i++;
            while (a[j] > p)
                j--;
            if (i <= j) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i++;
                j--;
            }
        }
        if (l < j)
            quick(a, l, j);
        if (i < r)
            quick(a, i, r);
    }

    void shell(int[] a) {
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                int temp = a[i], j = i;
                while (j >= gap && a[j - gap] > temp) {
                    a[j] = a[j - gap];
                    j -= gap;
                }
                a[j] = temp;
            }
        }
    }

    void radix(int[] a) {
        int max = Arrays.stream(a).max().getAsInt();
        for (int exp = 1; max / exp > 0; exp *= 10)
            countSort(a, exp);
    }

    void countSort(int[] a, int exp) {
        int n = a.length;
        int[] out = new int[n];
        int[] count = new int[10];
        for (int i = 0; i < n; i++)
            count[(a[i] / exp) % 10]++;
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            int idx = (a[i] / exp) % 10;
            out[count[idx] - 1] = a[i];
            count[idx]--;
        }
        for (int i = 0; i < n; i++)
            a[i] = out[i];
    }

    public static void main(String[] args) {
        new SistemaDeOrdenamientos().setVisible(true);
    }
}
