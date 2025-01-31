package com.ucb.webapp.dao;

import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ListaProductos;
import com.ucb.webapp.dto.RegistroProducto;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class DaoProductos {
    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<ListaProductos> listarProductos() {
        List<ListaProductos> productos = new LinkedList<>();
        String sql = "SELECT\n" +
                "\tProductos.idProducto,\n" +
                "\tProductos.nombre,\n" +
                "\tProductos.modelo,\n" +
                "\tMarcas.marca,\n" +
                "\tProductos.descripcion,\n" +
                "\tCategorias.categoria,\n" +
                "\tProductos.cantidad,\n" +
                "\tProductos.precio \n" +
                "FROM\n" +
                "\tProductos\n" +
                "\tINNER JOIN Marcas ON Productos.Marcas_idMarca = Marcas.idMarca\n" +
                "\tINNER JOIN Categorias ON Productos.Categorias_idCategoria = Categorias.idCategoria \n" +
                "WHERE\n" +
                "\tProductos.borrado = FALSE";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ListaProductos producto = new ListaProductos();
                producto.setIdProducto((long) resultSet.getInt("idProducto"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setModelo(resultSet.getString("modelo"));
                producto.setMarca(resultSet.getString("marca"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setCategoria(resultSet.getString("categoria"));
                producto.setCantidad(resultSet.getInt("cantidad"));
                producto.setPrecio(resultSet.getBigDecimal("precio"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            throw new CustomException("Error al ejecutar consulta");
        }
        return productos;
    }

    public int insertarProducto(RegistroProducto producto, int idMarca, int idCategoria) throws SQLException {
        System.out.println("Intentando insertar producto: " + producto.getNombre());

        String sql = "INSERT INTO productos (nombre, modelo, cantidad, descripcion, precio, borrado, Marcas_idMarca, Categorias_idCategoria) " +
                     "VALUES (?, ?, ?, ?, ?, false, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getModelo() != null ? producto.getModelo() : "");
            statement.setInt(3, producto.getCantidad());
            statement.setString(4, producto.getDescripcion() != null ? producto.getDescripcion() : "");
            statement.setBigDecimal(5, producto.getPrecio());
            statement.setInt(6, idMarca);
            statement.setInt(7, idCategoria);

            int affectedRows = statement.executeUpdate();
            System.out.println("Filas afectadas: " + affectedRows);

            if (affectedRows == 0) {
                throw new SQLException("Registro fallido, no se modificó ninguna fila");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("Producto insertado con ID: " + generatedKeys.getInt(1));
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Registro fallido, no se obtuvo ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomException("Error al insertar producto: " + e.getMessage());
        }
    }
}
    
