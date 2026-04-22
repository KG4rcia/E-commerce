public class Administrador extends Usuario {

    public Administrador(String nome, int idade, String cpf, String endereco, String cep) {
        super(nome, idade, cpf, endereco, cep);
    }

    @Override
    public void info() {
        System.out.println("- ADMINISTRADOR: ");
        super.info();
    }
}