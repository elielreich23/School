package exceptions;

public class EspacoIndisponivelException extends Exception {

    public EspacoIndisponivelException() {
        super("Não há espaço disponível para alocar o animal.");
    }

    public EspacoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}

