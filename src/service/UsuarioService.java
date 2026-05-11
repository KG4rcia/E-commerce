package service;
import model.Usuario;

public interface UsuarioService {
    void listarUsuarios();
    Usuario procurarUsuario(String cpf);
}