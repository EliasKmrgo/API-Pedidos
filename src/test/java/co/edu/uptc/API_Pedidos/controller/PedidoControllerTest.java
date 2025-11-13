package co.edu.uptc.API_Pedidos.controller;

import co.edu.uptc.API_Pedidos.model.Pedido;
import co.edu.uptc.API_Pedidos.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeCrearPedidoCuandoEsValido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNombre("capuccino");
        pedido.setTamaño("grande");

        Mockito.when(pedidoService.crearPedido(Mockito.any(Pedido.class))).thenReturn(true);

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pedido)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Pedido aceptado"));
    }

    @Test
    void rechazaPedidoCuandoBebidaNoExiste() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNombre("desconocida");
        pedido.setTamaño("mediano");

        Mockito.when(pedidoService.crearPedido(Mockito.any(Pedido.class))).thenReturn(false);

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pedido)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("La bebida no está disponible. Pedido rechazado"));
    }

    @Test
    void obtieneListaPedidos() throws Exception {
        Pedido pedido1 = new Pedido();
        pedido1.setNombre("capuccino");
        pedido1.setTamaño("grande");
        Pedido pedido2 = new Pedido();
        pedido2.setNombre("latte");
        pedido2.setTamaño("mediano");

        Mockito.when(pedidoService.listarPedidos()).thenReturn(Arrays.asList(pedido1, pedido2));

        mockMvc.perform(get("/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("capuccino"))
            .andExpect(jsonPath("$[1].nombre").value("latte"));
    }

    @Test
    void rechazaPedidoSiFaltanDatos() throws Exception {
        Pedido pedido = new Pedido();

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pedido)))
            .andExpect(status().isBadRequest());
    }
}