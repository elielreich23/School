class Cliente:
    def __init__(self, nome: str, cpf: str, email: str):
        self.nome = nome
        self.cpf = cpf
        self.email = email

    def __str__(self) -> str:
        return f"Cliente{{nome='{self.nome}', cpf='{self.cpf}', email='{self.email}'}}"
