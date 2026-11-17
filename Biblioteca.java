import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Biblioteca {
    ArrayList<Estudiante> estudiantes;
    ArrayList<Libro> libros;

    public Biblioteca(ArrayList<Estudiante> estudiantes, ArrayList<Libro> libros) {
        this.estudiantes = estudiantes;
        this.libros = libros;
    }

    public static void main(String[] args) {
        ArrayList<String> datosE = leerArchivo("./estudiantes.csv");
        ArrayList<String> datosL = leerArchivo("./libros.csv");
        Biblioteca b = new Biblioteca(crearEstudiantes(datosE), crearLibros(datosL));
        b.nav();
    }

    public void nav() {
        String[] opciones = {
            "0] Salir del menu",
            "1] Imprimir estudiantes registrados en biblioteca",
            "2] Imprimir libros registrados en biblioteca",
            "3] Prestamo de libros",
            "4] Devolucion de libros"
        };
        int i = -1;

        while (i != 0) {
            String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione una opción:",
                "----------Menu----------",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            // Si el usuario presiona Cancelar o cierra la ventana, sale del programa
            if (seleccion == null) {
                break;
            }

            i = Character.getNumericValue(seleccion.charAt(0));

            switch (i) {
                case 1:
                    this.imprimirEstudiantes();
                    break;
                case 2:
                    this.imprimLibros();
                    break;
                case 3:
                    this.hacerPrestamo();
                    break;
                case 4:
                    this.hacerDevolucion();
                    break;
            }
        }
    }

    public void imprimirEstudiantes() {
        StringBuilder sb = new StringBuilder();
        for (var e : estudiantes) {
            e.imprimir(); // Mantiene la impresión en consola por si tu profesor la revisa
            sb.append(e.codigo).append(", ").append(e.nombre).append(", ").append(e.carrera).append("\n");
        }
        if (sb.length() == 0) {
            sb.append("No hay estudiantes registrados.");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Estudiantes Registrados", JOptionPane.INFORMATION_MESSAGE);
    }

    public void imprimLibros() {
        StringBuilder sb = new StringBuilder();
        for (var libro : libros) {
            libro.imprimir(); // Mantiene la impresión en consola
            sb.append(libro.codigo).append(", ").append(libro.nombre).append(", ")
              .append(libro.anioPublicacion).append(", Disponibles: ").append(libro.cantidadDisponible).append("\n");
        }
        if (sb.length() == 0) {
            sb.append("No hay libros registrados.");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Libros Registrados", JOptionPane.INFORMATION_MESSAGE);
    }

    public Estudiante buscarEstudiante(String codigoRequerido) {
        for (var e : estudiantes) {
            if ((e.codigo).equals(codigoRequerido)) {
                return e;
            }
        }
        return null;
    }

    public Libro buscarLibro(String codigoRequerido) {
        for (var l : libros) {
            if ((l.codigo).equals(codigoRequerido)) {
                return l;
            }
        }
        return null;
    }

    public static ArrayList<Estudiante> crearEstudiantes(ArrayList<String> datos) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        String csvSplitBy = ",";
        for (String line : datos) {
            String[] datosA = line.split(csvSplitBy);
            String codigo = datosA[0].trim();
            String nombre = datosA[1].trim();
            String carrera = datosA[2].trim();

            if (carrera.equalsIgnoreCase("Ingenieria")) {
                estudiantes.add(new Ingenieria(codigo, nombre));
            } else if (carrera.equalsIgnoreCase("LicenciadoEnMatematicas")) {
                estudiantes.add(new LicenciadoEnMatematicas(codigo, nombre));
            } else if (carrera.equalsIgnoreCase("LicenciadoEnMusica")) {
                estudiantes.add(new LicenciadoEnMusica(codigo, nombre));
            } else if (carrera.equalsIgnoreCase("Doctor")) {
                estudiantes.add(new Doctor(codigo, nombre));
            }
        }
        return estudiantes;
    }

    public static ArrayList<Libro> crearLibros(ArrayList<String> datos) {
        String csvSplitBy = ",";
        ArrayList<Libro> libros = new ArrayList<>();
        for (String line : datos) {
            String[] datosA = line.split(csvSplitBy);

            String codigo = datosA[0].trim();
            String nombre = datosA[1].trim();
            String anioPublicacion = datosA[2].trim();
            int cantidadDisponible = Integer.parseInt(datosA[3].trim());
            String tipo = datosA[4].trim();

            if (tipo.equalsIgnoreCase("Matematica")) {
                libros.add(new Matematica(codigo, nombre, anioPublicacion, cantidadDisponible));
            } else if (tipo.equalsIgnoreCase("Literatura")) {
                libros.add(new Literatura(codigo, nombre, anioPublicacion, cantidadDisponible));
            } else if (tipo.equalsIgnoreCase("Biologia")) {
                libros.add(new Biologia(codigo, nombre, anioPublicacion, cantidadDisponible));
            } else if (tipo.equalsIgnoreCase("CienciasSociales")) {
                libros.add(new CienciasSociales(codigo, nombre, anioPublicacion, cantidadDisponible));
            }
        }
        return libros;
    }

    public static ArrayList<String> leerArchivo(String csvFile) {
        ArrayList<String> lineas = new ArrayList<String>();
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty())
                    lineas.add(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lineas;
    }

    void hacerPrestamo() {
        // Busca el estudiante
        String codigoE = JOptionPane.showInputDialog(null, "Codigo del estudiante:", "Préstamo de Libro", JOptionPane.QUESTION_MESSAGE);
        if (codigoE == null) return;

        Estudiante estudiante = buscarEstudiante(codigoE.trim());
        if (estudiante == null) {
            JOptionPane.showMessageDialog(null, "El estudiante del código solicitado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Busca el libro
        String codigoL = JOptionPane.showInputDialog(null, "Codigo del libro:", "Préstamo de Libro", JOptionPane.QUESTION_MESSAGE);
        if (codigoL == null) return;

        Libro libro = buscarLibro(codigoL.trim());
        if (libro == null) {
            JOptionPane.showMessageDialog(null, "El libro del código solicitado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        estudiante.prestamo(libro);
    }

    void hacerDevolucion() {
        // Busca el estudiante
        String codigoE = JOptionPane.showInputDialog(null, "Codigo del estudiante:", "Devolución de Libro", JOptionPane.QUESTION_MESSAGE);
        if (codigoE == null) return;

        Estudiante estudiante = buscarEstudiante(codigoE.trim());
        if (estudiante == null) {
            JOptionPane.showMessageDialog(null, "El estudiante del código solicitado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (estudiante.prestamos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El estudiante no tiene libros prestados actualmente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Muestra los libros prestados y pide el índice
        estudiante.imprimirPrestados();
        StringBuilder listaPrestados = new StringBuilder("Libros prestados a " + estudiante.nombre + ":\n");
        for (int i = 0; i < estudiante.prestamos.size(); i++) {
            Libro l = estudiante.prestamos.get(i);
            listaPrestados.append(i).append("] ").append(l.codigo).append(", ").append(l.nombre).append("\n");
        }
        listaPrestados.append("\nIndique el indice del libro a devolver:");

        String indiceStr = JOptionPane.showInputDialog(null, listaPrestados.toString(), "Devolución de Libro", JOptionPane.QUESTION_MESSAGE);
        if (indiceStr == null) return;

        try {
            int indice = Integer.parseInt(indiceStr.trim());
            estudiante.devolucion(indice);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class Libro {
    String codigo;
    String nombre;
    String anioPublicacion;
    int cantidadDisponible;

    public Libro(String codigo, String nombre, String anioPublicacion, int cantidadDisponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.anioPublicacion = anioPublicacion;
        this.cantidadDisponible = cantidadDisponible;
    }

    public void imprimir() {
        System.out.println(codigo + ", " + nombre + ", " + anioPublicacion + ", " + cantidadDisponible);
    }
}

class Matematica extends Libro {
    public Matematica(String codigo, String nombre, String anioPublicacion, int cantidadDisponible) {
        super(codigo, nombre, anioPublicacion, cantidadDisponible);
    }
}

class Literatura extends Libro {
    public Literatura(String codigo, String nombre, String anioPublicacion, int cantidadDisponible) {
        super(codigo, nombre, anioPublicacion, cantidadDisponible);
    }
}

class Biologia extends Libro {
    public Biologia(String codigo, String nombre, String anioPublicacion, int cantidadDisponible) {
        super(codigo, nombre, anioPublicacion, cantidadDisponible);
    }
}

class CienciasSociales extends Libro {
    public CienciasSociales(String codigo, String nombre, String anioPublicacion, int cantidadDisponible) {
        super(codigo, nombre, anioPublicacion, cantidadDisponible);
    }
}

abstract class Estudiante {
    String codigo;
    String nombre;
    int maxPrestamos;
    ArrayList<Libro> prestamos;
    String carrera;

    public Estudiante(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.prestamos = new ArrayList<>();
    }

    public void prestamo(Libro lib) {
        if (prestamos.size() >= maxPrestamos) {
            System.out.println("No se puede prestar el libro");
            JOptionPane.showMessageDialog(null, "No se puede prestar el libro (límite alcanzado: " + maxPrestamos + ")", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else if (lib.cantidadDisponible == 0) {
            System.out.println("No hay ejemplares del libro disponible para el prestamo");
            JOptionPane.showMessageDialog(null, "No hay ejemplares del libro disponible para el prestamo", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            prestamos.add(lib);
            lib.cantidadDisponible--;
            JOptionPane.showMessageDialog(null, "Préstamo realizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void devolucion(int index) {
        try {
            Libro libDevuelto = prestamos.remove(index);
            libDevuelto.cantidadDisponible++; // Aumenta nuevamente la cantidad disponible al devolver
            JOptionPane.showMessageDialog(null, "Devolución realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No se pudo encontrar el libro que desea devolver");
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el libro que desea devolver", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void imprimirPrestados() {
        System.out.println("Libros prestados a " + nombre + ":");
        for (var libro : prestamos) {
            libro.imprimir();
        }
    }

    public void imprimir() {
        System.out.println(codigo + ", " + nombre + ", " + carrera);
    }
}

class Ingenieria extends Estudiante {
    public Ingenieria(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 5;
        carrera = "Ingenieria";
    }
}

class LicenciadoEnMatematicas extends Estudiante {
    public LicenciadoEnMatematicas(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 7;
        carrera = "Licenciatura en matematicas";
    }
}

class LicenciadoEnMusica extends Estudiante {
    public LicenciadoEnMusica(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 2;
        carrera = "Licenciatura en musica";
    }
}

class Doctor extends Estudiante {
    public Doctor(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 12;
        carrera = "Medicina";
    }
}
