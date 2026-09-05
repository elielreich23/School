class Livro:
    def __init__(self, isbn: str, titulo: str, preco: float):
        self.isbn = isbn
        self.titulo = titulo
        self.preco = preco

    def __str__(self) -> str:
        return f"Livro{{ISBN='{self.isbn}', titulo='{self.titulo}', preco=R$ {self.preco:.2f}}}"

    def __eq__(self, other) -> bool:
        if not isinstance(other, Livro):
            return False
        return self.isbn == other.isbn
