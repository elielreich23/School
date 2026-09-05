class RegistroVenda:
    def __init__(self, cliente, funcionario, livros):
        """Create a sale record.
        :param cliente: Cliente object
        :param funcionario: Funcionario object
        :param livros: list of Livro objects (can contain duplicates)
        """
        self.cliente = cliente
        self.funcionario = funcionario
        self.livros = list(livros)  # ensure we have a mutable copy
        self.valor_total = self.calcular_valor_venda()

    def calcular_valor_venda(self):
        total = 0.0
        for livro in self.livros:
            if livro is not None:
                total += livro.preco
        return total

    def __str__(self):
        livros_str = ", ".join(str(l) for l in self.livros)
        return (f"RegistroVenda{{cliente={self.cliente}, funcionario={self.funcionario}, "
                f"livros=[{livros_str}], valor_total=R$ {self.valor_total:.2f}}}")
