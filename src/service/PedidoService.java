package service;
import model.Pedido;
import model.Usuario;

import java.util.Scanner;

public interface PedidoService {
    void gerenciandoStatusPedido(Scanner scanner);
    void adicionarPedido(Pedido pedido);
    boolean listarPedidos(Usuario usuario);

}