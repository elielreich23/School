/*
 * Teste das classes da lista 1 (atividade de POO).
 * Esta classe fica no diretório raiz "lista 1" e não possui pacote.
 */
public class TestLivraria {
    public static void main(String[] args) {
        // Cria clientes usando construtores diferentes
        Cliente cliente1 = new Cliente("Ana", "12345678900", "ana@example.com");
        Cliente cliente2 = new Cliente("Bruno", "bruno@example.com"); // cpf opcional

        // Cria funcionários usando construtores diferentes
        Funcionario func1 = new Funcionario("Carlos", "98765432100", "Vendedor");
        Funcionario func2 = new Funcionario("Diana", "Gerente"); // cpf opcional

        // Cria livros usando construtores diferentes
        Livro livro1 = new Livro("Java Basics", "John Doe", "ISBN12345", 59.90);
        Livro livro2 = new Livro("Effective Java", "ISBN67890", 120.00); // autor opcional

        // Testa getters e setters
        System.out.println("--- Teste de getters ---");
        System.out.println("Cliente1 nome: " + cliente1.getNome());
        System.out.println("Livro2 preço: " + livro2.getPreco());

        cliente2.setCpf("98765432101");
        func2.setCpf("11122233344");
        livro2.setAutor("Joshua Bloch");

        // Exibe objetos usando toString()
        System.out.println("\n--- toString() dos objetos ---");
        System.out.println(cliente1);
        System.out.println(cliente2);
        System.out.println(func1);
        System.out.println(func2);
        System.out.println(livro1);
        System.out.println(livro2);

        // Testa equals() de Livro (mesmo ISBN)
        Livro livroMesmoISBN = new Livro("Outro Título", "ISBN12345", 70.0);
        System.out.println("\nLivro1 equals livroMesmoISBN? " + livro1.equals(livroMesmoISBN)); // true
        System.out.println("Livro1 equals livro2? " + livro1.equals(livro2)); // false

        // Cria registro de venda
        RegistroVenda venda = new RegistroVenda("V001", cliente1, func1, new Livro[]{livro1, livro2});
        System.out.println("\n--- Registro de venda ---");
        System.out.println(venda);
        System.out.println("Valor total da venda: R$ " + venda.calcularValorVenda());
    }
}
