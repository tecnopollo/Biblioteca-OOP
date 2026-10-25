import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

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
    public void nav (){
        int i = -1;
        Scanner s = new Scanner(System.in);
        
        while (i != 0){
            imprimirMenu();
            i = s.nextInt();
            s.nextLine();
            switch (i){
                case 1 : this.imprimirEstudiantes();
                    break;
                case 2 : this.imprimLibros();
                    break;
                case 3 :this.hacerPrestamo(s);
                    break;
                case 4 : this.hacerDevolucion(s);
                    break;    
            }
        }
        s.close(); 
    }
    public void imprimirMenu() {
        System.out.println("----------Menu----------");
        System.out.println("0] Salir del menu");
        System.out.println("1] Imprimir estudiantes registrados en biblioteca");
        System.out.println("2] Imprimir libros registrados en biblioteca");
        System.out.println("3] Prestamo de libros");
        System.out.println("4] Devolucion de libros");
    }

    public void imprimirEstudiantes() {
        for (var e : estudiantes) {
            e.imprimir();
        }
    }

    public void imprimLibros() {
        for (var libro : libros) {
            libro.imprimir();
        }
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

    void hacerPrestamo(Scanner s) {
        
        // Busca el estudiante
        System.out.print("Codigo del estudiante: ");
        String codigoE = s.nextLine().trim();
        Estudiante estudiante = buscarEstudiante(codigoE);
        if (estudiante == null) {
            System.out.println("El estudiante del código solicitado no existe");
            return;
        }

        // Busca el libro 
        System.out.print("Codigo del libro: ");
        String codigoL = s.nextLine().trim();
        Libro libro = buscarLibro(codigoL);
        if(libro == null) {
            System.out.println("EL libro del código solicitado no existe");
            return;
        }
        estudiante.prestamo(libro);
    }

    void hacerDevolucion(Scanner s) {

        // Busca el estudiante
        System.out.print("Codigo del estudiante: ");
        String codigoE = s.nextLine().trim();
        Estudiante estudiante = buscarEstudiante(codigoE);
        if (estudiante == null) {
            System.out.println("El estudiante del código solicitado no existe");
            return;
        }       

        // Busca el libro
        estudiante.imprimirPrestados();
        System.out.print("Indique el indice del libro a devolver: ");
        int indice = s.nextInt(); 
        estudiante.devolucion(indice);
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
        } else if (lib.cantidadDisponible == 0) {
            System.out.println("No hay ejemplares del libro disponible para el prestamo");
        } else {
            prestamos.add(lib);
            lib.cantidadDisponible--;
        }
    }

    public void devolucion(int index) {
        try {
            prestamos.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No se pudo encontrar el libro que desea devolver");
        }
    }

    public void imprimirPrestados() {
        System.out.println("Libros prestados a" + nombre + ":");
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