import sys

from cliente import Cliente
from funcionario import Funcionario
from livro import Livro
from registro_venda import RegistroVenda

# In‑memory storage
clientes = []          # type: list[Cliente]
funcionarios = []      # type: list[Funcionario]
livros = []            # type: list[Livro]
vendas = []            # type: list[RegistroVenda]


def ler_inteiro(mensagem: str) -> int:
    while True:
        try:
            valor = int(input(mensagem).strip())
            return valor
        except ValueError:
            print("Entrada inválida. Digite um número inteiro.")


def ler_float(mensagem: str) -> float:
    while True:
        try:
            # Accept comma as decimal separator
            txt = input(mensagem).strip().replace(',', '.')
            valor = float(txt)
            if valor < 0:
                raise ValueError()
            return valor
        except ValueError:
            print("Entrada inválida. Digite um número decimal positivo.")


def cadastrar_cliente():
    nome = input("Nome: ").strip()
    cpf = input("CPF: ").strip()
    email = input("Email: ").strip()
    cliente = Cliente(nome, cpf, email)
    clientes.append(cliente)
    print("Cliente cadastrado com sucesso!")
    print(cliente)


def cadastrar_funcionario():
    nome = input("Nome: ").strip()
    cpf = input("CPF: ").strip()
    cargo = input("Cargo: ").strip()
    funcionario = Funcionario(nome, cpf, cargo)
    funcionarios.append(funcionario)
    print("Funcionário cadastrado com sucesso!")
    print(funcionario)


def cadastrar_livro():
    isbn = input("ISBN: ").strip()
    titulo = input("Título: ").strip()
    preco = ler_float("Preço: ")
    livro = Livro(isbn, titulo, preco)
    livros.append(livro)
    print("Livro cadastrado com sucesso!")
    print(livro)


def criar_venda():
    if not (clientes and funcionarios and livros):
        print("É necessário ter ao menos um cliente, um funcionário e um livro cadastrados.")
        return
    # escolher cliente
    print("Clientes cadastrados:")
    for i, c in enumerate(clientes, 1):
        print(f"{i} - {c.nome}")
    idx_cliente = ler_inteiro("Escolha o cliente (número): ") - 1
    if idx_cliente < 0 or idx_cliente >= len(clientes):
        print("Cliente inexistente.")
        return
    cliente = clientes[idx_cliente]
    # escolher funcionário
    print("Funcionários cadastrados:")
    for i, f in enumerate(funcionarios, 1):
        print(f"{i} - {f.nome}")
    idx_func = ler_inteiro("Escolha o funcionário (número): ") - 1
    if idx_func < 0 or idx_func >= len(funcionarios):
        print("Funcionário inexistente.")
        return
    funcionario = funcionarios[idx_func]
    # escolher livros
    qtd = ler_inteiro("Quantidade de livros na venda: ")
    if qtd <= 0:
        print("Quantidade inválida.")
        return
    livros_venda = []
    for i in range(qtd):
        print("Livros disponíveis:")
        for j, l in enumerate(livros, 1):
            print(f"{j} - ISBN: {l.isbn} | Título: {l.titulo} | Preço: R$ {l.preco:.2f}")
        escolha = ler_inteiro(f"Escolha o livro {i+1} (número): ") - 1
        if escolha < 0 or escolha >= len(livros):
            print("Livro inexistente. Operação abortada.")
            return
        livros_venda.append(livros[escolha])
    venda = RegistroVenda(cliente, funcionario, livros_venda)
    vendas.append(venda)
    print("Venda criada com sucesso!")
    print(venda)
    print(f"Valor total da venda: R$ {venda.valor_total:.2f}")


def listar_clientes():
    if not clientes:
        print("Nenhum cliente cadastrado.")
        return
    print("=== Clientes ===")
    for c in clientes:
        print(c)


def listar_funcionarios():
    if not funcionarios:
        print("Nenhum funcionário cadastrado.")
        return
    print("=== Funcionários ===")
    for f in funcionarios:
        print(f)


def listar_livros():
    if not livros:
        print("Nenhum livro cadastrado.")
        return
    print("=== Livros ===")
    for l in livros:
        print(l)


def listar_vendas():
    if not vendas:
        print("Nenhuma venda cadastrada.")
        return
    print("=== Vendas ===")
    for v in vendas:
        print(v)


def calcular_valor_venda():
    if not vendas:
        print("Nenhuma venda cadastrada.")
        return
    print("Vendas cadastradas:")
    for i, v in enumerate(vendas, 1):
        print(f"{i} - Cliente: {v.cliente.nome} | Funcionário: {v.funcionario.nome}")
    idx = ler_inteiro("Escolha a venda (número): ") - 1
    if idx < 0 or idx >= len(vendas):
        print("Venda inexistente.")
        return
    v = vendas[idx]
    print(f"Valor total da venda: R$ {v.valor_total:.2f}")


def testar_igualdade_livros():
    if len(livros) < 2:
        print("São necessários ao menos dois livros cadastrados para comparar.")
        return
    print("Livros cadastrados:")
    for i, l in enumerate(livros, 1):
        print(f"{i} - {l.titulo} (ISBN {l.isbn})")
    idx1 = ler_inteiro("Livro 1 (número): ") - 1
    idx2 = ler_inteiro("Livro 2 (número): ") - 1
    if any(i < 0 or i >= len(livros) for i in (idx1, idx2)):
        print("Índice inválido.")
        return
    livro1 = livros[idx1]
    livro2 = livros[idx2]
    print(f"Os livros são iguais: {livro1 == livro2}")


def consultar_venda():
    if not vendas:
        print("Nenhuma venda cadastrada.")
        return
    print("Vendas cadastradas:")
    for i, v in enumerate(vendas, 1):
        print(f"{i} - Cliente: {v.cliente.nome} | Funcionário: {v.funcionario.nome}")
    idx = ler_inteiro("Escolha a venda (número): ") - 1
    if idx < 0 or idx >= len(vendas):
        print("Venda inexistente.")
        return
    v = vendas[idx]
    print(v)
    print(f"Valor total: R$ {v.valor_total:.2f}")


def exibir_menu():
    print("========================================")
    print("SISTEMA DA LIVRARIA - PYTHON")
    print("============================")
    print("1 - Cadastrar cliente")
    print("2 - Cadastrar funcionário")
    print("3 - Cadastrar livro")
    print("4 - Criar venda")
    print("5 - Listar clientes")
    print("6 - Listar funcionários")
    print("7 - Listar livros")
    print("8 - Listar vendas")
    print("9 - Calcular valor de uma venda")
    print("10 - Testar igualdade entre livros")
    print("11 - Consultar venda")
    print("0 - Sair")
    print("========================================")


def main():
    while True:
        exibir_menu()
        opcao = ler_inteiro("Escolha uma opção: ")
        print()  # espaçamento
        if opcao == 1:
            cadastrar_cliente()
        elif opcao == 2:
            cadastrar_funcionario()
        elif opcao == 3:
            cadastrar_livro()
        elif opcao == 4:
            criar_venda()
        elif opcao == 5:
            listar_clientes()
        elif opcao == 6:
            listar_funcionarios()
        elif opcao == 7:
            listar_livros()
        elif opcao == 8:
            listar_vendas()
        elif opcao == 9:
            calcular_valor_venda()
        elif opcao == 10:
            testar_igualdade_livros()
        elif opcao == 11:
            consultar_venda()
        elif opcao == 0:
            print("Encerrando o sistema...")
            break
        else:
            print("Opção inválida. Tente novamente.")
        print()  # espaçamento entre execuções

if __name__ == "__main__":
    main()
