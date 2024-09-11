package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("en marcha facu");

        try {
            entityManager.getTransaction().begin();

            Cliente clienteA = Cliente.builder().nombre("Facundo").apellido("Ferreira").dni(44058477).build();

            Domicilio domicilio = Domicilio.builder().nombreCalle("Remedios de Escalada").numero(1872).build();

            Factura factura = Factura.builder().fecha("4 de Septiembre").numero(93012).total(1500).build();

            DetalleFactura detalleA = DetalleFactura.builder().cantidad(2).subtotal(500).build();

            Articulo articuloA = Articulo.builder().cantidad(30).denominacion("Fibron Rojo").precio(500).build();

            Articulo articuloB = Articulo.builder().cantidad(10).denominacion("Cubo Magico").precio(1000).build();

            DetalleFactura detalleB = DetalleFactura.builder().cantidad(2).subtotal(250).build();

            Categoria Libreria = Categoria.builder().denominacion("Libreria").build();

            Categoria Utiles = Categoria.builder().denominacion("Utiles").build();

            Categoria Jugueteria = Categoria.builder().denominacion("Jugueteria").build();

            clienteA.setDomicilio(domicilio);

            factura.setCliente(clienteA);
            factura.addDetalleFactura(detalleA);
            factura.addDetalleFactura(detalleB);

            articuloA.getCategorias().add(Libreria);
            articuloA.getCategorias().add(Utiles);

            articuloB.getCategorias().add(Libreria);
            articuloB.getCategorias().add(Jugueteria);

            detalleB.setArticulo(articuloB);
            detalleA.setArticulo(articuloA);

            entityManager.persist(clienteA);
            entityManager.persist(domicilio);
            entityManager.persist(factura);
            entityManager.persist(detalleB);
            entityManager.persist(detalleA);
            entityManager.persist(articuloA);
            entityManager.persist(articuloB);
            entityManager.persist(Libreria);
            entityManager.persist(Utiles);
            entityManager.persist(Jugueteria);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();

            entityManager.close();
            entityManagerFactory.close();
        }
    }
}