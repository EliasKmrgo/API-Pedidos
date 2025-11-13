package co.edu.uptc.API_Pedidos.service;

import co.edu.uptc.API_Pedidos.model.Pedido;
import co.edu.uptc.API_Pedidos.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    private PedidoService pedidoService;
    private PedidoRepository pedidoRepositoryMock;

    @BeforeEach
    void setUp() {
        pedidoRepositoryMock = Mockito.mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepositoryMock);
    }

    @Test
    void testCrearPedido_BebidaDisponible() {
        Pedido pedido = new Pedido();
        pedido.setNombre("capuccino");
        pedido.setTamaño("grande");

        PedidoService spyService = Mockito.spy(pedidoService);
        Mockito.doReturn(true).when(spyService).bebidaDisponible("capuccino");

        boolean resultado = spyService.crearPedido(pedido);

        assertTrue(resultado);
        assertEquals("ACEPTADO", pedido.getEstadoPedido());
        Mockito.verify(pedidoRepositoryMock).save(pedido);
    }

    @Test
    void testCrearPedido_BebidaNoDisponible() {
        Pedido pedido = new Pedido();
        pedido.setNombre("bebida_inexistente");
        pedido.setTamaño("chico");

        PedidoService spyService = Mockito.spy(pedidoService);
        Mockito.doReturn(false).when(spyService).bebidaDisponible("bebida_inexistente");

        boolean resultado = spyService.crearPedido(pedido);

        assertFalse(resultado);
        assertEquals("RECHAZADO", pedido.getEstadoPedido());
        Mockito.verify(pedidoRepositoryMock, Mockito.never()).save(pedido);
    }
}