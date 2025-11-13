package co.edu.uptc.API_Pedidos.service;

import co.edu.uptc.API_Pedidos.model.Pedido;
import co.edu.uptc.API_Pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    private final String BEBIDAS_API_URL = "http://localhost:8000/menu/";

    public boolean bebidaDisponible(String name) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            
            String url = BEBIDAS_API_URL + name;
            Object respuesta = restTemplate.getForObject(url, Object.class);
            return respuesta != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean crearPedido(Pedido pedido) {
        if (bebidaDisponible(pedido.getNombre())) {
            pedido.setEstadoPedido("ACEPTADO");
            pedidoRepository.save(pedido);
            return true;
        } else {
            pedido.setEstadoPedido("RECHAZADO");
            return false;
        }
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
}