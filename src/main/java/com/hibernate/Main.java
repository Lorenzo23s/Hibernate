package com;

import com.hibernate.Dao.EntrenadorDao;
import com.hibernate.Dao.EquipoDao;
import com.hibernate.Dao.JugadorDao;
import com.hibernate.Dao.LigaDao;
import com.hibernate.Model.*;
import com.hibernate.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        // Instanciar DAOs
        LigaDao ligaDao = new LigaDao();
        EquipoDao equipoDao = new EquipoDao();
        JugadorDao jugadorDao = new JugadorDao();
        EntrenadorDao entrenadorDao = new EntrenadorDao();

        // 1️⃣ Crear una liga solo si no existe
        Liga liga = new Liga("La Liga");
        if (!ligaDao.existeLiga(liga.getNombreLiga())) {
            ligaDao.save(liga);
            System.out.println("✅ Liga creada: " + liga.getNombreLiga());
        } else {
            liga = ligaDao.getByNombre("La Liga"); // Obtener liga existente
            System.out.println("⚠ La Liga ya existe.");
        }

        // 2️⃣ Crear equipos solo si no existen
        Equipo equipo1 = new Equipo("Real Madrid", liga);
        Equipo equipo2 = new Equipo("FC Barcelona", liga);
        Equipo equipo3 = new Equipo("Atletico Madrid", liga);

        if (!equipoDao.existeEquipo(equipo1.getNombre())) equipoDao.save(equipo1);
        if (!equipoDao.existeEquipo(equipo2.getNombre())) equipoDao.save(equipo2);
        if (!equipoDao.existeEquipo(equipo3.getNombre())) equipoDao.save(equipo3);

        // 3️⃣ Crear jugadores solo si no existen
        Jugador jugador1 = new Jugador("Vinicius Jr", equipo1, new Ubicacion("Madrid", "España"));
        Jugador jugador2 = new Jugador("Karim Benzema", equipo1, new Ubicacion("Madrid", "España"));
        Jugador jugador3 = new Jugador("Pedri", equipo2, new Ubicacion("Barcelona", "España"));
        Jugador jugador4 = new Jugador("Gavi", equipo2, new Ubicacion("Barcelona", "España"));

        if (!jugadorDao.existeJugador(jugador1.getNombre())) jugadorDao.save(jugador1);
        if (!jugadorDao.existeJugador(jugador2.getNombre())) jugadorDao.save(jugador2);
        if (!jugadorDao.existeJugador(jugador3.getNombre())) jugadorDao.save(jugador3);
        if (!jugadorDao.existeJugador(jugador4.getNombre())) jugadorDao.save(jugador4);

        // 4️⃣ Crear entrenadores solo si no existen
        Entrenador entrenador1 = new Entrenador("Carlo Ancelotti", equipo1);
        Entrenador entrenador2 = new Entrenador("Xavi Hernández", equipo2);

        if (!entrenadorDao.existeEntrenador(entrenador1.getNombre())) entrenadorDao.save(entrenador1);
        if (!entrenadorDao.existeEntrenador(entrenador2.getNombre())) entrenadorDao.save(entrenador2);

        transaction.commit();
        session.close();

        // 📌 Mostrar datos en consola para el profesor
        System.out.println("\n🔹 ** Datos en la base de datos ** 🔹");

        System.out.println("\n📌 Ligas Registradas:");
        List<Liga> ligas = ligaDao.getAll();
        for (Liga l : ligas) System.out.println(" - " + l.getNombreLiga());

        System.out.println("\n📌 Equipos Registrados:");
        List<Equipo> equipos = equipoDao.getAll();
        for (Equipo e : equipos) System.out.println(" - " + e.getNombre());

        System.out.println("\n📌 Jugadores Registrados:");
        List<Jugador> jugadores = jugadorDao.getAll();
        for (Jugador j : jugadores) System.out.println(" - " + j.getNombre() + " en " + j.getEquipo().getNombre());

        System.out.println("\n📌 Entrenadores Registrados:");
        List<Entrenador> entrenadores = entrenadorDao.getAll();
        for (Entrenador ent : entrenadores) System.out.println(" - " + ent.getNombre() + " en " + ent.getEquipo().getNombre());

        System.out.println("\n✅ Datos guardados correctamente en MySQL");
    }
}
