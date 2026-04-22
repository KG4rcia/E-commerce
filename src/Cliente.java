import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private List<Pedido> pedidosCliente = new ArrayList<>();

    public Cliente(String nome, int idade, String cpf, String endereco, String cep) {
        super(nome, idade, cpf, endereco, cep);
    }

    public void fazerPedido(Pedido pedido) {
        pedidosCliente.add(pedido);
    }

    @Override
    public void info() {
        System.out.println("- CLIENTE: ");
        super.info();
    }
}
