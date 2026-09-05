class Funcionario:
    def __init__(self, nome: str, cpf: str, cargo: str):
        self.nome = nome
        self.cpf = cpf
        self.cargo = cargo

    def __str__(self) -> str:
        return f"Funcionario{{nome='{self.nome}', cpf='{self.cpf}', cargo='{self.cargo}'}}"
