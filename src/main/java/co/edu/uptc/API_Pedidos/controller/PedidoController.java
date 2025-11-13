package co.edu.uptc.API_Pedidos.controller;

import co.edu.uptc.API_Pedidos.model.Pedido;
import co.edu.uptc.API_Pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<String> crearPedido(
            @RequestBody @Valid Pedido pedido) {
        if (pedido.getNombre() == null || pedido.getNombre().isBlank() ||
            pedido.getTamaño() == null || pedido.getTamaño().isBlank()) {
            return ResponseEntity.badRequest().body("nombre y tamaño son obligatorios");
        }

        boolean aceptado = pedidoService.crearPedido(pedido);
        if (aceptado) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido aceptado");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La bebida no está disponible. Pedido rechazado");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }
}