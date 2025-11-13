package co.edu.uptc.API_Pedidos.repository;

import co.edu.uptc.API_Pedidos.model.Pedido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PedidoRepository {
    private final List<Pedido> pedidos = new ArrayList<>();

    public void save(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> findAll() {
        return Collections.unmodifiableList(pedidos);
    }
}