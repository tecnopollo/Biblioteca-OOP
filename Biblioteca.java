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

    public static void main() {
        ArrayList<String> datosE = leerArchivo("./estudiantes");
        ArrayList<String> datosL = leerArchivo("./libros");
        
        Biblioteca b = new Biblioteca(crearEstudiantes(datosE), crearLibros(datosL));
        
    
    }
    public boolean existeEstudiante(String codigoRequerido){
        boolean existe = false;
        for (Estudiante e : estudiantes){
            if ((e.codigo).equals(codigoRequerido)){
                existe = true;
                }
        }
        return existe;
    }
    
    
    public static ArrayList<Estudiante> crearEstudiantes (ArrayList<String> datos){

            ArrayList<Estudiante> estudiantes= new ArrayList<>();
            String csvSplitBy=",";
            for(String line:datos)
            {
            String[] datosA = line.split(csvSplitBy);
            String codigo = datosA[0].trim();
            String nombre = datosA[1].trim();

            if ((datosA[2].trim()).equalsIgnoreCase("Ingenieria")) {
               estudiantes.add(new Ingenieria(codigo, nombre));
            } else if ((datosA[2].trim()).equalsIgnoreCase("LicenciadoEnMatematicas")) {
               estudiantes.add(new LicenciadoEnMatematicas(codigo,nombre));
            }
             else if ((datosA[2].trim()).equalsIgnoreCase("LicenciadoEnMusica")) {
               estudiantes.add(new LicenciadoEnMusica(codigo, nombre));
            }
             else if ((datosA[2].trim()).equalsIgnoreCase("Doctor")) {
               estudiantes.add(new Doctor(codigo, nombre));
            }
        }
          return estudiantes;
        }
    
    public static ArrayList<Libro> crearLibros(ArrayList<String> datos) {
            String csvSplitBy = ",";
            ArrayList<Libro> libros= new ArrayList<>();
            for (String line:datos) {
                String[] datosA = line.split(csvSplitBy);

                String codigo = datosA[0].trim();
                String nombre = datosA[1].trim();
                String anioPublicacion = datosA[2].trim();
                int cantidadDisponible = Integer.parseInt(datosA[3].trim());


                if ((datosA[4].trim()).equalsIgnoreCase("Matematica")) {
                    libros.add(new Matematica(codigo,nombre,anioPublicacion,cantidadDisponible));
                } else if ((datosA[4].trim()).equalsIgnoreCase("Literatura")) {
                    libros.add(new Literatura(codigo,nombre,anioPublicacion,cantidadDisponible));
                if ((datosA[4].trim()).equalsIgnoreCase("Matematica")) {
                    libros.add(new Matematica(codigo,nombre,anioPublicacion,cantidadDisponible));
                } else if ((datosA[4].trim()).equalsIgnoreCase("Literatura")) {
                    libros.add(new Literatura(codigo,nombre,anioPublicacion,cantidadDisponible));
                } else if ((datosA[4].trim()).equalsIgnoreCase("Biologia")) {
                    libros.add(new Biologia(codigo,nombre,anioPublicacion,cantidadDisponible));
                } else if ((datosA[4].trim()).equalsIgnoreCase("CienciasSociales")) {
                    libros.add(new CienciasSociales(codigo,nombre,anioPublicacion,cantidadDisponible));
                }
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
 public Matematica (String codigo, String nombre, String anioPublicacion, int cantidadDisponible ){
      super (codigo,nombre,anioPublicacion,cantidadDisponible); 
    }
}

class Literatura extends Libro {
public Literatura (String codigo, String nombre, String anioPublicacion, int cantidadDisponible ){
      super (codigo,nombre,anioPublicacion,cantidadDisponible); 
    }
}

class Biologia extends Libro {
public Biologia (String codigo, String nombre, String anioPublicacion, int cantidadDisponible ){
      super (codigo,nombre,anioPublicacion,cantidadDisponible);
    }
}

class CienciasSociales extends Libro {
public CienciasSociales (String codigo,String nombre,String  anioPublicacion,int cantidadDisponible ){
      super (codigo,nombre,anioPublicacion,cantidadDisponible);  
    }
}

abstract class Estudiante {
    String codigo;
    String nombre;
    int maxPrestamos;
    ArrayList<Libro> prestamos;

    public Estudiante(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.prestamos = new ArrayList<>();
    }

    public void prestamo(Libro lib) {
        if (maxPrestamos >= prestamos.size()) {
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
        for(var libro: prestamos) {
            libro.imprimir();
        }
    }
}

class Ingenieria extends Estudiante {

    public Ingenieria(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 5;
    }
}
class LicenciadoEnMatematicas extends Estudiante {
    public LicenciadoEnMatematicas(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 7;
    }
}

class LicenciadoEnMusica extends Estudiante {
    public LicenciadoEnMusica(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 2;
    }
}

class Doctor extends Estudiante {
    public Doctor(String codigo, String nombre) {
        super(codigo, nombre);
        this.maxPrestamos = 12;
    }
}