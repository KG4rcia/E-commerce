public class Vendedor extends Usuario {

    public Vendedor(String nome, int idade, String cpf, String endereco, String cep) {
        super(nome, idade, cpf, endereco, cep);
    }


    @Override
    public void info() {
        System.out.println("- VENDEDOR: ");
        super.info();
    }

}
